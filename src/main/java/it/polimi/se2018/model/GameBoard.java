package it.polimi.se2018.model;


import it.polimi.se2018.model.card.Deck;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.*;


/**
 * the game board for one game
 *
 * @author Luca Genoni
 */
public class GameBoard {
    private int currentround;//can have only the get
    private int currentTurn;//can have only the get
    private int indexCurrentPlayer;//can have only the get
    private boolean endGame;//can have only the get
    private DiceStack[] roundTrack;//can have only the get
    private Player[] player; //can have only the get(they are created with the constructor )
    private ToolCard[] toolCard;//can have only the get
    private ObjectivePublicCard[] objectivePublicCard;//can have only the get
    private DiceStack poolDice;//can have only the get
    private FactoryDice factoryDiceForThisGame; //nobody can see it

    /**
     * constructor for the gameBoard. Contain the preparation of the game, need to set the name of each player.
     *
     * @param namePlayers      an array of String with the name
     * @param indexFirstPlayer int of the First player, his Id is set to 0
     */
    public GameBoard(String[] namePlayers, int indexFirstPlayer) {
        currentround = 0;
        currentTurn = 0;
        roundTrack = new DiceStack[10];// don't need to be initialized, they take the reference of from the dicePool
        toolCard = new ToolCard[3];
        objectivePublicCard = new ObjectivePublicCard[3];
        factoryDiceForThisGame = new BalancedFactoryDice();// here for change the factory
        Deck deck = Deck.getDeck();
        player = new Player[namePlayers.length];
        poolDice = new DiceStack();

        //setUp player
        for (int i = 0; i < namePlayers.length; i++) {
            player[i] = new Player(namePlayers[((indexFirstPlayer + i) % namePlayers.length)], i);
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
        for (int i = 0; i < (2 * player.length + 1); i++) {
            poolDice.add(factoryDiceForThisGame.createDice());
        }
        endGame = false;
    }

    //************************************getter**********************************************
    //************************************getter**********************************************
    //************************************getter**********************************************

    /**
     * @return int for the current round
     */
    public int getCurrentround() {
        return currentround;
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
        if (index < 0 || index > player.length) return null;
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
    public boolean isEndGame() {
        return endGame;
    }

    //************************************setter**********************************************
    //************************************setter**********************************************
    //************************************setter**********************************************

    //no setter

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
        if (endGame) return false;// the game is already over
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
            currentround++;
            //Ora potrebbe finire il gioco
            if (currentround == roundTrack.length) {
                endGame = true;
                currentTurn = 2 * player.length + 1;
                //method for the end game
                calculatePoint();
            } else {
                roundTrack[currentround] = new DiceStack();
                for (int i = 0; i < (2 * player.length + 1); i++) {
                    Dice dice = factoryDiceForThisGame.createDice();
                    if (dice == null) {
                        System.err.println("Non ci sono abbastanza dadi, è strano perchè dovrebbero essere 90 esatti");
                        return false;
                    }
                    roundTrack[currentround].add(dice);
                }
                if (!player[indexCurrentPlayer].isFirstTurn()) return nextPlayer(indexCurrentPlayer);
            }
        } else {
            System.err.println("Gioco corrotto, impossibile proseguire i current turn non impazziti");
            return false;
        }
        return true;
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
        //useless check: if(currentround!=0 && currentTurn!=0)return false;//can't pick a window during a game.... troller
        if (indexOfThePlayer < 0 || indexOfThePlayer >= player.length) return false;//wrong index
        if (indexOfTheWindow < 0 || indexOfTheWindow > 3) return false;//wrong index
        if (player[indexOfThePlayer].getPlayerWindowPattern() != null) return false;//window already picked
        // ok i set your window
        player[indexOfThePlayer].setPlayerWindowPattern(indexOfTheWindow);
        return true;
    }

    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************

    /**
     * move for take the active dice in hand and change it with a new one
     *
     * @param indexPlayer who send the request of the move
     * @return true if is gone all ok, false otherwise
     */
    public boolean changeDiceBetweenHandAndFactory(int indexPlayer) {
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
    }

    public boolean changeDiceBetweenHandAndRoundTrack(int indexPlayer, int round, int indexStack) {
        if (indexPlayer != indexCurrentPlayer) return false;//not your turn
        if (round >= currentround || round < 0) return false;//can't select a round that didn't exist
        if (indexStack >= roundTrack[round].size() || indexStack < 0) return false;// index wrong
        if (!player[indexPlayer].isHasUsedToolCard()) return false;//you didn't use a tool card
        if (!player[indexPlayer].isHasDrawNewDice()) return false;
        if (player[indexPlayer].isHasPlaceANewDice()) return false;
        Dice dice = player[indexPlayer].removeDiceFromHand();
        if (dice == null) return false; // no dice in hand wtf
        player[indexPlayer].addDiceToHand(roundTrack[round].get(indexStack));
        roundTrack[round].add(indexStack, dice);
        return true;
    }

    public boolean moveDiceFromWindowPatternToHandWithRestriction(int indexPlayer, int round, int indexStack, int line, int column) {
        if (indexPlayer != indexCurrentPlayer) return false;//not your turn
        if (round >= currentround || round < 0) return false;//can't select a round that didn't exist
        if (indexStack >= roundTrack[round].size() || indexStack < 0) return false;// index wrong
        if (!player[indexPlayer].isHasUsedToolCard()) return false;//you didn't use a tool card
        Dice dHand= player[indexPlayer].getPlayerWindowPattern().getCell(line, column).getDice();
        if(dHand==null) return false;   //no dice in this cell
        if (dHand.getColor() != roundTrack[round].get(indexStack).getColor()) return false; // color isn't the same
        return player[indexPlayer].moveDiceFromWindowPatternToHand(line,column);
    }

}
