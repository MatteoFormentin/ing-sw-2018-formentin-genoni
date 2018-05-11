package it.polimi.se2018.event.list_event;

import it.polimi.se2018.model.GameBoard;

import java.io.Serializable;

/**
 * Abstract void class for the Event in a game, implements Serializable.
 * Every Event is produced by a player and belong to a specific game board
 *
 * @author Luca Genoni
 */
public abstract class Event implements Serializable {
    //the int or the name of the player who send the Event
    private String nickname;
    //private GameBoard gameboard or private int gameboard for multiple gameboard
}
