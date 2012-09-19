package net.user1.union.zz.baseGame.lib.vo;

import net.user1.union.api.Client;
import net.user1.union.core.attribute.Attribute;
import net.user1.union.zz.common.model.tables.Zzgameseat;
import net.user1.union.zz.common.model.tables.Zzuser;

import org.apache.log4j.Logger;
import org.jooq.Record;

public class BaseUserVO implements IUserVO{

	private Logger logger = Logger.getLogger(BaseUserVO.class);

    //private boolean flag_userName = true;
    //private boolean flag_userMobile = true;
    private boolean flag_userFacebookId = true;
    
    private String userUnionId = "";
    private String userSeatId = "";
    private String userPartyId = "";
    private String userLoginId = "";
    private String userFacebookId = "";
    private String userDisplayName = "";
    
    //private String userName = "";
    //private String userMobile = "";
    private boolean userValChanged = true;
    protected Client client = null;
    
    public BaseUserVO(Client client){
    	super();
    	this.client = client;
    	this.setUserUnionId(client.getClientID());
    }
    
    public BaseUserVO(){
    	super();
    }
    
    public void setUnionClient(Client client){
    	if(client != null && client.isLoggedIn() && client.getUserID().equalsIgnoreCase(this.userLoginId)){
    		this.client = client;
        	this.setUserUnionId(client.getClientID());
    	}
    }
    
    protected void setPublicUserVar(String key,Object val){
    	try{
    		if(client != null){
    			client.setAttribute(key,val,Attribute.SCOPE_GLOBAL,Attribute.FLAG_SHARED);
    		}
    	}catch(Exception e){
    		logger.debug("Exception occoured in BaseUserVO:setPublicUserVar() : " + e);
    		e.printStackTrace();
    	}
    }
    
    protected void setPublicPersistentUserVar(String key,Object val){
    	try{
    		if(client != null){
    			client.setAttribute(key,val, Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED | Attribute.FLAG_IMMUTABLE);
    		}
    	}catch(Exception e){
    		logger.debug("Exception occoured in BaseUserVO:setPublicPersistentUserVar() : " + e);
    		e.printStackTrace();
    	}
    }
	@Override
    public String toString() {
        return "[{" 
        		/*"UserMobile:" + this.getUserMobile() 
        		+ "},{User Name:" + this.getUserName()*/
                + "User SeatId:" + this.getUserSeatId() 
                + "},{User LoginId:" + this.getUserLoginId()
                + "},{User PartyId:" + this.getUserPartyId()
                + "},{User UnionId:" + this.getUserUnionId() 
                + "},{User Seat Id:" + this.getUserSeatId() 
                + "},{User Display Name:" + this.getUserDisplayName()
                + "}]";
    }
    
    /*@Override
	public boolean isFlag_userName() {
		return flag_userName;
	}

	@Override
	public void setFlag_userName(boolean flag_userName) {
		this.flag_userName = flag_userName;
	}

	@Override
	public boolean isFlag_userMobile() {
		return flag_userMobile;
	}

	@Override
	public void setFlag_userMobile(boolean flag_userMobile) {
		this.flag_userMobile = flag_userMobile;
	}

	@Override
	public String getUserMobile() {
        return userMobile;
    }

    @Override
	public void setUserMobile(String userMobile) {
    	if (this.userMobile != userMobile) {
            this.setUserValChanged(true);
            flag_userMobile = true;
        }
        this.userMobile = userMobile;
    }

    @Override
	public String getUserName() {
        return userName;
    }

    @Override
	public void setUserName(String userName) {
    	if (this.userName != userName) {
            this.setUserValChanged(true);
            flag_userName = true;
        }
        this.userName = userName;
    }
    */

    @Override
	public String getUserSeatId() {
        return userSeatId;
    }

    @Override
	public void setUserSeatId(String string) {
        this.userSeatId = string;
    }

    @Override
	public void updateUserVars() {
        if (this.isUserValChanged()) {
        	/*
            if (this.isFlag_userMobile()) {
                setPublicUserVar(IUserVO.USER_MOBILE, this.getUserMobile());
                this.setFlag_userMobile(false);
            }
            
            if (this.isFlag_userName()) {
                setPublicUserVar(IUserVO.USER_NAME, this.getUserName());
                this.setFlag_userName(false);
            }
            */
            /*
            if (this.isFlag_userFacebookId()) {
                setPublicUserVar(IUserVO.USER_FACEBOOK_ID, this.getUserFacebookId());
                this.setFlag_userFacebookId(false);
            }*/
            this.setUserValChanged(false);
        }
    }

    @Override
	public void setUserVars() {
        //setPublicUserVar(IUserVO.USER_NAME, this.getUserName());
        setPublicUserVar(IUserVO.USER_PARTY_ID, this.getUserPartyId());
        setPublicUserVar(IUserVO.USER_LOGIN_ID, this.getUserLoginId());
        setPublicUserVar(IUserVO.USER_FACEBOOK_ID, this.getUserFacebookId());
        setPublicUserVar(IUserVO.USER_SEAT_ID, this.getUserSeatId());
    }
    
