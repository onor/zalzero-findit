package com.zalerio.test;


import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GameEnterTest extends ZalerioBaseTest {
	

/*	public GameEnterTest(String os, String browser, String version,
			String user1id, String user2id, String password) {
		super(os, browser, version, user1id, user2id, password);
		// TODO Auto-generated constructor stub
	}
*/
	@Test
	public  void nonSecuredGameOpenTest() {
		
		driver2.quit();
		// Click Start a New Game
	/*	WebElement startButton = driver1.findElement(By.id("startButton"));
		if(startButton.isDisplayed())
		{
			String jobID = ( driver1).getSessionId().toString();
	        SauceREST client = new SauceREST("cloudbees_zalzero", "15b88203-7a0f-4339-804a-fffb6abb2e3c");
	        Map<String, Object>sauceJob = new HashMap<String, Object>();
	        sauceJob.put("name", "Test method: "+testName.getMethodName());
	        try {
				client.jobPassed(jobID);
				 client.updateJobInfo(jobID, sauceJob);
			} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
			}
	       
		}*/
	}
	
	@Test
	public void nonSecuredStartANewGame() {
		// Click Start a New Game
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement findFriendsHeader = driver1.findElement(By.className("findfriend"));
		assertEquals(findFriendsHeader.isDisplayed(),true);
	}
	

}