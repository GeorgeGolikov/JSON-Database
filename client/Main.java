package client;

import client.args.Args;
import com.beust.jcommander.JCommander;

public class Main {

    public static void main(String[] args) {
        Client.run(parseCLArgs(args));
    }

    private static Args parseCLArgs(String[] args) {
        if (args.length == 2) {
            if ("-in".equals(args[0])) {
                Args arguments = new Args();
                arguments.setRequest(args[0]);
                arguments.setCell(args[1]);
                return arguments;
            }
            if ("exit".equals(args[1])) return null;
        }

        Args arguments = new Args();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        return arguments;
    }
}


