package net.user1.union.zz.baseGame.event;

public class BaseGameRoomEvent {
	public static String CMD_CLIENT_CHAT_REQ = "CT";
	public static final String CMD_GAME_INSTANCE_START = "GS";
	public static final String CMD_GAME_INSTANCE_COMPLETED = "GC";
	
	public static final String CMD_GAME_INSTANCE = "GI";
	public static final String RES_GAME_INSTANCE = "GI";
	
	public static final String PARAMS_GAME_INSTANCE_ID = "GID";
	
	public static final String RES_GAME_INSTANCE_START = "GS";
	public static final String RES_GAME_VARS_UPDATED = "GU";
	public static final String RES_USER_VARS_UPDATED = "UU";
	public static final String RES_GAME_INSTANCE_COMPLETED = "GC";
	public static final String MODULE_MESSAGE = "RQ";
}
