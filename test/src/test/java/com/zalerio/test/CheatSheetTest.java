package com.zalerio.test;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.zalerio.config.Config;
import com.zalerio.config.GameUtil;
import com.zalerio.config.UserLogin;

public class CheatSheetTest extends ZalerioBaseTest {

	public CheatSheetTest(String os, String browser, String version) {
		super(os, browser, version);
		
	}

//	@Test
	public void openAndScrollCheatSheet() {
		String emailid=Config.FB_SECURED_ACCOUNT_USERNAME;
		String password=Config.FB_SECURED_ACCOUNT_PASSWORD;
		UserLogin.Olduserlogin(driver1, emailid, password);
		GameUtil.closeGameEndPopUp(driver1);
		// class friendChallenge
		// class friendChallenge back_to_the_game
		WebElement cheatSheetDiv = driver1.findElement(By
				.className("friendChallenge"));

		WebElement backToGameDiv = driver1.findElement(By.id("cheat-sheet"));
		backToGameDiv.click();

		// Check if the button shows Back to Game

		String backToGame = cheatSheetDiv.getAttribute("class");
		assertEquals(backToGame, "friendChallenge back_to_the_game");

		// Click back to game
		backToGameDiv.click();

		// Check if the button shows Cheat Sheet
		String cheatSheet = cheatSheetDiv.getAttribute("class");
		assertEquals(cheatSheet, "friendChallenge");
		// scroll needs to be done
	}
}
