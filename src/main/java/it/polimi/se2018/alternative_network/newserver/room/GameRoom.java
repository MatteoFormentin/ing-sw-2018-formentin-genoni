package it.polimi.se2018.alternative_network.newserver.room;

import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.alternative_network.newserver.Server2;
import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerException;
import it.polimi.se2018.exception.network_exception.server.GameStartedException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.UpdateDisconnection;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.UpdatePlayerConnection;
import it.polimi.se2018.model.UpdateRequestedByServer;
import it.polimi.se2018.utils.TimerCallback;
import it.polimi.se2018.utils.TimerThread;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRoom implements TimerCallback,GameInterface {

    private Server2 server;
    private LinkedList<RemotePlayer2> players;
    private UpdateRequestedByServer updater;
    private Controller controller;

    private int idGameBoard;
    private int roomTimeout;
    private int maxPlayer;

    private AtomicInteger currentConnected;
    private TimerThread timerThread;
    private boolean closed;

    public GameRoom(Server2 server, int idGameBoard) {
        this.server=server;
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
                UpdateDisconnection packet = new UpdateDisconnection(index, name);
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
        maxPlayer=2;
    }
    public RemotePlayer2 get(int index) {
        return players.get(index);
    }

    public boolean isClosed() {
        return closed;
    }

    public int size() {
        return players.size();
    }

    public void startGameRoom(Server2 server) {
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

    public RemotePlayer2 searchIfMatchName(String name) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNickname().equals(name)) return players.get(i);
        }
        return null;
    }


    public void addRemotePlayer(RemotePlayer2 remotePlayer) throws RoomIsFullException, GameStartedException {
        if (players.size() < maxPlayer) {
            System.err.println("viene aggiunto il player");
            players.add(remotePlayer);
            remotePlayer.setPlayerRunning(true);
            remotePlayer.setGameInterface(this);
            for(int i=0;i<players.size();i++) players.get(i).setIdPlayerInGame(i);
            updater.updatePlayerConnected(remotePlayer.getIdPlayerInGame(), remotePlayer.getNickname());
            currentConnected.incrementAndGet();
            checkOnLine();
            System.err.println("Gameroom -> addRemotePlayer: ci sono " + currentConnected + " connessi, " + players.size() + " registrati");
            if (currentConnected.get() == maxPlayer) {
                //TODO set something boolean of i don't know
                timerThread.shutdown();
                throw new GameStartedException();
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
     * @param idPlayer id del player da rimuovere
     */
    public void removeRemotePlayer(int idPlayer) {
        currentConnected.decrementAndGet();
        System.out.println(" ");
        if (controller != null) {
            System.out.println("light Remove. Disconnected during game");
            System.out.println("Gameroom -> removeRemotePlayer: ci sono " + currentConnected + " connessi e " + players.size() + " registrati");
            players.get(idPlayer).kickPlayerOut();
            players.get(idPlayer).setPlayerRunning(false);
            //TODO sostituire con un thead
            if (currentConnected.get() == 1) controller.endGame();
            else {
                updater.updateDisconnected(players.get(idPlayer).getIdPlayerInGame(), players.get(idPlayer).getNickname());
                controller.playerDown(idPlayer);
            }
        } else {
            //hard remove game not started
            System.out.println("Hard Remove");
            players.get(idPlayer).kickPlayerOut();
            players.get(idPlayer).setPlayerRunning(false);
            updater.updateDisconnected(players.get(idPlayer).getIdPlayerInGame(), players.get(idPlayer).getNickname());
            players.remove(idPlayer);
            for(int i=0;i<players.size();i++) players.get(i).setIdPlayerInGame(i);
            //TODO sostituire con thead
            if (currentConnected.get() == 1) timerThread.shutdown();
        }
        System.out.println(" ");
    }

    /**
     * check the player online in the game room
     */
    public void checkOnLine() {
        int i = 0;
        try {
            for (; i < players.size(); i++) {
                if (players.get(i).isPlayerRunning()) players.get(i).sayHelloClient();
            }
        } catch (ConnectionPlayerException ex) {
            removeRemotePlayer(i);
        }
    }

    public void reLogin(int idPlayer, RemotePlayer2 player) {
        currentConnected.incrementAndGet();
        try {
            players.get(idPlayer).sayHelloClient();
            System.out.println("il Client, era ancora collegato e precedentemente non è stato disconnesso");
        } catch (ConnectionPlayerException ex) {
            System.out.println("il Client è stato sostituito");
            players.set(idPlayer, player);
            //TODO send join game e avviso che il giocatore si è ricollegato
            updater.updateInfoReLogin(idPlayer);
        }
        checkOnLine();
    }

    @Override
    public void timerCallback() {
       // startGameRoom(server);
    }

    @Override
    public void timerCallbackWithIndex(int infoToReturn) {

    }


    public synchronized void endGame() {
        //TODO implementare invio di evento end game ai vari player
    }

    public void broadCast(EventView eventView) {
        for (int i = 0; i < players.size(); i++) {
            eventView.setPlayerId(i);
            sendEventToView(eventView);
        }
    }


    public void disconnectFromGameRoom(int indexRoom){
        removeRemotePlayer(indexRoom);
    }

    @Override
    public void sendEventToGameRoom(EventController eventController) {
        controller.sendEventToController(eventController);
    }

    @Override
    public void sendEventToView(EventView eventView) {
        try {
            if (players.get(eventView.getPlayerId()).isPlayerRunning())
                players.get(eventView.getPlayerId()).sendEventToView(eventView);
        } catch (ConnectionPlayerException ex) {
            removeRemotePlayer(eventView.getPlayerId());
        }
    }

}
