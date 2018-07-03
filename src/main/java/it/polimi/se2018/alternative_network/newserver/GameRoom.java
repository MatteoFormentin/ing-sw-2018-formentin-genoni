package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerExeption;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.server.GameStartedException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.UpdateDisconnection;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.UpdatePlayerConnection;
import it.polimi.se2018.model.UpdateRequestedByServer;
import it.polimi.se2018.utils.TimerCallback;
import it.polimi.se2018.utils.TimerThread;

import java.util.LinkedList;

public class GameRoom implements TimerCallback, ServerController2 {

    private LinkedList<RemotePlayer2> players;
    private int maxPlayer;
    private int currentConnected;
    private int timeRoom;
    private int indexRoom;

    private Controller controller;
    private UpdateRequestedByServer updater;
    private TimerThread timerThread;


    public GameRoom(int maxPlayer, int timeRoom, int indexRoom) {
        this.maxPlayer = maxPlayer;
        this.timeRoom = timeRoom;
        this.indexRoom = indexRoom;
        updater =new UpdateRequestedByServer() {
            @Override
            public void updatePlayerConnected(int index, String name) {
                for(int i=0;i<players.size();i++) updatePlayerConnected(i,index,name);
            }
            private void updatePlayerConnected(int indexToNotify, int index,String name){
                UpdatePlayerConnection packet = new UpdatePlayerConnection(index,name);
                packet.setPlayerId(indexToNotify);
                sendEventToView(packet);
            }
            @Override
            public void updateDisconnected(int index, String name) {
                for(int i=0;i<players.size();i++) updateDisconnected(i,index,name);
            }

            private void updateDisconnected(int indexToNotify, int index,String name){
                UpdateDisconnection packet = new UpdateDisconnection(index,name);
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
        timerThread = new TimerThread(this, 10 * 100);
        players = new LinkedList<>();
        currentConnected = 0;
    }

    public RemotePlayer2 get(int index) {
        return players.get(index);
    }

    public int size() {
        return players.size();
    }


    public void startGameRoom(Server2 server) {
        if (controller == null) {
            String[] playersName = new String[players.size()];
            for (int i = 0; i < players.size(); i++) playersName[i] = players.get(i).getNickname();
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
            if (players.add(remotePlayer)) {
                currentConnected++;
                remotePlayer.setPlayerRunning(true);
                remotePlayer.setIdPlayerInGame(players.size() - 1);
                updater.updatePlayerConnected(remotePlayer.getIdPlayerInGame(), remotePlayer.getNickname());
            }
            if (currentConnected == 2) timerThread.startThread();
            checkOnLine();
            System.err.println("Gameroom -> addRemotePlayer: ci sono " + currentConnected + " connessi, " + players.size() + " registrati");
            if (currentConnected == maxPlayer) {
                //TODO set something
                //shutdown the thread
                throw new GameStartedException();
            }
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
        currentConnected--;
        System.out.println(" ");
        if (controller != null) {
            System.out.println("light Remove. Disconnected during game");
            System.out.println("Gameroom -> removeRemotePlayer: ci sono " + currentConnected + " connessi e " + players.size() + " registrati");
            players.get(idPlayer).kickPlayerOut();
            players.get(idPlayer).setPlayerRunning(false);
            if (currentConnected == 1) controller.endGame();
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
        } catch (ConnectionPlayerExeption ex) {
            removeRemotePlayer(i);
        }
    }

    public void reLogin(int idPlayer, RemotePlayer2 player) {
        currentConnected++;
        try {
            players.get(idPlayer).sayHelloClient();
            System.out.println("il Client, era ancora collegato e precedentemente non è stato disconnesso");
        } catch (ConnectionPlayerExeption ex) {
            System.out.println("il Client è stato sostituito");
            players.set(idPlayer, player);
            //TODO send join game e avviso che il giocatore si è ricollegato
            updater.updateInfoReLogin(idPlayer);
        }
        checkOnLine();
    }

    @Override
    public void timerCallback() {
        //TODO implementare
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

    @Override
    public void sendEventToGameRoom(EventController eventController) {
        controller.sendEventToController(eventController);
    }

    @Override
    public void sendEventToView(EventView eventView) {
        try {
            if (players.get(eventView.getPlayerId()).isPlayerRunning())
                players.get(eventView.getPlayerId()).sendEventToView(eventView);
        } catch (ConnectionPlayerExeption ex) {
            removeRemotePlayer(eventView.getPlayerId());
        }
    }

}
