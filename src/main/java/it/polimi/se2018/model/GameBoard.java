package it.polimi.se2018.model;


import it.polimi.se2018.model.card.Deck;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.*;
import javafx.collections.ObservableArrayBase;

import java.util.Observable;
import java.util.LinkedList;
import java.util.Observer;
import java.util.Vector;


/**
 * the game board for one game with all the method that can be used in a game
 *
 * @author Luca Genoni
 */
public class GameBoard extends Observable {
    private int currentRound;//can have only the get
    private int currentTurn;//can have only the get
    private int indexCurrentPlayer;//can have only the get
    private boolean stopGame;//can have only the get
    private int countSetWindow;
    private DiceStack[] roundTrack;//can have only the get
    private Player[] player; //can have only the get(they are created with the constructor )
    private Vector<Observer> obs;
    private LinkedList<Observer> view;
    private ToolCard[] toolCard;//can have only the get
    private ObjectivePublicCard[] objectivePublicCard;//can have only the get
    private DiceStack poolDice;//can have only the get
    private FactoryDice factoryDiceForThisGame; //nobody can see it

    /**
     * constructor for the gameBoard. Contain the preparation of the game, need to set the name of each player.
     *
     * @param nickNamePlayers  an array of String with the name of each player
     * @param indexFirstPlayer int of the First player, his Id is set to 0
     */
    public GameBoard(String[] nickNamePlayers, int indexFirstPlayer) {
        if (nickNamePlayers.length < 2 || nickNamePlayers.length > 4) return;
        if (indexFirstPlayer >= nickNamePlayers.length || indexFirstPlayer < 0) return;
        currentRound = 0;
        currentTurn = 1;
        roundTrack = new DiceStack[10];// don't need to be initialized, they take the reference of from the dicePool
        toolCard = new ToolCard[3];
        objectivePublicCard = new ObjectivePublicCard[3];
        factoryDiceForThisGame = new BalancedFactoryDice();// here for change the factory
        Deck deck = Deck.getDeck();

        player = new Player[nickNamePlayers.length];


        //setUp player
        for (int i = 0; i < nickNamePlayers.length; i++) {
            player[i] = new Player(nickNamePlayers[((indexFirstPlayer + i) % nickNamePlayers.length)], i);
            player[i].setPrivateObject(deck.drawObjectivePrivateCard());
            WindowPatternCard[] window = new WindowPatternCard[4];
            for (int n = 0; n < 4; n++) {
                window[n] = deck.drawWindowPatternCard();
            }
            player[i].setThe4WindowPattern(window);
        }
        indexCurrentPlayer = indexFirstPlayer;
        for (int i = 0; i < toolCard.length; i++) toolCard[i] = deck.drawToolCard();
        for (int i = 0; i < objectivePublicCard.length; i++) objectivePublicCard[i] = deck.drawObjectivePublicCard();
        //create the first dicePool
        poolDice = new DiceStack();
        for (int i = 0; i < (2 * player.length + 1); i++) {
            poolDice.add(factoryDiceForThisGame.createDice());
        }
        stopGame = true;
    }

    //************************************getter**********************************************
    //************************************getter**********************************************
    //************************************getter**********************************************

    /**
     * @return int for the current round
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * @return the array of DiceStack belonging to the roundtrack
     */
    public DiceStack[] getRoundTrack() {
        return roundTrack;
    }

    /**
     * @return array of all the Player
     */
    public Player[] getPlayer() {
        return player;
    }

    /**
     * @param index of the player domain [0, n°player-1]
     * @return the player relative to the index, null if the index is wrong
     */
    public Player getPlayer(int index) {
        if (index < 0 || index >= player.length) return null;
        return player[index];
    }

    /**
     * @return an array of Tool Card
     */
    public ToolCard[] getToolCard() {
        return toolCard;
    }

    /**
     * @param index of the Tool card domain [0,2]
     * @return the ToolCard relative to the index, null if the index is wrong
     */
    public ToolCard getToolCard(int index) {
        if (index < 0 || index > toolCard.length) return null;
        return toolCard[index];
    }

    /**
     * @param index of the Tool card domain [0,2]
     * @return the ID of the ToolCard in position of the param index, -1 if index is wrong
     */
    public int getIdToolCard(int index) {
        if (index < 0 || index >= toolCard.length) return -1;
        return toolCard[index].getId();
    }

    /**
     * @return an array of ObjectivePublicCard
     */
    public ObjectivePublicCard[] getObjectivePublicCard() {
        return objectivePublicCard;
    }

    /**
     * @return a DiceStack relative to the DicePool
     */
    public DiceStack getPoolDice() {
        return poolDice;
    }

    /**
     * @return the index relative to the current player [0, n°player-1]
     */
    public int getIndexCurrentPlayer() {
        return indexCurrentPlayer;
    }

    /**
     * @return the number of the current round
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * @return true if the game over, false otherwise
     */
    public boolean isStopGame() {
        return stopGame;
    }

