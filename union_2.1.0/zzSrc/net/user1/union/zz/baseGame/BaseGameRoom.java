package net.user1.union.zz.baseGame;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import net.user1.union.api.Client;
import net.user1.union.api.Message;
import net.user1.union.api.Module;
import net.user1.union.api.Room;
import net.user1.union.core.attribute.Attribute;
import net.user1.union.core.context.ModuleContext;
import net.user1.union.core.event.RoomEvent;
import net.user1.union.core.exception.AttributeException;
import net.user1.union.core.exception.ClientNotFoundException;
import net.user1.union.zz.baseGame.event.BaseGameRoomEvent;
import net.user1.union.zz.baseGame.lib.vo.BaseUserVO;
import net.user1.union.zz.baseGame.lib.vo.IRoomVO;
import net.user1.union.zz.baseGame.lib.vo.IUserVO;
import net.user1.union.zz.common.ClientHelper;
import net.user1.union.zz.common.DBCon;
import net.user1.union.zz.common.UnionDataObject;
import net.user1.union.zz.common.model.tables.Zzgame;
import net.user1.union.zz.common.model.tables.Zzgameinst;
import net.user1.union.zz.common.model.tables.Zzgameseat;
import net.user1.union.zz.common.model.tables.Zzgameusersummary;
import net.user1.union.zz.common.model.tables.Zzproduct;
import net.user1.union.zz.common.model.tables.Zzuser;
import net.user1.union.zz.common.model.tables.Zzzlrogamebet;
import net.user1.union.zz.common.model.tables.Zzzlrogameround;
import net.user1.union.zz.zalerioGame.lib.vo.PlayerVO;

import org.apache.log4j.Logger;
import org.jooq.Field;
import org.jooq.JoinType;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Select;
import org.jooq.SelectQuery;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.Factory;

public class BaseGameRoom implements Module , IRoomVO{
	protected static Class<?> CURRENT_USER_VO = null;

	private static Logger logger = Logger.getLogger(BaseGameRoom.class);
	
	private int userCount = 0;
    private long userTotal = 0;
    private String roomName = "";
    private String roomPrizeName = "";
    private String roomPrizeId = "";
    private String roomPrizeCategory = "";
    private String roomImage = "";
    private String roomType="";
    private double roomMrp = 0.00;
    private String roomStartTime = "";
    private String roomEndTime = "";
    private Timestamp roomEndTimestamp = new Timestamp(0);
    private Timestamp roomStartTimestamp = new Timestamp(0);
    private long roomTTL=0;
    private int roomSize = 0;
    private String roomDbId = "dbId";//Auction_id from DB
    private int roomSFId = -1;//Server created ID (SmartFox)
    private String gameCreatorId;
    
    private boolean roomValChanged = true;
    
    private boolean flag_userCount = true;
    private boolean flag_userTotal = true;
    private boolean flag_winnerId = false;
    private String winnerId;
    
    protected HashMap<String,IUserVO> hmUserVOs;
    
	protected ModuleContext r_ctx = null;
	
	protected Room currentRoom = null ;
	
	protected Factory roomFactory = null;
	protected Factory roomFactoryWrite = null;
	
	Timer syncTTLTimer;

	private long totalSeats;

	private HashMap<String, String> hmUserVOsSeatIndex;
	
	public BaseGameRoom(){
		super();
		winnerId = OloConstant.STRING_NOTSET;
		hmUserVOs = new HashMap<String, IUserVO>();
		hmUserVOsSeatIndex = new HashMap<String, String>();
		syncTTLTimer = new Timer();
		
	}
	@Override
	public boolean init(ModuleContext ctx) {
		r_ctx = ctx;
		currentRoom = r_ctx.getRoom();
		roomFactory = DBCon.getFactory();
		roomFactoryWrite = DBCon.getFactory();
		CURRENT_USER_VO = BaseUserVO.class;
		currentRoom.addEventListener(RoomEvent.MODULE_MESSAGE, this, "onRoomModuleRequest");
		currentRoom.addEventListener(RoomEvent.ADD_CLIENT, this, "onClientAdd");
		//Added listener to catch the event when a client leaves the room
		currentRoom.addEventListener(RoomEvent.REMOVE_CLIENT, this, "onClientRemove");
		initRoom();
		updateRoomVars();
		
		/*
		 * Initialising the game end time sync for each player in the room
		 */
		runTTLSyncTimer();
				
		return true;
	}
	
	private void  runTTLSyncTimer(){
		
		
		//taskHandle = TaskRunner.this.sfs.getTaskScheduler().scheduleAtFixedRate(new TaskRunner(), 0, 1, TimeUnit.SECONDS);
		TimerTask syncTTLTask = new TimerTask() {
			
			@Override
			public void run() {
				
				long ttl = getRoomTTL();
				setPublicRoomVar(IRoomVO.ROOM_TTL,ttl);
			}
		};
		syncTTLTimer.scheduleAtFixedRate(syncTTLTask, 10000, 10000);
		
		
	}
	
