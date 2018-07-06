package it.polimi.se2018.alternative_network.newserver.room;

import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.server.GameStartedException;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.utils.TimerCallback;
import it.polimi.se2018.utils.TimerThread;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luca Genoni
 */
public class GameRoom extends Thread implements TimerCallback, GameInterface {

    private LinkedList<RemotePlayer2> players;
    private RemotePlayer2[] playersInGame;
 //   private UpdateRequestedByServer updateStateConnection;
    private Controller controller;

    private int idGameBoard;
    private int roomTimeout;
    private int maxPlayer;

    private AtomicInteger currentConnected;
    private TimerThread timerThread;
    private AtomicBoolean closed;
    private LinkedList<EventController> controls;

    public GameRoom(int idGameBoard) {
        this.idGameBoard = idGameBoard;
        loadConfigGame();
        timerThread = new TimerThread(this, roomTimeout);
        players = new LinkedList<>();
        currentConnected = new AtomicInteger(0);
      /*  updateStateConnection = new UpdateRequestedByServer() {
            @Override
            public void updatePlayerConnected(int index, String name) {
                for (int i = 0; i < players.size(); i++) updatePlayerConnected(i, index, name);
            }

            private void updatePlayerConnected(int indexToNotify, int index, String name) {
                UpdatePlayerConnection packet = new UpdatePlayerConnection(index, name);
                packet.setPlayerId(indexToNotify);
                sendEventToView(packet);
            }

            @Override
            public void updateDisconnected(int index, String name) {
                for (int i = 0; i < players.size(); i++) updateDisconnected(i, index, name);
            }

            private void updateDisconnected(int indexToNotify, int index, String name) {
                UpdateDisconnectionDuringSetup packet = new UpdateDisconnectionDuringSetup(index, name);
                packet.setPlayerId(indexToNotify);
                sendEventToView(packet);
            }

            @Override
            public void updateInfoReLogin(int indexPlayer) {

            }

            @Override
            public void updateInfoStart() {

            }
        };*/
    }


    public void loadConfigGame() {
        try {
            Properties configProperties = new Properties();

            String timeConfig = "src/resources/configurations/gameroom_configuration.properties";
            FileInputStream inputConnection = new FileInputStream(timeConfig);

            configProperties.load(inputConnection);
            // SERVER ADDRESS LOAD
            maxPlayer = Integer.parseInt(configProperties.getProperty("maxPlayer"));
            roomTimeout = Integer.parseInt(configProperties.getProperty("roomTimeout"));
        } catch (Exception e) {
            maxPlayer = 4;
            roomTimeout = 10000;
            System.out.println("Errore caricamento delle risorse");
        }
        //TODO rimuovere ma tenere per i test
        maxPlayer = 2;
    }

    public RemotePlayer2 get(int index) {
        return players.get(index);
    }


    public int size() {
        return players.size();
    }

    /**
     * metodo per startare la partita
     * è compatibile con il replay di una partita già fatta, ma non è stato implementato
     */
    public synchronized void startGame()throws GameStartedException{
        if (controller == null) {
            timerThread.shutdown();
            //TODO metterli nell'array
            playersInGame = new RemotePlayer2[players.size()];
            String[] playersName = new String[players.size()];
            for(int i=0;i<playersInGame.length;i++){
                playersInGame[i]= players.get(i);
                playersName[i] = playersInGame[i].getNickname();
            }
            controller = new Controller(playersName, this);
            controller.startController();
        }else{
            //TODO qui implementare il replay della partita
            System.out.println("errore è gia stata inizziata questa partita");
            throw new GameStartedException();
        }
    }

    public synchronized void resetOrStoreGameRoom(){
        //TODO refrech
    }

    /**
     * aggiongere un giocatore alla partita
     *
     * @param remotePlayer
     * @throws RoomIsFullException
     */
    public void addRemotePlayer(RemotePlayer2 remotePlayer) throws GameStartedException{
        if(controller==null){
            if (players.size() < maxPlayer) {
                System.err.println("viene aggiunto il player");
                players.add(remotePlayer);
                // viene settata la gameboard
                remotePlayer.setGameInterface(this);
                for (int i = 0; i < players.size(); i++) players.get(i).setIdPlayerInGame(i);
            //    updateStateConnection.updatePlayerConnected(remotePlayer.getIdPlayerInGame(), remotePlayer.getNickname());
                currentConnected.incrementAndGet();
                //  checkOnLine();
                System.err.println("Gameroom -> addRemotePlayer: ci sono " + currentConnected + " connessi, " + players.size() + " registrati");
                if (currentConnected.get() == maxPlayer) {
                    //TODO set something boolean of i don't know
                    startGame();
                }
                if (currentConnected.get() == 2) timerThread.startThread();
            } else {
                System.out.println("Gameroom -> addRemotePlayer: ci sono la partita è già iniziata. ");
                throw new GameStartedException();
            }
        }else{
            System.out.println("Gameroom -> addRemotePlayer: ci sono la partita è già iniziata. ");
            throw new GameStartedException();
        }
    }

