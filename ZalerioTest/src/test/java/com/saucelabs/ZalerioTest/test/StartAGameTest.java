package com.saucelabs.ZalerioTest.test;

import java.util.List;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.saucelabs.ZalerioTest.config.GameFeatures;


public class StartAGameTest extends Zalerio1UserBaseTest {
public StartAGameTest(String os, String browser, String version) {
		super(os, browser, version);//, userid, password);
		// TODO Auto-generated constructor stub
	}

@Test
		public void clickStartAGame() {
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

	}
@Test
	public void verifyPopUpOnNoFriendSelect() {
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// Click Send Challenge
		WebElement sendChallengeButton = driver1.findElement(By
				.id("sendinvite"));
		sendChallengeButton.click();
		// wait for pop up to arrive
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) driver1;
		js.executeScript("window.scrollTo(0,0)");

		WebElement okButton = driver1.findElement(By.className("msgbox-ok"));
		if (okButton != null) {
			System.out.println("ok button found");
		}
		okButton.click();
	}
@Test
	public void searchFriendsAndShowAll() {
		// Search For Friends by entering test (a-z;A-Z)
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		WebElement searchArea = driver1.findElement(By.id("findfriend"));
		searchArea.sendKeys("g");
		System.out.println("g");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {}
		searchArea.sendKeys("gr");
		System.out.println("gr");
		// show all
		WebElement showall = driver1.findElement(By.id("show_all_friends"));
		showall.click();
		System.out.println("showall clicked");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
@Test
	public void selectAndUnselect1Friend() {
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		WebElement a = driver1.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		WebElement fr1 = selectListOfButtons.get(2).findElement(
				By.className("select_button"));
		fr1.click();
		System.out.println("fr1 selected");
		// unselect 1 friend
		fr1.click();
		System.out.println("fr1 unselected");
	}
@Test
	public void createAGameWith5Friends() {
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		WebElement a = driver1.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		WebElement fr1 = selectListOfButtons.get(0).findElement(
				By.className("select_button"));
		// select 5 friends
		fr1.click();
		System.out.println("fr1 selected");
		WebElement fr2 = selectListOfButtons.get(1)
				.findElement(By.tagName("a"));
		fr2.click();
		System.out.println("fr2 selected");
		WebElement fr3 = selectListOfButtons.get(2)
				.findElement(By.tagName("a"));
		fr3.click();
		System.out.println("fr3 selected");
		WebElement fr4 = selectListOfButtons.get(3)
				.findElement(By.tagName("a"));
		fr4.click();
		System.out.println("fr4 selected");
		WebElement fr5 = selectListOfButtons.get(4)
				.findElement(By.tagName("a"));
		fr5.click();
		//send challenge
		WebElement sendChallengeButton = driver1.findElement(By
				.id("sendinvite"));
		sendChallengeButton.click();
		//check if modal dialog is seen
		try
		{
			// click Send Request on alert popup
			driver1.switchTo().defaultContent();
			Thread.sleep(5000);
			WebElement dialog_2 = driver1
					.findElement(By.className("generic_dialog"));
			WebElement generic_dialog_popup = dialog_2.findElement(By
					.className("generic_dialog_popup"));
			WebElement pop_container = generic_dialog_popup.findElement(By
					.className("pop_container"));
			WebElement pop_content = pop_container
					.findElement(By.id("pop_content"));
			WebElement dialog_buttons = pop_content.findElement(By
					.className("dialog_buttons"));
			WebElement rfloat = dialog_buttons.findElement(By.className("rfloat"));
			List<WebElement> uiButton = rfloat.findElements(By
					.className("uiButton"));
			uiButton.get(0).click();
			
				Thread.sleep(5000);
		}catch(Exception e)
		{}
		WebElement iframe = driver1.findElement(By.id("iframe_canvas"));
		driver1.switchTo().frame(iframe);
		System.out.println("challenge sent");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		// error pop up shown
		// click ok on pop up
		WebElement okButton = driver1.findElement(By.className("msgbox-ok"));
		okButton.click();
		System.out.println("ok button clicked");
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) driver1;
		js.executeScript("window.scrollTo(0,0)");
		// close the window-close button not working
		WebElement friendpopup = driver1.findElement(By.id("friendpopup"));
		WebElement closeButton = friendpopup.findElement(By.tagName("a"));
		// WebElement closeButton=driver.findElement(By.id("close"));
		closeButton.click();
	}
	@Test
	public void create2PlayerGame() {
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		WebElement a = driver1.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		
		 int friendPosition=GameFeatures.getFriendPosition();
		 
		WebElement fr1 = selectListOfButtons.get(friendPosition).findElement(
				By.className("select_button"));
		// select friend
		fr1.click();
		WebElement sendChallengeButton = driver1.findElement(By
				.id("sendinvite"));

		sendChallengeButton.click();
		//handle modal dialog if required 
		//check if modal dialog is seen
		try
		{
			// click Send Request on alert popup
			driver1.switchTo().defaultContent();
			Thread.sleep(5000);
			WebElement dialog_2 = driver1
					.findElement(By.className("generic_dialog"));
			WebElement generic_dialog_popup = dialog_2.findElement(By
					.className("generic_dialog_popup"));
			WebElement pop_container = generic_dialog_popup.findElement(By
					.className("pop_container"));
			WebElement pop_content = pop_container
					.findElement(By.id("pop_content"));
			WebElement dialog_buttons = pop_content.findElement(By
					.className("dialog_buttons"));
			WebElement rfloat = dialog_buttons.findElement(By.className("rfloat"));
			List<WebElement> uiButton = rfloat.findElements(By
					.className("uiButton"));
			uiButton.get(0).click();
				Thread.sleep(5000);
			WebElement iframe = driver1.findElement(By.id("iframe_canvas"));
			driver1.switchTo().frame(iframe);
		}catch(Exception e)
		{}
		WebElement iframe = driver1.findElement(By.id("iframe_canvas"));
		driver1.switchTo().frame(iframe);
		System.out.println("challenge sent");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) driver1;
		js.executeScript("window.scrollTo(0,0)");

	}
	@Test
	public void verifyClickOtherButtonsWhilePopUp() {
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		WebElement a = driver1.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		 int friendPosition=GameFeatures.getFriendPosition();
		WebElement fr1 = selectListOfButtons.get(friendPosition).findElement(
				By.className("select_button"));
		// select friend
		fr1.click();
		WebElement sendChallengeButton = driver1.findElement(By
				.id("sendinvite"));
        
		sendChallengeButton.click();
		//handle modal dialog if required 
		//check if modal dialog is seen
		try
		{
			// click Send Request on alert popup
			driver1.switchTo().defaultContent();
			Thread.sleep(5000);
			WebElement dialog_2 = driver1
					.findElement(By.className("generic_dialog"));
			WebElement generic_dialog_popup = dialog_2.findElement(By
					.className("generic_dialog_popup"));
			WebElement pop_container = generic_dialog_popup.findElement(By
					.className("pop_container"));
			WebElement pop_content = pop_container
					.findElement(By.id("pop_content"));
			WebElement dialog_buttons = pop_content.findElement(By
					.className("dialog_buttons"));
			WebElement rfloat = dialog_buttons.findElement(By.className("rfloat"));
			List<WebElement> uiButton = rfloat.findElements(By
					.className("uiButton"));
			uiButton.get(0).click();
				Thread.sleep(5000);
		}catch(Exception e)
		{}
		WebElement iframe = driver1.findElement(By.id("iframe_canvas"));
		driver1.switchTo().frame(iframe);
		System.out.println("challenge sent");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) driver1;
		js.executeScript("window.scrollTo(0,0)");
			 startButton = driver1.findElement(By.id("startButton"));
			try {	
			startButton.click();
		} catch (Exception e) {
			System.out.println("Start button cannot be clicked");

		}
			// ok or cancel any 1 has to be clicked
		WebElement okButton = driver1.findElement(By.className("msgbox-ok"));
		okButton.click();
		System.out.println("ok button clicked");

		// ok or cancel any 1 has to be clicked
		
	}
	@Test
	public void randomCheck()
	{
		
	}
}