	public void initRoom(){
		setRoomDbId((String) r_ctx.getRoom().getAttributeValue(IRoomVO.ROOM_GAMEINST_ID));
		String gameInstanceId = getRoomDbId();
		try{
			Factory factory = DBCon.getFactory();
			if(factory!=null){
				SelectQuery q = factory.selectQuery();
					q.addFrom(Zzgameinst.ZZGAMEINST);
					q.addSelect(Factory.field("EXTRACT(EPOCH FROM gameinst_endtime - now())",Long.class).as("GAME_INST_TTL"));
					q.addSelect(Zzgameinst.ZZGAMEINST.getFields());
					q.addSelect(Zzgame.ZZGAME.getFields());
					q.addSelect(Zzproduct.ZZPRODUCT.getFields());
					q.addConditions(Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(gameInstanceId));
					q.addJoin(Zzgame.ZZGAME, JoinType.JOIN,Zzgame.ZZGAME.GAME_ID.equal(Zzgameinst.ZZGAMEINST.GAMEINST_GAME_ID));
					q.addJoin(Zzproduct.ZZPRODUCT, JoinType.LEFT_OUTER_JOIN,Zzgameinst.ZZGAMEINST.GAMEINST_PRODUCT_ID.equal(Zzproduct.ZZPRODUCT.PRODUCT_ID));						
					q.addGroupBy(Zzgameinst.ZZGAMEINST.getFields());
					q.addGroupBy(Zzgame.ZZGAME.getFields());
					q.addGroupBy(Zzproduct.ZZPRODUCT.getFields());
					
				Record result = q.fetchOne();
				
				if(result!=null){
					loadFromDataRow(result);
					setRoomVars();
				}
			}
		}catch(Exception e){
			logger.error("Exception occoured at UserLoginEventHandler:checkuserAuthentication() : " + e);
			e.printStackTrace();
		}		
	}
	
	public void onClientAdd(RoomEvent e){
		Client client = e.getClient();
		addClientToRoom(client);
		updateClientGameSnapshot(client);
		updateClientAttrs(client);
		updateRoomVars();
		
		// To update the current played game for the user
		ClientHelper.updateCurrentActiveGameForUser(getRoomDbId(), null, client.getUserID());
		logger.debug(" UserVOs Hashmap : " + hmUserVOs);
	}
	
	public void onClientRemove(RoomEvent e){
		// To update the current played game for the user to null
		ClientHelper.updateCurrentActiveGameForUser(null, getRoomDbId(), e.getClient().getUserID());
	}
	
	public void getUserStatus(Client client) {
		this.updateClientAttrs(client);
    }
	
	public void updateClientAttrs(Client client){
		IUserVO userVO = (IUserVO) getUserVO(client.getUserID());
		refreshUserVariables(client);
		if (userVO != null){
			userVO.setUserVars();
			userVO.updateUserVars();
		}
	}
	
	protected void addClientToRoom(Client client){
		Select<Record> q = generateClientAttributeQuery(client);
		Record result = null;
		try{
			result = q.fetchOne();
		
			if(result!=null){
				this.setNewUserVO(client,result);
			}else{
				throw new Exception("User Record not found in DB!");
			}
		}catch(Exception e){
			logger.error("Exception occoured in BaseGameRoom:addClientToRoom() : " + e);
			e.printStackTrace();
		}
	}
	
	protected void addUserVOToRoomByUserLoginId(String userLoginId){
		Select<Record> q = generateClientAttributeQueryByUserLoginId(userLoginId);
		Record result = null;
		try{
			result = q.fetchOne();
		
			if(result!=null){
				this.setNewUserVO(null,result);
			}else{
				throw new Exception("User Record not found in DB!");
			}
		}catch(Exception e){
			logger.error("Exception occoured in BaseGameRoom:addUserVOToRoom() : " + e);
			e.printStackTrace();
		}
	}
	
	protected void addUserVOToRoomBySeatId(String userSeatId){
		Select<Record> q = generateClientAttributeQueryByUserSeatId(userSeatId);
		Record result = null;
		try{
			result = q.fetchOne();
		
			if(result!=null){
				this.setNewUserVO(null,result);
			}else{
				throw new Exception("User Record not found in DB!");
			}
		}catch(Exception e){
			logger.error("Exception occoured in BaseGameRoom:addUserVOToRoom() : " + e);
			e.printStackTrace();
		}
	}
	
	protected IUserVO getUserVO(String userDBId){
		if(userDBId != null && userDBId.length()>0 && hmUserVOs.containsKey(userDBId)){
			return (IUserVO) hmUserVOs.get(userDBId);
		}
		return null;
	}
	
	protected void setUserVO(IUserVO userVO){
		String userLoginId = userVO.getUserLoginId();
		if(userLoginId != null && userLoginId.length() > 0){
			hmUserVOs.put(userLoginId, userVO);
			hmUserVOsSeatIndex.put(userVO.getUserSeatId(),userLoginId);
		}
	}
	
	protected boolean containsUserVO(String userLoginId){
		return hmUserVOs.containsKey(userLoginId);
	}
	
	protected boolean setNewUserVO(Client client, Record userRecord){
		try{
			
			IUserVO userVO = getNewUserVO(client,userRecord);
			if(userVO != null){
				setUserVO(userVO);
				return true;
			}else{
				throw new Exception("Unable to set userVO");
			}
		}catch(Exception e){
			logger.error("Error in setting user VO in setNewUserVO() : " + e);
			e.printStackTrace();
		}
		return false;
	}
	
