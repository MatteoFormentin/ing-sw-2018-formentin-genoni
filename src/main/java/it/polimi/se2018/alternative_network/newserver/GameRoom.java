package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerExeption;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.server.GameStartedException;
import it.polimi.se2018.exception.network_exception.server.SinglePlayerException;
import it.polimi.se2018.list_event.event_received_by_controller.ControllerEndTurn;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.utils.TimerCallback;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameRoom implements TimerCallback,ServerController2 {

    private LinkedList<RemotePlayer2> players;
    private int maxPlayer;
    private int currentConnected;
    private int timeRoom;
    private int indexRoom;

    private Controller controller;
    private AtomicBoolean blocked;


    public GameRoom(int maxPlayer, int timeRoom, int indexRoom) {
        this.maxPlayer = maxPlayer;
        this.timeRoom = timeRoom;
        this.indexRoom = indexRoom;
        players = new LinkedList<>();
        blocked= new AtomicBoolean(false);
        currentConnected = 0;
    }

    public RemotePlayer2 get(int index) {
        return players.get(index);
    }

    public int size() {
        return players.size();
    }


    public void startGameRoom(Server2 server) {
        controller = new Controller(null, players.size(), this);
    }

    public RemotePlayer2 searchIfMatchName(String name) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNickname().equals(name)) return players.get(i);
        }
        return null;
    }


    public void addRemotePlayer(RemotePlayer2 remotePlayer) throws RoomIsFullException,GameStartedException {
        if (players.size() < maxPlayer) {
            System.err.println("viene aggiunto il player");
            if (players.add(remotePlayer)) {
                currentConnected++;
                remotePlayer.setPlayerRunning(true);
            }
            checkOnLine();
            System.err.println("Gameroom -> addRemotePlayer: ci sono "+currentConnected+" connessi e "
                    +players.size() == maxPlayer+" registrati");
            if (players.size() == maxPlayer){
                //TODO start the game room
                throw new GameStartedException();
            }
        } else{
            System.out.println("Gameroom -> addRemotePlayer: ci sono "+currentConnected+" connessi e ");
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
            //light remove game started
            System.out.println("light Remove. Disconnected during game");
            players.get(idPlayer).kickPlayerOut(true);
            players.get(idPlayer).setPlayerRunning(false);
            //TODO notificare tutti i giocatori dalla disconnessione
            if(currentConnected==1){
                //TODO ENDGAME per disconnessione
                //TODO return to server and reset
            }
        } else {
            //hard remove game not started
            System.out.println("Hard Remove");
            players.get(idPlayer).kickPlayerOut(true);
            players.get(idPlayer).setPlayerRunning(false);
            players.remove(idPlayer);
            //TODO notificare tutti i giocatori nella room che si sta costruendo
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
        }
        checkOnLine();
    }

    @Override
    public void timerCallback() {
        //TODO mettere a posto lo start del game   startGameRoom();
    }

    private synchronized void startGameRoom(){
        controller=new Controller(null,players.size(),this);
    }

    public synchronized void endGame(){
        //TODO implementare invio di evento end game ai vari player
    }


    @Override
    public void sendEventToGameRoom(EventController eventController) {
        controller.sendEventToController(eventController);
    }

    @Override
    public void sendEventToView(EventView eventView) {
        try {
            if(players.get(eventView.getPlayerId()).isPlayerRunning())
                players.get(eventView.getPlayerId()).sendEventToView(eventView);
        } catch (ConnectionPlayerExeption ex) {
            removeRemotePlayer(eventView.getPlayerId());
            EventController packet = new ControllerEndTurn();
            packet.setPlayerId(eventView.getPlayerId());
            controller.sendEventToController(packet);
        }
    }
}
