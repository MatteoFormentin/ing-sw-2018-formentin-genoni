package it.polimi.se2018.network.server.new_rmi;


import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.client.new_rmi.RMIClientInterface;

import java.rmi.RemoteException;

public class RMIPlayer extends RemotePlayer {
    private RMIClientInterface clientRMIInterface;
    private String nickname;
    private int idInGame;

    public RMIPlayer(String nickname,RMIClientInterface clientRMIInterface) {
        this.clientRMIInterface = clientRMIInterface;
    }

    @Override
    public void sendEventToView(EventView eventView) throws RemoteException{
        clientRMIInterface.notifyTheClient(eventView);
    }

    @Override
    public void ping() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public String sayHelloClient() throws RemoteException {
        return null;
    }

    public RMIClientInterface getiRMIClient() {
        return clientRMIInterface;
    }

}
