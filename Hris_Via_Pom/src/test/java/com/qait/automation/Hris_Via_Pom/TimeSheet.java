package com.qait.automation.Hris_Via_Pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TimeSheet {
	WebDriver driver;
	
	TimeSheet(WebDriver driver){
		this.driver = driver;
	}
	
	public WebElement get_SearchBox_To_Verify_Successfull_Login(){
		return this.driver.findElement(By.id("dvSearchBox"));
	}

	public Boolean is_successfull_login(){
	String classSearch = get_SearchBox_To_Verify_Successfull_Login().getAttribute("class");
	return classSearch.equals("search-box");
	}
	
	
}
