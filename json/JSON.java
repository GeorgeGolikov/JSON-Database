package json;

import client.args.Args;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JSON {
    // serialize client command
    public static String serialize(Args args) {
        String result = "";

        if ("-in".equals(args.getRequest())) {
            String FILE_REQUEST_PATH_TEST_ENVIRONMENT =
                    System.getProperty("user.dir") + "/src/client/data/";
            String FILE_REQUEST_PATH_LOCAL_ENVIRONMENT =
                    System.getProperty("user.dir") + "/JSON Database/task/src/client/data/";

            result = readFileAsString(FILE_REQUEST_PATH_TEST_ENVIRONMENT + args.getCell());
        } else {
            Map<String, String> command = new LinkedHashMap<>();
            command.put("type", args.getRequest());
            command.put("key", args.getCell());
            if (!"".equals(args.getText())) command.put("value", args.getText());
            result = new Gson().toJson(command);
        }

        return result;
    }

    // serialize server response
    // Gson cannot create json primitives from strings with whitespace characters!!!!!!!! e.g. "Elon Mask"
    public static String serialize(String response, String reason, String value) {
        if (reason != null) {

            return String.format("{\"response\":\"%s\",\"reason\":\"%s\"}", response, reason);

        } else if (value != null) {
            if (value.contains("{")) {

                return String.format("{\"response\":\"%s\",\"value\":%s}", response, value);

            }

            return String.format("{\"response\":\"%s\",\"value\":\"%s\"}", response, value);
        }

        return String.format("{\"response\":\"%s\"}", response);
    }

    // read json command into UserCommand class
    public static UserCommand deserialize(String strJson) {
        UserCommand command = new UserCommand();
        JsonObject request = new Gson().fromJson(strJson, JsonObject.class);

        JsonElement type = request.get("type");
        if (type != null && type.isJsonPrimitive()) {
            command.setType((JsonPrimitive) type);
        } else {
            command.setType(null);
        }

        command.setKey(request.get("key"));
        command.setValue(request.get("value"));

        return command;
    }

    public static boolean writeJsonToDB(JsonObject json, File file) {
        try (JsonWriter writer = new JsonWriter(new FileWriter(file))) {
            new Gson().toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static JsonObject readJsonFromDB(File file) {
        try (JsonReader reader = new JsonReader(new FileReader(file))) {
            return new Gson().fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String readFileAsString(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
