

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.zalerio.config.GameFeatures;
import com.zalerio.config.GameUtil;
import com.zalerio.config.Popup;
import com.zalerio.config.Tiles;

public class LeftHUDUserStatusTest extends Zalerio2UserBaseTest {
	@Test
	public void checkStatus() throws InterruptedException {
		
	/*	WebDriver driver1 = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver",
				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
		WebDriver driver2 = new ChromeDriver();
		
		String user1id = Config.FB_SECURED_ACCOUNT_USERNAME;
		String user1pass = Config.FB_SECURED_ACCOUNT_PASSWORD;
		String user2id = "hemantkumer007@gmail.com";
		String user2pass = "hemantkumer007";
		UserLogin.Olduserlogin(driver1, user1id, user1pass);
		UserLogin.Olduserlogin(driver2, user2id, user2pass);
		*/
		// user1 creates a new game
		int SelectedFriends[]=new int[]{2};
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {}
		GameUtil.closeGameEndPopUp(driver2);
		WebElement a = driver1.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		// select n friends
		for(int i=0;i<SelectedFriends.length;i++)
		{	
		WebElement friend = selectListOfButtons.get(SelectedFriends[i]).findElement(By.tagName("a"));
		friend.click();
		}
		GameUtil.closeGameEndPopUp(driver2);
		//send challenge
		WebElement sendChallengeButton = driver1.findElement(By
						.id("sendinvite"));
		sendChallengeButton.click();
		System.out.println("challenge sent");
		Popup.closePopup(driver1);
		Thread.sleep(2000);
			GameUtil.closeGameEndPopUp(driver2);
		// user status :not accepted yet in red
		WebElement gameInfo_game_players = driver1.findElement(By
				.id("gameInfo-game-players"));
		WebElement background = gameInfo_game_players.findElement(By
				.className("background"));
		WebElement infoPlate = background
				.findElement(By.className("infoPlate"));
		WebElement reminder = infoPlate.findElement(By.className("reminder"));
		boolean status = reminder.isDisplayed() && reminder.isEnabled();
		assertEquals(status, true);
		GameUtil.closeGameEndPopUp(driver2);
		assertEquals(infoPlate.findElement(By.className("userAreaImg"))
				.isDisplayed(), true);
		WebElement userinfo = infoPlate.findElement(By.className("userinfo"));
		assertEquals(userinfo.findElement(By.className("username"))
				.isDisplayed(), true);
		WebElement userPlayStatus = userinfo.findElement(By
				.className("userPlayStatus"));
		String userStatus = userPlayStatus.getText();
		String color=userPlayStatus.getAttribute("class");
		assertEquals(color.contains("red"),true);
		
		assertEquals(userStatus, "not accepted yet");
		GameUtil.closeGameEndPopUp(driver2);
		// grab new GameId
	String NewGameId=GameFeatures.grabGameId(driver1);
		//accept invitation by user2
	
		GameFeatures.acceptInvitation(driver2, NewGameId);
		GameUtil.closeGameEndPopUp(driver2);
		Thread.sleep(2000);
		// user status :...playing now in green
				WebElement gameInfo_game_players2 = driver1.findElement(By
						.id("gameInfo-game-players"));
				WebElement background2 = gameInfo_game_players2.findElement(By
						.className("background"));
				WebElement infoPlate2 = background2
						.findElement(By.className("infoPlate"));
		//		WebElement reminder2 = infoPlate2.findElement(By.className("reminder"));
		//		boolean status2 = reminder2.isDisplayed() && reminder2.isEnabled();
		//		assertEquals(status, true);
				assertEquals(infoPlate2.findElement(By.className("userAreaImg"))
						.isDisplayed(), true);
				GameUtil.closeGameEndPopUp(driver2);
				WebElement userinfo2 = infoPlate2.findElement(By.className("userinfo"));
				assertEquals(userinfo2.findElement(By.className("username"))
						.isDisplayed(), true);
				WebElement userPlayStatus2 = userinfo2.findElement(By
						.className("userPlayStatus"));
				GameUtil.closeGameEndPopUp(driver2);
				String userStatus2 = userPlayStatus2.getText();
				String color2=userPlayStatus2.getAttribute("class");
				assertEquals(color2.contains("green"),true);
				assertEquals(userStatus2, "...playing now");
		//drag and drop all tiles of user2 and click play
				
				Random randomGenerator = new Random();
				
				for (int i = 0; i < 9; i++) {
					WebElement gamewall = driver2.findElement(By.id("gamewall"));
					WebElement gameBetPanel = driver2.findElement(By.id("gameBetPanel"));
					// get bet
					String betid = "bet_" + i;
					WebElement bet = gameBetPanel.findElement(By.id(betid));
					int randomInt;
					GameUtil.closeGameEndPopUp(driver1);
					// get position
					WebElement position;
					String droppable;
					do {
						randomInt = randomGenerator.nextInt(480);
						String betPos = "boardTile-" + randomInt;
						position = gamewall.findElement(By.id(betPos));
						// check if it is droppable
						droppable = position.getAttribute("droppable");
						if (droppable.contains("2")) {
							// place bet
							Actions builder = new Actions(driver2); // Configure the
																	// Action
							Action dragAndDrop = builder.clickAndHold(bet)
									.moveToElement(position).release(position).build();
							dragAndDrop.perform();
							Thread.sleep(1000);
						}
					} while (droppable.contains("-1"));
				}
				GameUtil.closeGameEndPopUp(driver1);
				WebElement play2 = driver2.findElement(By.id("placeBetOnServer"));
				play2.click();
		//make user2 leave game screen
				driver2.switchTo().defaultContent();
			driver2.findElement(By.id("pageLogo")).click();		
		//check user2 status as "finished round"
			GameUtil.closeGameEndPopUp(driver2);
				 gameInfo_game_players = driver1.findElement(By
						.id("gameInfo-game-players"));
				 background = gameInfo_game_players.findElement(By
						.className("background"));
				 infoPlate = background
						.findElement(By.className("infoPlate"));
				GameUtil.closeGameEndPopUp(driver2);
				assertEquals(infoPlate.findElement(By.className("userAreaImg"))
						.isDisplayed(), true);
				 userinfo = infoPlate.findElement(By.className("userinfo"));
				assertEquals(userinfo.findElement(By.className("username"))
						.isDisplayed(), true);
				 userPlayStatus = userinfo.findElement(By
						.className("userPlayStatus"));
				 userStatus = userPlayStatus.getText();
				color=userPlayStatus.getAttribute("class");
				assertEquals(color.contains("green"),true);
				assertEquals(userStatus, "finished round");
				GameUtil.closeGameEndPopUp(driver2);
		//drag and drop all tiles by user1
				Tiles.dragAllTiles(driver1);
				GameUtil.closeGameEndPopUp(driver2);
				WebElement play1 = driver1.findElement(By.id("placeBetOnServer"));
				play1.click();
				Thread.sleep(8000);
				GameUtil.closeGameEndPopUp(driver2);
		//check user2 status "not played yet"	
			 gameInfo_game_players = driver1.findElement(By
						.id("gameInfo-game-players"));
				 background = gameInfo_game_players.findElement(By
						.className("background"));
				 infoPlate = background
						.findElement(By.className("infoPlate"));
				 reminder = infoPlate.findElement(By.className("reminder"));
				 status = reminder.isDisplayed() && reminder.isEnabled();
				assertEquals(status, true);
				assertEquals(infoPlate.findElement(By.className("userAreaImg"))
						.isDisplayed(), true);
				GameUtil.closeGameEndPopUp(driver2);
				 userinfo = infoPlate.findElement(By.className("userinfo"));
				assertEquals(userinfo.findElement(By.className("username"))
						.isDisplayed(), true);
				 userPlayStatus = userinfo.findElement(By
							.className("userPlayStatus"));
					 userStatus = userPlayStatus.getText();
					 GameUtil.closeGameEndPopUp(driver2);
					assertEquals(userStatus, "not played yet");
		//click resign by user 1 to end game
					WebElement bottomHUD = driver1.findElement(By.id("bottomHUD"));
					WebElement bottomHUDbuttons_more = bottomHUD.findElement(By
							.id("bottomHUDbuttons-more"));
					bottomHUDbuttons_more.click();
					GameUtil.closeGameEndPopUp(driver2);
					WebElement resignme =driver1.findElement(By.id("resignme"));
					resignme.click();
					GameUtil.closeGameEndPopUp(driver2);
					
	}

}
