package org.Shoppingoo.pages;

import org.Shoppingoo.utilities.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends AbstractClass {

    WebDriver driver;
    public CartPage(WebDriver driver){
        super(driver);
        this.driver =driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = "div.sc-caPbAK:nth-last-of-type(2)")
    WebElement lastAddedProduct;

    public List<String> getColorSizeAmountFromCart(){
        List<String> list = new ArrayList<String>();
        String color = lastAddedProduct.findElement(By.cssSelector("div.sc-gkSfol")).getAttribute("color");
        String size = lastAddedProduct.findElement(By.cssSelector("span.sc-fmPOXC")).getText().split(":")[1].trim();
        String amount = lastAddedProduct.findElement(By.cssSelector("div.sc-iqPaeV")).getText();
        list.add(color);
        list.add(size);
        list.add(amount);
        return list;
    }


}
