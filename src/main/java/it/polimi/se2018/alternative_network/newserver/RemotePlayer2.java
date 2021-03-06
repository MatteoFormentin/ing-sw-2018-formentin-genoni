package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.alternative_network.newserver.room.GameInterface;
import it.polimi.se2018.alternative_network.newserver.room.GameRoom;
import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerException;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;

import java.util.LinkedList;

/**
 * Generic abstract class for send and receive messages
 *
 * @author DavideMammarella
 */
public abstract class RemotePlayer2 {

    private String nickname;
    private GameInterface gameInterface;
    private boolean notifyed;
    private LinkedList<GameRoom> gamesPlayed;

    /**
     * method for get the name of the remote player
     *
     * @return the name of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * method for set the name of the player, typically is received when he ask to make the login
     *
     * @param nickname the nickname of the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * when the remote player is associated with a game return the reference to the interface
     * that allows to send messages directly to the game room and to notify a disconnection.
     *
     * @return the GameInterface associated to this player
     */
    public GameInterface getGameInterface() {
        return gameInterface;
    }

    /**
     * method to associate the remote player with the game room
     *
     * @param gameInterface associated to this player
     */
    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }

    public boolean isRunning() {
        try {
            checkOnline();
            return true;
        }catch(ConnectionPlayerException ex){
            return false;
        }
    }
    /**
     * method it's the same as is player running so
     * rmi can try to ping
     * socket response true or false depending if the thread is open
     */
    public abstract boolean checkOnline() throws ConnectionPlayerException;

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * this method is used when the game room needs to send some {@code EventClient}
     * to the client related to this remote player. this methos handle the connection problemn
     *
     * @param eventClient event that the client needs
     */
    public abstract void sendEventToView(EventClient eventClient);


    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - INTERNAL REQUEST
    //------------------------------------------------------------------------------------------------------------------

    /**
     * method that need to implement the remove from the client gatherer
     * or any type of class that the player use to send the View.
     * doing so the game Room can kick out from the server the client.
     */
    public abstract void kickPlayerOut();

    public boolean isDownIsNotifyed() {
        return notifyed;
    }

    public void setNotifyed(boolean notifyed) {
        this.notifyed = notifyed;
    }

    //**************************************************************************************************************
    //                                             PERSISTENZA
    //**************************************************************************************************************
    public void moveGameStored(GameRoom gameRoom){
        gamesPlayed.add(gameRoom);
    }



}
