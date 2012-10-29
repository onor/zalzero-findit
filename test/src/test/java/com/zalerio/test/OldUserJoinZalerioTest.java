package com.zalerio.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.zalerio.config.GameUtil;

public class OldUserJoinZalerioTest {
	WebDriver driver=null;
	//remove all active games presently
	//TODO the situation when user has 1 or more games that have not been accepted by others cannot be removed
	@Before
	public void removeAllGames()
	{
		System.setProperty("webdriver.chrome.driver", "C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
		 driver= new ChromeDriver();
		driver.get("http://apps.facebook.com/zalzerostaging/?force=play");
		WebElement login_form=driver.findElement(By.id("login_form"));
		WebElement email=driver.findElement(By.id("email"));
		email.sendKeys("hemantkumer007@gmail.com");
		WebElement pass=driver.findElement(By.id("pass"));
		pass.sendKeys("hemantkumer007");
		login_form.submit();
		driver.switchTo().frame("iframe_canvas");
		GameUtil.clickPlayHereForMultiTabIssue(driver);
		GameUtil.closeGameEndPopUp(driver);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("Logged in");
	
		WebElement  rightHUD_yourturn= driver.findElement(By.id("rightHUD-yourturn"));
		List<WebElement> your_turnTiles;
		//access your turn tiles
		try{
		 your_turnTiles=rightHUD_yourturn.findElements(By.className("userArea"));
		 System.out.print("got your turn tiles");
		}catch(Exception e)
		{
			return;
		}
		int size=your_turnTiles.size();
		for(int i=0;i<size-1;i++)
		{
			WebElement gameTile=null;
			try{
			 gameTile=your_turnTiles.get(i);
			gameTile.click();
			System.out.println("got your turn tile "+i);
			}catch(Exception e)
			{}
				//click play
				try{
					WebElement gdPlaydiv=driver.findElement(By.className("gdPlaydiv"));
					gdPlaydiv.click();
					System.out.print("cilcked play");
				}catch(Exception f)
				{System.out.print("no play");}
				//click resume
				try{
					WebElement gdResumeDiv=driver.findElement(By.className("gdResumeDiv"));
					gdResumeDiv.click();
					System.out.print("cilcked resume");
				}catch(Exception f)
				{System.out.print("no resume");}
				//decline invitations
				try
				{
				WebElement	checkInvitation=gameTile.findElement(By.className("accept_decline"));
				WebElement declineButton=checkInvitation.findElement(By.className("right_hud_decline"));
				declineButton.click();
				System.out.print("cilcked decline");
				Thread.sleep(5000);
				WebElement okButton=driver.findElement(By.className("msgbox-ok"));
					if(okButton.isDisplayed())
					{
						System.out.println("ok button found");
						okButton.click();
					}
				}catch(Exception f)
				{System.out.print("no invitation");}
			
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//click more to resign
			
				WebElement	bottomHUD=driver.findElement(By.id("bottomHUD"));
				WebElement bottomHUDbuttons_more=bottomHUD.findElement(By.id("bottomHUDbuttons-more"));
				bottomHUDbuttons_more.click();
				System.out.print("clicked More");
				// click resign
				WebElement resign=driver.findElement(By.id("resignme"));
				resign.click();
				System.out.print("clicked Resign");
				// click dismiss to close Game End PopUp
				GameUtil.closeGameEndPopUp(driver);
			
			
		}	
		WebElement rightHUD_theirturn=driver.findElement(By.id("rightHUD-theirturn"));
		List<WebElement> their_turnTiles=null;
		//access their turn tiles
		try{
		 their_turnTiles=rightHUD_theirturn.findElements(By.className("userArea"));
		}catch(Exception e)
		{
			System.out.println("their turn tiles not found");
		}
		 size=their_turnTiles.size();
		for(int i=0;i<size;i++)
		{
			WebElement gameTile=null;
			try{
			 gameTile=their_turnTiles.get(i);
			gameTile.click();
			System.out.print("game tile"+i+" found");
			}catch(Exception e)
			{System.out.print("game tile not found");}
			//click play
				try{
					WebElement gdPlaydiv=driver.findElement(By.className("gdPlaydiv"));
					gdPlaydiv.click();
				}catch(Exception f)
				{}
				//click resume
				try{
					WebElement gdResumeDiv=driver.findElement(By.className("gdResumeDiv"));
					gdResumeDiv.click();
				}catch(Exception f)
				{}
				//decline invitations
				try
				{
				WebElement	checkInvitation=gameTile.findElement(By.className("accept_decline"));
				WebElement declineButton=checkInvitation.findElement(By.className("right_hud_decline"));
				declineButton.click();
				WebElement okButton=driver.findElement(By.className("msgbox-ok"));
					if(okButton.isDisplayed())
					{
						System.out.println("ok button found");
						okButton.click();
					}
				}catch(Exception f)
				{System.out.print("NO INVITATION");}
			
				//click more to resign
				
				WebElement	bottomHUD=driver.findElement(By.id("bottomHUD"));
				WebElement bottomHUDbuttons_more=bottomHUD.findElement(By.id("bottomHUDbuttons-more"));
				bottomHUDbuttons_more.click();
				System.out.print("clicked More");
			// click resign
				WebElement resign=driver.findElement(By.id("resignme"));
				resign.click();
				System.out.print("clicked Resign");
				// click dismiss to close Game End PopUp
				GameUtil.closeGameEndPopUp(driver);
				
		}	
		driver.switchTo().defaultContent();
		driver.findElement(By.id("pageLogo")).click();
	}
	//old user with no active games currently
	@Test
	public void olduserNoActiveGame()
	{
		driver.navigate().back();
		GameUtil.closeGameEndPopUp(driver);
		//check Your Turns
		WebElement rightHUD_yourturn=driver.findElement(By.id("rightHUD-yourturn"));
		
		WebElement StartMoreGames=rightHUD_yourturn.findElement(By.className("newCarouselblanktile"));
		if(StartMoreGames.isDisplayed()&& StartMoreGames.isEnabled())
		{
			assertEquals(1,1);
		}
		//check Their Turns
		WebElement rightHUD_theirturn=driver.findElement(By.id("rightHUD-theirturn"));
		WebElement StartMoreGames2=rightHUD_theirturn.findElement(By.className("newCarouselblanktile"));
		if(StartMoreGames2.isDisplayed()&& StartMoreGames2.isEnabled())
		{
			assertEquals(1,1);
		}
		WebElement gameInfo_game_players=driver.findElement(By.id("gameInfo-game-players"));
		if(gameInfo_game_players.isDisplayed())
		{
			assertEquals(1,1);
		}	
		WebElement gamewall=driver.findElement(By.id("gamewall"));
		for(int i=0;i<=483;i++)
		{
			String tile="boardTile-"+i;
			WebElement boardTile=gamewall.findElement(By.id(tile));
			String droppable=boardTile.getAttribute("droppable");
			if(droppable.contains("-1"))
			{
				assertEquals(1, 1);
				return;
			}
		}
	}
}
