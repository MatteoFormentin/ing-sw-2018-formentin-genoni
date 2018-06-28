package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.SocketObject;
import it.polimi.se2018.network.client.AbstractClient;
import it.polimi.se2018.network.client.ClientController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the Socket client for every single player.
 * Extends AbstractClient class in order to implement and facilitate Client connection.
 *
 * @author DavideMammarella
 */
public class SocketClient extends AbstractClient implements Runnable {

    // comunicazione con il socket
    private Socket clientConnection;

    // stream di input
    private ObjectInputStream inputStream;
    // stream di output
    private ObjectOutputStream outputStream;

    // protocollo che verrà usato per gestire le risposte da server

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
    public void connectToServer() throws UnknownHostException, IOException {
        clientConnection = new Socket(getServerIpAddress(), getServerPort());

        outputStream = new ObjectOutputStream(clientConnection.getOutputStream());
        inputStream = new ObjectInputStream(clientConnection.getInputStream());
        outputStream.flush();
    }

    //------------------------------------------------------------------------------------------------------------------
    // RESPONSE LISTENER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for the server response listener thread.
     */


    /**
     * Class that define the listener for the server response.
     * This class will start a thread that will be on hold for a message from server, and will manage it with the protocol.
     */

    public void sendObject(SocketObject socketObject) {
        try {
            outputStream.writeObject(socketObject);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void socketObjectTraducer(SocketObject socketObject) {
        String type = socketObject.getType();
        if (type.equals("Event")) {
            try {
                sendEventToView((EventView) socketObject.getObject());
            } catch (RemoteException ex) {
                //TODO socket non lancia RemoteException!!! sistemare le interfaccie
                ex.printStackTrace();
            }
        }
    }

    /**
     * Method used to call the login event on the protocol.
     *
     * @param nickname name of the player associated to the client.
     */
    @Override
    public void login(String nickname) throws PlayerAlreadyLoggedException {
        SocketObject packet = new SocketObject();
        packet.setType("Login");
        packet.setStringField(nickname);
        sendObject(packet);


        try {
            SocketObject socketObject = (SocketObject) inputStream.readObject();


            if (socketObject.getType().equals("Ack")) {
                (new Thread(this)).start();
            }

            if (socketObject.getType().equals("Nack")) {
                throw new PlayerAlreadyLoggedException("error");
            }

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }


    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER (PROTOCOL CALL ONLY)
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to call the send event to controller on the protocol.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    @Override
    public void sendEventToController(EventController eventController) throws RemoteException {
        SocketObject packet = new SocketObject();
        packet.setType("Event");
        packet.setObject(eventController);
        sendObject(packet);
    }

    /**
     * Connection closer for socket client.
     * This method close the connection of the client.
     */
    public void closeConnection() {
        try {

            inputStream.close();
            outputStream.close();
            clientConnection.close();

            System.out.println("Connection closed!");
        } catch (IOException e) {
            // eccezione che dice che c'è stato un errore durante la chiusura di input/output/client
            e.printStackTrace();
        }
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
     * Runner for Response Listener thread.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                SocketObject received = (SocketObject) inputStream.readObject();

                socketObjectTraducer(received);

            } catch (IOException | ClassNotFoundException ex) {
                flag = false;
                ex.printStackTrace();
            }
        }
        closeConnection();
    }


}
