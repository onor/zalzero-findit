import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class MonitorUnionTest {
	@Test
	public static void main()
	{
		int status=0;
		WebDriver driver= new FirefoxDriver();
		driver.get("http://apps.facebook.com/zalzerostaging/?force=play");
		WebElement login_form=driver.findElement(By.className("login_form"));
		WebElement email=driver.findElement(By.id("email"));
		email.sendKeys("abhilashbhaduri@gmail.com");
		WebElement pass=driver.findElement(By.id("pass"));
		pass.sendKeys("16081989");
		login_form.submit();
		driver.switchTo().frame("iframe_canvas");
		// verify username is visible
		WebElement leftHUD= driver.findElement(By.id("leftHUD"));
		WebElement gameInfoPanel=leftHUD.findElement(By.className("gameInfoPanel"));
		WebElement gameInfoDiv=gameInfoPanel.findElement(By.className("gameInfoDiv"));
		WebElement userArea=gameInfoDiv.findElement(By.className("userArea"));
		WebElement userAreaName=userArea.findElement(By.id("userAreaName"));
		if(userAreaName.isDisplayed())
		{
			String name=userAreaName.getText();
			if(name.contains(""))
			{
				status=1;
			}	
		}
		//verify right HUD game carousals
		WebElement rightHUD =driver.findElement(By.id("rightHUD"));
		WebElement rightHUD_yourturn=rightHUD.findElement(By.id("rightHUD-yourturn"));
		WebElement yourturn_label=rightHUD_yourturn.findElement(By.id("yourturn-label"));
		 
		try
		{
			WebElement userArea1=yourturn_label.findElement(By.className("userArea"));
			if(userArea1.findElement(By.className("round_no")).isDisplayed())
			{
				status=2;
			}
		}catch(Exception e)
		{
			//else create a new game and verify right HUD carousals
			
			// new game creation
			WebElement startButton =driver.findElement(By.id("startButton"));
			startButton.click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			WebElement friendlist=driver.findElement(By.className("friendlist"));
			WebElement rep=friendlist.findElement(By.className("rep"));
			WebElement select_button=rep.findElement(By.tagName("a"));
			select_button.click();
			WebElement sendinvite=driver.findElement(By.id("sendinvite"));
			sendinvite.click();
			// check Right HUD
			 	rightHUD =driver.findElement(By.id("rightHUD"));
			 	rightHUD_yourturn=rightHUD.findElement(By.id("rightHUD-yourturn"));
			 	yourturn_label=rightHUD_yourturn.findElement(By.id("yourturn-label"));
			 	WebElement userArea1=yourturn_label.findElement(By.className("userArea"));
				if(userArea1.findElement(By.className("round_no")).isDisplayed())
				{
					status=2;
				}
			System.out.print("status "+status);
		}
	}

}
