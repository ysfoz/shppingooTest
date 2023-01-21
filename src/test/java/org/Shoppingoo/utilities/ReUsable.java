package org.Shoppingoo.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ReUsable {

    public static String getScreenShoot(String testCaseName) throws IOException {

        // name the screenshot with the current date time
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot ts = (TakesScreenshot) Driver.get();
        File source = ts.getScreenshotAs(OutputType.FILE);

        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + testCaseName + date + ".png";
        File finalDestination = new File(target);

        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }

    public static List<HashMap<String, String>> getDataMaptoString(String path) throws IOException {

        String jsonString = FileUtils.readFileToString(new File(System
                .getProperty("user.dir") + path), "UTF-8");

        ObjectMapper mapper = new ObjectMapper();

        List<HashMap<String, String>> data = mapper.readValue(jsonString, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;
    }
}
