package com.zalerio.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.zalerio.config.GameUtil;

public class StatsHandleTest extends ZalerioBaseTest {
	
	@Test
	public void statsClick() {
		GameUtil.closeGameEndPopUp(driver);
		// stats
		WebElement stats = driver.findElement(By.id("bottomHUDbuttons-stats"));
		stats.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// my levels
		WebElement myLevels = driver.findElement(By.id("mylevel_a"));
		myLevels.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// learn-performance-level
		WebElement learn_performance_level = driver.findElement(By
				.id("learn-performance-level"));
		learn_performance_level.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// Close performance window
		WebElement perform_close = driver.findElement(By
				.id("close-performance-level"));
		perform_close.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		WebElement myGames = driver.findElement(By.id("mygames_a"));
		myGames.click();
		//TODO
		// re match from left HUD
		
		// re-match cannot be done now- have some limitations
		//CLOSE stats s_friendpopup
		WebElement s_friendpopup=driver.findElement(By.id("s_friendpopup"));
		WebElement close=s_friendpopup.findElement(By.id("close"));
		close.click();
	}

}
