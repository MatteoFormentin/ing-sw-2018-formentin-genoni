package it.polimi.se2018.model.dice;

import java.util.LinkedList;
import java.util.List;

/**
 * DiceStack is a comfortable class that extends from LinkedList and have some utils
 *
 * @author Luca Genoni
 */
public class DiceStack extends LinkedList<Dice> {
    /**
     * return the dice for let them see the value or color, but the dice remain in the stack
     *
     * @param index of the dice
     * @return Dice or null if there isn't any dice in that index
     */
    @Override
    public Dice get(int index) {
        if(index>=this.size()) return null;
        return this.get(index);
    }

    /**
     * remove the dice from the stack.
     *
     * @param index integer of the index of the dice
     * @return the dice removed or null if there isn't any dice in that index
     */
    public Dice takeDiceFromStack(int index){
        if(index>=this.size()||index<0) return null;
        Dice dice=this.get(index);
        this.remove(index);
        return dice;
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
