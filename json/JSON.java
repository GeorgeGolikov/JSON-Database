package json;

import client.Args;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class JSON {
    public static String serialize(Args args) {
        Map<String, String> command = new HashMap<>();
        command.put("type", args.getRequest());
        command.put("key", args.getCell());
        command.put("value", args.getText());

        return new Gson().toJson(command);
    }

    public static void deserialize(String strJson) {

    }
}
