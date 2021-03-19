package json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class UserCommand {
    private JsonPrimitive type;
    private JsonElement key;
    private JsonElement value;

    public UserCommand() {
    }

    public UserCommand(JsonPrimitive type, JsonElement key, JsonElement value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public JsonPrimitive getType() {
        return type;
    }

    public JsonElement getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }

    public void setType(JsonPrimitive type) {
        this.type = type;
    }

    public void setKey(JsonElement key) {
        this.key = key;
    }

    public void setValue(JsonElement value) {
        this.value = value;
    }
}
