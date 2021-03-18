package server;

import json.JSON;

import java.io.File;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// repository and entity at the same time
public class Dao {
    private static Dao instance;
    private final Map<String, String> cells;

    private final File dbJson = new File(
            "./src/server/data/db.json"
    );
//    private final File dbJson = new File(
//            "/home/george/Work/Java/IdeaProjects/JSON Database/JSON Database/task/src/server/data/db.json"
//    );

    private final String errorStr = "Database not opened";

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

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
        readLock.lock();

        if (cells == null) {
            readLock.unlock();
            return errorStr;
        }

        String res = cells.get(cell);

        readLock.unlock();
        return res == null ? "ERROR" : res;
    }

    public String set(String cell, String text) {
        writeLock.lock();

        if (cells == null) {
            writeLock.unlock();
            return errorStr;
        }

        cells.put(cell, text);
        boolean written = JSON.serializeAndWrite(cells, dbJson);

        writeLock.unlock();
        return written ? "OK" : errorStr;
    }

    public String delete(String cell) {
        writeLock.lock();

        if (cells == null) {
            writeLock.unlock();
            return errorStr;
        }

        if (cells.remove(cell) == null) {
            writeLock.unlock();
            return "ERROR";
        }

        boolean written = JSON.serializeAndWrite(cells, dbJson);

        writeLock.unlock();
        return written ? "OK" : errorStr;
    }
}
