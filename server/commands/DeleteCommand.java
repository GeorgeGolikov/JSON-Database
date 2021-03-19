package server.commands;

import com.google.gson.JsonElement;
import server.Dao;

public class DeleteCommand implements Command {

    private final JsonElement key;

    public DeleteCommand(JsonElement key) {
        this.key = key;
    }

    @Override
    public String execute() {
        return Dao.getInstance().delete(key);
    }
}
