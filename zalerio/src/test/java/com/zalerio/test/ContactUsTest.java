package com.zalerio.test;




import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ContactUsTest extends Zalerio1UserBaseTest {

	public ContactUsTest(String os, String browser, String version,
			String userid, String password) {
		super(os, browser, version, userid, password);
		// TODO Auto-generated constructor stub
	}

//	@Test
	public void trySendMail() {

		// click help
		WebElement helpButton = driver1.findElement(By.className("helpButton"));
		helpButton.click();
		// Contact Us
		WebElement contactUs = driver1.findElement(By.id("a-help-contactus"));
		contactUs.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// Enter first name
		// first_name
		WebElement first_name = driver1.findElement(By.name("first_name"));
		first_name.sendKeys("Abhilash");
		// last_name
		WebElement last_name = driver1.findElement(By.name("last_name"));
		last_name.sendKeys("Mobicules");
		// email
		WebElement email = driver1.findElement(By.name("email"));
		email.sendKeys("abhilash@mobicules.com");
		// subject
		WebElement subject = driver1.findElement(By.name("subject"));
		Select select = new Select(subject);
		select.selectByVisibleText("Issue with game");
		// body
		WebElement body = driver1.findElement(By.name("body"));
		body.sendKeys("Testing Contact Us");
		/*
		 * TODO //verifyCode-----work has to be done on this-------------------
		 * WebElement verifyCode =driver.findElement(By.name("verifyCode"));
		 * verifyCode.sendKeys(" ");
		 */
		// upload_help_contactus
		WebElement send = driver1.findElement(By.id("upload_help_contactus"));
		send.click();
		// close window

		WebElement s_friendpopup = driver1.findElement(By.id("s_friendpopup"));
		WebElement close = s_friendpopup.findElement(By.id("close"));
		close.click();
	}
}
