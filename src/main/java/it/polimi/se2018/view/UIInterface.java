package it.polimi.se2018.view;

import it.polimi.se2018.list_event.event_received_by_view.EventView;

public interface UIInterface {

    //From model and controller
    public void showEventView(EventView eventView);
    public void restartConnection(String cause);
    public void errPrintln(String error);
}
