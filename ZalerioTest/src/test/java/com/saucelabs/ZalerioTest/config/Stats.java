package com.saucelabs.ZalerioTest.config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.Assert.assertEquals;
public class Stats 
{
	public static void verifyGameAddToPastGames(WebDriver driver,String gameID) throws InterruptedException
	{
		//right_hud_xxx
		String gameIDLastPart=gameID.substring(10);
		//myLevel_apg_xx
		String expectedID="myLevel_apg_"+gameIDLastPart;
		//find location of carousal in stats
		WebElement bottomHUDbuttons=driver.findElement(By.className("bottomHUDbuttons"));
		WebElement bottomHUDbuttons_stats=bottomHUDbuttons.findElement(By.id("bottomHUDbuttons-stats"));
		bottomHUDbuttons_stats.click();
		Thread.sleep(2000);
		WebElement mygames_stats=driver.findElement(By.id("mygames_stats"));
		WebElement rip_win_lost_container=mygames_stats.findElement(By.id("rip_win_lost_container"));
		WebElement rip_won=rip_win_lost_container.findElement(By.id("rip_won"));
		WebElement jspContainer=rip_won.findElement(By.className("jspContainer"));
		WebElement jspPane=jspContainer.findElement(By.className("jspPane"));
		WebElement rip_won_apg=jspPane.findElement(By.id("rip_won_apg"));
		WebElement actualGameCarousal=rip_won_apg.findElement(By.id(expectedID));
		boolean status=actualGameCarousal.isDisplayed();
		assertEquals(status,true);
	}

}
