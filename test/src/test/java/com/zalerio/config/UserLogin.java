package com.zalerio.config;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class UserLogin {
	public static void Olduserlogin(WebDriver driver, String emailid,
			String password)  {
		driver.get("https://www.facebook.com/");
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
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.get("http://apps.facebook.com/zalerio/?force=play");
		
		/* new WebDriverWait(driver,20).until(new ExpectedCondition<Boolean>() {

        public Boolean apply(WebDriver driver) {                
           boolean status=driver.findElement(By.id("iframe_canvas")).isDisplayed();
           return status;
           
        }
    });*/
		WebElement iframe=driver.findElement(By.id("iframe_canvas"));
		driver.switchTo().frame(iframe);
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
	//	driver.get("http://apps.facebook.com/zalzerostaging/?force=play");
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
