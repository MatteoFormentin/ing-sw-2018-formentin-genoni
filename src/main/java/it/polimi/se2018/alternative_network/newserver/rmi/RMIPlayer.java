package it.polimi.se2018.alternative_network.newserver.rmi;

import it.polimi.se2018.alternative_network.client.rmi_client.RMIClientInterface;
import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.alternative_network.newserver.room.GameInterface;
import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerException;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import org.fusesource.jansi.AnsiConsole;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.fusesource.jansi.Ansi.Color.BLUE;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Classe utilizzata per inviare messaggi al client
 * NON RICEVE MESSAGGI DAL CLIENT (se non per il return)
 */
public class RMIPlayer implements RemotePlayer2 {
    //for send event to the client
    private RMIClientInterface clientRMIInterface;


    private String nickname;
    private int idPlayerInGame;
    private GameInterface gameInterface;
   // private Object MUTEX;

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname=nickname;
    }

    @Override
    public int getIdPlayerInGame() {
        return idPlayerInGame;
    }

    @Override
    public void setIdPlayerInGame(int idPlayerInGame) {
        this.idPlayerInGame=idPlayerInGame;
    }

    @Override
    public GameInterface getGameInterface() {
        return gameInterface;
    }

    @Override
    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface=gameInterface;
    }

    @Override
    public void sendEventToView(EventClient eventClient){
        try {
            clientRMIInterface.notifyTheClient(eventClient);
        } catch (RemoteException ex) {
            getGameInterface().disconnectFromGameRoom(this);
        }
    }

    RMIPlayer(String nickname, RMIClientInterface clientRMIInterface) {
        setNickname(nickname);
        this.clientRMIInterface = clientRMIInterface;
      //  MUTEX=new Object();
    }


/*
    @Override
    public void sayHelloClient() throws ConnectionPlayerException {
        try {
            AnsiConsole.out.print(ansi().fg(BLUE).a("RMIPlayer -> sayHelloClient :" + getNickname() + "  ").reset());
            AnsiConsole.out.println(ansi().fg(BLUE).a(clientRMIInterface.pong("ping")).reset());
        } catch (RemoteException ex) {
            AnsiConsole.out.println(ansi().fg(BLUE).a("Non connesso.").reset());
            throw new ConnectionPlayerException();
        }
    }*/

    @Override
    public void kickPlayerOut() {
        AnsiConsole.out.println();
        AnsiConsole.out.print(ansi().fg(BLUE).a("RMIPlayer -> kickPlayerOut: " + getNickname() + "  ").reset());
        try {
            clientRMIInterface.ping("ping");
            UnicastRemoteObject.unexportObject(clientRMIInterface, true);
        } catch (NoSuchObjectException ex) {
            AnsiConsole.out.print(ansi().fg(BLUE).a(" ").reset());
        } catch (RemoteException ex) {
            AnsiConsole.out.print(ansi().fg(BLUE).a("Non raggiungibile").reset());
            try {
                UnicastRemoteObject.unexportObject(clientRMIInterface, true);
            } catch (NoSuchObjectException ex2) {
                AnsiConsole.out.print(ansi().fg(BLUE).a(" perchè già disconnesso").reset());
            }
        }
    }

    @Override
    public boolean checkOnline() {
        try {
            clientRMIInterface.ping("ping");
        } catch (RemoteException ex) {
            System.out.println("non c'è la connessione attiva");
            try {
                UnicastRemoteObject.unexportObject(clientRMIInterface, true);
            } catch (NoSuchObjectException ex2) {
                System.out.println("è stato già rimosso");
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void sendEventToController(EventController eventController) {
    //    synchronized (MUTEX){
            getGameInterface().sendEventToGameRoom(eventController);
    //    }
    }

}
