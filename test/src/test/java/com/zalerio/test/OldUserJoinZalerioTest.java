package com.zalerio.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.zalerio.config.GameUtil;
import com.zalerio.config.Popup;
import com.zalerio.config.UserLogin;

public class OldUserJoinZalerioTest extends Zalerio1UserBaseTest{
	public OldUserJoinZalerioTest(String os, String browser, String version,
			String userid, String password) {
		super(os, browser, version, userid, password);
		// TODO Auto-generated constructor stub
	}

	// remove all active games presently
	// TODO the situation when user has 1 or more games that have not been
	// accepted by others cannot be removed
	@Before
	public void removeAllGames() throws InterruptedException {
	//	System.setProperty("webdriver.chrome.driver",
	//			"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
	/*	driver = new FirefoxDriver();
		
		 String emailid="hemantkumer007@gmail.com";
		 String password="hemantkumer007";
		UserLogin.Olduserlogin(driver, emailid, password);
		Thread.sleep(10000);
		System.out.print("Logged in");*/
		WebElement rightHUD_yourturn = driver1.findElement(By
				.id("rightHUD-yourturn"));
		List<WebElement> your_turnTiles;
		// access your turn tiles
	
		try {
			your_turnTiles = rightHUD_yourturn.findElements(By
					.className("userArea"));
			System.out.print("got your turn tiles");
		} catch (Exception e) {
			return;
		}
		int size = your_turnTiles.size();
		for (int i = 0; i < size; i++) {
			WebElement gameTile = null;
			try {
				gameTile = your_turnTiles.get(i);
				gameTile.click();
				System.out.println("got your turn tile " + i);
			} catch (Exception e) {
			}
			// click play
			try {
				WebElement gdPlaydiv = driver1.findElement(By
						.className("gdPlaydiv"));
				gdPlaydiv.click();
				System.out.print("cilcked play");
			} catch (Exception f) {
				System.out.print("no play");
			}
			// click resume
			try {
				WebElement gdResumeDiv = driver1.findElement(By
						.className("gdResumeDiv"));
				gdResumeDiv.click();
				System.out.print("cilcked resume");
			} catch (Exception f) {
				System.out.print("no resume");
			}
			// decline invitations
			try {
				WebElement checkInvitation = gameTile.findElement(By
						.className("accept_decline"));
				WebElement declineButton = checkInvitation.findElement(By
						.className("right_hud_decline"));
				declineButton.click();
				System.out.print("cilcked decline");
				Thread.sleep(5000);
				Popup.closePopup(driver1);
			} catch (Exception f) {
				System.out.print("no invitation");
			}
			Thread.sleep(5000);
	
			// click more to resign

			WebElement bottomHUD = driver1.findElement(By.id("bottomHUD"));
			WebElement bottomHUDbuttons_more = bottomHUD.findElement(By
					.id("bottomHUDbuttons-more"));
			bottomHUDbuttons_more.click();
			System.out.print("clicked More");
			// click resign
			WebElement resign = driver1.findElement(By.id("resignme"));
			resign.click();
			System.out.print("clicked Resign");
			// click dismiss to close Game End PopUp
			GameUtil.closeGameEndPopUp(driver1);

		}
		//Their turn
		WebElement rightHUD_theirturn = driver1.findElement(By
				.id("rightHUD-theirturn"));
		List<WebElement> their_turnTiles = null;
		// access their turn tiles
		try {
			their_turnTiles = rightHUD_theirturn.findElements(By
					.className("userArea"));
		} catch (Exception e) {
			System.out.println("their turn tiles not found");
		}
		size = their_turnTiles.size();
		for (int i = 0; i < size; i++) {
			WebElement gameTile = null;
			try {
				gameTile = their_turnTiles.get(i);
				gameTile.click();
				System.out.print("game tile" + i + " found");
			} catch (Exception e) {
				System.out.print("game tile not found");
			}
			// click play
			try {
				WebElement gdPlaydiv = driver1.findElement(By
						.className("gdPlaydiv"));
				gdPlaydiv.click();
			} catch (Exception f) {
			}
			// click resume
			try {
				WebElement gdResumeDiv = driver1.findElement(By
						.className("gdResumeDiv"));
				gdResumeDiv.click();
			} catch (Exception f) {
			}
			// decline invitations
			try {
				WebElement checkInvitation = gameTile.findElement(By
						.className("accept_decline"));
				WebElement declineButton = checkInvitation.findElement(By
						.className("right_hud_decline"));
				declineButton.click();
				Popup.closePopup(driver1);
			} catch (Exception f) {
				System.out.print("NO INVITATION");
			}

			// click more to resign

			WebElement bottomHUD = driver1.findElement(By.id("bottomHUD"));
			WebElement bottomHUDbuttons_more = bottomHUD.findElement(By
					.id("bottomHUDbuttons-more"));
			bottomHUDbuttons_more.click();
			System.out.print("clicked More");
			// click resign
			WebElement resign = driver1.findElement(By.id("resignme"));
			resign.click();
			System.out.print("clicked Resign");
			// click dismiss to close Game End PopUp
			GameUtil.closeGameEndPopUp(driver1);

		}
		driver1.switchTo().defaultContent();
		driver1.findElement(By.id("pageLogo")).click();
	}

