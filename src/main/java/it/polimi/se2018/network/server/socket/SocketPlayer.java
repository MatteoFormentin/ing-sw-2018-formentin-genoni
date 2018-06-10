package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.server.ServerController;

import java.io.*;
import java.net.Socket;

/**
 * Class that extends the RemotePlayer in order to define a Socket Player.
 * This class is an handler for the request from client.
 *
 * @author DavideMammarella
 */
public class SocketPlayer extends RemotePlayer implements Runnable{

    // comunicazione con il server
    private final transient ServerController serverController;
    // comunicazione con il socket
    private final transient Socket socket;

    // stream
    // stream di input
    private final transient ObjectInputStream inputStream;
    // stream di output
    private final transient ObjectOutputStream outputStream;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Socket Player Constructor.
     *
     * @param serverController server interface, used as controller to communicate with the server.
     * @param socket socket used for server/client communication..
     * @throws IOException
     */
    public SocketPlayer(ServerController serverController, Socket socket) throws IOException {
        this.socket=socket;
        this.serverController=serverController;

        this.inputStream = new ObjectInputStream(this.socket.getInputStream());
        this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.outputStream.flush(); //USATO PER NON AVERE DEADLOCK
    }

    //------------------------------------------------------------------------------------------------------------------
    // RUNNER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Runner for Socket Player.
     *
     * @see Thread#run()
     */
    //TODO: ASSOCIA SOCKET PLAYER AL CLIENT
    //TODO: RIMUOVERE IL CLIENT
    @Override
    public void run(){
        try {

            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            boolean loop = true;

            while(loop) {

                // IL SERVER è SEMPRE IN ASCOLTO
                System.out.println("Waiting for messages.");

                String message = is.readLine();
                if ( message == null ) {
                    loop = false;
                } else {
                    //server.getImplementation().send(new Message(message));
                }

            }

            socket.close();
            //server.removeClient(this);

            System.out.println("Connection closed.");


        } catch (IOException e) {
            e.printStackTrace();
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
    public void sendEventToView(EventView eventView) {

    }
}
