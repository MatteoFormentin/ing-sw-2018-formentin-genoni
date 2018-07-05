package it.polimi.se2018.alternative_network.newserver.socket;

import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.network.SocketObject;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;

import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
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

    private Socket tunnel;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private LinkedList<SocketObject> eventViews;
    private SocketServer serverController;
    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Socket Player Constructor.
     *
     * @param serverController server interface, used as controller to communicate with the server.
     * @param connection       tunnel used to manage the socket connection.
     */
    public SocketPlayer(SocketServer serverController, Socket connection) {
        this.serverController = serverController;
        this.tunnel = connection;
        this.eventViews = new LinkedList<>();

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
        //TODO fixare il flusso di eventi che la virtual view, SocketPlayer
        try {
            System.out.println("Waiting for messages.");
            inputStream = new ObjectInputStream(tunnel.getInputStream());
            outputStream = new ObjectOutputStream(tunnel.getOutputStream());

            while (isPlayerRunning()) {
                try {
                    SocketObject received = (SocketObject) inputStream.readObject();
                    String type = received.getType();


                    System.out.println(type);
                    System.out.println(received.getStringField());
                    if (type == null) {
                        SocketObject packet = new SocketObject();
                        packet.setType("Disconnect");
                        sendObject(packet);
                        getGameInterface().disconnectFromGameRoom(this);
                        // } else {
                        if (type.equals("EventPreGame")) {
                            try {
                                setNickname(received.getStringField());
                                serverController.login(this);
                                System.out.println("Effettuato il login con successo");
                            } catch (RoomIsFullException | PlayerAlreadyLoggedException ex) {
                                SocketObject packet2 = new SocketObject();
                                packet.setType(ex.getMessage());
                                sendObject(packet);
                            }
                        } else if (type.equals("Disconnect")) {
                            SocketObject packet2 = new SocketObject();
                            packet.setType("Disconnect");
                            sendObject(packet);
                            //TODO legal disconnect
                        } else if (type.equals("Pong")) {
                            SocketObject packet2 = new SocketObject();
                            packet.setType("Ping");
                            sendObject(packet);
                            //TODO aveva richiesto il pingnon fare nulla
                        }
                    }
                    SocketObject packet = new SocketObject();
                    packet.setType("Server");
                    sendObject(packet);
                } catch (Exception ex) {
                    //TODO loop = false trovata un'eccezione
                    ex.printStackTrace();
                }
            }
            tunnel.close();
            //TODO send info of the disconnection ti the gameboard
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to traduce the object received from the client.
     *
     * @param socketObject object that will use the server to unleash the event associated.
     */


    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------


    /**
     * Method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    public void sendEventToController(EventController eventController) {
        this.getGameInterface().sendEventToGameRoom(eventController);
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
     * @param eventClient object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToView(EventClient eventClient) {
        SocketObject packet = new SocketObject();
        packet.setType("Event");
        packet.setObject(eventClient);
        eventViews.addLast(packet);
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
        setPlayerRunning(false);
        AnsiConsole.out.println();
        AnsiConsole.out.print(ansi().fg(YELLOW).a("SocketPlayer -> kickPlayerOut: " + getNickname() + "  ").reset());
        //TODO try to send a ping
        //if he is still connected then reopen the connection
    }

    /*
    public static void removePlayer(RemotePlayer remotePlayer){
        remotePlayer.setPlayerRunning(false);
        String nickname = remotePlayer.getNickname();
        socketPlayers.remove(nickname, remotePlayer);
    }*/
}
