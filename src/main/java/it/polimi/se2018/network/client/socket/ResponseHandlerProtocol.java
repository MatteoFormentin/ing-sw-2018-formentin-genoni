package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
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
    private final HashMap<String, ResponseHandlerInterface> eventsOnClient;

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
     * @param jsonObject server response (event).
     */
    public void handleResponse(JSONObject jsonObject) throws RemoteException {
        JSONParser jsonParser = new JSONParser();
        try {
            Object parsedObject = jsonParser.parse(new FileReader(String.valueOf(jsonObject)));

            JSONObject jsonParsedObject = (JSONObject) parsedObject;
            System.out.println(jsonParsedObject);

            String constant = (String) jsonParsedObject.get("Constant");

            ResponseHandlerInterface responseHandler = eventsOnClient.get(constant);

            if(responseHandler != null)
                responseHandler.handle();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
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
        eventsOnClient.put("sendEventToView", this::sendEventToView);
    }

    //------------------------------------------------------------------------------------------------------------------
    // EVENT HANDLING
    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to log the user to the server with his nickname.
     *
     * @param nickname   name of the player.
     */
    public void login(String nickname) {
        try {
        JSONObject loginJson = new JSONObject();
        loginJson.put("Constant", "login");
        loginJson.put("Nickname", nickname);

        protocolOutputStream.writeObject(loginJson.toJSONString());
        protocolOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // gestisco eccezioni??
    }

    /**
     * Method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    public void sendEventToController(EventController eventController) {
        try {
            JSONObject event = new JSONObject();
            event.put("Constant", "sendEventToController");
            event.put("Event", eventController);

            protocolOutputStream.writeObject(event.toJSONString());
            protocolOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // gestisco eccezioni??
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to unleash the sendEventToView on the client.
     */
    public void sendEventToView(){
            JSONParser jsonParser = new JSONParser();
            try {

                JSONObject jsonObject = (JSONObject) protocolInputStream.readObject();
                Object parsedObject = jsonParser.parse(new FileReader(String.valueOf(jsonObject)));

                JSONObject jsonParsedObject = (JSONObject) parsedObject;
                System.out.println(jsonParsedObject);

                EventView eventView = (EventView) jsonParsedObject.get("Event");

                this.socketClient.sendEventToView(eventView);

            } catch (ParseException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

}
