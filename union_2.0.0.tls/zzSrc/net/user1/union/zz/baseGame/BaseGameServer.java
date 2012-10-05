package net.user1.union.zz.baseGame;

import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.user1.union.api.Client;
import net.user1.union.api.Message;
import net.user1.union.api.Module;
import net.user1.union.api.Room;
import net.user1.union.api.Status;
import net.user1.union.core.attribute.Attribute;
import net.user1.union.core.context.ModuleContext;
import net.user1.union.core.def.AttributeDef;
import net.user1.union.core.def.ModuleDef;
import net.user1.union.core.def.RoomDef;
import net.user1.union.core.event.ClientEvent;
import net.user1.union.core.event.RoomEvent;
import net.user1.union.core.exception.AttributeException;
import net.user1.union.core.exception.ClientNotFoundException;
import net.user1.union.core.exception.CreateRoomException;
import net.user1.union.core.exception.RoomAlreadyExistsException;
import net.user1.union.core.exception.RoomNotFoundException;
import net.user1.union.zz.baseGame.event.BaseGameServerEvent;
import net.user1.union.zz.baseGame.lib.vo.IRoomVO;
import net.user1.union.zz.baseGame.lib.vo.IUserVO;
import net.user1.union.zz.common.ClientHelper;
import net.user1.union.zz.common.DBCon;
import net.user1.union.zz.common.DbConstants;
import net.user1.union.zz.common.UnionDataObject;
import net.user1.union.zz.common.model.tables.Zzgame;
import net.user1.union.zz.common.model.tables.Zzgameinst;
import net.user1.union.zz.common.model.tables.Zzgameseat;
import net.user1.union.zz.common.model.tables.Zzgameusersummary;
import net.user1.union.zz.common.model.tables.Zzproduct;
import net.user1.union.zz.common.model.tables.Zzuser;
import net.user1.union.zz.common.model.tables.Zzzlrogamebet;
import net.user1.union.zz.common.model.tables.Zzzlrogameround;
import net.user1.union.zz.common.model.tables.records.ZzgameseatRecord;
import net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord;
import net.user1.union.zz.zalerioGame.ZalerioGameRoom;
import net.user1.union.zz.zalerioGame.ZalerioGameRoom.BetStatus;
import net.user1.union.zz.zalerioGame.lib.vo.GameVO;
import net.user1.union.zz.zalerioGame.lib.vo.PlayerVO;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.jooq.Field;
import org.jooq.JoinType;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Select;
import org.jooq.SelectQuery;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.Factory;

public class BaseGameServer implements Module{
	
	public static final String EXTERNAL_COMMAND = "C";
	
	protected static Class<?> GAME_ROOM_CLASS = BaseGameRoom.class;
	
	protected ModuleContext s_ctx;
	private static Logger logger = Logger.getLogger(BaseGameServer.class);
	
	public boolean init(ModuleContext ctx){
		logger.info("BaseGameServer:init() method called!");
		this.s_ctx = ctx;
		return true;
	}
	
