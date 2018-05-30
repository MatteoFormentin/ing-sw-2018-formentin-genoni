package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.list_event.event_view.EventView;
import it.polimi.se2018.list_event.event_controller.EventController;
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
 * The class define the RMI client for every single game.
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
     * Instantiate one RMIClient.
     *
     * @param clientController client interface, used as
     *                         controller to communicate with the client.
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
     * Estabilish a connection with the RMI Registry (on the server).
     */
    // SISTEMA EXCEPTION
    public void connectToServer() throws RemoteException, NotBoundException {
        Registry registry;
        registry = LocateRegistry.getRegistry(getServerIpAddress(), getServerPort());
        iRMIServer = (IRMIServer) registry.lookup("IRMIServer");
        UnicastRemoteObject.exportObject(this, 0);
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Log the user to the RMI Server with the nickname.
     * The Username will be added to the map.
     *
     * @param nickname name used for the player.
     */
    @Override
    public void login(String nickname) throws RemoteException {
        iRMIServer.login(nickname, this);
    }

    /**
     * Send to the Server the request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */

    @Override
    public void sendEventToController(EventController eventController) throws RemoteException {
        iRMIServer.sendEventToController(eventController);
    }

    //Chiamato dal remotePlayer che è un rmiplayer  dinamico
    @Override
    public void sendEventToView(EventView eventView) throws RemoteException {
        getClientController().sendEventToView(eventView);
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    // NOTIFY
    //------------------------------------------------------------------------------------------------------------------

    /*
    @Override
    public void notify(EventUpdate eventUpdate){
        getClientController().update(eventUpdate);
    }
    */

}

