package net.user1.union.zz.zalerioGame.lib.vo;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import net.user1.union.zz.baseGame.GameUtil;
import net.user1.union.zz.common.DbConstants;
import net.user1.union.zz.common.UnionDataObject;

/**
 * This is used to store all the relevant information
 * for any player. This is mainly used to render the 
 * carousel in the game screen
 * @author Snehesh
 *
 */
public class PlayerVO implements Comparable<PlayerVO>{
	
	private static final String PLAYER_USER_ID = "UI";
	private static final String PLAYER_SEAT_ID = "PSI";
	private static final String PLAYER_DISP_NAME = "PDN";
	private static final String PLAYER_FULL_NAME = "PFN";
	private static final String PLAYER_RANK = "PR";
	private static final String PLAYER_SCORE = "PSC";
	private static final String PLAYER_LEVEL = "PL";
	private static final String PLAYER_LAST_PLAYED = "PLP";
	private static final String PLAYER_CURRENT_ROUND = "PCR";
	private static final String PLAYER_IS_ONLINE = "PON";
	private static final String PLAYER_FB_ID = "PFB";
	private static final String PLAYER_RESIGNED = "PRE";
	private static final String TOTAL_GAMES_PLAYED = "GP";
	private static final String GAMES_WON = "GW";
	private static final String JOINED_DATE = "JD";
	private static final String GAME_SEAT_STATUS = "GSS"; 
	private static final String GAME_SEAT_STATUS_UPDATE_TIME = "SUT";
	// Values, 0 = Not Played, 5 = Playing, 9 = Played
	private static final String CURRENT_ROUND_PLAYED_STATUS = "CRS";
	
	public static final Integer CURRENT_ROUND_NOT_PLAYED = 0;
	public static final Integer CURRENT_ROUND_PLAYING = 5;
	public static final Integer CURRENT_ROUND_PLAYED = 9;
	
	private static final Integer PLAYER_RESIGNED_TRUE = 1;
	private static final Integer PLAYER_RESIGNED_FALSE = 0;
	
	//Codes to optimize data transfer
	public static final Integer GAME_SEAT_STATUS_INVITED_CODE = 1;
	public static final Integer GAME_SEAT_STATUS_ACCEPTED_CODE = 2;
	public static final Integer GAME_SEAT_STATUS_DECLINED_CODE = 3;
	public static final Integer GAME_SEAT_STATUS_RESIGNED_CODE = 4;
	public static final Integer GAME_SEAT_STATUS_CANCELED_CODE = 5;

	private String userId;
	private String seatId;
	private String displayName;
	private String fullName;
	private String facebookId;
	private Integer score;
	private Integer rank;
	private Integer level;
	private String lastPlayed;
	private String currentRoundDisp;
	private Integer isOnline;
	private Integer currentRoundPlayed;
	private Integer totalGamesPlayed;
	private Integer gamesWon;
	private Long joinedDate;
	private Integer gameSeatStatus;
	private Long gameSeatStatusUpdateTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getLastPlayed() {
		return lastPlayed;
	}

	public void setLastPlayed(String lastPlayed) {
		this.lastPlayed = lastPlayed;
	}

	public String getCurrentRoundDisp() {
		return currentRoundDisp;
	}

	public void setCurrentRoundDisp(String currentRoundDisp) {
		this.currentRoundDisp = currentRoundDisp;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public Integer getCurrentRoundPlayed() {
		return currentRoundPlayed;
	}

	public void setCurrentRoundPlayed(Integer currentRoundPlayed) {
		this.currentRoundPlayed = currentRoundPlayed;
	}
	
	public Integer getTotalGamesPlayed() {
		return totalGamesPlayed;
	}

	public void setTotalGamesPlayed(Integer totalGamesPlayed) {
		this.totalGamesPlayed = totalGamesPlayed;
	}

	public Integer getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(Integer gamesWon) {
		this.gamesWon = gamesWon;
	}

	public Long getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Long joinedDate) {
		this.joinedDate = joinedDate;
	}
	
	public Integer getGameSeatStatus() {
		return gameSeatStatus;
	}

