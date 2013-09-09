package net.user1.union.zz.baseGame.lib.vo;

import org.jooq.Record;

public interface IRoomVO {

	public static final String ROOM_GAMEINST_ID = "GI"; 
	public static final String ROOM_USERCOUNT = "UC";
	public static final String ROOM_USERTOTAL = "UT";//Seats purchased
	public static final String ROOM_NAME = "RN";
	public static final String ROOM_STARTTIME = "ST";
	public static final String ROOM_ENDTIME = "ET";
	public static final String ROOM_TTL = "TL";
	public static final String ROOM_SIZE = "RS";//Total Seats available for the auction
	public static final String ROOM_MRP = "RM";
	public static final String ROOM_IMAGE = "RI";
	public static final String ROOM_PRIZE_NAME = "RP";
	public static final String ROOM_PRIZE_ID = "PI";
	public static final String ROOM_TYPE = "RT";
	public static final String ROOM_PRIZE_CATEGORY = "RC";
	public static final String ROOM_GAME_ENDS = "GE";
	public static final String ROOM_WINNER_ID = "WI";
	public static final String ROOM_GAMEDURATION = "GD";
	public static final String ROOM_TOTALSEATS = "TS";
	

	public abstract boolean isFlag_userCount();

	public abstract boolean isFlag_userTotal();

	public abstract int getUserCount();

	public abstract void setUserCount(int userCount);

	public abstract long getUserTotal();

	public abstract void setUserTotal(long userTotal);

	public abstract int getRoomSFId();

	public abstract void setRoomSFId(int roomSFId);

	public abstract String getRoomName();

	public abstract void setRoomName(String roomName);

	public abstract String getRoomPrizeId();

	public abstract void setRoomPrizeId(String roomPrizeId);
	
	public abstract String getRoomPrizeName();
	
	public abstract void setRoomPrizeName(String roomPrizeName);

	public abstract String getRoomType();

	public abstract void setRoomType(String roomType);

	public abstract String getRoomImage();

	public abstract void setRoomImage(String roomImage);

	public abstract double getRoomMrp();

	public abstract void setRoomMrp(double roomMrp);

	public abstract void setRoomStartTime(String roomStartTime);

	public abstract void setRoomEndTime(String roomEndTime);

	public abstract String getRoomDbId();

	public abstract void setRoomDbId(String roomDbId);

	public abstract int getRoomSize();

	public abstract void setRoomSize(int roomSize);

	public abstract long getRoomTTL();

	public abstract void setRoomTTL(long roomTTL);

	public abstract void loadFromDataRow(Record row);

	public abstract String toString();

	void updateRoomVars();

	void setRoomVars();

	void refreshRoomVars();

}