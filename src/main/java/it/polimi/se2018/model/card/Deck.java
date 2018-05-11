package it.polimi.se2018.model.card;

import it.polimi.se2018.model.card.objective_private_card.*;
import it.polimi.se2018.model.card.objective_public_card.*;
import it.polimi.se2018.model.card.tool_card.*;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCardLoader;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

/**
 * Card factory, extract random card.
 *
 * @author Matteo Formentin
 */

public class Deck {

    private static Deck singleDeck;
    private ArrayList<WindowPatternCard> windowPatternCardsDeck;
    private TreeSet<Integer> extractedPublic;
    private TreeSet<Integer> extractedPrivate;
    private TreeSet<Integer> extractedTool;
    private TreeSet<Integer> extractedWindow;

    private final int objectivePublicCardNumber = 10;
    private final int objectivePrivatedNumber = 5;
    private final int toolCardNumber = 12;
    private int windowPatternCardNumber;


    //return window_pattern_card[extract(23, extractedWindow)];?

    private Deck() {
        windowPatternCardsDeck = new WindowPatternCardLoader().initCard();
        windowPatternCardNumber = windowPatternCardsDeck.size();
        extractedPublic = new TreeSet<>();
        extractedPrivate = new TreeSet<>();
        extractedTool = new TreeSet<>();
        extractedWindow = new TreeSet<>();
    }

    /**
     * Return deck instance.
     * <p>
     * Implement singleton pattern.
     *
     * @return singleDeck Deck instance.
     */
    public static synchronized Deck getDeck() {
        if (singleDeck == null) {
            singleDeck = new Deck();
        }
        return singleDeck;
    }

    /**
     * Reset deck.
     */
    public void resetDeck() {
        extractedPublic = new TreeSet<>();
        extractedPrivate = new TreeSet<>();
        extractedTool = new TreeSet<>();
        extractedWindow = new TreeSet<>();
    }

    /**
     * Get number of cards objective public available
     *
     * @return objectivePublicCardNumber.
     */
    public int getObjectivePublicCardNumber() {
        return objectivePublicCardNumber;
    }

    /**
     * Get number of cards objective private available
     *
     * @return objectivePublicCardNumber.
     */
    public int getObjectivePrivatedNumber() {
        return objectivePrivatedNumber;
    }

    /**
     * Get number of cards window pattern available
     *
     * @return objectivePublicCardNumber.
     */
    public int getWindowPatternCardNumber() {
        return windowPatternCardNumber;
    }

    /**
     * Get number of cards tool available
     *
     * @return objectivePublicCardNumber.
     */
    public int getToolCardNumber() {
        return toolCardNumber;
    }

    /**
     * Extract one random objective public card.
     *
     * @return one random ObjectivePublicCard.
     * @throws IndexOutOfBoundsException if random error.
     */
    public ObjectivePublicCard drawObjectivePublicCard() {
        switch (extractInt(objectivePublicCardNumber, extractedPublic)) {
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
                throw new IndexOutOfBoundsException("Error");
        }
    }

    /**
     * Extract one random objective private card.
     *
     * @return one random ObjectivePrivateCard.
     * @throws IndexOutOfBoundsException if random error.
     */
    public ObjectivePrivateCard drawObjectivePrivateCard() {
        switch (extractInt(objectivePrivatedNumber, extractedPrivate)) {
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
                throw new IndexOutOfBoundsException("Error");
        }
    }

    /**
     * Extract one random tool card.
     *
     * @return one random ToolCard.
     * @throws IndexOutOfBoundsException if random error.
     */
    public ToolCard drawToolCard() {
        switch (extractInt(toolCardNumber, extractedTool)) {
            case 0:
                return new PinzaSgrossatrice();
            case 1:
                return new PennelloEglomise();
            case 2:
                return new AlesatoreLaminaRame();
            case 3:
                return new Lathekin();
            case 4:
                return new TaglierinaCircolare();
            case 5:
                return new PennelloPastaSalda();
            case 6:
                return new Martelletto();
            case 7:
                return new TenagliaRotelle();
            case 8:
                return new RigaSughero();
            case 9:
                return new TamponeDiamantato();
            case 10:
                return new DiluentePastaSalda();
            case 11:
                return new TaglierinaManuale();
            default:
                throw new IndexOutOfBoundsException("Error");
        }
    }

    /**
     * Extract one random window pattern card.
     *
     * @return one random WindowPatternCard.
     * @throws IndexOutOfBoundsException if random error.
     */
    public WindowPatternCard drawWindowPatternCard() {
        int index = extractInt(windowPatternCardNumber, extractedWindow);
        return windowPatternCardsDeck.get(index);
    }

    /**
     * Extract one random integer.
     * Result are stored in TreeSet to avoid repetition
     *
     * @param bound     integer upper bound limit.
     * @param extracted TreeSet contains extracted value.
     * @return one random integer between 0 and bound.
     */
    private int extractInt(int bound, TreeSet<Integer> extracted) {
        Random random = new Random();
        int rand;
        do {
            rand = random.nextInt(bound);
        } while (extracted.contains(rand));
        extracted.add(rand);
        return rand;
    }

}