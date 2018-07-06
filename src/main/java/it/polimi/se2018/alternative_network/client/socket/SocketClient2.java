package it.polimi.se2018.alternative_network.client.socket;

import it.polimi.se2018.alternative_network.client.AbstractClient2;
import it.polimi.se2018.list_event.event_received_by_server.EventServer;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.AskLogin;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.ConnectionDown;
import it.polimi.se2018.view.UIInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the Socket client for every single player.
 * Extends AbstractClient class in order to implement and facilitate Client connection.
 *
 * @author DavideMammarella
 */
public class SocketClient2 extends AbstractClient2 implements Runnable {

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
     * @param ip_host client interface, used as controller to communicate with the client.
     * @param port    server address.
     * @param view    port used from server to communicate.
     */
    public SocketClient2(String ip_host, int port, UIInterface view) {
        this.ip_host = ip_host;
        this.port = port;
        this.view = view;
    }

    //------------------------------------------------------------------------------------------------------------------
    // CONNECTION
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to establish a connection with the Server.
     */


    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to write on the output stream the object that will be sent to the server.
     *
     * @param eventServer object that will be traduced on the server (it contain an event).
     */
    public void sendObject(EventServer eventServer) {
        try {
            outputStream.writeObject(eventServer);
            outputStream.reset();
        } catch (IOException ex) {

            view.errPrintln(ex.getMessage());
            ex.printStackTrace();
        }
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

            ConnectionDown packet = new ConnectionDown("Sei stato disconnesso dal server.", true);
            sendEventToView(packet);

            ex.printStackTrace();
        }
    }

    /**
     * Method used to call the send event to controller on the protocol.
     *
     * @param eventServer object that will use the server to unleash the event associated.
     */
    @Override
    public void sendEventToController2(EventServer eventServer) {
        sendObject(eventServer);
    }

    @Override
    public void connectToServer2() {
        try {
            clientConnection = new Socket(ip_host, port);
            outputStream = new ObjectOutputStream(clientConnection.getOutputStream());
            inputStream = new ObjectInputStream(clientConnection.getInputStream());
            outputStream.flush();
            (new Thread(this)).start();
            AskLogin packet = new AskLogin();
            sendEventToView(packet);
        } catch (IOException ex) {
            ex.printStackTrace();
            ConnectionDown packet = new ConnectionDown("Controlla ip e porta.", false);
            sendEventToView(packet);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to send to the client an update of the game.
     *
     * @param eventClient object that will use the client to unleash the update associated.
     */
    void sendEventToView(EventClient eventClient) {
        view.showEventView(eventClient);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SOCKET OBJECT HANDLER
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
                EventClient received = (EventClient) inputStream.readObject();
                sendEventToView(received);
            } catch (IOException | ClassNotFoundException ex) {
                // ex.printStackTrace();
                flag = false;

                ConnectionDown packet = new ConnectionDown("Sei stato disconnesso dal server. Controlla la connessione.", false);
                sendEventToView(packet);
            }
        }
        shutDownClient2();
    }
}