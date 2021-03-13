package client;

import com.beust.jcommander.JCommander;

public class Main {

    public static void main(String[] args) {
        Client.run(parseCLArgs(args));
    }

    private static Args parseCLArgs(String[] args) {
        if (args.length == 2 && "exit".equals(args[1])) return null;

        Args arguments = new Args();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        return arguments;
    }
}


