package server.commands;

import com.google.gson.JsonElement;
import server.Dao;

public class GetCommand implements Command {

    private final JsonElement key;

    public GetCommand(JsonElement key) {
        this.key = key;
    }

    @Override
    public String execute() {
        return Dao.getInstance().get(key);
    }
}
