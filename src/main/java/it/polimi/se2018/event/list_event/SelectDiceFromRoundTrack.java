package it.polimi.se2018.event.list_event;

/**
 * Extends Event, describe the event "select dice from the round track" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectDiceFromRoundTrack extends Event  {
    int round;
    int index;
}
