package org.Shoppingoo.pages;

import org.Shoppingoo.utilities.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductPage extends AbstractClass {

    WebDriver driver;

    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "img.sc-jfvxQR")
    WebElement productImage;

    @FindBy(css = "h1.sc-oZIhv")
    WebElement productTitle;

    @FindBy(css = "p.sc-hiDMwi")
    WebElement productdesc;

    @FindBy(css = "h1.sc-oZIhv")
    WebElement productPrice;

    @FindBy(css = "div.sc-ckEbSK:nth-last-child(2)")
    WebElement productColor;

    @FindBy(css = "div.sc-GhhNo")
    List<WebElement> colorButtons;

    @FindBy(css = "div.sc-ckEbSK:nth-last-child(1)")
    WebElement productSize;

    @FindBy(css = "select.sc-fXqpFg")
    WebElement sizeSelect;

    @FindBy(css = "div.sc-jIILKH")
    WebElement amountContainer;

    @FindBy(xpath = "//*[@d='M19 13H5v-2h14v2z']/parent::*[local-name() = 'svg']")
    WebElement amountDecrease;

    @FindBy(xpath = "//*[@d='M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z']/parent::*[local-name() = 'svg']")
    WebElement amountIncrease;

    @FindBy(css = "span.sc-ZqFbI")
    WebElement amount;

    @FindBy(css = "button.sc-jNJNQp")
    WebElement addToCartButton;



//    @Test(groups = "logedIn",description = "to get product page and check all elements on this page")
//    public void goProductPage()
    public Boolean imageOnScreen() {
        waitVisibilityOf(productImage);
        return productImage.isDisplayed();
    }

    public Boolean titleOnScreen() {
        waitVisibilityOf(productTitle);
        return productTitle.isDisplayed();
    }

    public Boolean descOnScreen() {
        waitVisibilityOf(productdesc);
        return productdesc.isDisplayed();
    }

    public Boolean priceOnScreen() {
        waitVisibilityOf(productPrice);
        return productPrice.isDisplayed();
    }

    public Boolean colorOnScreen() {
        waitVisibilityOf(productColor);
        return productColor.isDisplayed();
    }

    public Boolean sizeOnScreen() {
        waitVisibilityOf(productSize);
        return productSize.isDisplayed();
    }

    public Boolean amountContainerOnScreen() {
        waitVisibilityOf(amountContainer);
        return amountContainer.isDisplayed();
    }

    public Boolean addToCartButtonOnScreen() {
        waitVisibilityOf(addToCartButton);
        return addToCartButton.isDisplayed();
    }

    public Boolean controlBadge(int productIndex) {
        waitVisibilityOf(addToCartButton);
        int bofereClicked = Integer.parseInt(badge.getText());
        addToCartButton.click();
        waitTextToBePresentInElement(badge,String.valueOf(bofereClicked+1));
        driver.navigate().back();
        goProductPage(productIndex,driver.findElements(By.cssSelector("div.sc-iveFHk")));
        waitVisibilityOf(addToCartButton);
        addToCartButton.click();
        waitTextToBePresentInElement(badge,String.valueOf(bofereClicked+2));
        return badge.getText().equals(String.valueOf(bofereClicked+2));
    }
}



