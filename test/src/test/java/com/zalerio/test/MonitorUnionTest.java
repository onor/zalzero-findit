package com.zalerio.test;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.firefox.FirefoxDriver;




public class MonitorUnionTest// extends ZalerioBaseTest
{
	@Test
		 public void unionMonitor()
			{
				int status=0;
			//	System.setProperty("webdriver.chrome.driver", "C:/ChromeDriver/chromedriver");
			//	WebDriver driver= new ChromeDriver();
				WebDriver driver=new FirefoxDriver();
			//	WebDriver driver=new OperaDriver();
				driver.get("http://apps.facebook.com/zalzerostaging/?force=play");
				WebElement login_form=driver.findElement(By.id("login_form"));
				WebElement email=driver.findElement(By.id("email"));
				email.sendKeys("griffinsingh1@gmail.com");
				WebElement pass=driver.findElement(By.id("pass"));
				pass.sendKeys("griffinsingh1");
				login_form.submit();
				driver.switchTo().frame("iframe_canvas");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				// verify username is visible
				WebElement leftHUD= driver.findElement(By.id("leftHUD"));
				WebElement gameInfoPanel=leftHUD.findElement(By.className("gameInfoPanel"));
				WebElement gameInfoDiv=gameInfoPanel.findElement(By.className("gameInfoDiv"));
				WebElement userArea=gameInfoDiv.findElement(By.className("userArea"));
				WebElement userAreaName=userArea.findElement(By.id("currentUserName"));
				if(userAreaName.isDisplayed())
				{
					String name=userAreaName.getText();
					if(name.contains("Singh"))
					{
						status=1;
						System.out.println("name is "+name+" status "+status);
					}	
				}
				//verify right HUD game carousals
				
				WebElement rightHUD =driver.findElement(By.id("rightHUD"));
				WebElement rightHUD_yourturn=rightHUD.findElement(By.id("rightHUD-yourturn"));
				
				 
				try
				{
					WebElement userArea1=rightHUD_yourturn.findElement(By.className("userArea"));
					if(userArea1.findElement(By.className("round_no")).isDisplayed())
					{
						status=2;
						System.out.println("round no found  "+ status +"status");
					}
				}catch(Exception e)
				{
					//else create a new game and verify right HUD carousals
					System.out.println("no game is found hence creating a new game");
					
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// new game creation
					WebElement startButton =driver.findElement(By.id("startButton"));
					startButton.click();
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					WebElement friendlist=driver.findElement(By.className("friendlist"));
					WebElement rep=friendlist.findElement(By.className("rep"));
					WebElement select_button=rep.findElement(By.tagName("a"));
					select_button.click();
					WebElement sendinvite=driver.findElement(By.id("sendinvite"));
					sendinvite.click();
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					WebElement okButton = driver.findElement(By.className("msgbox-ok"));
					if (okButton != null) {
						System.out.println("ok button found");
					}
					okButton.click();
					// check Right HUD
					 	rightHUD =driver.findElement(By.id("rightHUD"));
					 	rightHUD_yourturn=rightHUD.findElement(By.id("rightHUD-yourturn"));
					 	
					 	WebElement userArea2=rightHUD_yourturn.findElement(By.className("userArea"));
						if(userArea2.findElement(By.className("round_no")).isDisplayed())
						{
							status=2;
						}
					
				}
				System.out.print("status "+status);
				assertEquals(2,status);
			}

}
