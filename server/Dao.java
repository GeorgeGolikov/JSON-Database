package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import json.JSON;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// repository and entity at the same time
public class Dao {
    private static Dao instance;
    private JsonObject db;

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
        db = JSON.readJsonFromDB(dbJson);
    }

    public String set(JsonElement key, JsonElement value) {
        writeLock.lock();

        if (db == null) {
            writeLock.unlock();
            return errorStr;
        }

        boolean written = false;
        if (key.isJsonPrimitive()) {
            setValue(db, key.getAsString(), value);
            written = JSON.writeJsonToDB(db, dbJson);
        } else if (key.isJsonArray()) {
            setNestedValue(db, (JsonArray) key, value);
            written = JSON.writeJsonToDB(db, dbJson);
        }

        writeLock.unlock();
        return written ? "OK" : errorStr;
    }

    public String get(JsonElement key) {
        readLock.lock();

        if (db == null) {
            readLock.unlock();
            return errorStr;
        }

        String res = getEntity(findKeyObject(db, key));

        readLock.unlock();
        return res == null ? "ERROR" : res;
    }

    public String delete(JsonElement key) {
        writeLock.lock();

        if (db == null) {
            writeLock.unlock();
            return errorStr;
        }

        KeyObject keyObject = findKeyObject(db, key);

        JsonObject newJsonObject = deleteEntity(db, keyObject);
        if (newJsonObject == null) {
            writeLock.unlock();
            return "ERROR";
        }

        boolean written = JSON.writeJsonToDB(newJsonObject, dbJson);
        if (written) {
            this.db = JSON.readJsonFromDB(dbJson);
            writeLock.unlock();
            return "OK";
        }

        writeLock.unlock();
        return errorStr;
    }

    private void setValue(JsonObject currentJObject, String key, JsonElement value) {
        if (value.isJsonObject()) {
            currentJObject.add(key, value.getAsJsonObject());
        } else if (value.isJsonPrimitive()) {
            currentJObject.addProperty(key, value.getAsString());
        }
    }

    private void setNestedValue(JsonObject rootJson, JsonArray keys, JsonElement value) {
        JsonObject currentJObject = rootJson;
        JsonObject newJObject;
        String k = "";
        boolean branchIsCreated = false;
        for (int i = 0; i < keys.size(); i++) {
            k = keys.get(i).getAsString();
            if (!branchIsCreated && currentJObject.keySet().contains(k)) {
                JsonElement newJObj = currentJObject.get(k);
                if (newJObj.isJsonObject()) {
                    newJObject = newJObj.getAsJsonObject();

                    if (i < keys.size() - 1) {
                        currentJObject = newJObject;
                    }
                }
            } else {
                if (!branchIsCreated) {
                    branchIsCreated = true;
                }

                newJObject = new JsonObject();
                currentJObject.add(k, newJObject);

                if (i < keys.size() - 1) {
                    currentJObject = newJObject;
                }
            }
        }
        setValue(currentJObject, k, value);
    }

    private String getEntity(KeyObject keyObject) {
        JsonElement jsonEl = keyObject.getCurrentJObject().get(keyObject.k);
        if (jsonEl == null) {
            return null;
        }
        if (jsonEl.isJsonObject()) return new Gson().toJson(jsonEl);
        else if (jsonEl.isJsonPrimitive()) {
            return jsonEl.getAsString();
        }
        return null;
    }

    private JsonObject deleteEntity(final JsonObject rootJObject, final KeyObject keyObject) {
        if (keyObject.currentJObject.keySet().contains(keyObject.k)) {
            keyObject.currentJObject.remove(keyObject.k);
            return rootJObject;
        }
        return null;
    }

    private KeyObject findKeyObject(final JsonObject currentJObject, final JsonElement jeKey) {
        JsonObject newJObject = null;
        KeyObject keyObject = new KeyObject(currentJObject, "");

        if (jeKey.isJsonArray()) {
            JsonArray keys = jeKey.getAsJsonArray();
            for (int i = 0; i < keys.size(); i++) {
                keyObject.k = keys.get(i).getAsString();
                if (keyObject.currentJObject.keySet().contains(keyObject.k)) {
                    JsonElement jElem = keyObject.currentJObject.get(keyObject.k);
                    if (jElem.isJsonObject()) {
                        newJObject = jElem.getAsJsonObject();
                    } else if (jElem.isJsonPrimitive()) {
                        if (i < keys.size() - 1) {
                            break;
                        }
                    }
                    if (i < keys.size() - 1) {
                        keyObject.currentJObject = newJObject;
                    }
                } else {
                    break;
                }
            }
        } else if (jeKey.isJsonPrimitive()) {
            keyObject.k = jeKey.getAsString();
        }
        return keyObject;
    }

    private static class KeyObject {
        JsonObject currentJObject;
        String k;

        public KeyObject(JsonObject currentJObject, String k) {
            this.currentJObject = currentJObject;
            this.k = k;
        }

        public JsonObject getCurrentJObject() {
            return currentJObject;
        }
    }
}