	public void REQ(Message msg, Client client){
		String CMD = msg.getArg(BaseGameServer.EXTERNAL_COMMAND);
		logger.info("Message recieved in BaseGameServer : cmd:" + CMD + " for client:" + client.getUserID());
		
		if(CMD.equalsIgnoreCase(BaseGameServerEvent.CMD_CLIENT_LOGIN_REQ)){
			try {
				String userId = msg.getArg(BaseGameServerEvent.PARAMS_USER_ID);
				String gameId = msg.getArg(BaseGameServerEvent.PARAMS_GAMEINSTANCE_ID);
				logger.info("Login Requested : UserId:" + userId + ",gameInstId:" + gameId);
				Record authenticatedRecord = null;
				
				if(gameId.equals("0")) {
					authenticatedRecord = checkUserAuthentication(userId);
					if(authenticatedRecord != null){
						updateClient(client, authenticatedRecord);
					}
				} else {
					authenticatedRecord = checkUserAuthentication(userId,gameId);
					
					if(authenticatedRecord != null){
						if((client = updateClient(client, authenticatedRecord, gameId)) != null){
							joinTheRoom(client, gameId );
						}else{
							logger.error("Client cannot be updated in REQ() " );
							throw new Exception("Client cannot be updated in REQ() ");
						}
					}
					
					//logger.info("Message to update all game information for the client");
					//updateClientGameInfo(client, client.getAttribute(IUserVO.USER_LOGIN_ID).nullSafeGetValue());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(CMD.equalsIgnoreCase(BaseGameServerEvent.CMD_CLIENT_CHANGE_GAME)){
			try {
				String userId = msg.getArg(BaseGameServerEvent.PARAMS_USER_ID);
				String gameId = msg.getArg(BaseGameServerEvent.PARAMS_GAMEINSTANCE_ID);
				logger.info("Game Change Requested For : UserId:" + userId + ",gameInstId:" + gameId);
				Record authenticatedRecord = null;
				authenticatedRecord = checkUserAuthentication(userId,gameId);
				
				if(authenticatedRecord != null){
					if((client = updateClientForNewGame(client, authenticatedRecord, gameId)) != null){
						joinTheRoom(client, gameId );
					}else{
						logger.error("Client cannot be updated in REQ() " );
						throw new Exception("Client cannot be updated in REQ() ");
					}
				}
				
			} catch (Exception e) {
				logger.error("Client request for Change Game", e);
			}
		} else if (CMD.equalsIgnoreCase(BaseGameServerEvent.CMD_CLIENT_ALL_GAME_DATA)) {
			logger.info("Message to update all game information for the client");
			try {
				String userId = msg.getArg(BaseGameServerEvent.PARAMS_USER_LOGIN_ID);
				ClientHelper.updateClientGameInfo(s_ctx.getServer(), client, userId);
			} catch (Exception e) {
				logger.error("Issue with ", e);
			}
		} else if (CMD.equalsIgnoreCase(BaseGameServerEvent.CMD_DECLINE_FROM_GAME)) {
			logger.info("Client requested to decline the game");
			try {
				String gameSeatId = msg.getArg(BaseGameServerEvent.PARAMS_GAME_SEAT_ID);
				boolean result = declineGame(gameSeatId);
				
				if(result) {
					//Send Success Message to Client
					client.sendMessage(BaseGameServerEvent.CMD_DECLINE_FROM_GAME, "1");
				} else {
					//Send Failure Message to Client
					client.sendMessage(BaseGameServerEvent.CMD_DECLINE_FROM_GAME, "0");
				}
				
				// Updating Right Hud Information
				updateAllClientsInfoUsingGameSeat(client, gameSeatId);
				
			} catch (Exception e) {
				logger.error("Client requested to decline game", e);
			}
		}
	}
	
	/**
	 * This method will update the information for all the clients that belong
	 * to the same game as that of the supplied gameseat
	 * 
	 * @param gameSeatId
	 */
	private void updateAllClientsInfoUsingGameSeat(Client client, String gameSeatId) {
		//TODO: This can be optimized to send only delta information
		logger.info("Going to update information for all clients related to the current");
		try {
			Factory roomFactoryWrite = DBCon.getFactory();

			// Get all the players for the game for this seat id and then update
			// each one of them
			Select<Record> zzUsers = roomFactoryWrite
					.select(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID, Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID)
					.from(Zzgameseat.ZZGAMESEAT)
					.where(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.in(roomFactoryWrite.select(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID)
							.from(Zzgameseat.ZZGAMESEAT).where(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID.equal(gameSeatId))));

			Result<Record> results = zzUsers.fetch();
			if (results != null) {
				String gameId = null;
				for (Record result : results) {
					String userId = result.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID);
					if(gameId == null) {
						gameId = result.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID);
					}
					if (s_ctx.getServer().isAccountOnline(userId)) {
						try {
							Client roomClient = s_ctx.getServer().getClientByUserID(userId);
							logger.info("Updating Information for : " + userId);
							ClientHelper.updateClientGameInfo(s_ctx.getServer(), roomClient, userId);
						} catch (ClientNotFoundException e) {
							// Not a serious error
							logger.debug("Client not found");
						}
					}

				}
				//TODO: Known issue where AP Info for that room is not updated in real time
				Room room =  s_ctx.getServer().getRoom(gameId);
				ZZMessage msg = new ZZMessage();
				msg.setArg(BaseGameServer.EXTERNAL_COMMAND, BaseGameServerEvent.CMD_DECLINE_FROM_GAME);
				room.dispatchEvent(RoomEvent.MODULE_MESSAGE, new RoomEvent(room, client, msg));
			}
		} catch (Exception e) {
			logger.error("Error in updateAllClientsInfoUsingGameSeat : ", e);
		}
	}
	
