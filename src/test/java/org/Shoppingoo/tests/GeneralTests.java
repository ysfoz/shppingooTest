package org.Shoppingoo.tests;

import org.Shoppingoo.pages.CartPage;
import org.Shoppingoo.pages.ProductPage;
import org.Shoppingoo.utilities.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

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

    @Test(groups = "logedIn", description = "to get product page and check all elements on this page")
    public void goProductPage() {
        ProductPage productPage = home.getProductPage(0);
        softAssert.assertTrue(productPage.amountContainerOnScreen(), "Amount container is not displayed");
        softAssert.assertTrue(productPage.descOnScreen(), "Description is not displayed");
        softAssert.assertTrue(productPage.imageOnScreen(), "Image is not displayed");
        softAssert.assertTrue(productPage.priceOnScreen(), "Price is not displayed");
        softAssert.assertTrue(productPage.titleOnScreen(), "Title is not displayed");
        softAssert.assertTrue(productPage.addToCartButtonOnScreen(), "Add to Cart button is not displayed");
        softAssert.assertTrue(productPage.colorOnScreen(), "color container is not displayed");
        softAssert.assertTrue(productPage.sizeOnScreen(), "Size Container is not displayed");
        softAssert.assertAll();
    }

    @Test(groups = "logedIn", description = "to control badge text when the add cart button is clicked")
    public void verifyBadge() {
        ProductPage productPage = home.getProductPage(5);
        softAssert.assertTrue(productPage.controlBadge(3));
        softAssert.assertAll();
    }

    @Test(groups = "logedIn")
    public void verifyColorSizeAmount() {
        ProductPage productPage = home.getProductPage(5);
        String color = productPage.selectColor();
        String size = productPage.selectSize();
        String amount = productPage.selectAmount(5);
        productPage.clickAddToCartButton();
        CartPage cartPage = productPage.goToCartPage();
        List<String> propertiesList = cartPage.getColorSizeAmountFromCart();
        softAssert.assertTrue(color.equals(propertiesList.get(0)), "color is false");
        softAssert.assertTrue(size.equals(propertiesList.get(1)), "size is false");
        softAssert.assertTrue(amount.equals(propertiesList.get(2)), "amount is false");
        softAssert.assertAll();

    }

    @Test(groups = "logedIn", description = "to compare total price in order summery and sum of all product prices ")
    public void verifyTotalPrice() {
        CartPage cartPage = new CartPage(driver).goToCartPage();
        System.out.println(cartPage.compareTotalPrice());
        softAssert.assertTrue(cartPage.compareTotalPrice(), "the Price on order Summery is not equal with the total price in the cart");
        softAssert.assertAll();
    }


}
