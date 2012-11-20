package com.zalerio.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.zalerio.config.GameFeatures;
import com.zalerio.config.GameUtil;
import com.zalerio.config.Popup;
import com.zalerio.config.Tiles;

public class Create2PlayerGameTest  extends Zalerio2UserBaseTest
{
	
	//public Create2PlayerGameTest()

	//(String os, String browser, String version,
	//		String user1id, String user2id, String password) {
	//	super(os, browser, version, user1id, user2id, password);
		// TODO Auto-generated constructor stub
	//}

	@Test
	public void playGame() throws InterruptedException {
//		String user1Email = "abhilashbhaduri@gmail.com";
//		String user1Pass = "16081989"; //Config.FB_SECURED_ACCOUNT_PASSWORD;
//		String user2Email = "griffinsingh1@gmail.com";//Config.FB_SECURED_ACCOUNT_USERNAME2;
//		String user2Pass = "griffinsingh1";//Config.FB_SECURED_ACCOUNT_PASSWORD2;
//		WebDriver driver1 = new FirefoxDriver();
		// login User1
	//	driver1.manage().window().s
//		UserLogin.Olduserlogin(driver1, user1Email, user1Pass);
		//RatingScreenTest.closeGameEndPopupWithVerifyRating(driver1);
		Thread.sleep(2000);
		System.out.print("Logged in");
		// verify Username
//		VerifyFeatures.verifyUsername(driver1, "Abhi Vads");
		int []SelectedFriends=new int[]{2};
		// create new game at friendPosition
		GameFeatures.createGame(driver1, SelectedFriends);
		GameUtil.closeGameEndPopUp(driver2);
		// login user2
//		System.setProperty("webdriver.chrome.driver",
//				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
//		WebDriver driver2 = new ChromeDriver();
//		UserLogin.Olduserlogin(driver2, user2Email, user2Pass);
		
		// grab new GameId
		String NewGameId=GameFeatures.grabGameId(driver1);
		GameUtil.closeGameEndPopUp(driver2);
		//make user 2 click cheat sheet
		WebElement cheatSheetDiv2 = driver1.findElement(By
				.className("friendChallenge"));
		cheatSheetDiv2.click();
		Thread.sleep(2000);
		cheatSheetDiv2.click();
		// check Cheat Sheet,Help,Stats,game tile,More
		// cheat sheet
		WebElement cheatSheetDiv = driver1.findElement(By
				.className("friendChallenge"));
		cheatSheetDiv.click();
		Thread.sleep(2000);
		cheatSheetDiv.click();
		// Help
		WebElement helpButton = driver1.findElement(By.className("helpButton"));
		helpButton.click();
		WebElement s_friendpopup = driver1.findElement(By.id("s_friendpopup"));
		WebElement close = s_friendpopup.findElement(By.id("close"));
		close.click();
		// Stats
		WebElement stats = driver1.findElement(By.id("bottomHUDbuttons-stats"));
		stats.click();
		Thread.sleep(2000);
		WebElement s_friendpopup1 = driver1.findElement(By.id("s_friendpopup"));
		WebElement close1 = s_friendpopup1.findElement(By.id("close"));
		close1.click();
		// Game Tile
		WebElement presentGameTile = driver1.findElement(By.id(NewGameId));
		presentGameTile.click();
		Thread.sleep(2000);
		WebElement gdPlaydiv = driver1.findElement(By.className("gdPlaydiv"));
		gdPlaydiv.click();
		// More
		WebElement bottomHUD = driver1.findElement(By.id("bottomHUD"));
		WebElement bottomHUDbuttons_more = bottomHUD.findElement(By
				.id("bottomHUDbuttons-more"));
		boolean status=bottomHUDbuttons_more.isDisplayed(); 
		assertEquals(status, true);
		
		// close insufficent user popup for user2
		GameUtil.closeGameEndPopUp(driver2);
		System.out.print("User 2 has Logged in");
		// click Play and verify Error Pop up msg
		WebElement play = driver1.findElement(By.id("placeBetOnServer"));
		play.click();

		// verify pop up and close it
		Popup.verifyPopup(driver1, "No bets placed!");
		// drag and drop tiles
		WebElement gamewall = driver1.findElement(By.id("gamewall"));
		WebElement gameBetPanel = driver1.findElement(By.id("gameBetPanel"));

		// drag and place 1 tile on board and click play to verify pop up
		// drag and drop bets
		WebElement bet_0 = gameBetPanel.findElement(By.id("bet_0"));
		WebElement pos = gamewall.findElement(By.id("boardTile-483"));
		Tiles.drag1Tile(driver1, bet_0, pos);
		// click play to verify popup
		play.click();
		Popup.verifyPopup(driver1,
				"Not so fast... please place all of your 9 tiles!");
		// place 1 tile outside board
		WebElement startButton = driver1.findElement(By.id("startButton"));
		Thread.sleep(1000);
		WebElement bet_1 = gameBetPanel.findElement(By.id("bet_1"));
		Tiles.drag1Tile(driver1, bet_1, startButton);
		// place 1 tile on same position as tile0
		Tiles.drag1Tile(driver1, bet_1, pos);
		// interchange tile positions in tray
		bet_0 = gameBetPanel.findElement(By.id("bet_0"));
		bet_1 = gameBetPanel.findElement(By.id("bet_1"));
		Tiles.drag1Tile(driver1, bet_1, bet_0);
		GameUtil.closeGameEndPopUp(driver2);
		// drag rest of tiles at random and place on board.
		for (int i = 1; i < 9; i++) {
			String betid = "bet_" + i;
			String betPos = "boardTile-" + i;
			WebElement bet = gameBetPanel.findElement(By.id(betid));
			WebElement position = gamewall.findElement(By.id(betPos));
			Tiles.drag1Tile(driver1, bet, position);
			Thread.sleep(1000);
		}

		// interchange position of tiles on board
		WebElement tilePresentPosition = gamewall.findElement(By
				.id("boardTile-5"));
		WebElement tileNextPosition = gamewall.findElement(By
				.id("boardTile-482"));
		Tiles.drag1Tile(driver1, tilePresentPosition, tileNextPosition);
		// drag tile from board to tray
		tilePresentPosition = gamewall.findElement(By.id("boardTile-482"));
		tileNextPosition = gameBetPanel.findElement(By.id("bet_3"));
		Tiles.drag1Tile(driver1, tilePresentPosition, tileNextPosition);
		// click Play
		play.click();
		Thread.sleep(2000);
		// change position of tiles on board TODO unsuccessful need to be redone
	//	tilePresentPosition = gamewall.findElement(By.id("boardTile-90"));
	//	String draggable = tilePresentPosition.getAttribute("draggable");
		GameUtil.closeGameEndPopUp(driver2);
		// click Play to verify popup
		play.click();
		Popup.verifyPopup(driver1,
				"Not so fast.... lets wait for your friends to play their turn");
		// move to 2nd user

		// accept invitation and drag tiles to click play and complete 1 round
		// accept invitation
		
		GameFeatures.acceptInvitation(driver2, NewGameId);
		// drag and drop all tiles
		GameUtil.closeGameEndPopUp(driver1);
		Tiles.dragAllTiles(driver2);
		WebElement play2 = driver2.findElement(By.id("placeBetOnServer"));
		play2.click();
		// TODO shift to user 1 to verify round end
		Thread.sleep(3000);
		// check ScoreBoard
		WebElement leftHUD = driver1.findElement(By.id("leftHUD"));
		WebElement gameScore = leftHUD.findElement(By.className("gameScore"));
		WebElement gameScore_round = gameScore.findElement(By
				.className("gameScore-round"));
		WebElement doneRound = gameScore_round.findElement(By
				.className("doneRound"));
		String roundNo = doneRound.getText();
		status=roundNo.contains("1");
			assertEquals(status, true);
		GameUtil.closeGameEndPopUp(driver2);
		WebElement currentRound = gameScore_round.findElement(By
				.className("currentRound"));
		roundNo = currentRound.getText();
		status=roundNo.contains("2");
		assertEquals(status, true);
		WebElement notPlayedRound = gameScore_round.findElement(By
				.className("notPlayedRound"));
		roundNo = notPlayedRound.getText();
		status=roundNo.contains("3");
		assertEquals(status, true);
		WebElement gameScore_players = gameScore.findElement(By
				.className("gameScore-players"));
		WebElement userScoreHUDMain = gameScore_players.findElement(By
				.id("userScoreHUDMain"));
		List<WebElement> userScoreHUD_player = userScoreHUDMain.findElements(By
				.className("userScoreHUD_player"));
		GameUtil.closeGameEndPopUp(driver2);
		// check player1
		WebElement player1 = userScoreHUD_player.get(0);
		WebElement userScoreHUD_playerBetPlacedNoarrow = player1.findElement(By
				.className("userScoreHUD_playerBetPlacedNo"));
		status=userScoreHUD_playerBetPlacedNoarrow.isDisplayed();
			assertEquals(status, true);
		
		WebElement userScoreHUD_playerSerial = player1.findElement(By
				.className("userScoreHUD_playerSerial"));
		status=userScoreHUD_playerSerial.getText().contains("1");
			assertEquals(status, true);
		
		WebElement userScoreHUD_playerName = player1.findElement(By
				.className("userScoreHUD_playerName"));
		status=userScoreHUD_playerName.isDisplayed();
			assertEquals(status, true);
		
		WebElement userScoreHUD_playerScore = player1.findElement(By
				.className("userScoreHUD_playerScore"));
		status=userScoreHUD_playerScore.isDisplayed();
			assertEquals(status, true);
		
		// check player2
		WebElement player2 = userScoreHUD_player.get(1);
		userScoreHUD_playerBetPlacedNoarrow = player2.findElement(By
				.className("userScoreHUD_playerBetPlacedNo"));
		status=userScoreHUD_playerBetPlacedNoarrow.isDisplayed();
			assertEquals(status, true);
		
		userScoreHUD_playerSerial = player2.findElement(By
				.className("userScoreHUD_playerSerial"));
		status=userScoreHUD_playerSerial.getText().contains("2");
			assertEquals(status, true);
			GameUtil.closeGameEndPopUp(driver1);
		userScoreHUD_playerName = player2.findElement(By
				.className("userScoreHUD_playerName"));
		status=userScoreHUD_playerName.isDisplayed();
			assertEquals(status, true);
		
		userScoreHUD_playerScore = player2.findElement(By
				.className("userScoreHUD_playerScore"));
		status=userScoreHUD_playerScore.isDisplayed();
		assertEquals(status, true);
		
		// check gameTile
		WebElement rightHUD_yourturn= driver1.findElement(By.id("rightHUD-yourturn"));
		WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
		status=gameTile.findElement(By.className("round_no")).getText()
				.contains("2");
		assertEquals(status, true);
		GameUtil.closeGameEndPopUp(driver2);
		// Play User1 round2
		Thread.sleep(3000);
		Tiles.dragAllTiles(driver1);
		GameUtil.closeGameEndPopUp(driver2);
		play.click();
		// Play User2 round2
		Tiles.dragAllTiles(driver2);
		GameUtil.closeGameEndPopUp(driver1);
		play2.click();
		// Play User1 round3
		Thread.sleep(3000);
		Tiles.dragAllTiles(driver1);
		GameUtil.closeGameEndPopUp(driver2);
		play.click();
		// Play User2 round3
		Tiles.dragAllTiles(driver2);
		GameUtil.closeGameEndPopUp(driver1);
		play2.click();
		// Play User1 round4
		Thread.sleep(3000);
		Tiles.dragAllTiles(driver1);
		GameUtil.closeGameEndPopUp(driver2);
		play.click();
		// Play User2 round4
		Tiles.dragAllTiles(driver2);
		GameUtil.closeGameEndPopUp(driver1);
		play2.click();
		// Play User1 round5
		Thread.sleep(3000);
		Tiles.dragAllTiles(driver1);
		GameUtil.closeGameEndPopUp(driver2);
		play.click();
		// Play User2 round5
		Tiles.dragAllTiles(driver2);
		GameUtil.closeGameEndPopUp(driver1);
		play2.click();

		// Play User1 round6
		Thread.sleep(3000);
		Tiles.dragAllTiles(driver1);
		GameUtil.closeGameEndPopUp(driver2);
		play.click();
		// Play User2 round6
		Tiles.dragAllTiles(driver2);
		GameUtil.closeGameEndPopUp(driver1);
		play2.click();
		// Play User1 round7
		Thread.sleep(3000);
		Tiles.dragAllTiles(driver1);
		GameUtil.closeGameEndPopUp(driver2);
		play.click();
		// Play User2 round7
		Tiles.dragAllTiles(driver2);
		GameUtil.closeGameEndPopUp(driver1);
		play2.click();
		Thread.sleep(3000);
		// verify rating screen pop up
		//RatingScreenTest.closeGameEndPopupWithVerifyRating(driver1);
		GameUtil.closeGameEndPopUp(driver1);
		// handle finish game pop up
		Thread.sleep(5000);
		WebElement score_friendpopup = driver2.findElement(By
				.id("score_friendpopup"));
		// click dismiss
		WebElement show_score = score_friendpopup.findElement(By
				.className("show_score"));
		WebElement button_score = show_score.findElement(By
				.className("button_score"));
		List<WebElement> buttons = button_score.findElements(By.tagName("a"));
		WebElement dismiss = buttons.get(1);
		dismiss.click();
		// click x of rating screen
		WebElement rating_popup = driver2.findElement(By
				.className("rating-popup"));
		close1 = rating_popup.findElement(By.tagName("a"));
		close1.click();
		// reload the page
		driver2.navigate().refresh();
		Thread.sleep(8000);
		driver2.switchTo().frame("iframe_canvas");
		//RatingScreenTest.closeGameEndPopupWithVerifyRating(driver2);
		//check stats 
	//	Stats.verifyGameAddToPastGames(driver2, NewGameId);
	}
}