    /**
     * fimuovere la connessione del giocatore
     *
     * @param remotePlayerDown id del player da rimuovere
     */
    public void removeRemotePlayer(RemotePlayer2 remotePlayerDown) {
        currentConnected.decrementAndGet();
        System.out.println(" ");
        if (controller != null) {
            System.out.println("light Remove. Disconnected during game");
            System.out.println("Gameroom -> removeRemotePlayer: ci sono " + currentConnected + " connessi e " + players.size() + " registrati");
            remotePlayerDown.kickPlayerOut();
            //TODO implementare un corretto end game
            for (int i = 0; i < players.size(); i++) players.get(i).setIdPlayerInGame(i);
            if (currentConnected.get() == 1) controller.endGame();
            else {
                //updateStateConnection.updateDisconnected(remotePlayerDown.getIdPlayerInGame(), remotePlayerDown.getNickname());
                controller.playerDown(remotePlayerDown.getIdPlayerInGame());
            }
        } else {
            //hard remove game not started
            System.out.println("Hard Remove");
            remotePlayerDown.kickPlayerOut();
         //   updateStateConnection.updateDisconnected(remotePlayerDown.getIdPlayerInGame(), remotePlayerDown.getNickname());
            players.remove(remotePlayerDown);
           // for (int i = 0; i < players.size(); i++) players.get(i).setIdPlayerInGame(i);
            //TODO sostituire con thead
            if (currentConnected.get() == 1) timerThread.shutdown();
        }
    }


    /**
     * riaggiusta il collegamento con la
     * @param oldRemotePlayer
     * @param newRemotePlayer
     */
    public void reLogin(RemotePlayer2 oldRemotePlayer, RemotePlayer2 newRemotePlayer) {
        currentConnected.incrementAndGet();
        System.out.println("il Client è stato sostituito");
        int i = oldRemotePlayer.getIdPlayerInGame();
        newRemotePlayer.setIdPlayerInGame(i);
        newRemotePlayer.setGameInterface(this);
        playersInGame[i]= newRemotePlayer;
        controller.playerUp(i);

    }

    @Override
    public void timerCallback() {
        // startGameRoom(server);
    }

    @Override
    public void timerCallbackWithIndex(int infoToReturn) {

    }

    //***********************************************************************************************************************/
    //***********************************************************************************************************************/
    //
    //***********************************************************************************************************************/
    //***********************************************************************************************************************/


    @Override
    public void sendEventToGameRoom(EventController eventController) {
        //TODO rilevare che la partita è finita per aggiornare la gameRoom usare il try and catch
        if(controller==null){
            System.out.println("non è ancora iniziata la partita");
        }else{
            System.out.println("è in corso la partita");
            controller.sendEventToController(eventController);
        }
      //  try{

    /*    }catch(GameIsOverException ex){

        }*/

    }

    /**
     * metodo per aggiornare la view sia nel pregame che nel gioco in corso
     * @param eventClient
     */
    @Override
    public void sendEventToView(EventClient eventClient) {
        System.out.println("!!!GAMEROOM sendEventToView");
        if(controller ==null){
            System.out.println("Non è ancora startata la partita mando il messaggio alla linked list");
            eventClient.setIdGame(idGameBoard);
            if (players.get(eventClient.getPlayerId()).checkOnline())
                players.get(eventClient.getPlayerId()).sendEventToView(eventClient);
            else removeRemotePlayer(players.get(eventClient.getPlayerId()));
        }else{
            System.out.println("Durante la partita mando il messaggio all'array fisso");
            eventClient.setIdGame(idGameBoard);
            if (playersInGame[eventClient.getPlayerId()].checkOnline())  playersInGame[eventClient.getPlayerId()].sendEventToView(eventClient);
        }
    }

    @Override
    public void disconnectFromGameRoom(RemotePlayer2 oldRemotePlayer) {
        removeRemotePlayer(oldRemotePlayer);
    }

}
