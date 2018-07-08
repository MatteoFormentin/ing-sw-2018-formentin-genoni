package it.polimi.se2018.alternative_network.newserver.socket;

import it.polimi.se2018.alternative_network.newserver.PrincipalServer;
import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerException;
import it.polimi.se2018.list_event.event_received_by_server.EventServer;
import it.polimi.se2018.list_event.event_received_by_server.ServerVisitor;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.EventPreGame;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.EventPreGameVisitor;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.event_pre_game.LoginRequest;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import org.fusesource.jansi.AnsiConsole;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Class that extends the RemotePlayer in order to define a Socket Player.
 * This class will be used to manage every socket player as a thread.
 *
 * @author DavideMammarella
 */
//TODO implementare Remote PLayer2
public class SocketPlayer extends RemotePlayer2 implements ServerVisitor, EventPreGameVisitor, Runnable {

    // LISTA DEI GIOCATORI CHE HANNO EFFETTUATO IL LOGIN ED HANNO UN NICKNAME

    private Socket tunnel;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private int init;

    private AtomicBoolean online;
    private PrincipalServer server;

    /**
     * Socket Player Constructor.
     *
     * @param connection tunnel used to manage the socket connection.
     */
    public SocketPlayer(Socket connection, PrincipalServer server) {
        this.server = server;
        this.tunnel = connection;
        online = new AtomicBoolean(true);
        try {
            this.outputStream = new ObjectOutputStream(tunnel.getOutputStream());
            this.inputStream = new ObjectInputStream(tunnel.getInputStream());
            this.outputStream.flush();
        } catch (IOException ex) {
            online.set(false);
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
        init=0;
        online.set(true);
        while (online.get() && tunnel.isConnected()) {
            try {
                EventServer received = (EventServer) inputStream.readObject();
                received.acceptGeneric(this);
            } catch (EOFException e) {
                online.set(false);
                // se si disconnette metto il running a false e tengo in memoria il player
                e.printStackTrace();
                closeConnection();
            } catch (IOException | ClassNotFoundException ex) {
                online.set(false);
            }
        }
        closeConnection();
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void visit(EventPreGame event) {
        event.acceptPreGame(this);
    }

    @Override
    public void visit(LoginRequest event) {
        this.setNickname(event.getNickname());
        server.login(this);
    }

    @Override
    public void visit(EventController event) {
        getGameInterface().sendEventToGameRoom(event);
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to write on the output stream the object that will be sent to the client.
     *
     * @param socketObject object that will be traduced on the client (it contain an event).
     */
    public void sendObject(EventClient socketObject) {
        try {
            outputStream.writeObject(socketObject);
            outputStream.reset();
        } catch (IOException ex) {
            System.out.println("sendObject");
            kickPlayerOut();
        }
    }


    /**
     * Method used to send to the client an update of the game.
     *
     * @param eventClient object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToView(EventClient eventClient) {
        sendObject(eventClient);
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
            AnsiConsole.out.println(ansi().fg(GREEN).a(getNickname() + " has been removed!").reset());
        }
        online.set(false);
    }

    /**
     * Method used to remove a player from the server.
     * This method will also set the playerRunning boolean to false in order to remove correctly the user.
     */
    @Override
    public void kickPlayerOut() {
        closeConnection();
        AnsiConsole.out.println();
        AnsiConsole.out.print(ansi().fg(YELLOW).a("SocketPlayer -> kickPlayerOut: " + getNickname() + "  ").reset());
    }

    @Override
    public boolean checkOnline()throws ConnectionPlayerException {
        if(online.get()) return true;
        else if(init<5){
            init++;
            return true;
        }else throw new ConnectionPlayerException();
    }


}
