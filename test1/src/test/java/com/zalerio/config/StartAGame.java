package com.zalerio.config;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StartAGame {
	public static void createGame(WebDriver driver,int SelectedFriends[])
	{
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {}
		WebElement a = driver.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		// select n friends
		for(int i=0;i<SelectedFriends.length;i++)
		{	
		WebElement friend = selectListOfButtons.get(SelectedFriends[i]).findElement(By.tagName("a"));
		friend.click();
		}
		//send challenge
		WebElement sendChallengeButton = driver.findElement(By
						.id("sendinvite"));
		sendChallengeButton.click();
		System.out.println("challenge sent");
		Popup.closePopup(driver);
	}

}
