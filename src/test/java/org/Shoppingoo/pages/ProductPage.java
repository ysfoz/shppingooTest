package org.Shoppingoo.pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductPage extends PageBase {


    public ProductPage() {

        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "img.sc-jfvxQR")
    WebElement productImage;

    @FindBy(css = "h1.sc-oZIhv")
    public WebElement productTitle;

    @FindBy(css = "p.sc-hiDMwi")
    WebElement productdesc;


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

    public Boolean controlBadge(int productIndex, ExtentTest extentLogger) {
        waitVisibilityOf(addToCartButton);
        int bofereClicked = Integer.parseInt(badge.getText());
        extentLogger.info("badge count before test : " + badge.getText() );
        addToCartButton.click();
        waitTextToBePresentInElement(badge, String.valueOf(bofereClicked + 1));
        extentLogger.info("badge count after first clicked : " + badge.getText() );
        driver.navigate().back();
        goProductPage(productIndex, driver.findElements(By.cssSelector("div.sc-iveFHk")));
        extentLogger.info("getting second product,index is " + productIndex );
        waitVisibilityOf(addToCartButton);
        addToCartButton.click();
        waitTextToBePresentInElement(badge, String.valueOf(bofereClicked + 2));
        extentLogger.info("badge count after second clicked : " + badge.getText() );
        return badge.getText().equals(String.valueOf(bofereClicked + 2));
    }

    public String selectColor() {
        WebElement selectedButton = colorButtons.get(colorButtons.size() - 1);
        selectedButton.click();
        return selectedButton.getAttribute("color");
    }

    public String selectSize() {
        Select select = new Select(sizeSelect);
        select.getFirstSelectedOption();
        return select.getFirstSelectedOption().getText();
    }

    public String selectAmount(int repeat) {
        int inc = 0;
        int decr = 0;
        if (repeat <= 0) {
            inc = 0;
            decr = 0;
        } else {
            inc = repeat;
            decr = repeat - 1;
        }
        waitVisibilityOf(amountIncrease);
        while (inc > 0) {
            amountIncrease.click();
            inc--;
        }
        while (decr > 0) {
            amountDecrease.click();
            decr--;
        }
        return amount.getText();

    }

    public void clickAddToCartButton() {
        int value = Integer.parseInt(badge.getText());
        addToCartButton.click();
        waitTextToBePresentInElement(badge, String.valueOf(value + 1));
    }


}



