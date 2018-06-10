package it.polimi.se2018.network.server.rmi;

import it.polimi.se2018.exception.NetworkException.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.NetworkException.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.client.rmi.IRMIClient;
import it.polimi.se2018.network.server.AbstractServer;
import it.polimi.se2018.network.server.ServerController;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the RMI server for every single game.
 * Extends AbstractServer abstract class in order to implement and facilitate Server connection.
 *
 * @author DavideMammarella
 */
public class RMIServer extends AbstractServer implements IRMIServer {

    private static Registry registry;

    // LISTA DEI GIOCATORI CHE HANNO EFFETTUATO IL LOGIN ED HANNO UN NICKNAME
    private ArrayList<RMIPlayer> rmiPlayers;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * RMI Server constructor.
     *
     * @param serverController server interface, used as controller to communicate with the server.
     */
    public RMIServer(ServerController serverController) {
        super(serverController);
        rmiPlayers=new ArrayList<>();
    }

    //------------------------------------------------------------------------------------------------------------------
    // SERVER STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for RMI Server.
     *
     * @param port number of port that will be used on the connection.
     */

    // bind will throw an AlreadyBoundException if there's already an object bound to that name within the rmiregistry.
    // If there was no match, the object will be bound to the name within the registry.

    // rebind will replace any existing binding for the name within rmiregistry.
    // If there was no match, the object will be bound to the name within the registry as usual.
    @Override
    public void startServer(int port) throws Exception {
        registry = createOrLoadRegistry(port);

        try {
            registry.bind("IRMIServer", this);
            UnicastRemoteObject.exportObject(this, port);

            System.out.println("RMI Server running at " + port + " port...");
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creator/Loader for RMI registry.
     * This method start the RMI registry and put it on listen on the assigned port.
     * The RMI registry will exhibits services offered from server to client.
     *
     * @param port port on when the registry will be on listen.
     * @return RMI Registry created with the listen on the assigned port.
     */
    // TODO: GESTIRE REMOTE EXCEPTION
    private Registry createOrLoadRegistry(int port) throws Exception {
        registry=null;
        System.out.println("Creating RMI registry...");
        try {
            System.out.println("RMI registry created!");
            return LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            // Se questa eccezione è stata catturata, probabilmente è perchè il Registry è già stato
            // avviato da linea di comando o da un'altra esecuzione del Server non terminata
            // ( da un altro processo in generale )
            System.out.println("Registry is already initialized!");
        }
        try {
            return LocateRegistry.getRegistry(port);
        } catch (RemoteException e) {
            throw new Exception("Cannot initialize RMI registry!");
        }
    }


    public RemotePlayer searchPlayerById(int id){
        return getServerController().searchPlayerById(id);
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to log the user to the server with his nickname.
     *
     * @param nickname   name of the player.
     * @param iRMIClient client associated to the player.
     */
    @Override
    public void login(String nickname, IRMIClient iRMIClient) throws RemoteException {
        RMIPlayer player = new RMIPlayer(iRMIClient);
        player.setNickname(nickname);
        try {
            if (!getServerController().login(player)) {
                throw new RemoteException();
            }
        } catch (PlayerAlreadyLoggedException | RoomIsFullException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remote method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    @Override
    public void sendEventToController(EventController eventController) {
        getServerController().sendEventToController(eventController);
    }

}
