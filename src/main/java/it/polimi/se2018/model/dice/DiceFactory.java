package it.polimi.se2018.model.dice;

import it.polimi.se2018.model.dice.dice_color.DiceColor;

import java.util.LinkedList;
import java.util.Random;

import static it.polimi.se2018.model.dice.dice_color.DiceColor.getDiceColor;
import static it.polimi.se2018.model.dice.dice_color.DiceColor.getNumberOfDiceColors;

/**
 * Factorydice must be created only once at the start of the game
 * @author Luca Genoni
 * @version 1.0
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
            availableColours.addLast(getDiceColor(i));
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
                if (currentNumberOfEachDice[availableColours.get(random).ordinal()]>1) {
                    //found a color available, it stops the loop
                    repeat = false;
                }else if(currentNumberOfEachDice[availableColours.get(random).ordinal()]==1) {
                    //it's the last dice of that color, remove the reference
                    availableColours.remove(random);
                    repeat = false;
                }
            }
            currentNumberOfEachDice[availableColours.get(random).ordinal()]--;
            currentNumberOfDice--;
            Dice dice = new Dice(availableColours.get(random));    //the new dice
            return dice;
        }
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
        /*  useless??? the getDice is done well well see the the test
        if(currentNumberOfDice==numberOfDice){
            //restituisce i restanti 9 dadi
            for(int color; color<getNumberOfDiceColors();color++){
                while(currentNumberOfEachDice[color]!=0){
                    currentNumberOfDice--;
                    currentNumberOfEachDice[color]--;
                    arrayDice[currentNumberOfDice]= new Dice(getDiceColor(color));
                }
            }
        }else{
            for (int i=0; i<numberOfDice; i++) {
                arrayDice[i]=getDice();
            }
        }*/
        for (int i=0; i<numberOfDice; i++) {
            arrayDice[i]=getDice();
        }
        return arrayDice;
    }
    public void reInsertionDice(Dice d){

        if(currentNumberOfEachDice[d.getColor().ordinal()]==0) {

        }

        currentNumberOfEachDice[d.getColor().ordinal()]++;
    }

}