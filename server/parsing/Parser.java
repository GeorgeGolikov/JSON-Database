package server.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    An example of parsing console commands with Scanner, line by line
 */

@Deprecated
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
                    userCommand.forEach(System.out::println);
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
}
