package it.polimi.se2018.network.server.rmi;

import it.polimi.se2018.event.list_event.EventView;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.client.rmi.IRMIClient;
import it.polimi.se2018.network.server.AbstractServer;
import it.polimi.se2018.network.server.ServerController;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the RMI server for every single game.
 * Extends AbstractServer abstract class in order to implement and facilitate Server connection.
 *
 * @author DavideMammarella
 */
public class RMIServer extends AbstractServer implements IRMIServer {

    //Constructor
    public RMIServer(ServerController serverController){
        super(serverController);
    }

    //RMI Server starter

    /**
     * Starter for RMI Server.
     *
     * @param port number of port that will be used on the connection.
     * @throws Exception
     */
    @Override
    public void startServer(int port) throws Exception {
        Registry registry = createOrLoadRegistry(port);
        try {
            registry.bind("IRMIServer", this);
            UnicastRemoteObject.exportObject(this, port);
            System.out.println("Server running at "+port+" port...");
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (AlreadyBoundException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // Avvio il registry mettendolo in ascolto alla porta specificata.
    // Il registry espone i servizi offerti dal server ai client.
    private Registry createOrLoadRegistry(int port) throws Exception {
        try {
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

    /**
     * Getter for RemotePlayer.
     *
     * @param nickname name of the player.
     * @return RemotePlayer associated to the username.
     */
    private RemotePlayer getPlayer(String nickname){
        return getServerController().getPlayer(nickname);
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - CALL TO THE SERVER // RMI
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to login.
     * @param nickname name of the player.
     * @param iRMIClient client associated to the player.
     * @return
     */
    //MANCA JAVADOC
    //MANCA EXCEPTION
    @Override
    public boolean login(String nickname, IRMIClient iRMIClient){
        try{
            getServerController().login(nickname, new RMIPlayer(iRMIClient));
            return true;
        } catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remote method used to send to the Server a request to set off an event.
     *
     * @param nickname name of the player.
     * @param eventView object that will use the server to set off the event associated.
     */
    @Override
    public void sendEvent(String nickname, EventView eventView){
        RemotePlayer remotePlayer=getPlayer(nickname);
        //remotePlayer.getGame().performEvent(remotePlayer, eventView);
    }

}
