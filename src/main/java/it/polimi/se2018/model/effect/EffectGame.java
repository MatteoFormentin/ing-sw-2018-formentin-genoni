package it.polimi.se2018.model.effect;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.model.GameBoard;

public abstract class EffectGame {

    EffectGame(){

    }
    public abstract void doEffect(GameBoard gameBoard,int idPlayer) throws GameException;
    public abstract void undo(GameBoard gameBoard,int idPlayer) throws GameException;
    public abstract EventView askTheViewTheInfo();
}