	// old user with no active games currently
	 @Test
	public void olduserNoActiveGame() throws InterruptedException {
		driver1.navigate().back();
		
			Thread.sleep(10000);
		
		GameUtil.closeGameEndPopUp(driver1);
		// check Your Turns
		WebElement rightHUD_yourturn = driver1.findElement(By
				.id("rightHUD-yourturn"));

		WebElement StartMoreGames = rightHUD_yourturn.findElement(By
				.className("newCarouselblanktile"));
		boolean status=StartMoreGames.isDisplayed() && StartMoreGames.isEnabled();
			assertEquals(status,true);
		
		// check Their Turns
		WebElement rightHUD_theirturn = driver1.findElement(By
				.id("rightHUD-theirturn"));
		WebElement StartMoreGames2 = rightHUD_theirturn.findElement(By
				.className("newCarouselblanktile"));
		status=StartMoreGames2.isDisplayed() && StartMoreGames2.isEnabled();
		assertEquals(status,true);
		//check left HUD game carousal of last game
		WebElement gameInfo_game_players = driver1.findElement(By
				.id("gameInfo-game-players"));
		 
		status=gameInfo_game_players.isDisplayed();
		assertEquals(status, true);
		//check game wall is empty
		WebElement gamewall = driver1.findElement(By.id("gamewall"));
		for (int i = 0; i <= 483; i++) {
			String tile = "boardTile-" + i;
			WebElement boardTile = gamewall.findElement(By.id(tile));
			String droppable = boardTile.getAttribute("droppable");
			if (status=droppable.contains("-1")) {
				assertEquals(status, true);
				return;
			}
		}
		driver1.quit();
	}

	// old user with no game in Your Turn but many games in Their Turn
	@Before
	public void removeYourTurnGames() throws InterruptedException
	{
	//	System.setProperty("webdriver.chrome.driver", "C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
	//	 driver= new ChromeDriver();
		driver1=new FirefoxDriver();
		 String emailid="hemantkumer007@gmail.com";
		 String password="hemantkumer007";
		UserLogin.Olduserlogin(driver1, emailid, password);
	
		WebElement  rightHUD_yourturn= driver1.findElement(By.id("rightHUD-yourturn"));
		List<WebElement> your_turnTiles;
		//access your turn tiles
		try{
		 your_turnTiles=rightHUD_yourturn.findElements(By.className("userArea"));
		 System.out.print("got your turn tiles");
		}catch(Exception e)
		{
			return;
		}
		int size=your_turnTiles.size();
		for(int i=0;i<size;i++)
		{
			WebElement gameTile=null;
			try{
			 gameTile=your_turnTiles.get(i);
			gameTile.click();
			System.out.println("got your turn tile "+i);
			}catch(Exception e)
			{}
				//click play
				try{
					WebElement gdPlaydiv=driver1.findElement(By.className("gdPlaydiv"));
					gdPlaydiv.click();
					System.out.print("cilcked play");
				}catch(Exception f)
				{System.out.print("no play");}
				//click resume
				try{
					WebElement gdResumeDiv=driver1.findElement(By.className("gdResumeDiv"));
					gdResumeDiv.click();
					System.out.print("cilcked resume");
				}catch(Exception f)
				{System.out.print("no resume");}
				//decline invitations
				try
				{
				WebElement	checkInvitation=gameTile.findElement(By.className("accept_decline"));
				WebElement declineButton=checkInvitation.findElement(By.className("right_hud_decline"));
				declineButton.click();
				System.out.print("cilcked decline");
				Popup.closePopup(driver1);
				}catch(Exception f)
				{System.out.print("no invitation");}
					Thread.sleep(5000);
				//click more to resign
			
			WebElement bottomHUD = driver1.findElement(By.id("bottomHUD"));
			WebElement bottomHUDbuttons_more = bottomHUD.findElement(By
					.id("bottomHUDbuttons-more"));
			bottomHUDbuttons_more.click();
				System.out.print("clicked More");
				// click resign
				WebElement resign=driver1.findElement(By.id("resignme"));
				resign.click();
				System.out.print("clicked Resign");
				// click dismiss to close Game End PopUp
				GameUtil.closeGameEndPopUp(driver1);
		}
		driver1.switchTo().defaultContent();
		driver1.findElement(By.id("pageLogo")).click();	
	}
	

	@Test
	public void oldUserNoGameYourTurn() throws InterruptedException {
		driver1.navigate().back();
		
			Thread.sleep(10000);
		
		GameUtil.closeGameEndPopUp(driver1);
		// check Your Turns
		WebElement rightHUD_yourturn = driver1.findElement(By
				.id("rightHUD-yourturn"));

		WebElement StartMoreGames = rightHUD_yourturn.findElement(By
				.className("newCarouselblanktile"));
		boolean status =StartMoreGames.isDisplayed() && StartMoreGames.isEnabled();
			assertEquals(status, true);
		
		WebElement gameInfo_game_players = driver1.findElement(By
				.id("gameInfo-game-players"));
		status=gameInfo_game_players.isDisplayed();
			assertEquals(status,true);
		
		WebElement gamewall = driver1.findElement(By.id("gamewall"));
		for (int i = 0; i <= 483; i++) {
			String tile = "boardTile-" + i;
			WebElement boardTile = gamewall.findElement(By.id(tile));
			String droppable = boardTile.getAttribute("droppable");
			if (status=droppable.contains("-1")) {
				assertEquals(status, true);
				return;
			}
		}
	}
	// old user with many games in Your Turn but no games in Their Turn

}
