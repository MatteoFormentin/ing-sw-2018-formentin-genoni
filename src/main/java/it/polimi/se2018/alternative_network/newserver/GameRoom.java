package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerExeption;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.server.SinglePlayerException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;

import java.util.LinkedList;

public class GameRoom {

    private LinkedList<RemotePlayer2> players;
    private int maxPlayer;
    private int currentConnected;
    private int timeRoom;

    private Controller controller;

    public GameRoom(int maxPlayer, int timeRoom) {
        this.maxPlayer = maxPlayer;
        players = new LinkedList<>();
        currentConnected = 0;
    }

    public RemotePlayer2 get(int index) {
        return players.get(index);
    }

    public int size() {
        return players.size();
    }

    public int sizeOnline() {
        return currentConnected;
    }

    private void decreaseConnected() throws SinglePlayerException {

    }

    public void startGameRoom(Server2 server) {

        controller = new Controller(null, players.size(), server);
    }

    public RemotePlayer2 searchIfMatchName(String name) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNickname().equals(name)) return players.get(i);
        }
        return null;
    }


    public synchronized void addRemotePlayer(RemotePlayer2 remotePlayer) throws RoomIsFullException,GameException {
        if (players.size() < maxPlayer) {
            if (players.add(remotePlayer)) {
                currentConnected++;
                remotePlayer.setPlayerRunning(true);
            }
            checkOnLine();
            System.out.println("Gameroom -> addRemotePlayer: ci sono "+currentConnected+" connessi e "
                    +players.size() == maxPlayer+" registrati");
            if (players.size() == maxPlayer) throw new GameException("la stanza è pronta");
        } else throw new RoomIsFullException("room is full");
    }

    public void removeRemotePlayer(int idPlayer) {
        currentConnected--;
        if (controller != null) {
            //light remove game started
            System.out.println("light Remove");
            players.get(idPlayer).kickPlayerOut(true);
            players.get(idPlayer).setPlayerRunning(false);
        } else {
            //hard remove game not started
            System.out.println("Hard Remove");
            players.get(idPlayer).kickPlayerOut(true);
            players.get(idPlayer).setPlayerRunning(false);
            players.remove(idPlayer);
        }
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
            checkOnLine();
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
        }
        checkOnLine();
    }


    public void sendEventToController(EventController event) {
        controller.sendEventToController(event);
    }

    public void sendEventToClient(EventView eventView) {
        try {
            players.get(eventView.getPlayerId()).sendEventToView(eventView);
        } catch (ConnectionPlayerExeption ex) {
            removeRemotePlayer(eventView.getPlayerId());
        }
    }
}
