package it.polimi.se2018;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Cell cell = new Cell();
        cell.setValueRestriction(1);

        Dice dice = new Dice(DiceColor.Blue);
        dice.setValue(1);

        try {
            cell.insertDice(dice);
            System.out.println("ok!");

        } catch (Exception ex) {
            System.out.println("error");
        }
    }
}
