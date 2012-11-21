package net.user1.union.zz.zalerioGame.lib.vo;

import java.sql.Timestamp;

import net.user1.union.zz.common.UnionDataObject;
import net.user1.union.zz.common.model.tables.Zzgameseat;
import net.user1.union.zz.common.model.tables.Zzzlrogamebet;
import net.user1.union.zz.common.model.tables.Zzzlrogameround;

import org.apache.log4j.Logger;
import org.jooq.Record;

public class BetVO {
	private static Logger logger = Logger.getLogger(BetVO.class);
	
	public static String BET_ID = "BI";
	public static String BET_ROUND_ID = "BR";
	public static String BET_SEAT_ID = "BS";
	public static String BET_TIME = "BT";
	public static String BET_COORD = "BC";
	
	private String betId;
	private String roundId;
	private String gameSeatId;
	private Timestamp betTime;
	private Integer betCoord;
	
	public String getBetId() {
		return betId;
	}
	public void setBetId(String betId) {
		this.betId = betId;
	}
	public String getRoundId() {
		return roundId;
	}
	public void setRoundId(String roundId) {
		this.roundId = roundId;
	}
	public String getGameSeatId() {
		return gameSeatId;
	}
	public void setGameSeatId(String gameSeatId) {
		this.gameSeatId = gameSeatId;
	}
	public Timestamp getBetTime() {
		return betTime;
	}
	public void setBetTime(Timestamp betTime) {
		this.betTime = betTime;
	}
	
	
	public UnionDataObject getAO() {
    	UnionDataObject sfObj = new UnionDataObject();
        
        sfObj.append(BetVO.BET_ID, this.getBetId());
        sfObj.append(BetVO.BET_COORD, this.getBetCoord());
        sfObj.append(BetVO.BET_ROUND_ID, this.getRoundId());
        /*
        SFSObject sfBidHistoryArr=null;
        if(this.sfBidHistory!=null && this.sfBidHistory.size()>0){
            sfBidHistoryArr= new SFSObject();
            for(int i=0;i<this.sfBidHistory.size();i++){
                ISFSObject sfBidHistoryObj=this.sfBidHistory.getSFSObject(i);
                sfBidHistoryArr.putSFSObject(sfBidHistoryObj.getUtfString(BidHistoryVO.BIDHISTORY_ID).toString(), sfBidHistoryObj);
            }
            sfObj.putSFSObject(BidVO.BID_HISTORY, sfBidHistoryArr);
        }else{
            sfObj.putNull(BidVO.BID_HISTORY);
        }
        */
        return sfObj;
    }
	
	
	/**
     * Will update values from the datarow into the current object
     * @param row the sql should contain tbl_bid and tbl_seat in a join
     */
    public void loadfromDataRow(Record rec,Integer roomBoardY) {
        if (rec != null) {
        	try{
	            this.setBetId(rec.getValueAsString(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ID));
	            this.setBetCoord(rec.getValueAsInteger(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_BETCOORD));
	            
	            this.setBetTime(rec.getValueAsTimestamp(Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_BETTIME));
	            this.setRoundId(rec.getValueAsString(Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID));
	            this.setGameSeatId(rec.getValueAsString(Zzgameseat.ZZGAMESEAT.GAMESEAT_ID));
	            
            }catch(Exception e){
            	logger.error("loadfromDataRow() Exception occoured :" + e);
            	e.printStackTrace();
            }
        }
	
    }
	public Integer getBetCoord() {
		return betCoord;
	}
	public void setBetCoord(Integer betCoord) {
		this.betCoord = betCoord;
	}
}
