package it.polimi.se2018.network.server.socket;


import it.polimi.se2018.network.server.ServerController;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.ansi;

public class ClientGatherer implements Runnable {


    private final int port;
    boolean flag = true;
    private ServerSocket serverSocket;
    private ServerController serverController;


    public ClientGatherer(int port, ServerController serverController) {
        this.port = port;
        this.serverController = serverController;

        //Client gatherer created!
        try {
            this.serverSocket = new ServerSocket(port);
            AnsiConsole.out.println(ansi().fg(GREEN).a("SOCKET connection created!").reset());
            AnsiConsole.out.println(ansi().fg(DEFAULT).a("Socket Server running at " + port + " port").reset());
            AnsiConsole.out.println(ansi().fg(DEFAULT).a("-----------------------------------------").reset());
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

    @Override
    public void run() {
        Thread.currentThread().setName("Client Gatherer Thread");

        while (flag) {
            Socket newClientConnection;
            try {
                // Waiting for clients...
                newClientConnection = serverSocket.accept();
                System.out.println("A new client connected!");
                new Thread(new SocketPlayer(serverController, newClientConnection)).start();

            } catch (IOException e) {
                e.printStackTrace();
                flag = false;
            } catch (NullPointerException e1){
                System.err.println("Socket Server Connection refused on this port!");
                System.exit(0);
            }
        }
    }

    public void stop() {
        this.flag = false;
    }


}
