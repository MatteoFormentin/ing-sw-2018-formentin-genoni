package it.polimi.se2018.alternative_network.client.socket;

import it.polimi.se2018.alternative_network.client.AbstractClient2;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.list_event.event_received_by_server.EventServer;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.network.SocketObject;
import it.polimi.se2018.view.UIInterface;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the Socket client for every single player.
 * Extends AbstractClient class in order to implement and facilitate Client connection.
 *
 * @author DavideMammarella
 */
public class SocketClient2 extends AbstractClient2 {

    // comunicazione con il socket
    private ServerSocketInterface server;

    private AtomicBoolean stayAlive;

    public SocketClient2(String ip_host, int port, UIInterface view, ServerSocketInterface server) {
        this.ip_host = ip_host;
        this.port = port;
        this.view = view;
        this.server = server;
    }
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
        this.ip_host = serverIpAddress;
        this.port = serverPort;
        this.view = view;
        stayAlive.set(true);
    }

    public void setView(UIInterface view) {
        this.view = view;
    }

    /**
     * Method used to send to the client an update of the game.
     *
     * @param event object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToUIInterface2(EventClient event) {
        view.showEventView(event);
    }


    /**
     * Connection closer for socket client.
     * This method close the connection of the client.
     */
    @Override
    public void shutDownClient2() {
        //TODO mettere lo shutDown volontario
    }

    @Override
    public void sendEventToController2(EventServer eventController) {
        SocketObject packet = new SocketObject();
        packet.setType("Event");
        packet.setObject(eventController);
        //TODO send object
    }



    /**
     * Method used to call the login event.
     *
     * @param nickname name of the player associated to the client.
     */
    //TODO quando riceve l'evento deve far partire il network handler e b
    public void login2(String nickname) {
        server.start();
        //TODO il codice sotto è da mettere nel caso in cui non si riesca a mantenere viva la cli,
        // TODO è stato in serito per colpa della
     /*   stayAlive.set(true);
        SocketObject packet = new SocketObject();
        packet.setType("EventPreGame");
        packet.setStringField(nickname);
        server.send(packet);
        //dopo l'invio del login mi metto in attesa di pacchetti
        while (stayAlive.get()) {
            //TODO dare qualche spazio di tempo con un altro ciclo while
            try {
                Thread.sleep(100);
                if (!eventControllers.isEmpty()) {
                    server.send(eventControllers.getFirst());
                    eventControllers.removeFirst();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }*/
        System.err.println("client off");
    }

    /**
     * Method used to establish a connection with the Server.
     */
    @Override
    public void connectToServer2(){
        //TODO build the netork Handler
        server = new NetworkHandler(ip_host, port, view);
        //TODO send event if can't construct
    }
}
