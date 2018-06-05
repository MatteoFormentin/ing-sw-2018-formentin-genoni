package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.client.AbstractClient;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.network.server.rmi.IRMIServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
    public void connectToServer() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(getServerIpAddress(), getServerPort());
        iRMIServer = (IRMIServer) registry.lookup("IRMIServer");
        UnicastRemoteObject.exportObject(this, 0);
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
    public void login(String nickname) throws RemoteException {
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

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    //Called from remotePlayer who is a dynamic RMIPlayer
    @Override
    public void sendEventToView(EventView eventView) throws RemoteException {
        getClientController().sendEventToView(eventView);
    }

}