	public void setGameSeatStatus(String gameSeatStatus) {
		if(DbConstants.GAME_SEAT_STATUS_INVITED.equals(gameSeatStatus)) {
			this.gameSeatStatus = PlayerVO.GAME_SEAT_STATUS_INVITED_CODE;
		} else if(DbConstants.GAME_SEAT_STATUS_ACCEPTED.equals(gameSeatStatus)) {
			this.gameSeatStatus = PlayerVO.GAME_SEAT_STATUS_ACCEPTED_CODE;
		} else if(DbConstants.GAME_SEAT_STATUS_DECLINED.equals(gameSeatStatus)) {
			this.gameSeatStatus = PlayerVO.GAME_SEAT_STATUS_DECLINED_CODE;
		} else if(DbConstants.GAME_SEAT_STATUS_RESIGNED.equals(gameSeatStatus)) {
			this.gameSeatStatus = PlayerVO.GAME_SEAT_STATUS_RESIGNED_CODE;
		} else if(DbConstants.GAME_SEAT_STATUS_CANCELED.equals(gameSeatStatus)) {
			this.gameSeatStatus = PlayerVO.GAME_SEAT_STATUS_CANCELED_CODE;
		} else {
			//For old data, safe check
			this.gameSeatStatus = PlayerVO.GAME_SEAT_STATUS_ACCEPTED_CODE;
		}
	}
	
	/**
	 * 
	 * @return the gameSeatStatusUpdateTime
	 */
	public Long getGameSeatStatusUpdateTime() {
		return this.gameSeatStatusUpdateTime;
	}
	
	/**
	 * 
	 * @param gameSeatStatusUpdateTime
	 */
	public void setGameSeatStatusUpdateTime(Long gameSeatStatusUpdateTime) {
		this.gameSeatStatusUpdateTime = gameSeatStatusUpdateTime;
	}

