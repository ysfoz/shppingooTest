package org.Shoppingoo.tests;

import org.Shoppingoo.pages.CartPage;
import org.Shoppingoo.pages.ProductListPage;
import org.Shoppingoo.pages.ProductPage;
import org.Shoppingoo.utilities.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test(groups = "logedIn", description = "to control products's color, size and amount are correct or not")
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

    // bu fonksiyonlari filter yada search sonrasi kullanmak daha faydali olacak. Key word ler belirleyerek, urunlerin isimlerine gore sonuclar almayi deneyelim.

    @Test(groups = "logedIn", description = "to check delete button in Basket")
    public void checkDeleteButtonInBasket() throws InterruptedException {
        home.addToCartFromMostPopuler(3);
        CartPage cartPage = home.goToCartPage();
        Integer productCount = cartPage.getCountOfProducts("basket");
        System.out.println(productCount);
        Integer afterClickProductCount = cartPage.deleteProduct("basket");
        System.out.println(afterClickProductCount);
        softAssert.assertTrue(productCount - 1 == afterClickProductCount, "delete button does not work");
        softAssert.assertAll();
    }

    @Test(groups = "logedIn", description = "to check delete button in save for later ")
    // basket icersindeki ilk delete butonunu calistiriyor
    public void checkDeleteButtonInSaveForLater() throws InterruptedException {
        home.addToCartFromMostPopuler(5);
        CartPage cartPage = home.goToCartPage();
        cartPage.addSaveForLater();
        Integer productCount = cartPage.getCountOfProducts("later");
        System.out.println(productCount);
        Integer afterClickProductCount = cartPage.deleteProduct("later");
        System.out.println(afterClickProductCount);
        softAssert.assertTrue(productCount - 1 == afterClickProductCount, "delete button does not work in save for later");
        softAssert.assertAll();
    }

    @Test(groups = "logedIn", description = "to check save for later button functionality")
    public void checkSaveForLaterButton() throws InterruptedException {
        home.addToCartFromMostPopuler(2);
        CartPage cartPage = home.goToCartPage();
        Integer productCount = cartPage.getCountOfProducts("basket");
        Integer saveForLaterItemCount = cartPage.getCountOfProducts("later");
        Integer afterClickSaveForLaterItemCount = cartPage.saveForLaterOrMoveToBasket("save");
        Integer afterClickProductCount = cartPage.getCountOfProducts("basket");
        softAssert.assertTrue(saveForLaterItemCount + 1 == afterClickSaveForLaterItemCount, "save for later button does not work - save for later list did not change");
        softAssert.assertTrue(productCount - 1 == afterClickProductCount, "safe for later button does not work - product list did not change");
        softAssert.assertAll();

    }


    @Test(groups = "logedIn", description = "to check move to basket button")
    public void checkMoveToBasketButton() throws InterruptedException {
        home.addToCartFromMostPopuler(1);
        CartPage cartPage = home.goToCartPage();
        Integer productCount = cartPage.getCountOfProducts("basket");
        cartPage.addSaveForLater();
        Integer saveForLaterItemCount = cartPage.getCountOfProducts("later");
        Integer afterClickSaveForLaterItemCount = cartPage.saveForLaterOrMoveToBasket("move");
        Integer afterClickProductCount = cartPage.getCountOfProducts("basket");
        softAssert.assertTrue(saveForLaterItemCount - 1 == afterClickSaveForLaterItemCount, "move to basket button does not work - save for later list did not change");
        softAssert.assertTrue(productCount == afterClickProductCount, "move to basket button does not work - product list did not change");
        softAssert.assertAll();

    }

    @Test(groups = "logedIn", description = "to check see more like this button")
    // her zamna basket icersindeki ilk butonunu calistiriyor
    public void checkSeeMoreLikeThis() throws InterruptedException {
        home.addToCartFromMostPopuler(1);
        CartPage cartPage = home.goToCartPage();
        cartPage.seeMoreLikeThis();
    }

