package server;

import server.commands.Command;
import server.commands.DeleteCommand;
import server.commands.GetCommand;
import server.commands.SetCommand;

import java.util.ArrayList;

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

    public static Command createCommand(ArrayList<String> input) {
        if (input.size() == 2) {
            String command = input.get(0);
            switch (command) {
                case "get":
                    return new GetCommand(Integer.parseInt(input.get(1)) - 1);
                case "delete":
                    return new DeleteCommand(Integer.parseInt(input.get(1)) - 1);
                default:
                    return null;
            }
        } else if (input.size() == 3) {
            String command = input.get(0);
            if ("set".equals(command)) {
                return new SetCommand(Integer.parseInt(input.get(1)) - 1, input.get(2));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
