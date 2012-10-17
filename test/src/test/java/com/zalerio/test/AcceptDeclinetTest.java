package com.zalerio.test;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.zalerio.config.GameUtil;

public class AcceptDeclinetTest  extends ZalerioBaseTest  {
	public AcceptDeclinetTest(String os, String browser, String version,
			String userid, String password) {
		super(os, browser, version, userid, password);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void acceptDeclineTest()
	{
	GameUtil.clickPlayHereForMultiTabIssue(driver);
	GameUtil.closeGameEndPopUp(driver);
	// accept -decline
	//access right HUD
	WebElement  rightHUD_yourturn= driver.findElement(By.id("rightHUD-yourturn"));
	List<WebElement> your_turnTiles;
	//access your turn tiles
	try{
	 your_turnTiles=rightHUD_yourturn.findElements(By.className("userArea"));
	}catch(Exception e)
	{
		return;
	}
	int size=your_turnTiles.size();
	int j=0;
	for(int i=0;i<size;i++)
	{
	WebElement checkInvitation=null;
	WebElement gameTile=your_turnTiles.get(i);
	//check for each tile if it is an invitation
	try
	{
	checkInvitation=gameTile.findElement(By.className("accept_decline"));
	//access accept button
	if(j==0)
	{
	WebElement acceptButton=checkInvitation.findElement(By.className("right_hud_accept"));
	// click accept Button
	acceptButton.click();
	j=1;
	}	
	else
	{	
	//access decline button
	WebElement declineButton=checkInvitation.findElement(By.className("right_hud_decline"));
	// click decline button
	declineButton.click();
	}
	// click OK on POP up
	WebElement okButton=driver.findElement(By.className("msgbox-ok"));
	if(okButton.isDisplayed())
	{
	System.out.println("ok button found");
	okButton.click();
	}
	}catch(Exception e){}
	}}}