	protected IUserVO getNewUserVO(Client client,Record userRecord){
		IUserVO userVO = null;
		try {
			String userLoginId = null;
			if(userRecord.getValueAsString(Zzuser.ZZUSER.USER_ID)!= null && userRecord.getValueAsString(Zzuser.ZZUSER.USER_ID).length()>0){
        		userLoginId = userRecord.getValueAsString(Zzuser.ZZUSER.USER_ID);
        	}
			
			if(userLoginId != null){
				if(hmUserVOs.containsKey(userLoginId)){
					userVO = hmUserVOs.get(userLoginId);
				}else if(client != null){
					userVO = (IUserVO) CURRENT_USER_VO.getDeclaredConstructor(Client.class).newInstance(client);
				}else{
					userVO = (IUserVO) CURRENT_USER_VO.getDeclaredConstructor().newInstance();
				}
				if(client != null){
					userVO.setUnionClient(client);
				}
				userVO.loadClientDataFromDBRecord(userRecord);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return userVO;
	}
	
	protected Select<Record> generateClientAttributeQuery(Client client){
		String userLoginId;
		userLoginId = (String) client.getAttributeValue(IUserVO.USER_LOGIN_ID);
		return generateClientAttributeQueryByUserLoginId(userLoginId);
	}
	
	protected Select<Record> generateClientAttributeQueryByUserSeatId(String userSeatId){
		Factory factory = roomFactory;
		if(factory!= null && userSeatId != null && userSeatId.length() > 0){
			Select<Record> q = factory.select()
					.from(Zzuser.ZZUSER)
					.join(Zzgameseat.ZZGAMESEAT).on(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID.equal(Zzuser.ZZUSER.USER_ID))
					.join(Zzgameinst.ZZGAMEINST).on(Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID))
					.join(Zzgame.ZZGAME).on(Zzgame.ZZGAME.GAME_ID.equal(Zzgameinst.ZZGAMEINST.GAMEINST_GAME_ID))
					.join(Zzproduct.ZZPRODUCT).on(Zzproduct.ZZPRODUCT.PRODUCT_ID.equal(Zzgameinst.ZZGAMEINST.GAMEINST_PRODUCT_ID))
					.where(Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(currentRoom.getSimpleID()))
						.and(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID.equal(userSeatId))
					;
			return q;
		}
		return null;
	}
	
	protected Select<Record> generateClientAttributeQueryByUserLoginId(String userLoginId){
		Factory factory = roomFactory;
		if(factory!= null && userLoginId != null && userLoginId.length() > 0){
			Select<Record> q = factory.select()
					.from(Zzuser.ZZUSER)
					.join(Zzgameseat.ZZGAMESEAT).on(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID.equal(Zzuser.ZZUSER.USER_ID))
					.join(Zzgameinst.ZZGAMEINST).on(Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID))
					.join(Zzgame.ZZGAME).on(Zzgame.ZZGAME.GAME_ID.equal(Zzgameinst.ZZGAMEINST.GAMEINST_GAME_ID))
					.join(Zzproduct.ZZPRODUCT).on(Zzproduct.ZZPRODUCT.PRODUCT_ID.equal(Zzgameinst.ZZGAMEINST.GAMEINST_PRODUCT_ID))
					.where(Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(currentRoom.getSimpleID()))
							.and(Zzuser.ZZUSER.USER_ID.equal(userLoginId))
					;
			return q;
		}
		
			/*
			SelectQuery q = factory.selectQuery();
				q.addFrom();
				q.addJoin(Person.PERSON, JoinType.JOIN,UserLogin.USER_LOGIN.PARTY_ID.equal(Person.PERSON.PARTY_ID));
				q.addSelect(Factory.field("EXTRACT(EPOCH FROM olo_game_inst.end_time - now())",Long.class).as("GAME_INST_TTL"));
				q.addSelect(UserLogin.USER_LOGIN.getFields());
				q.addSelect(Person.PERSON.getFields());
				q.addSelect(OloGameInst.OLO_GAME_INST.getFields());
				q.addSelect(OloGameSeat.OLO_GAME_SEAT.getFields());
				q.addSelect(OloGame.OLO_GAME.getFields());
				q.addSelect(Product.PRODUCT.getFields());
				q.addJoin(OloGameInst.OLO_GAME_INST, JoinType.JOIN, OloGameInst.OLO_GAME_INST.GAME_INST_ID.equal(currentRoom.getSimpleID()));
				q.addJoin(OloGameSeat.OLO_GAME_SEAT, JoinType.JOIN,OloGameSeat.OLO_GAME_SEAT.GAME_PLAYER_ID.equal(UserLogin.USER_LOGIN.PARTY_ID).and(OloGameSeat.OLO_GAME_SEAT.GAME_INST_ID.equal(OloGameInst.OLO_GAME_INST.GAME_INST_ID)));
				q.addJoin(OloGame.OLO_GAME, JoinType.JOIN,OloGame.OLO_GAME.GAME_ID.equal(OloGameInst.OLO_GAME_INST.GAME_ID));
				q.addJoin(Product.PRODUCT, JoinType.LEFT_OUTER_JOIN,OloGameInst.OLO_GAME_INST.GAME_PRIZE_ID.equal(Product.PRODUCT.PRODUCT_ID));						
				q.addConditions(UserLogin.USER_LOGIN.USER_LOGIN_ID.equal(userLoginId));
				q.addGroupBy(UserLogin.USER_LOGIN.PARTY_ID);
				q.addGroupBy(UserLogin.USER_LOGIN.getFields());
				q.addGroupBy(Person.PERSON.getFields());
				q.addGroupBy(OloGameInst.OLO_GAME_INST.getFields());
				q.addGroupBy(OloGameSeat.OLO_GAME_SEAT.getFields());
				q.addGroupBy(OloGame.OLO_GAME.getFields());
				q.addGroupBy(Product.PRODUCT.getFields());
			return q;
			*/
		
		return null;
	}
	
	@Override
	public void shutdown() {
		syncTTLTimer.cancel();
	}
	
