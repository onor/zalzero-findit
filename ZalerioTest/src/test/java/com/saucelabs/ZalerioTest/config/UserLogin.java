package com.saucelabs.ZalerioTest.config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UserLogin {
	public static void Olduserlogin(WebDriver driver, String emailid,
			String password, String url) {
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
		try {
			WebElement pass = driver.findElement(By.id("pass"));
			pass.sendKeys(password);
		} catch (Exception e) {
		}
		login_form.submit();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.get(url);

		/*
		 * new WebDriverWait(driver,20).until(new ExpectedCondition<Boolean>() {
		 * 
		 * public Boolean apply(WebDriver driver) { boolean
		 * status=driver.findElement(By.id("iframe_canvas")).isDisplayed();
		 * return status;
		 * 
		 * } });
		 */
		WebElement iframe = driver.findElement(By.id("iframe_canvas"));
		driver.switchTo().frame(iframe);
		// multi tab error page handle
		boolean status = GameUtil.clickPlayHereForMultiTabIssue(driver);
		// try {
		// RatingScreenTest.closeGameEndPopupWithVerifyRating(driver);
		// } catch (InterruptedException e) {
		// }
		if (status == true) {
			driver.navigate().refresh();
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			iframe = driver.findElement(By.id("iframe_canvas"));
			driver.switchTo().frame(iframe);
		}
		// game formation popup
		try {
			WebElement invitestatus = driver.findElement(By.id("invitestatus"));
			WebElement score_friendpopup = invitestatus.findElement(By
					.id("score_friendpopup"));
			WebElement close = score_friendpopup.findElement(By.id("close"));
			close.click();
		} catch (Exception e) {

		}
		// insufficient user popup
		Popup.closePopup(driver);
		// game end popup close
		GameUtil.closeGameEndPopUp(driver);
	}

	public static void Newuserlogin(WebDriver driver, String emailid,
			String password, String url) {
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
		try {
			WebElement pass = driver.findElement(By.id("pass"));
			pass.sendKeys(password);
		} catch (Exception e) {
		}
		login_form.submit();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.get(url);

	}
	public static void logout(WebDriver driver)
	{
		driver.switchTo().defaultContent();
		WebElement userNavigationLabel=driver.findElement(By.id("userNavigationLabel"));
		WebElement logout_form=userNavigationLabel.findElement(By.id("logout_form"));
		logout_form.submit();
	}
}
