package net.user1.union.zz.baseGame.lib.vo;


public class BaseScoreCardVO {
	
	/*
	OloScoreCardRecord scoreCard;
	
	public static final String REQ_SCORECARD_ID="SI";
	public static final String RES_SCORECARD_ID = "SI";
	public static final String RES_SCORECARD_STARTTIME = "ST";
	public static final String RES_SCORECARD_ENDTIME = "ET";
	public static final String RES_SCORECARD_SCORE = "SC";
	public static final String REQ_SCORECARD_SCORE = "SC";
	

	
	
	public BaseScoreCardVO(OloScoreCardRecord scoreCard){
		this.scoreCard = scoreCard;
	}
	
	public UnionDataObject getAsSFSObjectForGameStart(){
		UnionDataObject obj = null;
			if(this.scoreCard!=null){
				obj = new UnionDataObject();
				obj.append(BaseScoreCardVO.RES_SCORECARD_ID, this.scoreCard.getScoreCardId());
				obj.append(BaseScoreCardVO.RES_SCORECARD_STARTTIME, this.scoreCard.getStartTime().toString());
			}
		return obj;		
	}
	
	public static UnionDataObject getAsSFSObjectForDemoGameStart(){
		UnionDataObject obj = null;
		
				obj = new UnionDataObject();
				obj.append(BaseScoreCardVO.RES_SCORECARD_ID, "DEMO");
				obj.append(BaseScoreCardVO.RES_SCORECARD_STARTTIME, new Date(Calendar.getInstance().getTime().getTime()).toString());
		
		return obj;		
	}
	
	public UnionDataObject getAsSFSObjectForGameCompleted(){
		UnionDataObject obj = null;
			if(this.scoreCard!=null){
				obj = new UnionDataObject();
				obj.append(BaseScoreCardVO.RES_SCORECARD_ID, this.scoreCard.getScoreCardId());
				obj.append(BaseScoreCardVO.RES_SCORECARD_STARTTIME, this.scoreCard.getStartTime().toString());
				obj.append(BaseScoreCardVO.RES_SCORECARD_ENDTIME, this.scoreCard.getEndTime().toString());
				obj.append(BaseScoreCardVO.RES_SCORECARD_SCORE, this.scoreCard.getGameScore().longValue());
			}
		return obj;		
	}
	
	public static UnionDataObject getAsSFSObjectForDemoGameCompleted(long score){
		UnionDataObject obj = null;
			
			obj = new UnionDataObject();
			obj.append(BaseScoreCardVO.RES_SCORECARD_ID, "DEMO");
			obj.append(BaseScoreCardVO.RES_SCORECARD_STARTTIME, "DEMO");
			obj.append(BaseScoreCardVO.RES_SCORECARD_ENDTIME, new Date(Calendar.getInstance().getTime().getTime()).toString());
			obj.append(BaseScoreCardVO.RES_SCORECARD_SCORE, score);
			
		return obj;		
	}
	
	*/

}
