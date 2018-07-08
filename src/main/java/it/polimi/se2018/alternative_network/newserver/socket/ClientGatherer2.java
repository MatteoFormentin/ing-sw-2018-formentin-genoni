package it.polimi.se2018.alternative_network.newserver.socket;


import it.polimi.se2018.alternative_network.newserver.PrincipalServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Gatherer for client.
 * This class extend thread for manage only new socket client connections.
 *
 * @author DavideMammarella
 */
public class ClientGatherer2 extends Thread {

    // Utilizzo variabili atomiche perchè evitano problemi di concorrenza
    // Così prevengo conflitti nel settaggio e check delle variabili da metodi differenti
    private final AtomicBoolean running;
    private PrincipalServer server;
    private ServerSocket serverSocket;


    public ClientGatherer2(PrincipalServer server, int port) throws IOException {
        this.server = server;
        //Client gatherer created!
        this.serverSocket = new ServerSocket(port);
        running = new AtomicBoolean();
        running.set(false);
    }

    //------------------------------------------------------------------------------------------------------------------
    // THREAD STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Runner for client gatherer thread.
     * While loop use AtomicBoolean in order to permit a clean managing of the thread.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        running.set(true);
        Thread.currentThread().setName("Client Gatherer Thread");
        // In loop attendo la connessione di nuovi client
        // Per ogni client che si collega non può ancora essere associato ad un socket player
        while (running.get()) {
            Socket newClientConnection;
            try {
                newClientConnection = serverSocket.accept();
                System.out.println("A new client connected.");
                SocketPlayer newPlayer = new SocketPlayer(newClientConnection, server);
                (new Thread(newPlayer)).start();



            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void stopGatherer() {
        running.set(false);
    }
}
