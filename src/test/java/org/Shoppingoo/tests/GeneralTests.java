package org.Shoppingoo.tests;

import org.Shoppingoo.pages.ProductPage;
import org.Shoppingoo.utilities.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class GeneralTests extends BaseTest {
    SoftAssert softAssert = new SoftAssert();


    @Test(groups = "withoutlogin", description = "Verify the company logo, name, title are  visible.")
    public void isVisibleCompanyNameLogoTitle() throws InterruptedException {
        softAssert.assertTrue(home.getCompanyName(), "Company name is not visible");
        softAssert.assertTrue(home.getCompanyLogo(), "Company logo is not visible");
        softAssert.assertTrue(home.getTitle(), "Title is not correct");
        softAssert.assertAll();
    }

    @Test(groups = "withoutlogin", description = "Verify that the user is able to navigate different categories with Category buttons on Slider")
    public void getSliderCataegories() {
        List<WebElement> sliderCategoryList = home.clickSliderCategoryButtons();
        sliderCategoryList.get(0).click();
        System.out.println(driver.getCurrentUrl());
        softAssert.assertTrue(driver.getCurrentUrl().contains("summer"), "summer button does not run");
        softAssert.assertAll();

    }

    @Test(groups = "withoutlogin", description = "Verify that the user is able to navigate different categories with Category buttons on Page")
    public void getPeopleCategories() throws InterruptedException {
        List<WebElement> peopleCategoryList = home.clickPeopleCategoryButtons();
        peopleCategoryList.get(2).click();
        System.out.println(driver.getCurrentUrl());
        softAssert.assertTrue(driver.getCurrentUrl().contains("child"), "Child category button does not run");
        softAssert.assertAll();
    }

    @Test(groups = "withoutlogin", description = "Verify - the most populer 8 products on screen and all have images")
    public void verifyMostPopularProducts() {
        List<Boolean> mostPopulerProductsList = home.mostPopulerProducts();
        softAssert.assertTrue(mostPopulerProductsList.size() == 8, "most populer products less then 8");
        mostPopulerProductsList.forEach(product -> softAssert.assertTrue(product));
        softAssert.assertAll();
    }


    @Test(groups = "withoutlogin", description = "sign in process")
    public void signIn() {
        home.getLoginPage("admin", "123456");
        softAssert.assertTrue(home.verifyLogin());
        softAssert.assertAll();
    }

    @Test(groups = "logedIn",description = "to get product page and check all elements on this page")
    public void goProductPage() {
        ProductPage productPage = home.getProductPage(0);
        softAssert.assertTrue(productPage.amountContainerOnScreen(),"Amount container is not displayed");
        softAssert.assertTrue(productPage.descOnScreen(),"Description is not displayed");
        softAssert.assertTrue(productPage.imageOnScreen(),"Image is not displayed");
        softAssert.assertTrue(productPage.priceOnScreen(),"Price is not displayed");
        softAssert.assertTrue(productPage.titleOnScreen(),"Title is not displayed");
        softAssert.assertTrue(productPage.addToCartButtonOnScreen(),"Add to Cart button is not displayed");
        softAssert.assertTrue(productPage.colorOnScreen(),"color container is not displayed");
        softAssert.assertTrue(productPage.sizeOnScreen(),"Size Container is not displayed");
        softAssert.assertAll();
    }

    @Test(groups = "logedIn", description = "to control badge text when the add cart button is clicked")
    public void verifyBadge()  {
        ProductPage productPage = home.getProductPage(5);
        softAssert.assertTrue(productPage.controlBadge(3));
        softAssert.assertAll();
    }


}
