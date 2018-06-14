package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.client.AbstractClient;
import it.polimi.se2018.network.client.ClientController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the Socket client for every single player.
 * Extends AbstractClient class in order to implement and facilitate Client connection.
 *
 * @author DavideMammarella
 */
public class SocketClient extends AbstractClient {

    // comunicazione con il socket
    private Socket clientConnection;

    // stream di input
    private ObjectInputStream inputStream;
    // stream di output
    private ObjectOutputStream outputStream;

    // protocollo che verrà usato per gestire le risposte da server
    private ResponseHandlerProtocol responseHandlerProtocol;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Socket Client constructor.
     *
     * @param clientController client interface, used as controller to communicate with the client.
     * @param serverIpAddress  server address.
     * @param serverPort       port used from server to communicate.
     */
    public SocketClient(ClientController clientController, String serverIpAddress, int serverPort) {
        super(clientController, serverIpAddress, serverPort);
    }

    //------------------------------------------------------------------------------------------------------------------
    // CONNECTION
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to establish a connection with the Server.
     */
    //TODO: EXCEPTION
    public void connectToServer() {
        try {

            clientConnection = new Socket(getServerIpAddress(), getServerPort());

            inputStream = new ObjectInputStream(clientConnection.getInputStream());
            outputStream = new ObjectOutputStream(clientConnection.getOutputStream());
            outputStream.flush();

        } catch (IOException e) {
            // eccezione di errore connessione client
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // PROTOCOL STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for the response handler protocol.
     */
    public void responseHandlerProtocolStarter(){
        try {
            this.responseHandlerProtocol = new ResponseHandlerProtocol(this, this.inputStream,this.outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // RESPONSE LISTENER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for the server response listener thread.
     */
    private void responseListenerStarter() {
        ResponseListener responseListener = new ResponseListener();
        responseListener.start();
    }

    /**
     * Class that define the listener for the server response.
     * This class will start a thread that will be on hold for a message from server, and will manage it with the protocol.
     */
    private class ResponseListener extends Thread {

        /**
         * Runner for Response Listener thread.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            try {

                boolean loop = true;

                // In loop attendo l'arrivo di messaggi dal server
                // Ogni messaggio verrà mandato al protocollo per la gestione
                while (loop) {

                    Object object = inputStream.readObject();
                    responseHandlerProtocol.handleResponse(object);

                }
            } catch (ClassNotFoundException | IOException e) {
                // eccezione nella lettura della messaggio del server
                e.printStackTrace();
            }

            finally {
                closeConnections();
            }
        }

    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER (PROTOCOL CALL ONLY)
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to call the login event on the protocol.
     *
     * @param nickname name of the player associated to the client.
     */
    @Override
    public void login(String nickname) throws RemoteException {
        //this.responseHandlerProtocol.login(nickname);
        // faccio partire il thread che resterà in ascolto di messaggi dal server
        //this.responseListenerStarter();
    }

    /**
     * Method used to call the send event to controller on the protocol.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    @Override
    public void sendEventToController(EventController eventController) throws RemoteException {
        //this.responseHandlerProtocol.sendEventToController(eventController);
    }

    //------------------------------------------------------------------------------------------------------------------
    // EVENT HANDLING
    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    void sendEventToView(EventView eventView) throws RemoteException {
        getClientController().sendEventToView(eventView);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Connection closer for socket client.
     * This method close the connection of the client.
     */
    public void closeConnections(){
        try {

            inputStream.close();
            outputStream.close();
            clientConnection.close();

            System.out.println("Connection closed!");
        }catch(IOException e){
            // eccezione che dice che c'è stato un errore durante la chiusura di input/output/client
            e.printStackTrace();
        }
    }


}
