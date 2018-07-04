package it.polimi.se2018.alternative_network.client.socket;

import it.polimi.se2018.alternative_network.client.AbstractClient2;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.SocketObject;
import it.polimi.se2018.view.UIInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the Socket client for every single player.
 * Extends AbstractClient class in order to implement and facilitate Client connection.
 *
 * @author DavideMammarella
 */
public class SocketClient2 extends  AbstractClient2 implements Runnable {

    // comunicazione con il socket
    private Socket clientConnection;

    // stream di input
    private ObjectInputStream inputStream;
    // stream di output
    private ObjectOutputStream outputStream;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Socket Client constructor.
     *
     * @param view            client interface, used as controller to communicate with the client.
     * @param serverIpAddress server address.
     * @param serverPort      port used from server to communicate.
     */
    public SocketClient2(String serverIpAddress, int serverPort, UIInterface view) {
       super(serverIpAddress,serverPort,view);
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to write on the output stream the object that will be sent to the server.
     *
     * @param socketObject object that will be traduced on the server (it contain an event).
     */
    private void sendObject(SocketObject socketObject) throws ConnectionProblemException {
        try {
            outputStream.writeObject(socketObject);
            outputStream.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ConnectionProblemException("errore stream");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // SOCKET OBJECT HANDLER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to traduce the object received from the server.
     *
     * @param socketObject object that will use the client to unleash the event associated.
     */
    private void socketObjectTraducer(SocketObject socketObject) throws SocketException {
        String type = socketObject.getType();
        if (type.equals("Event")) {
            sendEventToUIInterface2((EventView) socketObject.getObject());
        }

        if (type.equals("Ping")) {
            SocketObject packet = new SocketObject();
            packet.setType("Pong");
            try {
                sendObject(packet);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // RESPONSE LISTENER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Runner for Response Listener thread.
     * Method that define the listener for the server response.
     * This method will start a thread that will be on hold for a message from server, and will manage it with the protocol.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        Thread.currentThread().setName("Socket Client Thread");
        boolean flag = true;
        while (flag) {
            try {
                SocketObject received = (SocketObject) inputStream.readObject();
                socketObjectTraducer(received);
            } catch (Exception ex) {
                flag = false;
                System.err.println("Sei stato disconnesso dal server. Controlla la connessione.");
                ex.printStackTrace();
            }
        }
        System.err.println("client off");
        shutDownClient2();
    }

    /**
     * Method used to send to the client an update of the game.
     *
     * @param event object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToUIInterface2(EventView event) {
        view.showEventView(event);
    }


    /**
     * Connection closer for socket client.
     * This method close the connection of the client.
     */
    @Override
    public void shutDownClient2() {
        try {
            inputStream.close();
            outputStream.close();
            clientConnection.close();
            System.out.println("Connection closed!");
        } catch (IOException ex) {
            view.errPrintln(ex.getMessage());
            // eccezione che dice che c'è stato un errore durante la chiusura di input/output/client
            ex.printStackTrace();
        }
    }


    /**
     * Method used to call the send event to controller on the protocol.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    @Override
    public void sendEventToController2(EventController eventController) throws ConnectionProblemException {
        SocketObject packet = new SocketObject();
        packet.setType("Event");
        packet.setObject(eventController);
        sendObject(packet);
    }


    /**
     * Method used to call the login event.
     *
     * @param nickname name of the player associated to the client.
     */
    @Override
    public void login2(String nickname) throws ConnectionProblemException, PlayerAlreadyLoggedException, RoomIsFullException {
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
            throw new ConnectionProblemException("error");

        }
    }

    /**
     * Method used to establish a connection with the Server.
     */
    @Override
    public void connectToServer2() throws ConnectionProblemException {
        try {
            clientConnection = new Socket(ip_host,port);
            outputStream = new ObjectOutputStream(clientConnection.getOutputStream());
            inputStream = new ObjectInputStream(clientConnection.getInputStream());
            outputStream.flush();
        } catch (Exception ex) {
            view.errPrintln(ex.getMessage());
            throw new ConnectionProblemException("Socket Cannot Start");
        }
    }
}
