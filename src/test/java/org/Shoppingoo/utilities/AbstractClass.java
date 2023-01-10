package org.Shoppingoo.utilities;

import org.Shoppingoo.pages.CartPage;
import org.Shoppingoo.pages.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractClass {

    WebDriver driver;

    public AbstractClass(WebDriver driver) {

        this.driver = driver;
//        PageFactory.initElements(driver,this);
    }

    // Nav Bar Buttons
    @FindBy(css = "div.sc-bqWxrE:first-child")
    WebElement gotoCartButtonOnNavBar;

    @FindBy(css = "div.sc-bqWxrE img")
    WebElement profileImage;

    @FindBy(xpath = "//button[contains(text(),'profile')]")
    WebElement goToProfileButton;

    @FindBy(xpath = "//button[contains(text(),'Logout')]")
    WebElement logoutButton;

    @FindBy(css = "span.MuiBadge-badge")
    public WebElement badge;

    @FindBy(css = "h1.sc-csuSiG")
    WebElement mainTitle;

    @FindBy(css = "img.sc-fEXmlR")
    WebElement logo;

    @FindBy(css = "input.sc-kDvujY")
    WebElement searchBar;

    @FindBy(css = "div.sc-iveFHk")
    public List<WebElement> productListOnHome;


    public void waitVisibilityOf(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitVisibilityOfElementLocated(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

//    public void waitTextToBePresentInElementValue(WebElement element){
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        wait.until(ExpectedConditions.textToBePresentInElementValue(element, String.valueOf(Integer.parseInt(element.getText()) + 1)));
//    }

    public void waitTextToBePresentInElement(WebElement element, String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.textToBePresentInElement(element, value));
    }


    public void goProductPage(int productIndex, List<WebElement> list) {
        Actions actions = new Actions(driver);
        waitVisibilityOfElementLocated(By.cssSelector("div.sc-iveFHk"));
        WebElement productImage = list.get(productIndex).findElement(By.tagName("img"));
        actions.moveToElement(productImage);
        list.get(productIndex).findElement(By.tagName("a")).click();
    }

    public ProductPage getProductPage(int productIndex) {
        goProductPage(productIndex, productListOnHome);
        return new ProductPage(driver);
    }

    public CartPage goToCartPage() {
        gotoCartButtonOnNavBar.click();
        return new CartPage(driver);
    }

    // Filter  -  Sort Procesess

    public List<Boolean> searchProduct(List<String> keyList) {
        List<Boolean> list = new ArrayList<>();
        for (int i = 0; i < keyList.size(); i++) {
            searchBar.sendKeys(keyList.get(i));
            if (productListOnHome.size() > 0) {
                waitVisibilityOf(productListOnHome.get(0));
            }
            ProductPage productPage = getProductPage(productListOnHome.size() - 1);
            waitVisibilityOf(productPage.productTitle);
            String titleKey = productPage.productTitle.getText().split(" ")[0].trim();
            System.out.println(titleKey);
            Boolean match = titleKey.toLowerCase().contains(keyList.get(i).toLowerCase());
            System.out.println(match);
            list.add(match);
            driver.navigate().back();
        }

        return list;
    }


}
