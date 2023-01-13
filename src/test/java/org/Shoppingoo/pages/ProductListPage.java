package org.Shoppingoo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductListPage extends PageBase {

    public ProductListPage() {
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

    @FindBy(xpath = "//div[2]//select[1]")
    WebElement selectSort;

    // FILTERING //
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

    public List<Boolean> checkColorFilter()  {
        List<Boolean> matchList = new ArrayList<>();
        String imageSrc = getImageSrc();
        List<String> colors = getColors();
        Select select = new Select(selectColor);
        for (int i = 0; i < colors.size(); i++) {
            select.selectByVisibleText(colors.get(i));
            waitFor(3);
            Boolean match = productImageList.stream().anyMatch(element -> element.getAttribute("src").equals(imageSrc));
            matchList.add(match);
        }
        return matchList;
    }

    public List<Boolean> checkSizeFilter()  {
        List<Boolean> matchList = new ArrayList<>();
        String imageSrc = getImageSrc();
        List<String> sizes = getSizes();
        Select select = new Select(selectSize);
        for (int i = 0; i < sizes.size(); i++) {
            select.selectByVisibleText(sizes.get(i));
            waitFor(3);
            Boolean match = productImageList.stream().anyMatch(element -> element.getAttribute("src").equals(imageSrc));
            matchList.add(match);
        }
        return matchList;
    }

    public List<Boolean> checkColorFilterNotInProduct()  {
        List<Boolean> matchList = new ArrayList<>();
        List<String> allcolors = new ArrayList<>(List.of("black", "white", "blue", "yellow", "green", "red"));
        String imageSrc = getImageSrc();
        List<String> colors = getColors();
        Select select = new Select(selectColor);
        allcolors.removeAll(colors);
        for (int i = 0; i < allcolors.size(); i++) {
            select.selectByVisibleText(allcolors.get(i));
            waitFor(3);
            Boolean match = productImageList.stream().anyMatch(element -> element.getAttribute("src").equals(imageSrc));
            matchList.add(match);
        }
        return matchList;
    }

    public List<Boolean> checkSizeFilterNotInProduct() {
        List<Boolean> matchList = new ArrayList<>();
        List<String> allSizes = new ArrayList<>(List.of("xs", "s", "m", "l", "xl"));
        String imageSrc = getImageSrc();
        List<String> sizes = getSizes();
        Select select = new Select(selectSize);
        allSizes.removeAll(sizes);
        for (int i = 0; i < allSizes.size(); i++) {
            if (allSizes.get(i).equals("xs")) {
                select.selectByVisibleText("s");
                waitFor(3);
            }
            select.selectByVisibleText(allSizes.get(i));
            waitFor(3);
            Boolean match = productImageList.stream().anyMatch(element -> element.getAttribute("src").equals(imageSrc));
            matchList.add(match);

        }
        return matchList;
    }

    public Boolean checkSizeAndColorWithKeyword(String color, String size)  {
        Boolean colors = getColors().stream().anyMatch(item -> item.equals(color));
        Boolean sizes = getSizes().stream().anyMatch(item -> item.equals(size));
        Select selcolor = new Select(selectColor);
        Select selsize = new Select(selectSize);
        String imageSrc = getImageSrc();
        waitFor(3);
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

    // SORTING WITH FOLTERING //

    public String getPrice() {
        return productPrice.getText().split(" ")[1].trim();
    }

    public void setColor(String color) {
        Select select = new Select(selectColor);
        select.selectByVisibleText(color);
    }

    public void setSize(String size) {
        Select select = new Select(selectSize);
        select.selectByVisibleText(size);
    }

    public void setPriceSort(String descOrAsc) {
        Select select = new Select(selectSort);
        select.selectByValue(descOrAsc);
    }


    public List<String> SortFilteredProductsPrice(String color, String size, String descOrAsc)  {
        String price;
        List<String> priceList = new ArrayList<>();
        setColor(color);
        setSize(size);
        setPriceSort(descOrAsc);
        int count = 0;
        waitFor(3);
        int listSize = productList.size();
        while (count < listSize) {
            setColor(color);
            setSize(size);
            setPriceSort(descOrAsc);
            waitFor(3);
            goProductPage(count, productList);
            waitFor(3);
            price = getPrice();
            priceList.add(price);
            driver.navigate().back();
            count++;
        }
        return priceList;

    }

    public Boolean controlSortFunction(String color, String size, String descOrAsc) throws InterruptedException {
        List<String> list = SortFilteredProductsPrice(color, size, descOrAsc);
        List<Integer> priceList =list.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> sortedList = priceList.stream().sorted().collect(Collectors.toList());
        if (descOrAsc.equals("asc") && sortedList.equals(priceList)) {
            return true;
        } else if (descOrAsc.equals("desc") && sortedList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).equals(priceList)) {
            return true;
        } else {
            return false;
        }


    }


}
