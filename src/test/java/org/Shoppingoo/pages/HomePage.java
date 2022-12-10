package org.Shoppingoo.pages;

import org.Shoppingoo.utilities.AbstractClass;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.util.ArrayList;
import java.util.List;

public class HomePage extends AbstractClass {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
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

    @FindBy(css = "div.sc-iveFHk")
    List<WebElement> productList;

    @FindBy(css = "div.sc-iOeugr")
    WebElement product;

    // ACTIONS


    public void getPage() {
        driver.get("http://localhost:3000/");
    }

    //    @Test(description = "Verify the company logo, name, title are  visible.")
//    public void isVisibleCompanyNameLogoTitle() throws InterruptedException
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

    //    @Test(description = "Verify that the user is able to navigate through all the products across different categories")
//    public void getCataegories() throws InterruptedException
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

    // Most Popular Product on Home Screen

    public List mostPopulerProducts() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,700)");
        waitVisibilityOfElementLocated(By.cssSelector("div.sc-iveFHk"));
        List<Boolean> areAllImagesOnScreen = new ArrayList<>();
        productList.stream().forEach(item ->
                areAllImagesOnScreen.add(item.findElement(By.tagName("img")).isDisplayed())
        );
        return areAllImagesOnScreen;
    }

    public Boolean mostPopulerProductsButtons(int productIndex) {
        Actions actions = new Actions(driver);
        waitVisibilityOfElementLocated(By.cssSelector("div.sc-iveFHk"));
        WebElement productImage = productList.get(productIndex).findElement(By.tagName("img"));
        String imgLink = productImage.getAttribute("src");
        System.out.println(imgLink);
        actions.moveToElement(productImage);
        WebElement product = productList.get(productIndex).findElement(By.tagName("a"));
        product.click();
        waitVisibilityOf(product);
        String imgLinkNewPage = product.findElement(By.tagName("img")).getAttribute("src");
        return imgLink.equals(imgLinkNewPage);


    }


}
