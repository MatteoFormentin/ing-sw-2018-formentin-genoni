package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Class that define the response handler protocol for server response.
 *
 * @author DavideMammarella
 */
public class ResponseHandlerProtocol {

    // comunicazione con il socket client
    private SocketClient socketClient;

    // stream di input
    private final ObjectInputStream protocolInputStream;
    // stream di output
    private final ObjectOutputStream protocolOutputStream;

    // mappa di eventi su client, saranno poi associati ai rispettivi metodi
    private final HashMap<Object, ResponseHandlerInterface> eventsOnClient;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Response Handler Protocol constructor.
     *
     * @param socketClient socket client that represent a user.
     * @param inputStream input stream, used to receive object.
     * @param outputStream output stream, used to send object.
     */
    public ResponseHandlerProtocol(SocketClient socketClient, ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException {
        this.socketClient=socketClient;

        this.protocolInputStream = inputStream;
        this.protocolOutputStream = outputStream;

        eventsOnClient=new HashMap<>();
        this.associateEventsToMethods();
    }


    //------------------------------------------------------------------------------------------------------------------
    // REQUEST HANDLER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Handler for the server response.
     * SERVER RESPONSE (EVENT) -> handleResponse -> METHOD INVOCATION (OF THE METHOD ASSOCIATED TO THE EVENT ON THE eventsOnClient)
     *
     * @param object server response (event).
     */
    public void handleResponse(Object object) {
        ResponseHandlerInterface responseHandler = eventsOnClient.get(object);
        if(responseHandler != null)
            responseHandler.handle();
    }

    /**
     * Interface used to handle every server response.
     */
    @FunctionalInterface
    private interface ResponseHandlerInterface{
        void handle();
    }

    //------------------------------------------------------------------------------------------------------------------
    // EVENT LOADER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to connect every event that can be responded from server to the respective method on the client.
     */
    private void associateEventsToMethods() {
        // sendEventToView
    }

    //------------------------------------------------------------------------------------------------------------------
    // EVENT HANDLING
    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    private void login(String nickname) {
        // comunico al server il nickname
        // faccio il writeObject del login
        // gestisco eccezioni??
    }

    private void sendEventToController(EventController eventController) {
        // comunico al server l'evento richiesto
        // faccio il writeObject dell'evento
        // gestisco eccezioni??
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to call the send event to view on the socket client.
     */
    public void sendEventToView() throws RemoteException {
        try {
            EventView eventView = (EventView) protocolInputStream.readObject();
            this.socketClient.sendEventToView(eventView);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
