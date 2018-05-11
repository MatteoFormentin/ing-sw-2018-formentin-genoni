package it.polimi.se2018.model.player_moves;

import it.polimi.se2018.model.GameBoard;

public class CommandUseToolCard implements ICommandPlayerMove {
    public boolean canPerform(GameBoard gameBoard){
        return false;
    }
    public void doMove(GameBoard gameBoard){

    }
}
