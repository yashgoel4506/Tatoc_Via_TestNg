package com.qait.automation.Tatoc_Via_Maven;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TatocViaMaven {
	WebDriver driver;
	
	
	@BeforeClass
	public void startTatoc(){
		driver = new ChromeDriver();
		driver.get("http://10.0.1.86/tatoc");
	}
	
	@Test
	public void startBasicCourse(){
		WebElement search = driver.findElement(By.linkText("Basic Course"));
		Assert.assertEquals(search.isDisplayed(), true);
		search.click();
		Assert.assertEquals("http://10.0.1.86/tatoc/basic/grid/gate", driver.getCurrentUrl());
	}
	
	@Test(dependsOnMethods = {"startBasicCourse"})
	public void search_GreenBox(){
		Assert.assertEquals(driver.findElement(By.className("greenbox")).isDisplayed(), true);
		driver.findElement(By.className("greenbox")).click();
		Assert.assertEquals("http://10.0.1.86/tatoc/basic/frame/dungeon", driver.getCurrentUrl());
	}
	
	@Test(dependsOnMethods = {"search_GreenBox"})
	public void repaint_Box2_Until_Same_Color_As_Box1(){
		driver.switchTo().frame("main");
		String box1 = new String();
		box1 = driver.findElement(By.id("answer")).getAttribute("class");
		Assert.assertEquals(driver.findElement(By.id("answer")).isDisplayed(), true);
		String box2 = "";
		driver.switchTo().frame("child");
		Assert.assertEquals(driver.findElement(By.id("answer")).isDisplayed(), true);
		box2 = driver.findElement(By.id("answer")).getAttribute("class");
		while(!box1.equals(box2))
		{
			driver.switchTo().defaultContent();
			driver.switchTo().frame("main");
			Assert.assertEquals(driver.findElement(By.linkText("Repaint Box 2")).isDisplayed(), true);
			driver.findElement(By.linkText("Repaint Box 2")).click();
			driver.switchTo().frame("child");
			box2 = driver.findElement(By.id("answer")).getAttribute("class");
		}
		driver.switchTo().defaultContent();
		driver.switchTo().frame("main");
		Assert.assertEquals(driver.findElement(By.linkText("Proceed")).isDisplayed(), true);
		driver.findElement(By.linkText("Proceed")).click();
		Assert.assertEquals("http://10.0.1.86/tatoc/basic/drag", driver.getCurrentUrl());
	}
	
	@Test(dependsOnMethods = {"repaint_Box2_Until_Same_Color_As_Box1"})
	public void to_Drag_DragBox_Into_DropBox(){
		WebElement to = driver.findElement(By.id("dropbox"));
		WebElement from = driver.findElement(By.id("dragbox"));
		Assert.assertEquals(to.isDisplayed(), true);
		Assert.assertEquals(from.isDisplayed(), true);
		Actions action = new Actions(driver);
		action.dragAndDrop(from, to).build().perform();
		driver.findElement(By.linkText("Proceed")).click();
		Assert.assertEquals("http://10.0.1.86/tatoc/basic/windows", driver.getCurrentUrl());
	}
	
	@Test(dependsOnMethods = {"to_Drag_DragBox_Into_DropBox"})
	public void to_Launch_Popup(){
		Assert.assertEquals(driver.findElement(By.linkText("Launch Popup Window")).isDisplayed(), true);
		driver.findElement(By.linkText("Launch Popup Window")).click();
		String main = driver.getWindowHandle();
		Set<String> popup = driver.getWindowHandles();
		Iterator<String> i = popup.iterator();
		String child = " ";
		while(i.hasNext())
		{
			child = i.next();
		}
		driver.switchTo().window(child);
		driver.findElement(By.id("name")).sendKeys("Yash Goel");
		driver.findElement(By.id("submit")).click();
		driver.switchTo().window(main);
		Assert.assertEquals(driver.findElement(By.linkText("Proceed")).isDisplayed(), true);
		driver.findElement(By.linkText("Proceed")).click();
		Assert.assertEquals("http://10.0.1.86/tatoc/basic/cookie", driver.getCurrentUrl());
	}
	
	@Test(dependsOnMethods = {"to_Launch_Popup"})
	public void to_Generate_Token(){
		Assert.assertEquals(driver.findElement(By.linkText("Generate Token")).isDisplayed(), true);
		driver.findElement(By.linkText("Generate Token")).click();
		Assert.assertEquals(driver.findElement(By.id("token")).isDisplayed(), true);
		String token = driver.findElement(By.id("token")).getText();
		String tokenid = token.substring(7,token.length());
		Cookie cookie = new Cookie("Token", tokenid);
		driver.manage().addCookie(cookie);
		Assert.assertEquals(driver.findElement(By.linkText("Proceed")).isDisplayed(), true);
		driver.findElement(By.linkText("Proceed")).click();
		Assert.assertEquals("http://10.0.1.86/tatoc/end", driver.getCurrentUrl());
	}
	
	@AfterClass
	public void close(){
		driver.close();
	}

}
