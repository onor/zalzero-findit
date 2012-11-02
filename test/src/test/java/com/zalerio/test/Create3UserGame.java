package com.zalerio.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.zalerio.config.GameUtil;
import com.zalerio.config.Popup;
import com.zalerio.config.UserLogin;
import com.zalerio.config.VerifyFeatures;

public class Create3UserGame { // verify username
								// left HUD carousal for friend decline
								// Game confirmation Pop up
	@Test
	public void createGame() throws InterruptedException {
		// create 3 player game
		String user1ID = "abhilashbhaduri@gmail.com";
		String user1Password = "16081989";
		String user2ID = "griffinsingh1@gmail.com";

		String user2Password = "griffinsingh1";
		
		WebDriver driver1 = new FirefoxDriver();
		// login User1
		UserLogin.userlogin(driver1, user1ID, user1Password);
		driver1.switchTo().frame("iframe_canvas");
		GameUtil.clickPlayHereForMultiTabIssue(driver1);
		Popup.verifyPopup(driver1, "Game cancel!!!  Insufficient Users in Game");
		GameUtil.closeGameEndPopUp(driver1);
		Thread.sleep(6000);
		System.out.print("Logged in");
		VerifyFeatures.verifyUsername(driver1, "Abhi Vads");
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		Thread.sleep(3000);

		WebElement a = driver1.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		WebElement fr1 = selectListOfButtons.get(2).findElement(
				By.className("select_button"));
		fr1.click();
		WebElement fr2 = selectListOfButtons.get(3).findElement(
				By.className("select_button"));
		fr2.click();
		WebElement sendChallengeButton = driver1.findElement(By
				.id("sendinvite"));
		sendChallengeButton.click();
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) driver1;
		js.executeScript("window.scrollTo(0,0)");

		// verify pop up and load the game
		Popup.verifyPopup(driver1,
				"Challenge has been sent. Would you like to start playing.");
		Thread.sleep(2000);

		// grab new GameId
		String NewGameId = "";
		WebElement rightHUD_yourturn = driver1.findElement(By
				.id("rightHUD-yourturn"));
		int newSize;
		// access your turn tiles
		try {
			List<WebElement> your_turnTiles = rightHUD_yourturn.findElements(By
					.className("userArea"));
			System.out.print("got your turn tiles");
			newSize = your_turnTiles.size();
			System.out.println("new size" + newSize);
			WebElement gameTile = null;
			gameTile = your_turnTiles.get(newSize - 1);
			NewGameId = gameTile.getAttribute("id");
			System.out.println("got New your turn tile with Id " + NewGameId);
		} catch (Exception f) {
		}
		// move to user2
		// login user2
		System.setProperty("webdriver.chrome.driver",
				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
		WebDriver driver2 = new ChromeDriver();
		UserLogin.userlogin(driver2, user2ID, user2Password);
		driver2.switchTo().frame("iframe_canvas");
		GameUtil.clickPlayHereForMultiTabIssue(driver2);
		Popup.verifyPopup(driver2, "Game cancel!!!  Insufficient Users in Game");
		GameUtil.closeGameEndPopUp(driver2);
		// accept invitation
		rightHUD_yourturn = driver2.findElement(By.id("rightHUD-yourturn"));
		WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
		WebElement accept_decline = gameTile.findElement(By
				.className("accept_decline"));
		WebElement decline = accept_decline.findElement(By
				.className("right_hud_decline"));
		decline.click();
		// click OK on Pop up
		Popup.verifyPopup(driver2, "You have declined successfuly");
		//close game formation Pop up to  check left HUD
		WebElement invitestatus=driver1.findElement(By.id("invitestatus"));
		WebElement score_friendpopup=invitestatus.findElement(By.id("score_friendpopup"));
		WebElement close=score_friendpopup.findElement(By.id("close"));
		close.click();
		WebElement gameInfo_game_players=driver1.findElement(By.id("gameInfo-game-players"));
		WebElement background=gameInfo_game_players.findElement(By.className("background"));
		List<WebElement> infoPlates=background.findElements(By.className("infoPlate"));
		assertEquals(infoPlates.size(),1);
		//reload page to check game formation Pop up
		driver1.navigate().refresh();
		Thread.sleep(5000);
		driver1.switchTo().frame("iframe_canvas");
		 invitestatus=driver1.findElement(By.id("invitestatus"));
		 score_friendpopup=invitestatus.findElement(By.id("score_friendpopup"));
		WebElement invite_status_base=score_friendpopup.findElement(By.className("invite_status_base"));
		WebElement statusWrapper=invite_status_base.findElement(By.className("statusWrapper"));
			//check accepted
			WebElement acceptBlock=statusWrapper.findElement(By.className("acceptBlock"));
			List<WebElement>imgWrapper=acceptBlock.findElements(By.className("imgWrapper"));
			assertEquals(imgWrapper.size(),1);
			//check declined
			 WebElement declineBlock=statusWrapper.findElement(By.className("declineBlock"));
			imgWrapper=declineBlock.findElements(By.className("imgWrapper"));
			assertEquals(imgWrapper.size(),1);
			//check NotResponded Yet
			WebElement inviteBlock=statusWrapper.findElement(By.className("inviteBlock"));
			imgWrapper=inviteBlock.findElements(By.className("imgWrapper"));
			assertEquals(imgWrapper.size(),1);
		WebElement buttonWrapper=invite_status_base.findElement(By.className("buttonWrapper"));	
			//send Reminder
			WebElement rightButtonWrapper=buttonWrapper.findElement(By.className("rightButtonWrapper"));
			WebElement rightButton=rightButtonWrapper.findElement(By.className("rightButton"));
			rightButton.click();
			Popup.verifyPopup(driver1, "You have sent reminder successfuly");
			//close invitations
			WebElement leftButtonWrapper=buttonWrapper.findElement(By.className("leftButtonWrapper"));
			WebElement leftButton=leftButtonWrapper.findElement(By.className("leftButton"));
			leftButton.click();
			Popup.verifyPopup(driver1, "Game cancel!!!   Insufficient Users in Game");
	}
}
