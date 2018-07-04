package it.polimi.se2018.view;

import it.polimi.se2018.list_event.event_received_by_view.EventView;

public interface UIInterface {

    //From model and controller
    public void showMessage(EventView eventView);
    public void restartConnectionDuringGame(String cause);
    public void errPrintln(String error);
}
