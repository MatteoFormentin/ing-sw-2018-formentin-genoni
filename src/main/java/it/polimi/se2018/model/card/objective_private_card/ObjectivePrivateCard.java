package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceColor;
import it.polimi.se2018.model.dice.Dice;

import java.io.Serializable;

/**
 * Base class for objective private card.
 *
 * @author Matteo Formentin
 */
public abstract class ObjectivePrivateCard implements Serializable {
    private int id;
    private String name;
    private String description;
    private DiceColor diceColor;

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
     * @return Description String.
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

    /**
     * Return card objective color.
     *
     * @return diceColor DiceColor.
     */
    public DiceColor getDiceColor() {
        return diceColor;
    }

    /**
     * Set card objective color.
     *
     * @param diceColor DiceColor.
     */
    public void setDiceColor(DiceColor diceColor) {
        this.diceColor = diceColor;
    }


    /**
     * Calculate card point coming from objective.
     *
     * @param windowPatternCard WindowPatternCard.
     */
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        Cell[][] matrix = windowPatternCard.getMatrix();
        int points = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Dice currentCellDice = matrix[i][j].getDice();
                if (currentCellDice == null) continue;
                if (currentCellDice.getColor() == this.getDiceColor()) {
                    points += currentCellDice.getValue();
                }
            }
        }
        return points;
    }


}