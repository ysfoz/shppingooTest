package org.Shoppingoo.tests;

import org.Shoppingoo.pages.LoginPage;
import org.Shoppingoo.utilities.DataUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {

    @Test(groups = "withoutlogin", dataProviderClass = DataUtil.class, dataProvider = "loginData")
    public void checkLoginWithValidAccounts(Object username, Object password) {
        extentLogger = report.createTest("Check sign in with valid usernames and passwords");
        home.getLoginPage((String) username, (String) password);
        extentLogger.info("username : " + username + ", password : " + password);
        Assert.assertTrue(home.verifyLogin());
        extentLogger.info("Profile image is visible on navbar after signed in");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "withoutlogin")
    public void checkLoginWithShortUserName()  {
        String username = "tt";
        String password = "123456";
        String expectedError = "Username is too short - should be 3 chars minimum.";
        extentLogger = report.createTest("Check sign in with too short username ");
        LoginPage loginpage = home.goToLoginPage(username, password);
        extentLogger.info("username : " + username + ", password : " + password);
        Assert.assertTrue(loginpage.verifyUsername(expectedError));
        extentLogger.info("The Error is '" + expectedError + "' as expected");
        extentLogger.pass("PASSED");
    }
    @Test(groups = "withoutlogin")
    public void checkLoginWithNoUserName()  {
        String username = "";
        String password = "123456";
        String expectedError = "username is required";
        extentLogger = report.createTest("Check sign in with without username ");
        LoginPage loginpage = home.goToLoginPage(username, password);
        extentLogger.info("username : -" + username + ", password : " + password);
        Assert.assertTrue(loginpage.verifyUsername(expectedError));
        extentLogger.info("The Error is '" + expectedError + "' as expected");
        extentLogger.pass("PASSED");

    }

    @Test(groups = "withoutlogin")
    public void checkLoginWithShortPassword()  {
        String username = "admin";
        String password = "12345";
        String expectedError = "Password is too short - should be 6 chars minimum.";
        extentLogger = report.createTest("Check sign in with too short password ");
        LoginPage loginpage = home.goToLoginPage(username, password);
        extentLogger.info("username : " + username + ", password : " + password);
        Assert.assertTrue(loginpage.verifyPassword(expectedError));
        extentLogger.info("The Error is '" + expectedError + "' as expected");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "withoutlogin")
    public void checkLoginWithNoPassword()  {
        String username = "admin";
        String password = "";
        String expectedError = "No password provided.";
        extentLogger = report.createTest("Check sign in without password ");
        LoginPage loginpage = home.goToLoginPage(username, password);
        extentLogger.info("username : " + username + ", password : - " + password);
        Assert.assertTrue(loginpage.verifyPassword(expectedError));
        extentLogger.info("The Error is '" + expectedError + "' as expected");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "withoutlogin")
    public void checkLoginWithWrongPassword()  {
        String username = "admin";
        String password = "1234567";
        String expectedError = "Something went wrong !!!";
        extentLogger = report.createTest("Check sign in with wrong password");
        LoginPage loginpage = home.goToLoginPage(username, password);
        extentLogger.info("username : " + username + ", password : - " + password);
        Assert.assertTrue(loginpage.verfiyLoginWithInvalidValues(expectedError));
        extentLogger.info("Password is wrong, the Error is '" + expectedError + "' as expected");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "withoutlogin")
    public void checkLoginWithWrongUsername()  {
        String username = "adminn";
        String password = "123456";
        String expectedError = "Something went wrong !!!";
        extentLogger = report.createTest("Check sign in  with wrong username");
        LoginPage loginpage = home.goToLoginPage(username, password);
        extentLogger.info("username : " + username + ", password : - " + password);
        Assert.assertTrue(loginpage.verfiyLoginWithInvalidValues(expectedError));
        extentLogger.info("Username is wrong, the Error is '" + expectedError + "' as expected");
        extentLogger.pass("PASSED");
    }

    @Test(groups = "withoutlogin")
    public void checkLoginWithWrongUsernameAndPAssword()  {
        String username = "adminn";
        String password = "123455";
        String expectedError = "Something went wrong !!!";
        extentLogger = report.createTest("Check sign in  with wrong username and password");
        LoginPage loginpage = home.goToLoginPage(username, password);
        extentLogger.info("username : " + username + ", password : - " + password);
        Assert.assertTrue(loginpage.verfiyLoginWithInvalidValues(expectedError));
        extentLogger.info("Username and password are wrong, the Error is '" + expectedError + "' as expected");
        extentLogger.pass("PASSED");
    }




}
