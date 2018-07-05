package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.model.GameBoard;

import java.io.Serializable;

public abstract class EffectGame implements Serializable {
    private GameBoard gameBoard;
    private int idPlayer;

    public abstract void doEffect(GameBoard gameBoard, int idPlayer,int[] infoMove) throws GameException;
    public abstract void undo() throws GameException;
    public abstract EventClient eventViewToAsk();

    GameBoard getGameBoard() {
        return gameBoard;
    }

    void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    int getIdPlayer() {
        return idPlayer;
    }

    void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
