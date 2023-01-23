package org.Shoppingoo.pages;

import org.Shoppingoo.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;


public class LoginPage extends PageBase {

    public LoginPage() {
        PageFactory.initElements(Driver.get(), this);
    }


    @FindAll({@FindBy(css = "span.sc-WKhSL:nth-of-type(2)"),
            @FindBy(css = "span.sc-WKhSL:nth-of-type(1)")
    })
    WebElement passwordError;

    @FindBy(css = "span.sc-WKhSL:nth-of-type(1)")
    WebElement userNameError;

    @FindAll({@FindBy(css = "span.sc-WKhSL:nth-of-type(3)"),
            @FindBy(css = "span.sc-WKhSL:nth-of-type(1)")
    })
    WebElement loginError;


    public Boolean verifyUsername(String expectedError) {
        return expectedError.equals(userNameError.getText());

    }

    public Boolean verifyPassword(String expectedError) {
        return expectedError.equals(passwordError.getText());

    }

    public Boolean verfiyLoginWithInvalidValues(String expectedError) {
        return expectedError.equals(loginError.getText());
    }

}
