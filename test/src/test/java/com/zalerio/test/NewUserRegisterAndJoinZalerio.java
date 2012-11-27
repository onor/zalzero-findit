

import static org.junit.Assert.assertEquals;

import com.zalerio.config.Config;
import com.zalerio.config.UserLogin;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NewUserRegisterAndJoinZalerio // extends ZalerioBaseTest 
{
	@Test
	public void EnterGame()
	{
//email kfyryni_mcdonaldstein_1351753743@tfbnw.net
//password zalerio
	//login user
		WebDriver driver1=new FirefoxDriver();
	String emailid=Config.FB_NEWUSER_USERNAME;
	String password=Config.FB_NEWUSER_PASSWORD;
			UserLogin.Newuserlogin(driver1,emailid,password);
	//select join app
	WebElement grant_required_clicked=driver1.findElement(By.name("grant_required_clicked"));
	grant_required_clicked.click();
	//select Permissions
	WebElement grant_clicked=driver1.findElement(By.name("grant_clicked"));
	grant_clicked.click();
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//enter and verify  InGameScreen
	driver1.switchTo().frame("iframe_canvas");
	WebElement popup_wrapper=driver1.findElement(By.className("popup-wrapper"));
	assertEquals(popup_wrapper.isDisplayed(),true);
	WebElement start_popup=popup_wrapper.findElement(By.className("start-popup"));
	WebElement title=start_popup.findElement(By.className("title"));
	String welcomeTitle=title.getText();
	assertEquals(welcomeTitle,"Welcome to Zalerio!");
	WebElement left_button=start_popup.findElement(By.className("left-button"));
	left_button.click();
	}
	}
