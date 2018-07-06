package it.polimi.se2018.alternative_network.newserver.socket;


import it.polimi.se2018.alternative_network.newserver.PrincipalServer;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.AskLogin;

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

    private PrincipalServer server;

    // Utilizzo variabili atomiche perchè evitano problemi di concorrenza
    // Così prevengo conflitti nel settaggio e check delle variabili da metodi differenti
    private final AtomicBoolean running = new AtomicBoolean();

    private ServerSocket serverSocket;


    public ClientGatherer2(PrincipalServer server, int port) throws IOException {
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
                SocketPlayer newPlayer = new SocketPlayer(newClientConnection, server);
                newPlayer.run();
                AskLogin packet = new AskLogin();
                newPlayer.sendEventToView(packet);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
        }
    }
}
