package org.Shoppingoo.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class AbstractClass {

    WebDriver driver;

    public AbstractClass(WebDriver driver){

        this.driver = driver;
//        PageFactory.initElements(driver,this);
    }
    // Nav Bar Buttons
    @FindBy(css = "MuiBadge-root")
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

    @FindBy(css ="img.sc-fEXmlR" )
    WebElement logo;



    public void waitVisibilityOf(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitVisibilityOfElementLocated(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitTextToBePresentInElement(WebElement element, String value){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.textToBePresentInElement(element,value));
    }

    public void goProductPage(int productIndex, List<WebElement> list){
        Actions actions = new Actions(driver);
        waitVisibilityOfElementLocated(By.cssSelector("div.sc-iveFHk"));
        WebElement productImage = list.get(productIndex).findElement(By.tagName("img"));
        actions.moveToElement(productImage);
        list.get(productIndex).findElement(By.tagName("a")).click();
    }


}
