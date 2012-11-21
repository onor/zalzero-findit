package net.user1.union.zz.zalerioGame;

import net.user1.union.zz.baseGame.lib.vo.IRoomVO;

public interface IZalerioGameRoomVO extends IRoomVO{
	public static final String ROOM_TOTALROUNDS = "TR";
	public static final String ROOM_CURRENTROUND = "CR";
	public static final String ROOM_ALLROUNDS = "AR";
	public static final String ROOM_ROUND_ENDTIME = "RE";
	public static final String ROOM_ROUND_ENDTIMEINMS = "EM";
	public static final String ROOM_ROUND_NOOFBETS = "NB";
	
	public static final String ROOM_BOARDVARS = "BV";
	public static final String ROOM_BOARD_XY = "XY";
	public static final String ROOM_TOTALFIGS = "TF";
	public static final String ROOM_FIGUREDETAIL = "FD";
	public static final String FINAL_ROUND_COMPLETE = "FR";
	public static final String ZLRO_ROOM_SCORE = "ZS";
	
	public static final String ROOM_ROUNDS_PLAYER_PLAYED = "PP";//TODO: Not used anymore
	public static final String ROOM_ALL_PLAYERS = "AP";
}
