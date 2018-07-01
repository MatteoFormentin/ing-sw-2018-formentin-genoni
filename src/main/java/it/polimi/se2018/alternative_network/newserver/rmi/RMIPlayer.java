package it.polimi.se2018.alternative_network.newserver.rmi;


import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerExeption;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.alternative_network.client.RMIClientInterface;
import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Classe utilizzata per inviare messaggi al client
 * NON RICEVE MESSAGGI DAL CLIENT (se non per il return)
 */
public class RMIPlayer extends RemotePlayer2 {

    private int idInGame;

    //for send event to the client
    private RMIClientInterface clientRMIInterface;

    public RMIPlayer(String nickname, RMIClientInterface clientRMIInterface) {
        super(nickname);
        this.clientRMIInterface = clientRMIInterface;
    }

    @Override
    public void sendEventToView(EventView eventView) throws ConnectionPlayerExeption {
        try {
            clientRMIInterface.notifyTheClient(eventView);
        } catch (RemoteException ex) {
            kickPlayerOut(false);
            throw new ConnectionPlayerExeption();
        }
    }

    @Override
    public String sayHelloClient() throws ConnectionPlayerExeption {
        try {
            System.out.println("Trying to contact the client");
            return clientRMIInterface.pong("ping");
        } catch (RemoteException ex) {
            System.out.println("RMIPlayer, sayHelloClient-> Richiede disconnessione del client");
            kickPlayerOut(true);
            throw new ConnectionPlayerExeption();
        }
    }

    @Override
    public void kickPlayerOut(boolean force) {
        if (force) {
            System.out.println("Richiesta epulsione Hard dal server. quindi rimosso il riferimento del client");
            setPlayerRunning(false);
            try {
                UnicastRemoteObject.unexportObject(clientRMIInterface, true);
                clientRMIInterface.pong("ping");//return pong
                System.out.println("Il player non è stato disconnesso");
                setPlayerRunning(true);
            } catch(NoSuchObjectException ex){
                System.out.println(getNickname()+" è già stato disconnesso");
            } catch(RemoteException ex){
                System.out.println(getNickname()+" è stato disconnesso con successo");
            }
        } else {
            System.out.println("Richiesta espulsione light dal server. quindi settato come false ma ancora raggiungibile!");
            setPlayerRunning(false);
        }
    }
}
