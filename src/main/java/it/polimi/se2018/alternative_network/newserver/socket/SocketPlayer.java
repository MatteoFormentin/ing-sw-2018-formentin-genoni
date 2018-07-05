package it.polimi.se2018.alternative_network.newserver.socket;

import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.alternative_network.newserver.Server2;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.PlayerNetworkException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.SocketObject;
import org.fusesource.jansi.AnsiConsole;
import sun.awt.image.ImageWatched;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;

import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Class that extends the RemotePlayer in order to define a Socket Player.
 * This class will be used to manage every socket player as a thread.
 *
 * @author DavideMammarella
 */
public class SocketPlayer extends RemotePlayer2 implements Runnable {

    // LISTA DEI GIOCATORI CHE HANNO EFFETTUATO IL LOGIN ED HANNO UN NICKNAME
    static HashMap<String, SocketPlayer> socketPlayers;
    // comunicazione con il server
    private final Server2 server;
    private Socket tunnel;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private LinkedList<SocketObject>  eventViews;


    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Socket Player Constructor.
     *
     * @param serverController server interface, used as controller to communicate with the server.
     * @param connection       tunnel used to manage the socket connection.
     */
    public SocketPlayer(Server2 serverController, Socket connection) {
        this.server = serverController;

        this.tunnel = connection;

        try {
            this.outputStream = new ObjectOutputStream(tunnel.getOutputStream());
            this.inputStream = new ObjectInputStream(tunnel.getInputStream());
            this.outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // THREAD STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Runner for socket player thread.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        Thread.currentThread().setName("Socket Player Thread");
        boolean flag = true;
        while (flag && tunnel.isConnected()) {
            try {
                SocketObject received = (SocketObject) inputStream.readObject();
                socketObjectTraducer(received);
            } catch (EOFException e) {
                System.err.println("Player: " + getNickname() + " has made a disconnection!");
                // se si disconnette metto il running a false e tengo in memoria il player
                closeConnection();
                flag = false;
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                flag = false;
            }
        }
        closeConnection();
    }

    /**
     * Method used to traduce the object received from the client.
     *
     * @param socketObject object that will use the server to unleash the event associated.
     */
    private void socketObjectTraducer(SocketObject socketObject) {
        String type = socketObject.getType();

        if (type.equals("Login")) {
            try {
                setNickname(socketObject.getStringField());
                server.login(this);
                sendAck();
            } catch (RoomIsFullException | PlayerAlreadyLoggedException ex) {
                System.err.println("Can't Login using Socket Connection.");
                sendNack();
            }
        }

        if (type.equals("Event")) {
            sendEventToController((EventController) socketObject.getObject());
        }

    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to log the user to the server with his nickname.
     *
     * @param nickname name of the player.
     */
    public void login(String nickname) throws PlayerAlreadyLoggedException, PlayerNetworkException, RoomIsFullException {

    }

    /**
     * Method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    public void sendEventToController(EventController eventController) {
        this.server.sendEventToGameRoom(eventController);
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to write on the output stream the object that will be sent to the client.
     *
     * @param socketObject object that will be traduced on the client (it contain an event).
     */
    public void sendObject(SocketObject socketObject) {
        try {
            outputStream.writeObject(socketObject);
            outputStream.reset();
        } catch (SocketException e) {
            //System.err.println("Connection issue during client connection.\nError: " + e.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method used to to send an ACK (Acknowledge) packet from server to client in order to signal
     * the correct reception of a data packet.
     */
    public void sendAck() {
        SocketObject packet = new SocketObject();
        packet.setType("Ack");
        sendObject(packet);
    }

    /**
     * Method used to to send a NACK (Not Acknowledge) packet from server to client in order to signal
     * a missing reception of a data packet.
     */
    public void sendNack() {
        SocketObject packet = new SocketObject();
        packet.setType("Nack");
        sendObject(packet);
    }

    /**
     * Method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToView(EventView eventView) {
        SocketObject packet = new SocketObject();
        packet.setType("Event");
        packet.setObject(eventView);
        sendObject(packet);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Connection closer for socket player.
     * This method close the connection of the client and tell it to the remote player.
     */
    public void closeConnection() {
        try {
            tunnel.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reset player connection on reLogin.
     */
    public void resetConnection(Socket connection, ObjectOutputStream output, ObjectInputStream input) {
        this.tunnel = connection;
        this.outputStream = output;
        this.inputStream = input;
    }

    /**
     * Method used to remove a player from the server.
     * This method will also set the playerRunning boolean to false in order to remove correctly the user.
     */
    public void disconnect() {
        // chiudo connessione ma lascio socket player nella lista
        closeConnection();
        AnsiConsole.out.println(ansi().fg(GREEN).a(getNickname() + " has been removed!").reset());
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("-----------------------------------------").reset());
    }

    @Override
    public void kickPlayerOut() {

    }

    /*
    public static void removePlayer(RemotePlayer remotePlayer){
        remotePlayer.setPlayerRunning(false);
        String nickname = remotePlayer.getNickname();
        socketPlayers.remove(nickname, remotePlayer);
    }*/
}
