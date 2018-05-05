package it.polimi.se2018.model.dice;

import it.polimi.se2018.model.dice.dice_factory.Dice;
import it.polimi.se2018.model.dice.dice_factory.DiceFactory;

import java.util.LinkedList;

/**
 * DiceDtack is a comfortable class to handle a stack of dice
 * @author Luca Genoni
 * @version 1.0
 * @since 1.0
 */
public class DiceStack {
    private  LinkedList<Dice> diceList;
    private static DiceFactory diceFactory;
    /**
     * Method <strong>DiceStack</strong>
     * <em>Description</em>: constructor for empty DiceStack
     */
    public DiceStack(DiceFactory diceFactory){
        diceList = new LinkedList<>();
        this.diceFactory= diceFactory;
    }
    public DiceStack(int numberOfNewDice){
        diceList = new LinkedList<>();
        for(int i=0;i<numberOfNewDice;i++){
            diceList.add((diceFactory.createDice()));
        }
    }
    public void setDiceFactory(DiceFactory diceFactory) {
        this.diceFactory = diceFactory;
    }
    /**
     * Method <strong>addDice</strong>
     * <em>Description</em>: can add dice to the stack
     *
     * @param Dice to add to the stack
     */
    public void addDice(Dice Dice) {
        diceList.add(Dice);
    }

    /**
     * Method <strong>getDice</strong>
     * <em>Description</em>: can return the dice for let them see the value or color
     *
     * @param index
     * @return Dice  or null if there isn't any dice in that index
     */
    public Dice getDice(int index) {
        if(index>=diceList.size()) return null;
        return diceList.get(index);
    }
    /**
     * Method <strong>removeDiceFromStack</strong>
     * <em>Description</em>: remove the dice from the stack.
     *
     * @param index integer of the index of the dice
     * @return the dice removed or null if there isn't any dice in that index
     */
    public Dice removeDiceFromStack(int index){
        if(index>=diceList.size()||index<0) return null;
        Dice dice=diceList.get(index);
        diceList.remove(index);
        return dice;
    }
    /**
     * Method <strong>removeDiceFromGame</strong>
     * <em>Description</em>: remove the dice from the stack.
     *
     * @param index integer of the index of the dice
     */
    public void reinsertDiceToFactory(int index){
        diceFactory.removeDice(removeDiceFromStack(index));
    }
    /**
     * Method <strong>DiceStack</strong>
     * <em>Description</em>: remove the dice from the stack
     *
     * @return integer, the size
     */
    public int size(){
        return diceList.size();
    }

}
