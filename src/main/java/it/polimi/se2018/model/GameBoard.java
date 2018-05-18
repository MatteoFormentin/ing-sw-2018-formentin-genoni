package it.polimi.se2018.model;


import it.polimi.se2018.model.card.Deck;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.*;

import java.util.LinkedList;


/**
 * the game board for one game
 *
 * @author Luca Genoni
 * @version 1.2 fix getDice() & getpoll removed
 * @since 1.0
 */
public class GameBoard {
    private int currentround;
    private DiceStack[] roundTrack;
    private Player[] player;
    private ToolCard[] toolCard;
    private ObjectivePublicCard[] objectivePublicCard;
    private DiceStack poolDice; // can also be a DiceStack
    private Player currentPlayer;
    private boolean endGame;
    private FactoryDice factoryDiceForThisGame;

    /**
     * constructor for the gameBoard. The preparation of the game.
     *
     * @param namePlayers      an array of String with the name
     * @param indexFirstPlayer int of the First player, his Id is set to 0
     */
    public GameBoard(String[] namePlayers, int indexFirstPlayer) {
        currentround = 0;
        roundTrack = new DiceStack[10];// don't need to be initialized, they take the reference of from the dicePool
        toolCard = new ToolCard[3];
        objectivePublicCard = new ObjectivePublicCard[3];
        factoryDiceForThisGame = new BalancedFactoryDice();// here for change the factory
        Deck deck = Deck.getDeck();
        player = new Player[namePlayers.length];

        //setUp player
        for (int i = 0; i < namePlayers.length; i++) {
            player[i] = new Player(namePlayers[((indexFirstPlayer + i) % namePlayers.length)], i);
            player[i].setPrivateObject(deck.drawObjectivePrivateCard());
            WindowPatternCard[] window = new WindowPatternCard[4];
            for (int n = 0; n < 4; n++) {
                window[n] = deck.drawWindowPatternCard();
            }
            player[i].setChoiceWindowPattern(window);
        }
        currentPlayer = player[indexFirstPlayer];
        for (int i = 0; i < toolCard.length; i++) toolCard[i] = deck.drawToolCard();
        for (int i = 0; i < objectivePublicCard.length; i++) objectivePublicCard[i] = deck.drawObjectivePublicCard();
        endGame = false;
    }

    public int getCurrentround() {
        return currentround;
    }

    public void setCurrentround(int currentround) {
        this.currentround = currentround;
    }

    public Player[] getPlayer() {
        return player;
    }

    public Player getPlayer(int index) {
        return player[index];
    }

    public void setPlayer(Player[] player) {
        this.player = player;
    }

    public DiceStack getPoolDice() {return poolDice;}

    public void setPoolDice(DiceStack poolDice) {this.poolDice = poolDice;}

    public ToolCard[] getToolCard() {
        return toolCard;
    }

    public void setToolCard(ToolCard[] toolCard) {
        this.toolCard = toolCard;
    }

    public ObjectivePublicCard[] getObjectivePublicCard() {
        return objectivePublicCard;
    }

    public void setObjectivePublicCard(ObjectivePublicCard[] objectivePublicCard) {
        this.objectivePublicCard = objectivePublicCard;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    /**
     * method for know which tool card correspond to the index 0,1 or 2
     *
     * @param indexOfTheToolInGame 0,1 or 2 the possible index of one card
     * @return the ID of the card
     */
    public int RealIdOfToolCard(int indexOfTheToolInGame) {
        if (indexOfTheToolInGame < 0 || indexOfTheToolInGame >= 3) return -1;
        else return toolCard[indexOfTheToolInGame].getId();
    }

    /**
     * method for change the status of the tool Card(maybe this should be here and not in ToolCard :/)
     * specialmente per il controllo di alcune condizioni
     */
    public ToolCard getToolCard(int indexOfTheToolInGame) {
        return toolCard[indexOfTheToolInGame];
    }

    /**
     * return the next player
     *
     * @param clockWise true if the rotation is clockwise, otherwise false
     */
    private void nextPlayer(Boolean clockWise) {
        if (clockWise) {
            int indexCurrentPlayer = currentPlayer.getId() + 1;
            if (indexCurrentPlayer == player.length)
                currentPlayer = player[0];// or currentPlayer=player[currentPlayer-player.length];
        } else {
            int indexCurrentPlayer = currentPlayer.getId() - 1;
            if (indexCurrentPlayer < 0) currentPlayer = player[player.length - 1];
        }
    }

    public boolean setWindowOfPlayer(int indexOfThePlayer, int indexOfTheWindow) {
        if (indexOfThePlayer < 0 || indexOfThePlayer >= player.length) return false;//wrong index
        if (indexOfTheWindow < 0 || indexOfTheWindow > 3) return false;//wrong index
        if (player[indexOfThePlayer].getPlayerWindowPattern() != null) return false;//window already picked
        // ok i set your window
        player[indexOfThePlayer].setPlayerWindowPattern(player[indexOfThePlayer].getChoiceWindowPattern(indexOfTheWindow));
        return true;
    }

/*
    private void doRound() {
        //init round
        poolDice = new DiceStack(factoryDiceForThisGame);
        poolDice.createDice(player.length * 2 + 1);
        for (Player aPlayer : player) {
            if (!currentPlayer.isSecondTurn()) doTurn();
            nextPlayer(true);
        }//now currentPLayer is the first player who played!
        for (Player aPlayer : player) {
            nextPlayer(false);
            if (currentPlayer.isSecondTurn()) doTurn();
        }//now currentPLayer is the last player who played!
        //clean round
        nextPlayer(true);
        roundTrack[currentround] = poolDice;
        currentround++;
    }*/

}
