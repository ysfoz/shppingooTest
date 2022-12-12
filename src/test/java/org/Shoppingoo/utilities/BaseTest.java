package org.Shoppingoo.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.Shoppingoo.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public HomePage home;

    public WebDriver getDriver() throws IOException {
        Properties properties = new Properties();
        String path = "/Users/yusuf/IdeaProjects/ShoppingooTest/configirations.properties";
        FileInputStream fis = new FileInputStream(path);
        properties.load(fis);

        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");

        switch (browserName) {
            case ("chrome") -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            case ("firefox") -> {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            case "safari" -> {
                WebDriverManager.getInstance(SafariDriver.class).setup();
                driver = new SafariDriver();
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup()
                ;
                driver = new EdgeDriver();
            }
            case "headless-chrome" -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(new ChromeOptions().setHeadless(true));
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }

        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }
    @BeforeMethod(onlyForGroups = "logedIn",alwaysRun = true)
    public HomePage logedInMethod() throws IOException {
        driver = getDriver();
        home = new HomePage(driver);
        home.getPage();
        home.getLoginPage("admin","123456");
        return home;
    }
    @BeforeMethod(onlyForGroups = "withoutlogin",alwaysRun = true)
    public HomePage launchApp() throws IOException {
        driver = getDriver();
        home = new HomePage(driver);
        home.getPage();
        return home;
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() throws IOException {
        driver.close();
    }
}