	/*
	 * Current nothing specifically happens here
	 */
	public void onRoomModuleRequest(RoomEvent evt){
		//Client client = evt.getClient();
		Message msg = evt.getMessage();
		String CMD = msg.getArg(BaseGameServer.EXTERNAL_COMMAND);
		logger.info("Message recieved in BaseGameRoom : cmd:" + CMD);
		if(CMD.equalsIgnoreCase(BaseGameRoomEvent.CMD_GAME_INSTANCE_START)){
			try {
				//joinTheRoom(client, "123");
				//s_ctx.getServer().getRoom("123").setAttribute("TU", TU, Attribute.SCOPE_GLOBAL,OloConstant.ATTR_SERVER_SHARED);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(CMD.equalsIgnoreCase(BaseGameRoomEvent.CMD_GAME_INSTANCE_COMPLETED)){
			try {
				//s_ctx.getServer().getRoom("123").setAttribute("TU", TU, Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void setPublicRoomVar(String key , Object val){
		try{
			r_ctx.getRoom().setAttribute(key,val, Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED);
		}catch(AttributeException e){
			logger.debug("Exception occoured in BaseGameRoom::setPublicRoomVar() : " + e);
			e.printStackTrace();
		}
    }
	 	
	    @Override
		public boolean isFlag_userCount() {
	        return flag_userCount;
	    }

	    private void setFlag_userCount(boolean flag_userCount) {
	        this.flag_userCount = flag_userCount;
	    }

	    
	    @Override
		public boolean isFlag_userTotal() {
	        return flag_userTotal;
	    }

	    private void setFlag_userTotal(boolean flag_userTotal) {
	        this.flag_userTotal = flag_userTotal;
	    }

	    
	    @Override
		public int getUserCount() {
	        return userCount;
	    }

	    @Override
		public void setUserCount(int userCount) {
	        if (this.userCount != userCount) {
	            this.setRoomValChanged(true);
	            flag_userCount = true;
	        }
	        this.userCount = userCount;
	    }

	    @Override
		public long getUserTotal() {
	        return userTotal;
	    }

	    @Override
		public void setUserTotal(long userTotal) {
	        if (this.userTotal != userTotal) {
	        	this.setRoomValChanged(true);
	            flag_userTotal = true;
	        }
	        this.userTotal = userTotal;
	    }

	    @Override
		public int getRoomSFId() {
	        return roomSFId;
	    }

	    @Override
		public void setRoomSFId(int roomSFId) {
	        this.roomSFId = roomSFId;
	        //OloBaseExtension.setOloGameEndTimer(roomSFId, getRoomEndTimestamp().getTime());
	    }

	    @Override
		public String getRoomName() {
	        return roomName;
	    }

	    @Override
		public void setRoomName(String roomName) {
	        //Trimming roomName if length > 15
	        if (roomName.length() > 10) {
	            roomName = roomName.substring(0, 10);
	        }
	        this.roomName = roomName;
	    }

	    

	    @Override
		public String getRoomType() {
	        return roomType;
	    }

	    @Override
		public void setRoomType(String roomType) {
	        this.roomType = roomType;
	    }

	    @Override
		public String getRoomImage() {
	        return roomImage;
	    }

	    @Override
		public void setRoomImage(String roomImage) {
	        this.roomImage = roomImage;
	    }

	    @Override
		public double getRoomMrp() {
	        return roomMrp;
	    }

	    @Override
		public void setRoomMrp(double roomMrp) {
	        this.roomMrp = roomMrp;
	    }

	    private String getRoomStartTime() {
	        return roomStartTime;
	    }

	    @Override
		public void setRoomStartTime(String roomStartTime) {
	        this.roomStartTime = roomStartTime;
	    }

	    private String getRoomEndTime() {
	        return roomEndTime;
	    }

	    @Override
		public void setRoomEndTime(String roomEndTime) {
	        this.roomEndTime = roomEndTime;
	    }

	    @Override
		public String getRoomDbId() {
	        return roomDbId;
	    }

	    @Override
		public void setRoomDbId(String roomDbId) {
	        this.roomDbId = roomDbId;
	    }
	    
	    public String getGameCreatorId() {
			return gameCreatorId;
		}
		public void setGameCreatorId(String gameCreatorId) {
			this.gameCreatorId = gameCreatorId;
		}

		@Override
		public int getRoomSize() {
	        return roomSize;
	    }

		@Override
		public void setRoomSize(int roomSize) {
	        this.roomSize = roomSize;
	    }

	    @Override
		public long getRoomTTL() {
	    	//setRoomTTL(roomTTL);
	    	Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	    	long secondsLeft = ((this.getRoomEndTimestamp().getTime() - currentTimestamp.getTime())/1000);
	    	setRoomTTL(secondsLeft);
	        return this.roomTTL;
	    }
	    
	    public long getRoomDuration(){
	    	long gameDuration = ((this.getRoomEndTimestamp().getTime() - this.getRoomStartTimestamp().getTime())/1000);
	    	return gameDuration;
	    	
	    }

	    @Override
		public void setRoomTTL(long roomTTL) {
	        this.roomTTL = roomTTL;
	    }

	    @Override
		public void updateRoomVars() {
	    	refreshRoomVars();
	    	try{
		        if (this.isRoomValChanged()) {
	
		            if (this.isFlag_userCount()) {
		                r_ctx.getRoom().setAttribute(IRoomVO.ROOM_USERCOUNT, this.getUserCount(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		                this.setFlag_userCount(false);
		            }
	
		            if (this.isFlag_userTotal()) {
		                r_ctx.getRoom().setAttribute(IRoomVO.ROOM_USERTOTAL, this.getUserTotal(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		                this.setFlag_userTotal(false);
		            }
		            
		            this.setRoomValChanged(false);
		        }
	    	} catch (AttributeException e) {
				logger.error("Error occoured in setRoomVars(): " + e);
				e.printStackTrace();
			}
	    }

	    public void setRoomVars() {
	    	logger.info("Setting up roomVars in BaseGameRoom");
	        Room room = r_ctx.getRoom();
	        try {
				room.setAttribute(IRoomVO.ROOM_USERCOUNT, this.getUserCount(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_USERTOTAL, this.getUserTotal(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_NAME, this.getRoomName(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_STARTTIME, this.getRoomStartTime(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_ENDTIME, this.getRoomEndTime(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_TTL, this.getRoomTTL(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_GAMEDURATION, this.getRoomDuration(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_SIZE, this.getRoomSize(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_MRP, this.getRoomMrp(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_IMAGE, this.getRoomImage(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_PRIZE_ID, this.getRoomPrizeId(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_PRIZE_NAME, this.getRoomPrizeName(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_TYPE, this.getRoomType(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_TTL, this.getRoomTTL(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_TOTALSEATS, this.getTotalSeats(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_PRIZE_CATEGORY, this.getRoomPrizeCategory(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
		        room.setAttribute(IRoomVO.ROOM_WINNER_ID, this.getWinnerId(),Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
	        } catch (AttributeException e) {
				logger.error("Error occoured in setRoomVars(): " + e);
				e.printStackTrace();
			}
	    }

	    /* (non-Javadoc)
		 * @see com.olo.base.vo.IRoomVO#loadFromDataRow(org.jooq.Record)
		 */
	    @Override
		public void loadFromDataRow(Record row) {
	    	try{
		        if (row != null) {
		        	if(row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_ID) != null && row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_ID).length()>0) 
		        		this.setRoomDbId(row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_ID));
		        	
		        	if(row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_CREATED_BY) != null && row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_CREATED_BY).length()>0) {
		        		this.setGameCreatorId(row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_CREATED_BY));
		        	}
		        	
		        	if(row.getValueAsString(Zzgame.ZZGAME.GAME_NAME) != null && row.getValueAsString(Zzgame.ZZGAME.GAME_NAME).length()>0 && row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_ID)!= null && row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_ID).length()>0)
		        		this.setRoomName(row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_ID) + " - " + row.getValueAsString(Zzgame.ZZGAME.GAME_NAME));
		        	
		        	if(row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_PRODUCT_ID) != null && row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_PRODUCT_ID).length()>0){
		        		this.setRoomPrizeId(row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_PRODUCT_ID));	        		
		            }
		        	
		        	if(row.getValueAsString(Zzproduct.ZZPRODUCT.PRODUCT_NAME) != null && row.getValueAsString(Zzproduct.ZZPRODUCT.PRODUCT_NAME).length()>0){
		        		this.setRoomPrizeName(row.getValueAsString(Zzproduct.ZZPRODUCT.PRODUCT_NAME));	        		
		            }
		            
		            if(row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_STARTTIME) != null && row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_STARTTIME).length()>0){
		            	this.setRoomStartTime(row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_STARTTIME));
		            	this.setRoomStartTimestamp(row.getValueAsTimestamp(Zzgameinst.ZZGAMEINST.GAMEINST_STARTTIME));
		            }
		            
		            long totalSeats = 0;
					if(row.getValueAsLong(Zzgameinst.ZZGAMEINST.GAMEINST_CAPACITY) != null){
						totalSeats += row.getValueAsLong(Zzgameinst.ZZGAMEINST.GAMEINST_CAPACITY);
					}
					/*
					if(row.getValueAsLong(OloGameInst.OLO_GAME_INST.SEAT_QUOTA_PROMOTIONAL) != null){
						totalSeats += row.getValueAsLong(OloGameInst.OLO_GAME_INST.SEAT_QUOTA_PROMOTIONAL);
					}
					*/
					this.setTotalSeats(totalSeats);
		            
		            if(row.getValueAsString("GAME_INST_TTL") != null && row.getValueAsString("GAME_INST_TTL").length()>0)
		            	this.setRoomTTL(row.getValueAsLong("GAME_INST_TTL"));
		            
		            
		            if(row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME) != null && row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME).length()>0){
		            	this.setRoomEndTime(row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME));
		            	this.setRoomEndTimestamp(row.getValueAsTimestamp(Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME));
		            }
		            
		            if(row.getValueAsString(Zzgameinst.ZZGAMEINST.GAMEINST_CAPACITY) != null && row.getValueAsInteger(Zzgameinst.ZZGAMEINST.GAMEINST_CAPACITY)>0)
		            	this.setRoomSize(row.getValueAsInteger(Zzgameinst.ZZGAMEINST.GAMEINST_CAPACITY));
		            /*
		            if(row.getValueAsString(OloGameInst.OLO_GAME_INST.GAME_INST_CAT) != null && row.getValueAsString(OloGameInst.OLO_GAME_INST.GAME_INST_CAT).length()>0)
		            	this.setRoomType(row.getValueAsString(OloGameInst.OLO_GAME_INST.GAME_INST_CAT));
		            */
		            
		            /*
		            if(row.getValueAsString(Product.PRODUCT.PRIMARY_PRODUCT_CATEGORY_ID) != null && row.getValueAsString(Product.PRODUCT.PRIMARY_PRODUCT_CATEGORY_ID).length()>0)
		            	this.setRoomPrizeCategory(row.getValueAsString(Product.PRODUCT.PRIMARY_PRODUCT_CATEGORY_ID));
		            */
		        }
	    	}catch(Exception e){
	    		logger.debug("BaseGameRoom::loadFromDataRow() - Exception occoured: " + e.toString());    		
	    	}

	    }
	    
	    

	    private String getRoomPrizeCategory() {
			return roomPrizeCategory;
		}

	    /*
		private void setRoomPrizeCategory(String roomPrizeCategory) {
			this.roomPrizeCategory = roomPrizeCategory;
		}
		*/

	    @Override
	    public String toString() {

	        return "[{Room Name:" + this.getRoomName()  
	                + "},{User Total:" + this.getUserTotal()
	                + "},{User Count:" + this.getUserCount()
	                + "},{Room StartTime:" + this.getRoomStartTime() 
	                + "},{Room EndTime:" + this.getRoomEndTime()
	                + "},{Room Size: " + this.getRoomSize() 
	                + "},{Room DB Id: " + this.getRoomDbId()
	                + "},{Room SF Id: " + this.getRoomSFId() 
	                + "}]";

	    }

		public void setRoomPrizeId(String roomPrizeId) {
			this.roomPrizeId = roomPrizeId;
		}

		public String getRoomPrizeId() {
			return roomPrizeId;
		}

		public void setRoomPrizeName(String roomPrizeName) {
			this.roomPrizeName = roomPrizeName;
		}

		public String getRoomPrizeName() {
			return roomPrizeName;
		}

		public void setRoomEndTimestamp(Timestamp roomEndTimestamp) {
	        if (this.roomEndTimestamp.getTime() != roomEndTimestamp.getTime()) {
	        	this.roomEndTimestamp = roomEndTimestamp;
	        }
		}

		public Timestamp getRoomEndTimestamp() {
			return roomEndTimestamp;
		}
		
		public String getWinnerId() {
			return this.winnerId;
		}
		
		public void setWinnerId(String winnerId) {
			if(!this.getWinnerId().equalsIgnoreCase(winnerId)){
				this.setRoomValChanged(true);
				setFlag_winnerId(true);
				this.winnerId = winnerId;
			}
		}

		public void setFlag_winnerId(boolean flag_winnerId) {
			this.flag_winnerId = flag_winnerId;
		}

		public boolean isFlag_winnerId() {
			return flag_winnerId;
		}
		
		
		@Override
		public void refreshRoomVars() {
			setUserCount(r_ctx.getRoom().getNumClients());
			Factory factory = this.roomFactory;
			SelectQuery qTotalUserSeats = factory.selectQuery();
				qTotalUserSeats.addFrom(Zzgameseat.ZZGAMESEAT);
				qTotalUserSeats.addSelect(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID.count().as("total_seats"),Zzgameinst.ZZGAMEINST.GAMEINST_CAPACITY);
				qTotalUserSeats.addConditions(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.equal(this.currentRoom.getSimpleID()));
				qTotalUserSeats.addJoin(Zzgameinst.ZZGAMEINST, Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID));
				qTotalUserSeats.addGroupBy(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID,Zzgameinst.ZZGAMEINST.GAMEINST_CAPACITY);
			Record rec = null ;
			try{
				rec = qTotalUserSeats.fetchOne();
			
				if ( rec != null){
					if(rec.getValueAsLong("total_seats") != null ){
						this.setUserTotal(rec.getValueAsLong("total_seats"));
					}
					
				}
			}catch(Exception e){
				logger.error("Exception occoured at BaseGameRoom::refreshRoomVars() : " + e);
				e.printStackTrace();
			}
			SelectQuery qEndTime = factory.selectQuery();
				qEndTime.addFrom(Zzgameinst.ZZGAMEINST);
				qEndTime.addSelect(Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME);
				qEndTime.addConditions(Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(this.currentRoom.getSimpleID()));
			rec = null;
			try{
				rec = qEndTime.fetchOne();
			
				if(rec != null ){
					if(rec.getValueAsTimestamp(Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME) != null){
						this.setRoomEndTimestamp(rec.getValueAsTimestamp(Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME));
					}
				}
				
			}catch(DataAccessException e){
				logger.error("Exception occoured at BaseGameRoom::refreshRoomVars() : " + e);
				e.printStackTrace();
			}
		}

		private void setTotalSeats(long tSeats) {
			this.totalSeats = tSeats;
		}
		
		public long getTotalSeats(){
			return this.totalSeats;
		}

		private void setRoomValChanged(boolean roomValChanged) {
			this.roomValChanged = roomValChanged;
		}

		private boolean isRoomValChanged() {
			return roomValChanged;
		}

		protected void refreshUserVariables(Client client) {
			// TODO Auto-generated method stub
			
		}
		
		public Client findUserByUserLoginId(String userLoginId){
	    	Client client = null;
		    if(userLoginId != null && userLoginId.length() > 0){
		    	try{
		    		client = r_ctx.getServer().getClientByUserID(userLoginId);
		    	}catch(Exception e){
		    		logger.debug("Exception occoured in findUserByUserLoginId() : " + e);
		    		//e.printStackTrace();
		    	}
	    	}
	    	return client;
	    }
	    
	    /**
	     * Will return the user information based on the user_id in the database
	     *
	     * @param user_id Mapped to user_id in the database
	     * @param room room in which to find user
	     * @return User object or null if not found
	     */
	    public Client findUserByPartyId(String userPartyId) {
	        HashMap<String,IUserVO> clientVOs = this.hmUserVOs;
	        Set<String> clientVOKeys = clientVOs.keySet();
	        IUserVO clientVO = null;
	        for(String clientVOKey : clientVOKeys){
	        	clientVO = clientVOs.get(clientVOKey);
	        	if(clientVO.getUserPartyId().equalsIgnoreCase(userPartyId)){
	        		return findUserByUserLoginId(clientVO.getUserLoginId());
	        	}
	        }
	        return null;
	    }
	    
	    public Client findUserBySeatId(String userSeatId) {
	    	logger.debug("START : BaseGameRoom:findUserBySeatId() [userSeatId:" + userSeatId + "]");
	    	if(this.hmUserVOsSeatIndex.containsKey(userSeatId)){
	    		return findUserByUserLoginId(this.hmUserVOsSeatIndex.get(userSeatId));
	    	}else{
	    		
	    	}
	    	return null;
	    	
	    	/**
	    	
	        HashMap<String,IUserVO> clientVOs = this.userVOs;
	        Set<String> clientVOKeys = clientVOs.keySet();
	        IUserVO clientVO = null;
	        logger.debug("ClientVOs size : " + this.userVOs.size() );
	        for(String clientVOKey : clientVOKeys){
	        	
	        	clientVO = clientVOs.get(clientVOKey);
	        	logger.debug("ClientVO : " + clientVO);
	        	if(clientVO.getUserSeatId().equalsIgnoreCase(userSeatId)){
	        		return findUserByUserLoginId(clientVO.getUserLoginId());
	        	}
	        }
	        return null;
	        */
	    }
	    
	    public IUserVO findUserVOBySeatId(String userSeatId){
	    	logger.debug("START : BaseGameRoom:findUserVOBySeatId() [userSeatId:" + userSeatId + "]");
	    	IUserVO userVO = null;
	    	try{
		    	if(this.hmUserVOsSeatIndex.containsKey(userSeatId)){
		    		userVO = hmUserVOs.get(this.hmUserVOsSeatIndex.get(userSeatId));
		    	}else{
		    		addUserVOToRoomBySeatId(userSeatId);
		    		if(this.hmUserVOsSeatIndex.containsKey(userSeatId)){
			    		userVO =  hmUserVOs.get(this.hmUserVOsSeatIndex.get(userSeatId));
			    	}else{
			    		throw new Exception("Unable to generate or find UserVO by userSeatId [userSeatId:" + userSeatId + "]");
			    	}
		    	}
	    	}catch(Exception e){
	    		logger.error("Error in BaseGameRoom:findUserVOBySeatId() : " + e);
	    		e.printStackTrace();
	    	}
	    	return userVO;
	    }

		public void updateClientGameSnapshot(Client client) {
			
			
		}

		private Timestamp getRoomStartTimestamp() {
			return roomStartTimestamp;
		}

		private void setRoomStartTimestamp(Timestamp roomStartTimestamp) {
			this.roomStartTimestamp = roomStartTimestamp;
		}
		
		protected void intimateBidder(UnionDataObject udObj, Client client, String cmd) {
		    if (currentRoom.containsClient(client)){
		    	client.sendMessage(cmd, udObj.toString());
		    } else {
		    	logger.error("intimateBidder() User : null -- User not logged in");
		    }
		}
		
		
		
		protected void updateAllUserVOVarsToGame(){
			Set<String> userLoginIds = hmUserVOs.keySet();
			for(String userLoginId:userLoginIds){
				IUserVO userVO = hmUserVOs.get(userLoginId);
				userVO.updateUserVars();
			}
		}
		
		protected Set<PlayerVO> getAllPlayersForGame(String gameId, boolean flag_gameover) {
			
			Set<PlayerVO> players = new HashSet<PlayerVO>();
			
			logger.info("Fetching all players for the Game = " + gameId);
			
			Select<Record> zzgameseatRecords = null;
			
			if(!flag_gameover) {
				Timestamp now = new Timestamp(System.currentTimeMillis());
				Field<Object> betsPlaced =
						roomFactory.selectCount()
					          .from(Zzzlrogamebet.ZZZLROGAMEBET)
					          .where(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_GAMESEAT_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID).and(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ZLGAMEROUND_ID.equal(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID)))
					          .asField("BETS_PLACED");
				Field<Object> lastPlayed = 
						roomFactory.select(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_BETTIME.max())
				          .from(Zzzlrogamebet.ZZZLROGAMEBET)
				          .where(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_GAMESEAT_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID))
				          .asField("LAST_PLAYED");
				zzgameseatRecords = roomFactory
						.select(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID, Zzuser.ZZUSER.USER_ID, Zzgameseat.ZZGAMESEAT.GAMESEAT_ID, Zzuser.ZZUSER.USER_FNAME,
								Zzuser.ZZUSER.USER_LNAME, Zzgameseat.ZZGAMESEAT.GAME_RANK, Zzuser.ZZUSER.USER_FBID, Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID,
								Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ROUNDNAME, betsPlaced, lastPlayed, Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_LEVEL,
								Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAMES_PLAYED, Zzgameseat.ZZGAMESEAT.GAMESEAT_STATUS_UPDATE_TIME,
								Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAME_WON, Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS, Zzgameseat.ZZGAMESEAT.GAMESEAT_SCORE)
						.from(Zzgameseat.ZZGAMESEAT, Zzuser.ZZUSER, Zzzlrogameround.ZZZLROGAMEROUND, Zzgameusersummary.ZZGAMEUSERSUMMARY)
						.where(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.equal(gameId)
								.and(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID.equal(Zzuser.ZZUSER.USER_ID)))
								.and(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_GAMEINST_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID))
								.and(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_TIMESTART.lessOrEqual(now))
								.and(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_TIMEEND.greaterThan(now))
								.and(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_ID.equal(Zzuser.ZZUSER.USER_ID))
						.orderBy(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID);
			} else {
				Field<Object> lastPlayed = 
						roomFactory.select(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_BETTIME.max())
				          .from(Zzzlrogamebet.ZZZLROGAMEBET)
				          .where(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_GAMESEAT_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID))
				          .asField("LAST_PLAYED");
				zzgameseatRecords = roomFactory
						.select(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID, Zzuser.ZZUSER.USER_ID, Zzgameseat.ZZGAMESEAT.GAMESEAT_ID, Zzuser.ZZUSER.USER_FNAME,
								Zzuser.ZZUSER.USER_LNAME, Zzgameseat.ZZGAMESEAT.GAME_RANK, Zzuser.ZZUSER.USER_FBID, lastPlayed,
								Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_LEVEL, Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAMES_PLAYED, 
								Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAME_WON, Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS
								, Zzgameseat.ZZGAMESEAT.GAMESEAT_STATUS_UPDATE_TIME, Zzgameseat.ZZGAMESEAT.GAMESEAT_SCORE)
						.from(Zzgameseat.ZZGAMESEAT, Zzuser.ZZUSER, Zzgameusersummary.ZZGAMEUSERSUMMARY)
						.where(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.equal(gameId)
								.and(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID.equal(Zzuser.ZZUSER.USER_ID)))
								.and(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_ID.equal(Zzuser.ZZUSER.USER_ID))
						.orderBy(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID);
			}
			
			
			try {
				Result<Record> results = zzgameseatRecords.fetch();

				if (results != null) {
					logger.info("Players in the Game : " + results.size());
					
					for (Record rec : results) {
						PlayerVO playerVO = new PlayerVO();
						playerVO.setUserId(rec.getValueAsString(Zzuser.ZZUSER.USER_ID));
						playerVO.setSeatId(rec.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID));
						playerVO.setDisplayName(GameUtil.getDisplayName(rec.getValueAsString(Zzuser.ZZUSER.USER_FNAME), rec.getValueAsString(Zzuser.ZZUSER.USER_LNAME)));
						playerVO.setFullName(rec.getValueAsString(Zzuser.ZZUSER.USER_FNAME) + " " + rec.getValueAsString(Zzuser.ZZUSER.USER_LNAME));
						playerVO.setFacebookId(rec.getValueAsString(Zzuser.ZZUSER.USER_FBID));
						playerVO.setScore(rec.getValueAsInteger(Zzgameseat.ZZGAMESEAT.GAMESEAT_SCORE));
						playerVO.setRank(rec.getValueAsInteger(Zzgameseat.ZZGAMESEAT.GAME_RANK));
						playerVO.setLevel(rec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_LEVEL));
						playerVO.setLastPlayed(GameUtil.getTimeElapsed(rec.getValueAsTimestamp("LAST_PLAYED")));
						playerVO.setLastPlayedTime(rec.getValueAsTimestamp("LAST_PLAYED"));
						if(rec.getValueAsTimestamp(Zzgameseat.ZZGAMESEAT.GAMESEAT_STATUS_UPDATE_TIME)!=null) {
							playerVO.setGameSeatStatusUpdateTime(rec.getValueAsTimestamp(Zzgameseat.ZZGAMESEAT.GAMESEAT_STATUS_UPDATE_TIME).getTime());
						} else {
							playerVO.setGameSeatStatusUpdateTime(null);
						}
						//playerVO.setTotalGamesPlayed(rec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAMES_PLAYED));
						//playerVO.setGamesWon(rec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAME_WON));
						if(!flag_gameover) {
							playerVO.setCurrentRoundDisp(GameUtil.getRoundName(rec.getValueAsString(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ROUNDNAME)));
						} else {
							playerVO.setCurrentRoundDisp(GameUtil.getRoundName(null));
						}
						
						if(r_ctx.getServer().isAccountOnline(rec.getValueAsString(Zzuser.ZZUSER.USER_ID))) {
							logger.info(rec.getValueAsString(Zzuser.ZZUSER.USER_ID) + "  is online");
							playerVO.setIsOnline(1);//TODO: To create constants for Online/Offline Flag
						} else {
							logger.info(rec.getValueAsString(Zzuser.ZZUSER.USER_ID) + "  is offline");
							playerVO.setIsOnline(0);
						}
						
						if(!flag_gameover) {
							if (rec.getValueAsInteger("BETS_PLACED") > 0) {
								playerVO.setCurrentRoundPlayed(PlayerVO.CURRENT_ROUND_PLAYED);
							} else {
								try {
									logger.info("Looking for client with user id = " + rec.getValueAsString(Zzuser.ZZUSER.USER_ID)); 
									Client userClient = r_ctx.getServer().getClientByUserID(rec.getValueAsString(Zzuser.ZZUSER.USER_ID));
									logger.info("CLient found with client id : " + userClient.getClientID() + " and rooms : " + userClient.getRoomList().size());
									if (userClient.getRoomList().contains(r_ctx.getRoom().getSimpleID())) {
										playerVO.setCurrentRoundPlayed(PlayerVO.CURRENT_ROUND_PLAYING);
									} else {
										playerVO.setCurrentRoundPlayed(PlayerVO.CURRENT_ROUND_NOT_PLAYED);
									}
								} catch (ClientNotFoundException e) {
									playerVO.setCurrentRoundPlayed(PlayerVO.CURRENT_ROUND_NOT_PLAYED);
								}
							}
						} else {
							//Game is over
							playerVO.setCurrentRoundPlayed(PlayerVO.CURRENT_ROUND_PLAYED);
						}
						
						playerVO.setGameSeatStatus(rec.getValueAsString(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS));
						
						logger.info("Updating Player VO for Game : " +  playerVO.getSeatId() + " - " + playerVO.toUDO().toString());
						players.add(playerVO);
					}
				}
			} catch (DataAccessException e) {
				logger.error("Exception in ZalerioGameBoard:loadActiveTiles() :" + e);
			}
			
			return players;
		}
}
