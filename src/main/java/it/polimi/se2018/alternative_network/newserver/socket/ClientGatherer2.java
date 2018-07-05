package it.polimi.se2018.alternative_network.newserver.socket;


import it.polimi.se2018.alternative_network.newserver.Server2;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Gatherer for client.
 * This class extend thread for manage only new socket client connections.
 *
 * @author DavideMammarella
 */
public class ClientGatherer2 extends Thread {

    private SocketServer server;

    // Utilizzo variabili atomiche perchè evitano problemi di concorrenza
    // Così prevengo conflitti nel settaggio e check delle variabili da metodi differenti
    private final AtomicBoolean running = new AtomicBoolean();

    private ServerSocket serverSocket;


    public ClientGatherer2(SocketServer server, int port) throws IOException {
        this.server = server;
        //Client gatherer created!
        this.serverSocket = new ServerSocket(port);
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
        Thread.currentThread().setName("Client Gatherer Thread");

        // In loop attendo la connessione di nuovi client
        // Per ogni client che si collega non può ancora essere associato ad un socket player
        while (true) {
            Socket newClientConnection;
            try {
                newClientConnection = serverSocket.accept();
                System.out.println("A new client connected.");
                server.addClient(newClientConnection);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
        }
    }
}
