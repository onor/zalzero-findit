package com.zalerio.test;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.zalerio.config.GameUtil;

public class CheatSheetTest extends ZalerioBaseTest {

	public CheatSheetTest(String os, String browser, String version,
			String userid, String password) {
		super(os, browser, version, userid, password);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void openAndScrollCheatSheet() {
		GameUtil.closeGameEndPopUp(driver);
		// class friendChallenge
		// class friendChallenge back_to_the_game
		WebElement cheatSheetDiv = driver.findElement(By
				.className("friendChallenge"));

		WebElement backToGameDiv = driver.findElement(By.id("cheat-sheet"));
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
