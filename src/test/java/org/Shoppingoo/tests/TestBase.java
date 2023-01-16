package org.Shoppingoo.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.Shoppingoo.pages.HomePage;
import org.Shoppingoo.utilities.ConfigurationReader;
import org.Shoppingoo.utilities.Driver;
import org.Shoppingoo.utilities.ReUsable;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.IOException;

public class TestBase {
    protected WebDriver driver;
    protected HomePage home;
    protected ExtentReports report;
    //this will define a test, enables adding lgs, authors, test steps
    protected ExtentTest extentLogger;
    // This is used to create HTML report file
    protected ExtentHtmlReporter htmlReporter;

    @BeforeTest
    public void startReport() {
        //initialize report
        report = new ExtentReports();
        //create a report path
        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "/test-output/report.html";

        //initialize html reporter
        htmlReporter = new ExtentHtmlReporter(path);

        //attach html report to report
        report.attachReporter(htmlReporter);

        //title in report
        htmlReporter.config().setReportName("Shoppingoo Smoke Tests");

        // set enviroment information
        report.setSystemInfo("Envioriment", "QA");
        report.setSystemInfo("Browser", System.getProperty("browser") != null ? System.getProperty("browser") : ConfigurationReader.get("browser"));
        report.setSystemInfo("OS", System.getProperty("os.name"));

    }

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
    public void tearDown(ITestResult result) throws InterruptedException, IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            //record the name of failed test case
            extentLogger.fail(result.getName());

            // take the screenshoot and return location of screenshot
            String screenShotPath = ReUsable.getScreenShoot(result.getName());

            // adding screenshot to report
//            extentLogger.addScreenCaptureFromPath(screenShotPath);

            extentLogger.addScreenCaptureFromPath(screenShotPath);
            // adding Extension to report

            extentLogger.fail(result.getThrowable());


        }
        Thread.sleep(2000);
        Driver.closeDriver();
    }

    @AfterTest
    public void endReport() {
        report.flush();
    }
}


