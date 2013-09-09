package net.user1.union.zz.zalerioGame.lib.vo;

import net.user1.union.api.Client;
import net.user1.union.zz.baseGame.lib.vo.BaseUserVO;

import org.jooq.Record;

	public class UserVO extends BaseUserVO implements Comparable<UserVO>{
		
		//private Logger logger = Logger.getLogger(UserVO.class);

	    public static final String USER_DISC_FIGS = "DF";
	    
	    private int zalerioUserScore = 0;
	    private int userDiscFigs = 0;
	    
	    private boolean userValChanged = true;
	    private boolean flag_userDiscFigs = true;
	    private boolean flag_scoreChanged = true;
		
	    
	    public UserVO(Client client) {
			super(client);
		}
	    
	    public UserVO(){
	    	super();
	    }
	    
	    
	    public int getUserDiscFigs() {
			return userDiscFigs;
		}


		public void setUserDiscFigs(int userDiscFigs) {
			this.userDiscFigs = userDiscFigs;
		}

		public boolean isFlag_userDiscFigs() {
			return flag_userDiscFigs;
		}


		public void setFlag_userDiscFigs(boolean flag_userDiscFigs) {
			this.flag_userDiscFigs = flag_userDiscFigs;
		}


		public boolean isFlag_scoreChanged() {
			return flag_scoreChanged;
		}


		public void setFlag_scoreChanged(boolean flag_scoreChanged) {
			this.flag_scoreChanged = flag_scoreChanged;
		}


		@Override
	    public void updateUserVars() {
	    	super.updateUserVars();
	        if (this.isUserValChanged()) {
	            if (this.isFlag_userDiscFigs()) {
	                setPublicUserVar(UserVO.USER_DISC_FIGS, this.getUserDiscFigs());
	                this.setFlag_userDiscFigs(false);
	            }
	            this.setUserValChanged(false);
	        }
	    }
	    
	    @Override
	    public void setUserVars() {
	    	super.setUserVars();
	    	setPublicUserVar(UserVO.USER_DISC_FIGS, this.getUserDiscFigs());
	    }
	    
	    @Override
	    public void loadClientDataFromDBRecord(Record row) {
	    	super.loadClientDataFromDBRecord(row);
	    }

		private boolean isUserValChanged() {
			return userValChanged;
		}

		private void setUserValChanged(boolean userValChanged) {
			this.userValChanged = userValChanged;
		}
		
		public boolean isUserVOChanged(){
			if(isUserValChanged() || super.isUserVOChanged()){
				return true;
			}
			return false;
		}


		public void setZalerioRoundScore(Integer playerScoreInt) {
			if(this.zalerioUserScore != playerScoreInt){
				this.zalerioUserScore = playerScoreInt;
				setFlag_scoreChanged(true);
			}
			
		}

		public int getZalerioUserScore() {
			return zalerioUserScore;
		}
		
		@Override
		public int compareTo(UserVO o) {
			if(this.getZalerioUserScore() < o.getZalerioUserScore()) {
				return -1;
			} else if(this.getZalerioUserScore() > o.getZalerioUserScore()) {
				return 1;
			}
			return 0;
		}
	    
	}