	private class ZZMessage implements Message {
		
		private Map<String, String> msg = new HashMap<String, String>();
		
		public void setArg(String key, String val) {
			msg.put(key, val);
		}

		@Override
		public String getArg(String arg0) {
			return msg.get(arg0);
		}

		@Override
		public Map getArgs() {
			return msg;
		}

		@Override
		public String getMessageName() {
			return "ZZMessage";
		}
		
	}
	
	/**
	 * Method to decline game based on gameSeatId
	 * The status is set to decline
	 * @param gameSeatId
	 * @return
	 */
	private boolean declineGame(String gameSeatId) {
		
		boolean flag_dbModified = false;

		try {
			Factory roomFactoryWrite = DBCon.getFactory();
			Savepoint updateRankSavePoint = roomFactoryWrite.getConnection().setSavepoint("DECLINE_GAME");
			
			// Fetch the data already saved in the db
			ZzgameseatRecord zzgameseatRecord = roomFactoryWrite.selectFrom(Zzgameseat.ZZGAMESEAT)
					.where(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID.equal(gameSeatId))
					.fetchOne();
			
			if (zzgameseatRecord != null && !zzgameseatRecord.getZzgameseatStatus().equals(DbConstants.GAME_SEAT_STATUS_DECLINED)) {
				zzgameseatRecord.setZzgameseatStatus(DbConstants.GAME_SEAT_STATUS_DECLINED);
				zzgameseatRecord.setGameseatStatusUpdateTime(new Timestamp(System.currentTimeMillis()));
				zzgameseatRecord.store();
				flag_dbModified = true;
			}

			if (flag_dbModified) {
				roomFactoryWrite.getConnection().commit();
			} else {
				roomFactoryWrite.getConnection().rollback(updateRankSavePoint);
			}
			
		} catch (Exception e) {
			logger.error("Error in declineGame : " + e);
		}
		
		logger.info("Updated database to decline game for seat id " + gameSeatId);
		return flag_dbModified;
	}
	
