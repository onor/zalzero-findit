package net.user1.union.zz.zalerioGame.events;

import net.user1.union.zz.baseGame.event.BaseGameRoomEvent;

public class ZalerioGameRoomEvent extends BaseGameRoomEvent{
	public static final String CMD_ZALERIOGAME_PLACEBETS = "PB";
	public static final String CMD_ZALERIOGAME_ORIGINALFIGS = "OF";
	public static final String CMD_PREVIOUSBETS = "PT";
	public static final String CMD_RESIGN_FROM_GAME = "RG";
	public static final String CMD_CLOSE_INVITATION_GAME = "CI";
	
	public static final String RES_ZALERIOGAME_CHANGEBETS = "CB";
	public static final String RES_ZALERIOGAME_ORIGINALFIGS = "OF";
	public static final String RES_PREVIOUSBETS = "PT";
	public static final String RES_ZALERIOGAME_FAILEDPLACEBETS = "PB";
}
