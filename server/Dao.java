package server;

import java.util.ArrayList;

public class Dao {
    public static int CAPACITY = 1000;

    private static Dao instance;
    private final ArrayList<String> cells;

    public static Dao getInstance() {
        if (instance == null) {
            instance = new Dao();
        }
        return instance;
    }

    private Dao() {
        cells = new ArrayList<>(CAPACITY);
        for (int i = 0; i < CAPACITY; ++i) {
            cells.add("");
        }
    }

    public String get(int cell) {
        if (checkCell(cell)) {
            String res = cells.get(cell);
            if (res.isEmpty()) {
                return "ERROR";
            }
            return res;
        }
        return "ERROR";
    }

    public String set(int cell, String text) {
        if (checkCell(cell)) {
            cells.set(cell, text);
            return "OK";
        }
        return "ERROR";
    }

    public String delete(int cell) {
        if (checkCell(cell)) {
            cells.set(cell, "");
            return "OK";
        }
        return "ERROR";
    }

    private static boolean checkCell(int cell) {
        return cell >= 0 && cell < CAPACITY;
    }
}
