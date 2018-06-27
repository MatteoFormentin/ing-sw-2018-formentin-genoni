package it.polimi.se2018.model.card;

import it.polimi.se2018.controller.effect.EffectGame;
import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.gameboard_exception.FatalGameErrorException;
import it.polimi.se2018.exception.gameboard_exception.NoDiceException;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.AlreadyDrawANewDiceException;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.AlreadyPlaceANewDiceException;
import it.polimi.se2018.exception.gameboard_exception.tool_exception.DiceInHandToolException;
import it.polimi.se2018.exception.gameboard_exception.tool_exception.FlowTurnException;
import it.polimi.se2018.model.Player;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Base class for tool card.
 * Card id starts form 0 instead of 1.
 *
 * @author Matteo Formentin
 */
public class ToolCard implements Serializable {
    //description of each tool card
    private int id;
    private String name;
    private String description;
    private int numberTimeUsed;
    private int favorToken;

    private boolean undoAble;
    //field for make the tool work
    private LinkedList<EffectGame> listEffect;
    private int firstEffect;
    private boolean checkFirstTurn;
    private boolean checkSecondTurn;
    private boolean checkSomeDiceInHand;
    private boolean checkDrawDice;
    private boolean checkIfNotDicePlaced;
    private int checkNumberDiceInWindow;
    private boolean checkFirstRound;

    public ToolCard() {
        id = 0;
        name = null;
        description = null;
        numberTimeUsed = 0;
        favorToken = 1;
        listEffect = null;
        firstEffect = 0;
        checkFirstTurn = false;
        checkSecondTurn = false;
        checkSomeDiceInHand = false;
        checkIfNotDicePlaced = false;
        checkFirstRound = false;
    }

    /**
     * Return card id.
     *
     * @return id int.
     */
    public int getId() {
        return id;
    }

    /**
     * Set card id.
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return card name.
     *
     * @return name String.
     */
    public String getName() {
        return name;
    }

    /**
     * Set card name.
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return card description.
     *
     * @return description String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set card description.
     *
     * @param description String.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberTimeUsed() {
        return numberTimeUsed;
    }

    public int getFavorToken() {
        return favorToken;
    }

    public void setFavorToken(int favorToken) {
        this.favorToken = favorToken;
    }

    void setListEffect(LinkedList<EffectGame> listEffect) {
        this.listEffect = listEffect;
    }

    //************************************************************method for a general tool Card*************************
    public LinkedList getCopyListEffect() {
        LinkedList<EffectGame> copyListEffect = new LinkedList<>();
        for (int i = firstEffect; i < listEffect.size(); i++) {
            EffectGame effectGame = listEffect.get(i);
            copyListEffect.addLast(effectGame);
        }
        firstEffect = 0;
        return copyListEffect;
    }

    public boolean isUndoAble() {
        return undoAble;
    }

    /**
     * method for check the state of the player in relation to the dice that he has drawn
     * checkDrawDice && checkSomeDiceInHand care if he has drawn a die and he should have ones in his hand (After draw a die)
     * checkDrawDice && !checkSomeDiceInHand care if he has drawn a die and he shouldn't have ones in his hand (Before draw a die)
     * !checkDrawDice && checkSomeDiceInHand don't care about either of them
     * !checkDrawDice && !checkSomeDiceInHand don't care if he has drawn a die, but he shouldn't have ones in his hand (Normal tool card, with no dice in hand)
     *
     * @param checkDrawDice       if i care that the player make this move
     * @param checkSomeDiceInHand if checkSomeDiceInHand is true the player should have some dice in hand, false otherwise
     */
    public void setCheck(boolean checkDrawDice, boolean checkSomeDiceInHand) {
        this.checkDrawDice = checkDrawDice;
        this.checkSomeDiceInHand = checkSomeDiceInHand;
    }

