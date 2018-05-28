package it.polimi.se2018.network;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.event.list_event.EventView;

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

    // GAME DELLA ROOM
    private Controller game;

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
    //MUTEX usato per gestire una stanza alla volta (senza questo potrebbe crearsi congestione durante il login)
    private static final Object ROOMS_MUTEX = new Object();

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
        roomJoinable=true;
        roomCounter ++;
        players = new ArrayList<RemotePlayer>();
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

    /**
     * Room initializer.
     * Thread that start the game after the timeout expires.
     */
    @Override
    public void run(){
        System.out.println("Room started...");
        // FAR PARTIRE THREAD
        while(roomWaitingTime<=roomStartTimeout) {
            try {
                if (this.roomStartTimeout > 0)
                    Thread.sleep(this.roomStartTimeout);
                if (!Thread.currentThread().isInterrupted())
                    //ROOM STARTER
                    GameRoom.this.startRoom();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    /**
     * Game initializer.
     * Start the game thread closing the access to the room.
     */
    public void startRoom(){
        System.out.println("Game started...");
        // CREAZIONE SESSIONE DI GIOCO
        System.out.println("Creating game session...");
        // PASSAGGIO PARAMETRI AL CONTROLLER
        game = new Controller(this, players);
        System.out.println("Closing Room...");
        roomJoinable=false;
        Thread threadGame=new Thread(game);
        threadGame.start();
    }

    //------------------------------------------------------------------------------------------------------------------
    // PLAYER MANAGING
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Add one player to the current room.
     *
     * @param player that will be added to the room.
     */
    public void addPlayer(RemotePlayer player){
        synchronized (ROOMS_MUTEX){
            if(roomJoinable){
                this.players.add(player);
                System.out.println("Player added...");
                if (players.size()==minPlayers){
                    // FAI PARTIRE IL TEMPO DI ATTESA
                    roomWaitingTime=System.currentTimeMillis();
                    System.out.println("Timeout started...");
                    new Thread(this).start();
                } else if (players.size()==maxPlayers){
                    this.startRoom();
                }
            } else {
                System.out.println("Sorry but the room is not joinable...");
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // UNLEASH EVENT
    //------------------------------------------------------------------------------------------------------------------

    public void unleashEvent(RemotePlayer player, EventView eventView){
        game.unleashEvent(player, eventView);
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

    /**
     * Getter for Player Array List.
     *
     * @return list of player in the room.
     */
    public ArrayList<RemotePlayer> getPlayersArrayList() {
        return this.players;
    }
}
