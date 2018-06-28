package it.polimi.se2018.network.server.socket;


import it.polimi.se2018.network.server.ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientGatherer implements Runnable {


    private final int port;
    boolean flag = true;
    private ServerSocket serverSocket;
    private ServerController serverController;


    public ClientGatherer(int port, ServerController serverController) {
        this.port = port;
        this.serverController = serverController;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Waiting for clients.\n");

        while (flag) {
            Socket newClientConnection;
            try {
                newClientConnection = serverSocket.accept();
                System.out.println("A new client connected.");
                new Thread(new SocketPlayer(serverController, newClientConnection)).start();

            } catch (IOException e) {
                flag = false;
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.flag = false;
    }
}
