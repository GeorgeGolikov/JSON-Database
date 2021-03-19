package server.commands;

import com.google.gson.JsonElement;
import server.Dao;

public class SetCommand implements Command {

    private final JsonElement key;
    private final JsonElement value;

    public SetCommand(JsonElement key, JsonElement value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String execute() {
        return Dao.getInstance().set(key, value);
    }
}
