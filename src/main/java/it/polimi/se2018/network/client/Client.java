package it.polimi.se2018.network.client;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.client.rmi.RMIClient;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.cli.CliController;
import it.polimi.se2018.view.gui.GuiReceiver;


import java.rmi.RemoteException;

import static it.polimi.se2018.view.gui.GuiReceiver.setUpGUI;


/**
 * Class based on the Abstract Factory Design Pattern.
 * This class define the client side of the game.
 * This class implements ClientController to have basic methods for RMI and Socket Client.
 *
 * @author DavideMammarella
 */
public class Client implements ClientController {

    // Indirizzo su cui le comunicazioni sono aperte a lato server
    private static final String SERVER_ADDRESS = "localhost";
    //Porta su cui si appoggierà la comunicazione socket
    private static final int SERVER_SOCKET_PORT = 16180;
    //Porta su cui si appoggierà la comunicazione RMI
    private static final int SERVER_RMI_PORT = 31415;
    private static UIInterface view;
    // Classe che rappresenta il client selezionato
    private AbstractClient abstractClient;
    // Nome del giocatore corrente
    private String nickname;
    // Turno della partita (da 1 a 9)
    private int turn;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Client constructor.
     */
    public Client() {
        nickname = "Mr.Nessuno";
        this.turn = 0;
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
    //TODO:aggiungere porta socket se aggiungi socket
    public static void main(String[] args) {
        String serverIpAddress = SERVER_ADDRESS;
        int rmiPort = SERVER_RMI_PORT;

        try {
            /*serverIpAddress = args[0];
            rmiPort = Integer.parseInt(args[0]);*/
            ClientController client = new Client();

            if (args[0].equals("cli")) {
                view = new CliController(client);
            }

            if (args[0].equals("gui")) {
                view = new GuiReceiver();
                setUpGUI(args);
            }
        } catch (Exception e) {
            System.exit(0);
        }
    }

    /**
     * Starter for the client connection.
     *
     * @param rmiPort port used for RMI connection.
     */
    //TODO:AGGIUNGI int socketPort se aggiungi socket
    //TODO:Se aggiungi socket qui ci dovrà essere la selezione, data da cli o gui fra RMI o Socket ed in base alla scelta bisogna far partire connessioni diverse
    public void startClient(String serverIpAddress, int rmiPort) {
        startRMIClient(serverIpAddress, rmiPort);
    }

    /**
     * Starter for the RMI connection.
     *
     * @param serverIpAddress address on where the server side communication are open.
     * @param rmiPort         port used for RMI connection.
     * @return true if the connection is established, false otherwise.
     */
    @Override
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
     * Starter for the RMI connection with default ip and port.
     *
     * @return true if the connection is established, false otherwise.
     */
    @Override
    public boolean startRMIClient() {
        try {
            abstractClient = new RMIClient(this, "127.0.0.1", 31415);
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
            return true;
        } catch (RemoteException e) {
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
        view.showMessage(eventView);
        //TODO:gestisci update
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

}