    //************************************setter**********************************************
    //************************************setter**********************************************
    //************************************setter**********************************************

    //no setter

    //**************************************Observer/observable**************************************************


    //************************************class's method**********************************************
    //************************************class's method**********************************************
    //************************************class's method**********************************************

    /**
     * change the current player to the next.
     * check the state of the player for the first/second turn
     *
     * @param indexPlayer the index of the player who send the request (can also change to the nickname)
     * @return true if the change was a success, false if there is a problem.
     */
    public boolean nextPlayer(int indexPlayer) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn baby
            if (currentTurn < player.length) {
                if (player[indexPlayer].isFirstTurn()) {
                    player[indexPlayer].endTrun(false);
                }//else he use a tool card that alter the normal circle
                indexCurrentPlayer = (indexCurrentPlayer + 1) % player.length;
                currentTurn++;
                if (!player[indexCurrentPlayer].isFirstTurn()) return nextPlayer(indexCurrentPlayer);

                //se è uguale siamo nella fare tra il 1° e il 2° giro
            } else if (currentTurn == player.length) {
                if (player[indexPlayer].isFirstTurn()) {
                    player[indexPlayer].endTrun(false);
                }//else he use a tool card that alter the normal circle
                currentTurn++;
                if (player[indexCurrentPlayer].isFirstTurn()) return nextPlayer(indexCurrentPlayer);

                //siamo in pieno 2°giro
            } else if (currentTurn < (2 * player.length)) {
                if (!player[indexPlayer].isFirstTurn()) {
                    player[indexPlayer].endTrun(true);
                }//else he use a tool card that alter the normal circle
                indexCurrentPlayer = (indexCurrentPlayer - 1) % player.length;
                currentTurn++;
                if (player[indexCurrentPlayer].isFirstTurn()) return nextPlayer(indexCurrentPlayer);

                //siamo tra il 2° e il 1° giro/endgame
            } else if (currentTurn == (2 * player.length)) {
                if (!player[indexPlayer].isFirstTurn()) {
                    player[indexPlayer].endTrun(true);
                }//else he use a tool card that alter the normal circle
                indexCurrentPlayer = (indexCurrentPlayer + 1) % player.length;
                currentTurn = 0;
                currentRound++;
                //Ora potrebbe finire il gioco
                if (currentRound == roundTrack.length) {
                    stopGame = true;
                    currentTurn = 2 * player.length + 1;
                    //method for the end game
                    calculatePoint();
                } else {
                    roundTrack[currentRound] = new DiceStack();
                    for (int i = 0; i < (2 * player.length + 1); i++) {
                        Dice dice = factoryDiceForThisGame.createDice();
                        if (dice == null) {
                            System.err.println("Non ci sono abbastanza dadi, è strano perchè dovrebbero essere 90 esatti");
                            return false;
                        }
                        roundTrack[currentRound].add(dice);
                    }
                    if (!player[indexCurrentPlayer].isFirstTurn()) return nextPlayer(indexCurrentPlayer);
                }
            } else {
                System.err.println("Gioco corrotto, impossibile proseguire i current turn non impazziti");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void calculatePoint() {

        int pointCounter;
        for (Player player : this.player) {
            pointCounter = 0;
            //calculate point for the public object
            for (ObjectivePublicCard objectivePublicCard : this.objectivePublicCard) {
                pointCounter += objectivePublicCard.calculatePoint(player.getPlayerWindowPattern());
            }
            //calculate point for the private object
            pointCounter += player.getPrivateObject().calculatePoint(player.getPlayerWindowPattern());
            //add the token left
            pointCounter += player.getFavorToken();
            //subtract the void cell
            pointCounter = pointCounter - (20 - player.getPlayerWindowPattern().getNumberOfCellWithDice());
            player.setPoints(pointCounter);
        }
    }

    /**
     * move for select the window Pattern
     *
     * @param indexOfThePlayer who want to set the window
     * @param indexOfTheWindow of the window selected
     * @return true if all is gone perfectly, false otherwise.
     */
    public boolean setWindowOfPlayer(int indexOfThePlayer, int indexOfTheWindow) {
        try {

            //useless check: if(currentRound!=0 && currentTurn!=0)return false;//can't pick a window during a game.... troller
            if (indexOfThePlayer < 0 || indexOfThePlayer >= player.length) return false;//wrong index
            if (indexOfTheWindow < 0 || indexOfTheWindow > 3) return false;//wrong index
            if (player[indexOfThePlayer].getPlayerWindowPattern() != null) return false;//window already picked
            // ok i set your window
            player[indexOfThePlayer].setPlayerWindowPattern(indexOfTheWindow);
            countSetWindow++;
            if (countSetWindow == player.length) stopGame = false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }


//*****************************************metodi del player************************************************************************
//*****************************************metodi del player************************************************************************
//*****************************************metodi del player************************************************************************
//*****************************************metodi del player************************************************************************
//*****************************************metodi del player************************************************************************

    public boolean addNormalDiceToHandFromDraftPool(int indexPlayer, int indexdiceDraftpool) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            if (indexdiceDraftpool >= poolDice.size() || indexdiceDraftpool < 0) return false;// index wrong
            Dice dice = poolDice.getDice(indexdiceDraftpool);
            if (dice == null) return false;
            if (!player[indexPlayer].addNormalDiceToHandFromDraftPool(dice)) return false;
            poolDice.takeDiceFromStack(indexdiceDraftpool);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public boolean insertDice(int indexPlayer, int line, int column) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            if (!player[indexPlayer].isHasPlaceANewDice()) return false;
            return player[indexPlayer].insertDice(line, column);//if true ok, false if something gone wrong
        } catch (Exception e) {
            return false;
        }
    }

