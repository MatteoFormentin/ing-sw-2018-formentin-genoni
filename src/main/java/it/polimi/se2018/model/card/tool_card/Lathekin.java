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
        super.setID(3);
        super.setName("Lathekin");
        super.setDescription("Muovi  esattamente  due  dadi,  rispettando  tutte  le  restrizioni  di  piazzamento");
    }

    /**
     * Card effect..
     */
    public int effect() {
        return 0;
    }

}
