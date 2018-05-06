package it.polimi.se2018.model.dice.dice_factory;

import it.polimi.se2018.model.dice.DiceColor;

import java.util.LinkedList;
import java.util.Random;


/**
 * <strong>FactoryDice</strong> singleton Class only one BalancedDiceFactory
 * @author Luca Genoni
 * @version 1.3 singleton with synchronized
 * @version 1.2 fix getDice() & getpoll removed
 * @since 1.0
 */
public class DiceBalancedFactory extends DiceFactory {

    private static DiceBalancedFactory singleDiceBalancedFactory;
    private int currentNumberOfDice;
    //currentNumberOfEachDice stores the number of dice for each color. It is indexed with the ordinal number relative to each color
    private int[] currentNumberOfEachDice;
    //availableColours stores the ordinal number linked to the color
    private LinkedList <DiceColor> availableColours;

    private DiceBalancedFactory(){
        int MaxNumberOfDice= 90;
        currentNumberOfDice=MaxNumberOfDice;
        currentNumberOfEachDice= new int[DiceColor.getNumberOfDiceColors()];
        availableColours= new LinkedList<>();
        int maxNumberOfEachDice= MaxNumberOfDice/DiceColor.getNumberOfDiceColors();
        for (int i=0;i<currentNumberOfEachDice.length; i++){
            currentNumberOfEachDice[i]=maxNumberOfEachDice;
            availableColours.add(DiceColor.getDiceColor(i));
        }
    }
    public static synchronized DiceBalancedFactory getBalancedDiceFactory(){
        if(singleDiceBalancedFactory ==null){
            singleDiceBalancedFactory = new DiceBalancedFactory();
        }
        return singleDiceBalancedFactory;
    }
    /**
     * Method <strong>createDice</strong>
     * <em>Description</em>
     * constructor for dices.
     *
     * @return the new dice or null
     */
    @Override
    public Dice createDice() {
        if(currentNumberOfDice==0) {
            return null;
        } else{
            boolean repeat=true;    //for the while loop
            int random=0;
            while(repeat){
                random = new Random().nextInt(availableColours.size());
                if (currentNumberOfEachDice[availableColours.get(random).ordinal()]>0) {
                    //when it found a color available, the loop stops
                    repeat = false;
                }
            }
            currentNumberOfDice--;
            currentNumberOfEachDice[availableColours.get(random).ordinal()]--;
            Dice dice = new Dice(availableColours.get(random));
            if(currentNumberOfEachDice[availableColours.get(random).ordinal()]==0){
                //remove the availableColours after the creation of the dice.
                availableColours.remove(random);
            }
            return dice;
        }
    }

    /**
     * Method <strong>getPoolDice</strong>
     * <em>Description</em>: friendly method to reinsert a die in the factory (ie delete it from the game, without creating errors)
     *
     * @param dice to reinsert in the factory
     */
    @Override
    public void removeDice(Dice dice){
        if(currentNumberOfEachDice[dice.getColor().ordinal()]==0) {
            availableColours.addLast(dice.getColor()); // re-add the flag to make the color available
        }
        currentNumberOfDice++;
        currentNumberOfEachDice[dice.getColor().ordinal()]++;

    }
    public static void reset(){
        singleDiceBalancedFactory =null;
    }
    /**
     * Method <strong>getPoolDice</strong>
     * <em>Description</em>
     * constructor for the poolDices.
     *
     * @param numberOfDice to draw from the factory
     * @return array with numberOfDice Dice
    */
    public Dice[] getPoolDice(int numberOfDice){
        Dice[] arrayDice= new Dice[numberOfDice];
        for (int i=0; i<numberOfDice; i++) {
            arrayDice[i]=createDice();
        }
        return arrayDice;
    }




}