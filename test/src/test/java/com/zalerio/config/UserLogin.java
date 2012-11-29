package com.zalerio.config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

//import com.zalerio.test.RatingScreenTest;

public class UserLogin {
	public static void Olduserlogin(WebDriver driver, String emailid,
			String password)  {
		driver.get(Config.TEST_APP_URL);//("http://apps.facebook.com/zalerio/?force=play");//
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement login_form = driver.findElement(By.id("login_form"));
		WebElement email = driver.findElement(By.id("email"));
		email.sendKeys(emailid);
		try{
		WebElement pass = driver.findElement(By.id("pass"));
		pass.sendKeys(password);
		}catch(Exception e)
		{}
		login_form.submit();
		driver.switchTo().frame("iframe_canvas");
		GameUtil.clickPlayHereForMultiTabIssue(driver);
	//	try {
	//		RatingScreenTest.closeGameEndPopupWithVerifyRating(driver);
	//	} catch (InterruptedException e) {
	//		}
		Popup.closePopup(driver);
		GameUtil.closeGameEndPopUp(driver);
	}
	public static void Newuserlogin(WebDriver driver, String emailid,
			String password){
	//	driver.get("http://apps.facebook.com/zalerio/?force=play");
		driver.get("http://apps.facebook.com/zalzerostaging/?force=play");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement login_form = driver.findElement(By.id("login_form"));
		WebElement email = driver.findElement(By.id("email"));
		email.sendKeys(emailid);
		WebElement pass = driver.findElement(By.id("pass"));
		pass.sendKeys(password);
		login_form.submit();
		
		
	}
}
