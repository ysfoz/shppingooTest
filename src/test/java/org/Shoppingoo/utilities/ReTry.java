package org.Shoppingoo.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class ReTry implements IRetryAnalyzer {
    int count = 0;
    int maxTry = 2;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if (count < maxTry) {
            count++;
            System.out.println(count);
            return true;
        }
        return false;
    }
}