	public UnionDataObject toUDO() {
		UnionDataObject udo = new UnionDataObject();
		udo.append(PlayerVO.PLAYER_USER_ID, userId);
		udo.append(PlayerVO.PLAYER_SEAT_ID, seatId);
		udo.append(PlayerVO.PLAYER_DISP_NAME, displayName);
		udo.append(PlayerVO.PLAYER_FULL_NAME, fullName);
		udo.append(PlayerVO.PLAYER_FB_ID, facebookId);
		udo.append(PlayerVO.PLAYER_RANK, GameUtil.getRank(rank));
		if(score == null) {
			udo.append(PlayerVO.PLAYER_SCORE, "0");
		} else {
			udo.append(PlayerVO.PLAYER_SCORE, score);
		}
		udo.append(PlayerVO.PLAYER_LEVEL, level);
		udo.append(PlayerVO.PLAYER_LAST_PLAYED, lastPlayed);
		udo.append(PlayerVO.PLAYER_CURRENT_ROUND, currentRoundDisp);
		udo.append(PlayerVO.PLAYER_IS_ONLINE, isOnline);
		udo.append(PlayerVO.CURRENT_ROUND_PLAYED_STATUS, currentRoundPlayed);
		//TODO: This needs to be changed to ensure all operation is through game status
		if(gameSeatStatus == PlayerVO.GAME_SEAT_STATUS_RESIGNED_CODE) {
			udo.append(PlayerVO.PLAYER_RESIGNED, PLAYER_RESIGNED_TRUE);
		} else {
			udo.append(PlayerVO.PLAYER_RESIGNED, PLAYER_RESIGNED_FALSE);
		}
		udo.append(PlayerVO.TOTAL_GAMES_PLAYED, totalGamesPlayed);
		udo.append(PlayerVO.GAMES_WON, gamesWon);
		
		if(joinedDate != null && joinedDate.longValue() != 0) {
			DateTime dt = new DateTime(joinedDate);
			DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d, yyyy");
			udo.append(PlayerVO.JOINED_DATE, fmt.print(dt));
		} else {
			udo.append(PlayerVO.JOINED_DATE, "");
		}
		
		if(gameSeatStatusUpdateTime != null && gameSeatStatusUpdateTime.longValue() != 0) {
			udo.append(PlayerVO.GAME_SEAT_STATUS_UPDATE_TIME, gameSeatStatusUpdateTime);
		} else {
			udo.append(PlayerVO.GAME_SEAT_STATUS_UPDATE_TIME, "");
		}
		
		udo.append(PlayerVO.GAME_SEAT_STATUS, gameSeatStatus);
		return udo;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentRoundDisp == null) ? 0 : currentRoundDisp.hashCode());
		result = prime * result + ((currentRoundPlayed == null) ? 0 : currentRoundPlayed.hashCode());
		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + ((facebookId == null) ? 0 : facebookId.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((gameSeatStatus == null) ? 0 : gameSeatStatus.hashCode());
		result = prime * result + ((gameSeatStatusUpdateTime == null) ? 0 : gameSeatStatusUpdateTime.hashCode());
		result = prime * result + ((gamesWon == null) ? 0 : gamesWon.hashCode());
		result = prime * result + ((isOnline == null) ? 0 : isOnline.hashCode());
		result = prime * result + ((joinedDate == null) ? 0 : joinedDate.hashCode());
		result = prime * result + ((lastPlayed == null) ? 0 : lastPlayed.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((seatId == null) ? 0 : seatId.hashCode());
		result = prime * result + ((totalGamesPlayed == null) ? 0 : totalGamesPlayed.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PlayerVO)) {
			return false;
		}
		PlayerVO other = (PlayerVO) obj;
		if (currentRoundDisp == null) {
			if (other.currentRoundDisp != null) {
				return false;
			}
		} else if (!currentRoundDisp.equals(other.currentRoundDisp)) {
			return false;
		}
		if (currentRoundPlayed == null) {
			if (other.currentRoundPlayed != null) {
				return false;
			}
		} else if (!currentRoundPlayed.equals(other.currentRoundPlayed)) {
			return false;
		}
		if (displayName == null) {
			if (other.displayName != null) {
				return false;
			}
		} else if (!displayName.equals(other.displayName)) {
			return false;
		}
		if (facebookId == null) {
			if (other.facebookId != null) {
				return false;
			}
		} else if (!facebookId.equals(other.facebookId)) {
			return false;
		}
		if (fullName == null) {
			if (other.fullName != null) {
				return false;
			}
		} else if (!fullName.equals(other.fullName)) {
			return false;
		}
		if (gameSeatStatus == null) {
			if (other.gameSeatStatus != null) {
				return false;
			}
		} else if (!gameSeatStatus.equals(other.gameSeatStatus)) {
			return false;
		}
		if (gameSeatStatusUpdateTime == null) {
			if (other.gameSeatStatusUpdateTime != null) {
				return false;
			}
		} else if (!gameSeatStatusUpdateTime.equals(other.gameSeatStatusUpdateTime)) {
			return false;
		}
		if (gamesWon == null) {
			if (other.gamesWon != null) {
				return false;
			}
		} else if (!gamesWon.equals(other.gamesWon)) {
			return false;
		}
		if (isOnline == null) {
			if (other.isOnline != null) {
				return false;
			}
		} else if (!isOnline.equals(other.isOnline)) {
			return false;
		}
		if (joinedDate == null) {
			if (other.joinedDate != null) {
				return false;
			}
		} else if (!joinedDate.equals(other.joinedDate)) {
			return false;
		}
		if (lastPlayed == null) {
			if (other.lastPlayed != null) {
				return false;
			}
		} else if (!lastPlayed.equals(other.lastPlayed)) {
			return false;
		}
		if (level == null) {
			if (other.level != null) {
				return false;
			}
		} else if (!level.equals(other.level)) {
			return false;
		}
		if (rank == null) {
			if (other.rank != null) {
				return false;
			}
		} else if (!rank.equals(other.rank)) {
			return false;
		}
		if (score == null) {
			if (other.score != null) {
				return false;
			}
		} else if (!score.equals(other.score)) {
			return false;
		}
		if (seatId == null) {
			if (other.seatId != null) {
				return false;
			}
		} else if (!seatId.equals(other.seatId)) {
			return false;
		}
		if (totalGamesPlayed == null) {
			if (other.totalGamesPlayed != null) {
				return false;
			}
		} else if (!totalGamesPlayed.equals(other.totalGamesPlayed)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(PlayerVO o) {
		if(this.getScore() < o.getScore()) {
			return -1;
		} else if(this.getScore() > o.getScore()) {
			return 1;
		}
		return 0;
	}
	
}
