package it.polimi.se2018.network.client;

import it.polimi.se2018.list_event.event_view.EventView;
import it.polimi.se2018.list_event.event_controller.EventController;
import it.polimi.se2018.network.client.rmi.RMIClient;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.cli.CliController;

/**
 * Class based on the Abstract Factory Design Pattern.
 * This class define the client side of the game.
 * This class implements ClientController to have basic methods for RMI and Socket Client.
 *
 * @author DavideMammarella
 */
public class Client implements ClientController{

    // Classe che rappresenta il client selezionato
    private AbstractClient abstractClient;
    // Interfaccia utilizzata per inviare eventi a CLI o GUI (a seconda del tipo di interfaccia utilizzata per il client)

    // INDIRIZZI PER LA COMUNICAZIONE

    // Indirizzo su cui le comunicazioni sono aperte a lato server
    private static final String serverAddress = "localhost";
    //Porta su cui si appoggierà la comunicazione socket
    private static final int serverSocketPort = 16180;
    //Porta su cui si appoggierà la comunicazione RMI
    private static final int serverRMIPort = 31415;

    // Nome del giocatore corrente
    private String nickname;
    // Turno della partita (da 1 a 9)
    private int turn;


    private static UIInterface view;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    // MANCA TUTTO
    public Client(){
        nickname="Mr.Nessuno";
        this.turn = 0;
    }

    //------------------------------------------------------------------------------------------------------------------
    // CLIENT STARTER
    //------------------------------------------------------------------------------------------------------------------

    // aggiungere porta socket se aggiungi socket
    public static void main(String[] args){
        String serverIpAddress = serverAddress;
        int rmiPort = serverRMIPort;

        try {
            /*serverIpAddress = args[0];
            rmiPort = Integer.parseInt(args[0]);*/

            ClientController client = new Client();
            view = new CliController(client);
        } catch (Exception e){
            System.exit(0);
        }
    }

    // AGGIUNGI int socketPort se aggiungi socket
    // Se aggiungi socket qui ci dovrà essere la selezione, data da cli o gui fra RMI o Socket ed in base alla scelta bisogna far partire connessioni diverse
    public void startClient(String serverIpAddress, int rmiPort){
        startRMIClient(serverIpAddress, rmiPort);
    }

    public boolean startRMIClient(String serverIpAddress, int rmiPort) {
        try {
            abstractClient = new RMIClient(this, serverIpAddress, rmiPort);
            abstractClient.connectToServer();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Log the user to the Server with the nickname
     *
     * @param nickname name used for the player.
     */
    public boolean login(String nickname) {
        try {
            abstractClient.login(nickname);
            this.nickname = nickname;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Send to the server the request to unleash an event.
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

    @Override
    public void sendEventToView(EventView eventView) {
        view.showMessage(eventView);
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    // NOTIFY
    //------------------------------------------------------------------------------------------------------------------

    /*
    public void sendUpdateToView(EventUpdate eventUpdate){
    // GESTIONE UPDATE
    }
     */

    //------------------------------------------------------------------------------------------------------------------
    // METHOD FOR SUPPORT (GET, SET, CHECK)
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for nickname.
     *
     * @return nickname of the player associated to the client
     */
    public String getNickname(){
        return this.nickname;
    }

}
