package it.polimi.se2018.alternative_network.newserver.rmi;

import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerExeption;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.alternative_network.client.rmi_client.RMIClientInterface;
import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import org.fusesource.jansi.AnsiConsole;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static org.fusesource.jansi.Ansi.Color.BLUE;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Classe utilizzata per inviare messaggi al client
 * NON RICEVE MESSAGGI DAL CLIENT (se non per il return)
 */
public class RMIPlayer extends RemotePlayer2{
    //for send event to the client
    private RMIClientInterface clientRMIInterface;


    RMIPlayer(String nickname, RMIClientInterface clientRMIInterface) {
        super(nickname);
        this.clientRMIInterface = clientRMIInterface;
    }

    @Override
    public void sendEventToView(EventView eventView) throws ConnectionPlayerExeption {
        try {
            clientRMIInterface.notifyTheClient(eventView);
        } catch (RemoteException ex) {
            throw new ConnectionPlayerExeption();
        }
    }

    @Override
    public void sayHelloClient() throws ConnectionPlayerExeption {
        try {
            AnsiConsole.out.print(ansi().fg(BLUE).a("RMIPlayer -> sayHelloClient :" + getNickname() + "  ").reset());
            AnsiConsole.out.println(ansi().fg(BLUE).a(clientRMIInterface.pong("ping")).reset());
        } catch (RemoteException ex) {
            AnsiConsole.out.println(ansi().fg(BLUE).a("Non connesso.").reset());
            throw new ConnectionPlayerExeption();
        }
    }

    @Override
    public void kickPlayerOut() {
        setPlayerRunning(false);
        AnsiConsole.out.println();
        AnsiConsole.out.print(ansi().fg(BLUE).a("RMIPlayer -> kickPlayerOut: " + getNickname() + "  ").reset());
        try {
            AnsiConsole.out.println(clientRMIInterface.pong("ping"));
            UnicastRemoteObject.unexportObject(clientRMIInterface, true);
        }catch(NoSuchObjectException ex){
            AnsiConsole.out.print(ansi().fg(BLUE).a(" ").reset());
        }catch(RemoteException ex){
            AnsiConsole.out.print(ansi().fg(BLUE).a("Non raggiungibile").reset());
            try{
                UnicastRemoteObject.unexportObject(clientRMIInterface, true);
            }catch(NoSuchObjectException ex2){
                AnsiConsole.out.print(ansi().fg(BLUE).a(" perchè già disconnesso").reset());
            }
        }
    }

}
