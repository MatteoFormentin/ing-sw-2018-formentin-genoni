package it.polimi.se2018.model;

/**
 * for the comunication mvc have only the public method for update to the view
 */
//maybe virtualview? for less class? don't know *_*
public class Model {
    private GameBoard gameboard;
    public Model(GameBoard gameboard){
        this.gameboard=gameboard;
    }

}
