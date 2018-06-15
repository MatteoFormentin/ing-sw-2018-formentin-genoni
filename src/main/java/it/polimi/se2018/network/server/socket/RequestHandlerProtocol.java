package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Class that define the request handler protocol for client request.
 *
 * @author DavideMammarella
 */
public class RequestHandlerProtocol{

    // giocatore remoto
    private SocketPlayer socketPlayer;

    // stream di input
    private final ObjectInputStream protocolInputStream;
    // stream di output
    private final ObjectOutputStream protocolOutputStream;

    // mappa di eventi su server, saranno poi associati ai rispettivi metodi
    private final HashMap<Object, RequestHandlerInterface> eventsOnServer;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Request Handler Protocol constructor.
     *
     * @param socketPlayer remote player associated to a client.
     * @param inputStream input stream, used to receive object.
     * @param outputStream output stream, used to send object.
     */
    public RequestHandlerProtocol(SocketPlayer socketPlayer, ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException {
        this.socketPlayer=socketPlayer;

        this.protocolInputStream = inputStream;
        this.protocolOutputStream = outputStream;

        eventsOnServer=new HashMap<>();
        this.associateEventsToMethods();
    }


    //------------------------------------------------------------------------------------------------------------------
    // REQUEST HANDLER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Handler for the client request.
     * CLIENT REQUEST (EVENT) -> handleRequest -> METHOD INVOCATION (OF THE METHOD ASSOCIATED TO THE EVENT ON THE eventsOnServer)
     *
     * @param object client request (event).
     */
    public void handleRequest(Object object) {
        RequestHandlerInterface requestHandler = eventsOnServer.get(object);
        if(requestHandler != null)
            requestHandler.handle();
    }

    /**
     * Interface used to handle every client request.
     */
    @FunctionalInterface
    private interface RequestHandlerInterface{
        void handle();
    }

    //------------------------------------------------------------------------------------------------------------------
    // EVENT LOADER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to connect every event that can be requested from client to the respective method on the server.
     */
    private void associateEventsToMethods() {
        // login
        // sendEventToController
    }

    //------------------------------------------------------------------------------------------------------------------
    // EVENT HANDLING
    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    private void login() {
        try {
            // leggo nickname con inputStream
            String nickname = (String) protocolInputStream.readObject();
            // chiamo il login su server
            this.socketPlayer.login(nickname);
        } catch (PlayerAlreadyLoggedException | RoomIsFullException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // TODO GESTISCO ECCEZIONI
    }

    private void sendEventToController() {
        try {
            // leggo evento richiesto da server
            EventController eventController = (EventController) protocolInputStream.readObject();
            // mando l'evento al socketplayer che lo scatenerà
            this.socketPlayer.sendEventToController(eventController);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // gestisco eccezioni??
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    public void sendEventToView(EventView eventView) {
        // synchronized per gli output
        // mando evento al client
    }
}
