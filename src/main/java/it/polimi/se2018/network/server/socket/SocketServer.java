package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.network.socket.SocketConnection;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that define the socket server for every single game.
 *
 * @author Davide Mammarella
 */

public final class SocketServer {
    private static ServerSocket serverSocket;
    private static Thread connectionsHandler;
    private static List<SocketConnection> socketConnections;

    /**
     * Constructor used to create a new socket server.
     * @param port used from the socket to connect to the server
     */
    public SocketServer(Integer port) {
        try{
            serverSocket=new ServerSocket(port);
            connectionsHandler = new Thread(new it.polimi.se2018.network.socket.SocketServer.ConnectionHandler());
            socketConnections = new ArrayList<>();
            connectionsHandler.start();
        }catch(Exception Exception) {
            //eccezione da sistemare
            this.terminate();
        }
    }


    /**
     * Terminate a single Socket Server
     */
    public void terminate(){ }

    /**
     * Handle the socket connection
     * It implements a Runnable because every thread must be managed in an indipendent way
     */
    private class ConnectionHandler implements Runnable {
        private boolean run = true;

        @Override
        public void run() {
            while(this.run) {
                try {
                    Socket socket = serverSocket.accept();
                    SocketConnection connection = new SocketConnection(socket, it.polimi.se2018.network.socket.SocketServer.this);
                    new Thread(connection).start();
                    it.polimi.se2018.network.socket.SocketServer.socketConnections.add(connection);
                } catch(Exception Exception) {
                    //eccezione da sistemare
                    this.terminate();
                }
            }
        }

        public void terminate() {
            this.run = false;
        }
    }

}