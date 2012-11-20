package com.zalerio.test;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.zalerio.config.GameUtil;
import com.zalerio.config.Popup;

public class Try {
//	@Test
	public void createGame() throws InterruptedException
	{	
		int friendPosition=2;
		
		WebDriver driver1=new FirefoxDriver();
		//login User1
		driver1.get("http://apps.facebook.com/zalerio/?force=play");
		Thread.sleep(8000);
		WebElement login_form=driver1.findElement(By.id("login_form"));
		WebElement email=driver1.findElement(By.id("email"));
		email.sendKeys("abhilashbhaduri@gmail.com");
		WebElement pass=driver1.findElement(By.id("pass"));
		pass.sendKeys("16081989");
		login_form.submit();
		driver1.switchTo().frame("iframe_canvas");
		GameUtil.clickPlayHereForMultiTabIssue(driver1);
		GameUtil.closeGameEndPopUp(driver1);
		Thread.sleep(10000);
		System.out.print("Logged in");
	
	//create new game at friendPosition
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		Thread.sleep(5000);
	
		WebElement a = driver1.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		WebElement fr1 = selectListOfButtons.get(friendPosition).findElement(
				By.className("select_button"));
		fr1.click();
		System.out.println("fr1 selected");
		
	//load the game on main screen
		// Click Send Challenge
		WebElement sendChallengeButton = driver1.findElement(By.id("sendinvite"));
		sendChallengeButton.click();
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) driver1;
		js.executeScript("window.scrollTo(0,0)");
		
		//verify pop up and load the game
		Popup.verifyPopup(driver1, "Challenge has been sent. Would you like to start playing.");
		Thread.sleep(5000);
		WebElement gamewall=driver1.findElement(By.id("gamewall"));
		WebElement gameBetPanel=driver1.findElement(By.id("gameBetPanel"));	
		Random randomGenerator = new Random();
		for(int i=0;i<9;i++)
		{
			//get bet
			String betid="bet_"+i;
			int randomInt;
			WebElement bet=gameBetPanel.findElement(By.id(betid));
			//get position
			WebElement position;
			String droppable;
			do{
			 randomInt = randomGenerator.nextInt(483);
			String betPos="boardTile-"+randomInt;
			System.out.print(betPos +"bet position");
			position=gamewall.findElement(By.id(betPos)); 
			//check if it is droppable
			
			droppable=position.getAttribute("droppable");
			if(droppable.contains("2"))
			{	
			//place bet
			 Actions builderN = new Actions(driver1);  // Configure the Action  
			 Action dragAndDropN = builderN.clickAndHold(bet)  
			    .moveToElement(position)  
			    .release(position)  
			    .build();  // Get the action  
			    dragAndDropN.perform();
			    try {
					Thread.sleep(5000);
				} catch (InterruptedException e)
				{e.printStackTrace();}
			}
			}	while(droppable.contains("-1"));
		}
	}
}
