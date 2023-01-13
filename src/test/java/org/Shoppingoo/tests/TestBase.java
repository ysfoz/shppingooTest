package org.Shoppingoo.tests;

import org.Shoppingoo.pages.HomePage;
import org.Shoppingoo.utilities.ConfigurationReader;
import org.Shoppingoo.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {
    protected WebDriver driver;
    protected HomePage home;

    @BeforeMethod(onlyForGroups = "logedIn", alwaysRun = true)
    public HomePage loginAndLaunchApp() {
        driver = Driver.get();
        home = new HomePage();
        driver.get(ConfigurationReader.get("developmentUrl"));
        home.getLoginPage(ConfigurationReader.get("user"), ConfigurationReader.get("password"));
        return home;
    }

    @BeforeMethod(onlyForGroups = "withoutlogin", alwaysRun = true)
    public HomePage launchApp() {
        driver = Driver.get();
        home = new HomePage();
        driver.get(ConfigurationReader.get("developmentUrl"));
        return home;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Driver.closeDriver();
    }
}

