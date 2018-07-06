package it.polimi.se2018.view;

import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;

public interface UIInterface {

    //From model and controller
    void showEventView(EventClient eventClient);

    void sendEventToNetwork(EventController eventController);

    void restartConnection(String message);

    void errPrintln(String message);

    void loginOk();
}
