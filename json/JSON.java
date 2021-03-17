package json;

import client.Args;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSON {
    public static String serialize(Args args) {
        Map<String, String> command = new LinkedHashMap<>();
        command.put("type", args.getRequest());
        command.put("key", args.getCell());
        if (!"".equals(args.getText())) command.put("value", args.getText());

        return new Gson().toJson(command);
    }

    public static String serialize(String response, String reason, String value) {
        Map<String, String> command = new LinkedHashMap<>();

        command.put("response", response);
        if (reason != null) command.put("reason", reason);
        if (value != null) command.put("value", value);

        return new Gson().toJson(command);
    }

    public static boolean serializeAndWrite(Map<String, String> cells, File file) {
        try (JsonWriter writer = new JsonWriter(new FileWriter(file))) {
            writer.beginObject();
            for (var cell : cells.entrySet()) {
                writer.name(cell.getKey()).value(cell.getValue());
            }
            writer.endObject();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static UserCommand deserialize(String strJson) {
        return new Gson().fromJson(strJson, UserCommand.class);
    }

    public static HashMap<String, String> deserialize(File file) {
        HashMap<String, String> cells = new HashMap<>();
        String key = null;

        try (JsonReader reader = new JsonReader(new FileReader(file))) {
            while (reader.hasNext()) {
                JsonToken nextToken = reader.peek();

                if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
                    reader.beginObject();
                } else if (JsonToken.NAME.equals(nextToken)) {
                    key = reader.nextName();
                } else if (JsonToken.STRING.equals(nextToken)) {
                    String value = reader.nextString();
                    if (key == null) throw new IOException("Got value but no key was provided");
                    cells.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return cells;
    }
}
