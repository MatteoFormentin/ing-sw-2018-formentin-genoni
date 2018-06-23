package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
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

    //MUTEX usato per gestire un output alla volta (senza questo potrebbe crearsi congestione durante il login)
    private static final Object OUTPUT_MUTEX = new Object();

    // mappa di eventi su server, saranno poi associati ai rispettivi metodi
    private final HashMap<String, RequestHandlerInterface> eventsOnServer;

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
     * @param jsonObject client request (event).
     */
    public void handleRequest(JSONObject jsonObject) {
        JSONParser jsonParser = new JSONParser();
        try {
            Object parsedObject = jsonParser.parse(new FileReader(String.valueOf(jsonObject)));

            JSONObject jsonParsedObject = (JSONObject) parsedObject;
            System.out.println(jsonParsedObject);

            String constant = (String) jsonParsedObject.get("Constant");

            RequestHandlerInterface requestHandler = eventsOnServer.get(constant);

            if(requestHandler != null)
                requestHandler.handle();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

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
        eventsOnServer.put("login", this::login);
        eventsOnServer.put("sendEventToController", this::sendEventToController);
    }

    //------------------------------------------------------------------------------------------------------------------
    // EVENT HANDLING
    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to unleash the login method on the server.
     */
    private void login() {
        JSONParser jsonParser = new JSONParser();
        try {
            // leggo evento richiesto da server
            JSONObject jsonObject = (JSONObject) protocolInputStream.readObject();
            Object parsedObject = jsonParser.parse(new FileReader(String.valueOf(jsonObject)));

            JSONObject jsonParsedObject = (JSONObject) parsedObject;
            System.out.println(jsonParsedObject);

            String nickname = (String) jsonParsedObject.get("Nickname");

            // mando l'evento al socketplayer che lo scatenerà
            this.socketPlayer.login(nickname);

        } catch (ParseException | PlayerAlreadyLoggedException | RoomIsFullException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // TODO GESTISCO ECCEZIONI
    }

    /**
     * Method used to unleash the sendEventToController on the server.
     */
    private void sendEventToController() {
        JSONParser jsonParser = new JSONParser();
        try {
            // leggo evento richiesto da server
            JSONObject jsonObject = (JSONObject) protocolInputStream.readObject();
            Object parsedObject = jsonParser.parse(new FileReader(String.valueOf(jsonObject)));

            JSONObject jsonParsedObject = (JSONObject) parsedObject;
            System.out.println(jsonParsedObject);

            EventController eventController = (EventController) jsonParsedObject.get("Event");

            // mando l'evento al socketplayer che lo scatenerà
            this.socketPlayer.sendEventToController(eventController);

        } catch (ParseException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // gestisco eccezioni??
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    public void sendEventToView(EventView eventView) {
        synchronized (OUTPUT_MUTEX) {
            try {
                JSONObject event = new JSONObject();
                event.put("Constant", "sendEventToView");
                event.put("Event", eventView);

                protocolOutputStream.writeObject(event.toJSONString());
                protocolOutputStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
            // gestisco eccezioni??
            // synchronized per gli output
            // mando evento al client
        }
    }
}
