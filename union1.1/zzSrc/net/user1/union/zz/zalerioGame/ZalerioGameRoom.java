package net.user1.union.zz.zalerioGame;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import net.user1.union.api.Client;
import net.user1.union.api.Message;
import net.user1.union.core.context.ModuleContext;
import net.user1.union.core.event.RoomEvent;
import net.user1.union.core.exception.ClientNotFoundException;
import net.user1.union.zz.baseGame.BaseGameRoom;
import net.user1.union.zz.baseGame.GameUtil;
import net.user1.union.zz.baseGame.event.BaseGameServerEvent;
import net.user1.union.zz.baseGame.lib.vo.IUserVO;
import net.user1.union.zz.common.ClientHelper;
import net.user1.union.zz.common.DBCon;
import net.user1.union.zz.common.DbConstants;
import net.user1.union.zz.common.UnionDataObject;
import net.user1.union.zz.common.model.tables.Zzgameinst;
import net.user1.union.zz.common.model.tables.Zzgameseat;
import net.user1.union.zz.common.model.tables.Zzgameusersummary;
import net.user1.union.zz.common.model.tables.Zzuser;
import net.user1.union.zz.common.model.tables.Zzzlrofig;
import net.user1.union.zz.common.model.tables.Zzzlrofiginst;
import net.user1.union.zz.common.model.tables.Zzzlrofiginstcoord;
import net.user1.union.zz.common.model.tables.Zzzlrogamebet;
import net.user1.union.zz.common.model.tables.Zzzlrogameinstfig;
import net.user1.union.zz.common.model.tables.Zzzlrogameround;
import net.user1.union.zz.common.model.tables.Zzzlrogamescore;
import net.user1.union.zz.common.model.tables.records.ZzgameRecord;
import net.user1.union.zz.common.model.tables.records.ZzgameinstRecord;
import net.user1.union.zz.common.model.tables.records.ZzgameseatRecord;
import net.user1.union.zz.common.model.tables.records.ZzzlrogamebetRecord;
import net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord;
import net.user1.union.zz.common.model.tables.records.ZzzlrogamescoreRecord;
import net.user1.union.zz.zalerioGame.events.ZalerioGameRoomEvent;
import net.user1.union.zz.zalerioGame.lib.exception.ZalerioBoardCoordinateOutOfBoundException;
import net.user1.union.zz.zalerioGame.lib.vo.BetVO;
import net.user1.union.zz.zalerioGame.lib.vo.GameVO;
import net.user1.union.zz.zalerioGame.lib.vo.PlayerBetCurrentRoundVO;
import net.user1.union.zz.zalerioGame.lib.vo.PlayerVO;
import net.user1.union.zz.zalerioGame.lib.vo.RoundVO;
import net.user1.union.zz.zalerioGame.lib.vo.UserVO;

import org.apache.log4j.Logger;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Select;
import org.jooq.SelectQuery;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.Factory;

public class ZalerioGameRoom extends BaseGameRoom implements IZalerioGameRoomVO{
	private static Logger logger = Logger.getLogger(ZalerioGameRoom.class);
	protected static Class<?> GAME_ROOM_CLASS = ZalerioGameRoom.class;
	protected Timer syncTTLTimerPerSec = new Timer();
	
	/* Added by Snehesh to award points for correct, incorrect and bonus */
	private int GAME_CORRECT_BET_POINTS = 10;
	private int GAME_INCORRECT_BET_POINTS = 1;
	private int GAME_BONUS_POINTS_FOR_FIRST_PLAYER = 75;
	
	private int FIGURE_TYPE_NUMERIC = 0;
	private int FIGURE_TYPE_SUPER_JOKER = 1;
	private int FIGURE_TYPE_JOKER = 2;
	
	public static final int MAP_ACTIVE_TILES = 1;
	
	/**
	 * RoomVars
	 */
	//Non-Persistant roomVars
	private RoundVO roomCurrentRound;
	
	//Persistant roomVars
	private Integer roomTotalRounds ;
	private Integer roomBoardX;
	private Integer roomBoardY;
	private Integer roomRoundNoOfBets;
	private Integer roomTotalFigs;
	private HashSet<Integer> hmFigureTiles;
	HashMap<String, FigureDetailVO> hmFigureDetails;
	private HashMap<Integer, TileVO> hmAllTiles;
	private HashMap<String, BetVO> hmBetVOs;
	private HashMap<String, Set<String>> hmPlayerBets;
	private HashMap<String,Integer> hmBetToTiles;
	private LinkedHashMap<String,RoundVO> hmRoomRounds;
	private HashMap<String,PlayerBetCurrentRoundVO> hmCurrentRoundPlayerBets;
	private Map<String, List<String>> jokerWinners;
	private Map<String, String> jokerWonInRounds;
	private Map<String, PlayerVO> allPlayersMap;
	private Map<String, PlayerVO> allPlayersMapByUserId;
	private Set<PlayerVO> allPlayers;
	private GameVO currentGame;
	
	//Non-Shared-OnClient RoomVars
	
	/**
	 * Flags for maintaining roomVars
	 */
	private boolean flag_roomValChanged = true;
	private boolean flag_roomCurrentRound = false;
	private boolean flag_roomRoundEndtime = false;
	private boolean flag_roomRoundEndtimeInMs = false;
	private boolean flag_boardCoordsMap ;
	private boolean flag_lastRound = false;
	private boolean flag_forceUpdateForEmptyZalerioScore = true;
	private boolean flag_gameover = false;
	
	// Flag added to ensure no concurrent modifications are happening during
	// round change
	private boolean processingGameRound = false;
	private boolean updatingGameInfo = false;
	private boolean updatingCurrentGamePlayersInfo = false;
	
	private Integer activePlayersCount;
	private Integer totalPlayerCount;
	
	public ZalerioGameRoom(){
		super();
		logger.info("ZalerioGameRoom constructor called!");
		hmAllTiles = new HashMap<Integer, ZalerioGameRoom.TileVO>();
		hmFigureTiles = new HashSet<Integer>();
		hmBetVOs = new HashMap<String, BetVO>();
		hmRoomRounds = new LinkedHashMap<String, RoundVO>();
		hmPlayerBets = new HashMap<String, Set<String>>();
		hmBetToTiles = new HashMap<String, Integer>();
		hmCurrentRoundPlayerBets = new HashMap<String, PlayerBetCurrentRoundVO>();
		jokerWinners = new HashMap<String, List<String>>();
		jokerWonInRounds = new HashMap<String, String>();
		allPlayersMap = new HashMap<String, PlayerVO>();
		allPlayersMapByUserId = new HashMap<String, PlayerVO>();
		allPlayers = new HashSet<PlayerVO>();
		currentGame = new GameVO();
	}
	
	public boolean init(ModuleContext ctx){
		logger.info("Zalerio Room init() method called!");
		boolean valid = super.init(ctx);
		
    	CURRENT_USER_VO = net.user1.union.zz.zalerioGame.lib.vo.UserVO.class;
    	
		logger.info("Size of Room Layout: " + hmAllTiles.keySet().size());
		try{
			for(int y = 0; y < this.roomBoardY ; y++){
				for (int x=0; x < this.roomBoardX; x++){
					TileVO tVO = new TileVO(x,y);
					hmAllTiles.put(tVO.getCoordNum(), tVO);
				}
			}
		}catch(ZalerioBoardCoordinateOutOfBoundException e){
			logger.error("Exception occoured in ZalerioGameBoard:constructor() : " + e);
		}
		
		loadVars();
		
		if(!flag_gameover) { 
			runTTLSyncTimerPerSec();
		}
		
		//runSyncAllPlayerPerSec();
		//updateClientGameInfoAsync();
		//runSyncClientData();
		
		return valid;
    }
	
	private boolean loadCurrentGame() {
		logger.info("Loading All Players Data");
		
		boolean isChanged = true;
		
		updatingCurrentGamePlayersInfo = true;
		currentGame.setGameInstId(r_ctx.getRoom().getSimpleID());
		currentGame.setGameCreatorId(getGameCreatorId());
		
		Set<PlayerVO> updatePlayersList = getAllPlayersForGame(r_ctx.getRoom().getSimpleID(), flag_gameover);
		if (!updatePlayersList.equals(allPlayers)) {
			allPlayers = updatePlayersList;
			int count = 0;
			for (Iterator<PlayerVO> itr = updatePlayersList.iterator(); itr.hasNext();) {
				PlayerVO playerVO = (PlayerVO) itr.next();
				allPlayersMap.put(playerVO.getSeatId(), playerVO);
				allPlayersMapByUserId.put(playerVO.getUserId(), playerVO);
				if (playerVO.getGameSeatStatus().equals(PlayerVO.GAME_SEAT_STATUS_ACCEPTED_CODE)
						|| playerVO.getGameSeatStatus().equals(PlayerVO.GAME_SEAT_STATUS_INVITED_CODE)) {
					count++;
				}
			}
			currentGame.setPlayers(new ArrayList<PlayerVO>(allPlayersMap.values()));
			activePlayersCount = count;
			totalPlayerCount = allPlayers.size();
		} else {
			//isChanged = false;
		}
		
		updatingCurrentGamePlayersInfo = false;
		return isChanged;
	}
	
	private void syncAllPlayer() {
		logger.info("Syncing All Player Data for the game");
		/*
		Set<String> playerIds = allPlayers.keySet();
		UnionDataObject udo = new UnionDataObject();
		for (String playerId : playerIds) {
			PlayerVO player = allPlayers.get(playerId);
			udo.append(playerId, player.toUDO());
		}
		logger.info("All Player Date : " + udo);
		setPublicRoomVar(IZalerioGameRoomVO.ROOM_ALL_PLAYERS, udo);
		*/
		if(currentGame != null && currentGame.getPlayers() != null/* && currentGame.isChanged()*/) {
			UnionDataObject udo = new UnionDataObject();
			udo.append(currentGame.getGameInstId(), currentGame.toUDO());
			logger.info("Setting All Players Info to: " +  udo);
			setPublicRoomVar(IZalerioGameRoomVO.ROOM_ALL_PLAYERS, udo);
		}
		logger.info("Update all player data");
	}
	
	@Override
	public void onClientAdd(RoomEvent e){
		logger.info("Client add callback");
		
		super.onClientAdd(e);
		Client client = e.getClient();
		IUserVO userVO = (IUserVO) getUserVO(client.getUserID());
		userVO.updateUserVars();
		
		/* Whenever a client is added the data is synced */
		// Syncing all player data for the user
		if(!updatingCurrentGamePlayersInfo) {
			if(loadCurrentGame()) {
				syncAllPlayer();
			}
		}

		afterBetPlaced(userVO.getUserSeatId());
		updateZalerioScores();
		PlayerBetCurrentRoundVO playerBetCurrentRoundVO  = hmCurrentRoundPlayerBets.get(userVO.getUserSeatId());
		
		intimateBidder(playerBetCurrentRoundVO.toUDO(getRoomCurrentRoundId()),client,ZalerioGameRoomEvent.RES_ZALERIOGAME_CHANGEBETS);
		playerBetCurrentRoundVO.setVoChangeFalse();
		
		updateClientGameInfoAsync();
		
		logger.info("Client added");
	}
	