    public void setCheck(boolean checkIfNotDicePlaced, int checkNumberDiceInWindow) {
        this.checkIfNotDicePlaced = checkIfNotDicePlaced;
        this.checkNumberDiceInWindow = checkNumberDiceInWindow;
        undoAble = true;
    }

    /**
     * @param checkDrawDice        if i care that the player make this move
     * @param checkSomeDiceInHand  if checkSomeDiceInHand is true the player should have some dice in hand, false otherwise
     * @param checkFirstRound      if false don't care, if true the round shouldn't be the first one
     */
    public void setCheck(boolean checkDrawDice, boolean checkSomeDiceInHand, boolean checkFirstRound) {
        this.checkDrawDice = checkDrawDice;
        this.checkSomeDiceInHand = checkSomeDiceInHand;
        this.checkFirstRound = checkFirstRound;
    }

    /**
     * @param checkDrawDice            if i care that the player make this move
     * @param checkSomeDiceInHand      if checkSomeDiceInHand is true the player should have some dice in hand, false otherwise
     * @param checkFirstRound          if false don't care, if true the round shouldn't be the first one
     * @param trueFirstTurnFalseSecond if true check if it's the first Turn; if false check if it's the second one
     */
    public void setCheck(boolean checkDrawDice, boolean checkSomeDiceInHand, boolean checkFirstRound, boolean trueFirstTurnFalseSecond) {
        this.checkDrawDice = checkDrawDice;
        this.checkSomeDiceInHand = checkSomeDiceInHand;
        this.checkFirstRound = checkFirstRound;
        if (trueFirstTurnFalseSecond) checkFirstTurn = true;
        else checkSecondTurn = true;
    }

    /**
     * method for check the usability of the tool card during the game
     *
     * @param roundGame he need the current round of the game
     * @param player    he need to check the player state
     * @throws GameException if there is something wrong for using this tool card
     */
    public void checkUsabilityToolCard(int roundGame, Player player) throws NoDiceException,FlowTurnException,AlreadyPlaceANewDiceException,
            AlreadyDrawANewDiceException,DiceInHandToolException,FatalGameErrorException{
        if (checkFirstRound && roundGame == 0) throw new NoDiceException();
        //throw id checkFirstTurn=  true and player isn't in his first turn
        if (checkFirstTurn && !player.isFirstTurn())
            throw new FlowTurnException();
        //throw id checkFirstTurn=  true and player isn't in his first turn
        if (checkSecondTurn && player.isFirstTurn())
            throw new FlowTurnException();

        if (checkNumberDiceInWindow > 0 && player.getPlayerWindowPattern().getNumberOfCellWithDice() < checkNumberDiceInWindow)
            throw new NoDiceException();
        //throw if checkIfNotDicePlaced->!player.isHasPlaceANewDice()= !A or !B so throw Exception if !(A->!B)= A and B
        if (checkIfNotDicePlaced && player.isHasPlaceANewDice()) throw new AlreadyPlaceANewDiceException();
        //throw if checkFirstRound= true and roundGame=0
        if (checkDrawDice) {// i care about the player move draw a dice
            if (checkSomeDiceInHand) { // if the player should have a die in hand
                if (player.isHasDrawNewDice()) {
                    if (player.getHandDice().isEmpty()) throw new AlreadyDrawANewDiceException();
                    else firstEffect = 1; //non pesca il dado
                } else {
                    if (player.getHandDice().isEmpty()) firstEffect = 0; //pesca il dado
                    else throw new FatalGameErrorException();
                }
            } else {
                if (player.isHasDrawNewDice()) { // if he shouldn't have dice in hand, he shouldn't even have drawn them if i care about this move
                    throw new AlreadyDrawANewDiceException();
                }//else it's ok
            }
        } else {
            if (!checkSomeDiceInHand && !player.getHandDice().isEmpty())
                throw new DiceInHandToolException();
        }
    }

    public void incrementUsage() {
        favorToken = 2;
        numberTimeUsed++;
    }

}
