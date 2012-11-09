package com.zalerio.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.zalerio.config.GameFeatures;
import com.zalerio.config.GameUtil;
import com.zalerio.config.Popup;
import com.zalerio.config.StartAGame;
import com.zalerio.config.Tiles;
import com.zalerio.config.UserLogin;

 public class User1ResignTest extends ZalerioBaseTest{
/*	public User1ResignTest(String os, String browser, String version,
			String user1id, String user2id, String password) {
		super(os, browser, version, user1id, user2id, password);
		// TODO Auto-generated constructor stub
	}
*/
	// user1 resigns from a game before user2 accepts the game
	 @Test
	public void resignBeforeAccept() throws InterruptedException {
		//WebDriver driver1 = new FirefoxDriver();
		//String emailid = "abhilashbhaduri@gmail.com";
		//String password = "16081989";
		//UserLogin.Olduserlogin(driver1, emailid, password);
		int[] SelectedFriends = new int[] { 2 };
		StartAGame.createGame(driver1, SelectedFriends);
		Thread.sleep(2000);
		WebElement bottomHUDbuttons = driver1.findElement(By
				.className("bottomHUDbuttons"));
		WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
				.id("bottomHUDbuttons-more"));
		bottomHUDbuttons_more.click();
		Thread.sleep(2000);
		boolean status = driver1.findElement(By.className("resignPopup"))
				.isDisplayed();
		assertEquals(status, false);
	}

	// user1 resigns from a game after user2 accepts game
	 @Test
	public void resignImmediateAfterAccept() throws InterruptedException {
//		WebDriver driver1 = new FirefoxDriver();
//		String emailid1 = "abhilashbhaduri@gmail.com";
//		String password1 = "16081989";
//		UserLogin.Olduserlogin(driver1, emailid1, password1);
		int[] SelectedFriends = new int[] { 2 };
		StartAGame.createGame(driver1, SelectedFriends);
		Thread.sleep(2000);
		// grab new GameId
		String NewGameId = GameFeatures.grabGameId(driver1);
//		System.setProperty("webdriver.chrome.driver",
//				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
//		WebDriver driver2 = new ChromeDriver();
//		String emailid2 = "griffinsingh1@gmail.com";
//		String password2 = "griffinsingh1";
//		UserLogin.Olduserlogin(driver2, emailid2, password2);
//		Thread.sleep(2000);
		// make user2 accept game
		GameFeatures.acceptInvitation(driver2, NewGameId);
		
		

		// grab shown name of user
		WebElement leftHUD = driver2.findElement(By.id("leftHUD"));
		WebElement gameInfoPanel = leftHUD.findElement(By
				.className("gameInfoPanel"));
		WebElement gameInfoDiv = gameInfoPanel.findElement(By
				.className("gameInfoDiv"));
		WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
		// verify user name
		WebElement currentUserName = userArea.findElement(By
				.id("currentUserName"));
		String shownName = currentUserName.getText();
		Thread.sleep(2000);
		// click resign by user1
		WebElement bottomHUDbuttons = driver1.findElement(By
				.className("bottomHUDbuttons"));
		WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
				.id("bottomHUDbuttons-more"));
		bottomHUDbuttons_more.click();
		Thread.sleep(2000);
		boolean status = driver1.findElement(By.className("resignPopup"))
				.isDisplayed();
		assertEquals(status, true);
		status = driver1.findElement(By.id("resignmeClose")).isDisplayed();
		assertEquals(status, true);
		WebElement resignButton = driver1.findElement(By.id("resignme"));
		status = resignButton.isDisplayed();
		resignButton.click();
		Thread.sleep(2000);
		// verfiy game end popup
		WebElement winnerscreen = driver1.findElement(By.id("winnerscreen"));
		WebElement score_friendpopup = winnerscreen.findElement(By
				.id("score_friendpopup"));
		WebElement show_score = score_friendpopup.findElement(By
				.className("show_score"));
		WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
		WebElement score1 = topScore_div.findElement(By.id("score1"));
		// user2 will be shown as winner with score 0
		WebElement name = score1.findElement(By.className("name"));
		String observedName = name.getText();
		// check observed name of user2 with shown name
		assertEquals(shownName, observedName);
		WebElement score = score1.findElement(By.className("score"));
		assertEquals(score.getText(), "0");
		GameUtil.closeGameEndPopUp(driver1);
		// TODO verfiy scoreboard

	}

	// user1 resigns from a game after user2 accepts and user1 drags a all tiles
	 @Test
	public void resignAfterDragFewTiles() throws InterruptedException {
//		WebDriver driver1 = new FirefoxDriver();
//		String emailid1 = "abhilashbhaduri@gmail.com";
//		String password1 = "16081989";
//		UserLogin.Olduserlogin(driver1, emailid1, password1);
		int[] SelectedFriends = new int[] { 2 };
		StartAGame.createGame(driver1, SelectedFriends);
		Thread.sleep(2000);
		// grab new GameId
		String NewGameId=GameFeatures.grabGameId(driver1);
		//login user2
//		System.setProperty("webdriver.chrome.driver",
//				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
//		WebDriver driver2 = new ChromeDriver();
//		String emailid2 = "griffinsingh1@gmail.com";
//		String password2 = "griffinsingh1";
//		UserLogin.Olduserlogin(driver2, emailid2, password2);
//		Thread.sleep(2000);
//		// make user2 accept game
		GameFeatures.acceptInvitation(driver2, NewGameId);

		// grab shown name of user
		WebElement leftHUD = driver2.findElement(By.id("leftHUD"));
		WebElement gameInfoPanel = leftHUD.findElement(By
				.className("gameInfoPanel"));
		WebElement gameInfoDiv = gameInfoPanel.findElement(By
				.className("gameInfoDiv"));
		WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
		// verify user name
		WebElement currentUserName = userArea.findElement(By
				.id("currentUserName"));
		String shownName = currentUserName.getText();
		Thread.sleep(2000);
		// User1 draga all tiles
		Tiles.dragAllTiles(driver1);
		// click resign by user1
		WebElement bottomHUDbuttons = driver1.findElement(By
				.className("bottomHUDbuttons"));
		WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
				.id("bottomHUDbuttons-more"));
		bottomHUDbuttons_more.click();
		Thread.sleep(2000);
		boolean status = driver1.findElement(By.className("resignPopup"))
				.isDisplayed();
		assertEquals(status, true);
		status = driver1.findElement(By.id("resignmeClose")).isDisplayed();
		assertEquals(status, true);
		WebElement resignButton = driver1.findElement(By.id("resignme"));
		status = resignButton.isDisplayed();
		resignButton.click();
		Thread.sleep(2000);
		// verfiy game end popup
		WebElement winnerscreen = driver1.findElement(By.id("winnerscreen"));
		WebElement score_friendpopup = winnerscreen.findElement(By
				.id("score_friendpopup"));
		WebElement show_score = score_friendpopup.findElement(By
				.className("show_score"));
		WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
		WebElement score1 = topScore_div.findElement(By.id("score1"));
		// user2 will be shown as winner with score 0
		WebElement name = score1.findElement(By.className("name"));
		String observedName = name.getText();
		// check observed name of user2 with shown name
		assertEquals(shownName, observedName);
		WebElement score = score1.findElement(By.className("score"));
		assertEquals(score.getText(), "0");
	}

	 @Test
	public void resignAfterPl2dragsAllTiles() throws InterruptedException {
//		WebDriver driver1 = new FirefoxDriver();
//		String emailid1 = "abhilashbhaduri@gmail.com";
//		String password1 = "16081989";
//		UserLogin.Olduserlogin(driver1, emailid1, password1);
		int[] SelectedFriends = new int[] { 2 };
		StartAGame.createGame(driver1, SelectedFriends);
		Thread.sleep(2000);
		// grab new GameId
		String NewGameId = GameFeatures.grabGameId(driver1);
//		System.setProperty("webdriver.chrome.driver",
//				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
//		WebDriver driver2 = new ChromeDriver();
//		String emailid2 = "griffinsingh1@gmail.com";
//		String password2 = "griffinsingh1";
//		UserLogin.Olduserlogin(driver2, emailid2, password2);
//		Thread.sleep(2000);
		// make user2 accept game
		GameFeatures.acceptInvitation(driver2, NewGameId);

		// grab shown name of user
		WebElement leftHUD = driver2.findElement(By.id("leftHUD"));
		WebElement gameInfoPanel = leftHUD.findElement(By
				.className("gameInfoPanel"));
		WebElement gameInfoDiv = gameInfoPanel.findElement(By
				.className("gameInfoDiv"));
		WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
		// verify user name
		WebElement currentUserName = userArea.findElement(By
				.id("currentUserName"));
		String shownName = currentUserName.getText();
		Thread.sleep(2000);
		// User2 drags all tiles
		Tiles.dragAllTiles(driver2);
		// click resign by user1
		WebElement bottomHUDbuttons = driver1.findElement(By
				.className("bottomHUDbuttons"));
		WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
				.id("bottomHUDbuttons-more"));
		bottomHUDbuttons_more.click();
		Thread.sleep(2000);
		boolean status = driver1.findElement(By.className("resignPopup"))
				.isDisplayed();
		assertEquals(status, true);
		status = driver1.findElement(By.id("resignmeClose")).isDisplayed();
		assertEquals(status, true);
		WebElement resignButton = driver1.findElement(By.id("resignme"));
		status = resignButton.isDisplayed();
		resignButton.click();
		Thread.sleep(2000);
		// verfiy game end popup
		WebElement winnerscreen = driver1.findElement(By.id("winnerscreen"));
		WebElement score_friendpopup = winnerscreen.findElement(By
				.id("score_friendpopup"));
		WebElement show_score = score_friendpopup.findElement(By
				.className("show_score"));
		WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
		WebElement score1 = topScore_div.findElement(By.id("score1"));
		// user2 will be shown as winner with score 0
		WebElement name = score1.findElement(By.className("name"));
		String observedName = name.getText();
		// check observed name of user2 with shown name
		assertEquals(shownName, observedName);
		WebElement score = score1.findElement(By.className("score"));
		assertEquals(score.getText(), "0");
	}

	// user1 resigns after playing round 1 after user has accepted game
	// @Test
	public void resignAfterPl1clicksPlay() throws InterruptedException {
//		WebDriver driver1 = new FirefoxDriver();
//		String emailid1 = "abhilashbhaduri@gmail.com";
//		String password1 = "16081989";
//		UserLogin.Olduserlogin(driver1, emailid1, password1);
		int[] SelectedFriends = new int[] { 2 };
		StartAGame.createGame(driver1, SelectedFriends);
		Thread.sleep(2000);
		// grab new GameId
		String NewGameId = GameFeatures.grabGameId(driver1);
//		System.setProperty("webdriver.chrome.driver",
//				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
//		WebDriver driver2 = new ChromeDriver();
//		String emailid2 = "griffinsingh1@gmail.com";
//		String password2 = "griffinsingh1";
//		UserLogin.Olduserlogin(driver2, emailid2, password2);
//		Thread.sleep(2000);
		// make user2 accept game
		GameFeatures.acceptInvitation(driver2, NewGameId);

		// grab shown name of user
		WebElement leftHUD = driver2.findElement(By.id("leftHUD"));
		WebElement gameInfoPanel = leftHUD.findElement(By
				.className("gameInfoPanel"));
		WebElement gameInfoDiv = gameInfoPanel.findElement(By
				.className("gameInfoDiv"));
		WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
		// verify user name
		WebElement currentUserName = userArea.findElement(By
				.id("currentUserName"));
		String shownName = currentUserName.getText();
		Thread.sleep(2000);
		// User1 drags all tiles
		Tiles.dragAllTiles(driver1);
		// User1 clicks Play
		WebElement play = driver1.findElement(By.id("placeBetOnServer"));
		play.click();
		System.out.print("user1 has clicked Play");
		// click resign by user1
		WebElement bottomHUDbuttons = driver1.findElement(By
				.className("bottomHUDbuttons"));
		WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
				.id("bottomHUDbuttons-more"));
		bottomHUDbuttons_more.click();
		Thread.sleep(2000);
		boolean status = driver1.findElement(By.className("resignPopup"))
				.isDisplayed();
		assertEquals(status, true);
		status = driver1.findElement(By.id("resignmeClose")).isDisplayed();
		assertEquals(status, true);
		WebElement resignButton = driver1.findElement(By.id("resignme"));
		status = resignButton.isDisplayed();
		resignButton.click();
		Thread.sleep(2000);
		// verfiy game end popup
		WebElement winnerscreen = driver1.findElement(By.id("winnerscreen"));
		WebElement score_friendpopup = winnerscreen.findElement(By
				.id("score_friendpopup"));
		WebElement show_score = score_friendpopup.findElement(By
				.className("show_score"));
		WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
		WebElement score1 = topScore_div.findElement(By.id("score1"));
		// user2 will be shown as winner with score 0
		WebElement name = score1.findElement(By.className("name"));
		String observedName = name.getText();
		// check observed name of user2 with shown name
		assertEquals(shownName, observedName);
		WebElement score = score1.findElement(By.className("score"));
		assertEquals(score.getText(), "0");
	}

	 @Test
	public void resignAfterPl2ClicksPlay() throws InterruptedException {
//		WebDriver driver1 = new FirefoxDriver();
//		String emailid1 = "abhilashbhaduri@gmail.com";
//		String password1 = "16081989";
//		UserLogin.Olduserlogin(driver1, emailid1, password1);
		int[] SelectedFriends = new int[] { 2 };
		StartAGame.createGame(driver1, SelectedFriends);
		Thread.sleep(2000);
		// grab new GameId
		String NewGameId = GameFeatures.grabGameId(driver1);
//		System.setProperty("webdriver.chrome.driver",
//				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
//		WebDriver driver2 = new ChromeDriver();
//		String emailid2 = "griffinsingh1@gmail.com";
//		String password2 = "griffinsingh1";
//		UserLogin.Olduserlogin(driver2, emailid2, password2);
//		Thread.sleep(2000);
		// make user2 accept game
		GameFeatures.acceptInvitation(driver2, NewGameId);

		// grab shown name of user
		WebElement leftHUD = driver2.findElement(By.id("leftHUD"));
		WebElement gameInfoPanel = leftHUD.findElement(By
				.className("gameInfoPanel"));
		WebElement gameInfoDiv = gameInfoPanel.findElement(By
				.className("gameInfoDiv"));
		WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
		// verify user name
		WebElement currentUserName = userArea.findElement(By
				.id("currentUserName"));
		String shownName = currentUserName.getText();
		Thread.sleep(2000);
		// User2 drags all tiles
		Tiles.dragAllTiles(driver2);
		// User1 clicks Play
		WebElement play = driver2.findElement(By.id("placeBetOnServer"));
		play.click();
		System.out.print("user1 has clicked Play");
		// click resign by user1
		WebElement bottomHUDbuttons = driver1.findElement(By
				.className("bottomHUDbuttons"));
		WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
				.id("bottomHUDbuttons-more"));
		bottomHUDbuttons_more.click();
		Thread.sleep(2000);
		boolean status = driver1.findElement(By.className("resignPopup"))
				.isDisplayed();
		assertEquals(status, true);
		status = driver1.findElement(By.id("resignmeClose")).isDisplayed();
		assertEquals(status, true);
		WebElement resignButton = driver1.findElement(By.id("resignme"));
		status = resignButton.isDisplayed();
		resignButton.click();
		Thread.sleep(2000);
		// verfiy game end popup
		WebElement winnerscreen = driver1.findElement(By.id("winnerscreen"));
		WebElement score_friendpopup = winnerscreen.findElement(By
				.id("score_friendpopup"));
		WebElement show_score = score_friendpopup.findElement(By
				.className("show_score"));
		WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
		WebElement score1 = topScore_div.findElement(By.id("score1"));
		// user2 will be shown as winner with score 0
		WebElement name = score1.findElement(By.className("name"));
		String observedName = name.getText();
		// check observed name of user2 with shown name
		assertEquals(shownName, observedName);
		WebElement score = score1.findElement(By.className("score"));
		int j = 0;
		status = score.getText().contains(String.valueOf(j));
		assertEquals(status, false);
	}

	 @Test
	public void resignRoundNWithEqualScores() throws InterruptedException {
//		WebDriver driver1 = new FirefoxDriver();
//		String emailid1 = "abhilashbhaduri@gmail.com";
//		String password1 = "16081989";
//		UserLogin.Olduserlogin(driver1, emailid1, password1);
		int[] SelectedFriends = new int[] { 2 };
		StartAGame.createGame(driver1, SelectedFriends);
		Thread.sleep(2000);
		// grab new GameId
		String NewGameId = GameFeatures.grabGameId(driver1);
//		System.setProperty("webdriver.chrome.driver",
//				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
//		WebDriver driver2 = new ChromeDriver();
//		String emailid2 = "griffinsingh1@gmail.com";
//		String password2 = "griffinsingh1";
//		UserLogin.Olduserlogin(driver2, emailid2, password2);
//		Thread.sleep(2000);
//		// make user2 accept game
		GameFeatures.acceptInvitation(driver2, NewGameId);

		// grab shown name of user
		WebElement leftHUD = driver2.findElement(By.id("leftHUD"));
		WebElement gameInfoPanel = leftHUD.findElement(By
				.className("gameInfoPanel"));
		WebElement gameInfoDiv = gameInfoPanel.findElement(By
				.className("gameInfoDiv"));
		WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
		// verify user name
		WebElement currentUserName = userArea.findElement(By
				.id("currentUserName"));
		String shownName = currentUserName.getText();
		Thread.sleep(2000);
		// User1 places all tiles sequentially and clicks play
		WebElement gamewall = driver1.findElement(By.id("gamewall"));
		WebElement gameBetPanel = driver1.findElement(By.id("gameBetPanel"));
		for (int i = 0; i < 9; i++) {
			String betid = "bet_" + i;
			String betPos = "boardTile-" + i;
			WebElement bet = gameBetPanel.findElement(By.id(betid));
			WebElement position = gamewall.findElement(By.id(betPos));
			Tiles.drag1Tile(driver1, bet, position);
			Thread.sleep(1000);
		}
		WebElement play = driver1.findElement(By.id("placeBetOnServer"));
		play.click();
		// User2 drags all tiles
		WebElement gamewall2 = driver2.findElement(By.id("gamewall"));
		WebElement gameBetPanel2 = driver2.findElement(By.id("gameBetPanel"));
		for (int i = 0; i < 9; i++) {
			String betid = "bet_" + i;
			String betPos = "boardTile-" + i;
			WebElement bet = gameBetPanel2.findElement(By.id(betid));
			WebElement position = gamewall2.findElement(By.id(betPos));
			Tiles.drag1Tile(driver2, bet, position);
			Thread.sleep(1000);
		}
		WebElement play2 = driver2.findElement(By.id("placeBetOnServer"));
		play2.click();
		System.out.print("user1 has clicked Play");
		// click resign by user1
		WebElement bottomHUDbuttons = driver1.findElement(By
				.className("bottomHUDbuttons"));
		WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
				.id("bottomHUDbuttons-more"));
		bottomHUDbuttons_more.click();
		Thread.sleep(2000);
		boolean status = driver1.findElement(By.className("resignPopup"))
				.isDisplayed();
		assertEquals(status, true);
		status = driver1.findElement(By.id("resignmeClose")).isDisplayed();
		assertEquals(status, true);
		WebElement resignButton = driver1.findElement(By.id("resignme"));
		status = resignButton.isDisplayed();
		resignButton.click();
		Thread.sleep(2000);
		// verfiy game end popup
		WebElement winnerscreen = driver1.findElement(By.id("winnerscreen"));
		WebElement score_friendpopup = winnerscreen.findElement(By
				.id("score_friendpopup"));
		WebElement show_score = score_friendpopup.findElement(By
				.className("show_score"));
		WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
		WebElement score1 = topScore_div.findElement(By.id("score1"));
		// user2 will be shown as winner with score 0
		WebElement name = score1.findElement(By.className("name"));
		String observedName = name.getText();
		// check observed name of user2 with shown name
		assertEquals(shownName, observedName);
		WebElement score = score1.findElement(By.className("score"));
		int j = 0;
		status = score.getText() != "";
		assertEquals(status, true);
	}

	//@Test
	public void resignFinalRoundAfterUser2HitsPlay()
			throws InterruptedException {
		WebDriver driver1 = new FirefoxDriver();
		String emailid1 = "abhilashbhaduri@gmail.com";
		String password1 = "16081989";
		UserLogin.Olduserlogin(driver1, emailid1, password1);
		int[] SelectedFriends = new int[] { 2 };
		StartAGame.createGame(driver1, SelectedFriends);
		Thread.sleep(2000);
		// grab new GameId
		String NewGameId = GameFeatures.grabGameId(driver1);
		Thread.sleep(2000);
		System.setProperty("webdriver.chrome.driver",
				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
		WebDriver driver2 = new ChromeDriver();
		String emailid2 = "griffinsingh1@gmail.com";
		String password2 = "griffinsingh1";
		UserLogin.Olduserlogin(driver2, emailid2, password2);
		Thread.sleep(2000);
		// make user2 accept game
		GameFeatures.acceptInvitation(driver2, NewGameId);

		// grab shown name of user
		WebElement leftHUD = driver2.findElement(By.id("leftHUD"));
		WebElement gameInfoPanel = leftHUD.findElement(By
				.className("gameInfoPanel"));
		WebElement gameInfoDiv = gameInfoPanel.findElement(By
				.className("gameInfoDiv"));
		WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
		// verify user name
		WebElement currentUserName = userArea.findElement(By
				.id("currentUserName"));
		String shownName = currentUserName.getText();
		Thread.sleep(2000);
		// User1 places all tiles sequentially and clicks play
		Tiles.dragAllTiles(driver1);
		WebElement play = driver1.findElement(By.id("placeBetOnServer"));
		play.click();
		// User2 drags all tiles
		Tiles.dragAllTiles(driver2);
		WebElement play2 = driver2.findElement(By.id("placeBetOnServer"));
		play2.click();
		System.out.print("user1 has clicked Play");
		// user1 places all tile in round 2
		Tiles.dragAllTiles(driver1);
		play = driver1.findElement(By.id("placeBetOnServer"));
		play.click();
		// user2 places all tiles in round 2
		Tiles.dragAllTiles(driver2);
		play2 = driver2.findElement(By.id("placeBetOnServer"));
		play2.click();
		// user1 places all tile in round 3
		Tiles.dragAllTiles(driver1);
		play = driver1.findElement(By.id("placeBetOnServer"));
		play.click();
		// user2 places all tiles in round 3
		Tiles.dragAllTiles(driver2);
		play2 = driver2.findElement(By.id("placeBetOnServer"));
		play2.click();
		// user1 places all tile in round 4
		Tiles.dragAllTiles(driver1);
		play = driver1.findElement(By.id("placeBetOnServer"));
		play.click();
		// user2 places all tiles in round 4
		Tiles.dragAllTiles(driver2);
		play2 = driver2.findElement(By.id("placeBetOnServer"));
		play2.click();
		// user1 places all tile in round 5
		Tiles.dragAllTiles(driver1);
		play = driver1.findElement(By.id("placeBetOnServer"));
		play.click();
		// user2 places all tiles in round 5
		Tiles.dragAllTiles(driver2);
		play2 = driver2.findElement(By.id("placeBetOnServer"));
		play2.click();
		// user1 places all tile in round 6
		Tiles.dragAllTiles(driver1);
		play = driver1.findElement(By.id("placeBetOnServer"));
		play.click();
		// user2 places all tiles in round 6
		Tiles.dragAllTiles(driver2);
		play2 = driver2.findElement(By.id("placeBetOnServer"));
		play2.click();
		// user2 places all tiles in round 7
		Tiles.dragAllTiles(driver2);
		play2 = driver2.findElement(By.id("placeBetOnServer"));
		play2.click();
		// click resign by user1
		WebElement bottomHUDbuttons = driver1.findElement(By
				.className("bottomHUDbuttons"));
		WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
				.id("bottomHUDbuttons-more"));
		bottomHUDbuttons_more.click();
		Thread.sleep(2000);
		boolean status = driver1.findElement(By.className("resignPopup"))
				.isDisplayed();
		assertEquals(status, true);
		status = driver1.findElement(By.id("resignmeClose")).isDisplayed();
		assertEquals(status, true);
		WebElement resignButton = driver1.findElement(By.id("resignme"));
		status = resignButton.isDisplayed();
		resignButton.click();
		Thread.sleep(2000);
		// verfiy game end popup
		WebElement winnerscreen = driver1.findElement(By.id("winnerscreen"));
		WebElement score_friendpopup = winnerscreen.findElement(By
				.id("score_friendpopup"));
		WebElement show_score = score_friendpopup.findElement(By
				.className("show_score"));
		WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
		WebElement score1 = topScore_div.findElement(By.id("score1"));
		// user2 will be shown as winner with score 0
		WebElement name = score1.findElement(By.className("name"));
		String observedName = name.getText();
		// check observed name of user2 with shown name
		assertEquals(shownName, observedName);
		WebElement score = score1.findElement(By.className("score"));
		int j = 0;
		status = score.getText() != "";
		assertEquals(status, true);
	}
}
