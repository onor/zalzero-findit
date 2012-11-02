package com.zalerio.config;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class Tiles {
	public static void drag1Tile(WebDriver driver, WebElement source_position,
			WebElement end_position) {
		Actions builder = new Actions(driver); // Configure the Action
		Action dragAndDrop = builder.clickAndHold(source_position)
				.moveToElement(end_position).release(end_position).build();
		dragAndDrop.perform();
	}

	// place all tiles in random positions by checking that it can be placed
	// over there
	public static void dragAllTiles(WebDriver driver)
			throws InterruptedException {
		Thread.sleep(2000);
		Random randomGenerator = new Random();
		
		for (int i = 0; i < 9; i++) {
			WebElement gamewall = driver.findElement(By.id("gamewall"));
			WebElement gameBetPanel = driver.findElement(By.id("gameBetPanel"));
			// get bet
			String betid = "bet_" + i;
			WebElement bet = gameBetPanel.findElement(By.id(betid));
			int randomInt;
			// get position
			WebElement position;
			String droppable;
			do {
				randomInt = randomGenerator.nextInt(480);
				String betPos = "boardTile-" + randomInt;
				position = gamewall.findElement(By.id(betPos));
				// check if it is droppable
				droppable = position.getAttribute("droppable");
				if (droppable.contains("2")) {
					// place bet
					Actions builder = new Actions(driver); // Configure the
															// Action
					Action dragAndDrop = builder.clickAndHold(bet)
							.moveToElement(position).release(position).build();
					dragAndDrop.perform();
					Thread.sleep(2000);
				}
			} while (droppable.contains("-1"));
		}
	}
}
