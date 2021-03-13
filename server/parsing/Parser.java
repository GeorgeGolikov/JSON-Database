package server.parsing;

import json.UserCommand;
import server.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    An example of parsing console commands with Scanner, line by line
 */

public final class Parser {

    public static void handleConsole() {
        ArrayList<String> userCommand;

        try (Scanner input = new Scanner(System.in)) {
            while (true) {
                try {
                    StringBuilder com = new StringBuilder("");
                    if (input.hasNextLine()) com.append(input.nextLine());

                    userCommand = Parser.parseCommand(com.toString());
                    if (userCommand == null) {
                        return;
                    }
                    Menu.createCommand(userCommand);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static ArrayList<String> parseCommand(String inputLine) {
        try (Scanner scanner = new Scanner(inputLine)) {
            if (scanner.hasNext()) {
                String command = scanner.next();
                if ("exit".equals(command)) {
                    return null;
                }

                if (scanner.hasNext()) {
                    String cell = scanner.next();

                    if (scanner.hasNext()) {
                        return new ArrayList<>(List.of(command, cell, scanner.nextLine().trim()));
                    }
                    return new ArrayList<>(List.of(command, cell));
                }
            }
            return new ArrayList<>();
        }
    }

    public static ArrayList<String> parseJsonPojoCommand(UserCommand command) {
        if (command == null) return new ArrayList<>();

        if ("exit".equals(command.getType())) {
            return null;
        }

        if (command.getValue() == null) {
            return new ArrayList<>(List.of(command.getType(), command.getKey()));
        }

        return new ArrayList<>(List.of(command.getType(), command.getKey(), command.getValue()));
    }
}
