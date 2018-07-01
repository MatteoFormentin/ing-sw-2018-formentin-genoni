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
    public void sayHelloClient() throws ConnectionPlayerExeption {
        try {
            System.out.print("RMIPlayer -> sayHelloClient :" + getNickname() + "  ");
            System.out.println(clientRMIInterface.pong("ping"));
            ;
        } catch (RemoteException ex) {
            System.out.println("Non connesso.");
            throw new ConnectionPlayerExeption();
        }
    }

    @Override
    public void kickPlayerOut(boolean force) {
        //TODO rimuovere il force=true
        force=true;
        System.out.println();
        System.out.print("RMIPlayer -> kickPlayerOut : ");
        try {
            clientRMIInterface.pong("ping");//return pong
            System.out.print("-> Il giocatore è ancora connesso");
            setPlayerRunning(true);
        } catch (RemoteException ex) {
            System.out.print("-> non connesso");
            setPlayerRunning(false);
        }
        if(force){
            if(isPlayerRunning()){//ancora connesso
                try {
                    UnicastRemoteObject.unexportObject(clientRMIInterface, true);
                    System.out.println("-> è stato disconnesso");
                } catch (NoSuchObjectException ex) {
                    System.out.println("-> già disconnesso");
                }
                setPlayerRunning(false);
            }else{ //non più connesso
                System.out.println("-> già disconnesso");
                setPlayerRunning(false);
            }
        }else{
            System.out.println("-> Espulsione light, settato come false ma potrebbe ancora inviare messaggi al server");
            setPlayerRunning(false);
        }
        System.out.println();
    }

}
