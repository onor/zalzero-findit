package net.user1.union.zz.zalerioGame.lib.vo;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import net.user1.union.zz.common.UnionDataObject;
import net.user1.union.zz.zalerioGame.ZalerioGameRoom;

/**
 * This is the wrapper of the players relevant information
 * for each game.
 * @author Snehesh
 *
 */
public class GameVO {
	
	private static Logger logger = Logger.getLogger(GameVO.class);
	
	private final String TOTAL_PLAYERS = "TP";
	private final String START_DATE = "SD";
	private final String END_DATE = "ED";
	private final String END_DATE_LONG = "EDL";
	private final String GAME_CREATED_BY = "GCB";
	private final String GAME_STATUS = "GS"; 
	private final String PLAYERS = "PLRS";
	private final String CURRENT_ROUND = "CR";
	
	private List<PlayerVO> players;
	private String gameInstId;
	private long gameStartDate;
	private boolean changed = true;
	private String gameCreatorId;
	private String gameStatus;
	private long gameEndDate;
	private String currentRound;
	
	public List<PlayerVO> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerVO> players) {
		if(!players.equals(this.players)) {
			changed = true;
			this.players = players;
		} else {
			changed = false;
		}
	}

	public String getGameInstId() {
		return gameInstId;
	}

	public void setGameInstId(String gameInstId) {
		if(!gameInstId.equals(this.gameInstId)) {
			changed = true;
			this.gameInstId = gameInstId;
		} else {
			changed = false;
		}
	}
	
	public void addPlayers(PlayerVO playerVO) {
		this.players.add(playerVO);
	}
	
	public void addPlayers(PlayerVO playerVO, boolean atStart) {
		if(atStart) {
			this.players.add(0, playerVO);
		} else {
			this.players.add(playerVO);
		}
	}

	public long getGameStartDate() {
		return gameStartDate;
	}

	public void setGameStartDate(long gameStartDate) {
		this.gameStartDate = gameStartDate;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	public String getGameCreatorId() {
		return gameCreatorId;
	}

	public void setGameCreatorId(String gameCreatorId) {
		this.gameCreatorId = gameCreatorId;
	}
	
	public long getGameEndDate() {
		return gameEndDate;
	}

	public void setGameEndDate(long gameEndDate) {
		this.gameEndDate = gameEndDate;
	}
	
	public String getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(String currentRound) {
		this.currentRound = currentRound;
	}

	public UnionDataObject toUDO() {
		// For all players game seat
		UnionDataObject udo = new UnionDataObject();
		
		if(gameStartDate != 0) {
			DateTime dt = new DateTime(gameStartDate);
			DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d, yyyy");
			udo.append(START_DATE, fmt.print(dt));
		} else {
			udo.append(START_DATE, "");
		}
		
		if(gameEndDate != 0) {
			DateTime dt = new DateTime(gameEndDate);
			//DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d, yyyy HH:MM");
			DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d");
			udo.append(END_DATE, fmt.print(dt));
		} else {
			udo.append(END_DATE, "");
		}
		
		/* For sorting purposes in update past game carousel */
		if(gameEndDate !=0) {
			udo.append(END_DATE_LONG, gameEndDate);
		} else {
			udo.append(END_DATE_LONG,"");
		}

		udo.append(GAME_CREATED_BY, gameCreatorId);
		udo.append(CURRENT_ROUND, currentRound);
		udo.append(GAME_STATUS, gameStatus);
		
		if(players != null) {
			udo.append(TOTAL_PLAYERS, players.size());
			UnionDataObject playersUdo = new UnionDataObject();
			for (Iterator<PlayerVO> itr = players.iterator(); itr.hasNext();) {
				PlayerVO playerVO = itr.next();
				playersUdo.append(playerVO.getSeatId(), playerVO.toUDO());
			}
			udo.append(PLAYERS, playersUdo);
		}
		
		return udo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gameCreatorId == null) ? 0 : gameCreatorId.hashCode());
		result = prime * result + ((gameInstId == null) ? 0 : gameInstId.hashCode());
		result = prime * result + (int) (gameStartDate ^ (gameStartDate >>> 32));
		result = prime * result + ((gameStatus == null) ? 0 : gameStatus.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameVO other = (GameVO) obj;
		if (gameCreatorId == null) {
			if (other.gameCreatorId != null)
				return false;
		} else if (!gameCreatorId.equals(other.gameCreatorId))
			return false;
		if (gameInstId == null) {
			if (other.gameInstId != null)
				return false;
		} else if (!gameInstId.equals(other.gameInstId))
			return false;
		if (gameStartDate != other.gameStartDate)
			return false;
		if (gameStatus == null) {
			if (other.gameStatus != null)
				return false;
		} else if (!gameStatus.equals(other.gameStatus))
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		return true;
	}
	
}
