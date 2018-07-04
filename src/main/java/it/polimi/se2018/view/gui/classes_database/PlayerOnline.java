package it.polimi.se2018.view.gui.classes_database;

/**
 * Class that hold the player data
 *
 * @author Luca Genoni
 */
public class PlayerOnline {

    private int index;
    private String nickname;
    private boolean connected;

    /**
     * default constructor
     */
    public PlayerOnline() {
        this.index = 0;
        this.nickname = "";
        this.connected = false;
    }
    /**
     *
     */
    /**
     * Constructor
     *
     * @param index of the player in the game
     * @param nickname of the player in the game
     * @param connected true if the player is connected, false otherwise
     */
    public PlayerOnline(int index, String nickname, boolean connected) {
        this.index = index;
        this.nickname = nickname;
        this.connected = connected;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
