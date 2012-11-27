

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.zalerio.config.GameFeatures;
import com.zalerio.config.GameUtil;
import com.zalerio.config.Popup;
import com.zalerio.config.VerifyFeatures;



public class Create3UserGame extends Zalerio2UserBaseTest{
	// verify username
	// left HUD carousal for friend decline
								// Game confirmation Pop up
	@Test
	public void createGame() throws InterruptedException {
		// create 3 player game
		/*String user1ID = "abhilashbhaduri@gmail.com";
		String user1Password = "16081989";
		String user2ID = "griffinsingh1@gmail.com";

		String user2Password = "griffinsingh1";*/
		
		
		//verify username
		VerifyFeatures.verifyUsername(driver1, "Abhi Vads");
		VerifyFeatures.verifyUsername(driver2, "Griffin Singh");
		//create a new game
		int[] SelectedFriends=new int[]{2,3};
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		GameUtil.closeGameEndPopUp(driver2);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {}
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
		// grab new GameId
		String NewGameId=GameFeatures.grabGameId(driver1);
		// move to user2
		// login user2
	//	System.setProperty("webdriver.chrome.driver",
	//			"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
	//	WebDriver driver2 = new ChromeDriver();
	//	UserLogin.Olduserlogin(driver2, user2ID, user2Password);
		// decline invitation
		WebElement rightHUD_yourturn = driver2.findElement(By.id("rightHUD-yourturn"));
		WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
		WebElement accept_decline = gameTile.findElement(By
				.className("accept_decline"));
		WebElement decline = accept_decline.findElement(By
				.className("right_hud_decline"));
		decline.click();
		GameUtil.closeGameEndPopUp(driver1);
		// click OK on Pop up
		Popup.verifyPopup(driver2, "You have declined successfuly");
		//close game formation Pop up to  check left HUD
		WebElement invitestatus=driver1.findElement(By.id("invitestatus"));
		WebElement score_friendpopup=invitestatus.findElement(By.id("score_friendpopup"));
		WebElement close=score_friendpopup.findElement(By.id("close"));
		close.click();
		GameUtil.closeGameEndPopUp(driver1);
		WebElement gameInfo_game_players=driver1.findElement(By.id("gameInfo-game-players"));
		WebElement background=gameInfo_game_players.findElement(By.className("background"));
		List<WebElement> infoPlates=background.findElements(By.className("infoPlate"));
		assertEquals(infoPlates.size(),1);
		//reload page to check game formation Pop up
		driver1.navigate().refresh();
		Thread.sleep(5000);
		driver1.switchTo().frame("iframe_canvas");
		GameUtil.closeGameEndPopUp(driver2);
		 invitestatus=driver1.findElement(By.id("invitestatus"));
		 score_friendpopup=invitestatus.findElement(By.id("score_friendpopup"));
		WebElement invite_status_base=score_friendpopup.findElement(By.className("invite_status_base"));
		WebElement statusWrapper=invite_status_base.findElement(By.className("statusWrapper"));
			//check accepted
			WebElement acceptBlock=statusWrapper.findElement(By.className("acceptBlock"));
			List<WebElement>imgWrapper=acceptBlock.findElements(By.className("imgWrapper"));
			assertEquals(imgWrapper.size(),1);
			GameUtil.closeGameEndPopUp(driver2);
			//check declined
			 WebElement declineBlock=statusWrapper.findElement(By.className("declineBlock"));
			imgWrapper=declineBlock.findElements(By.className("imgWrapper"));
			assertEquals(imgWrapper.size(),1);
			GameUtil.closeGameEndPopUp(driver2);
			//check NotResponded Yet
			WebElement inviteBlock=statusWrapper.findElement(By.className("inviteBlock"));
			imgWrapper=inviteBlock.findElements(By.className("imgWrapper"));
			assertEquals(imgWrapper.size(),1);
		WebElement buttonWrapper=invite_status_base.findElement(By.className("buttonWrapper"));	
		GameUtil.closeGameEndPopUp(driver2);
			//send Reminder
			WebElement rightButtonWrapper=buttonWrapper.findElement(By.className("rightButtonWrapper"));
			WebElement rightButton=rightButtonWrapper.findElement(By.className("rightButton"));
			rightButton.click();
			Popup.verifyPopup(driver1, "You have sent reminder successfuly");
			GameUtil.closeGameEndPopUp(driver2);
			//close invitations
			WebElement leftButtonWrapper=buttonWrapper.findElement(By.className("leftButtonWrapper"));
			WebElement leftButton=leftButtonWrapper.findElement(By.className("leftButton"));
			leftButton.click();
			Popup.verifyPopup(driver1, "Game cancel!!!   Insufficient Users in Game");
			GameUtil.closeGameEndPopUp(driver2);
	}
}
