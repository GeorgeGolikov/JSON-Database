package server;

import json.JSON;

import java.io.File;
import java.util.Map;

// repository and entity at the same time
public class Dao {
    private static Dao instance;
    private final Map<String, String> cells;

    private final File dbJson = new File(
            "/home/george/Work/Java/IdeaProjects/JSON Database/JSON Database/task/src/server/data/db.json"
    );
    private final String errorStr = "Database not opened";

    public static Dao getInstance() {
        if (instance == null) {
            instance = new Dao();
        }
        return instance;
    }

    private Dao() {
        cells = JSON.deserialize(dbJson);
    }

    public String get(String cell) {
        if (cells == null) return errorStr;
        String res = cells.get(cell);
        return res == null ? "ERROR" : res;
    }

    public String set(String cell, String text) {
        if (cells == null) return errorStr;
        cells.put(cell, text);
        return JSON.serializeAndWrite(cells, dbJson) ? "OK" : errorStr;
    }

    public String delete(String cell) {
        if (cells == null) return errorStr;
        if (cells.remove(cell) == null) return "ERROR";
        return JSON.serializeAndWrite(cells, dbJson) ? "OK" : errorStr;
    }
}
