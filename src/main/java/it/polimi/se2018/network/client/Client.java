package it.polimi.se2018.network.client;

import it.polimi.se2018.exception.network_exception.NoPortRightException;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.ProblemConnectionException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.client.rmi.RMIClient;
import it.polimi.se2018.network.client.socket.SocketClient;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.cli.CliController;
import it.polimi.se2018.view.gui.GuiInstance;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;

import static it.polimi.se2018.view.gui.GuiInstance.createGuiInstance;
import static it.polimi.se2018.view.gui.GuiInstance.getGuiInstance;


/**
 * Class based on the Abstract Factory Design Pattern.
 * This class define the client side of the game.
 * This class implements ClientController to have basic methods for RMI and Socket Client.
 *
 * @author DavideMammarella
 */
public class Client implements ClientController {

    // Indirizzo su cui le comunicazioni sono aperte a lato server
    private static String SERVER_ADDRESS;
    //Porta su cui si appoggierà la comunicazione socket
    private static int SERVER_SOCKET_PORT;
    //Porta su cui si appoggierà la comunicazione RMI
    private static int SERVER_RMI_PORT;
    private static UIInterface view;
    // Classe che rappresenta il client selezionato
    private AbstractClient abstractClient;
    // Nome del giocatore corrente
    private String nickname;
    // Turno della partita (da 1 a 9)
    private int turn;

    // Si ricollega alla logica dei thread (vedi Timer)
    // Setto una variabile booleana grazie la quale posso fornire lo stato del client
    // Ovvero se c'è una connessione ancora valida (Running / true) o meno (Not Running / false)
    private Boolean clientRunning;


    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Client constructor.
     */
    public Client() {
        this.nickname = "Mr.Nessuno";
        this.turn = 0;
        this.clientRunning = false;
    }

    //------------------------------------------------------------------------------------------------------------------
    // CLIENT STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for the client.
     * This method start the client using the assigned address and port.
     *
     * @param args parameters used for the connection.
     */
    public static void main(String[] args) {

        // CONFIGURATIONS LOADER
        System.out.println("Configuring the connection...");
        try {
            // LOAD FROM PROPERTIES
            Properties configProperties = new Properties();

            String connectionConfig = "src/resources/configurations/connection_configuration.properties";
            FileInputStream inputConnection = new FileInputStream(connectionConfig);

            configProperties.load(inputConnection);

            // SERVER ADDRESS LOAD
            SERVER_ADDRESS = configProperties.getProperty("SERVER_ADDRESS");
            System.out.println("Server address set to " + configProperties.getProperty("SERVER_ADDRESS"));

            // RMI PORT LOAD
            SERVER_RMI_PORT = Integer.parseInt(configProperties.getProperty("RMI_PORT"));
            System.out.println("RMI port set to " + configProperties.getProperty("RMI_PORT"));

            // SOCKET PORT LOAD
            SERVER_SOCKET_PORT = Integer.parseInt(configProperties.getProperty("SOCKET_PORT"));
            System.out.println("Socket port set to " + configProperties.getProperty("SOCKET_PORT"));

            // ASSEGNO LE PORTE ALLE VARIABILI
            String serverIpAddress = SERVER_ADDRESS;
            int rmiPort = SERVER_RMI_PORT;
            int socketPort = SERVER_SOCKET_PORT;

        } catch (IOException e) {
            // LOAD FAILED
            System.out.println("Sorry, the configuration can't be setted! The default one will be used...");
            // Default timeout in case of exception.
            SERVER_ADDRESS = "localhost";
            // Default RMI PORT in case of exception.
            SERVER_RMI_PORT = 31415;
            // Default Socket PORT in case of exception.
            SERVER_SOCKET_PORT = 16180;
        }

        try {
            ClientController client = new Client();


            if (args[0].equals("cli")) {
                view = new CliController(client);
            }

            if (args[0].equals("gui")) {
                createGuiInstance(client);
                view = getGuiInstance();
                ((GuiInstance) view).startGui();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // the main finish by it self only if there are no thread
            System.exit(0);
        }
    }

    /**
     * Visual starter of the client.
     *
     * @param serverIpAddress address on where the server side communication are open.
     * @param socketRmi       number used to manage the decision of the user about the connection. RMI (=0) SOCKET (=1).
     */
    public void startClient(String serverIpAddress, int socketRmi) throws Exception {
        Properties configProperties = new Properties();
        String connectionConfig = "src/resources/configurations/connection_configuration.properties";
        FileInputStream inputConnection = new FileInputStream(connectionConfig);
        configProperties.load(inputConnection);

        String ipServerTrue = configProperties.getProperty("SERVER_ADDRESS");
        if (serverIpAddress.equals("") || serverIpAddress.equals("0")) serverIpAddress = ipServerTrue;
        int numberPort;
        if (socketRmi == 0) {

            System.out.println("RMI!!!");

            numberPort = Integer.parseInt(configProperties.getProperty("RMI_PORT"));
            //TODO rimuovere i boolean e mettere eccezioni
            boolean connected = startRMIClient(serverIpAddress, numberPort);
            if (!connected) throw new ProblemConnectionException();
        } else if (socketRmi == 1) {
            System.out.println("SOCKET!!!");

            numberPort = Integer.parseInt(configProperties.getProperty("SOCKET_PORT"));
            //TODO rimuovere i boolean e mettere eccezioni
            boolean connected = startSocketClient(serverIpAddress, numberPort);
            if (!connected) throw new ProblemConnectionException();
        } else throw new NoPortRightException();
    }

    /**
     * Starter for the RMI connection.
     *
     * @param serverIpAddress address on where the server side communication are open.
     * @param rmiPort         port used for RMI connection.
     * @return true if the connection is established, false otherwise.
     */
    public boolean startRMIClient(String serverIpAddress, int rmiPort) {
        try {
            abstractClient = new RMIClient(this, serverIpAddress, rmiPort);
            abstractClient.connectToServer();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Starter for the Socket connection.
     *
     * @param serverIpAddress address on where the server side communication are open.
     * @param socketPort      port used for Socket connection.
     * @return true if the connection is established, false otherwise.
     */
    public boolean startSocketClient(String serverIpAddress, int socketPort) {
        try {
            abstractClient = new SocketClient(this, serverIpAddress, socketPort);
            abstractClient.connectToServer();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to log the user to the server with his nickname.
     *
     * @param nickname name of the player associated to the client.
     * @return true if the user is logged, false otherwise.
     */
    public boolean login(String nickname) {
        try {
            abstractClient.login(nickname);
            this.nickname = nickname;
            this.clientRunning = true;
            return true;
        } catch (RemoteException | PlayerAlreadyLoggedException e) {
            return false;
        }
    }

    /**
     * Remote method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    public void sendEventToController(EventController eventController) {
        try {
            abstractClient.sendEventToController(eventController);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToView(EventView eventView) {
        try {
            view.showMessage(eventView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //TODO:gestisci update
    }

    /**
     * Remote method used to ping the client.
     */
    @Override
    public void ping() {
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for the nickname.
     *
     * @return nickname of the player associated to the client.
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Getter for the client status.
     *
     * @return true if the client is running, false otherwise.
     */
    public Boolean getClientRunning() {
        return this.clientRunning;
    }
}
