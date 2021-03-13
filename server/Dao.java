package server;

import java.util.HashMap;
import java.util.Map;

public class Dao {
    private static Dao instance;
    private final Map<String, String> cells;

    public static Dao getInstance() {
        if (instance == null) {
            instance = new Dao();
        }
        return instance;
    }

    private Dao() {
        cells = new HashMap<>();
    }

    public String get(String cell) {
        String res = cells.get(cell);
        return res == null ? "ERROR" : res;
    }

    public String set(String cell, String text) {
        cells.put(cell, text);
        return "OK";
    }

    public String delete(String cell) {
        return cells.remove(cell) == null ? "ERROR" : "OK";
    }
}
