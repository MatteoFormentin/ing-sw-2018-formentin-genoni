package it.polimi.se2018.network.server.rmi;

import it.polimi.se2018.exception.network_exception.PlayerNetworkException;
import it.polimi.se2018.exception.network_exception.ServerSideException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.client.rmi.IRMIClient;
import it.polimi.se2018.network.server.AbstractServer;
import it.polimi.se2018.network.server.ServerController;
import it.polimi.se2018.view.cli.CliParser;
import org.fusesource.jansi.AnsiConsole;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Properties;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the RMI server for every single game.
 * Extends AbstractServer abstract class in order to implement and facilitate Server connection.
 *
 * @author DavideMammarella
 */
public class RMIServer extends AbstractServer implements IRMIServer{

    // LISTA DEI GIOCATORI CHE HANNO EFFETTUATO IL LOGIN ED HANNO UN NICKNAME
    private ArrayList<RMIPlayer> rmiPlayers;

    // Indirizzo su cui le comunicazioni sono aperte a lato server
    private static String SERVER_ADDRESS;

    //Porta su cui si appoggierà la comunicazione RMI
    private static int SERVER_RMI_PORT;

    private CliParser input;

    private int RMI_PORT;

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
        try {
            // LOAD FROM PROPERTIES
            Properties configProperties = new Properties();

            String connectionConfig = "src/resources/configurations/connection_configuration.properties";
            FileInputStream inputConnection = new FileInputStream(connectionConfig);

            configProperties.load(inputConnection);

            // SERVER ADDRESS LOAD
            SERVER_ADDRESS = configProperties.getProperty("SERVER_ADDRESS");
            // RMI PORT LOAD
            SERVER_RMI_PORT = Integer.parseInt(configProperties.getProperty("RMI_PORT"));

        } catch (IOException e) {
            // LOAD FAILED
            // Default timeout in case of exception.
            SERVER_ADDRESS = "localhost";
            // Default RMI PORT in case of exception.
            SERVER_RMI_PORT = 31415;
        }
        rmiPlayers = new ArrayList<>();
    }

    //------------------------------------------------------------------------------------------------------------------
    // SERVER STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for RMI Server.
     * This method start the RMI Server after the RMI registry creation.
     * RMI registry will be created and putted on listen on the assigned port.
     * The RMI registry will exhibits services offered from server to client.
     *
     * @param port number of port that will be used on the connection (on when the registry will be on listen).
     */
    @Override
    public void startServer(int port) throws ServerSideException {
        Registry registry = null;
        // Creating RMI registry
        try {
            registry = LocateRegistry.createRegistry(port);
            //RMI registry created!
            AnsiConsole.out.println(ansi().fg(GREEN).a("RMI connection created!").reset());
            AnsiConsole.out.println(ansi().fg(YELLOW).a("SERVER IP in client:"+getServerController()).reset());
        } catch (RemoteException e) {
            // Se questa eccezione è stata catturata, probabilmente è perchè il Registry è già stato
            // avviato da linea di comando o da un'altra esecuzione del Server non terminata
            // ( da un altro processo in generale )
            System.err.println("RMI Registry already initialized!");
            try {
                Naming.rebind("//" + SERVER_ADDRESS + ":" + RMI_PORT + "/IRMIServer", this);
                AnsiConsole.out.println(ansi().fg(GREEN).a("RMI Registry rebinded").reset());
            } catch (MalformedURLException e1) {
                System.err.println("Error on syntax of the remote object URL!");
            } catch (RemoteException e1) {
                System.err.println("RMI Server Connection refused on this port!");
            }
        }

        if(registry!=null) {
            try {
                registry.rebind("IRMIServer", this);
                UnicastRemoteObject.exportObject(this, port);

                AnsiConsole.out.println(ansi().fg(DEFAULT).a("RMI Server running at " + port + " port\n").reset());

            } catch (RemoteException e) {
                throw new ServerSideException("Unable to create Server Interface.");
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to log the user to the server with his nickname.
     * @param nickname name of the player.
     * @param iRMIClient client associated to the player.*/
    @Override
    public void login(String nickname, IRMIClient iRMIClient) throws RemoteException {
        RMIPlayer player = new RMIPlayer(iRMIClient);
        player.setNickname(nickname);
        rmiPlayers.add(player);
        try {
            if (!getServerController().login(player)) {
                throw new RemoteException();
            }
        } catch (PlayerNetworkException e) {
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

    /**
     * Remote method used to disconnect a client from the server.
     *
     * @param id id associated to the player.
     */
    @Override
    public void disconnect(int id) throws IOException {
        searchPlayerById(id).disconnect();
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to remove a player from the RMI server.
     *
     * @param remotePlayer player that must be removed.
     */
    public void removePlayer(RemotePlayer remotePlayer){
        remotePlayer.setPlayerRunning(false);
        int id = rmiPlayers.indexOf(remotePlayer);
        if(id != -1)
            rmiPlayers.remove(id);
    }

    /**
     * Method used to search a player on the RMI server.
     *
     * @param id id associated to the player.
     * @return Remote Player associated to the id.
     */
    public RemotePlayer searchPlayerById(int id) {
        return getServerController().searchPlayerById(id);
    }

}
