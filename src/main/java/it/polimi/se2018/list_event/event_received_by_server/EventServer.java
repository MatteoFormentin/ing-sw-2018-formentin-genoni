package it.polimi.se2018.list_event.event_received_by_server;

import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;

import java.io.Serializable;

public abstract class EventServer implements Serializable {

    public abstract void acceptGeneric(ServerVisitor visitor);

}