package it.polimi.se2018.model.dice;

import it.polimi.se2018.exception.gameboard_exception.NoDiceException;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * DiceStack is a comfortable class that extends from LinkedList and have some utils
 *
 * @author Luca Genoni
 */
public class DiceStack extends LinkedList<Dice> implements Serializable{

    /**
     * the method right method for take a dice without exception
     *
     * @param index of the dice
     * @return dice or null
     */
    public Dice getDice(int index) throws NoDiceException {
        if(index>=this.size()) throw new NoDiceException();
        return this.get(index);
    }


    /**
     * method for select the dice in hand
     *
     * @param index of the dice to select, if out of bound don't do anything
     */
    public void moveDiceToTheTop(int index){
        if(index==0 || index>=this.size()) return;
        this.addFirst(this.remove(index));
    }
    public void reRollAllDiceInStack(){
        for (Dice dice : this) {

            dice.rollDice();
        }
    }
}
