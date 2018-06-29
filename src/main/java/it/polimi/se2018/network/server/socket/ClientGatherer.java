package it.polimi.se2018.network.server.socket;


import it.polimi.se2018.network.server.ServerController;

import java.io.IOException;
import java.net.BindException;
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
        }
        // dal funzionamento imposto il client gatherer può partire senza aver conoscenza del numero di server
        // quindi è utile creare una bind exception per evitare che si generino errori durante la creazione
        catch (BindException e){
            System.err.println("New Client Gatherer Connection refused!");
        }
        catch (IOException e) {
            System.err.println("New Client Gatherer Connection refused!");
        }
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Client Gatherer Thread");

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
