package it.polimi.se2018.alternative_network.newserver.room;

import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerException;
import it.polimi.se2018.exception.network_exception.server.GameStartedException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.UpdateDisconnectionDuringSetup;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.UpdatePlayerConnection;
import it.polimi.se2018.model.UpdateRequestedByServer;
import it.polimi.se2018.utils.TimerCallback;
import it.polimi.se2018.utils.TimerThread;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRoom implements TimerCallback, GameInterface {

    private LinkedList<RemotePlayer2> players;
    private UpdateRequestedByServer updater;
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
        updater = new UpdateRequestedByServer() {
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
        };
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

    public void startGame() {
        if (controller == null) {
            timerThread.shutdown();
            String[] playersName = new String[players.size()];
            for (int i = 0; i < players.size(); i++) {
                System.out.println("Player " + i + " -> " + players.get(i).getNickname());
                playersName[i] = players.get(i).getNickname();
            }
            controller = new Controller(null, playersName, this);
            updater = controller.getUpdater();
            controller.startController();
        }
    }


    public void addRemotePlayer(RemotePlayer2 remotePlayer) throws RoomIsFullException, GameStartedException {
        if (players.size() < maxPlayer) {
            System.err.println("viene aggiunto il player");
            players.add(remotePlayer);
            remotePlayer.setPlayerRunning(true);
            remotePlayer.setGameInterface(this);
            for (int i = 0; i < players.size(); i++) players.get(i).setIdPlayerInGame(i);
            updater.updatePlayerConnected(remotePlayer.getIdPlayerInGame(), remotePlayer.getNickname());
            currentConnected.incrementAndGet();
            //  checkOnLine();
            System.err.println("Gameroom -> addRemotePlayer: ci sono " + currentConnected + " connessi, " + players.size() + " registrati");
            if (currentConnected.get() == maxPlayer) {
                //TODO set something boolean of i don't know
                timerThread.shutdown();
                startGame();
            }
            if (currentConnected.get() == 2) timerThread.startThread();
        } else {
            System.out.println("Gameroom -> addRemotePlayer: ci sono " + currentConnected + " connessi e ");
            throw new RoomIsFullException("The current room is starting retry login.");
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
            remotePlayerDown.setPlayerRunning(false);
            //TODO sostituire con un thead
            if (currentConnected.get() == 1) controller.endGame();
            else {
                updater.updateDisconnected(remotePlayerDown.getIdPlayerInGame(), remotePlayerDown.getNickname());
                controller.playerDown(remotePlayerDown.getIdPlayerInGame());
            }
        } else {
            //hard remove game not started
            System.out.println("Hard Remove");
            remotePlayerDown.kickPlayerOut();
            remotePlayerDown.setPlayerRunning(false);
            updater.updateDisconnected(remotePlayerDown.getIdPlayerInGame(), remotePlayerDown.getNickname());
            players.remove(remotePlayerDown);
            for (int i = 0; i < players.size(); i++) players.get(i).setIdPlayerInGame(i);
            //TODO sostituire con thead
            if (currentConnected.get() == 1) timerThread.shutdown();
        }
        System.out.println(" ");
    }


    public void reLogin(RemotePlayer2 oldRemotePlayer, RemotePlayer2 newRemotePlayer) {
        currentConnected.incrementAndGet();
        System.out.println("il Client è stato sostituito");
        int i = oldRemotePlayer.getIdPlayerInGame();
        newRemotePlayer.setIdPlayerInGame(i);
        newRemotePlayer.setGameInterface(this);
        players.set(i, newRemotePlayer);
        updater.updateInfoReLogin(i);

    }

    @Override
    public void timerCallback() {
        // startGameRoom(server);
    }

    @Override
    public void timerCallbackWithIndex(int infoToReturn) {

    }


    public void endGame() {
        closed.set(true);
        //TODO per stoppare il thread della partita
    }


    //***********************************************************************************************************************/
    //***********************************************************************************************************************/
    //
    //***********************************************************************************************************************/
    //***********************************************************************************************************************/


    @Override
    public void sendEventToGameRoom(EventController eventController) {
        controller.sendEventToController(eventController);
    }

    @Override
    public void sendEventToView(EventView eventView) {
        System.out.println("!!!GAMEROOM sendEventToView");
        eventView.setIdGame(idGameBoard);
        try {
            //  if (players.get(eventView.getPlayerId()).isPlayerRunning())
                players.get(eventView.getPlayerId()).sendEventToView(eventView);
        } catch (ConnectionPlayerException ex) {
            removeRemotePlayer(players.get(eventView.getPlayerId()));
        }
    }

    @Override
    public void disconnectFromGameRoom(RemotePlayer2 oldRemotePlayer) {
        removeRemotePlayer(oldRemotePlayer);
    }
}
