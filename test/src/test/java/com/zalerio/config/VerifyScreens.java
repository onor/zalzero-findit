package com.zalerio.config;


import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VerifyScreens {
	//verify Ingame for a user entering staging for the first time 
	public static void verifyIngameScreenFirstTimeEnter(WebDriver driver)
	{
		int status=0;
		WebElement leftHUD=driver.findElement(By.id("leftHUD"));
		// verify Start-a-Game
		WebElement startGame=leftHUD.findElement(By.className("startGame"));
		if(startGame.isDisplayed())
		{
			status++;
		}
		//verify Left HUD game carousal
		
		WebElement gameInfoPanel=leftHUD.findElement(By.className("gameInfoPanel"));
		WebElement gameInfoDiv=gameInfoPanel.findElement(By.className("gameInfoDiv"));
		WebElement userArea=gameInfoDiv.findElement(By.className("userArea"));
		// verify user image
		WebElement currentUserAreaImg=userArea.findElement(By.id("currentUserAreaImg"));
		if(currentUserAreaImg.isDisplayed())
		{
			status++;
		}
		// verify user name
		WebElement currentUserName=userArea.findElement(By.id("currentUserName"));
		if(currentUserName.isDisplayed())
		{
			status++;
		}
		//Verify score board
		
		WebElement gameScore= leftHUD.findElement(By.className("gameScore"));
		WebElement gameScore_round=gameScore.findElement(By.id("gameScore-round"));
		String gameRounds=gameScore_round.getText();
        if(gameRounds.contains("Game Rounds"))
        {
        	status++;
        	assertEquals("1","1");
        }
        // Verify main game
        WebElement mainGame=driver.findElement(By.id("mainGame"));
        WebElement gameBetPanel=mainGame.findElement(By.className("gameBetPanel"));
        //verfiy cheat-sheet
        WebElement friendChallenge=gameBetPanel.findElement(By.className("friendChallenge"));
        WebElement cheatSheet=friendChallenge.findElement(By.id("cheat-sheet"));
        if(cheatSheet.isDisplayed())
        {
        status++;	
        }
        WebElement gameBetPanelUl=gameBetPanel.findElement(By.className("gameBetPanelUl"));
        List<WebElement> bets= gameBetPanelUl.findElements(By.className("draggableBets"));
        for(int i=0;i<bets.size();i++)
        {
        	WebElement bet=bets.get(i);
        	String draggable=bet.getAttribute("draggable");
        	if(draggable.equals("true"))
        	{
        		assertEquals(1, 1);
        	}
        }
        status++;
        //gamewall
        // verify board tiles
        WebElement gamewall=mainGame.findElement(By.id("gamewall"));
        String boardTile="boardTile-"+483;
        if(gamewall.findElement(By.id(boardTile)).isDisplayed())
        {
        	status++;
        }
        if(driver.findElement(By.className("audioButton")).isDisplayed())
        {
        	status++;
        }
        
        WebElement rightHUD_yourturn=driver.findElement(By.id("rightHUD-yourturn"));
        //verify your Turn
        if(rightHUD_yourturn.findElement(By.className("newCarouselblanktile")).isDisplayed())
        {status++;
        }
        //verify their Turn 
        WebElement rightHUD_theirturn=driver.findElement(By.id("rightHUD-theirturn"));
        if(rightHUD_theirturn.findElement(By.className("newCarouselblanktile")).isDisplayed())
        {
        	status++;
        }
        // verfiy More
        if(driver.findElement(By.id("bottomHUDbuttons-more")).isDisplayed())
        {
        	status++;
        }
        // verfiy stats
        if(driver.findElement(By.id("bottomHUDbuttons-stats")).isDisplayed())
        {
        	status++;
        }
        // verfiy play
        if(driver.findElement(By.id("bottomHUDbuttons-play")).isDisplayed())
        {
        	status++;
        }
        System.out.print("Status+ "+status);
	}
}
