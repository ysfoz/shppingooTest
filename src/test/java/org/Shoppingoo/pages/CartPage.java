package org.Shoppingoo.pages;

import org.Shoppingoo.utilities.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

    @FindBy(css = "div.sc-hsWlPz div.sc-jGNhvO:first-of-type div.sc-caPbAK:nth-last-of-type(2)")
    WebElement lastAddedProduct;

    @FindBy(css = "div.sc-hsWlPz div.sc-jGNhvO:first-of-type")
    WebElement productContainer;

    @FindBy(css = "div.sc-hsWlPz div.sc-jGNhvO:first-of-type div.sc-caPbAK")
    List<WebElement> productList;

    @FindBy(css = "div.sc-hsWlPz div.sc-jGNhvO:first-of-type div.sc-dvEHMn")
    List<WebElement> buttonboxesUnderProducts;

//    @FindBy(css = "div.sc-hsWlPz div.sc-jGNhvO:last-child")
//    WebElement saveForLaterProductsContainer;

    @FindBy(xpath = "//span[text()='Your save for later items']/following-sibling::div")
    WebElement saveForLaterProductsContainer;


    @FindBy(css = "div.sc-hsWlPz div.sc-jGNhvO:last-child div.sc-caPbAK")
    List<WebElement> saveForLaterList;

//    @FindBy(css = "div.sc-hsWlPz div.sc-jGNhvO:last-child div.sc-dvEHMn")
//    List<WebElement> buttonboxesUnderSaveForLater;

    @FindBy(xpath = "//span[text()='Your save for later items']/following-sibling::div//div[contains(@class, 'sc-dvEHMn')]")
    List<WebElement> buttonboxesUnderSaveForLater;


    @FindBy(xpath = "//button[text()='Orders']")
    WebElement ordersButton;

    @FindBy(xpath = "//button[text()='CONTINUE SHOPPING']")
    WebElement continueShoppingButton;

    @FindBy(xpath = "//button[text()='CHECKOUT NOW']")
    WebElement CheckoutButton;

    @FindBy(xpath = "//*[@type='total']//span[contains(.,'$')]")
    WebElement totalPrice;

    @FindBy(xpath = "//div[@style='font-size: 12px; color: blueviolet;']")
    List<WebElement> allPriceList;

    @FindBy(css = "h6.sc-fxTzYC:first-child")
    List<WebElement> productsInSeeMoreLikeThis;


    public List<String> getColorSizeAmountFromCart() {
        List<String> list = new ArrayList<>();
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
        if (priceOnOrderSummery == 0) {
            return true;
        }
        for (int i = 0; i < allPriceList.size(); i++) {
            int price = Integer.parseInt(allPriceList.get(i).getText().split(" ")[1].trim());
            totalPriceOnProducts += price;
        }
        if (totalPriceOnProducts < 60) {
            totalPriceOnProducts += 5.90;
        }
        return totalPriceOnProducts == priceOnOrderSummery;

    }

    public Integer getCountOfProducts(String whichList) {
        if (whichList.equals("basket")) {
            return productList.size();
        } else {
            return saveForLaterList.size();
        }
    }

    public Integer deleteProduct(String whichList) throws InterruptedException {
        if (whichList.equals("basket")) {
            waitVisibilityOf(productContainer);
            WebElement deleteButton = buttonboxesUnderProducts.get(buttonboxesUnderProducts.size() - 1).findElement(By.xpath("//button[text()='Delete']"));
            deleteButton.click();
            Thread.sleep(3000);
            return productList.size();
        } else {
            waitVisibilityOf(saveForLaterProductsContainer);
            WebElement deleteButton = buttonboxesUnderSaveForLater.get(buttonboxesUnderSaveForLater.size() - 1).findElement(By.xpath("//button[text()='Delete']"));
            deleteButton.click();
            Thread.sleep(3000);
            return saveForLaterList.size();
        }
    }

    public void addSaveForLater() throws InterruptedException {
        WebElement saveForLaterButton = buttonboxesUnderProducts.get(buttonboxesUnderProducts.size() - 1).findElement(By.xpath("//button[text()='Save for later']"));
        saveForLaterButton.click();
        Thread.sleep(3000);
    }

    public void moveToBasket() {
        WebElement moveToBasketButton = buttonboxesUnderSaveForLater.get(buttonboxesUnderSaveForLater.size() - 1).findElement(By.xpath("//button[text()='move to basket']"));
        moveToBasketButton.click();
    }

    // bu fonksiyon uzerinde calisilacak
    public void seeMoreLikeThis() throws InterruptedException {
        String title = productList.get(productList.size() - 1).findElement(By.cssSelector("span.sc-gzrROc")).getText().split(":")[1].split(" ")[0].trim();
        System.out.println(title + " title");
        System.out.println(productList.size() + " product list");
        waitVisibilityOf(buttonboxesUnderProducts.get(buttonboxesUnderProducts.size() - 1));
        Thread.sleep(5000);
        buttonboxesUnderProducts.forEach(item -> System.out.println(item.findElement(By.xpath("//button[text()='See more like this']")).getAttribute("class")));
        WebElement seeMoreLikeThisButton = buttonboxesUnderProducts.get(buttonboxesUnderProducts.size() - 1).findElement(By.xpath("//button[text()='See more like this']"));
        seeMoreLikeThisButton.click();
        System.out.println(buttonboxesUnderProducts.size() + " button boexes list");
        waitVisibilityOf(productsInSeeMoreLikeThis.get(0));
        productsInSeeMoreLikeThis.forEach(element -> System.out.println("element " + element.getText().split(" ")[0].trim()));
    }

    public Integer saveForLaterOrMoveToBasket(String action) throws InterruptedException {
        if (action.equals("save")) {
            addSaveForLater();
            return saveForLaterList.size();
        } else {
            moveToBasket();
            return saveForLaterList.size();
        }

    }

}
