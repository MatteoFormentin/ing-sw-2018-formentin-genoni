package it.polimi.se2018.controller;

import it.polimi.se2018.event.list_event.EventView;
import it.polimi.se2018.event.list_event.Login;

public class Controller {
    /**
     * this method is the function that processes me, and recognizes the event both in the case of an RMI and socket
     *
     * @param eventView
     */
    public void newEvent(EventView eventView){
        //identifica ed elabora questo evento
        if (eventView instanceof Login) // fai il login se la gameboard non è 0 fai relogin è piena error
        eventView.getModel(); // se esiste ok se no message error

        eventView.getNicknamPlayer(); // se è il suo turno se no message error
        //
        //la ca
    }
}