	public boolean loadVars(){
		boolean flag_noError = true;
		
		/**
		 * Loading figures
		 */
		flag_noError = (flag_noError && loadFigureTiles());
		
		/**
		 * Loading rounds
		 */
		flag_noError = (flag_noError && loadGameRounds());
		syncGameRound();
		selectRoomRound(true);
		
		/**
		 * Loading Player Information
		 */
		flag_noError = (flag_noError && loadCurrentGame());//loadAllPlayers()
		syncAllPlayer();
		
		/**
		 * Loading activeBets
		 */
		flag_noError = (flag_noError && loadActiveBets());
		syncActiveBets();
		
		return flag_noError;
		
	}
	
	private boolean loadActiveBets(){
		
		logger.info("Loading Active Bets");
		
		boolean flag_noError = true;
		//Fetch all the bets made by each user for all the rounds before the current round
		Select<Record> qActiveBets;
		if(isFlag_lastRound()){
			qActiveBets = roomFactory.select()
					.from(Zzzlrogamebet.ZZZLROGAMEBET)
					.join(Zzgameseat.ZZGAMESEAT).on(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_GAMESEAT_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID))
					.leftOuterJoin(Zzzlrogameround.ZZZLROGAMEROUND).on(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ZLGAMEROUND_ID.equal(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID))
					.where(
							Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.equal(r_ctx.getRoom().getSimpleID())
					)
					.orderBy(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_BETTIME);//Added order by to track who completed first
		}else{
			qActiveBets = roomFactory.select()
					.from(Zzzlrogamebet.ZZZLROGAMEBET)
					.join(Zzgameseat.ZZGAMESEAT).on(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_GAMESEAT_ID.equal(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID))
					.leftOuterJoin(Zzzlrogameround.ZZZLROGAMEROUND).on(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ZLGAMEROUND_ID.equal(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID))
					.where(
							Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.equal(r_ctx.getRoom().getSimpleID())
							.and(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID.notEqual(getRoomCurrentRoundId()))
					)
					.orderBy(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_BETTIME);//Added order by to track who completed first
		}
			
		try{
			//Added by Ravi for correct and incorrect score
			Map<String,Long> hmPlayerAdditionalScores = new HashMap<String, Long>();
			//Added by Ravi for correct and incorrect score
			
			HashMap<String,HashMap<String,Integer>> hmPlayerFigureCount = new HashMap<String, HashMap<String,Integer>>();
			
			// Set of all the players who have placed a bet in the game
			Set<String> playersSeat = new HashSet<String>(); 
			Result<Record> results = qActiveBets.fetch();

			//Added to have the time of the last bet placed to complete a figure
			Map<String, Map<String, Timestamp>> timeOfLastPlacedBetPerPlayerPerFigure = new HashMap<String, Map<String, Timestamp>>();
			if(results != null){
				
				for(Record rec:results){
					BetVO betVO = new BetVO();
					betVO.loadfromDataRow(rec,getRoomBoardY());
					
					// Adding to the set of all the players
					playersSeat.add(betVO.getGameSeatId());
					
					hmBetVOs.put(betVO.getBetId(), betVO);
					
					Integer currentTileNum = betVO.getBetCoord();
					//Saving as an index between bets and tiles
					hmBetToTiles.put(betVO.getBetId(), currentTileNum);
					
					//Saving the bet for each player in a separate Map
					Set<String> currentPlayerBets;
					if(hmPlayerBets.containsKey(betVO.getGameSeatId())){
						currentPlayerBets = hmPlayerBets.get(betVO.getGameSeatId());
					}else{
						currentPlayerBets = new HashSet<String>();
					}
					currentPlayerBets.add(betVO.getBetId());
					hmPlayerBets.put(betVO.getGameSeatId(), currentPlayerBets);
					
					//For calculating per player per figure count
					HashMap<String, Integer> playerFigureCount = null;
					if(hmPlayerFigureCount.containsKey(betVO.getGameSeatId())){
						playerFigureCount = hmPlayerFigureCount.get(betVO.getGameSeatId());
					}else{
						playerFigureCount = new HashMap<String, Integer>();
					}
					TileVO currentTile = hmAllTiles.get(betVO.getBetCoord());
					String currentTileFigure = currentTile.getCoordVO().getCoordFigureId();
					
					//Added by Ravi for correct and incorrect score
					long playerAddScores = 0;
					if(hmPlayerAdditionalScores.containsKey(betVO.getGameSeatId())) {
						playerAddScores = hmPlayerAdditionalScores.get(betVO.getGameSeatId());
					}
					//Added by Ravi for correct and incorrect score
					
					if (currentTileFigure != null && currentTileFigure.length() > 0) {
						Integer figCounter = 0;
						if (playerFigureCount.containsKey(currentTileFigure)) {
							figCounter = playerFigureCount.get(currentTileFigure);
						}
						playerFigureCount.put(currentTileFigure, ++figCounter);
						hmPlayerFigureCount.put(betVO.getGameSeatId(), playerFigureCount);
						
						//To check if the user is selecting a Joker or a Super Joker
						FigureDetailVO figureDetailVO = hmFigureDetails.get(currentTile.getCoordVO().getCoordFigureId());
						if(figureDetailVO.getFigureType() == FIGURE_TYPE_NUMERIC) {
							//Added by Ravi for correct and incorrect score
							playerAddScores += GAME_CORRECT_BET_POINTS;
						} else if(figureDetailVO.getFigureType() == FIGURE_TYPE_SUPER_JOKER || figureDetailVO.getFigureType() == FIGURE_TYPE_JOKER) {
							// Joker or Super Joker
							//logger.info("Joker Found");
							if(jokerWinners.get(currentTileFigure) == null) {
								//No one has won this so far
								//This is all sorted as per time, so the one who wins this first will be in the map
								jokerWonInRounds.put(currentTileFigure, betVO.getRoundId());
								
								List<String> winners = new ArrayList<String>();
								winners.add(betVO.getGameSeatId());
								jokerWinners.put(currentTileFigure, winners);
								currentTile.setBetWinner(winners);
								//logger.info("First one to win Joker:" + betVO.getGameSeatId());
							} else if (!jokerWinners.get(currentTileFigure).contains(betVO.getGameSeatId())
									&& jokerWonInRounds.get(currentTileFigure).equals(betVO.getRoundId())) {
								// Others who have won in the same round
								jokerWinners.get(currentTileFigure).add(betVO.getGameSeatId());
								currentTile.setBetWinner(jokerWinners.get(currentTileFigure));
								//logger.info("Other who won in the same round : " + betVO.getGameSeatId());
							} else {
								// Other seat has won the joker before, but no negative marking to other players
								// playerAddScores += GAME_INCORRECT_BET_POINTS;
								// logger.info("Already someone has one the Joker:" + betVO.getGameSeatId());
							}
						}
						
						//TODO: This needs to be fixed to update the last time stamp of the bet placed for a user for a key
						if(timeOfLastPlacedBetPerPlayerPerFigure.containsKey(betVO.getGameSeatId())) {
							// This will override the timestamp for a particular tile
							timeOfLastPlacedBetPerPlayerPerFigure.get(betVO.getGameSeatId()).put(currentTileFigure, betVO.getBetTime());
						} else {
							// Creating a tile to ts mapping and storing the same
							Map<String, Timestamp> tileBetTSMap = new HashMap<String, Timestamp>();
							tileBetTSMap.put(currentTileFigure, betVO.getBetTime());
							timeOfLastPlacedBetPerPlayerPerFigure.put(betVO.getGameSeatId(), tileBetTSMap);
						}
					} else {
						playerAddScores += GAME_INCORRECT_BET_POINTS;
					}
					//Added by Ravi for correct and incorrect score
					hmPlayerAdditionalScores.put(betVO.getGameSeatId(), playerAddScores);                   

					
					//Adding user and round info to tiles Map and incrementing the tile-count simultaneously
					TileVO currentTileVO = hmAllTiles.get(currentTileNum);
					currentTileVO.setPlayerBetOnTile(betVO.getGameSeatId(), betVO.getRoundId());
				}
			}
			
			//Checking the completion of a figure and holds the count for each completion
			HashMap<String,Integer> playerPerFigureCounts = new HashMap<String, Integer>(); 
			HashMap<String,Timestamp> lastTsPerFigure = new HashMap<String, Timestamp>();
			HashMap<String,String> figureFirstPlayerMap = new HashMap<String, String>();
			//Checking for user scores
			Set<String> allPlayerKeys = hmPlayerFigureCount.keySet();
			
			//For each Player
			for(String playerSeatId:allPlayerKeys){
				HashMap<String,Integer> figureCounts = hmPlayerFigureCount.get(playerSeatId);
				Set<String> figureCountKeys = figureCounts.keySet();
				
				//Iterate over each type of figure
				for(String currentFigId:figureCountKeys){
					long playerFigureCountTotal = figureCounts.get(currentFigId);
					FigureDetailVO figureDetailVO = hmFigureDetails.get(currentFigId);
					if(figureDetailVO.getFigureTotalCount() == playerFigureCountTotal){
						//Figure Completed
						Integer figRepeat = 0;
						if(playerPerFigureCounts.containsKey(currentFigId)){
							figRepeat = playerPerFigureCounts.get(currentFigId);
						}
						//Setting the number of people who have completed the figure
						playerPerFigureCounts.put(currentFigId, ++figRepeat);
						
						//Setting the last time stamp of the user
						if(lastTsPerFigure.containsKey(currentFigId)) {
							if(lastTsPerFigure.get(currentFigId).after(timeOfLastPlacedBetPerPlayerPerFigure.get(playerSeatId).get(currentFigId))) {
								lastTsPerFigure.put(currentFigId, timeOfLastPlacedBetPerPlayerPerFigure.get(playerSeatId).get(currentFigId));
								figureFirstPlayerMap.put(currentFigId, playerSeatId);
							}
						} else {
							lastTsPerFigure.put(currentFigId, timeOfLastPlacedBetPerPlayerPerFigure.get(playerSeatId).get(currentFigId));
							figureFirstPlayerMap.put(currentFigId, playerSeatId);
						}
					}
				}
			}
			
			//Calculating scores
			//logger.info("Calculating score for all players");
			
			// Iterating over each seat irrespective of right or wring bets
			for(String playerSeatId:playersSeat){
			//for(String playerSeatId:allPlayerKeys){
				//logger.info("Calculating score for player seat: " + playerSeatId);
				long playerScore = 0;
				
				// Added by Ravi for correct and incorrect score
				// Adding bonus points
				long playerAddScores = 0;
				if (hmPlayerAdditionalScores.containsKey(playerSeatId)) {
					playerAddScores = hmPlayerAdditionalScores.get(playerSeatId);
				}
				//logger.info("Net Score based on bet placement : " + playerAddScores);
				playerScore += playerAddScores;
				// Added by Ravi for correct and incorrect score

				HashMap<String,Integer> figureCounts = hmPlayerFigureCount.get(playerSeatId);
				// Figure count can be null for players who have not placed any bets
				if(figureCounts != null) {
					Set<String> figureCountKeys = figureCounts.keySet();
					for(String currentFigId:figureCountKeys){
						//logger.info("Current Figure Id : " + currentFigId);
						long playerFigureCountTotal = figureCounts.get(currentFigId);
						FigureDetailVO figureDetailVO = hmFigureDetails.get(currentFigId);
						if(figureDetailVO.getFigureTotalCount() == playerFigureCountTotal){
							
							if(figureDetailVO.getFigureType() == FIGURE_TYPE_SUPER_JOKER || figureDetailVO.getFigureType() == FIGURE_TYPE_JOKER) {
								if(jokerWinners.get(currentFigId).contains(playerSeatId)) {
									// The players who found the joker in one round get it
									playerScore += figureDetailVO.getFigureTotalScore();
									//logger.info("Found Joker : " + figureDetailVO.getFigureTotalScore());
								}
							} else {
								//logger.info("Found Normal Figure");
								playerScore += (  figureDetailVO.getFigureTotalScore() / playerPerFigureCounts.get(currentFigId) );
								if(figureFirstPlayerMap.get(currentFigId).equals(playerSeatId)) {
									//First Player
									playerScore += GAME_BONUS_POINTS_FOR_FIRST_PLAYER;//Bonus Points;
									//logger.info("Got Bonus Points");
								}
							}
						}
					}
				}
				Integer playerScoreInt = Integer.valueOf((int) playerScore);
				
				UserVO userVO=(UserVO) findUserVOBySeatId(playerSeatId);
				if(userVO != null) {
					userVO.setZalerioRoundScore(playerScoreInt);
				}
				
				PlayerVO playerVO = allPlayersMap.get(playerSeatId);
				if(playerVO != null) {
					playerVO.setScore(playerScoreInt);
				}
				
				//Updating Score per Seat per round
				//Commenting this as this is not needed for now
				//updateZalerioScoreForSeatForRoundInDB(playerSeatId, playerScoreInt);
			}
			
			updateZalerioScores();
			updateRanks();
			
		}catch(DataAccessException e){
			logger.error("Exception in ZalerioGameBoard:loadActiveTiles()", e);
			flag_noError = false;
		}
		
