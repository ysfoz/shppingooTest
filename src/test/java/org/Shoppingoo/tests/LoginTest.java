package org.Shoppingoo.tests;

import org.Shoppingoo.utilities.DataUtil;
import org.testng.Assert;
import org.testng.annotations.Test;



public class LoginTest extends TestBase {



    @Test(groups = "withoutlogin",dataProviderClass = DataUtil.class,dataProvider = "loginData")
    public void checkLoginWithValidAccounts(Object username, Object password){
        extentLogger =report.createTest("Check sign in with valid usernames and passwords");
        home.getLoginPage((String) username,(String)password);
        extentLogger.info("username : " + username + ", password : " + password);
        Assert.assertTrue(home.verifyLogin());
        extentLogger.info("Profile image is visible on navbar after signed in");
        extentLogger.pass("PASSED");

    }



}
