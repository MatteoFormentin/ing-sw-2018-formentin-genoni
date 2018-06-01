package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.network.server.AbstractServer;
import it.polimi.se2018.network.server.ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the Socket server for every single game.
 * Extends AbstractServer abstract class in order to implement and facilitate Server connection.
 *
 * @author DavideMammarella
 */
public class SocketServer extends AbstractServer {

    // socket lato server
    private ServerSocket serverSocket;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Socket Server constructor.
     *
     * @param serverController server interface, used as controller to communicate with the server.
     */
    public SocketServer(ServerController serverController){
        super (serverController);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SERVER STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for Socket Server.
     *
     * @param port number of port that will be used on the connection.
     */
    @Override
    public void startServer(int port){
        try {
            // Inizializzo il server socket
            serverSocket=new ServerSocket(port);
            System.out.println("Socket Server running at " + port + " port...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Faccio partire il Client Gatherer
        new ClientGatherer().start();
    }

    //------------------------------------------------------------------------------------------------------------------
    // CLIENT GATHERER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Gatherer for client.
     * This thread manage new socket client connections.
     */
    private class ClientGatherer extends Thread{

        @Override
        public void run(){

            // In loop attendo la connessione di nuovi client
            // Per ogni client che si collega viene fatto partire un SocketPlayer
            System.out.println("Waiting for clients...\n");

            while(true) {

                Socket newClientConnection;

                try {
                    newClientConnection = serverSocket.accept();

                    System.out.println("A new client connected!");
                    // Aggiungo il client
                    //server.addClient(newClientConnection);
                    SocketPlayer socketPlayer = new SocketPlayer(getServerController(), newClientConnection);
                    new Thread(socketPlayer).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    // END
}