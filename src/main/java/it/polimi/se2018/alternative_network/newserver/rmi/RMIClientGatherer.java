package it.polimi.se2018.alternative_network.newserver.rmi;

import it.polimi.se2018.alternative_network.client.rmi_client.RMIClientInterface;
import it.polimi.se2018.alternative_network.newserver.Server2;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import org.fusesource.jansi.AnsiConsole;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

import static org.fusesource.jansi.Ansi.Color.BLUE;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Class that build and destroy the connection with the Rmi player
 * this is the class that can receive the messages from the player
 *
 * @author DavideMammarella
 * @author Luca Genoni
 */
public class RMIClientGatherer extends UnicastRemoteObject implements RMIServerInterfaceSeenByClient {
    //MAKE HASH MAP
    static LinkedList<RMIPlayer> playerLinkedList;
    private static RMIClientGatherer instance;
    private transient RMIServer server;
    private Server2 mainServer;

    private RMIClientGatherer(Server2 mainServer, RMIServer server, int port) throws RemoteException {
        super(port);
        this.server = server;
        this.mainServer = mainServer;
    }
    //********************************* FROM THE SERVER **********************************************
    //********************************* FROM THE SERVER **********************************************
    //********************************* FROM THE SERVER **********************************************

    static RMIClientGatherer getSingletonClientGatherer(Server2 mainServer, RMIServer rmiServer, int port) throws RemoteException {
        if (instance == null) instance = new RMIClientGatherer(mainServer, rmiServer, port);
        return instance;
    }
    //__________________________________________________________
    //                                                          |
    //      FROM THE CLIENT ALL MUST IMPLEMENT RemoteException  |
    //__________________________________________________________|

    @Override
    public void addClient(String nickname, RMIClientInterface client) {
        //il collegamento viene assegnato al RMIPLayer
        //visto che ho già il nome posso richiedere direttamente il login
        RMIPlayer player = new RMIPlayer(nickname, client);
        mainServer.login(player);
    }


    @Override
    public void disconnect(RMIClientInterface client) {
        try {
            UnicastRemoteObject.unexportObject(client, true);
        } catch (NoSuchObjectException ex) {
            AnsiConsole.out.println(ansi().fg(BLUE).a("RMIClientGatherer, kickPlayer : è stato già eliminato l'RMI Player").reset());
        }
    }

    @Override
    public void sendEventToController(EventController event) {
        mainServer.sendEventToGame(event);
        //  server.sendEventToServer(event);
    }

    public String sayHelloToGatherer() {
        return "ping";
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}