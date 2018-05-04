package it.polimi.se2018.model.dice;

import it.polimi.se2018.model.dice.dice_color.DiceColor;

import java.util.LinkedList;
import java.util.Random;

import static it.polimi.se2018.model.dice.dice_color.DiceColor.getDiceColor;
import static it.polimi.se2018.model.dice.dice_color.DiceColor.getNumberOfDiceColors;

/**
 * Factorydice must be created only once at the start of the game
 * @author Luca Genoni
 * @version 1.2 fix getDice() & getpoll removed
 * @since 1.0
 */
public class DiceFactory {
    /**
     * <strong>Attribute </strong>
     * maxNumberOfDice = 90 it's the only constant that can be changed. It can depend by the number of the Players, but the source code needs some changes
     * For example : 2 player = 50 model.Dice, 3 player = 70 model.Dice, 4 player = 90 model.Dice,
     * @require to be divisible by the NumberOfDiceColor=5 (if source code of DiceColor isn't changed)
     */
    private static final int maxNumberOfDice = 90;
    private static int currentNumberOfDice = maxNumberOfDice;
    private static final int maxNumberOfEachDice= maxNumberOfDice/getNumberOfDiceColors();
    //currentNumberOfEachDice stores the number of dice for each color. It is indexed with the ordinal number relative to each color
    private static final int[] currentNumberOfEachDice= new int[getNumberOfDiceColors()];
    //availableColours stores the ordinal number linked to the color
    private static final LinkedList <DiceColor> availableColours= new LinkedList<>();

    public static void DiceFactory(){
        for (int i=0;i<currentNumberOfEachDice.length; i++){
            currentNumberOfEachDice[i]=maxNumberOfEachDice;
            availableColours.add(getDiceColor(i));
        }
    }

    /**
     * Method <strong>getDice</strong>
     * <em>Description</em>
     * constructor for dices.
     *
     * @return the new dice or null
     */
    public static Dice getDice(){
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
    /* perhaps useless and not convenient
    /**
     * Method <strong>getPoolDice</strong>
     * <em>Description</em>
     * constructor for the poolDices.
     *
     * @param numberOfDice to draw from the factory
     * @return array with numberOfDice Dice

    public Dice[] getPoolDice(int numberOfDice){
        Dice[] arrayDice= new Dice[numberOfDice];
        for (int i=0; i<numberOfDice; i++) {
            arrayDice[i]=getDice();
        }
        return arrayDice;
    }
    */
    /**
     * Method <strong>getPoolDice</strong>
     * <em>Description</em>
     * method to reinsert a die in the factory (ie delete it from the game, without creating errors)
     *
     * @param dice to reinsert in the factory
     * @return null per il dado
     */
    public void reInsertDice(Dice dice){
        if(currentNumberOfEachDice[dice.getColor().ordinal()]==0) {
            availableColours.addLast(dice.getColor()); // re-add the flag to make the color available
        }
        currentNumberOfDice++;
        currentNumberOfEachDice[dice.getColor().ordinal()]++;

    }

}