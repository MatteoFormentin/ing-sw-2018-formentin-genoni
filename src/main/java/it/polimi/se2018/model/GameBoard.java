package it.polimi.se2018.model;


import it.polimi.se2018.model.card.Deck;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceFactory;
import it.polimi.se2018.model.dice.DiceStack;

public class GameBoard {
    private int round;
    private DiceStack[] roundTrack;
    private Player[] player;
    private final ToolCard[] toolCard;
    private final ObjectivePublicCard[] objectivePublicCard;
    private DiceStack poolDice; // can also be a DiceStack
    private int currentPlayer;

    /*
    ** Method
    */
    //costruttore di gameboard gli attributi che non variano in base al numero giocatori,

    /**
     * /**
     * Method <strong>GameBoard</strong>
     * <em>Description</em>: constructor for the gameBoard. The preparation of the game
     *
     * @param arrayPlayer to memorize the reference of the players
     * @param indexFirstPlayer the index to the fristplayer
     */
    public GameBoard(Player[] arrayPlayer, int indexFirstPlayer) {
        round =1;
        roundTrack = new DiceStack[10];
        player= arrayPlayer;
        toolCard = new ToolCard[3];
        objectivePublicCard = new ObjectivePublicCard[3];
        currentPlayer =indexFirstPlayer;

        //initialization of card arrays
        Deck deck = new Deck();
        for(int i=0;i<toolCard.length;i++){
            toolCard[i]= deck.drawToolCard();
        }
        for(int i=0;i<toolCard.length;i++){
            objectivePublicCard[i]= deck.drawObjectivePublicCard();
        }
    }
    /**
     * /**
     * Method <strong>doRound</strong>
     * <em>Description</em>: all the logic to handle a round
     */
    public void doRound() {
        poolDice= new DiceStack(DiceFactory.getPoolDice(player.length));
        for(int i=0;i<player.length;i++){
            player[currentPlayer].doTurn();
            currentPlayer= player[currentPlayer];
        }
        for(int i = player.length-1; i>=0; i--){
            if(player[currentPlayer].isSecondTurn()) player[currentPlayer].doTurn();//doturn deve cambiare il vaore di sendond turn, basta negazione
            currentPlayer= player[++i];
        }

        round++;
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