	protected Client updateClient(Client client,Record clientRec,String gameId){
		boolean clientUpdated = false;
		String userLoginId = clientRec.getValueAsString(Zzuser.ZZUSER.USER_ID);
		//String userDisplayName = clientRec.getValueAsString(Zzuser.ZZUSER.USER_NAME);
		String firstName = clientRec.getValueAsString(Zzuser.ZZUSER.USER_FNAME);
		String lastName = clientRec.getValueAsString(Zzuser.ZZUSER.USER_LNAME);
		String userSeatId = clientRec.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID);
		String userFacebookId = ( clientRec.getValueAsString(Zzuser.ZZUSER.USER_FBID) != null 
    			 && clientRec.getValueAsString(Zzuser.ZZUSER.USER_FBID).length()>0 ) ? clientRec.getValueAsString(Zzuser.ZZUSER.USER_FBID) : null ;
    	String userPartyId = clientRec.getValueAsString(Zzuser.ZZUSER.USER_ID);
		if(userLoginId != null && userLoginId.length() > 0){
			try{
				boolean accountExist = false;
				if(s_ctx.getServer().containsAccount(userLoginId)){
					accountExist = true;
				}else{
					if (Status.SUCCESS.equals(s_ctx.getServer().createAccount(userLoginId, ""))){
						accountExist = true;
					}
				}
				if(accountExist){
					if (s_ctx.getServer().isAccountOnline(userLoginId)) {
						// Logging off the client who is already logged in, the
						// client can play through one session only
						Client otherClient = s_ctx.getServer().getClientByUserID(userLoginId);
						if (otherClient != null && otherClient.isLoggedIn()) {
							otherClient.logoff("");
						}
					}
					client.login(userLoginId, "");
					clientUpdated = true;
				}
				
				if(client.getAttribute(IUserVO.USER_LOGIN_ID) == null){
					client.setAttribute(IUserVO.USER_LOGIN_ID, userLoginId, Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED | Attribute.FLAG_IMMUTABLE);
				}
				
				client.setAttribute(IUserVO.USER_DISPLAY_NAME, GameUtil.getDisplayName(firstName, lastName), Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED
						| Attribute.FLAG_IMMUTABLE);
				
				if(userFacebookId != null && userFacebookId.length() > 0){
					if(client.getAttribute(IUserVO.USER_FACEBOOK_ID) == null){
						client.setAttribute(IUserVO.USER_FACEBOOK_ID, userFacebookId, Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED | Attribute.FLAG_IMMUTABLE);
					}
				}
				if(userPartyId != null && userPartyId.length() > 0){
					if(client.getAttribute(IUserVO.USER_PARTY_ID) == null){
						client.setAttribute(IUserVO.USER_PARTY_ID, userPartyId, Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED | Attribute.FLAG_IMMUTABLE);
					}
				}
				if(userSeatId != null && userSeatId.length() > 0){
					// Transient Data
					client.setAttribute(IUserVO.USER_SEAT_ID, userSeatId, Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED);
				}
				
				PlayerVO playerVO = new PlayerVO();
				playerVO.setUserId(clientRec.getValueAsString(Zzuser.ZZUSER.USER_ID));
				playerVO.setSeatId(clientRec.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID));
				playerVO.setDisplayName(GameUtil.getDisplayName(clientRec.getValueAsString(Zzuser.ZZUSER.USER_FNAME), clientRec.getValueAsString(Zzuser.ZZUSER.USER_LNAME)));
				playerVO.setFullName(clientRec.getValueAsString(Zzuser.ZZUSER.USER_FNAME) + " " + clientRec.getValueAsString(Zzuser.ZZUSER.USER_LNAME));
				playerVO.setFacebookId(clientRec.getValueAsString(Zzuser.ZZUSER.USER_FBID));
				playerVO.setLevel(clientRec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_LEVEL));
				playerVO.setTotalGamesPlayed(clientRec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAMES_PLAYED));
				playerVO.setGamesWon(clientRec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAME_WON));
				playerVO.setJoinedDate(clientRec.getValueAsTimestamp(Zzuser.ZZUSER.CREATE_TIME).getTime());
				playerVO.setGameSeatStatus(clientRec.getValueAsString(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS));
				
				logger.info("Client added with data : " + playerVO.toUDO().toString());
				// Transient Data
				client.setAttribute(IUserVO.USER_INFO, playerVO.toUDO().toString(), Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED);
				
				//This is required to clean up client specific information
				client.addEventListener(ClientEvent.SHUTDOWN, this, "onClientShutdown");
				
				//ClientHelper.updateClientGameInfo(s_ctx.getServer(), client, userLoginId);
				
			}catch(Exception e){
				logger.error("Error occoured in updateClient() : " + e);
				e.printStackTrace();
			}
		}
		if(clientUpdated){
			return client;
		}else{
			return null;
		}
	}
	
	protected Client updateClient(Client client,Record clientRec){
		boolean clientUpdated = false;
		String userLoginId = clientRec.getValueAsString(Zzuser.ZZUSER.USER_ID);
		//String userDisplayName = clientRec.getValueAsString(Zzuser.ZZUSER.USER_NAME);
		String firstName = clientRec.getValueAsString(Zzuser.ZZUSER.USER_FNAME);
		String lastName = clientRec.getValueAsString(Zzuser.ZZUSER.USER_LNAME);
		String userFacebookId = ( clientRec.getValueAsString(Zzuser.ZZUSER.USER_FBID) != null 
    			 && clientRec.getValueAsString(Zzuser.ZZUSER.USER_FBID).length()>0 ) ? clientRec.getValueAsString(Zzuser.ZZUSER.USER_FBID) : null ;
    	String userPartyId = clientRec.getValueAsString(Zzuser.ZZUSER.USER_ID);
		if(userLoginId != null && userLoginId.length() > 0){
			try{
				boolean accountExist = false;
				if(s_ctx.getServer().containsAccount(userLoginId)){
					accountExist = true;
				}else{
					if (Status.SUCCESS.equals(s_ctx.getServer().createAccount(userLoginId, ""))){
						accountExist = true;
					}
				}
				if(accountExist){
					if (s_ctx.getServer().isAccountOnline(userLoginId)) {
						// Logging off the client who is already logged in, the
						// client can play through one session only
						Client otherClient = s_ctx.getServer().getClientByUserID(userLoginId);
						if (otherClient != null && otherClient.isLoggedIn()) {
							otherClient.logoff("");
						}
					}
					client.login(userLoginId, "");
					clientUpdated = true;
				}
				
				if(client.getAttribute(IUserVO.USER_LOGIN_ID) == null){
					client.setAttribute(IUserVO.USER_LOGIN_ID, userLoginId, Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED | Attribute.FLAG_IMMUTABLE);
				}
				
				client.setAttribute(IUserVO.USER_DISPLAY_NAME, GameUtil.getDisplayName(firstName, lastName), Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED
						| Attribute.FLAG_IMMUTABLE);
				
				if(userFacebookId != null && userFacebookId.length() > 0){
					if(client.getAttribute(IUserVO.USER_FACEBOOK_ID) == null){
						client.setAttribute(IUserVO.USER_FACEBOOK_ID, userFacebookId, Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED | Attribute.FLAG_IMMUTABLE);
					}
				}
				if(userPartyId != null && userPartyId.length() > 0){
					if(client.getAttribute(IUserVO.USER_PARTY_ID) == null){
						client.setAttribute(IUserVO.USER_PARTY_ID, userPartyId, Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED | Attribute.FLAG_IMMUTABLE);
					}
				}
				
				PlayerVO playerVO = new PlayerVO();
				playerVO.setUserId(clientRec.getValueAsString(Zzuser.ZZUSER.USER_ID));
				playerVO.setDisplayName(GameUtil.getDisplayName(clientRec.getValueAsString(Zzuser.ZZUSER.USER_FNAME), clientRec.getValueAsString(Zzuser.ZZUSER.USER_LNAME)));
				playerVO.setFullName(clientRec.getValueAsString(Zzuser.ZZUSER.USER_FNAME) + " " + clientRec.getValueAsString(Zzuser.ZZUSER.USER_LNAME));
				playerVO.setFacebookId(clientRec.getValueAsString(Zzuser.ZZUSER.USER_FBID));
				playerVO.setLevel(clientRec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_LEVEL));
				playerVO.setTotalGamesPlayed(clientRec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAMES_PLAYED));
				playerVO.setGamesWon(clientRec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAME_WON));
				playerVO.setJoinedDate(clientRec.getValueAsTimestamp(Zzuser.ZZUSER.CREATE_TIME).getTime());
				
				logger.info("Client added with data : " + playerVO.toUDO().toString());
				// Transient Data
				client.setAttribute(IUserVO.USER_INFO, playerVO.toUDO().toString(), Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED);
				
				//This is required to clean up client specific information
				client.addEventListener(ClientEvent.SHUTDOWN, this, "onClientShutdown");
				
				ClientHelper.updateClientGameInfo(s_ctx.getServer(), client, userLoginId);
				
			}catch(Exception e){
				logger.error("Error occoured in updateClient() : " + e);
				e.printStackTrace();
			}
		}
		if(clientUpdated){
			return client;
		}else{
			return null;
		}
	}	
	
	public void onClientShutdown(ClientEvent evt){
		Client client = evt.getClient();
		//Clean up Cache
		if(client != null) {
			if(ClientHelper.clientCacheForGames.containsKey(client.getUserID())) {
				ClientHelper.clientCacheForGames.remove(client.getUserID());
			}
		}
	}
	
	
	/**
	 * This method is used to update the delta information when the game is
	 * changed. It also removes the client from any other room
	 * 
	 * @param client
	 * @param clientRec
	 * @param gameId
	 * @return
	 */
	protected Client updateClientForNewGame(Client client, Record clientRec, String gameId) {
		boolean clientUpdated = false;
		
		// Removing client from other rooms, this is required other wise those
		// rooms shall always be alive as long as the client is alive
		Set<String> rooms = client.getRoomList();
		for (Iterator<String> itr = rooms.iterator(); itr.hasNext();) {
			String roomId = itr.next();
			try {
				client.leaveRoom(roomId);
			} catch (RoomNotFoundException e) {
				logger.error("Error occoured whule removing client from room", e);
			}
		}
		
		// This part may not be needed if the JS ensures that the game seat id
		// can be removed from Client Info
		String userLoginId = clientRec.getValueAsString(Zzuser.ZZUSER.USER_ID);
		String userSeatId = clientRec.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID);
		
		if (userLoginId != null && userLoginId.length() > 0) {
			try {

				if (userSeatId != null && userSeatId.length() > 0) {
					// Transient Data
					client.setAttribute(IUserVO.USER_SEAT_ID, userSeatId, Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED);
				}

				PlayerVO playerVO = new PlayerVO();
				playerVO.setUserId(clientRec.getValueAsString(Zzuser.ZZUSER.USER_ID));
				playerVO.setSeatId(clientRec.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID));
				playerVO.setDisplayName(GameUtil.getDisplayName(clientRec.getValueAsString(Zzuser.ZZUSER.USER_FNAME),
						clientRec.getValueAsString(Zzuser.ZZUSER.USER_LNAME)));
				playerVO.setFullName(clientRec.getValueAsString(Zzuser.ZZUSER.USER_FNAME) + " " + clientRec.getValueAsString(Zzuser.ZZUSER.USER_LNAME));
				playerVO.setFacebookId(clientRec.getValueAsString(Zzuser.ZZUSER.USER_FBID));
				playerVO.setLevel(clientRec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_LEVEL));
				playerVO.setTotalGamesPlayed(clientRec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAMES_PLAYED));
				playerVO.setGamesWon(clientRec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAME_WON));
				playerVO.setJoinedDate(clientRec.getValueAsTimestamp(Zzuser.ZZUSER.CREATE_TIME).getTime());
				playerVO.setGameSeatStatus(clientRec.getValueAsString(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS));

				logger.info("Client added with data : " + playerVO.toUDO().toString());
				client.setAttribute(IUserVO.USER_INFO, playerVO.toUDO().toString(), Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED);

				//ClientHelper.updateClientGameInfo(s_ctx.getServer(), client, userLoginId);
				clientUpdated = true;

			} catch (Exception e) {
				logger.error("Error occoured in updateClient()", e);
			}
		}
		
		if (clientUpdated) {
			return client;
		} else {
			return null;
		}
	}	
	
	protected boolean joinTheRoom(Client client, String roomId){
		logger.info("Joining the room with GAMEROOMCLASS:" + GAME_ROOM_CLASS.getName());
		return joinTheRoom(client,roomId,GAME_ROOM_CLASS);
	}
	
	/**
	 * Make a new Room
	 * @param roomId the room ID
	 * @return the new room object
	 * @throws Exception 
	 */
	protected Room makeNewRoom(String roomId,Class<?> roomClass){
		Room newRoom = null;
		
		ModuleDef moduleDef = new ModuleDef();
		moduleDef.setType("class");
		moduleDef.setSource(roomClass.getName());
		
		AttributeDef attrGameInstId = new AttributeDef();
		attrGameInstId.setName(IRoomVO.ROOM_GAMEINST_ID);
		attrGameInstId.setValue(roomId);
		attrGameInstId.setFlags(Attribute.FLAG_SHARED | Attribute.FLAG_IMMUTABLE);
		
		RoomDef roomDef = new RoomDef();
		roomDef.setRoomID(roomId);
		roomDef.addAttribute(attrGameInstId);
		roomDef.addModule(moduleDef);
		
		try{
			newRoom = s_ctx.getServer().createRoom(roomDef);
		}catch(RoomAlreadyExistsException e){
			try {
				newRoom = s_ctx.getServer().getRoom(roomId);
			} catch (RoomNotFoundException e1) {
				logger.debug("Error occoured - makeNewRoom() - Room not found!");
			}
		}catch(CreateRoomException e){
			e.printStackTrace();
			logger.debug("Exception occoured : " + e);
		}
		return newRoom;
	}
	
	protected boolean joinTheRoom(Client client, String roomId,Class<?> roomClass) {
		boolean flag_joinedRoom = false;
		//IUserVO userVO=user.containsProperty("userVO")?(BaseUserVO) user.getProperty("userVO"):null;
		if(client!=null && roomId!=null){
			Room currentRoom = null;
			try {
				currentRoom = s_ctx.getServer().getRoom(roomId);
				logger.info("Room already exists");
			}catch(RoomNotFoundException e){
				logger.info("Room not found, hence creating one");
				currentRoom = makeNewRoom(roomId,roomClass);
				logger.info("New Room Created");
			}
			
			if(currentRoom != null){
				String status = "";
				try{
					logger.info("Client joining the Room");
					status = client.joinRoom(roomId,null);
					logger.info("Client joined Room = " + status);
				} catch(Exception e){
					logger.error("Error occoured in joinTheRoom() : ", e);
				}
				if(!Status.SUCCESS.equals(status)){
					logger.debug("User is not able to join the room " + client + " : " + currentRoom);
				}else{
					//Update user summary table with the last_active_game_id
					updateLastGameinstIdForUser(roomId, client.getUserID());
					flag_joinedRoom = true;
				}
			}
		}
			
		return flag_joinedRoom;
	}
	
	/**
	 * Method to update the last played game inst id for a user. This is used to
	 * show the saame game when the user is back to zalerio
	 * 
	 * @param gameInstId
	 * @param userId
	 * @return
	 */
	private boolean updateLastGameinstIdForUser(String gameInstId, String userId) {
		
		boolean flag_dbModified = false;

		try {
			Factory roomFactoryWrite = DBCon.getFactory();
			Savepoint updateSavePoint = roomFactoryWrite.getConnection().setSavepoint("DECLINE_GAME");
			
			// Fetch the data already saved in the db
			ZzgameusersummaryRecord zzgameusersummaryRecord = roomFactoryWrite.selectFrom(Zzgameusersummary.ZZGAMEUSERSUMMARY)
					.where(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_ID.equal(userId)).fetchOne();
			
			if (zzgameusersummaryRecord != null
					&& (zzgameusersummaryRecord.getLastGameinstId() == null || !zzgameusersummaryRecord.getLastGameinstId().equals(gameInstId))) {
				zzgameusersummaryRecord.setLastGameinstId(gameInstId);
				zzgameusersummaryRecord.setLastGameinstUpdateTime(new Timestamp(System.currentTimeMillis()));
				zzgameusersummaryRecord.store();
				flag_dbModified = true;
			}

			if (flag_dbModified) {
				roomFactoryWrite.getConnection().commit();
			} else {
				roomFactoryWrite.getConnection().rollback(updateSavePoint);
			}
			
		} catch (Exception e) {
			logger.error("Error in updateLastGameinstIdForUser : ", e);
		}
		
		logger.info("Updated database to set last game for user id " + userId);
		return flag_dbModified;
	}
	
	public Record checkUserAuthentication(String userEmail) {
		try{
			Factory factory = DBCon.getFactory();
			if(factory!=null){
				SelectQuery q = factory.selectQuery();
					q.addFrom(Zzuser.ZZUSER);
					q.addSelect(Zzuser.ZZUSER.getFields());
					q.addSelect(Zzgameusersummary.ZZGAMEUSERSUMMARY.getFields());
					q.addJoin(Zzgameusersummary.ZZGAMEUSERSUMMARY, JoinType.LEFT_OUTER_JOIN, Zzuser.ZZUSER.USER_ID.equal(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_ID));
					q.addConditions(Zzuser.ZZUSER.USER_EMAIL.equal(userEmail));
				Record result = q.fetchOne();
					
				/*
				Record result =  factory.select()
				    .from(UserLogin.USER_LOGIN)
				    .leftOuterJoin(OloGameSeat.OLO_GAME_SEAT)
				    .on(UserLogin.PARTY_ID.equal(OloGameSeat.GAME_PLAYER_ID))
				    .leftOuterJoin(OloGameInst.OLO_GAME_INST)
				    .on(OloGameInst.GAME_INST_ID.equal(gameInstanceId))
				    .leftOuterJoin(OloGame.OLO_GAME)
				    .on(OloGame.GAME_ID.equal(OloGameInst.GAME_ID))
				    .where(OloGameSeat.GAME_INSTANCE_ID.equal(gameInstanceId))
				    .and(UserLogin.USER_LOGIN_ID.equal(userEmail))			    
				    .groupBy(UserLogin.PARTY_ID).fetchOne();
				*/
				
				if(result!=null){
					
					return result;
				}
			}
	             
		}catch(Exception e){
			logger.error("Exception occoured at UserLoginEventHandler:checkuserAuthentication() : " + e);
			e.printStackTrace();
		}		
		return null;
	}	
	
	public Record checkUserAuthentication(String userEmail,String gameInstanceId){
		try{
			Factory factory = DBCon.getFactory();
			if(factory!=null){
				SelectQuery q = factory.selectQuery();
					q.addFrom(Zzuser.ZZUSER);
					q.addSelect(Factory.field("EXTRACT(EPOCH FROM zzgameinst.gameinst_endtime - now())",Long.class).as("GAME_INST_TTL"));
					q.addSelect(Zzuser.ZZUSER.getFields());
					q.addSelect(Zzgameinst.ZZGAMEINST.getFields());
					q.addSelect(Zzgameseat.ZZGAMESEAT.getFields());
					q.addSelect(Zzgame.ZZGAME.getFields());
					q.addSelect(Zzproduct.ZZPRODUCT.getFields());
					q.addSelect(Zzgameusersummary.ZZGAMEUSERSUMMARY.getFields());
					q.addJoin(Zzgameinst.ZZGAMEINST, JoinType.JOIN, Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(gameInstanceId));
					q.addJoin(Zzgameseat.ZZGAMESEAT, JoinType.JOIN,Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID.equal(Zzuser.ZZUSER.USER_ID).and(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.equal(Zzgameinst.ZZGAMEINST.GAMEINST_ID)));
					q.addJoin(Zzgame.ZZGAME, JoinType.JOIN,Zzgame.ZZGAME.GAME_ID.equal(Zzgameinst.ZZGAMEINST.GAMEINST_GAME_ID));
					q.addJoin(Zzproduct.ZZPRODUCT, JoinType.LEFT_OUTER_JOIN,Zzgameinst.ZZGAMEINST.GAMEINST_PRODUCT_ID.equal(Zzproduct.ZZPRODUCT.PRODUCT_ID));
					q.addJoin(Zzgameusersummary.ZZGAMEUSERSUMMARY, JoinType.LEFT_OUTER_JOIN, Zzuser.ZZUSER.USER_ID.equal(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_ID));
					q.addConditions(Zzuser.ZZUSER.USER_EMAIL.equal(userEmail));
					q.addGroupBy(Zzuser.ZZUSER.USER_ID);
					q.addGroupBy(Zzuser.ZZUSER.getFields());
					q.addGroupBy(Zzgameinst.ZZGAMEINST.getFields());
					q.addGroupBy(Zzgameseat.ZZGAMESEAT.getFields());
					q.addGroupBy(Zzgame.ZZGAME.getFields());
					q.addGroupBy(Zzproduct.ZZPRODUCT.getFields());
					q.addGroupBy(Zzgameusersummary.ZZGAMEUSERSUMMARY.getFields());
					
				Record result = q.fetchOne();
					
				/*
				Record result =  factory.select()
				    .from(UserLogin.USER_LOGIN)
				    .leftOuterJoin(OloGameSeat.OLO_GAME_SEAT)
				    .on(UserLogin.PARTY_ID.equal(OloGameSeat.GAME_PLAYER_ID))
				    .leftOuterJoin(OloGameInst.OLO_GAME_INST)
				    .on(OloGameInst.GAME_INST_ID.equal(gameInstanceId))
				    .leftOuterJoin(OloGame.OLO_GAME)
				    .on(OloGame.GAME_ID.equal(OloGameInst.GAME_ID))
				    .where(OloGameSeat.GAME_INSTANCE_ID.equal(gameInstanceId))
				    .and(UserLogin.USER_LOGIN_ID.equal(userEmail))			    
				    .groupBy(UserLogin.PARTY_ID).fetchOne();
				*/
				
				if(result!=null){
					
					return result;
				}
			}
	             
		}catch(Exception e){
			logger.error("Exception occoured at UserLoginEventHandler:checkuserAuthentication() : " + e);
			e.printStackTrace();
		}		
		return null;
	}
	
	@Override
	public void shutdown() {
		
	}
}
