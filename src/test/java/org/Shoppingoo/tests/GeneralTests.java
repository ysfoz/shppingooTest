package org.Shoppingoo.tests;

import org.Shoppingoo.utilities.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class GeneralTests extends BaseTest {
    SoftAssert softAssert = new SoftAssert();


    @Test(description = "Verify the company logo, name, title are  visible.")
    public void isVisibleCompanyNameLogoTitle() throws InterruptedException {
        softAssert.assertTrue(home.getCompanyName(), "Company name is not visible");
        softAssert.assertTrue(home.getCompanyLogo(), "Company logo is not visible");
        softAssert.assertTrue(home.getTitle(), "Title is not correct");
        softAssert.assertAll();
    }

    @Test(description = "Verify that the user is able to navigate different categories with Category buttons on Slider")
    public void getSliderCataegories() {
        List<WebElement> sliderCategoryList = home.clickSliderCategoryButtons();
        sliderCategoryList.get(0).click();
        System.out.println(driver.getCurrentUrl());
        softAssert.assertTrue(driver.getCurrentUrl().contains("summer"));
        softAssert.assertAll();

    }

    @Test(description = "Verify that the user is able to navigate different categories with Category buttons on Page")
    public void getPeopleCategories() throws InterruptedException {
        List<WebElement> peopleCategoryList = home.clickPeopleCategoryButtons();
        peopleCategoryList.get(2).click();
        System.out.println(driver.getCurrentUrl());
        softAssert.assertTrue(driver.getCurrentUrl().contains("child"));
        softAssert.assertAll();
    }

    @Test(description = "Verify - the most populer 8 products on screen and all have images")
    public void verifyMostPopularProducts() {
        List<Boolean> mostPopulerProductsList = home.mostPopulerProducts();
        softAssert.assertTrue(mostPopulerProductsList.size() == 8);
        mostPopulerProductsList.forEach(product -> softAssert.assertTrue(product));
        softAssert.assertAll();
    }

    @Test(description = "Verify -get product buttons functions") // there a problem at api/ without login impossible to get Product page
    public void mostPopularProductsButtons() {
        softAssert.assertTrue(home.mostPopulerProductsButtons(2));
        softAssert.assertAll();
    }

    }
