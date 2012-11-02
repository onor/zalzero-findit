package com.zalerio.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.zalerio.config.GameUtil;

public class HelpTest extends ZalerioBaseTest  {
	public HelpTest(String os, String browser, String version, String userid,
			String password) {
		super(os, browser, version, userid, password);
		// TODO Auto-generated constructor stub
	}

	//@Test
	public void tryOptions() {
		GameUtil.closeGameEndPopUp(driver);
		// help
		WebElement helpButton = driver.findElement(By.className("helpButton"));
		helpButton.click();
		// rules-content
		WebElement ruleContent = driver.findElement(By
				.className("rules-content"));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// tutorial_startgame
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		WebElement gameRulesStep1 = ruleContent.findElement(By
				.id("tutorial_startgame"));
		gameRulesStep1.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// tutorial_taketurn
		WebElement gameRulesStep2 = ruleContent.findElement(By
				.id("tutorial_taketurn"));
		gameRulesStep2.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// tutorial_scoring
		WebElement gameRulesStep3 = ruleContent.findElement(By
				.id("tutorial_scoring"));
		gameRulesStep3.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// tutorial_jokers
		WebElement gameRulesStep4 = ruleContent.findElement(By
				.id("tutorial_jokers"));
		gameRulesStep4.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		//close window
		WebElement s_friendpopup=driver.findElement(By.id("s_friendpopup"));
		WebElement close=s_friendpopup.findElement(By.id("close"));
		close.click();
	}
}
