package server;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import json.UserCommand;
import server.commands.Command;
import server.commands.DeleteCommand;
import server.commands.GetCommand;
import server.commands.SetCommand;

public class Menu {

    private Command command;

    public Menu(Command command) {
        this.command = command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String executeCommand() {
        return command.execute();
    }

    public static Command createCommand(UserCommand input) {
        JsonPrimitive type = input.getType();
        JsonElement key = input.getKey();
        JsonElement value = input.getValue();

        if (type == null || key == null) return null;

        String command = type.getAsString();
        switch (command) {
            case "set":
                if (value == null) return null;
                return new SetCommand(key, value);
            case "get":
                return new GetCommand(key);
            case "delete":
                return new DeleteCommand(key);
            default:
                return null;
        }
    }
}
