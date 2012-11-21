package net.user1.union.zz.zalerioGame.lib.vo;

import java.sql.Timestamp;
import java.util.Calendar;

import net.user1.union.zz.common.UnionDataObject;

public class RoundVO {
	private static final String ROUND_ENDTIMETTL = "EM";
	public static final String ROUND_ID = "RI";
	public static final String ROUND_NAME = "RN";
	public static final String ROUND_STARTTIME = "ST";
	public static final String ROUND_ENDTIME = "ET";
	
	private String roundId;
	private String roundName;
	private Timestamp startTime;
	private Timestamp endTime;
	private boolean voChanged = true;
	private boolean lastRound = false;
	
	public RoundVO(String roundId, String roundName, Timestamp startTime, Timestamp endTime) {
		this.roundId = roundId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roundName = roundName;
	}
	
	public boolean isVoChanged() {
		return voChanged;
	}

	public void setVoChangedFalse() {
		this.voChanged = false;
	}

	public String getRoundId() {
		return roundId;
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}
	
	public Timestamp getEndTime() {
		return endTime;
	}
	
	public Long getEndTimeTTL() {
		return this.endTime.getTime() - Calendar.getInstance().getTimeInMillis() ;
	}
	
	/*
	public Long getStartTimeInMS() {
		return this.startTime.getTime() - Calendar.getInstance().getTimeInMillis() ;
	}
	*/

	public String getRoundName() {
		return roundName;
	}
	
	public boolean isLastRound() {
		return lastRound;
	}

	public void setLastRound(boolean lastRound) {
		this.lastRound = lastRound;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[ROUND_ID:" + roundId + ",");
		sb.append("ROUND_NAME:" + roundName + ",");
		sb.append("START_TIME:" + startTime.getTime() + ",");
		sb.append("END_TIME:" + endTime.getTime() + "]");
		return sb.toString();
	}
	public UnionDataObject toUDO() {
		UnionDataObject udo = new UnionDataObject();
		
		udo.append(RoundVO.ROUND_ID, roundId);
		udo.append(RoundVO.ROUND_NAME, roundName);
		udo.append(RoundVO.ROUND_STARTTIME, startTime);
		udo.append(RoundVO.ROUND_ENDTIME, endTime);
		udo.append(RoundVO.ROUND_ENDTIMETTL, getEndTimeTTL());
		return udo;
	}

	public void setData(String roundName, Timestamp startTime,
			Timestamp endTime) {
		if(this.roundName != roundName){
			this.roundName = roundName;
			this.voChanged = true;
		}
		if(!this.startTime.equals(startTime)){
			this.startTime = startTime;
			this.voChanged = true;
		}
		if(!this.endTime.equals(endTime)){
			this.endTime = endTime;
			this.voChanged = true;
		}
		
	}

	
	
	
}
