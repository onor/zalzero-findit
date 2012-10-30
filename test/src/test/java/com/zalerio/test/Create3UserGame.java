package com.zalerio.test;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.zalerio.config.GameUtil;
import com.zalerio.config.Popup;
import com.zalerio.config.VerifyFeatures;

public class Create3UserGame 
{	//verify username
	//left HUD carousal for friend decline
	//Game confirmation Pop up
	@Test
	public void createGame() throws InterruptedException
	{
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
		WebElement fr2 = selectListOfButtons.get(13).findElement(
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
		} catch (Exception f) {}
	
		

	}
}
