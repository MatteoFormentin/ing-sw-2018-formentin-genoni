package it.polimi.se2018.model.card.tool_card;

/**
 * Tool card Pennello per Eglomise.
 * <p>
 * Description
 * Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore.
 * Devi rispettare tutte le altre restrizioni di piazzamento
 *
 * @author Matteo Formentin
 */
public class PennelloEglomise extends ToolCard {
    public PennelloEglomise() {
        super();
        super.setId(1);
        super.setName("Pennello per Eglomise");
        super.setDescription("Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore.\n" +
                "Devi rispettare tutte le altre restrizioni di piazzamento");
    }

    /**
     * Card effect.
     */
    public void effect() {
        /*
        ****** the Player use this card check state player need the favor token and hasusedtoolcard==false
        select dice from window and go to hand of player
        inserDice Color restriction= false the other true
        end toolCard (reduce favor token of the player )
         */
    }

}
