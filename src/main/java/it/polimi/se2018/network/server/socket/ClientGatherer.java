package it.polimi.se2018.network.server.socket;


import it.polimi.se2018.network.server.ServerController;
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
 * This class will implement a thread that manage new socket client connections.
 *
 * @author DavideMammarella
 */
public class ClientGatherer implements Runnable {

    private final int port;
    // Utilizzo variabili atomiche perchè evitano problemi di concorrenza
    // Così prevengo conflitti nel settaggio e check delle variabili da metodi differenti
    private final AtomicBoolean running = new AtomicBoolean();
    private ServerSocket serverSocket;
    private ServerController serverController;

    boolean flag=true;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Client Gatherer constructor.
     *
     * @param port port that will be used on the connection.
     */
    public ClientGatherer(int port, ServerController serverController) {
        this.port = port;
        this.serverController = serverController;

        //Client gatherer created!
        try {
            this.serverSocket = new ServerSocket(port);
            AnsiConsole.out.println(ansi().fg(GREEN).a("SOCKET connection created!").reset());
            AnsiConsole.out.println(ansi().fg(DEFAULT).a("Socket Server running at " + port + " port").reset());
            AnsiConsole.out.println(ansi().fg(DEFAULT).a("<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>>\n").reset());
        }
        // dal funzionamento imposto il client gatherer può partire senza aver conoscenza del numero di server
        // quindi è utile creare una bind exception per evitare che si generino errori durante la creazione
        catch (BindException e){
            System.err.println("Client gatherer already initialized!");
        }
        catch (IOException e) {
            System.err.println("New Client Gatherer Connection refused!");
        }
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
        // Per ogni client che si collega viene fatto partire un SocketPlayer

        while (flag) {
            Socket newClientConnection;

            try {
                newClientConnection = serverSocket.accept();
                AnsiConsole.out.println(ansi().fg(DEFAULT).a("-----------------------------------------").reset());
                AnsiConsole.out.println(ansi().fg(DEFAULT).a("A new client connected!").reset());
                SocketPlayer socketPlayer = new SocketPlayer(serverController, newClientConnection);
                new Thread(socketPlayer).start();
            } catch (SocketException e){
                // eccezione che classifica problemi di connessione
                System.err.println("Connection issue during client connection.\nError: "+e.getMessage());
            } catch (IOException e) {
                // eccezione che classifica problemi IO generali
                System.err.println("Client connection refused.\nError: "+e.getMessage());
                flag=false;
            } catch (NullPointerException e1){
                // CATCH DEL NULLPOINTER PERCHè SE IL CLIENT GATHERER NON C'è SIGNIFICA CHE NON è STATO CREATO IL THREAD
                System.err.println("Socket Server Connection refused on this port!");
                AnsiConsole.out.println(ansi().fg(DEFAULT).a("<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>>\n").reset());
                System.exit(0);
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Stopper for the client gatherer thread.
     */
    public void stop(){
        this.running.set(false);
    }
}