		/*
		// Syncing all player data for the user
		if(!updatingCurrentGamePlayersInfo) {
			if(loadCurrentGame()) {
				syncAllPlayer();
			}
		}
		*/
		
		logger.info("Loading Active Bets = " + flag_noError);
		return flag_noError;
	}
	
	/**
	 * This method is used to update the scores of each round into the database
	 */
	private void updateZalerioScoreForSeatForRoundInDB(String userSeatId, int score) {
		logger.info("Updating round scores for seats");
		try {

			Savepoint changeRoundSavePoint = roomFactoryWrite.getConnection().setSavepoint("CHANGE_ROUND_SCORE");

			// Update this data in the database
			//Set<String> userLoginIds = hmUserVOs.keySet();

			boolean flag_dbModified = false;

			//for (String userLoginId : userLoginIds) {
				//UserVO userVO = (UserVO) hmUserVOs.get(userLoginId);

				// Fetch the data already saved in the db
				ZzzlrogamescoreRecord rcGameScoreRecords = roomFactoryWrite.selectFrom(Zzzlrogamescore.ZZZLROGAMESCORE)
						.where(Zzzlrogamescore.ZZZLROGAMESCORE.ZLGAMESCORE_GAMESEAT_ID.equal(userSeatId))
						.and(Zzzlrogamescore.ZZZLROGAMESCORE.ZLGAMESCORE_ZLGAMEROUND_ID.equal(getRoomCurrentRoundId())).fetchOne();

				flag_dbModified = true;
				
				if (rcGameScoreRecords == null) {

					Timestamp nowTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());

					ZzzlrogamescoreRecord gamesScore = roomFactoryWrite.newRecord(Zzzlrogamescore.ZZZLROGAMESCORE);
					gamesScore.setCreateTime(nowTimestamp);
					gamesScore.setUpdateTime(nowTimestamp);
					logger.info(userSeatId + " : " + getRoomCurrentRoundId() + " : " + score);
					gamesScore.setZlgamescoreGameseatId(userSeatId);
					gamesScore.setZlgamescoreId(getRoomCurrentRoundId() + "_" + userSeatId);
					gamesScore.setZlgamescoreScored(score);
					gamesScore.setZlgamescoreZlgameroundId(getRoomCurrentRoundId());
					gamesScore.store();
				} else {
					rcGameScoreRecords.setZlgamescoreScored(score);
					rcGameScoreRecords.store();
				}
			//}

			if (flag_dbModified) {
				roomFactoryWrite.getConnection().commit();
			} else {
				roomFactoryWrite.getConnection().rollback(changeRoundSavePoint);
			}
		} catch (Exception e) {
			logger.error("Error in ZalerioGameRoom:changeCurrentRound() : " + e);
		}
		logger.info("Updated round scores for seats");
	}
	
	private void updateZalerioScores() {
		//List<UserVO> userVos = new ArrayList<UserVO>();
		boolean flag_Scorechanged = false;
		boolean flag_ForceUpdate = false;
		//Set<String> userLoginIds = hmUserVOs.keySet();
		Set<String> userLoginIds = allPlayersMap.keySet();
		UnionDataObject udo = new UnionDataObject();
		for(String userLoginId:userLoginIds){
			PlayerVO playerVO = allPlayersMap.get(userLoginId);
			//UserVO userVO = (UserVO) hmUserVOs.get(userLoginId);
			flag_ForceUpdate = flag_forceUpdateForEmptyZalerioScore;
			/*
			if(userVO.isFlag_scoreChanged()){
				flag_Scorechanged = true;
				userVO.setFlag_scoreChanged(true);
			}*/
			udo.append(playerVO.getSeatId(), playerVO.getScore());
			//udo.append(userVO.getUserSeatId(), userVO.getZalerioUserScore());
			//userVos.add(userVO);
		}
		//if(flag_Scorechanged || flag_ForceUpdate){
			flag_forceUpdateForEmptyZalerioScore = flag_ForceUpdate;
			setPublicRoomVar(IZalerioGameRoomVO.ZLRO_ROOM_SCORE, udo);
		//}
	}
	
	private void updateRanks() {
		List<PlayerVO> players = new ArrayList<PlayerVO>(allPlayersMap.values());
		Collections.sort(players, Collections.reverseOrder());
		try {
			Savepoint updateRankSavePoint = roomFactoryWrite.getConnection().setSavepoint("UPDATE_RANKS");
			int i = 0;
			for (Iterator<PlayerVO> itr = players.iterator(); itr.hasNext();) {
				PlayerVO playerVO = itr.next();

				// Update this data in the database
				// Set<String> userLoginIds = hmUserVOs.keySet();

				boolean flag_dbModified = false;

				// for (String userLoginId : userLoginIds) {
				// UserVO userVO = (UserVO) hmUserVOs.get(userLoginId);

				// Fetch the data already saved in the db
				ZzgameseatRecord zzgameseatRecord = roomFactoryWrite.selectFrom(Zzgameseat.ZZGAMESEAT)
						.where(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID.equal(playerVO.getSeatId())).fetchOne();

				flag_dbModified = true;

				if (zzgameseatRecord != null) {
					zzgameseatRecord.setGameseatScore(allPlayersMap.get(playerVO.getSeatId()).getScore());
					if (allPlayersMap.get(playerVO.getSeatId()).getGameSeatStatus().equals(PlayerVO.GAME_SEAT_STATUS_ACCEPTED_CODE)) {
						// Only Active accepted players get rank
						zzgameseatRecord.setGameRank(++i);
					} else {
						zzgameseatRecord.setGameRank(Integer.valueOf(0));
					}
					zzgameseatRecord.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					zzgameseatRecord.store();
					flag_dbModified = true;
				}

				if (flag_dbModified) {
					roomFactoryWrite.getConnection().commit();
				} else {
					roomFactoryWrite.getConnection().rollback(updateRankSavePoint);
				}
			}
		} catch (Exception e) {
			logger.error("Error in ZalerioGameRoom:changeCurrentRound() : " + e);
		}
		logger.info("Updated round scores for seats");
	}

	private void selectRoomRound(boolean isInit){
		logger.info("Select Room Round");
		long nowtimeInMs = Calendar.getInstance().getTimeInMillis();
		Set<String> roomRoundIds= hmRoomRounds.keySet();
		RoundVO lastRoundVO = null;
		RoundVO newRoundVO = null;
		for(String roomRoundId:roomRoundIds){
			RoundVO roundVO = hmRoomRounds.get(roomRoundId);
			logger.info("[Round info : " + roundVO + ",nowtimeInMs:" + nowtimeInMs + "]");
			if(lastRoundVO == null && roundVO.isLastRound()){
				logger.info("Found last round");
				lastRoundVO = roundVO ;
			}
			if(roundVO.getStartTime().getTime() < nowtimeInMs && roundVO.getEndTime().getTime() > nowtimeInMs){
				// The round is running
				logger.info("Found current round");
				newRoundVO = roundVO;
				break;
			}
		}
		
		if(newRoundVO != null){
			setRoomCurrentRound(newRoundVO);
		}else if(lastRoundVO != null){
			logger.info("No current round found, hence setting the round as last");
			setRoomCurrentRound(lastRoundVO);
			flag_gameover = true;
		}
		
		if(getRoomCurrentRoundId() != null){
			setPublicRoomVar(IZalerioGameRoomVO.ROOM_CURRENTROUND, getRoomCurrentRoundId());
			setPublicRoomVar(IZalerioGameRoomVO.ROOM_ROUND_ENDTIME, getRoomRoundEndtime());
			setPublicRoomVar(IZalerioGameRoomVO.ROOM_ROUND_ENDTIMEINMS, getRoomRoundEndTimeInMs());
			if(!isInit) {
				afterRoundChangedOnTimeExpire();
			}
		}
	}
	
	private boolean loadGameRounds() {
		logger.info("Loading Game Round");
		boolean flag_noError = true;
		Select<Record> qGameRounds = roomFactory.select()
				.from(Zzzlrogameround.ZZZLROGAMEROUND)
				.where(
						Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_GAMEINST_ID.equal(r_ctx.getRoom().getSimpleID())
				)
				.orderBy(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ROUNDNAME.cast(Long.class).asc());
		
		try{
			Result<Record> results = qGameRounds.fetch();
			int roundNumber = 0;
			if(results != null){
				for(Record rec:results){
					++roundNumber;
					RoundVO roundVO;
					if(hmRoomRounds.containsKey(rec.getValueAsString(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID))){
						roundVO = hmRoomRounds.get(rec.getValueAsString(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID));
						roundVO.setData(rec.getValueAsString(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ROUNDNAME),
							rec.getValueAsTimestamp(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_TIMESTART),
							rec.getValueAsTimestamp(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_TIMEEND));
					}else{
						roundVO = new RoundVO(
							rec.getValueAsString(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID), 
							rec.getValueAsString(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ROUNDNAME),
							rec.getValueAsTimestamp(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_TIMESTART),
							rec.getValueAsTimestamp(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_TIMEEND)
						);
						if(roundNumber == results.size()) {
							//Setting this round as the last round
							roundVO.setLastRound(true);
						}
						hmRoomRounds.put(roundVO.getRoundId(), roundVO);
					}
					
				}
			}
		}catch (DataAccessException e) {
			logger.error("Exception in ZalerioGameBoard:loadActiveTiles()", e);
			flag_noError = false;
		}
		logger.info("Game Rounds data loaded");
		logger.info("Checking for game time up");
		Record qGame = roomFactory.select(Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME)
				.from(Zzgameinst.ZZGAMEINST).where(Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(r_ctx.getRoom().getSimpleID())).fetchOne();
		if(qGame.getValueAsTimestamp(Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME).before(new Timestamp(System.currentTimeMillis())))
		{
//			flag_gameover = true;
			setPublicRoomVar(IZalerioGameRoomVO.FINAL_ROUND_COMPLETE,1); 
		}
		return flag_noError;
	}
	
	private void syncGameRound(){
		logger.info("Syncing Game Round");
		Set<String> roomRoundIds = hmRoomRounds.keySet();
		UnionDataObject udo = new UnionDataObject();
		for(String roomRoundId:roomRoundIds){
			RoundVO roundVO = hmRoomRounds.get(roomRoundId);
			if(roundVO.isVoChanged()){
				udo.append(roomRoundId, roundVO.toUDO());
				roundVO.setVoChangedFalse();
			}
		}
		logger.debug("Room all rounds data : " + udo);
		setPublicRoomVar(IZalerioGameRoomVO.ROOM_ALLROUNDS, udo);
		logger.info("Game Round Synced");
	}
	
	private void syncActiveBets() {
		logger.info("Syncing Active Bets");
		// TODO Auto-generated method stub
		Set<String> roomActiveBets = hmBetToTiles.keySet();
		UnionDataObject udo = new UnionDataObject();
		for(String roomActiveBet:roomActiveBets){
			Integer betPos = hmBetToTiles.get(roomActiveBet);
			if(hmAllTiles.containsKey(betPos)){
				udo.append(betPos, hmAllTiles.get(betPos).toUDO());
				//roundVO.setVoChangedFalse();
			}
		}
		logger.info("Room Board Vars (BV) = " + udo);
		setPublicRoomVar(IZalerioGameRoomVO.ROOM_BOARDVARS, udo);
		logger.info("Active Bets Synced");
	}
	
	

	private boolean loadFigureTiles(){
		logger.info("Loading Figure Tiles");
		boolean flag_noError = true;
		Integer figXPos ;
		Integer figYPos ;
		//Integer figNoOfCols ;
		//Integer figNoOfRows ;
		Integer cvX ;
		Integer cvY ;
		Record recTemp = null;
		hmFigureDetails = new HashMap<String, FigureDetailVO>();
		
		Select<Record> qFigurePoints = roomFactory.select()
				.from(Zzzlrofiginstcoord.ZZZLROFIGINSTCOORD,Zzzlrofiginst.ZZZLROFIGINST,Zzzlrogameinstfig.ZZZLROGAMEINSTFIG,Zzzlrofig.ZZZLROFIG)
				.where(
						Zzzlrogameinstfig.ZZZLROGAMEINSTFIG.ZLGAMEINSTFIG_GAMEINST_ID.equal(r_ctx.getRoom().getSimpleID())
						.and(Zzzlrofiginst.ZZZLROFIGINST.ZLFIGINST_ID.equal(Zzzlrogameinstfig.ZZZLROGAMEINSTFIG.ZLGAMEINSTFIG_ZLFIGINST_ID))
						.and(Zzzlrofiginstcoord.ZZZLROFIGINSTCOORD.ZLFIGINSTCOORD_ZLFIGINST_ID.equal(Zzzlrofiginst.ZZZLROFIGINST.ZLFIGINST_ID))
						.and(Zzzlrofig.ZZZLROFIG.ZLFIG_ID.equal(Zzzlrofiginst.ZZZLROFIGINST.ZLFIGINST_ZLFIG_ID))
				);
		try{
			Result<Record> results = qFigurePoints.fetch();
			
			if(results != null){
				for(Record rec:results){
					recTemp = rec;
					String gameInstFigureId = rec.getValueAsString(Zzzlrogameinstfig.ZZZLROGAMEINSTFIG.ZLGAMEINSTFIG_ID);
					//logger.info("Game Inst Figure Id : " + gameInstFigureId);
					//figNoOfCols = rec.getValueAsInteger(OloZlroFigInst.OLO_ZLRO_FIG_INST.NO_OF_COLUMNS);
					//figNoOfRows = rec.getValueAsInteger(OloZlroFigInst.OLO_ZLRO_FIG_INST.NO_OF_ROWS);
					figXPos = rec.getValueAsInteger(Zzzlrogameinstfig.ZZZLROGAMEINSTFIG.ZLGAMEINSTFIG_POSLEFT);
					figYPos = rec.getValueAsInteger(Zzzlrogameinstfig.ZZZLROGAMEINSTFIG.ZLGAMEINSTFIG_POSTOP);
					cvX = rec.getValueAsInteger(Zzzlrofiginstcoord.ZZZLROFIGINSTCOORD.ZLFIGINSTCOORD_POSX);
					cvY = rec.getValueAsInteger(Zzzlrofiginstcoord.ZZZLROFIGINSTCOORD.ZLFIGINSTCOORD_POSY);
					Long figureScore = rec.getValueAsLong(Zzzlrofig.ZZZLROFIG.ZLFIG_POINTS);
					Long figureType = rec.getValueAsLong(Zzzlrofig.ZZZLROFIG.ZLFIG_TYPE);
					//logger.debug("Figure Score : " + figureScore);
					//logger.debug("Figure Type : " + figureType);
					
					if(cvX < 0 || cvY < 0){
						throw new ZalerioBoardCoordinateOutOfBoundException(roomBoardX,roomBoardY,cvX,cvY);
					}
					cvX = cvX + figXPos  ;
					cvY = cvY + figYPos  ;
					if(cvX > roomBoardX || cvY > roomBoardY){
						throw new ZalerioBoardCoordinateOutOfBoundException(roomBoardX,roomBoardY,cvX,cvY);
					}
					Integer activeCO = calcCoordNum(cvX,cvY,roomBoardY);
					hmFigureTiles.add(activeCO);
					
					TileVO currentTileVO = hmAllTiles.get(activeCO);
					currentTileVO.getCoordVO().setCoordFigureId(gameInstFigureId);
					
					FigureDetailVO currentFigDetail = null;
					if(hmFigureDetails.containsKey(gameInstFigureId)){
						currentFigDetail = hmFigureDetails.get(gameInstFigureId);
					}else{
						currentFigDetail = new FigureDetailVO();
						currentFigDetail.setFigureId(gameInstFigureId);
						currentFigDetail.setFigureTotalScore(figureScore);
						currentFigDetail.setFigureType(figureType);
					}
					long currentFigCount = currentFigDetail.getFigureTotalCount();
					currentFigDetail.setFigureTotalCount(++currentFigCount);
					
					hmFigureDetails.put(currentFigDetail.getFigureId(), currentFigDetail);
					
				}
				
				//Setting figure data to room vars
				UnionDataObject udo = new UnionDataObject();
				Set<String> hmFigureDetailsKeys = hmFigureDetails.keySet();
				FigureDetailVO figureDetailVO = null;
				for(String figureDetailKey:hmFigureDetailsKeys){
					figureDetailVO =  hmFigureDetails.get(figureDetailKey);
					udo.append(figureDetailKey, figureDetailVO.getFigureTotalCount());
				}
				setPublicRoomVar(IZalerioGameRoomVO.ROOM_FIGUREDETAIL, udo);
				
			}
		}catch (ZalerioBoardCoordinateOutOfBoundException e) {
			logger.error("Exception in ZalerioGameBoard:loadActiveTiles() :" + " in record : " + recTemp, e);
			flag_noError = false;
		}catch (DataAccessException e) {
			logger.error("Exception in ZalerioGameBoard:loadActiveTiles() :", e);
			flag_noError = false;
		}
		
		logger.info("Figures Loaded");
		return flag_noError;
	}
	

	
	
	
	public HashMap<Integer,String> placeBets(Client client,String betStr) {
		logger.info("Placing bets for " + client.getUserID() + " bets = " + betStr);
		boolean checkBetValid = true;
		String roundId = getRoomCurrentRoundId();
		logger.info("Current Round Id = " + roundId);
		
		IUserVO userVO = getUserVO(client.getUserID());
		logger.info("Client = " + userVO.getUserLoginId() + " - " + userVO.getUserSeatId());
		
		HashMap<Integer,String> betStatus = new HashMap<Integer, String>();
		Set<Integer> betStatusKeys = null;
		
		/**
		 * Extracting the bets from the string
		 */
		// All bests for the Round is being parser
		logger.info("Bets Placed = " + betStr);
		try{
			String[] betStrArr = betStr.split(":");
			Integer[] bets = new Integer[betStrArr.length];
			int i = 0;
			for(String betStrNum:betStrArr){
				Integer bet = Integer.parseInt(betStrNum);
				bets[i++] = bet;//TODO - This is not used
				betStatus.put(bet, BetStatus.BET_FAILED);
			}
		}catch(Exception e){
			logger.error("Error in parsing the string to bets : " + e);
			checkBetValid = false;
		}
		logger.info("Bets parsing done successfully");
		
		if(!checkBetValid){
			return betStatus;
		}
		
		//Assigning keySet for easy iteration in the bet's Map
		betStatusKeys = betStatus.keySet();

		
		/**
		 * Checking for number of bets placed against the number of bets allowed for each round
		 */
		if(getRoomRoundNoOfBets() < betStatus.size()){
			checkBetValid = false;
		}
		if(!checkBetValid){
			for(Integer bet:betStatusKeys){
				betStatus.put(bet, BetStatus.BET_FAILED_MAX_BET_EXCEED);
			}
			return betStatus;
		}
		logger.info("Bets not more than maximum bets per round");
		
		/**
		 * Checking for boundary conditions for each bet
		 */
		if(checkBetValid){
			Integer tileSize = hmAllTiles.size();
			for(Integer bet:betStatusKeys){
				if(bet < 0 || bet > tileSize){
					betStatus.put(bet, BetStatus.BET_FAILED_BOUNDARY_CONDITION);
					checkBetValid = false;
				}
			}
		}
		if(!checkBetValid){
			return betStatus;
		}
		logger.info("Bets does not fail boundary condition");
		
		
		/**
		 * Checking for number of bets placed in the current round by the player
		 */
		logger.info("To check if bets are already placed by the user for this round");
		Record qCountBetForCurrentRound = roomFactory.select(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ID.count().as("BET_COUNT"))
				.from(Zzzlrogamebet.ZZZLROGAMEBET,Zzzlrogameround.ZZZLROGAMEROUND)
				.where(
						Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ZLGAMEROUND_ID.equal(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID)
						.and(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_GAMESEAT_ID.equal(userVO.getUserSeatId()))
						.and(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID.equal(roundId))
				).fetchOne();
		if(qCountBetForCurrentRound != null){
			int betCount = qCountBetForCurrentRound.getValueAsInteger("BET_COUNT");
			
			//if there are bets placed for this round by the player
			if(betCount > 0 ){
				checkBetValid = false;
			}
		}
		if(!checkBetValid){
			for(Integer bet:betStatusKeys){
				betStatus.put(bet, BetStatus.BET_FAILED_ROUND_PLAYED);
			}
			return betStatus;
		}
		logger.info("No Bets placed for this round from database");
		
		/**
		 * Is bet already placed
		 */
		logger.info("Checking if bets already placed");
		Set<String> betsByPlayer = hmPlayerBets.get(userVO.getUserSeatId());
		if(betsByPlayer != null ){
			for(String betByPlayer : betsByPlayer){
				BetVO bVO = hmBetVOs.get(betByPlayer);
				for(Integer bet:betStatusKeys){
					if(bVO.getBetCoord() == bet){
						betStatus.put(bet, BetStatus.BET_FAILED_ALREADY_PLACED);
						checkBetValid = false;
					}
				}
			}
		}
		if(!checkBetValid){
			return betStatus;
		}
		logger.info("Bets are not already placed");
		
		Timestamp nowTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		Savepoint transactionSavePoint = null;
		Factory writeFactory = roomFactoryWrite;		
		
		try {
			transactionSavePoint = writeFactory.getConnection().setSavepoint("PLACE_BET");
		} catch (SQLException e) {
			logger.error("Error in getting connection: " + e);
			checkBetValid = false;
			e.printStackTrace();
		}
		if(!checkBetValid){
			for(Integer bet:betStatusKeys){
				betStatus.put(bet, BetStatus.BET_FAILED_DB_CONNECTION);
			}
			return betStatus;
		}
		logger.info("Transaction point saved.");
		
		Long newGameBetIdForDB = DBCon.getSerialisedPrimaryKeyForLong(Zzzlrogamebet.ZZZLROGAMEBET, Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ID);
		
		for(Integer bet:betStatusKeys){
			String betReturnStatus = persistBet(writeFactory,bet,newGameBetIdForDB++, client,userVO.getUserSeatId(),nowTime,roundId);
			betStatus.put(bet, betReturnStatus);
			if(!(betReturnStatus.equalsIgnoreCase(BetStatus.BET_PLACED))){
				checkBetValid = false;
				break;
			}
		}
		if(!checkBetValid){
			//Rollback the transaction
			try {
				writeFactory.getConnection().rollback(transactionSavePoint);
			} catch (SQLException e) {
				logger.error("Error in rollback of failed transaction : " + e);
				e.printStackTrace();
			}
		}else{
			//Commit the transaction
			try {
				writeFactory.getConnection().releaseSavepoint(transactionSavePoint);
			
				writeFactory.getConnection().commit();
				
			} catch (SQLException e) {
				checkBetValid = false;
				logger.error("Error in commit of transaction : " + e);
				e.printStackTrace();
			} 
		}
		if(!checkBetValid){
			for(Integer bet:betStatusKeys){
				betStatus.put(bet, BetStatus.BET_FAILED_DB_CONNECTION_COMMIT);
			}
			return betStatus;
		}
		
		return betStatus;
	}
	
	private String persistBet(Factory writeFactory,Integer bet,Long newGameBetIdForDB, Client client,String userSeatId,Timestamp nowTime,String currentRoundId) {
		try{
			ZzzlrogamebetRecord gameBet = writeFactory.newRecord(Zzzlrogamebet.ZZZLROGAMEBET);
			gameBet.setZlgamebetId(newGameBetIdForDB.toString());
			gameBet.setZlgamebetBettime(nowTime);
			gameBet.setZlgamebetBetcoord(bet);
			gameBet.setZlgamebetGameseatId(userSeatId);
			gameBet.setZlgamebetZlgameroundId(currentRoundId);
			gameBet.setCreateTime(nowTime);
			gameBet.setUpdateTime(nowTime);
			int resp = gameBet.store();
			if(resp != 1){
				logger.error("Could not save bet due to some error!");
				return BetStatus.BET_FAILED_DB_ERROR;
			}
		}catch(Exception e){
			logger.error("Error occoured in saving bet : " + e);
			return BetStatus.BET_FAILED_DB_ERROR;
		}
		return BetStatus.BET_PLACED;
		
	}

	public String activeSettoString(){
		StringBuilder sb = new StringBuilder();
		Iterator<Integer> figureIt = hmFigureTiles.iterator();
		while(figureIt.hasNext()){
			TileVO tempCVO = hmAllTiles.get(figureIt.next());
			sb.append(tempCVO);
		}
		return sb.toString();
	}
	
	public UnionDataObject getMap(int MAP_TYPE){
		UnionDataObject udo = new UnionDataObject();
		try{
		switch(MAP_TYPE){
		case MAP_ACTIVE_TILES:
			Iterator<Integer> activeIt = hmFigureTiles.iterator();
			
			while(activeIt.hasNext()){
				TileVO tempCVO = hmAllTiles.get(activeIt.next());
				udo.append(tempCVO.getCoordNum(),tempCVO.getCoordVO().getCoordFigureId());
			}
			break;
		}
		}catch(Exception e){
			logger.error("Exception occoured in getting map at ZalerioGameBoard:getMap() :" + e);
			e.printStackTrace();
		}
		return udo;
	}
	
	public static Integer calcCoordNum(Integer coordX,Integer coordY,Integer boardY){
		return (coordY * boardY) + coordX;
	}
	
	public static Integer calcCoordX(Integer coordNum,Integer boardX){
		return (((Integer)(coordNum % boardX)) );
	}
	
	public static Integer calcCoordY(Integer coordNum,Integer boardX){
		return ((Integer)(coordNum / boardX) );
	}
	 
	 public void updateClientGameSnapshot(Client client){
	    	super.updateClientGameSnapshot(client);
	    	//sendPreviousBets(client);
	 }
	 
	 
	 
	 public void loadFromDataRow(Record rec) {
			super.loadFromDataRow(rec);
			try{
				if ( rec != null){
					logger.info("Setting up GameInst specific vars in ZalerioGameRoom:loadFromDataRow()");
					if(rec.getValueAsInteger(Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM1) != null ){
						this.setRoomBoardX(rec.getValueAsInteger(Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM1));
					}
					
					if(rec.getValueAsInteger(Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM2) != null){
						this.setRoomBoardY(rec.getValueAsInteger(Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM2));
					}
					
					if(rec.getValueAsInteger(Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM3) != null){
						this.setRoomRoundNoOfBets(rec.getValueAsInteger(Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM3));
					}
					
					if(rec.getValueAsInteger(Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM4) != null){
						this.setRoomTotalRounds(rec.getValueAsInteger(Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM4));
					}
					
					if(rec.getValueAsInteger(Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM5) != null){
						this.setRoomTotalFigs(rec.getValueAsInteger(Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM5));
					}
				}else{
					logger.info("GameInst record is null in ZalerioGameRoom:loadFromDataRow()"); 
				}
			}catch(DataAccessException e){
				logger.error("Exception occoured at ZalerioGameRoom::refreshRoomVars() : " + e);
				e.printStackTrace();
			}					
	}
	
	public void onRoomModuleRequest(RoomEvent evt){
		
		Client client = evt.getClient();
		Message msg = evt.getMessage();
		String CMD = msg.getArg(ZalerioGameServer.EXTERNAL_COMMAND);
		
		logger.info("Message recieved in ZalerioGameRoom : cmd:" + CMD + " for client:" + client.getUserID());
		super.onRoomModuleRequest(evt);
		
		if(CMD.equalsIgnoreCase(ZalerioGameRoomEvent.CMD_ZALERIOGAME_ORIGINALFIGS)){
			try {
				UnionDataObject zalerioActiveMapData = getMap(ZalerioGameRoom.MAP_ACTIVE_TILES);
				logger.info("Zalerio Figure Data :" + zalerioActiveMapData);
				intimateBidder(zalerioActiveMapData, client, ZalerioGameRoomEvent.CMD_ZALERIOGAME_ORIGINALFIGS);
				
			} catch (Exception e) {
				logger.error("onRoomModuleRequest() Exception occoured :" + e);
				e.printStackTrace();
			}
		} else if(CMD.equalsIgnoreCase(ZalerioGameRoomEvent.CMD_ZALERIOGAME_PLACEBETS)){
			UserVO userVO = (UserVO) getUserVO(client.getUserID());
			String betStr = msg.getArg("BI");
			
			HashMap<Integer,String> betsResult = placeBets(client, betStr);
			Set<Integer> betResultIdKeys = betsResult.keySet();
			boolean flag_betSuccess = true;
			for(Integer betResultIdKey:betResultIdKeys){
				String betStatusId = betsResult.get(betResultIdKey);
				if(!betStatusId.equals(BetStatus.BET_PLACED)){
					flag_betSuccess = false;//checking if all the bets are placed successfully
				}
			}
			if(flag_betSuccess && betsResult.size() > 0){
				logger.info("Bets have been placed successfully for Client = " + client.getUserID());
				afterBetPlaced(userVO.getUserSeatId());
				logger.info("After Bets have been processed successfully for Client = " + client.getUserID());
				
				// Syncing all player data for the user
				if(!updatingCurrentGamePlayersInfo) {
					if(loadCurrentGame()) {
						syncAllPlayer();
					}
				}
				
				//Updating Client Info after a user has placed bets
				updateClientGameInfoAsync();
			
			}else{
				logger.info("Bets placement has failed");
				//Bet Failed... inform user
				UnionDataObject betResponseUdo = new UnionDataObject();
				Set<Integer> betStatusKeys = betsResult.keySet();
				for(Integer betStatusKey:betStatusKeys){
					betResponseUdo.append(betStatusKey, betsResult.get(betStatusKey));
				}
				intimateBidder(betResponseUdo, client, ZalerioGameRoomEvent.RES_ZALERIOGAME_FAILEDPLACEBETS);
			}
		} else if(CMD.equalsIgnoreCase(ZalerioGameRoomEvent.CMD_RESIGN_FROM_GAME)){
			logger.info("User " +  client.getUserID() + " wants to resign for Game " + r_ctx.getRoom().getSimpleID());
			
			// Query to update this user seat to Resigned
			boolean resigned = resignPlayer(client.getUserID());
			
			if(resigned) {
				client.sendMessage(ZalerioGameRoomEvent.CMD_RESIGN_FROM_GAME, "1");
				
				// Check after bets placed
				afterBetPlaced(allPlayersMapByUserId.get(client.getUserID()).getSeatId());
				
				// Syncing all player data for the user
				if(!updatingCurrentGamePlayersInfo) {
					if(loadCurrentGame()) {
						syncAllPlayer();
					}
				}
				
				//Updating Client Info after a user has placed bets
				updateClientGameInfoAsync();
				
			} else {
				client.sendMessage(ZalerioGameRoomEvent.CMD_RESIGN_FROM_GAME, "0");
			}
			
		} else if(CMD.equalsIgnoreCase(ZalerioGameRoomEvent.CMD_CLOSE_INVITATION_GAME)){
			logger.info("Invitation close request for Game: " + r_ctx.getRoom().getSimpleID());
			
			// Query to update this user seat to Resigned
			boolean invitationsClosed = closeInvitationsForRoom();
			
			if(invitationsClosed) {
				client.sendMessage(ZalerioGameRoomEvent.CMD_CLOSE_INVITATION_GAME, "1");
				
				// Check after bets placed
				afterBetPlaced(allPlayersMapByUserId.get(client.getUserID()).getSeatId());
				
				// Syncing all player data for the user
				if(!updatingCurrentGamePlayersInfo) {
					if(loadCurrentGame()) {
						syncAllPlayer();
					}
				}
				
				//Updating Client Info after a user has placed bets
				updateClientGameInfoAsync();
				
			} else {
				client.sendMessage(ZalerioGameRoomEvent.CMD_CLOSE_INVITATION_GAME, "0");
			}
		} else if (CMD.equalsIgnoreCase(BaseGameServerEvent.CMD_DECLINE_FROM_GAME)) {
			// Updating user status
			allPlayersMapByUserId.get(client.getUserID()).setGameSeatStatus(DbConstants.GAME_SEAT_STATUS_DECLINED);
			activePlayersCount--;

			// Check after bets placed
			afterBetPlaced(allPlayersMapByUserId.get(client.getUserID()).getSeatId());

			// Syncing all player data for the user
			if (!updatingCurrentGamePlayersInfo) {
				if (loadCurrentGame()) {
					syncAllPlayer();
				}
			}
		}
	}
	
	private boolean resignPlayer(String clientId) {
		
		boolean flag_dbModified = false;

		try {
			Savepoint updateRankSavePoint = roomFactoryWrite.getConnection().setSavepoint("RESIGN_PLAYER");
			
			// Fetch the data already saved in the db
			ZzgameseatRecord zzgameseatRecord = roomFactoryWrite.selectFrom(Zzgameseat.ZZGAMESEAT)
					.where(Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID.equal(clientId))
					.and(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.equal(r_ctx.getRoom().getSimpleID()))
					.fetchOne();
			
			if (zzgameseatRecord != null
					&& (zzgameseatRecord.getZzgameseatStatus().equals(DbConstants.GAME_SEAT_STATUS_ACCEPTED) || zzgameseatRecord.getZzgameseatStatus().equals(
							DbConstants.GAME_SEAT_STATUS_INVITED))) {
				zzgameseatRecord.setZzgameseatStatus(DbConstants.GAME_SEAT_STATUS_RESIGNED);
				zzgameseatRecord.setGameseatStatusUpdateTime(new Timestamp(System.currentTimeMillis()));
				zzgameseatRecord.store();
				flag_dbModified = true;
			}

			if (flag_dbModified) {
				roomFactoryWrite.getConnection().commit();
				allPlayersMapByUserId.get(clientId).setGameSeatStatus(DbConstants.GAME_SEAT_STATUS_RESIGNED);
				activePlayersCount--;
			} else {
				roomFactoryWrite.getConnection().rollback(updateRankSavePoint);
			}
			
		} catch (Exception e) {
			logger.error("Error in ZalerioGameRoom:resignPlayer() : " + e);
		}
		
		logger.info("Updated database t resign player " + clientId);
		return flag_dbModified;
	}
	
	/**
	 * The game creator can choose to close all the invitations,
	 * when this happens the pending invits are automatically canceled
	 * @return
	 */
	private boolean closeInvitationsForRoom() {
		
		boolean flag_dbModified = false;

		try {
			Savepoint savePoint = roomFactoryWrite.getConnection().setSavepoint("CLOSE_INVITATIONS_FOR_ROOM");
			
			// Fetch the data already saved in the db
			Result<ZzgameseatRecord> zzgameseatRecords = roomFactoryWrite.selectFrom(Zzgameseat.ZZGAMESEAT)
					.where(Zzgameseat.ZZGAMESEAT.ZZGAMESEAT_STATUS.equal(DbConstants.GAME_SEAT_STATUS_INVITED))
					.and(Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.equal(getRoomDbId()))
					.fetch();
			
			for (ZzgameseatRecord zzgameseatRecord:zzgameseatRecords) {
				zzgameseatRecord.setZzgameseatStatus(DbConstants.GAME_SEAT_STATUS_CANCELED);
				zzgameseatRecord.setGameseatStatusUpdateTime(new Timestamp(System.currentTimeMillis()));
				zzgameseatRecord.store();
				flag_dbModified = true;
				allPlayersMapByUserId.get(zzgameseatRecord.getGameseatUserId()).setGameSeatStatus(DbConstants.GAME_SEAT_STATUS_CANCELED);
				activePlayersCount--;
			}
			
			if (flag_dbModified) {
				roomFactoryWrite.getConnection().commit();
			} else {
				roomFactoryWrite.getConnection().rollback(savePoint);
			}
			
		} catch (Exception e) {
			logger.error("Error in ZalerioGameRoom:resignPlayer() : " + e);
		}
		
		logger.info("Updated database to close invitations");
		return flag_dbModified;
	}
	
	public void afterBetPlaced(String userSeatId){
		boolean flag_roundChanged = false;
		int roundChangeSuccess = 1;
		
		//Checking for round Completion
		logger.info("isFlag_lastRound : " + isFlag_lastRound());
		while(checkForAllPlayersPlacedBetsInCurrentRound() && roundChangeSuccess == 1 && !isFlag_lastRound()){
			//Round changed by placing all the bets in the current round
			roundChangeSuccess = changeCurrentRound();
			logger.info("roundChangeSuccess : " + roundChangeSuccess);
			afterRoundChanged();
			flag_roundChanged = true;
			logger.info("Round Changed = " + flag_roundChanged);
			
			// Adding code to select new round, this is important as the Thread
			// may not run by then
			selectRoomRound(false);
		}
		
		if(!flag_roundChanged){
			//Round is not finished and hence updating the player bets in the current round only
			updatePlayerBetsInCurrentRound(userSeatId);
			updatePlayersPlayedInRound();
		}
	}
	
	private void afterRoundChangedOnTimeExpire(){
		//Round changed by endTime expiry
		logger.info("Data will be cleared as Round Expired");
		afterRoundChanged();
	}
	
	private void afterRoundChanged(){
		logger.info("Round Changed, Data will be cleared");
		hmCurrentRoundPlayerBets.clear();
		jokerWinners.clear();
		
		loadGameRounds();
		logger.info("New Game Rounds loaded i.e. db updated with new data");
		syncGameRound();
		logger.info("Game Rounds Synced");
		checkForAllPlayersPlacedBetsInCurrentRound();
		logger.info("Checked again for all players bets in current round");
		loadActiveBets();
		syncActiveBets();
		updateAllPlayersBetsInCurrentRound();
		
		//updatePlayersPlayedInRound();
	}
	
	/**
	 * Updating the list of players who have played the game in this round
	 */
	private void updatePlayersPlayedInRound() {
		Set<String> playerSeatIds = hmCurrentRoundPlayerBets.keySet();
		StringBuilder sb = new StringBuilder();
		for(String playerseatId:playerSeatIds){
			PlayerBetCurrentRoundVO playerBetCurrentRoundVO  = hmCurrentRoundPlayerBets.get(playerseatId);
			if(playerBetCurrentRoundVO.getBetCount() > 0) {
				sb.append(playerseatId);
				sb.append(",");
			}
		}
		if(sb.length() > 0) {
			setPublicRoomVar(IZalerioGameRoomVO.ROOM_ROUNDS_PLAYER_PLAYED, sb.substring(0, sb.length() - 1));
		}
	}
	
	private void updateAllPlayersBetsInCurrentRound(){
		logger.info("Update All Players Bet in the Current Round");
		try{
			Set<String> playerSeatIds = hmCurrentRoundPlayerBets.keySet();
			for(String playerseatId:playerSeatIds){
				PlayerBetCurrentRoundVO playerBetCurrentRoundVO  = hmCurrentRoundPlayerBets.get(playerseatId);
				Client currentClient = findUserBySeatId(playerseatId);
				logger.info("Current Client : " + currentClient);
				logger.info("Player Bet Current Round VO : " + playerBetCurrentRoundVO.isVoChanged());
				if(currentClient !=null && playerBetCurrentRoundVO.isVoChanged() ){
					//logger.info("Sending each player his own current status!");
					
					intimateBidder(playerBetCurrentRoundVO.toUDO(getRoomCurrentRoundId()),currentClient,ZalerioGameRoomEvent.RES_ZALERIOGAME_CHANGEBETS);
					playerBetCurrentRoundVO.setVoChangeFalse();
					logger.info("VO after Game Round : " + playerBetCurrentRoundVO);
				}
			}
		}catch(Exception e){
			logger.error("Error in ZalerioGameRoom:updateAllPlayersBetsInCurrentRound() : " + e);
		}
	}
	
	private void updatePlayerBetsInCurrentRound(String userSeatId){
		try{
			if(hmCurrentRoundPlayerBets.containsKey(userSeatId)){
				PlayerBetCurrentRoundVO playerBetCurrentRoundVO  = hmCurrentRoundPlayerBets.get(userSeatId);
				Client currentClient = findUserBySeatId(playerBetCurrentRoundVO.getPlayerSeatId());
				if(currentClient !=null && playerBetCurrentRoundVO.isVoChanged() ){
					//logger.info("Sending each player his own current status!");
					
					intimateBidder(playerBetCurrentRoundVO.toUDO(getRoomCurrentRoundId()),currentClient,ZalerioGameRoomEvent.RES_ZALERIOGAME_CHANGEBETS);
					playerBetCurrentRoundVO.setVoChangeFalse();
				}
			}
		}catch(Exception e){
			logger.error("Error in ZalerioGameRoom:updatePlayerBetsInCurrentRound() : " + e);
		}
	}
	
	private boolean checkForAllPlayersPlacedBetsInCurrentRound(){
		boolean flag_allPlayersPlacedBetsForCurrentRound = false;
		logger.info("Fetch all bets placed for the current round = " + getRoomCurrentRoundId());
		Select<Record> qCheckForBetsByPlayers = roomFactory.select(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID,Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ID,Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_BETCOORD,Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ZLGAMEROUND_ID)
				.from(Zzgameseat.ZZGAMESEAT)
				.leftOuterJoin(Zzzlrogamebet.ZZZLROGAMEBET)
					.on(
							Zzgameseat.ZZGAMESEAT.GAMESEAT_ID.equal(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_GAMESEAT_ID)
							.and(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ZLGAMEROUND_ID.equal(getRoomCurrentRoundId()))
					)
				.where(
						Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.equal(r_ctx.getRoom().getSimpleID())
				);
		try{
			
			Result<Record> results = qCheckForBetsByPlayers.fetch();
			if(results != null){
				logger.info("Some bets are placed");
				boolean flag_allBetsPlaced = true;
				for(Record rec:results){
					String playerSeatId = rec.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID);
					PlayerBetCurrentRoundVO playerBetCurrentRoundVO ;
					logger.info("Player Seat Id = " +  playerSeatId);
					if(playerSeatId != null){
						if(hmCurrentRoundPlayerBets.containsKey(playerSeatId)){
							logger.info("Player Seat already present");
							playerBetCurrentRoundVO = hmCurrentRoundPlayerBets.get(playerSeatId);
						}else{
							logger.info("Creating new one");
							playerBetCurrentRoundVO = new PlayerBetCurrentRoundVO(playerSeatId);
							hmCurrentRoundPlayerBets.put(rec.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID),playerBetCurrentRoundVO);
						}
						logger.info("Going to update playerBetCurrentRoundVO");
						if(rec.getValueAsString(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ID) == null){
							logger.info("Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ID is null");
							
							//If the player is not resigned and does not have any bets placed
							logger.info("Game Seat Status (Resigned = 4) : " + allPlayersMap.get(playerSeatId).getGameSeatStatus());
							logger.info("Active Player Count : " + activePlayersCount);
							if ((allPlayersMap.get(playerSeatId).getGameSeatStatus().equals(PlayerVO.GAME_SEAT_STATUS_ACCEPTED_CODE) || allPlayersMap
									.get(playerSeatId).getGameSeatStatus().equals(PlayerVO.GAME_SEAT_STATUS_INVITED_CODE))
									&& activePlayersCount > 1) {
								flag_allBetsPlaced = false;
							}
						}else{
							logger.info("Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ID is not null");
							Integer betCoord = rec.getValueAsInteger(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_BETCOORD);
							boolean isFigure = hmFigureTiles.contains(betCoord);
							logger.info("betCoord - " + betCoord + " - isFigure:" + isFigure);
							playerBetCurrentRoundVO.setPlayerBet(betCoord, isFigure,rec.getValueAsString(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ZLGAMEROUND_ID));
						}
					}
				}
				if(flag_allBetsPlaced && (getTotalSeats() == hmCurrentRoundPlayerBets.size())){
					flag_allPlayersPlacedBetsForCurrentRound = true;
				}
			}
			
		}catch(Exception e){
			logger.error("Error in ZalerioGameRoom:checkForAllPlayersPlacedBetsInCurrentRound() : ", e);
			e.printStackTrace();
			flag_allPlayersPlacedBetsForCurrentRound = false;
		}
		logger.info("flag_allPlayersPlacedBetsForCurrentRound : " + flag_allPlayersPlacedBetsForCurrentRound);
		return flag_allPlayersPlacedBetsForCurrentRound;
	}
	
	/**
	 * CAUTION : Will not check for any pre-condition to change the round.. 
	 * Will change the currentRound to nextComingRound
	 * Database is updated with the new timestamp etc
	 */
	private int changeCurrentRound() {
		//Processing Game Round
		this.processingGameRound = true;
		
		int flag_changeSuccess = 0;
		RoundVO currentRoundVO = hmRoomRounds.get(getRoomCurrentRoundId());
		logger.debug("Changing current round - [currentRoundVO:" + currentRoundVO + "]");
		try{
			Savepoint changeRoundSavePoint = roomFactoryWrite.getConnection().setSavepoint("CHANGE_ROUND");
			Result<ZzzlrogameroundRecord> rcGameRoundRecords = roomFactoryWrite.selectFrom(Zzzlrogameround.ZZZLROGAMEROUND)
					.where(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_GAMEINST_ID.equal(r_ctx.getRoom().getSimpleID()))
					.orderBy(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ROUNDNAME.cast(Long.class).asc())
					.fetch();
			ZzgameinstRecord zzgameinstRecord = roomFactoryWrite.selectFrom(Zzgameinst.ZZGAMEINST)
					.where(Zzgameinst.ZZGAMEINST.GAMEINST_ID.equal(r_ctx.getRoom().getSimpleID()))
					.fetchOne();
				
				/*select(OloZlroGameRound.OLO_ZLRO_GAME_ROUND.ZLRO_GAME_ROUND_ID,OloZlroGameRound.OLO_ZLRO_GAME_ROUND.TIME_START,OloZlroGameRound.OLO_ZLRO_GAME_ROUND.TIME_END)
				.from(OloZlroGameRound.OLO_ZLRO_GAME_ROUND)
				.where(OloZlroGameRound.OLO_ZLRO_GAME_ROUND.GAME_INST_ID.equal(r_ctx.getRoom().getSimpleID()))
				.orderBy(OloZlroGameRound.OLO_ZLRO_GAME_ROUND.TIME_START).fetch();
				*/
			
		
			if(rcGameRoundRecords != null){
				boolean flag_currentRoundFound = false;
				Timestamp nowTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
				Timestamp lastTimeEndstamp = null;
				boolean flag_dbModified = false;
				long roundCtr = 0;
				for(ZzzlrogameroundRecord rec:rcGameRoundRecords){
					roundCtr++;
					if(rec.getValueAsString(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ROUNDNAME).equals(currentRoundVO.getRoundName()) && (rcGameRoundRecords.size() == roundCtr) ){

						if (activePlayersCount > 1) {
							// If active player count is more than 1 only then
							// the game will continue or else the game shall end
							setFlag_lastRound(1);
						} else {
							// TODO: Need to check here if the game is a
							// completed game or a canceled one based on the
							// status of the players
							// So if more than one player status is accpeted,
							// only then the game actually completes
							int playerWhoPlayedCount = 0;
							List<PlayerVO> players = currentGame.getPlayers();
							for (PlayerVO player : players) {
								if (player.getGameSeatStatus().equals(PlayerVO.GAME_SEAT_STATUS_ACCEPTED_CODE)
										|| player.getGameSeatStatus().equals(PlayerVO.GAME_SEAT_STATUS_RESIGNED_CODE)) {
									playerWhoPlayedCount++;
								}
							}

							if (playerWhoPlayedCount > 1) {
								// The game actually finished
								// If 1 player is left then the last round flag
								// is set to true i.e. game is finish
								setFlag_lastRound(1);
							} else {
								// The game needs to be canceled
								setFlag_lastRound(2);
							}
						}
						
						rec.setZlgameroundTimeend(nowTimestamp);
						rec.store();
						
						//Updating Actual Game End Time
						zzgameinstRecord.setGameinstEndtime(nowTimestamp);
						zzgameinstRecord.store();
						flag_dbModified = true;
						logger.info("This is the last round! Not changing anything");
						break;
					} else {
						if(rec.getZlgameroundId().equalsIgnoreCase(currentRoundVO.getRoundId())){
							flag_currentRoundFound = true;
							
							//change EndTime To Now
							rec.setZlgameroundTimeend(nowTimestamp);
							lastTimeEndstamp = nowTimestamp;
						}
						if(flag_currentRoundFound){
							//for rounds after the current round
							if(!(rec.getZlgameroundId().equalsIgnoreCase(currentRoundVO.getRoundId()))){
								Long timeInterval = rec.getValueAsTimestamp(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_TIMEEND).getTime() - rec.getValueAsTimestamp(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_TIMESTART).getTime();
								
								rec.setZlgameroundTimestart(lastTimeEndstamp);
								
								lastTimeEndstamp = new Timestamp(lastTimeEndstamp.getTime() + timeInterval);
								rec.setZlgameroundTimeend(lastTimeEndstamp);
								
								//Updating Actual Game End Time
								if(rcGameRoundRecords.size() == roundCtr) {
									//Last Round
									zzgameinstRecord.setGameinstEndtime(lastTimeEndstamp);
									zzgameinstRecord.store();
								}
							}
							//update the current round in DB
							rec.store();
							flag_dbModified = true;
						}
					}
				}
				
				if(flag_dbModified){
					roomFactoryWrite.getConnection().commit();
					flag_changeSuccess = 1;
				}else{
					roomFactoryWrite.getConnection().rollback(changeRoundSavePoint);
					flag_changeSuccess = 0;
				}
			}else{
				flag_changeSuccess = 0;
				roomFactoryWrite.getConnection().rollback(changeRoundSavePoint);
				this.processingGameRound = false;
				throw new Exception("Rooms not found! Using query ");
			}
		}catch(Exception e){
			flag_changeSuccess = 0;
			logger.error("Error in ZalerioGameRoom:changeCurrentRound() : " + e);
		}
		this.processingGameRound = false;
		return flag_changeSuccess;
		//Processing Game Round
	}

	public void setRoomVars(){
		logger.info("Setting up roomVars in ZalerioGameRoom");
		super.setRoomVars();
		setPublicRoomVar(IZalerioGameRoomVO.ROOM_BOARD_XY, this.getRoomBoardX() + ":" + this.getRoomBoardY());
		setPublicRoomVar(IZalerioGameRoomVO.ROOM_BOARDVARS, this.getRoomBoardVars());
		/*
		setPublicRoomVar(IZalerioGameRoomVO.ROOM_CURRENTROUND, this.getRoomCurrentRoundId());
		setPublicRoomVar(IZalerioGameRoomVO.ROOM_ROUND_ENDTIME, this.getRoomRoundEndtime());
		setPublicRoomVar(IZalerioGameRoomVO.ROOM_ROUND_ENDTIMEINMS, this.getRoomRoundEndTimeInMs());
		*/
		setPublicRoomVar(IZalerioGameRoomVO.ROOM_TOTALFIGS, this.getRoomTotalFigs());
		setPublicRoomVar(IZalerioGameRoomVO.ROOM_TOTALROUNDS, this.getRoomTotalRounds());
		setPublicRoomVar(IZalerioGameRoomVO.ROOM_ROUND_NOOFBETS, this.getRoomRoundNoOfBets());

		//setPublicRoomVar(IBidGameRoomVO.ROOM_WINNINGBID_AMT,this.getWinningBidAmt());
	}

	public Integer getRoomTotalRounds() {
		return roomTotalRounds;
	}

	public void setRoomTotalRounds(Integer roomTotalRounds) {
		this.roomTotalRounds = roomTotalRounds;
	}

	public String getRoomCurrentRoundId() {
		if(roomCurrentRound != null){
			return roomCurrentRound.getRoundId();
		}
		return null;
	}

	public void setRoomCurrentRound(RoundVO roundVO) {
		logger.info("Current Round Changed to : " + roundVO.getRoundName() + " - " + roundVO.getRoundId());
		this.roomCurrentRound = roundVO;
	}

	public Timestamp getRoomRoundEndtime() {
		if(roomCurrentRound != null){
			return roomCurrentRound.getEndTime();
		}
		return null;
	}
	
	public Long getRoomRoundEndTimeInMs() {
		if(roomCurrentRound != null){
			return roomCurrentRound.getEndTimeTTL();
		}
		return null;
	}

	public Integer getRoomBoardX() {
		return roomBoardX;
	}

	public void setRoomBoardX(Integer roomBoardX) {
		this.roomBoardX = roomBoardX;
	}

	public boolean isFlag_lastRound() {
		return flag_lastRound;
	}

	public void setFlag_lastRound(int lastRoundStatus) {
		if(lastRoundStatus == 1) {
			// Game Finished
			this.flag_lastRound = true;
			setPublicRoomVar(IZalerioGameRoomVO.FINAL_ROUND_COMPLETE, 1);
		} else if(lastRoundStatus == 2) {
			// Game Canceled
			this.flag_lastRound = true;
			setPublicRoomVar(IZalerioGameRoomVO.FINAL_ROUND_COMPLETE, 2);
		} else {
			this.flag_lastRound = false;
		}
	}

	public Integer getRoomBoardY() {
		return roomBoardY;
	}

	public void setRoomBoardY(Integer roomBoardY) {
		this.roomBoardY = roomBoardY;
	}

	public Integer getRoomTotalFigs() {
		return roomTotalFigs;
	}

	public void setRoomTotalFigs(Integer roomTotalFigs) {
		this.roomTotalFigs = roomTotalFigs;
	}
	
	public String getRoomBoardVars(){
		UnionDataObject allTilesUDO = new UnionDataObject();
		if(hmAllTiles.size() > 0){
			Set<Integer> allTileKeys = hmAllTiles.keySet();
			for(Integer allTileKey:allTileKeys){
				allTilesUDO.append(allTileKey, hmAllTiles.get(allTileKey).toUDO());
			}
		}
		return allTilesUDO.toString();
	}
	 
	
	public Integer getRoomRoundNoOfBets() {
		return roomRoundNoOfBets;
	}

	public void setRoomRoundNoOfBets(Integer roomRoundNoOfBets) {
		this.roomRoundNoOfBets = roomRoundNoOfBets;
	}
	
	public void refreshRoomVars() {
		super.refreshRoomVars();
	}
	
	public void updateRoomVars() {
		super.updateRoomVars();
		if(this.flag_roomValChanged){
			if(flag_boardCoordsMap){
				//setPublicRoomVar(ROOM_B, val)
				flag_boardCoordsMap = false;
			}
			if(flag_roomCurrentRound){
				setPublicRoomVar(ROOM_CURRENTROUND, getRoomCurrentRoundId());
				flag_roomCurrentRound = false;
			}
			if(flag_roomRoundEndtime){
				setPublicRoomVar(ROOM_ROUND_ENDTIME, getRoomRoundEndtime());
				flag_roomRoundEndtime = false;
			}
			if(flag_roomRoundEndtimeInMs){
				setPublicRoomVar(ROOM_ROUND_ENDTIMEINMS, getRoomRoundEndTimeInMs());
				flag_roomRoundEndtimeInMs = false;
			}
			flag_roomValChanged = false;
			
		}
	}
	
	
	
	public void shutdown() {
		syncTTLTimerPerSec.cancel();
		super.shutdown();
	}
	 
	
	/**
	 * Inner Class for maintaining Coordinates of Zalerio Game Board tiles
	 * @author Shekhar
	 *
	 */
	
	public class CoordVO {
		public static final String RES_UDO_COORD_X = "CX";
		public static final String RES_UDO_COORD_Y = "CY";
		public static final String RES_UDO_COORD_COUNT = "BC";
		public static final String RES_UDO_COORD_NUM = "CN";
		public static final String RES_UDO_COORD_FIGURE_ID = "CF";
		private Integer coordX;
		private Integer coordY;
		private Integer coordCount;
		private Integer coordNum;
		private String coordFigureId;
		
		CoordVO(Integer coordNum) throws ZalerioBoardCoordinateOutOfBoundException{
			if(coordNum > 0 && coordNum < (roomBoardX* roomBoardY)){
				setCoordX(((Integer)(coordNum % roomBoardX)) );
				setCoordY((Integer)(coordNum / roomBoardX) );
				setCoordNum(coordNum);
				setCoordCount(-1);
			}else{
				throw new ZalerioBoardCoordinateOutOfBoundException(roomBoardX, roomBoardY, coordNum);
			}
			
		}
		
		CoordVO(Integer coordX,Integer coordY) throws ZalerioBoardCoordinateOutOfBoundException{
			if(coordX < roomBoardX && coordY < roomBoardY && coordX > -1 && coordY > -1){
				setCoordX(coordX);
				setCoordY(coordY);
				setCoordNum((coordY * roomBoardY) + coordX);
				setCoordCount(-1);
			}else{
				throw new ZalerioBoardCoordinateOutOfBoundException(roomBoardX, roomBoardY, coordX,coordY);
			}
		}

		public Integer getCoordX() {
			return coordX;
		}

		public void setCoordX(Integer coordX) {
			this.coordX = coordX;
		}

		public Integer getCoordY() {
			return coordY;
		}

		public void setCoordY(Integer coordY) {
			this.coordY = coordY;
		}

		public Integer getCoordCount() {
			return coordCount;
		}

		public void setCoordCount(Integer coordCount) {
			this.coordCount = coordCount;
		}

		public Integer getCoordNum() {
			return coordNum;
		}

		public void setCoordNum(Integer coordNum) {
			this.coordNum = coordNum;
		}
		
		public String getCoordFigureId() {
			return coordFigureId;
		}

		public void setCoordFigureId(String coordFigureId) {
			this.coordFigureId = coordFigureId;
		}

		public String toString(){
			return "[X:" + coordX + ",Y:" + coordY + ",coordCount:" + coordCount + ",coordNum:" + coordNum + "]";
		}
		
		public UnionDataObject toUDO(){
			UnionDataObject udo = new UnionDataObject();
			
			udo.append(CoordVO.RES_UDO_COORD_NUM, this.getCoordNum());
			udo.append(CoordVO.RES_UDO_COORD_X, this.getCoordX());
			udo.append(CoordVO.RES_UDO_COORD_Y, this.getCoordY());
			udo.append(CoordVO.RES_UDO_COORD_COUNT, this.getCoordCount());
			udo.append(RES_UDO_COORD_FIGURE_ID, this.getCoordFigureId());
			return udo;
		}
	}
	
	
	/**
	 * Inner class for Tiles
	 */
	public class TileVO{
		public static final String PLAYER_BETS_IN_ROUND = "PR";
		public static final String COORD_VO = "CV";
		public static final String BET_WINNER = "BW";
		private CoordVO cvo;
		private HashMap<String,String> playerBets;
		private List<String> betWinner;
		private boolean flag_changed = true;
		
		TileVO(int coordX, int coordY) throws ZalerioBoardCoordinateOutOfBoundException {
			cvo = new CoordVO(coordX, coordY);
			playerBets = new HashMap<String, String>();
			betWinner = null;
		}
		
		public void setPlayerBetOnTile(String playerGameSeatId,String roundId){
			playerBets.put(playerGameSeatId, roundId);
			if(cvo.coordCount != playerBets.size()){
				cvo.coordCount = playerBets.size();
				flag_changed = true;
			}
		}
		
		public CoordVO getCoordVO(){
			return this.cvo;
		}
		
		public boolean isChanged(){
			return this.flag_changed;
		}
		
		public Integer getCoordNum(){
			return cvo.getCoordNum();
		}
		
		public List<String> getBetWinner() {
			return betWinner;
		}

		public void setBetWinner(List<String> betWinner) {
			this.betWinner = betWinner;
		}

		public UnionDataObject toUDO(){
			UnionDataObject playerBetUdo = new UnionDataObject();
			Set<String> playerBetSeatIds = playerBets.keySet();
			for(String playerBetSeatId:playerBetSeatIds){
				playerBetUdo.append(playerBetSeatId, playerBets.get(playerBetSeatId));
			}
			
			UnionDataObject tileUdo = new UnionDataObject();
			tileUdo.append(PLAYER_BETS_IN_ROUND, playerBetUdo);
			tileUdo.append(CoordVO.RES_UDO_COORD_COUNT, cvo.getCoordCount());
			tileUdo.append(CoordVO.RES_UDO_COORD_FIGURE_ID, cvo.getCoordFigureId());
			if(betWinner != null) {
				StringBuilder sb = new StringBuilder();
				for (Iterator<String> betWinners = betWinner.iterator(); betWinners.hasNext();) {
					sb.append(betWinners.next());
					sb.append(",");
				}
				if(sb.length() > 0) {
					tileUdo.append(BET_WINNER, sb.substring(0, sb.length() - 1));
				}
				
			}
			return tileUdo;
		}
	}
	
	private class FigureDetailVO{
		private String figureId = "-";
		private long figureTotalCount = 0;
		private long figureTotalScore = 0;
		private long figureType = FIGURE_TYPE_NUMERIC;
		
		
		public String getFigureId() {
			return figureId;
		}
		public void setFigureId(String figureId) {
			this.figureId = figureId;
		}
		
		public long getFigureTotalCount() {
			return figureTotalCount;
		}
		
		public void setFigureTotalCount(long figureTotalCount) {
			this.figureTotalCount = figureTotalCount;
		}
		
		public long getFigureTotalScore() {
			return figureTotalScore;
		}
		
		public void setFigureTotalScore(long figureTotalScore) {
			this.figureTotalScore = figureTotalScore;
		}

		public long getFigureType() {
			return figureType;
		}
		
		public void setFigureType(long figureType) {
			this.figureType = figureType;
		}
		
		
	}
	
	public interface BetStatus{
		public static final String BET_FAILED_MAX_BET_EXCEED = "FB";
		public static final String BET_FAILED_DB_CONNECTION_COMMIT = "FM";
		public static final String BET_FAILED_DB_CONNECTION = "FN";
		public static final String BET_FAILED_DB_ERROR = "FD";
		public static final String BET_FAILED_ALREADY_PLACED = "FP";
		public static final String BET_FAILED_ROUND_PLAYED = "FR";
		public static final String BET_FAILED_BOUNDARY_CONDITION = "FC";
		public static final String BET_PLACED = "SP"; 
		public static final String BET_FAILED = "FA"; 
	}
	
	
	/**
	 * Timer task to check if the current round has expired or not
	 */
	private void  runTTLSyncTimerPerSec(){
		
		
		//taskHandle = TaskRunner.this.sfs.getTaskScheduler().scheduleAtFixedRate(new TaskRunner(), 0, 1, TimeUnit.SECONDS);
		TimerTask syncTTLTask = new TimerTask() {
			
			@Override
			public void run() {
				logger.info("Task running to check if the current round has expired or not");
				if(!processingGameRound && !flag_gameover) {
					logger.info("Checking if game has expired or not");
				try{
					long ttl;
					RoundVO currentRound = hmRoomRounds.get(getRoomCurrentRoundId());
					if(currentRound != null ){
						ttl = currentRound.getEndTimeTTL();
					}else{
						logger.info("Current Round not set!");
						ttl = -1;
					}
					
					if(ttl<0){
						selectRoomRound(false);
						
					}
				}catch(Exception e){
					logger.error("Error in ZalerionGameRoom:runTTLSyncTimerPerSec() : " + e);
					e.printStackTrace();
				}
				} else {
					logger.info("Game Round is getting processed");
				}
			}
		};
		syncTTLTimerPerSec.scheduleAtFixedRate(syncTTLTask, 0, 1000);
	}
	
	private void  runSyncAllPlayerPerSec(){
		
		TimerTask syncAllPlayersTask = new TimerTask() {
			
			@Override
			public void run() {
				// Syncing all player data for the user
				if(!updatingCurrentGamePlayersInfo) {
					if(loadCurrentGame()) {
						syncAllPlayer();
					}
				}
			}

		};
		syncTTLTimerPerSec.scheduleAtFixedRate(syncAllPlayersTask, 0, 2000);
	}
	
	private void  runSyncClientData(){
		
		TimerTask syncClientInfo = new TimerTask() {
			
			@Override
			public void run() {
				updateClientGameInfo();
			}

		};
		syncTTLTimerPerSec.scheduleAtFixedRate(syncClientInfo, 0, 5000);
	}
	
	private void updateClientGameInfoAsync() {
		//Updating Client Info after a user has placed bets
		Runnable updateClientGameInfoInParallel = new Runnable(){
			public void run(){
				updateClientGameInfo();
			}
		};
		updateClientGameInfoInParallel.run();
	}
	
	private void updateClientGameInfo() {
		if(!updatingGameInfo) {
			updatingGameInfo = true;
			logger.info("Running Client Update Code");
			List<PlayerVO> players = currentGame.getPlayers();
			for(PlayerVO player: players) {
				if(r_ctx.getServer().isAccountOnline(player.getUserId())) {
					try {
						Client client = r_ctx.getServer().getClientByUserID(player.getUserId());
						logger.info("Updating Information for : " + player.getUserId());
						ClientHelper.updateClientGameInfo(r_ctx.getServer(), client, player.getUserId());
					} catch (ClientNotFoundException e) {
						//Not a serious error
						logger.debug("Client not found");
					}
				}
			}
			/*
			Set<Client> clients = r_ctx.getRoom().getClients();
			logger.info("Number of Clients: " + clients.size());
			for (Iterator<Client> iterator = clients.iterator(); iterator.hasNext();) {
				Client client = iterator.next();
				logger.info("Updating for client : " + client.getUserID());
				ClientHelper.updateClientGameInfo(r_ctx.getServer(), client, client.getUserID());
			}
			*/
			updatingGameInfo = false;
		}
	}

}
