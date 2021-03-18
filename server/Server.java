package server;

import json.JSON;
import server.commands.Command;
import server.parsing.Parser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    public static void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName(SERVER_ADDRESS))) {
            System.out.println("Server started!");
            int poolSize = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(poolSize);
            Dao dao = Dao.getInstance();

            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept();
                executor.submit(() -> {
                    try {
                        processRequests(socket, serverSocket);
                    } catch (IOException e) {
                        System.out.println(e.getMessage() + "!!!");
                    }
                });
            }
        } catch (RuntimeException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void processRequests(Socket socket, ServerSocket serverSocket) throws IOException {
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        String income = input.readUTF();
        System.out.printf("Received: %s%n", income);

        String out;
        ArrayList<String> commands = Parser.parseJsonPojoCommand(JSON.deserialize(income));
        if (commands == null) {
            out = JSON.serialize("OK", null, null);
            output.writeUTF(out);
            System.out.printf("Sent: %s%n", out);

            input.close();
            output.close();
            socket.close();
            serverSocket.close();
            Runtime.getRuntime().exit(0);
        }
        if (commands.isEmpty()) {
            out = JSON.serialize("ERROR", null, null);
        } else {
            Command command = Menu.createCommand(commands);
            if (command == null) {
                out = JSON.serialize("ERROR", null, null);
            } else {
                Menu invoker = new Menu(command);
                String result = invoker.executeCommand();
                if ("Database not opened".equals(result)) {
                    out = JSON.serialize("ERROR", result, null);
                } else if (!"OK".equals(result) && !"ERROR".equals(result)) {
                    out = JSON.serialize("OK", null, result);
                } else if ("OK".equals(result)) {
                    out = JSON.serialize("OK", null, null);
                } else {
                    out = JSON.serialize("ERROR", "No such key", null);
                }
            }
        }

        output.writeUTF(out);
        System.out.printf("Sent: %s%n", out);

        input.close();
        output.close();
        socket.close();
    }
}
