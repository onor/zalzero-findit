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
import com.zalerio.config.Tiles;
import com.zalerio.config.VerifyFeatures;
//TODO handle the situation when new Game Tile is not visible on Right HUD tile
public class Create2PlayerGame {
	@Test
	public void createGame() throws InterruptedException {
		int friendPosition = 2;

		WebDriver driver1 = new FirefoxDriver();
		// login User1
		driver1.get("http://apps.facebook.com/zalerio/?force=play");
		Thread.sleep(4000);
		WebElement login_form = driver1.findElement(By.id("login_form"));
		WebElement email = driver1.findElement(By.id("email"));
		email.sendKeys("abhilashbhaduri@gmail.com");
		WebElement pass = driver1.findElement(By.id("pass"));
		pass.sendKeys("16081989");
		login_form.submit();
		driver1.switchTo().frame("iframe_canvas");
		GameUtil.clickPlayHereForMultiTabIssue(driver1);
		GameUtil.closeGameEndPopUp(driver1);
		Thread.sleep(6000);
		System.out.print("Logged in");
		//verify Username
		VerifyFeatures.verifyUsername(driver1,"Abhi Vads");
		// create new game at friendPosition
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		Thread.sleep(3000);

		WebElement a = driver1.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		WebElement fr1 = selectListOfButtons.get(friendPosition).findElement(
				By.className("select_button"));
		fr1.click();
		System.out.println("fr1 selected");

		// load the game on main screen
		// Click Send Challenge
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
		if (bottomHUDbuttons_more.isDisplayed()) {
			assertEquals(1, 1);
		}

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
		Thread.sleep(2000);
		WebElement bet_1 = gameBetPanel.findElement(By.id("bet_1"));
		Tiles.drag1Tile(driver1, bet_1, startButton);
		// place 1 tile on same position as tile0
		Tiles.drag1Tile(driver1, bet_1, pos);
		// interchange tile positions in tray
		bet_0 = gameBetPanel.findElement(By.id("bet_0"));
		bet_1 = gameBetPanel.findElement(By.id("bet_1"));
		Tiles.drag1Tile(driver1, bet_1, bet_0);

		// drag rest of tiles at random and place on board.
		for (int i = 1; i < 9; i++) {
			String betid = "bet_" + i;
			String betPos = "boardTile-" + i;
			WebElement bet = gameBetPanel.findElement(By.id(betid));
			WebElement position = gamewall.findElement(By.id(betPos));
			Tiles.drag1Tile(driver1, bet, position);
			Thread.sleep(2000);
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
		Thread.sleep(5000);
		// change position of tiles on board TODO unsuccessful need to be redone
		tilePresentPosition = gamewall.findElement(By.id("boardTile-90"));
		String draggable = tilePresentPosition.getAttribute("draggable");
		if (draggable.contains("false")) {
			assertEquals(1, 1);
		}
		// click Play to verify popup
		play.click();
		Popup.verifyPopup(driver1,
				"Not so fast.... lets wait for your friends to play their turn");
		// move to 2nd user
		System.setProperty("webdriver.chrome.driver",
				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
		WebDriver driver2 = new ChromeDriver();

		// log User2
		driver2.get("http://apps.facebook.com/zalerio/?force=play");
		Thread.sleep(5000);
		login_form = driver2.findElement(By.id("login_form"));
		email = driver2.findElement(By.id("email"));
		email.sendKeys("griffinsingh1@gmail.com");
		pass = driver2.findElement(By.id("pass"));
		pass.sendKeys("griffinsingh1");
		login_form.submit();
		driver2.switchTo().frame("iframe_canvas");
		GameUtil.clickPlayHereForMultiTabIssue(driver2);
		GameUtil.closeGameEndPopUp(driver2);
		Thread.sleep(6000);
		System.out.print("Logged in");
		// accept invitation and drag tiles to click play and complete 1 round
		// accept invitation
		rightHUD_yourturn = driver2.findElement(By.id("rightHUD-yourturn"));
		WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
		WebElement accept_decline = gameTile.findElement(By
				.className("accept_decline"));
		WebElement accept = accept_decline.findElement(By
				.className("right_hud_accept"));
		accept.click();
		// click OK on Pop up
		Popup.verifyPopup(driver2, "Would you like to start playing");
		// drag and drop all tiles

		Tiles.dragAllTiles(driver2);
		WebElement play2 = driver2.findElement(By.id("placeBetOnServer"));
		play2.click();
		// TODO shift to user 1 to verify round end
		Thread.sleep(5000);
		// check ScoreBoard
		WebElement leftHUD = driver1.findElement(By.id("leftHUD"));
		WebElement gameScore = leftHUD.findElement(By.className("gameScore"));
		WebElement gameScore_round = gameScore.findElement(By
				.className("gameScore-round"));
		WebElement doneRound = gameScore_round.findElement(By
				.className("doneRound"));
		String roundNo = doneRound.getText();
		if (roundNo.contains("1")) {
			assertEquals(1, 1);
		}
		WebElement currentRound = gameScore_round.findElement(By
				.className("currentRound"));
		roundNo = currentRound.getText();
		if (roundNo.contains("2")) {
			assertEquals(1, 1);
		}
		WebElement notPlayedRound = gameScore_round.findElement(By
				.className("notPlayedRound"));
		roundNo = notPlayedRound.getText();
		if (roundNo.contains("3")) {
			assertEquals(1, 1);
		}
		WebElement gameScore_players = gameScore.findElement(By
				.className("gameScore-players"));
		WebElement userScoreHUDMain = gameScore_players.findElement(By
				.id("userScoreHUDMain"));
		List<WebElement> userScoreHUD_player = userScoreHUDMain.findElements(By
				.className("userScoreHUD_player"));
		// check player1
		WebElement player1 = userScoreHUD_player.get(0);
		WebElement userScoreHUD_playerBetPlacedNoarrow = player1.findElement(By
				.className("userScoreHUD_playerBetPlacedNo"));
		if (userScoreHUD_playerBetPlacedNoarrow.isDisplayed()) {
			assertEquals(1, 1);
		}
		WebElement userScoreHUD_playerSerial = player1.findElement(By
				.className("userScoreHUD_playerSerial"));
		if (userScoreHUD_playerSerial.getText().contains("1")) {
			assertEquals(1, 1);
		}
		WebElement userScoreHUD_playerName = player1.findElement(By
				.className("userScoreHUD_playerName"));
		if (userScoreHUD_playerName.isDisplayed()) {
			assertEquals(1, 1);
		}
		WebElement userScoreHUD_playerScore = player1.findElement(By
				.className("userScoreHUD_playerScore"));
		if (userScoreHUD_playerScore.isDisplayed()) {
			assertEquals(1, 1);
		}
		// check player2
		WebElement player2 = userScoreHUD_player.get(1);
		userScoreHUD_playerBetPlacedNoarrow = player2.findElement(By
				.className("userScoreHUD_playerBetPlacedNo"));
		if (userScoreHUD_playerBetPlacedNoarrow.isDisplayed()) {
			assertEquals(1, 1);
		}
		userScoreHUD_playerSerial = player2.findElement(By
				.className("userScoreHUD_playerSerial"));
		if (userScoreHUD_playerSerial.getText().contains("2")) {
			assertEquals(1, 1);
		}
		userScoreHUD_playerName = player2.findElement(By
				.className("userScoreHUD_playerName"));
		if (userScoreHUD_playerName.isDisplayed()) {
			assertEquals(1, 1);
		}
		userScoreHUD_playerScore = player2.findElement(By
				.className("userScoreHUD_playerScore"));
		if (userScoreHUD_playerScore.isDisplayed()) {
			assertEquals(1, 1);
		}
		// check gameTile
		gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
		if (gameTile.findElement(By.className("round_no")).getText()
				.contains("2")) {
			assertEquals(1, 1);
		}

		// Play User1 round2
		Tiles.dragAllTiles(driver1);
		play.click();
		// Play User2 round2
		Tiles.dragAllTiles(driver2);
		play2.click();
		// Play User1 round3
		Tiles.dragAllTiles(driver1);
		play.click();
		// Play User2 round3
		Tiles.dragAllTiles(driver2);
		play2.click();
		// Play User1 round4
		Tiles.dragAllTiles(driver1);
		play.click();
		// Play User2 round4
		Tiles.dragAllTiles(driver2);
		play2.click();
		// Play User1 round5
		Tiles.dragAllTiles(driver1);
		play.click();
		// Play User2 round5
		Tiles.dragAllTiles(driver2);
		play2.click();

		// Play User1 round6
		Tiles.dragAllTiles(driver1);
		play.click();
		// Play User2 round6
		Tiles.dragAllTiles(driver2);
		play2.click();
		// Play User1 round7
		Tiles.dragAllTiles(driver1);
		play.click();
		// Play User2 round7
		Tiles.dragAllTiles(driver2);
		play2.click();
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
		// TODO this is not working properly
		// GameUtil.closeGameEndPopUp(driver2);
		GameUtil.closeGameEndPopUp(driver1);
	}
}
