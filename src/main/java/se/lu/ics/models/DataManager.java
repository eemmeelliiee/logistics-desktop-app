package se.lu.ics.models;

import java.io.*;
import java.util.*;

public class DataManager {
    private static DataManager instance;
    private Map<String, String> data;

    private DataManager() {
        data = new HashMap<>();
        loadData();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private void loadData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length >= 2) {
                    String key = parts[0];
                    String value = parts[1];
                    data.put(key, value);
                } else {
                    System.out.println("ignoring line: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            PrintWriter writer = new PrintWriter("data.txt", "UTF-8");
            for (Map.Entry<String, String> entry : data.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getData(String key) {
        return data.get(key);
    }

    public void setData(String key, String value) {
        data.put(key, value);
    }

    public void removeData(String key) {
        data.remove(key);
    }
}