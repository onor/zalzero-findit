package net.user1.union.zz.baseGame.lib.vo;

import net.user1.union.api.Client;

import org.jooq.Record;

public interface IUserVO {

	public static final String USER_NAME = "UN";
	public static final String USER_LOGIN_ID = "UI";
	public static final String USER_PARTY_ID = "UP";
	public static final String USER_MOBILE = "UM";
	public static final String USER_DISPLAY_NAME = "UD";
	public static final String USER_FACEBOOK_ID = "UF";
	public static final String USER_SEAT_ID = "UR";
	public static final String USER_INFO = "UINFO";

	public abstract String toString();

	public abstract String getUserUnionId();

	public abstract void setUserUnionId(String userUnionId);

	public abstract String getUserPartyId();
	
	public abstract String getUserLoginId();
	
	public abstract void setUserLoginId(String userLoginId);
	
	public abstract void setUserPartyId(String userPartyId);
	
	public String getUserFacebookId();
	
	public abstract String getUserSeatId();

	public abstract void setUserSeatId(String string);

	public void updateUserVars();
	
	public boolean isUserVOChanged();

	void setUserVars();

	void loadClientDataFromDBRecord(Record row);
	
	public void setUnionClient(Client client);

}