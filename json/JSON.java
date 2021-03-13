package json;

import client.Args;
import com.google.gson.Gson;

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

    public static UserCommand deserialize(String strJson) {
        return new Gson().fromJson(strJson, UserCommand.class);
    }
}
