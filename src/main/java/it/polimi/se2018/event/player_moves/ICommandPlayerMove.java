package it.polimi.se2018.event.player_moves;

import it.polimi.se2018.model.GameBoard;

public interface ICommandPlayerMove {
    public boolean canPerform(GameBoard gameBoard);
    public void doMove(GameBoard gameBoard);
}
