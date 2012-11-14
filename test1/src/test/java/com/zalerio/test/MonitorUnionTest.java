package com.zalerio.test;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.firefox.FirefoxDriver;




public class MonitorUnionTest extends ZalerioBaseTest{
	@Test
	public void unionMonitor()
	{
		int status=0;
	//	System.setProperty("webdriver.chrome.driver1", "C:/ChromeDriver/chromedriver");
	//	WebDriver driver1= new ChromeDriver();
	//	WebDriver driver1=new FirefoxDriver();
	//	WebDriver driver1=new OperaDriver();
		driver1.get("http://apps.facebook.com/zalerio/?force=play");
		WebElement login_form=driver1.findElement(By.id("login_form"));
		WebElement email=driver1.findElement(By.id("email"));
		email.sendKeys("griffinsingh1@gmail.com");
		WebElement pass=driver1.findElement(By.id("pass"));
		pass.sendKeys("griffinsingh1");
		login_form.submit();
		driver1.switchTo().frame("iframe_canvas");
		// verify username is visible
		WebElement leftHUD= driver1.findElement(By.id("leftHUD"));
		WebElement gameInfoPanel=leftHUD.findElement(By.className("gameInfoPanel"));
		WebElement gameInfoDiv=gameInfoPanel.findElement(By.className("gameInfoDiv"));
		WebElement userArea=gameInfoDiv.findElement(By.className("userArea"));
		WebElement userAreaName=userArea.findElement(By.id("currentUserName"));
		if(userAreaName.isDisplayed())
		{
			String name=userAreaName.getText();
			if(name.contains(""))
			{
				status=1;
			}	
		}
		//verify right HUD game carousals
		
		WebElement rightHUD =driver1.findElement(By.id("rightHUD"));
		WebElement rightHUD_yourturn=rightHUD.findElement(By.id("rightHUD-yourturn"));
		
		 
		try
		{
			WebElement userArea1=rightHUD_yourturn.findElement(By.className("userArea"));
			if(userArea1.findElement(By.className("round_no")).isDisplayed())
			{
				status=2;
			}
		}catch(Exception e)
		{
			//else create a new game and verify right HUD carousals
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// new game creation
			WebElement startButton =driver1.findElement(By.id("startButton"));
			startButton.click();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			WebElement friendlist=driver1.findElement(By.className("friendlist"));
			WebElement rep=friendlist.findElement(By.className("rep"));
			WebElement select_button=rep.findElement(By.tagName("a"));
			select_button.click();
			WebElement sendinvite=driver1.findElement(By.id("sendinvite"));
			sendinvite.click();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			WebElement okButton = driver1.findElement(By.className("msgbox-ok"));
			if (okButton != null) {
				System.out.println("ok button found");
			}
			okButton.click();
			// check Right HUD
			 	rightHUD =driver1.findElement(By.id("rightHUD"));
			 	rightHUD_yourturn=rightHUD.findElement(By.id("rightHUD-yourturn"));
			 	
			 	WebElement userArea2=rightHUD_yourturn.findElement(By.className("userArea"));
				if(userArea2.findElement(By.className("round_no")).isDisplayed())
				{
					status=2;
				}
			
		}
		System.out.print("status "+status);
	}

}
