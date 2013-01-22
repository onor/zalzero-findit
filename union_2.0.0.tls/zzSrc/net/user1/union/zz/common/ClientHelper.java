package net.user1.union.zz.common;

import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import net.user1.union.api.Client;
import net.user1.union.api.Room;
import net.user1.union.api.Server;
import net.user1.union.core.attribute.Attribute;
import net.user1.union.core.exception.AttributeException;
import net.user1.union.core.exception.ClientNotFoundException;
import net.user1.union.core.exception.RoomNotFoundException;
import net.user1.union.zz.baseGame.GameUtil;
import net.user1.union.zz.baseGame.lib.vo.IUserVO;
import net.user1.union.zz.common.model.tables.Zzgameinst;
import net.user1.union.zz.common.model.tables.Zzgameseat;
import net.user1.union.zz.common.model.tables.Zzgameusersummary;
import net.user1.union.zz.common.model.tables.Zzuser;
import net.user1.union.zz.common.model.tables.Zzzlrogamebet;
import net.user1.union.zz.common.model.tables.Zzzlrogameround;
import net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord;
import net.user1.union.zz.zalerioGame.events.ZalerioGameRoomEvent;
import net.user1.union.zz.zalerioGame.lib.vo.GameVO;
import net.user1.union.zz.zalerioGame.lib.vo.PlayerVO;

import org.apache.log4j.Logger;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Select;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.Factory;

/**
 * @author Snehesh
 *
 */
public class ClientHelper {
	
	private static Logger logger = Logger.getLogger(ClientHelper.class);
	public static final int MAX_GAME_DATA_LIMIT = 100;
	public static final int MAX_PAST_GAME_DATA_LIMIT = 9;
	public static final String ALL_PAST_GAMES = "APG";
	public static final String GAMES_WITH_MY_TURN = "MT";
	public static final String GAMES_WITH_THEIR_TURN = "TT";
	
	public static Map<String, Map<String, GameVO>> clientCacheForGames = new HashMap<String, Map<String,GameVO>>();
	
	/**
	 * Method to update all game info for the client
	 * @param client
	 * @param userId
	 * @return
	 */
	public static Client updateClientGameInfo(Server server, Client client, String userId){
		
		logger.info("Update Client Game Info for User Id : "+ userId);
		
		boolean clientUpdatedForActiveGames = updateActiveGameCarousel(server, client, userId);
		boolean clientUpdatedForPastGames = updatePastGameCarousel(server, client, userId);
		
		if (clientUpdatedForActiveGames || clientUpdatedForPastGames) {
			return client;
		} else {
			return null;
		}
	}
	
