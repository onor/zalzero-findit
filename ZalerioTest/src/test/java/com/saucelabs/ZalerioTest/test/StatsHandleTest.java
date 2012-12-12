package com.saucelabs.ZalerioTest.test;




import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class StatsHandleTest extends Zalerio1UserBaseTest {
	
	
public StatsHandleTest(String os, String browser, String version
			) {
		super(os, browser, version);//, userid, password);
		// TODO Auto-generated constructor stub
	}
	//@Test
	public void statsClick() {
		
		// stats
		WebElement stats = driver1.findElement(By.id("bottomHUDbuttons-stats"));
		stats.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// my levels
		WebElement myLevels = driver1.findElement(By.id("mylevel_a"));
		myLevels.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// learn-performance-level
		WebElement learn_performance_level = driver1.findElement(By
				.id("learn-performance-level"));
		learn_performance_level.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// Close performance window
		WebElement perform_close = driver1.findElement(By
				.id("close-performance-level"));
		perform_close.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		WebElement myGames = driver1.findElement(By.id("mygames_a"));
		myGames.click();
		//TODO
		// re match from left HUD
		
		// re-match cannot be done now- have some limitations
		//CLOSE stats s_friendpopup
		WebElement s_friendpopup=driver1.findElement(By.id("s_friendpopup"));
		WebElement close=s_friendpopup.findElement(By.id("close"));
		close.click();
	}

}
