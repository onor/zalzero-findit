package com.saucelabs.ZalerioTest.test;




import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import com.saucelabs.ZalerioTest.config.GameFeatures;
import com.saucelabs.ZalerioTest.config.GameUtil;
import com.saucelabs.ZalerioTest.config.Popup;
import com.saucelabs.ZalerioTest.config.VerifyFeatures;




public class Create3UserGameTest extends Zalerio2UserBaseTest{
	// verify username
	// left HUD carousal for friend decline
								// Game confirmation Pop up
@Test
	public void createGame()  {
		// create 3 player game
		/*String user1ID = "abhilashbhaduri@gmail.com";
		String user1Password = "16081989";
		String user2ID = "griffinsingh1@gmail.com";

		String user2Password = "griffinsingh1";*/
		
		
		//verify username
	//get data from testing.properties
			Properties values= new Properties();
			try {
				values.load(new FileInputStream("testing.properties"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String type =System.getenv("JOB_NAME");
			String user1Name,user2Name;
			if(type.contains("Zalerio-Staging-Test"))
			{
			
			user1Name=values.getProperty("user1_stag_name");
			user2Name=values.getProperty("user2_stag_name");
			}	
			else
			{
				user1Name=values.getProperty("user1_prod_name");
				user2Name=values.getProperty("user2_prod_name");
			}	
		VerifyFeatures.verifyUsername(driver1,user1Name);
		VerifyFeatures.verifyUsername(driver2,user2Name );
	
		//create a new game
	int friendPosition=GameFeatures.getFriendPosition();
		int[] SelectedFriends=new int[]{friendPosition,5};
		try {
			GameFeatures.createGameWithDelay(driver1, SelectedFriends, driver2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GameUtil.makebusy(driver2);
		// grab new GameId
		String NewGameId=GameFeatures.grabGameIdWithDelay(driver1,driver2);
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
		GameUtil.makebusy(driver1);
		// click OK on Pop up
		Popup.verifyPopup(driver2, "You have declined successfuly");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//close game formation Pop up to  check left HUD
		WebElement invitestatus=driver1.findElement(By.id("invitestatus"));
		WebElement score_friendpopup=invitestatus.findElement(By.id("score_friendpopup"));
		WebElement close=score_friendpopup.findElement(By.id("close"));
		close.click();
		GameUtil.makebusy(driver2);
		WebElement gameInfo_game_players=driver1.findElement(By.id("gameInfo-game-players"));
		WebElement background=gameInfo_game_players.findElement(By.className("background"));
		List<WebElement> infoPlates=background.findElements(By.className("infoPlate"));
		assertEquals(infoPlates.size(),1);
		//reload page to check game formation Pop up
		driver1.navigate().refresh();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver1.switchTo().frame("iframe_canvas");
		GameUtil.makebusy(driver2);
		 invitestatus=driver1.findElement(By.id("invitestatus"));
		 score_friendpopup=invitestatus.findElement(By.id("score_friendpopup"));
		WebElement invite_status_base=score_friendpopup.findElement(By.className("invite_status_base"));
		WebElement statusWrapper=invite_status_base.findElement(By.className("statusWrapper"));
			//check accepted
			WebElement acceptBlock=statusWrapper.findElement(By.className("acceptBlock"));
			List<WebElement>imgWrapper=acceptBlock.findElements(By.className("imgWrapper"));
			assertEquals(imgWrapper.size(),1);
			GameUtil.makebusy(driver2);
			//check declined
			 WebElement declineBlock=statusWrapper.findElement(By.className("declineBlock"));
			imgWrapper=declineBlock.findElements(By.className("imgWrapper"));
			assertEquals(imgWrapper.size(),1);
			GameUtil.makebusy(driver2);
			//check NotResponded Yet
			WebElement inviteBlock=statusWrapper.findElement(By.className("inviteBlock"));
			imgWrapper=inviteBlock.findElements(By.className("imgWrapper"));
			assertEquals(imgWrapper.size(),1);
		WebElement buttonWrapper=invite_status_base.findElement(By.className("buttonWrapper"));	
		GameUtil.makebusy(driver2);
			//send Reminder
//			WebElement rightButtonWrapper=buttonWrapper.findElement(By.className("rightButtonWrapper"));
//			WebElement rightButton=rightButtonWrapper.findElement(By.className("rightButton"));
//			rightButton.click();
//			Popup.verifyPopup(driver1, "You have sent reminder successfuly");
//			GameUtil.makebusy(driver2);
			//close invitations
			WebElement leftButtonWrapper=buttonWrapper.findElement(By.className("leftButtonWrapper"));
			WebElement leftButton=leftButtonWrapper.findElement(By.className("leftButton"));
			leftButton.click();
			Popup.verifyPopup(driver1, "Game cancel!!!   Insufficient Users in Game");
			GameUtil.makebusy(driver2);
	}
}
