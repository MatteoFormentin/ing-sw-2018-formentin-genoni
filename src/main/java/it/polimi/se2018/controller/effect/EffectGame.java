package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.model.GameBoard;

public abstract class EffectGame {
    private GameBoard gameBoard;
    private int idPlayer;

    public abstract void doEffect(GameBoard gameBoard, int idPlayer,int[] infoMove) throws GameException;
    public abstract void undo() throws GameException;
    public abstract EventView askTheViewTheInfo();


    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
