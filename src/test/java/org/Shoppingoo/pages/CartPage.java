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

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div.sc-caPbAK:nth-last-of-type(2)")
    WebElement lastAddedProduct;

    @FindBy(css = "div.sc-caPbAK")
    List<WebElement> productList;

    @FindBy(xpath = "//*[@type='total']//span[contains(.,'$')]")
    WebElement totalPrice;

    @FindBy(xpath = "//div[@style='font-size: 12px; color: blueviolet;']")
    List<WebElement> allPriceList;


    public List<String> getColorSizeAmountFromCart() {
        List<String> list = new ArrayList<String>();
        String color = lastAddedProduct.findElement(By.cssSelector("div.sc-gkSfol")).getAttribute("color");
        String size = lastAddedProduct.findElement(By.cssSelector("span.sc-fmPOXC")).getText().split(":")[1].trim();
        String amount = lastAddedProduct.findElement(By.cssSelector("div.sc-iqPaeV")).getText();
        list.add(color);
        list.add(size);
        list.add(amount);
        return list;
    }

    public Boolean compareTotalPrice() {
        int totalPriceOnProducts = 0;
        int priceOnOrderSummery = Integer.parseInt(totalPrice.getText().split(" ")[1].trim());
        if (priceOnOrderSummery == 0){
            return true;
        }
        for(int i = 0; i < allPriceList.size();i++){
            int price = Integer.parseInt(allPriceList.get(i).getText().split(" ")[1].trim());
            totalPriceOnProducts += price;
        }
        if(totalPriceOnProducts < 60){
            totalPriceOnProducts += 5.90;
        }
        return totalPriceOnProducts == priceOnOrderSummery;


    }


}
