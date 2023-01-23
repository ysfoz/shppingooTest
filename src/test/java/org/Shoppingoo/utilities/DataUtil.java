package org.Shoppingoo.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DataUtil {

    @DataProvider(name = "loginData")
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getDataMaptoString("/src/test/java/org/Shoppingoo/data/userData.json");
        Object[][] list = new Object[data.size()][2];
        Iterator<HashMap<String, String>> iterator = data.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            HashMap<String, String> obj = iterator.next();
            list[i][0] = obj.get("username");
            list[i][1] = obj.get("password");
            i++;
        }
        return list;
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

