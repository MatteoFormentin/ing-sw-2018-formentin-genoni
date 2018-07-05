package it.polimi.se2018.view;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;

public interface UIInterface {

    //From model and controller
    void showEventView(EventView eventView);
    void sendEventToNetwork(EventController eventController);

    //TODO da tradurre come EventController
    void loginOk();
    void restartConnection(String cause);
    void errPrintln(String error);

}