    @Override
	public void loadClientDataFromDBRecord(Record row) {
    	try{
	        if (row != null) {
	        	String userLoginId = row.getValueAsString(Zzuser.ZZUSER.USER_ID); 
	        	if( userLoginId != null && userLoginId.length()>0){
	        		setUserLoginId(userLoginId);
	        	}
	        	
	        	String userPartyId = row.getValueAsString(Zzuser.ZZUSER.USER_ID); 
	        	if(userPartyId != null && userPartyId.length()>0){
	        		setUserPartyId(userPartyId);
	        	}
	        	
	        	String userSeatId = row.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID);  
	        	if( userSeatId != null && userSeatId.length()>0){
	        		setUserSeatId(userSeatId);
	        	}
	        	
	        	String userDisplayName = row.getValueAsString(Zzuser.ZZUSER.USER_NAME);
	        	if(userDisplayName != null && userDisplayName.length()>0){
	        		setUserDisplayName(userDisplayName);
	        	}
	        	
	        	String userFacebookId = ( row.getValueAsString(Zzuser.ZZUSER.USER_FBID) != null  
	        			 && row.getValueAsString(Zzuser.ZZUSER.USER_FBID).length()>0 ) ? row.getValueAsString(Zzuser.ZZUSER.USER_FBID) : null ;
	        	if(userFacebookId != null && userFacebookId.length() > 0){
	        		setUserFacebookId(userFacebookId);
	        	}
	        	/*
	        	if(row.getValueAsString(UserLogin.USER_LOGIN.DISPLAY_NAME) != null && row.getValueAsString(UserLogin.USER_LOGIN.DISPLAY_NAME).length()>0){
	        		this.setUserName(row.getValueAsString(UserLogin.USER_LOGIN.DISPLAY_NAME));
	        	}
	        	*/
	        	
	        	/*
	        	// if(row.getValueAsString(UserLogin.IS_O_AUTH_ENABLED) != null && row.getValueAsString(UserLogin.IS_O_AUTH_ENABLED).equalsIgnoreCase("Y") 
	        	if ( row.getValueAsString(UserLogin.O_AUTH_PROVIDER_ID) != null && row.getValueAsString(UserLogin.O_AUTH_PROVIDER_ID).equalsIgnoreCase("1") 
	        			&& row.getValueAsString(UserLogin.O_AUTH_ID)!= null && row.getValueAsString(UserLogin.O_AUTH_ID).length()>0){
	        		this.setUserFacebookId(row.getValueAsString(UserLogin.O_AUTH_ID));
	        	}
	        	*/
	            /*
	            this.setRoomName(row.getValueAsString(OloGame.NAME) + " - " + row.getValueAsString(OloGameInst.GAME_INST_ID));
	            if(row.getValueAsString(OloGameInst.GAME_PRIZE_ID)!=null && row.getValueAsString(OloGameInst.GAME_PRIZE_ID)!=""){
	            	this.setItemName(row.getValueAsString(OloGameInst.GAME_PRIZE_ID));
	            }
	            this.setRoomStartTime(row.getValueAsString(OloGameInst.START_TIME));
	            this.setRoomEndTime(row.getValueAsString(OloGameInst.END_TIME));
	            this.setRoomSize(row.getValueAsInteger(OloGameInst.SEAT_QUOTA));
	            this.setRoomType(row.getValueAsString(OloGameInst.GAME_INST_CAT_ID));
	            */
	        }
    	}catch(Exception e){
    		logger.debug("UserVO::loadFromDataRow() - Exception occoured: " + e.toString());
    		e.printStackTrace();
    	}

    }

	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}
	
	public String getUserDisplayName(){
		return this.userDisplayName;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getUserPartyId() {
		return userPartyId;
	}

	public void setUserPartyId(String userPartyId) {
		this.userPartyId = userPartyId;
	}

	@Override
	public String getUserUnionId() {
		return this.userUnionId;
	}

	@Override
	public void setUserUnionId(String userUnionId) {
		this.userUnionId = userUnionId;
	}

	private boolean isUserValChanged() {
		return userValChanged;
	}
	
	public boolean isUserVOChanged(){
		return userValChanged;
	}

	private void setUserValChanged(boolean userValChanged) {
		this.userValChanged = userValChanged;
	}

	public String getUserFacebookId() {
		return userFacebookId;
	}

	public void setUserFacebookId(String userFacebookId) {
		if (this.userFacebookId != userFacebookId) {
			this.setUserValChanged(true);
            flag_userFacebookId = true;
        }
		this.userFacebookId = userFacebookId;
	}

	public boolean isFlag_userFacebookId() {
		return flag_userFacebookId;
	}

	public void setFlag_userFacebookId(boolean flag_userFacebookId) {
		this.flag_userFacebookId = flag_userFacebookId;
	}

}
