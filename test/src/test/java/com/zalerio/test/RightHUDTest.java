package com.zalerio.test;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.zalerio.config.GameUtil;

public class RightHUDTest extends ZalerioBaseTest {
	//@Test
	public void rightHUDTest() {
		GameUtil.closeGameEndPopUp(driver);
		// right HUD
		// your turn -check game tiles and click
		WebElement rightHUD_yourturn = driver.findElement(By
				.id("rightHUD-yourturn"));
		// check if your turn is empty or not
		try { // if no game tiles are available
			WebElement newCarouselblanktile = rightHUD_yourturn.findElement(By
					.className("newCarouselblanktile"));
			newCarouselblanktile.click();
			//start a game window comes up.
			// close the window-close button 
			WebElement friendpopup = driver.findElement(By.id("friendpopup"));
			WebElement closeButton = friendpopup.findElement(By.tagName("a"));
			closeButton.click();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			// if game tiles are available-- try Play,Resume ,Return,Remind to
			// Play
			List<WebElement> gameTiles = rightHUD_yourturn.findElements(By
					.className("userArea"));
			int no_of_games_yourTurn = gameTiles.size();
			for (int i = 0; i < no_of_games_yourTurn; i++) {
				//check if it is an invitation or not
				//class-accept_decline
				try{
				WebElement accept_decline=gameTiles.get(i).findElement(By.className("accept_decline"));
				continue;
				}catch(Exception f){}
				gameTiles.get(i).click();
				
				// Click Play or Resume whichever is available
				try {
					// click play button
					driver.findElement(By.className("gdPlaydiv")).click();
				} catch (org.openqa.selenium.NoSuchElementException f) {
					// click resume button
					(driver.findElement(By.className("gdResumeDiv"))).click();
				}
				// remind in 2nd last game
				if (i == no_of_games_yourTurn - 2) {
					// click the tile
					gameTiles.get(i).click();
					// click remind
					(driver.findElement(By.className("gdReminddiv"))).click();
					// wait
					try {
						Thread.sleep(4000);
					} catch (InterruptedException f) {
					}
					// click ok on popup
					WebElement okButton = driver.findElement(By
							.className("msgbox-ok"));
					if (okButton != null) {
						System.out.println("ok button found");
					}
					okButton.click();
				}
				if (i == no_of_games_yourTurn - 1) {
					// click the tile
					gameTiles.get(i).click();
					// gdReturndiv
					(driver.findElement(By.className("gdReturndiv"))).click();
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException f) {
				}
			}

		}
		//TODO ----------- their turns is not working
		// Their turns- check if game tiles present or not
		WebElement rightHUD_theirturn = driver.findElement(By
				.id("theirturn-label"));
		// check if your turn is empty or not
		try { // if no game tiles are available
			WebElement newCarouselblanktile = rightHUD_theirturn.findElement(By
					.className("newCarouselblanktile"));
			newCarouselblanktile.click();
			//start a game window comes up.
			// close the window-close button 
			WebElement friendpopup = driver.findElement(By.id("friendpopup"));
			WebElement closeButton = friendpopup.findElement(By.tagName("a"));
			closeButton.click();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			// if game tiles are available-- try Play,Resume ,Return,Remind to
			// Play
			List<WebElement> gameTiles2 = rightHUD_theirturn.findElements(By
					.className("userArea"));
			int no_of_games_theirTurn = gameTiles2.size();
			for (int i = 0; i < no_of_games_theirTurn; i++) {
				gameTiles2.get(i).click();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException f) {}
				try {
					// click play button
					driver.findElement(By.className("gdPlaydiv")).click();
				} catch (org.openqa.selenium.NoSuchElementException f) {
					// click resume button
					(driver.findElement(By.className("gdResumeDiv"))).click();
				}
				// remind in 2nd last game
				if (i == no_of_games_theirTurn - 2) {
					// click the tile
					gameTiles2.get(i).click();
					// click remind
					(driver.findElement(By.className("gdReminddiv"))).click();
					// wait
					try {
						Thread.sleep(2000);
					} catch (InterruptedException f) {
					}
					// click ok on pop up
					WebElement okButton = driver.findElement(By
							.className("msgbox-ok"));
					if (okButton != null) {
						System.out.println("ok button found");
					}
					okButton.click();
				}
				//click return in last game
				if (i == no_of_games_theirTurn - 1) {
					// click the tile
					gameTiles2.get(i).click();
					// click return
					(driver.findElement(By.className("gdReturndiv"))).click();
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException f) {}
				}
			}

		}
	}