    public boolean useToolCard(int indexPlayer, int cost) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            if (cost < 0) return false;
            return player[indexPlayer].useToolCard(cost);
        } catch (Exception e) {
            return false;
        }
    }
    //*****************************************Tool's method of Gameboard **********************************************
    //*****************************************Tool's method of Gameboard **********************************************
    //*****************************************Tool's method of Gameboard **********************************************
    //*****************************************Tool's method of Gameboard **********************************************
    //*****************************************Tool's method of Gameboard **********************************************

    /**
     * move for take the active dice in hand and change it with a new one
     *
     * @param indexPlayer who send the request of the move
     * @return true if is gone all ok, false otherwise
     */
    public boolean changeDiceBetweenHandAndFactory(int indexPlayer) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false;//not your turn
            if (!player[indexPlayer].isHasUsedToolCard()) return false;//you didn't use a tool card
            if (!player[indexPlayer].isHasDrawNewDice()) return false;
            if (player[indexPlayer].isHasPlaceANewDice()) return false;
            Dice dice = player[indexPlayer].removeDiceFromHand();
            if (dice == null) return false;
            factoryDiceForThisGame.removeDice(dice);
            dice = factoryDiceForThisGame.createDice();
            player[indexPlayer].addDiceToHand(dice);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean changeDiceBetweenHandAndRoundTrack(int indexPlayer, int round, int indexStack) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false;//not your turn
            if (round >= currentRound || round < 0) return false;//can't select a round that didn't exist
            if (indexStack >= roundTrack[round].size() || indexStack < 0) return false;// index wrong
            if (!player[indexPlayer].isHasDrawNewDice()) return false;
            if (player[indexPlayer].isHasPlaceANewDice()) return false;
            Dice dice = player[indexPlayer].removeDiceFromHand();
            if (dice == null) return false; // no dice in hand wtf
            if (player[indexPlayer].addDiceToHand(roundTrack[round].get(indexStack))) return false;
            roundTrack[round].add(indexStack, dice);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean moveDiceFromWindowPatternToHandWithRestriction(int indexPlayer, int round, int indexStack, int line, int column) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false;//not your turn
            if (round >= currentRound || round < 0) return false;//can't select a round that didn't exist
            if (indexStack >= roundTrack[round].size() || indexStack < 0) return false;// index wrong
            if (!player[indexPlayer].isHasUsedToolCard()) return false;//you didn't use a tool card
            Dice dHand = player[indexPlayer].getPlayerWindowPattern().getCell(line, column).getDice();
            if (dHand == null) return false;   //no dice in this cell
            if (dHand.getColor() != roundTrack[round].get(indexStack).getColor()) return false; // color isn't the same
            return player[indexPlayer].moveDiceFromWindowPatternToHand(line, column);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean rollDicePool(int indexPlayer) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false;//not your turn
            if (player[indexPlayer].isFirstTurn()) return false;
            poolDice.reRollAllDiceInStack();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************

    public boolean insertDice(int indexPlayer, int line, int column, boolean adjacentRestriction, boolean colorRestriction, boolean valueRestriction) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexPlayer].insertDice(line, column, adjacentRestriction, colorRestriction, valueRestriction);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean moveDiceFromWindowPatternToHand(int indexPlayer, int line, int column) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexPlayer].moveDiceFromWindowPatternToHand(line, column);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean rollDiceInHand(int indexPlayer) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexPlayer].rollDiceInHand();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean increaseOrDecrease(int indexPlayer, boolean increase) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexCurrentPlayer].increaseOrDecrease(increase);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean oppositeFaceDice(int indexPlayer) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexCurrentPlayer].oppositeFaceDice();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean endSpecialFirstTurn(int indexPlayer) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexCurrentPlayer].endSpecialFirstTurn();
        } catch (Exception e) {
            return false;
        }
    }

    //*********************************************Utils*************************************************
    //*********************************************Utils*************************************************
    //*********************************************Utils*************************************************
    //*********************************************Utils*************************************************
    //*********************************************Utils*************************************************

    public boolean selectDiceInHand(int indexPlayer, int index) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexCurrentPlayer].selectDiceInHand(indexPlayer);
        } catch (Exception e) {
            return false;
        }
    }


}
