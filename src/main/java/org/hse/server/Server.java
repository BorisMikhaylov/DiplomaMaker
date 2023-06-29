package org.hse.server;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;

public class Server {
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("Server is listening on port 8000");

            try (Socket socket = serverSocket.accept()) {
                System.out.println("New client connected");

                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                File file = (File) in.readObject();
                System.out.println("Received: " + file.getPath());
            }
        }
    }
}
