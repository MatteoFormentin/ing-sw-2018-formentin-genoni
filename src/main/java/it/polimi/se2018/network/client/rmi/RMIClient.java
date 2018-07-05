package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.exception.network_exception.ClientSideException;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.network.client.AbstractClient;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.network.server.rmi.IRMIServer;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the RMI client for every single player.
 * Extends AbstractClient class in order to implement and facilitate Client connection.
 *
 * @author DavideMammarella
 */
public class RMIClient extends AbstractClient implements IRMIClient {

    //Interfaccia client dell'RMI, viene utilizzata per permettere l'invocazione remota di metodi tramite server
    private IRMIServer iRMIServer;

    // Username del giocatore
    private String nickname;

    private int id;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * RMI Client constructor.
     *
     * @param clientController client interface, used as controller to communicate with the client.
     * @param serverIpAddress  server address.
     * @param serverPort       port used from server to communicate.
     */
    public RMIClient(ClientController clientController, String serverIpAddress, int serverPort) {
        super(clientController, serverIpAddress, serverPort);
    }

    //------------------------------------------------------------------------------------------------------------------
    // CONNECTION
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to establish a connection with the RMI Registry (on the server).
     */
    //TODO: EXCEPTION
    public void connectToServer() throws ClientSideException {
        /*try{
            AnsiConsole.out.println(ansi().fg(DEFAULT).a("Trying Locate Registry...").reset());
            Registry registry = LocateRegistry.getRegistry(getServerIpAddress(), getServerPort());
        iRMIServer = (IRMIServer) registry.lookup("IRMIServer");
        AnsiConsole.out.println(ansi().fg(YELLOW).a("SERVER IP in client:"+getServerIpAddress()).reset());
        AnsiConsole.out.println(ansi().fg(YELLOW).a("SERVER PORT in client :"+getServerPort()).reset());
        UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException | NotBoundException e){
            System.err.println("Server is not attainable!");
        }*/

        try{
            AnsiConsole.out.println(ansi().fg(DEFAULT).a("Trying Naming Registry...").reset());
            iRMIServer = (IRMIServer) Naming.lookup("//"+getServerIpAddress()+":"+getServerPort()+"/IRMIServer");
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            throw new ClientSideException("RMI Server Connection refused on this port!",e);
        } catch (NotBoundException e) {
            throw new ClientSideException("Impossible to lookup or unbind in the registry a name that has no associated binding!",e);
        } catch (MalformedURLException e) {
            throw new ClientSideException("Error on syntax of the remote object URL!",e);
        }

    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to log the user to the server with his nickname.
     *
     * @param nickname name of the player associated to the client.
     */
    @Override
    public void login(String nickname) throws RemoteException{
        iRMIServer.login(nickname, this);
        //magari flag per vedere se il login è avvenuto con successo (entra anche il clientcontroller)
    }

    /**
     * Remote method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    @Override
    public void sendEventToController(EventController eventController) throws RemoteException {
        iRMIServer.sendEventToController(eventController);
    }

    /**
     * Remote method used to disconnect a client from the server.
     */
    @Override
    public void disconnect() throws RemoteException {
        try {
            iRMIServer.disconnect(this.id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to send to the client an update of the game.
     *
     * @param eventClient object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToView(EventClient eventClient) throws RemoteException {
        getClientController().sendEventToView(eventClient);
    }

    /**
     * Remote method used to ping the client.
     */
    @Override
    public void ping() throws RemoteException {
        getClientController().ping();
    }

}

