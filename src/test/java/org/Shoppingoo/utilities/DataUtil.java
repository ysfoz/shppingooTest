package org.Shoppingoo.utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DataUtil {

    @DataProvider(name = "loginData")
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = ReUsable.getDataMaptoString("/src/test/java/org/Shoppingoo/data/userData.json");
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
}
