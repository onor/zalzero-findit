package net.user1.union.zz.zalerioGame;

import net.user1.union.api.Client;
import net.user1.union.zz.baseGame.BaseGameServer;

public class ZalerioGameServer extends BaseGameServer{
private static final Class<?> GAME_ROOM_CLASS = ZalerioGameRoom.class;
	
	protected boolean joinTheRoom(Client client, String roomId){
		return joinTheRoom(client,roomId,GAME_ROOM_CLASS);
	}
}
