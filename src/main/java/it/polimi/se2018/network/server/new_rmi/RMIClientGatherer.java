package it.polimi.se2018.network.server.new_rmi;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventViewFromController;
import it.polimi.se2018.network.client.new_rmi.RMIClientInterface;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;

public class RMIClientGatherer extends UnicastRemoteObject implements RMIServerInterfaceSeenByClient {

    private LinkedList<RMIClientInterface> clients = new LinkedList<>();
    private Server mainServer;
    protected RMIClientGatherer(Server mainServer) throws RemoteException {
        super(0);
        this.mainServer=mainServer;
    }


    @Override
    public void addClient(RMIClientInterface client) throws RemoteException {
        //TODO a che serve? .... bah non lo so proprio
        client.sayHelloClient();
        System.out.println("Client " + (clients.indexOf(client) + 1) + " connesso al gatherer!");
    }

    @Override
    public void login(String nickname, RMIClientInterface client) throws RemoteException,PlayerAlreadyLoggedException{
        //controlla unicità del nome dei giocatori on
        RMIPlayer player = new RMIPlayer(nickname,client);
        player.setNickname(nickname);
        if(mainServer.searchPlayerLogged(player)==null){
            mainServer.login(player);
        }
    }


    @Override
    public void send(String eventForServer) throws RemoteException {
        System.out.println("Ricevuto " + eventForServer);
    }

    @Override
    public void sendEventToController(EventController event) throws RemoteException {

    }

    @Override
    public void disconnect(String nickname, RMIClientInterface client) throws RemoteException {

    }

    @Override
    public String sayHelloServer() throws RemoteException {
        return "Ok, connesso";
    }

    void broadCast(EventView packet){
        //TODO sistemare il broadcas nel server
        Iterator<RMIClientInterface> clientIterator = clients.iterator();
        while (clientIterator.hasNext()) {
            try {
                clientIterator.next().notifyTheClient(packet);
            } catch (ConnectException e) {
                clientIterator.remove();
                System.out.println("Client rimosso!");
                broadCast(new EventViewFromController());
            } catch (RemoteException e){
                clientIterator.remove();
                System.out.println("Client rimosso!");
                broadCast(new EventViewFromController());
            }
        }
    }

}