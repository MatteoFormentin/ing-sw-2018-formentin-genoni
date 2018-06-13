package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.server.ServerController;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Class that extends the RemotePlayer in order to define a Socket Player.
 * This class will be used to manage every socket player as a thread.
 *
 * @author DavideMammarella
 */
public class SocketPlayer extends RemotePlayer implements Runnable {

    // comunicazione con il server
    private final transient ServerController serverController;
    // comunicazione con il socket
    private final transient Socket clientConnection;

    // stream di input
    private final transient ObjectInputStream inputStream;
    // stream di output
    private final transient ObjectOutputStream outputStream;

    // protocollo che verrà usato per gestire le richieste da client
    private final transient RequestHandlerProtocol requestHandlerProtocol;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Socket Player Constructor.
     *
     * @param serverController server interface, used as controller to communicate with the server.
     * @param socket           socket used for server/client communication..
     * @throws IOException
     */
    public SocketPlayer(ServerController serverController, Socket socket) throws IOException {
        playerRunning=true;

        this.clientConnection = socket;
        this.serverController = serverController;

        this.inputStream = new ObjectInputStream(this.clientConnection.getInputStream());
        this.outputStream = new ObjectOutputStream(this.clientConnection.getOutputStream());
        this.outputStream.flush(); //USATO PER NON AVERE DEADLOCK

        this.requestHandlerProtocol = new RequestHandlerProtocol(this, inputStream,outputStream);
    }

    //------------------------------------------------------------------------------------------------------------------
    // RUNNER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Runner for Socket Player.
     *
     * @see Thread#run()
     */
    @Override
    public void run (){
        try {

            boolean loop = true;

            while (loop) {

                // IL SERVER è SEMPRE IN ASCOLTO
                System.out.println("Waiting for event...");

                Object object = inputStream.readObject();
                requestHandlerProtocol.handleRequest(object);

                }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        finally {
            closeConnections();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToView (EventView eventView)throws RemoteException{

    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to log the user to the server with his nickname.
     *
     * @param nickname name of the player.
     */
    public void login(String nickname) throws PlayerAlreadyLoggedException, RoomIsFullException {
        this.serverController.login(this);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Connection closer for socket player.
     * This method close the connection of the client and tell it to the remote player.
     */
    public void closeConnections(){
        try {
            playerRunning = false;
            inputStream.close();
            outputStream.close();
            clientConnection.close();

            System.out.println("Connection closed!");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