	/**
	 * Gets data for my game and their game carousels
	 * @param client
	 * @param userId
	 * @return
	 */
	private static boolean updateActiveGameCarousel(Server server, Client client, String userId) {
		
		// userId - zzuser.user_id
		// seatId - zzgameseat.gameseat_id
		// displayName - zzuser.user_name
		// fullName - zzuser.user_name
		// score = ?
		// rank - zzgameseat.game_rank
		// level - zzgameusersummary.user_level
		// lastPlayed = "24H Ago";
		// currentRoundDisp = "3";
		// isOnline = 1;
		// facebookId - zzuser.fb_id
		// currentRoundPlayed = 1; 

		// Fetch data for Active Games
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Factory factory = DBCon.getFactory();
		Field<Object> betsPlaced =
				factory.selectCount()
			          .from(Zzzlrogamebet.ZZZLROGAMEBET)
			          .where(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_GAMESEAT_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID).and(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ZLGAMEROUND_ID.equal(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID)))
			          .asField("BETS_PLACED");
		Field<Object> lastPlayed = 
				factory.select(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_BETTIME.max())
		          .from(Zzzlrogamebet.ZZZLROGAMEBET)
		          .where(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_GAMESEAT_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID))
		          .asField("LAST_PLAYED");
		Select<Record> zzgameseatRecords = factory
				.select(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID, Zzuser.ZZUSER.USER_ID, Zzgameseat.ZZGAMESEAT.GAMESEAT_ID, Zzuser.ZZUSER.USER_FNAME,
						Zzuser.ZZUSER.USER_LNAME, Zzgameseat.ZZGAMESEAT.GAME_RANK, Zzuser.ZZUSER.USER_FBID, Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID,
						Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ROUNDNAME, betsPlaced, lastPlayed, Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_LEVEL, 
						Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS, Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME, Zzgameseat.ZZGAMESEAT.GAMESEAT_SCORE, Zzgameinst.ZZGAMEINST.GAMEINST_STARTTIME)
				.from(Zzgameseat.ZZGAMESEAT, Zzuser.ZZUSER, Zzzlrogameround.ZZZLROGAMEROUND, Zzgameusersummary.ZZGAMEUSERSUMMARY, Zzgameinst.ZZGAMEINST)
				.where(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.in(
						factory.select(Zzgameinst.ZZGAMEINST.GAMEINST_ID)
								.from(Zzgameseat.ZZGAMESEAT, Zzgameinst.ZZGAMEINST)
								.where(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID.equal(userId)
										.and(Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID)
										.and(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS.notEqual(DbConstants.GAME_SEAT_STATUS_DECLINED)
										.and(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS.notEqual(DbConstants.GAME_SEAT_STATUS_RESIGNED)))
										/*.and(Zzgameinst.ZZGAMEINST.GAME_COMPLETE.equal(0))*/)))
										/*
						.and(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS.equal(DbConstants.GAME_SEAT_STATUS_ACCEPTED)
								.or(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS.equal(DbConstants.GAME_SEAT_STATUS_INVITED))
								.or(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS.equal(DbConstants.GAME_SEAT_STATUS_RESIGNED)))
								*/
						.and(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID.equal(Zzuser.ZZUSER.USER_ID)))
						.and(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_GAMEINST_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID))
						.and(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_TIMESTART.lessOrEqual(now))
						.and(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_TIMEEND.greaterThan(now))
						.and(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_ID.equal(Zzuser.ZZUSER.USER_ID))
						.and(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.equal(Zzgameinst.ZZGAMEINST.GAMEINST_ID))
				.orderBy(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.desc());

		List<GameVO> games = null;

		try {
			Result<Record> results = zzgameseatRecords.fetch();

			if (results != null) {
				logger.debug("Results is not null");
				String tempGameId = "";
				GameVO gameVO = null;
				games = new ArrayList<GameVO>();
				int maxGameCounter = 0;
				boolean isFirstSet = false;
				
				Map<String, GameVO> clientGames = ClientHelper.clientCacheForGames.get(client.getUserID());
				logger.info("Client Games from cache is " + clientGames);
				Map<String, GameVO> newClientGames = new HashMap<String, GameVO>();

				for (Record rec : results) {
					String gameId = rec.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID);
					logger.info("Game Id = " + gameId);
					if (!gameId.equals(tempGameId)) {
						// Game has changed
						if (++maxGameCounter > MAX_GAME_DATA_LIMIT) {
							break;
						}
						logger.debug("Adding Game Data for Game Id : " + gameId);

						// Game Changed
						gameVO = new GameVO();
						gameVO.setGameInstId(gameId);
						gameVO.setCurrentRound(GameUtil.getRoundName(rec.getValueAsString(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ROUNDNAME)));
						gameVO.setGameEndDate(rec.getValueAsTimestamp(Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME).getTime());
						gameVO.setGameStartDate(rec.getValueAsTimestamp(Zzgameinst.ZZGAMEINST.GAMEINST_STARTTIME).getTime());
						gameVO.setPlayers(new ArrayList<PlayerVO>());
						tempGameId = gameId;
						isFirstSet = false;
						if (gameVO != null) {
							// The last gameVO needs to be added to the list
							games.add(gameVO);
							newClientGames.put(gameId, gameVO);
						}
					}

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
					playerVO.setCurrentRoundDisp(GameUtil.getRoundName(rec.getValueAsString(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ROUNDNAME)));
					
					if(server.isAccountOnline(rec.getValueAsString(Zzuser.ZZUSER.USER_ID))) {
						logger.debug(rec.getValueAsString(Zzuser.ZZUSER.USER_ID) + "  is online");
						playerVO.setIsOnline(1);//TODO: To create constants for Online/Offline Flag
					} else {
						logger.debug(rec.getValueAsString(Zzuser.ZZUSER.USER_ID) + "  is offline");
						playerVO.setIsOnline(0);
					}
					
					// TODO: This needs to be checked
					if (rec.getValueAsInteger("BETS_PLACED") > 0) {
						playerVO.setCurrentRoundPlayed(PlayerVO.CURRENT_ROUND_PLAYED);
					} else {
						try {
							logger.debug("Looking for client with user id = " + rec.getValueAsString(Zzuser.ZZUSER.USER_ID)); 
							Client userClient = server.getClientByUserID(rec.getValueAsString(Zzuser.ZZUSER.USER_ID));
							logger.debug("CLient found with client id : " + userClient.getClientID() + " and rooms : " + userClient.getRoomList().size());
							if (userClient.getRoomList().contains(gameId)) {
								playerVO.setCurrentRoundPlayed(PlayerVO.CURRENT_ROUND_PLAYING);
							} else {
								playerVO.setCurrentRoundPlayed(PlayerVO.CURRENT_ROUND_NOT_PLAYED);
							}
						} catch (ClientNotFoundException e) {
							playerVO.setCurrentRoundPlayed(PlayerVO.CURRENT_ROUND_NOT_PLAYED);
						}
					}
					
					if (userId.equals(rec.getValueAsString(Zzuser.ZZUSER.USER_ID))) {
						// Checking only on the basis of current User
						if (rec.getValueAsInteger("BETS_PLACED") > 0) {
							// Current User has placed bets i.e. it is their turn
							gameVO.setGameStatus("2");
						} else {
							// Current User has not placed bets i.e. it is my turn
							gameVO.setGameStatus("1");// My Turn
						}
					}
					
					playerVO.setGameSeatStatus(rec.getValueAsString(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS));
					
					/*
					if(!isFirstSet) {
						if(gameVO.isMyTurn()) {
							// If it is my turn, then the client should be shown first
							if(playerVO.getUserId().equals(userId)) {
								gameVO.addPlayers(playerVO, true);
								isFirstSet = true;
							} else {
								gameVO.addPlayers(playerVO);
							}
						} else {
							// If it is not my turn then the user who is yet to play should be shown first
							if(!playerVO.getUserId().equals(userId) && playerVO.getCurrentRoundPlayed().equals(PlayerVO.CURRENT_ROUND_NOT_PLAYED)) {
								gameVO.addPlayers(playerVO, true);
								isFirstSet = true;
							} else if(!playerVO.getUserId().equals(userId) && playerVO.getCurrentRoundPlayed().equals(PlayerVO.CURRENT_ROUND_PLAYING)) {
								gameVO.addPlayers(playerVO, true);
								isFirstSet = true;
							} else if(!playerVO.getUserId().equals(userId)) {
								gameVO.addPlayers(playerVO, true);
								isFirstSet = true;
							} else {
								gameVO.addPlayers(playerVO);
							}
						}
					} else {
						gameVO.addPlayers(playerVO);
					}
					*/
					gameVO.addPlayers(playerVO);
					
					logger.debug("Player Info " + playerVO.toUDO());
					logger.debug("Updated for Game Id : " + tempGameId + "  for user : " + playerVO.getDisplayName());
				}
				
				if (clientGames != null) {
					logger.debug("Client Games from cache is not null");
					// Updating the Past Games
					Set<String> updatedGames = newClientGames.keySet();
					for (String gameId : clientGames.keySet()) {
						if (!updatedGames.contains(gameId)) {
							logger.debug("Found Game " + gameId + " which is not there in the current");
							
							// If the new game does not contain a game earlier
							// present then it needs to be removed from the
							// current
							// game list
							gameVO = new GameVO();
							gameVO.setGameInstId(gameId);
							gameVO.setGameStatus("0");// To be removed
							games.add(gameVO);
							
							/* Update Belt Info if required */
							updateLeftCarousel(server,gameId);
						}
					}
				}
				
				//Set updated Client Games list in Cache
				ClientHelper.clientCacheForGames.put(client.getUserID(), newClientGames);
				
			} else {
				logger.info("Results is null");
			}
		} catch (DataAccessException e) {
			logger.error("Exception in ZalerioGameBoard:loadActiveTiles()", e);
		}
		
		UnionDataObject udoMyTurn = new UnionDataObject();
		//UnionDataObject udoTheirTurn = new UnionDataObject();
		
		int counter = 1;
		if (games != null && !games.isEmpty()) {
			for (Iterator<GameVO> itr = games.iterator(); itr.hasNext();) {
				GameVO gameVO = itr.next();
				//if(gameVO.isMyTurn()) {
					udoMyTurn.append(gameVO.getGameInstId(), gameVO.toUDO());
					// Logic to push small packets of data into the right hud
					if(++counter > 5 || !itr.hasNext()) {//Either send 5 games or if no more games found
						counter = 1;
						String rightHudData = udoMyTurn.toString();
						logger.info("Right Hud Data : " + rightHudData);
						client.sendMessage(GAMES_WITH_MY_TURN, rightHudData);
						// Clearing unionDataObject
						udoMyTurn = new UnionDataObject();
					}
				//} else {
					//udoTheirTurn.append(gameVO.getGameInstId(), gameVO.toUDO());
				//}
			}
		}
		
		//Get Data From Cache and Update the String
		
		// My Turn
		//boolean clientUpdated1 = updateAttributeInClient(client, GAMES_WITH_MY_TURN, udoMyTurn);
		
		// Their Turn
		//boolean clientUpdated2 = updateAttributeInClient(client, GAMES_WITH_THEIR_TURN, udoTheirTurn);
		//client.sendMessage(GAMES_WITH_THEIR_TURN, udoTheirTurn.toString());
		
		return true;
		//return (clientUpdated1 ||  clientUpdated2);
	}

	/**
	 * Gets data for my game and their game carousels
	 * @param client
	 * @param userId
	 * @return
	 */
	private static boolean updatePastGameCarousel(Server server, Client client, String userId) {
		
		logger.info("Loading Past Game for client with user id = " + userId);

		// userId - zzuser.user_id
		// seatId - zzgameseat.gameseat_id
		// displayName - zzuser.user_name
		// fullName - zzuser.user_name
		// score = ?
		// rank - zzgameseat.game_rank
		// level - zzgameusersummary.user_level
		// lastPlayed = "24H Ago";
		// currentRoundDisp = "3";
		// isOnline = 1;
		// facebookId - zzuser.fb_id
		// currentRoundPlayed = 1; 

		// Fetch data for all Past Games
		Factory factory = DBCon.getFactory();
		Select<Record> zzgameseatRecords = factory
				.select(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID, Zzuser.ZZUSER.USER_ID, Zzgameseat.ZZGAMESEAT.GAMESEAT_ID, Zzuser.ZZUSER.USER_FNAME,
						Zzuser.ZZUSER.USER_LNAME, Zzgameseat.ZZGAMESEAT.GAME_RANK, Zzuser.ZZUSER.USER_FBID, Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_LEVEL,
						Zzgameinst.ZZGAMEINST.GAMEINST_STARTTIME, Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME, Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS, Zzgameseat.ZZGAMESEAT.GAMESEAT_SCORE)
				.from(Zzgameseat.ZZGAMESEAT, Zzuser.ZZUSER, Zzgameusersummary.ZZGAMEUSERSUMMARY, Zzgameinst.ZZGAMEINST)
				.where(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.in(
						factory.select(Zzgameinst.ZZGAMEINST.GAMEINST_ID)
								.from(Zzgameseat.ZZGAMESEAT, Zzgameinst.ZZGAMEINST)
								.where(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID.equal(userId)
										.and(Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID)
										.and(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS.notEqual(DbConstants.GAME_SEAT_STATUS_DECLINED))//Do not show Declined Games
										.and(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS.notEqual(DbConstants.GAME_SEAT_STATUS_RESIGNED))
										.and(Zzgameinst.ZZGAMEINST.ZZGAMEINST_STATUS.equal(DbConstants.GAME_INST_STATUS_COMPLETED)))))//Game is complete
						.and(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS.equal(DbConstants.GAME_SEAT_STATUS_ACCEPTED)
								.or(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS.equal(DbConstants.GAME_SEAT_STATUS_INVITED))
								.or(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS.equal(DbConstants.GAME_SEAT_STATUS_RESIGNED)))
						.and(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID.equal(Zzuser.ZZUSER.USER_ID)))
						.and(Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID))
						/*
						.and(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_GAMEINST_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID))
						.and(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_TIMESTART.lessOrEqual(now))
						.and(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_TIMEEND.greaterThan(now))
						*/
						.and(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_ID.equal(Zzuser.ZZUSER.USER_ID))
				//.orderBy(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.desc());
				.orderBy(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.cast(Integer.class).desc());

		List<GameVO> games = null;

		try {
			Result<Record> results = zzgameseatRecords.fetch();

			if (results != null) {
				logger.debug("Results is not null");
				String tempGameId = "";
				GameVO gameVO = null;
				games = new ArrayList<GameVO>();
				int maxGameCounter = 0;

				for (Record rec : results) {
					String gameId = rec.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID);
					logger.debug("Game Id = " + gameId);
					if (!gameId.equals(tempGameId)) {
						// Game has changed
						if (++maxGameCounter > MAX_PAST_GAME_DATA_LIMIT) {
							break;
						}
						logger.debug("Adding Game Data for Game Id : " + gameId);

						// Game Changed
						gameVO = new GameVO();
						gameVO.setGameInstId(gameId);
						gameVO.setGameStartDate(rec.getValueAsTimestamp(Zzgameinst.ZZGAMEINST.GAMEINST_STARTTIME).getTime());
						gameVO.setGameEndDate(rec.getValueAsTimestamp(Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME).getTime());
						gameVO.setPlayers(new ArrayList<PlayerVO>());
						tempGameId = gameId;
						if (gameVO != null) {
							// The last gameVO needs to be added to the list
							games.add(gameVO);
						}
					}

					PlayerVO playerVO = new PlayerVO();
					playerVO.setUserId(rec.getValueAsString(Zzuser.ZZUSER.USER_ID));
					playerVO.setSeatId(rec.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID));
					playerVO.setDisplayName(GameUtil.getDisplayName(rec.getValueAsString(Zzuser.ZZUSER.USER_FNAME), rec.getValueAsString(Zzuser.ZZUSER.USER_LNAME)));
					playerVO.setFullName(rec.getValueAsString(Zzuser.ZZUSER.USER_FNAME) + " " + rec.getValueAsString(Zzuser.ZZUSER.USER_LNAME));
					playerVO.setFacebookId(rec.getValueAsString(Zzuser.ZZUSER.USER_FBID));
					playerVO.setScore(rec.getValueAsInteger(Zzgameseat.ZZGAMESEAT.GAMESEAT_SCORE));
					playerVO.setRank(rec.getValueAsInteger(Zzgameseat.ZZGAMESEAT.GAME_RANK));
					playerVO.setLevel(rec.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_LEVEL));
					
					if(server.isAccountOnline(rec.getValueAsString(Zzuser.ZZUSER.USER_ID))) {
						playerVO.setIsOnline(1);//TODO: To create constants for Online/Offline Flag
					} else {
						playerVO.setIsOnline(0);
					}
					
					playerVO.setGameSeatStatus(rec.getValueAsString(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS));
					
					gameVO.addPlayers(playerVO);
					logger.debug("Player Info " + playerVO.toUDO());
					logger.debug("Updated for Game Id : " + tempGameId + "  for user : " + playerVO.getDisplayName());
				}
			} else {
				logger.info("Results is null");
			}
		} catch (DataAccessException e) {
			logger.error("Exception in ZalerioGameBoard:loadActiveTiles() :" + e);
			e.printStackTrace();
		}
		
		UnionDataObject udoPastGames = new UnionDataObject();
		
		if (games != null && !games.isEmpty()) {
			for (Iterator<GameVO> itr = games.iterator(); itr.hasNext();) {
				GameVO gameVO = itr.next();
				udoPastGames.append(gameVO.getGameInstId(), gameVO.toUDO());
			}
		}
		
		boolean clientUpdated = updateAttributeInClient(client, ALL_PAST_GAMES, udoPastGames);
		
		return clientUpdated;
	}
	
	private static boolean updateAttributeInClient(Client client, String attributeName, UnionDataObject newAttributeVal) {
		logger.info("Loading for : " + attributeName);
		boolean clientUpdated = false;
		// My Turn
		try {
			Attribute attrValInClient = client.getAttribute(attributeName);
			String attrValInClientStr = "";
			if (attrValInClient != null) {
				attrValInClientStr = attrValInClient.nullSafeGetValue();
			}
			logger.info("Value in Client : " + attrValInClientStr);

			String newAttrValStr = newAttributeVal.toString();
			logger.info("New Value: " + newAttrValStr);
			if (!newAttrValStr.equals(attrValInClientStr)) {
				client.setAttribute(attributeName, newAttrValStr, Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED | Attribute.FLAG_SERVER_ONLY);
				clientUpdated = true;
				logger.info("New Value updated");
			}
		} catch (AttributeException e) {
			logger.error("Error while updating Active Games", e);
		}
		return clientUpdated;
	}
	
	/**
	 * This method shall update zzgameusersummary.current_gameinst_id with the
	 * parameter gameid for the userId
	 * 
	 * @param gameId
	 * @param userId
	 * @return
	 */
	public static boolean updateCurrentActiveGameForUser(String currentGameId, String oldGameId, String userId) {
		logger.info("Saving Current Active Game Value for Client = " + userId + " to game id = " + currentGameId + " from old game id = " + oldGameId);
		boolean flag_dbModified = false;

		try {
			Factory roomFactoryWrite = DBCon.getFactory();

			Savepoint updateSavePoint = roomFactoryWrite.getConnection().setSavepoint("JOIN_A_ROOM");

			// Fetch the data already saved in the db
			ZzgameusersummaryRecord zzgameusersummaryRecord = roomFactoryWrite.selectFrom(Zzgameusersummary.ZZGAMEUSERSUMMARY)
					.where(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_ID.equal(userId)).fetchOne();

			if (zzgameusersummaryRecord != null) {
				String oldGameIdFromDB = zzgameusersummaryRecord.getValueAsString(Zzgameusersummary.ZZGAMEUSERSUMMARY.CURRENT_GAMEINST_ID);
				if (currentGameId == null) {
					if (oldGameIdFromDB != null && oldGameId.equals(oldGameIdFromDB)) {
						// Safe check to ensure that the current game is made
						// null only when the user quits a game that the user
						// was already in
						zzgameusersummaryRecord.setCurrentGameinstId(currentGameId);
						zzgameusersummaryRecord.store();
						flag_dbModified = true;
					}
				} else if (!currentGameId.equals(oldGameIdFromDB)) {
					// Changing only if the game id has changed
					zzgameusersummaryRecord.setCurrentGameinstId(currentGameId);
					zzgameusersummaryRecord.store();
					flag_dbModified = true;
				}
			}

			if (flag_dbModified) {
				roomFactoryWrite.getConnection().commit();
			} else {
				roomFactoryWrite.getConnection().rollback(updateSavePoint);
			}

		} catch (Exception e) {
			logger.error("Error in updateCurrentActiveGame : " + e);
		}

		logger.info("Updated Current Active Game Value for Client = " + userId + " to game id = " + currentGameId + " with status = " + flag_dbModified);
		return flag_dbModified;
	}
	
	/**
	 * Updates right side carousel
	 * @param server
	 * @param gameId
	 */
	public static void updateLeftCarousel(Server server,String gameId) {
		Factory recFec = DBCon.getFactory();
		Select<Record> query = recFec.select(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID, Zzuser.ZZUSER.USER_FBID, Zzuser.ZZUSER.USER_FNAME
									, Zzuser.ZZUSER.USER_LNAME, Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAMES_PLAYED
									, Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAME_WON, Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_LEVEL)
									.from(Zzgameseat.ZZGAMESEAT, Zzgameusersummary.ZZGAMEUSERSUMMARY, Zzgameinst.ZZGAMEINST, Zzuser.ZZUSER)
									.where((Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(gameId))
											.and(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.equal(Zzgameinst.ZZGAMEINST.GAMEINST_ID))
//											.and(Zzgameinst.ZZGAMEINST.ZZGAMEINST_STATUS.equal(DbConstants.GAME_INST_STATUS_COMPLETED))
											.and(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID))
											.and(Zzuser.ZZUSER.USER_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID)));
		Result<Record> results = query.fetch();
		if(!results.isEmpty()) {
			for(Record result:results) {
				PlayerVO player = new PlayerVO();
				player.setUserId(result.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID));
				player.setTotalGamesPlayed(result.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAMES_PLAYED));
				player.setGamesWon(result.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.TOTAL_GAME_WON));
				player.setLevel(result.getValueAsInteger(Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_LEVEL));
				player.setDisplayName(GameUtil.getDisplayName(result.getValueAsString(Zzuser.ZZUSER.USER_FNAME), result.getValueAsString(Zzuser.ZZUSER.USER_LNAME)));
				player.setFacebookId(result.getValueAsString(Zzuser.ZZUSER.USER_FBID));
				try {
					logger.info("Sending Updated left carousel");
					Client client = server.getClientByUserID(result.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID));
					if(server.isAccountOnline(result.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID))) {
						client.setAttribute(IUserVO.USER_INFO, player.toUDO().toString(), Attribute.SCOPE_GLOBAL, Attribute.FLAG_SHARED);
					}
				} catch (DataTypeException e) {
					// TODO Auto-generated catch block
					logger.error("Datatype error in updateLeftCarousel", e);
				} catch (ClientNotFoundException e) {
					// TODO Auto-generated catch block
					logger.error("Client error in updateLeftCarousel", e);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					logger.error("Illegal Argument error in updateLeftCarousel", e);
				} catch (Exception e) {
					logger.error("Unknown error in updateLeftCarousel",e);
				}
			}
		} else {
			logger.info("Result NULL in left Carousel");
		}
	}
		
		
}
