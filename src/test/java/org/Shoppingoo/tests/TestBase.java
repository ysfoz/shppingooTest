package org.Shoppingoo.tests;

import org.Shoppingoo.pages.HomePage;
import org.Shoppingoo.utilities.ConfigurationReader;
import org.Shoppingoo.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.openqa.selenium.interactions.Actions;
import java.io.IOException;
import java.time.Duration;

public class TestBase {
    protected WebDriver driver;
    protected HomePage home;

    @BeforeMethod(onlyForGroups = "logedIn", alwaysRun = true)
    public HomePage logedInMethod() {
        driver = Driver.get();
        home = new HomePage(driver);
        driver.get(ConfigurationReader.get("developmentUrl"));
        home.getLoginPage(ConfigurationReader.get("user"), ConfigurationReader.get("password"));
        return home;
    }

    @BeforeMethod(onlyForGroups = "withoutlogin", alwaysRun = true)
    public HomePage launchApp() {
        driver = Driver.get();
        home = new HomePage(driver);
        driver.get(ConfigurationReader.get("developmentUrl"));
        return home;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Driver.closeDriver();
    }
}

