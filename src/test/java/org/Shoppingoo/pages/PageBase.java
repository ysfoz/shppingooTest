package org.Shoppingoo.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.Shoppingoo.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class PageBase {
    WebDriver driver = Driver.get();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    Actions actions = new Actions(driver);

    public PageBase() {

        PageFactory.initElements(driver,this);
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

    public @FindBy(css = "span.sc-ehvNnt")
    WebElement productPrice;


    public void waitVisibilityOf(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitVisibilityOfElementLocated(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    public void waitTextToBePresentInElement(WebElement element, String value) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, value));
    }


    public void goProductPage(int productIndex, List<WebElement> list) {
        waitVisibilityOfElementLocated(By.cssSelector("div.sc-iveFHk"));
        WebElement productImage = list.get(productIndex).findElement(By.tagName("img"));
        actions.moveToElement(productImage);
        list.get(productIndex).findElement(By.tagName("a")).click();
    }

    public ProductPage getProductPage(int productIndex,ExtentTest extentLogger) {
        extentLogger.info("getting the product, index is " + productIndex);
        goProductPage(productIndex, productListOnHome);
        return new ProductPage();
    }

    public CartPage goToCartPage(ExtentTest extentLogger) {
        gotoCartButtonOnNavBar.click();
        extentLogger.info("Go to Cart");
        return new CartPage();
    }

    // Filter  -  Sort Procesess

    public List<Boolean> searchProduct(List<String> keyList,ExtentTest extentLogger) throws InterruptedException {
        List<Boolean> list = new ArrayList<>();
        extentLogger.info("search keys : " + keyList);
        for (int i = 0; i < keyList.size(); i++) {
            waitFor(3);
            searchBar.sendKeys(keyList.get(i));
            if (productListOnHome.size() > 0) {
                waitVisibilityOf(productListOnHome.get(0));

            }
            ProductPage productPage = getProductPage(productListOnHome.size() - 1,extentLogger);
            waitVisibilityOf(productPage.productTitle);
            String titleKey = productPage.productTitle.getText().split(" ")[0].trim();
            Boolean match = titleKey.toLowerCase().contains(keyList.get(i).toLowerCase());
            list.add(match);
            extentLogger.info("key: " + keyList.get(i)+ " / result : " + match );
            driver.navigate().back();
        }

        return list;
    }

    public  void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
