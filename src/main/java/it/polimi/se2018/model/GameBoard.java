package it.polimi.se2018.model;


import it.polimi.se2018.model.card.objectivePublicCard.ObjectivePublicCard;
import it.polimi.se2018.model.card.toolCard.ToolCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.roundTrack.RoundTrack;

public class GameBoard {
    private int round;
    private int turn;
    private RoundTrack[] roundTrack;
    private Player[] player;
    private ToolCard[] toolCard;
    private ObjectivePublicCard[] objectivePublicCard;
    private Dice[] poolDice;
    private Player currentPlayer;

    /*
    ** Method
    */
    //costruttore di gameboard gli attributi che non variano in base al numero giocatori,
    public GameBoard() {
        turn =1;
        toolCard = new ToolCard[3];
        objectivePublicCard = new ObjectivePublicCard[3];
        roundTrack = new RoundTrack[10];
    }
    public void setupGame() {

    }

    public void getRoundPoolDice() {

    }

    public void insertDice() {

    }

    public void useToolCard() {

    }

    public void endTurn() {

    }


}
