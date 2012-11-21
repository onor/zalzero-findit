package com.zalerio.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.zalerio.config.Popup;

public class RatingScreenTest {
	
	public static void closeGameEndPopupWithVerifyRating(WebDriver driver) throws InterruptedException
	{
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// If Game End Pop up Found then dismiss it
		try{
		WebElement score_friendpopup=driver.findElement(By.id("score_friendpopup"));
		WebElement close=score_friendpopup.findElement(By.id("close"));	
		close.click();
		}catch(Exception e)
		{e.printStackTrace();}
		//close feedback screen
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		WebElement rating_popup=driver.findElement(By.className("rating-popup"));
		WebElement rating_content=rating_popup.findElement(By.className("rating-content"));
		WebElement rating_form=rating_content.findElement(By.id("rating_form"));
		WebElement submit_rating=rating_form.findElement(By.id("submit_rating"));
		submit_rating.click();
		Popup.verifyPopup(driver, "Please rate the game first.");
		WebElement star_3=rating_form.findElement(By.className("star_3"));
		star_3.click();
		Thread.sleep(2000);
		String star_3Selected=star_3.getAttribute("class");
		assertEquals(star_3Selected,"star_3 selected");
		star_3.click();
		Thread.sleep(2000);
		WebElement star_4=rating_form.findElement(By.className("star_4"));
		star_4.click();
		 star_3Selected=star_3.getAttribute("class");
		assertEquals(star_3Selected,"star_3");
		List<WebElement> suggestions=rating_form.findElements(By.className("suggestion"));
		WebElement comment_improvement=suggestions.get(0).findElement(By.name("comment_improvement"));
		comment_improvement.click();
		comment_improvement.sendKeys("checking feedback");
		WebElement comment_like=suggestions.get(1).findElement(By.name("comment_like"));
		Actions builder=new Actions(driver);
		Action pressTab=builder.keyDown(comment_improvement,Keys.TAB).keyUp(comment_improvement,Keys.TAB).build();
		pressTab.perform();
		comment_like.sendKeys("checking feedback");
		System.out.print("tab contains "+comment_like.getText());
		submit_rating.click();
		Popup.verifyPopup(driver, "Thank you for your suggestions.");
		driver.navigate().refresh();
		Thread.sleep(8000);
		driver.switchTo().frame("iframe_canvas");
		WebElement score_friendpopup=driver.findElement(By.id("score_friendpopup"));
		WebElement show_score = score_friendpopup.findElement(By
				.className("show_score"));
		WebElement button_score = show_score.findElement(By
				.className("button_score"));
		List<WebElement> buttons = button_score.findElements(By.tagName("a"));
		WebElement rematch = buttons.get(0);
		rematch.click();
		Popup.verifyPopup(driver, "Challenge has been sent. Would you like to start playing.");
	}

}
