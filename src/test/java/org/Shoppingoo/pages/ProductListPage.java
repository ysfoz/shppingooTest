package org.Shoppingoo.pages;

import org.Shoppingoo.utilities.AbstractClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ProductListPage extends AbstractClass {
    WebDriver driver;

    public ProductListPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div.sc-iveFHk")
    List<WebElement> productList;

    @FindBy(css = "div.sc-iveFHk img")
    List<WebElement> productImageList;

    @FindBy(css = "div.sc-GhhNo")
    List<WebElement> colorList;

    @FindBy(css = "select.sc-fXqpFg")
    WebElement selectInProduct;

    @FindBy(xpath = "//select[@name='color']")
    WebElement selectColor;
    @FindBy(xpath = "//select[@name='size']")
    WebElement selectSize;

    public List<String> getColors() {
        List<String> colors = new ArrayList<>();
        goProductPage(productList.size() - 1, productList);
        waitVisibilityOf(colorList.get(0));
        colorList.forEach(element -> colors.add(element.getAttribute("color")));
        driver.navigate().back();
        return colors;
    }

    public List<String> getSizes() {
        List<String> sizes = new ArrayList<>();
        goProductPage(productList.size() - 1, productList);
        waitVisibilityOf(selectInProduct);
        Select select = new Select(selectInProduct);
        select.getOptions().forEach(element -> sizes.add(element.getText()));
        driver.navigate().back();
        return sizes;
    }

    public String getImageSrc() {
        return productImageList.get(productImageList.size() - 1).getAttribute("src");
    }

    public List<Boolean> checkColorFilter() throws InterruptedException {
        List<Boolean> matchList = new ArrayList<>();
        String imageSrc = getImageSrc();
        List<String> colors = getColors();
        Select select = new Select(selectColor);
        for (int i = 0; i < colors.size(); i++) {
            select.selectByVisibleText(colors.get(i));
            Thread.sleep(2000);
            Boolean match = productImageList.stream().anyMatch(element -> element.getAttribute("src").equals(imageSrc));
            matchList.add(match);
        }
        return matchList;
    }

    public List<Boolean> checkSizeFilter() throws InterruptedException {
        List<Boolean> matchList = new ArrayList<>();
        String imageSrc = getImageSrc();
        List<String> sizes = getSizes();
        Select select = new Select(selectSize);
        for (int i = 0; i < sizes.size(); i++) {
            select.selectByVisibleText(sizes.get(i));
            Thread.sleep(2000);
            Boolean match = productImageList.stream().anyMatch(element -> element.getAttribute("src").equals(imageSrc));
            matchList.add(match);
        }
        return matchList;
    }

    public List<Boolean> checkColorFilterNotInProduct() throws InterruptedException {
        List<Boolean> matchList = new ArrayList<>();
        List<String> allcolors = new ArrayList<>(List.of("black", "white", "blue", "yellow", "green", "red"));
        String imageSrc = getImageSrc();
        List<String> colors = getColors();
        Select select = new Select(selectColor);
        allcolors.removeAll(colors);
        for (int i = 0; i < allcolors.size(); i++) {
            select.selectByVisibleText(allcolors.get(i));
            Thread.sleep(2000);
            Boolean match = productImageList.stream().anyMatch(element -> element.getAttribute("src").equals(imageSrc));
            matchList.add(match);
        }
        return matchList;
    }

    public List<Boolean> checkSizeFilterNotInProduct() throws InterruptedException {
        List<Boolean> matchList = new ArrayList<>();
        List<String> allSizes = new ArrayList<>(List.of("xs", "s", "m", "l", "xl"));
        String imageSrc = getImageSrc();
        List<String> sizes = getSizes();
        Select select = new Select(selectSize);
        allSizes.removeAll(sizes);
        for (int i = 0; i < allSizes.size(); i++) {
            if (allSizes.get(i).equals("xs")) {
                select.selectByVisibleText("s");
                Thread.sleep(2000);
            }
            select.selectByVisibleText(allSizes.get(i));
            Thread.sleep(2000);
            Boolean match = productImageList.stream().anyMatch(element -> element.getAttribute("src").equals(imageSrc));
            matchList.add(match);

        }
        return matchList;
    }


    public Boolean checkSizeAndColorWithKeyword(String color, String size) throws InterruptedException {
        Boolean colors = getColors().stream().anyMatch(item -> item.equals(color));
        Boolean sizes = getSizes().stream().anyMatch(item -> item.equals(size));
        Select selcolor = new Select(selectColor);
        Select selsize = new Select(selectSize);
        String imageSrc = getImageSrc();
        Thread.sleep(2000);
        if (color.equals("white")) {
            selcolor.selectByVisibleText("black");
            selcolor.selectByVisibleText(color);
        } else {
            selcolor.selectByVisibleText(color);
        }
        if (size.equals("xs")) {
            selsize.selectByVisibleText("s");
            selsize.selectByVisibleText(size);
        } else {
            selsize.selectByVisibleText(size);
        }

        Boolean match = productImageList.stream().anyMatch(element -> element.getAttribute("src").equals(imageSrc));
        if (colors.equals(true) && sizes.equals(true) && match.equals(true)) {
            return true;
        } else if (colors.equals(false) && sizes.equals(true) && match.equals(false)) {
            return true;
        } else if (colors.equals(true) && sizes.equals(false) && match.equals(false)) {
            return true;
        } else if (colors.equals(false) && sizes.equals(false) && match.equals(false)) {
            return true;
        }
        return false;

    }


}
