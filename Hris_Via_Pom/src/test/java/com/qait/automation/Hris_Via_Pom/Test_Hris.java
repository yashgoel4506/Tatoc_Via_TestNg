package com.qait.automation.Hris_Via_Pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class Test_Hris {
    
    WebDriver driver;
    
    LoginTest loginTest;
    
    @Test
    public void attempt_Login_With_No_Password_Should_Annotate_Black_Password_Field(){
        loginTest.login("yashgoel", "");
        // red border in password entry
        Assert.assertTrue(loginTest.isPasswordEntryAnnotated());  
    }
    
    @Test(dependsOnMethods  = {"attempt_Login_With_No_Password_Should_Annotate_Black_Password_Field"})
    public void attempt_Login_With_Incorrect_Password_Should_Render_Error_Message(){
        Assert.assertTrue(loginTest
                .loginWithIncorrectCredentials("INVALID_USERN", "INVALUD_PASSWEOR").contains("Invalid Login"));
    }
    
    @Test(dependsOnMethods  = {"attempt_Login_With_Incorrect_Password_Should_Render_Error_Message"})
    public void Attempt_Login_With_correct_Credentials(){
    	TimeSheet time = loginTest.loginWithCorrectCredentials("yashgoel", "Yash@321#");
    	Assert.assertTrue(time.is_successfull_login());
    }
    
    @BeforeClass
    public void launchBrowser(){
        driver = new ChromeDriver();
        driver.get("https://hris.qainfotech.com");
        loginTest = new LoginTest(driver);
    }
    
    @AfterClass
    public void closeBrowser(){
        driver.quit();
    }
    
}
