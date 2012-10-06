package zalerioProceduralTestCases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class StartAGame {
	WebDriver browser;
	WebElement startButton;

	public void setUpEnv() {
		browser = new FirefoxDriver();
		browser.get("http://apps.facebook.com/zalzerostaging/");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		WebElement userEmail = browser.findElement(By.id("email"));
		WebElement userPassword = browser.findElement(By.id("pass"));
		userEmail.sendKeys("abhilashbhaduri@gmail.com");
		userPassword.sendKeys("16081989");
		WebElement login = browser.findElement(By.name("login"));
		login.submit();
		System.out.println("user clicked login");
		browser.switchTo().frame("iframe_canvas");
	}

	public void clickStartAGame() {
		startButton = browser.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}

	}

	public void verifyPopUpOnNoFriendSelect() {
		WebElement startButton = browser.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		// Click Send Challenge
		WebElement sendChallengeButton = browser.findElement(By
				.id("sendinvite"));
		sendChallengeButton.click();
		// wait for pop up to arrive
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) browser;
		js.executeScript("window.scrollTo(0,0)");

		WebElement okButton = browser.findElement(By.className("msgbox-ok"));
		if (okButton != null) {
			System.out.println("ok button found");
		}
		okButton.click();
	}

	public void searchFriendsAndShowAll() {
		// Search For Friends by entering test (a-z;A-Z)
		startButton.click();
		WebElement searchArea = browser.findElement(By.name("findfrind"));
		String text = Character.toString('g');
		searchArea.sendKeys(text);
		System.out.println(text);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		searchArea.sendKeys("gr");
		System.out.println("gr");
		// show all
		WebElement showall = browser.findElement(By.id("show_all_friends"));
		showall.click();
		System.out.println("showall clicked");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void selectAndUnselect1Friend() {
		WebElement a = browser.findElement(By.className("friendlist"));
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

	public void createAGameWith5Friends() {
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
		sendChallengeButton.click();
		System.out.println("challenge sent");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		// error pop up shown
		// click ok on pop up
		okButton = browser.findElement(By.className("msgbox-ok"));
		okButton.click();
		System.out.println("ok button clicked");
		// scroll window to default position
		js = (JavascriptExecutor) browser;
		js.executeScript("window.scrollTo(0,0)");
		// close the window-close button not working
		WebElement friendpopup = browser.findElement(By.id("friendpopup"));
		WebElement closeButton = friendpopup.findElement(By.tagName("a"));
		// WebElement closeButton=browser.findElement(By.id("close"));
		closeButton.click();
	}

	public void create2PlayerGame() {
		startButton = browser.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		// select friend
		fr1.click();
		WebElement sendChallengeButton = browser.findElement(By
				.id("sendinvite"));

		sendChallengeButton.click();
		System.out.println("challenge sent");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) browser;
		js.executeScript("window.scrollTo(0,0)");

	}

	public void verifyClickOtherButtonsWhilePopUp() {
		try {
			startButton = browser.findElement(By.id("startButton"));
			startButton.click();
		} catch (Exception e) {
			System.out.println("Start button cannot be clicked");

		}

		WebElement okButton = browser.findElement(By.className("msgbox-ok"));
		okButton.click();
		System.out.println("ok button clicked");

		// ok or cancel any 1 has to be clicked
		// click cancel on pop up
		WebElement cancelButton = browser.findElement(By
				.className("msgbox-cancel"));
		cancelButton.click();
		System.out.println("cancel button clicked");
	}
}
