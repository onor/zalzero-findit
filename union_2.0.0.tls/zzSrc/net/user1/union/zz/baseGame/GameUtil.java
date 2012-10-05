package net.user1.union.zz.baseGame;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

public class GameUtil {
	
	/**
	 * Returns time elapsed in readable format
	 * @param lastPlayedTs
	 * @return
	 */
	public static String getTimeElapsed(Timestamp lastPlayedTs) {
		
		String timeElapsed = " ";
		
		if(lastPlayedTs != null) {
			DateTime now = new DateTime();
			DateTime lastPlayed = new DateTime(lastPlayedTs.getTime());
			int days = Days.daysBetween(lastPlayed, now).getDays();
			if (days > 1) {
				timeElapsed = days + "d ago";
			} else if (days == 1) {
				timeElapsed = days + "d ago";
			} else {
				int hours = Hours.hoursBetween(lastPlayed, now).getHours();
				if (hours > 1) {
					timeElapsed = hours + "h ago";
				} else if (hours == 1) {
					timeElapsed = hours + "h ago";
				} else {
					int minutes = Minutes.minutesBetween(lastPlayed, now).getMinutes();
					if (minutes > 1) {
						timeElapsed = minutes + "m ago";
					} else if (minutes == 1) {
						timeElapsed = minutes + "m ago";
					} else {
						timeElapsed = "<1m ago";
					}
				}
			}
		}

		return timeElapsed;
	}
	
	/**
	 * Creates the name in the appropriate display format.
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public static String getDisplayName(String firstName, String lastName) {
		String name = "";
		if(firstName != null && (firstName.length() <= 6 || lastName == null || lastName.length() > 6)) {
			if(firstName.length() <= 6) {
				name = firstName;
			} else {
				name = firstName.substring(0, 6) + ".";
			}
			
			if(lastName != null) {
				name += " " + lastName.substring(0, 1) + ".";
			}
		} else {
			if(lastName != null) {
				if(lastName.length() <=6 ) {
					name = lastName;
				} else {
					name = lastName.substring(0, 6) + ".";
				}
				
				if(firstName != null) {
					name += " " + firstName.substring(0, 1) + ".";
				}
			}
		}
		return name;
	}
	
	/**
	 * Returns the rank in the format 1st, 2nd, 3rd, 4th and so on
	 * @param rank
	 * @return
	 */
	public static String getRank(Integer rank) {
		// The data needs to be shown in superscript format at the front end
		// hence sending only the number.
		if(rank != null) {
			return rank.toString(); 
		} else {
			return "";
		}
		/*
		String rankStr = "";
		if(rank != null) {
			switch (rank.intValue()) {
			case 1:
				rankStr = "1st";
				break;
			case 2:
				rankStr = "2nd";
				break;
			case 3:
				rankStr = "3rd";
				break;
			default:
				rankStr = rank.intValue() + "th";
				break;
			}
		}
		return rankStr;
		*/
	}
	
	public static String getRoundName(String round) {
		String roundName = "";
		if(round == null || round.equals("7")) {
			roundName = "F";
		} else {
			roundName = round;
		}
		return roundName;
	}

}
