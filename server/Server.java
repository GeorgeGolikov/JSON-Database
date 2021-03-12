package server;

import server.commands.Command;
import server.parsing.Parser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    public static void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName(SERVER_ADDRESS))) {
            System.out.println("Server started!");

            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String income = input.readUTF();
                    System.out.printf("Received: %s%n", income);

                    String out;
                    ArrayList<String> commands = Parser.parseCommand(income);
                    if (commands == null) break;
                    if (commands.isEmpty()) {
                        out = "ERROR";
                    } else {
                        Command command = Menu.createCommand(commands);
                        if (command == null) {
                            out = "ERROR";
                        } else {
                            Menu invoker = new Menu(command);
                            out = invoker.executeCommand();
                        }
                    }

                    output.writeUTF(out);
                    System.out.printf("Sent: %s%n", out);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
