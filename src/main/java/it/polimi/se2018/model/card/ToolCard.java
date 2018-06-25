package it.polimi.se2018.model.card;

import it.polimi.se2018.controller.effect.EffectGame;
import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.model.GameBoard;
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
    private int id;
    private String name;
    private String description;
    private int numberTimeUsed = 0;
    private int favorToken = 1;
    private LinkedList<EffectGame> listEffect;
    private boolean checkTurn;
    private boolean trueFirstTurnFalseSecond;
    private boolean checkHasNotPlaceNewDice;
    private boolean checkDiceInHand;
    private boolean trueHaveDiceFalseNoDice;
    private boolean checkFirstRound;

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
        for (int i = 0; i < listEffect.size(); i++) {
            EffectGame effectGame = listEffect.get(i);
            copyListEffect.addLast(effectGame);
        }
        return copyListEffect;
    }

    public void setCheckFirstRound(boolean checkFirstRound){
        this.checkFirstRound=checkFirstRound;
    }
    public void setCheckTrun(boolean trueFirstTurnFalseSecond) {
        this.trueFirstTurnFalseSecond = trueFirstTurnFalseSecond;
        checkTurn = true;
    }

    public void setCheckDiceHand(boolean checkHasNotPlaceNewDice) {
        this.checkHasNotPlaceNewDice = checkHasNotPlaceNewDice;
        this.checkDiceInHand = false;
    }

    public void setCheckDiceHand(boolean checkHasNotPlaceNewDice, boolean checkDiceInHand, boolean trueHaveDiceFalseNoDice) {
        this.checkHasNotPlaceNewDice = checkHasNotPlaceNewDice;
        this.checkDiceInHand = checkDiceInHand;
        this.trueHaveDiceFalseNoDice = trueHaveDiceFalseNoDice;
    }

    public void checkUsabilityToolCard(int roundGame, Player player) throws GameException {
        //if i need to check the condition AND the player didn't draw a dice or draw it but he already place it
        if (checkHasNotPlaceNewDice && player.isHasPlaceANewDice())  throw new GameException("Hai già piazzato un dado");
        if (checkDiceInHand && trueHaveDiceFalseNoDice == player.getHandDice().isEmpty()) throw new GameException("Non puoi usare questa carta. Controlla i dadi in mano");
        if(checkFirstRound && roundGame==0)  throw new GameException("Non ci sono dadi nel roundTrack");
        //if i need to check the condition AND the player isn't in the same turn available
        if (checkTurn && trueFirstTurnFalseSecond != player.isFirstTurn())  throw new GameException("Non puoi giocare la carta in questo TURNO.");
    }

    public void incrementUsage() {
        favorToken = 2;
        numberTimeUsed++;
    }

}
