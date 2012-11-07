package com.zalerio.test;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.zalerio.config.GameUtil;

public class StartAGameTest extends ZalerioBaseTest {
	public StartAGameTest(String os, String browser, String version) {
		super(os, browser, version);
		// TODO Auto-generated constructor stub
	}
	//	@Test
		public void clickStartAGame() {
		GameUtil.closeGameEndPopUp(driver);
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

	}
	//@Test
	public void verifyPopUpOnNoFriendSelect() {
		GameUtil.closeGameEndPopUp(driver);
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// Click Send Challenge
		WebElement sendChallengeButton = driver.findElement(By
				.id("sendinvite"));
		sendChallengeButton.click();
		// wait for pop up to arrive
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,0)");

		WebElement okButton = driver.findElement(By.className("msgbox-ok"));
		if (okButton != null) {
			System.out.println("ok button found");
		}
		okButton.click();
	}
//	@Test
	public void searchFriendsAndShowAll() {
		// Search For Friends by entering test (a-z;A-Z)
		GameUtil.closeGameEndPopUp(driver);
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		WebElement searchArea = driver.findElement(By.name("findfrind"));
		searchArea.sendKeys("g");
		System.out.println("g");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {}
		searchArea.sendKeys("gr");
		System.out.println("gr");
		// show all
		WebElement showall = driver.findElement(By.id("show_all_friends"));
		showall.click();
		System.out.println("showall clicked");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	//@Test
	public void selectAndUnselect1Friend() {
		GameUtil.closeGameEndPopUp(driver);
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		WebElement a = driver.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		WebElement fr1 = selectListOfButtons.get(1).findElement(
				By.className("select_button"));
		fr1.click();
		System.out.println("fr1 selected");
		// unselect 1 friend
		fr1.click();
		System.out.println("fr1 unselected");
	}
	//@Test
	public void createAGameWith5Friends() {
		GameUtil.closeGameEndPopUp(driver);
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		WebElement a = driver.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		WebElement fr1 = selectListOfButtons.get(1).findElement(
				By.className("select_button"));
		// select 5 friends
		fr1.click();
		System.out.println("fr1 selected");
		WebElement fr2 = selectListOfButtons.get(4)
				.findElement(By.tagName("a"));
		fr2.click();
		System.out.println("fr2 selected");
		WebElement fr3 = selectListOfButtons.get(5)
				.findElement(By.tagName("a"));
		fr3.click();
		System.out.println("fr3 selected");
		WebElement fr4 = selectListOfButtons.get(6)
				.findElement(By.tagName("a"));
		fr4.click();
		System.out.println("fr4 selected");
		WebElement fr5 = selectListOfButtons.get(7)
				.findElement(By.tagName("a"));
		fr5.click();
		//send challenge
		WebElement sendChallengeButton = driver.findElement(By
				.id("sendinvite"));
		sendChallengeButton.click();
		System.out.println("challenge sent");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		// error pop up shown
		// click ok on pop up
		WebElement okButton = driver.findElement(By.className("msgbox-ok"));
		okButton.click();
		System.out.println("ok button clicked");
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,0)");
		// close the window-close button not working
		WebElement friendpopup = driver.findElement(By.id("friendpopup"));
		WebElement closeButton = friendpopup.findElement(By.tagName("a"));
		// WebElement closeButton=driver.findElement(By.id("close"));
		closeButton.click();
	}
//	@Test
	public void create2PlayerGame() {
		GameUtil.closeGameEndPopUp(driver);
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		WebElement a = driver.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		WebElement fr1 = selectListOfButtons.get(1).findElement(
				By.className("select_button"));
		// select friend
		fr1.click();
		WebElement sendChallengeButton = driver.findElement(By
				.id("sendinvite"));

		sendChallengeButton.click();
		System.out.println("challenge sent");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,0)");

	}
	//@Test
	public void verifyClickOtherButtonsWhilePopUp() {
		
			GameUtil.closeGameEndPopUp(driver);
			WebElement startButton = driver.findElement(By.id("startButton"));
			try {	
			startButton.click();
		} catch (Exception e) {
			System.out.println("Start button cannot be clicked");

		}

		WebElement okButton = driver.findElement(By.className("msgbox-ok"));
		okButton.click();
		System.out.println("ok button clicked");

		// ok or cancel any 1 has to be clicked
		
	}
}
