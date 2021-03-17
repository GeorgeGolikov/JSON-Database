package client;

import client.args.Args;
import json.JSON;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void run(Args args) {
        System.out.println("Client started!");

        try (
                Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            String strToSend = args == null ? "{\"type\":\"exit\"}" : JSON.serialize(args);

            output.writeUTF(strToSend);
            System.out.printf("Sent: %s%n", strToSend);
            System.out.printf("Received: %s%n", input.readUTF());
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
