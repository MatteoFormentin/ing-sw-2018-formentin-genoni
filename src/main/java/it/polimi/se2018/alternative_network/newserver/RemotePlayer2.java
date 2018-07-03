package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerExeption;
import it.polimi.se2018.list_event.event_received_by_view.EventView;

/**
 * Generic class for send messages NOT RECEIVE
 */
public abstract class RemotePlayer2 {
    private String nickname;
    private boolean playerRunning;
    private int idPlayerInGame;


    public RemotePlayer2() {
        playerRunning = true;
    }


    /**
     * trim the name and set the connection as true when created
     *
     * @param nickname
     */
    public RemotePlayer2(String nickname) {
        setNickname(nickname);
        playerRunning = true;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        nickname = nickname.trim();
        this.nickname = nickname;
    }

    public boolean isPlayerRunning() {
        return playerRunning;
    }

    public void setPlayerRunning(boolean playerRunning) {
        this.playerRunning = playerRunning;
    }

    public int getIdPlayerInGame() {
        return idPlayerInGame;
    }

    public void setIdPlayerInGame(int idPlayerInGame) {
        this.idPlayerInGame = idPlayerInGame;
    }


    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    public abstract void sendEventToView(EventView eventView) throws ConnectionPlayerExeption;

    public abstract void sayHelloClient() throws ConnectionPlayerExeption;


    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - INTERNAL REQUEST
    //------------------------------------------------------------------------------------------------------------------

    public abstract void kickPlayerOut();


}
