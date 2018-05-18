package it.polimi.se2018.model.dice;

import java.util.LinkedList;

/**
 * DiceStack is a comfortable class to handle a pile of dice
 *
 * @author Luca Genoni
 */
public class DiceStack {
    private  LinkedList<Dice> diceList;
    private FactoryDice factoryDice;

    /**
     * Create a stack of dice for convenient use of the dice, the factory must be the same as the game in which this stack is generated
     */
    public DiceStack(FactoryDice factoryDice){
        diceList = new LinkedList<>();
        this.factoryDice = factoryDice;
    }

    /**
     * the only legal public method for create a dice out of the package dice
     *
     * @param numberOfNewDice to create
     */
    public void createDice(int numberOfNewDice){
        diceList = new LinkedList<>();
        for(int i=0;i<numberOfNewDice;i++){
            diceList.add((factoryDice.createDice()));
        }
    }

    /**
     * set the factory
     *
     * @param factoryDice the type
     */
    public void setDiceFactory(FactoryDice factoryDice) {
        this.factoryDice = factoryDice;
    }
    /**
     * can add dice to the stack for Player and Tool Card
     *
     * @param Dice to add to the stack
     */
    public void addDice(Dice Dice) {
        diceList.add(Dice);
    }

    /**
     * return the dice for let them see the value or color, but the dice remain in the stack
     *
     * @param index of the dice
     * @return Dice or null if there isn't any dice in that index
     */
    public Dice getDice(int index) {
        if(index>=diceList.size()) return null;
        return diceList.get(index);
    }
    public void addADiceFromFactory(){
        diceList.add(factoryDice.createDice());
    }
    /**
     * remove the dice from the stack.
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
     * method for select the dice in hand
     *
     * @param index of the dice to select, if out of bound don't do anything
     */
    public void moveDiceToTheTop(int index){
        if(index==0 || index>=diceList.size()) return;
        diceList.addFirst(diceList.remove(index));
    }
    public void reRollAllDiceInStack(){
        for (Dice dice : diceList) {
            dice.rollDice();
        }
    }
    /**
     * remove the dice from the stack.
     *
     * @param index integer of the index of the dice
     */
    public void reinsertDiceToFactory(int index){
        factoryDice.removeDice(removeDiceFromStack(index));
    }
    /**
     * show the size of the stack
     *
     * @return integer, the size
     */
    public int size(){
        return diceList.size();
    }

}
