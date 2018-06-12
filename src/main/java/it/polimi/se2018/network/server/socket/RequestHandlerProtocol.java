package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Class that define the request handler for client request.
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
        this.protocolOutputStream.flush();

        eventsOnServer=new HashMap<>();
        this.associateEventsToMethods();
    }


    //------------------------------------------------------------------------------------------------------------------
    // REQUEST HANDLER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Handler for the client request.
     * CLIENT REQUEST (EVENT) -> handleRequest -> METHOD INVOCATION (OF THE METHOD ASSOCIATED TO THE EVENT ON THE eventOnServer)
     *
     * @param object client request (event).
     */
    public void handleRequest(Object object) {
        RequestHandlerInterface requestHandler = eventsOnServer.get(object);
        if(requestHandler != null)
            requestHandler.Handle();
    }

    /**
     * Interface used to handle every client request.
     */
    @FunctionalInterface
    private interface RequestHandlerInterface{
        void Handle();
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
        // leggo nickname da input
        // chiamo il login su server
        // gestisco eccezioni??
    }

    private void sendEventToController(EventController eventController) {
        // leggo evento richiesto da server
        // scateno l'evento
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