// buraya kadar olan testler tekrar gozden gecirilecek - --  -- - - - - - -  - --


    // bu testen bilerek hata almak istiyorum, raporda gosterebilmek icin.
    @Test(groups = "logedIn", description = "to check possibility of same product 2 times adding to cart with add cart button ")
    public void controlSameProductTwoTimesAdding() throws InterruptedException {
        softAssert.assertTrue(home.addProductToCartThreeTimes(), "Same product can be added 2 or more times to cart");
        softAssert.assertAll();
    }

    @Test(groups = "logedIn", description = "to control search function with keyword value")
    public void verifyFunctionalityOfSearchbarAtHomePage() {
        List<String> keyList = new ArrayList<>(List.of("bag", "coat", "dress"));
        List<Boolean> matchList = home.searchProduct(keyList);
        softAssert.assertTrue(matchList.stream().allMatch(n -> n = true), "All search keys are not match with product's title");
        softAssert.assertAll();
    }

    @Test(groups = "logedIn", description = "To control filtering function, but only what colors are available for the product.")
    public void verifyColorFilter() throws InterruptedException {
        ProductListPage productListPage = home.getProductListPage();
        List<Boolean> matchList = productListPage.checkColorFilter();
        matchList.forEach(element -> softAssert.assertTrue(element, "This Product with this color not in product List"));
        softAssert.assertAll();
    }

    @Test(groups = "logedIn", description = "To control filtering function, but only what colors are not available for the product.")
    public void verifyColorFilterNotInProduct() throws InterruptedException {
        ProductListPage productListPage = home.getProductListPage();
        List<Boolean> matchList = productListPage.checkColorFilterNotInProduct();
        matchList.forEach(element -> softAssert.assertFalse(element, "This Product with this size in product List"));
        softAssert.assertAll();
    }

    @Test(groups = "logedIn", description = "To control filtering function, but only what sizes are available for the product.")
    public void verifySizeFilter() throws InterruptedException {
        ProductListPage productListPage = home.getProductListPage();
        List<Boolean> matchList = productListPage.checkSizeFilter();
        matchList.forEach(element -> softAssert.assertTrue(element, "This Product with this size not in product List"));
        softAssert.assertAll();
    }

    @Test(groups = "logedIn", description = "To control filtering function, but only what sizes are not available for the product.")
    public void verifySizeFilterNotInProduct() throws InterruptedException {
        ProductListPage productListPage = home.getProductListPage();
        List<Boolean> matchList = productListPage.checkSizeFilterNotInProduct();
        matchList.forEach(element -> softAssert.assertFalse(element, "This Product with this size in product List"));
        softAssert.assertAll();
    }

    @Test(groups = "logedIn", description = "To control filtering functions for sizes and colors with keyword values")
    public void verifySizeAndColorWithKeyword() throws InterruptedException {
        ProductListPage productListPage = home.getProductListPage();
        Boolean match1 = productListPage.checkSizeAndColorWithKeyword("red", "s");
        Boolean match2 = productListPage.checkSizeAndColorWithKeyword("yellow", "xl");
        Boolean match3 = productListPage.checkSizeAndColorWithKeyword("white", "l");
        Boolean match4 = productListPage.checkSizeAndColorWithKeyword("black", "xs");
        softAssert.assertTrue(match1, "selected a product that is in the list but it is not on the screen");
        softAssert.assertTrue(match2, "selected a product that is not in the list but it is on the screen");
        softAssert.assertTrue(match3, "selected a product that is not in the list but it is on the screen");
        softAssert.assertTrue(match4, "selected a product that is not in the list but it is on the screen");
        softAssert.assertAll();
    }
    @Test(groups = "logedIn", description = "To control filtering functions and sorting functions together")
    public void verifySortingProductsWithPriceAsc() throws InterruptedException{
        ProductListPage productListPage = home.getProductListPage();
        Boolean match = productListPage.controlSortFunction("black","s","asc");
        softAssert.assertTrue(match,"Asc sorting Process is not working correctly");

    }

    @Test(groups = "logedIn", description = "To control filtering functions and sorting functions together")
    public void verifySortingProductsWithPriceDesc() throws InterruptedException{
        ProductListPage productListPage = home.getProductListPage();
        Boolean match = productListPage.controlSortFunction("blue","l","desc");
        softAssert.assertTrue(match,"Desc sorting Process is not working correctly");

    }


}
