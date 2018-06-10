package it.polimi.se2018.model.effect;

import it.polimi.se2018.model.GameBoard;

public abstract class EffectGame {
    GameBoard gameBoard;
    int idPlayer;

    public EffectGame(GameBoard gameBoard, int idPlayer) {
        this.gameBoard = gameBoard;
        this.idPlayer = idPlayer;
    }

    public abstract void doEffect() throws Exception;
    public abstract void undo();
}
