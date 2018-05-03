package it.polimi.se2018.model.card.tool_card;

/**
 * Tool card Alesatore lamina di rame.
 * <p>
 * Description
 * Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore
 * Devi rispettare tutte le altre restrizioni di piazzamento
 *
 * @author Matteo Formentin
 */
public class AlesatoreLaminaRame extends ToolCard {
    public AlesatoreLaminaRame() {
        super();
        super.setId(2);
        super.setName("Alesatore per lamina di rame");
        super.setDescription("Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore\n" +
                "Devi rispettare tutte le altre restrizioni di piazzamento");
    }

    /**
     * Card effect.
     */
    public void effect() {
    }

}
