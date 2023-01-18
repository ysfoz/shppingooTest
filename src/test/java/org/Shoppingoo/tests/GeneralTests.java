package org.Shoppingoo.tests;

import org.Shoppingoo.pages.CartPage;
import org.Shoppingoo.pages.ProductListPage;
import org.Shoppingoo.pages.ProductPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class GeneralTests extends TestBase {
    SoftAssert softAssert = new SoftAssert();


    @Test(groups = "logedIn", description = "to get product page and check all elements on this page")
    public void goProductPage() {
        int productIndex = 0;
        extentLogger = report.createTest("to get product page and check all elements on this page");
        ProductPage productPage = home.getProductPage(productIndex, extentLogger);
        extentLogger.info("Amount container is displayed");
        softAssert.assertTrue(productPage.amountContainerOnScreen(), "Amount container is not displayed");
        extentLogger.info("Description is displayed");
        softAssert.assertTrue(productPage.descOnScreen(), "Description is not displayed");
        extentLogger.info("Image is displayed");
        softAssert.assertTrue(productPage.imageOnScreen(), "Image is not displayed");
        extentLogger.info("Price is displayed");
        softAssert.assertTrue(productPage.priceOnScreen(), "Price is not displayed");
        extentLogger.info("Title is displayed");
        softAssert.assertTrue(productPage.titleOnScreen(), "Title is not displayed");
        extentLogger.info("Add to Cart button is displayed");
        softAssert.assertTrue(productPage.addToCartButtonOnScreen(), "Add to Cart button is not displayed");
        extentLogger.info("color container is displayed");
        softAssert.assertTrue(productPage.colorOnScreen(), "color container is not displayed");
        extentLogger.info("Size Container is displayed");
        softAssert.assertTrue(productPage.sizeOnScreen(), "Size Container is not displayed");
        softAssert.assertAll();
        extentLogger.pass("PASSED");
    }

    @Test(groups = "logedIn", description = "to control badge text when the add cart button is clicked two times")
    public void verifyBadge() {
        extentLogger = report.createTest("check badge text changing when the add cart button is clicked two times");
        int productIndex = 5;
        int secondIndex = 3;
        ProductPage productPage = home.getProductPage(productIndex, extentLogger);
        Assert.assertTrue(productPage.controlBadge(secondIndex, extentLogger));
        extentLogger.pass("PASSED");
    }

    @Test(groups = "logedIn", description = "to control products's color, size and amount are correct or not")
    public void verifyColorSizeAmount() {
        extentLogger = report.createTest("Check color,size and amount are correct when a product was added to the cart");
        int productIndex = 5;
        ProductPage productPage = home.getProductPage(productIndex, extentLogger);
        String color = productPage.selectColor();
        String size = productPage.selectSize();
        String amount = productPage.selectAmount(5);
        extentLogger.info("Getting color, size and amount from product page");
        productPage.clickAddToCartButton();
        extentLogger.info("The selected Product is added to the cart");
        CartPage cartPage = productPage.goToCartPage(extentLogger);
        List<String> propertiesList = cartPage.getColorSizeAmountFromCart();
        extentLogger.info("Getting color, size and amount from cart");
        softAssert.assertTrue(color.equals(propertiesList.get(0)), "color is incorrect");
        extentLogger.info("Color is correct");
        softAssert.assertTrue(size.equals(propertiesList.get(1)), "size is incorrect");
        extentLogger.info("Size is correct");
        softAssert.assertTrue(amount.equals(propertiesList.get(2)), "amount is incorrect");
        extentLogger.info("Amount is correct");
        softAssert.assertAll();
        extentLogger.pass("PASSED");

    }

    @Test(groups = "logedIn", description = "to compare total price in order summery and sum of all product prices")
    public void verifyTotalPrice() {
        extentLogger = report.createTest("to compare total price in order summery and sum of all product prices, which is in the cart");
        CartPage cartPage = new CartPage().goToCartPage(extentLogger);
        Assert.assertTrue(cartPage.compareTotalPrice(), "the Price on order Summery is not equal with the total price in the cart");
        extentLogger.info("the Price on order Summery is equal with the total price in the cart");
        extentLogger.pass("PASSED");
    }

    // bu fonksiyonlari filter yada search sonrasi kullanmak daha faydali olacak. Key word ler belirleyerek, urunlerin isimlerine gore sonuclar almayi deneyelim.

    @Test(groups = "logedIn", description = "to check delete button in Basket")
    public void checkDeleteButtonInBasket() {
        extentLogger = report.createTest("Check delete button in Basket");
        int productIndex = 3;
        home.addToCartFromMostPopuler(productIndex, extentLogger);
        CartPage cartPage = home.goToCartPage(extentLogger);
        Integer productCount = cartPage.getCountOfProducts("basket");
        extentLogger.info(String.valueOf(productCount) + " Products in the basket");
        Integer afterClickProductCount = cartPage.deleteProduct("basket");
        extentLogger.info(String.valueOf(afterClickProductCount) + " Products in the basket after delete button clicked");
        Assert.assertTrue(productCount - 1 == afterClickProductCount, "delete button does not work");
        extentLogger.info("Delete button works");
        extentLogger.pass("PASSED");

    }

    @Test(groups = "logedIn", description = "to check delete button in save for later ")
    // basket icersindeki ilk delete butonunu calistiriyor
    public void checkDeleteButtonInSaveForLater() {
        extentLogger = report.createTest("Check delete button in Save For Later items");
        int productIndex = 5;
        home.addToCartFromMostPopuler(productIndex, extentLogger);
        CartPage cartPage = home.goToCartPage(extentLogger);
        cartPage.addSaveForLater();
        extentLogger.info("The product is added to Save for later list , which index number is " + productIndex);
        Integer productCount = cartPage.getCountOfProducts("later");
        extentLogger.info(String.valueOf(productCount) + " Products in the List");
        Integer afterClickProductCount = cartPage.deleteProduct("later");
        extentLogger.info(String.valueOf(afterClickProductCount) + " Products in the List");
        Assert.assertTrue(productCount - 1 == afterClickProductCount, "delete button does not work in save for later");
        extentLogger.info("Delete button in save for later works");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "logedIn", description = "to check save for later button functionality")
    public void checkSaveForLaterButton() throws InterruptedException {
        extentLogger = report.createTest("check save for later button functionality");
        home.addToCartFromMostPopuler(2, extentLogger);
        CartPage cartPage = home.goToCartPage(extentLogger);
        Integer productCount = cartPage.getCountOfProducts("basket");
        Integer saveForLaterItemCount = cartPage.getCountOfProducts("later");
        Integer afterClickSaveForLaterItemCount = cartPage.saveForLaterOrMoveToBasket("save");
        Integer afterClickProductCount = cartPage.getCountOfProducts("basket");
        softAssert.assertTrue(saveForLaterItemCount + 1 == afterClickSaveForLaterItemCount, "save for later button does not work - save for later list did not change");
        extentLogger.info("Save for later button works and adds an items to save for later items");
        softAssert.assertTrue(productCount - 1 == afterClickProductCount, "safe for later button does not work - product list did not change");
        extentLogger.info("Save for later button works and removes an item from basket");
        softAssert.assertAll();
        extentLogger.pass("PASSED");

    }


    @Test(groups = "logedIn", description = "to check move to basket button")
    public void checkMoveToBasketButton() throws InterruptedException {
        extentLogger = report.createTest("check move to basket button");
        home.addToCartFromMostPopuler(1, extentLogger);
        CartPage cartPage = home.goToCartPage(extentLogger);
        Integer productCount = cartPage.getCountOfProducts("basket");
        cartPage.addSaveForLater();
        extentLogger.info("add product to Save for later list");
        Integer saveForLaterItemCount = cartPage.getCountOfProducts("later");
        Integer afterClickSaveForLaterItemCount = cartPage.saveForLaterOrMoveToBasket("move");
        Integer afterClickProductCount = cartPage.getCountOfProducts("basket");
        softAssert.assertTrue(saveForLaterItemCount - 1 == afterClickSaveForLaterItemCount, "move to basket button does not work - save for later list did not change");
        extentLogger.info("Move to basket button works and removes an item from save for later items");
        softAssert.assertTrue(productCount == afterClickProductCount, "move to basket button does not work - product list did not change");
        extentLogger.info("Move to basket button works and adds item to basket");
        softAssert.assertAll();
        extentLogger.pass("PASSED");

    }

    @Test(groups = "logedIn", description = "to check see more like this button")
    // her zamna basket icersindeki ilk butonunu calistiriyor
    public void checkSeeMoreLikeThis() throws InterruptedException {
        extentLogger = report.createTest("Check see more like this button");
        int productIndex = 1;
        home.addToCartFromMostPopuler(productIndex, extentLogger);
        CartPage cartPage = home.goToCartPage(extentLogger);
        cartPage.seeMoreLikeThis(extentLogger);
    }

// buraya kadar olan testler tekrar gozden gecirilecek - --  -- - - - - - -  - --


    // bu testen bilerek hata almak istiyorum, raporda gosterebilmek icin.
    @Test(groups = "logedIn", description = "to check possibility of same product 2 times adding to cart with add cart button ")
    public void checkSameProductTwoTimesAdding() {
        extentLogger = report.createTest("checking possibility of adding a product to cart two times");
        int productIndex = 1;
        Assert.assertTrue(home.addProductToCartThreeTimes(productIndex, extentLogger), "Same product can be added 2 or more times to cart");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "logedIn", description = "to control search function with keyword value")
    public void verifyFunctionalityOfSearchbarAtHomePage() throws InterruptedException {
        extentLogger = report.createTest("Search functionality");
        List<String> keyList = new ArrayList<>(List.of("bag", "coat", "dress"));
        List<Boolean> matchList = home.searchProduct(keyList, extentLogger);
        Assert.assertTrue(matchList.stream().allMatch(n -> n = true), "All search keys are not match with product's title");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "logedIn", description = "To control filtering function, but only what colors are available for the product.")
    public void verifyColorFilter() {
        extentLogger =report.createTest("Filtering products test with available colors ");
        ProductListPage productListPage = home.getProductListPage(extentLogger);
        List<Boolean> matchList = productListPage.checkColorFilter();
        extentLogger.info("There are " + matchList.size() +"  available colors");
        matchList.forEach(element -> Assert.assertTrue(element, "This product with this color is not in product List"));
        extentLogger.info("Filtering function with available colors works regularly");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "logedIn", description = "To control filtering function, but only what colors are not available for the product.")
    public void verifyColorFilterNotInProduct() {
        extentLogger =report.createTest("Filtering products test with unavailable colors ");
        ProductListPage productListPage = home.getProductListPage(extentLogger);
        List<Boolean> matchList = productListPage.checkColorFilterNotInProduct();
        extentLogger.info("There are " + matchList.size() +"  unavailable colors");
        matchList.forEach(element -> Assert.assertFalse(element, "This Product(" + element + ") with this size in product List"));
        extentLogger.info("Filtering function with unavailable colors works regularly");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "logedIn", description = "To control filtering function, but only what sizes are available for the product.")
    public void verifySizeFilter() {
        extentLogger =report.createTest("Filtering products test with available sizes");
        ProductListPage productListPage = home.getProductListPage(extentLogger);
        List<Boolean> matchList = productListPage.checkSizeFilter();
        extentLogger.info("There are " + matchList.size() +"  available sizes");
        matchList.forEach(element -> Assert.assertTrue(element, "This Product(" + element + ") with this size not in product List"));
        extentLogger.info("Filtering function with available sizes works regularly");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "logedIn", description = "To control filtering function, but only what sizes are not available for the product.")
    public void verifySizeFilterNotInProduct() {
        extentLogger =report.createTest("Filtering products test with unavailable sizes");
        ProductListPage productListPage = home.getProductListPage(extentLogger);
        List<Boolean> matchList = productListPage.checkSizeFilterNotInProduct();
        extentLogger.info("There are " + matchList.size() +"  unavailable sizes");
        matchList.forEach(element -> Assert.assertFalse(element, "This Product(" + element + ") with this size in product List"));
        extentLogger.info("Filtering function with unavailable sizes works regularly");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "logedIn", description = "To control filtering functions for sizes and colors with keyword values")
    public void verifySizeAndColorWithKeyword() {
        extentLogger = report.createTest("Filtering test with keywords");
        ProductListPage productListPage = home.getProductListPage(extentLogger);
        Boolean match1 = productListPage.checkSizeAndColorWithKeyword("red", "s",extentLogger);
        Boolean match2 = productListPage.checkSizeAndColorWithKeyword("yellow", "xl",extentLogger);
        Boolean match3 = productListPage.checkSizeAndColorWithKeyword("white", "l",extentLogger);
        Boolean match4 = productListPage.checkSizeAndColorWithKeyword("black", "xs",extentLogger);
        softAssert.assertTrue(match1, "selected a product is in the list but it is not on the screen");
        softAssert.assertTrue(match2, "selected a product is not in the list but it is on the screen");
        softAssert.assertTrue(match3, "selected a product is not in the list but it is on the screen");
        softAssert.assertTrue(match4, "selected a product is not in the list but it is on the screen");
        softAssert.assertAll();
        extentLogger.pass("PASSED");
    }

    @Test(groups = "logedIn", description = "To control filtering functions and sorting(asc) functions together")
    public void verifySortingProductsWithPriceAsc()  {
        extentLogger = report.createTest("Filtering and sorting test together");
        ProductListPage productListPage = home.getProductListPage(extentLogger);
        Boolean match = productListPage.controlSortFunction("black", "s", "asc",extentLogger);
        Assert.assertTrue(match, "Asc sorting Process is not working correctly");
        extentLogger.pass("PASSED");

    }

    @Test(groups = "logedIn", description = "To control filtering functions and sorting functions together")
    public void verifySortingProductsWithPriceDesc()  {
        extentLogger = report.createTest("Filtering and sorting(desc) test together");
        ProductListPage productListPage = home.getProductListPage(extentLogger);
        Boolean match = productListPage.controlSortFunction("black", "s", "desc",extentLogger);
        Assert.assertTrue(match, "Desc sorting Process is not working correctly");
        extentLogger.pass("PASSED");

    }


}
