package it.polimi.se2018.alternative_network.newserver.socket;

import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.alternative_network.newserver.room.GameInterface;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.network.SocketObject;
import org.fusesource.jansi.AnsiConsole;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

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
//TODO implementare Remote PLayer2
public class SocketPlayer implements Runnable,RemotePlayer2{

    // LISTA DEI GIOCATORI CHE HANNO EFFETTUATO IL LOGIN ED HANNO UN NICKNAME
    static HashMap<String, SocketPlayer> socketPlayers;
    // comunicazione con il server

    private SocketServer serverController;

    private Socket tunnel;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    //info remote player
    private String nickname;
    private int idPlayerInGame;
    private GameInterface gameInterface;



    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname=nickname;
    }

    @Override
    public int getIdPlayerInGame() {
        return idPlayerInGame;
    }

    @Override
    public void setIdPlayerInGame(int idPlayerInGame) {
        this.idPlayerInGame=idPlayerInGame;
    }

    @Override
    public GameInterface getGameInterface() {
        return gameInterface;
    }

    @Override
    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface=gameInterface;
    }
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
        //TODO inizzializzare una variabile atomic boolean per segnare se il thread è attivo oppure no
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
        Thread.currentThread().setName("Socket Player Thread");
        boolean flag = true;
        while (flag && tunnel.isConnected()) {
            try {
                SocketObject received = (SocketObject) inputStream.readObject();
                //socketObjectTraducer(received);
            } catch (EOFException e) {
                // se si disconnette metto il running a false e tengo in memoria il player
                if(gameInterface==null) ;//TODO non ho ancora fatto il login
                    else gameInterface.disconnectFromGameRoom(this);
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
     * Method used to send to the client an update of the game.
     *
     * @param eventClient object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToView(EventClient eventClient) {
        SocketObject packet = new SocketObject();
        packet.setType("Event");
        packet.setObject(eventClient);
        //TODO mandare il pacchetto
       // eventViews.addLast(packet);
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
        //TODO variabile Atomic solo per socket per segnare se il thread è attivo o no

       // setta la variabile come false
        AnsiConsole.out.println();
        AnsiConsole.out.print(ansi().fg(YELLOW).a("SocketPlayer -> kickPlayerOut: " + getNickname() + "  ").reset());
        //TODO try to send a ping
        //if he is still connected then reopen the connection
    }

    @Override
    public boolean checkOnline() {
        //TODO variabile Atomic solo per socket per segnare se il thread è attivo o no
        // setta la variabile come false
        return false;
    }

    /*
    public static void removePlayer(RemotePlayer remotePlayer){
        remotePlayer.setPlayerRunning(false);
        String nickname = remotePlayer.getNickname();
        socketPlayers.remove(nickname, remotePlayer);
    }*/
}
