package org.Shoppingoo.tests;

import org.Shoppingoo.utilities.ConfigurationReader;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class WithoutLoginTest extends TestBase {
    SoftAssert softAssert = new SoftAssert();

    @Test(groups = "withoutlogin", description = "Verify the company logo, name, title are  visible.")
    public void isVisibleCompanyNameLogoTitle() throws InterruptedException {
        extentLogger =report.createTest("Check Company name, logo and title on Browser");
        extentLogger.info("Company name is Shoppingoo");
        softAssert.assertTrue(home.getCompanyName(), "Company name is not visible");
        extentLogger.info("Company logo is visible");
        softAssert.assertTrue(home.getCompanyLogo(), "Company logo is not visible");
        extentLogger.info("Title contains 'React'");
        softAssert.assertTrue(home.getTitle(), "Title is not correct");
        softAssert.assertAll();
        extentLogger.pass("PASSED");
    }

    @Test(groups = "withoutlogin", description = "Verify that the user is able to navigate different categories with Category buttons on Slider")
    public void getSliderCataegories() {
        extentLogger =report.createTest("Check changing category buttons on slider menu functionality");
        List<WebElement> sliderCategoryList = home.clickSliderCategoryButtons();
        sliderCategoryList.get(0).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("summer"), "summer button does not run");
        extentLogger.info("When Zeroth index in Slider Categories is clicked, url changes and contains 'Summer'");
        extentLogger.pass("PASSED");

    }

    @Test(groups = "withoutlogin", description = "Verify that the user is able to navigate different categories with Category buttons on Page")
    public void getPeopleCategories() throws InterruptedException {
        extentLogger = report.createTest("Check changing category buttons on page functionality");
        List<WebElement> peopleCategoryList = home.clickPeopleCategoryButtons();
        peopleCategoryList.get(2).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("child"), "Child category button does not run");
        extentLogger.info("When second index in Slider Categories is clicked, url changes and contains 'Summer'");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "withoutlogin", description = "Verify - the most populer 8 products on screen and all have images")
    public void verifyMostPopularProducts() {
        extentLogger =report.createTest("Check count of most popular Products and all visible");
        List<Boolean> mostPopularProductsList = home.mostPopularProducts();
        Assert.assertTrue(mostPopularProductsList.size() == 8, "most populer products less then 8");
        extentLogger.info("There are 8 Products as expected");
        mostPopularProductsList.forEach(product -> Assert.assertTrue(product));
        extentLogger.info("All Product's images is visible");
        extentLogger.pass("PASSED");

    }


    @Test(groups = "withoutlogin", description = "sign in process")
    public void signIn() {
        extentLogger =report.createTest("Check sign in with correct username and password");
        home.getLoginPage(ConfigurationReader.get("user"), ConfigurationReader.get("password"));
        Assert.assertTrue(home.verifyLogin());
        extentLogger.info("Profile image is visible on navbar after signed in");
        extentLogger.pass("PASSED");
    }


}
