package org.example.prefs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Prefs {

    public static final String DB_JDBC_CONNECTION_URL = "urlDB";

    private Map<String, Object> prefs;
    public static final String DEFAULT_PREFS_FILENAME = "prefs.json";

    public Prefs(){
        this(DEFAULT_PREFS_FILENAME);
    }

    public Prefs(String fileName){
        try {
            String json = String.join("\n", Files.readAllLines(Paths.get(fileName)));

            prefs = new Gson().fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getString(String key){
        return  prefs.get(key).toString();
    }
}
