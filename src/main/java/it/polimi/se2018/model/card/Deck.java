package it.polimi.se2018.model.card;

import it.polimi.se2018.model.card.objective_private_card.*;
import it.polimi.se2018.model.card.objective_public_card.*;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

import java.util.Random;
import java.util.TreeSet;

/**
 * Gestisce le carte e l'estrazione delle stesse.
 *
 * @author Matteo Formentin
 */

public class Deck {
    private ObjectivePublicCard[] objectivePublicCard;

    private ToolCard[] toolCard;

    private ObjectivePrivateCard[] objectivePrivateCard;

    private WindowPatternCard[] windowPatternCard;

    private TreeSet<Integer> extractedPublic;
    private TreeSet<Integer> extractedPrivate;
    private TreeSet<Integer> extractedTool;
    private TreeSet<Integer> extractedWindow;

    //return windowPatternCard[extract(23, extractedWindow)];?

    public Deck() {
        objectivePublicCard = new ObjectivePublicCard[10];
        objectivePrivateCard = new ObjectivePrivateCard[5];
        toolCard = new ToolCard[11];
        windowPatternCard = new WindowPatternCard[24];

        extractedPublic = new TreeSet<>();
        extractedPrivate = new TreeSet<>();
        extractedTool = new TreeSet<>();
        extractedWindow = new TreeSet<>();
    }

    /**
     * Estrae una carta Obiettivo pubblico casuale.
     *
     * @return one random ObjectivePublicCard.
     * @throws IndexOutOfBoundsException se si verifica un errore nel random.
     */
    public ObjectivePublicCard drawObjectivePublicCard() {
        switch (extractInt(10, extractedPublic)) {
            case 0:
                return new DifferentColorRow();
            case 1:
                return new DifferentColorColumn();
            case 2:
                return new DifferentNumberRow();
            case 3:
                return new DifferentNumberColumn();
            case 4:
                return new LightNumber();
            case 5:
                return new MidNumber();
            case 6:
                return new DarkNumber();
            case 7:
                return new DifferentNumber();
            case 8:
                return new ColoredDiagonal();
            case 9:
                return new DifferentColor();
            default:
                throw new IndexOutOfBoundsException("Errore nel random");
        }
    }

    /**
     * Estrae una carta Obiettivo privato casuale.
     *
     * @return one random ObjectivePrivateCard.
     * @throws IndexOutOfBoundsException se si verifica un errore nel random.
     */
    public ObjectivePrivateCard drawObjectivePrivateCard() {
        switch (extractInt(4, extractedPrivate)) {
            case 0:
                return new RedObjectivePrivateCard();
            case 1:
                return new YellowObjectivePrivateCard();
            case 2:
                return new GreenObjectivePrivateCard();
            case 3:
                return new BlueObjectivePrivateCard();
            case 4:
                return new PurpleObjectivePrivateCard();
            default:
                throw new IndexOutOfBoundsException("Errore nel random");
        }
    }

    /**
     * Estrae una carta Utensile pubblico casuale.
     *
     * @return one random ToolCard.
     * @throws IndexOutOfBoundsException se si verifica un errore nel random.
     */
    public ToolCard drawToolCard() {
        return null;
    }

    public WindowPatternCard drawWindowPatternCard() {
        return null;
    }

    /**
     * Estrae un intero casuale compreso tra 0 e bound. Salva i risultati in un
     * TreeSet per evitare ripetizioni
     *
     * @param bound     limite superiore intero.
     * @param extracted TreeSet contenenete i numeri già estratti.
     * @return one random integer between 0 and bound.
     */
    private int extractInt(int bound, TreeSet<Integer> extracted) {
        Random random = new Random();
        int rand;
        do {
            rand = random.nextInt(bound);
        } while (!extracted.contains(rand));
        extracted.add(rand);
        return rand;
    }

}