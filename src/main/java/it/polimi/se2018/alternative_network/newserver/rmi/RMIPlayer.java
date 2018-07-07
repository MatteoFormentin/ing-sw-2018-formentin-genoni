package it.polimi.se2018.alternative_network.newserver.rmi;

import it.polimi.se2018.alternative_network.client.rmi_client.RMIClientInterface;
import it.polimi.se2018.alternative_network.newserver.PrincipalServer;
import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.alternative_network.newserver.room.GameInterface;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import org.fusesource.jansi.AnsiConsole;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static org.fusesource.jansi.Ansi.Color.BLUE;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Classe utilizzata per inviare messaggi al client
 * NON RICEVE MESSAGGI DAL CLIENT (se non per il return)
 *
 * @author DavideMammarella
 */
public class RMIPlayer extends RemotePlayer2 {
    //for send event to the client
    private RMIClientInterface clientRMIInterface;
    private PrincipalServer mainServer;
    private boolean online;
    RMIPlayer(String nickname, RMIClientInterface clientRMIInterface) {
        setNickname(nickname);
        this.clientRMIInterface = clientRMIInterface;
    }

    @Override
    public void sendEventToView(EventClient eventClient) {
        try {
            clientRMIInterface.notifyTheClient(eventClient);
        } catch (RemoteException ex) {
            online=false;
            getGameInterface().disconnectFromGameRoom(this);
        }
    }

    @Override
    public void sendEventToGame(EventController eventController) {
        if(getGameInterface()!=null) getGameInterface().sendEventToGameRoom(eventController);
        else kickPlayerOut();
    }

    @Override
    public void kickPlayerOut() {
        AnsiConsole.out.println();
        AnsiConsole.out.print(ansi().fg(BLUE).a("RMIPlayer -> kickPlayerOut: " + getNickname() + "  ").reset());
        try {
            UnicastRemoteObject.unexportObject(clientRMIInterface, true);
            clientRMIInterface.ping("ping");
        } catch (RemoteException ex) {
            if (getGameInterface() != null) getGameInterface().disconnectFromGameRoom(this);
        }
    }

    @Override
    public boolean checkOnline() {
        if(!online) return false;
        try {
            clientRMIInterface.ping("ping");
        } catch (RemoteException ex) {
            try {
                UnicastRemoteObject.unexportObject(clientRMIInterface, true);
            } catch (NoSuchObjectException ex2) {
                return false;
            }
            return false;
        }
        return true;
    }

}
