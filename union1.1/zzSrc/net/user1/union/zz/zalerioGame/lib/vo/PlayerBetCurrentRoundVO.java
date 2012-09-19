package net.user1.union.zz.zalerioGame.lib.vo;

import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import net.user1.union.zz.common.UnionDataObject;

public class PlayerBetCurrentRoundVO {
	private static Logger logger = Logger.getLogger(PlayerBetCurrentRoundVO.class);
	
	public static final String PLAYER_BETS = "PB";
	public static final String PLAYER_BET_COUNT = "BC";
	public static final String PLAYER_SEAT_ID = "PS";
	public static final String PLAYER_CURRENT_ROUND_ID = "PR";
			
	private int betCount = 0;
	private String currentRoundId = "";
	private String playerSeatId;
	private HashMap<Integer,Integer> playerBets;
	private boolean voChanged = true;
	
	
	public PlayerBetCurrentRoundVO(String playerSeatId) {
		this.playerSeatId = playerSeatId;
		this.playerBets = new HashMap<Integer, Integer>();
	}
	public String getPlayerSeatId() {
		return playerSeatId;
	}
	
	/*
	public HashMap<Integer,Integer> getPlayerBets() {
		return playerBets;
	}
	*/

	public void setPlayerBet(Integer coordNum,boolean isFigure,String currentRoundId){
		logger.debug("Old Current Round Id = " + this.currentRoundId + " : New Round Id = " + currentRoundId);
		if(!this.currentRoundId.equalsIgnoreCase(currentRoundId)){
			logger.debug("Rounds are different");
			this.playerBets.clear();
			this.currentRoundId = currentRoundId;
			this.voChanged = true;
		}
		logger.debug("Old Player Bet = " + this.playerBets + " : New Player Bet = " + coordNum);
		if(!this.playerBets.containsKey(coordNum)){
			logger.debug("Player Bets is different");
			this.playerBets.put(coordNum,isFigure==true?1:0);
			this.voChanged = true;
		}
		betCount = this.playerBets.size();
	}
	
	public boolean isVoChanged() {
		return voChanged;
	}
	
	public void setVoChangeFalse(){
		this.voChanged = false;
	}
	
	public UnionDataObject toUDO(String currentRoundId){
		UnionDataObject playerBetUdo = new UnionDataObject();
		Set<Integer> playerBetKeys = playerBets.keySet();
		for(Integer coordNum:playerBetKeys){
			playerBetUdo.append(coordNum, playerBets.get(coordNum));
		}
		
		UnionDataObject udo = new UnionDataObject();
		udo.append(PLAYER_BETS, playerBetUdo);
		udo.append(PLAYER_BET_COUNT, this.betCount);
		udo.append(PLAYER_SEAT_ID, this.playerSeatId);
		udo.append(PLAYER_CURRENT_ROUND_ID, currentRoundId);
		return udo;
		
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		Set<Integer> playerBetKeys = playerBets.keySet();
		sb.append("PLAYER_BETS :[");
		for(Integer coordNum:playerBetKeys){
			sb.append("[CV:" + coordNum + "," + playerBets.get(coordNum) + "]");
		}
		sb.append("]");
		sb.append(",PLAYER_BET_COUNT:" + this.betCount );
		sb.append(",PLAYER_SEAT_ID:" + this.playerSeatId);
		sb.append(",PLAYER_CURRENT_ROUND_ID:" + currentRoundId + "]");
		return sb.toString();
	}
	
	// Used to get Bet Count
	public int getBetCount() {
		return this.betCount;
	}
	
}
