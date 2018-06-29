package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.SocketObject;
import it.polimi.se2018.network.server.ServerController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Class that extends the RemotePlayer in order to define a Socket Player.
 * This class will be used to manage every socket player as a thread.
 *
 * @author DavideMammarella
 */
public class SocketPlayer extends RemotePlayer implements Runnable {

    // comunicazione con il server
    private final ServerController serverController;
    private Socket tunnel;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;


    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Socket Player Constructor.
     *
     * @param serverController server interface, used as controller to communicate with the server.
     * @throws IOException
     */
    public SocketPlayer(ServerController serverController, Socket connection) throws IOException {
        this.serverController = serverController;
        playerRunning = true;

        this.tunnel = connection;

        try {
            this.outputStream = new ObjectOutputStream(tunnel.getOutputStream());
            this.inputStream = new ObjectInputStream(tunnel.getInputStream());
            this.outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void run() {
        Thread.currentThread().setName("Socket Player: " + getNickname() + " Thread");
        boolean flag = true;
        while (flag && tunnel.isConnected()) {
            try {
                SocketObject received = (SocketObject) inputStream.readObject();

                socketObjectTraducer(received);

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                flag = false;
            }
        }
        closeConnection();
    }

    public void sendObject(SocketObject socketObject) {
        try {
            outputStream.writeObject(socketObject);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void socketObjectTraducer(SocketObject socketObject) {
        String type = socketObject.getType();

        if (type.equals("Login")) {
            try {
                login(socketObject.getStringField());
                sendAck();
                System.out.println("LOGIN OK VIA SOCKET");

            } catch (PlayerAlreadyLoggedException ex) {
                System.out.println("LOGIN NO VIA SOCKET");
                sendNack();
            } catch (RoomIsFullException ex) {
                System.out.println("LOGIN NO VIA SOCKET");
                sendNack();
            }
        }

        if (type.equals("Event")) {
            sendEventToController((EventController) socketObject.getObject());
        }

    }

    public void sendAck() {
        SocketObject packet = new SocketObject();
        packet.setType("Ack");
        sendObject(packet);
    }

    public void sendNack() {
        SocketObject packet = new SocketObject();
        packet.setType("Nack");
        sendObject(packet);
    }

    /**
     * Remote method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToView(EventView eventView) throws RemoteException {
        SocketObject packet = new SocketObject();
        packet.setType("Event");
        packet.setObject(eventView);
        sendObject(packet);
    }

    @Override
    public void ping() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public String sayHelloClient() throws RemoteException {
        return null;
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to log the user to the server with his nickname.
     *
     * @param nickname name of the player.
     */
    public void login(String nickname) throws PlayerAlreadyLoggedException, RoomIsFullException {
        setNickname(nickname);
        if (!this.serverController.login(this)) {
            throw new PlayerAlreadyLoggedException("error");
        }
    }

    /**
     * Method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    public void sendEventToController(EventController eventController) {
        this.serverController.sendEventToController(eventController);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Connection closer for socket player.
     * This method close the connection of the client and tell it to the remote player.
     */
    public void closeConnection() {
        try {
            tunnel.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
