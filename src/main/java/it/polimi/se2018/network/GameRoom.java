package it.polimi.se2018.network;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Class that define a single GameRoom on the Server.
 * Every GameRoom has one Game (Controller) and multiple players.
 *
 * @author DavideMammarella
 */

public class GameRoom implements Runnable{

    // NUM MINIMO DI GIOCATORI PER PARTITA
    public static final int minPlayers = 2;
    // NUM MASSIMO DI GIOCATORI PER PARTITA
    public static final int maxPlayers = 4;
    //GIOCATORI NELLA STANZA
    private final ArrayList<RemotePlayer> players;
    // CONTATORE STANZA
    private static int roomCounter =0;

    // TIME
    private long roomWaitingTime;
    private long roomStartTimeout;

    //STATO STANZA
    private boolean roomJoinable;

    // LOAD FROM PROPERTIES
    Properties configProperties = new Properties();
    InputStream input = null;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Room constructor.
     * Room Timeout will be uploaded from config.propeties
     */
    public GameRoom(){
        roomCounter ++;
        players = new ArrayList<RemotePlayer>();
        roomJoinable=true;
        //roomStartTimeout upload
            try {
                // LOAD FROM PROPERTIES
                String config = "src/main/java/it/polimi/se2018/resources/configurations/gameroom_configuration.properties";
                input = GameRoom.class.getClassLoader().getResourceAsStream(config);

                if (input == null) {
                    System.out.println("Sorry," + config + " can't be found...");
                    return;
                }
                configProperties.load(input);
                //*1000 per convertire in millisecondi
                roomStartTimeout = Long.parseLong(configProperties.getProperty("roomStartTimeout"))*1000;
                System.out.println(configProperties.getProperty("roomStartTimeout"));
            } catch (IOException e){
                e.printStackTrace();
            }

            if (input!=null){
                try {
                    input.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
    }

    //------------------------------------------------------------------------------------------------------------------
    // GAMEROOM MANAGING
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void run(){
        System.out.println("Room started...");
        // FAR PARTIRE THREAD
    }

    //------------------------------------------------------------------------------------------------------------------
    // GETTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for room number.
     *
     * @return the number of the single room.
     */
    public int getRoomNumber(){
        return roomCounter;
    }

    /**
     * Getter for room timeout.
     *
     * @return a long that define the room timeout.
     */
    public long getRoomTimeout(){
        return this.roomStartTimeout;
    }

    /**
     * Getter for room state.
     *
     * @return true if the room is joinable, false otherwise.
     */
    public boolean getRoomState(){
        return this.roomJoinable;
    }

}
