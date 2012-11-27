

import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GameEnterTest extends Zalerio1UserBaseTest {

	public GameEnterTest(String os, String browser, String version,
			String userid, String password) {
		super(os, browser, version, userid, password);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void nonSecuredGameOpenTest() {

		// Check if start-a-game button is visible
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement startButton = driver1.findElement(By.id("startButton"));
		assertEquals(startButton.isDisplayed(), true);

	}

	@Test
	public void nonSecuredStartANewGame() {
		// Click Start a New Game
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement findFriendsHeader = driver1.findElement(By
				.className("findfriend"));
		assertEquals(findFriendsHeader.isDisplayed(), true);
	}

}