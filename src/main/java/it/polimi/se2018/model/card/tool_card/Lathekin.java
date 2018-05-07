package it.polimi.se2018.model.card.tool_card;

/**
 * Tool card Lathekin.
 * <p>
 * Description
 * Muovi  esattamente  due  dadi,  rispettando  tutte  le  restrizioni  di  piazzamento
 *
 * @author Matteo Formentin
 */
public class Lathekin extends ToolCard {
    public Lathekin() {
        super();
        super.setId(3);
        super.setName("Lathekin");
        super.setDescription("Muovi  esattamente  due  dadi,  rispettando  tutte  le  restrizioni  di  piazzamento");
    }

    /**
     * Card effect.
     */
    public void effect() {
    /*
        ****** the Player use this card check state player need the favor token and hasusedtoolcard==false
        select dice from window and go to hand of player
        select dice from window and go to hand of player
        inserDice one of the dice value restriction= false the other true
        insertlast Dice in hand
        end toolCard (reduce favor token of the player )
         */
    }

}
