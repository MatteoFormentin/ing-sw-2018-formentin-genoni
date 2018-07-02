package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.alternative_network.client.AbstractClient2;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.SocketObject;
import it.polimi.se2018.network.client.AbstractClient;
import it.polimi.se2018.network.client.ClientController;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the Socket client for every single player.
 * Extends AbstractClient class in order to implement and facilitate Client connection.
 *
 * @author DavideMammarella
 */
public class SocketClient extends AbstractClient implements Runnable,AbstractClient2 {

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
     * @param clientController client interface, used as controller to communicate with the client.
     * @param serverIpAddress  server address.
     * @param serverPort       port used from server to communicate.
     */
    public SocketClient(ClientController clientController, String serverIpAddress, int serverPort) {
        super(clientController, serverIpAddress, serverPort);
        AnsiConsole.out.println(ansi().fg(YELLOW).a("SERVER IP in client:"+getServerIpAddress()).reset());
        AnsiConsole.out.println(ansi().fg(YELLOW).a("SERVER PORT in client :"+getServerPort()).reset());
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
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to write on the output stream the object that will be sent to the server.
     *
     * @param socketObject object that will be traduced on the server (it contain an event).
     */
    public void sendObject(SocketObject socketObject) {
        try {
            outputStream.writeObject(socketObject);
        } catch (IOException ex) {
            getView().errPrintln(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Method used to call the login event.
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
            getView().errPrintln(ex.getMessage());
            ex.printStackTrace();
        }

    }

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

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    void sendEventToView(EventView eventView){
        getClientController().sendEventToView(eventView);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SOCKET OBJECT HANDLER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to traduce the object received from the server.
     *
     * @param socketObject object that will use the client to unleash the event associated.
     */
    private void socketObjectTraducer(SocketObject socketObject) throws  SocketException{
        String type = socketObject.getType();
        if (type.equals("Event")) {
            //try {
                sendEventToView((EventView) socketObject.getObject());
            /*} catch (SocketException ex) {
                //TODO socket non lancia RemoteException!!! sistemare le interfaccie
                getView().errPrintln(ex.getMessage());
                ex.printStackTrace();
            }*/
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
            } catch (IOException | ClassNotFoundException ex) {
                flag = false;
                getView().errPrintln(ex.getMessage());
                System.err.println("Sei stato disconnesso dal server. Controlla la connessione.");
                ex.printStackTrace();
            }
        }
        closeConnection();
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to disconnect a client from the server.
     */
    @Override
    public void disconnect(){

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
        } catch (IOException ex) {
            getView().errPrintln(ex.getMessage());
            // eccezione che dice che c'è stato un errore durante la chiusura di input/output/client
            ex.printStackTrace();
        }
    }

    /*************************************************newInterface**********************************/
    /*************************************************newInterface**********************************/
    /*************************************************newInterface**********************************/
    @Override
    public void sendEventToUIInterface2(EventView event) {
        getView().showMessage(event);
    }

    @Override
    public void shutDownClient2() {
        closeConnection();
    }

    @Override
    public void sendEventToController2(EventController eventController) throws ConnectionProblemException {

    }

    @Override
    public void login2(String nickname) throws ConnectionProblemException, PlayerAlreadyLoggedException, RoomIsFullException {
        login(nickname);
    }

    @Override
    public void connectToServer2() throws ConnectionProblemException {
        try {
            connectToServer();
        }catch(Exception ex){
            getView().errPrintln(ex.getMessage());
            throw new ConnectionProblemException("Socket Cannot Start");
        }
    }
}
