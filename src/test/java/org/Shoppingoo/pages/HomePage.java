package org.Shoppingoo.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends PageBase {

    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    // LOCATORS
    @FindBy(css = "h1.sc-csuSiG")
    WebElement companyName;

    @FindBy(css = "img.sc-fEXmlR")
    WebElement companyLogo;

    @FindBy(xpath = "//button[text()='Show more . . .']")
    List<WebElement> sliderCategoriesButtons;

    @FindBy(xpath = "//button[text()='SHOP NOW']")
    List<WebElement> peopleCategoriesButtons;

    @FindBy(xpath = "//div[@direction='right']")
    WebElement slideRightButton;

    @FindBy(xpath = "//a[@href='/login']")
    WebElement loginButton;

    @FindBy(xpath = "//a[@href='/register']")
    WebElement registerButton;

    @FindBy(xpath = "//input[@placeholder='username']")
    WebElement username;

    @FindBy(xpath = "//input[@placeholder='password']")
    WebElement password;

    @FindBy(css = "button.sc-dkBdza")
    WebElement loginSubmitButton;

    @FindBy(css = "img.sc-ksBlkl")
    WebElement profilImage;

    @FindBy(xpath = "//h1[text()='WOMAN']/following-sibling::button")
    WebElement womanProductButton;


    // ACTIONS


    public Boolean getCompanyLogo() throws InterruptedException {
        waitVisibilityOf(companyLogo);
        return companyLogo.isDisplayed();
    }

    public Boolean getCompanyName() throws InterruptedException {
        waitVisibilityOf(companyName);
        return companyName.getText().equalsIgnoreCase("Shoppingoo");
    }

    public Boolean getTitle() throws InterruptedException {
        return driver.getTitle().contains("React");
    }

    public List clickSliderCategoryButtons() {
        waitVisibilityOfElementLocated(By.xpath("//button[text()='Show more . . .']"));
        for (int i = 0; i < sliderCategoriesButtons.size(); i++) {
            if (i == 1) {
                slideRightButton.click();
            }
            if (i == 2) {
                slideRightButton.click();
                slideRightButton.click();
            }
            waitVisibilityOfElementLocated(By.xpath("//button[text()='Show more . . .']"));
            sliderCategoriesButtons.get(i).click();

            driver.navigate().back();
        }
        return sliderCategoriesButtons;
    }

    public List clickPeopleCategoryButtons() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        waitVisibilityOfElementLocated(By.xpath("//button[text()='SHOP NOW']"));
        js.executeScript("window.scrollBy(0,700)");
        for (int i = 0; i < peopleCategoriesButtons.size(); i++) {
            waitVisibilityOfElementLocated(By.xpath("//button[text()='SHOP NOW']"));
            peopleCategoriesButtons.get(i).click();
            driver.navigate().back();
        }
        return peopleCategoriesButtons;
    }

    public List mostPopulerProducts() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,700)");
        waitVisibilityOfElementLocated(By.cssSelector("div.sc-iveFHk"));
        List<Boolean> areAllImagesOnScreen = new ArrayList<>();
        productListOnHome.stream().forEach(item ->
                areAllImagesOnScreen.add(item.findElement(By.tagName("img")).isDisplayed())
        );
        return areAllImagesOnScreen;
    }

    public void getLoginPage(String user, String userPassword) {
        waitVisibilityOf(loginButton);
        loginButton.click();
        waitVisibilityOf(username);
        username.sendKeys(user);
        password.sendKeys(userPassword);
        loginSubmitButton.click();
    }

    public Boolean verifyLogin() {
        waitVisibilityOf(profilImage);
        return profilImage.isDisplayed();
    }

    public void addToCartFromMostPopuler(int productIndex) {
        waitVisibilityOfElementLocated(By.cssSelector("div.sc-iveFHk"));
        WebElement productImage = productListOnHome.get(productIndex).findElement(By.tagName("img"));
        actions.moveToElement(productImage);
        productListOnHome.get(productIndex).findElement(By.tagName("svg")).click();
    }

    public Boolean addProductToCartThreeTimes() throws InterruptedException {
        waitVisibilityOf(badge);
        int totalProductFirst = Integer.parseInt(badge.getText());
        addToCartFromMostPopuler(4);
        waitFor(1);
        addToCartFromMostPopuler(4);
        waitFor(1);
        addToCartFromMostPopuler(4);
        waitFor(1);
        int totalProductLast = Integer.parseInt(badge.getText());
        return (totalProductLast - 1) == totalProductFirst;
    }

    public ProductListPage getProductListPage() {
        waitVisibilityOf(womanProductButton);
        womanProductButton.click();
        return new ProductListPage();
    }

}
