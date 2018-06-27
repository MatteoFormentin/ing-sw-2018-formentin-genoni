package it.polimi.se2018.model.card;

import it.polimi.se2018.controller.effect.*;
import it.polimi.se2018.model.card.objective_private_card.*;
import it.polimi.se2018.model.card.objective_public_card.*;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCardLoader;

import java.util.ArrayList;
import java.util.LinkedList;
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
        ToolCard newToolCard = new ToolCard();
        LinkedList<EffectGame> effect = new LinkedList<>();
        switch (extractInt(toolCardNumber, extractedTool)) {
            case 0:
                newToolCard.setId(0);
                newToolCard.setName("Pinza Sgrossatrice");
                newToolCard.setDescription("Dopo aver scelto un dado, aumenta o dominuisci il valore del dado scelto di 1\n" +
                        "Non puoi cambiare un 6 in 1 o un 1 in 6");
                //-------------load effect tool
                effect.addLast(new DicePoolEffect(true));
                effect.addLast(new SelectValue(false));
                effect.addLast(new InsertDice(true,true,true,true));
                newToolCard.setListEffect(effect);
                //-------------load preCheck tool
                newToolCard.setCheck(true,true);
                return newToolCard;
            case 1:
                newToolCard.setId(1);
                newToolCard.setName("Pennello per Eglomise");
                newToolCard.setDescription("Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore.\n" +
                        "Devi rispettare tutte le altre restrizioni di piazzamento");
                effect.addLast(new RemoveDiceFromWindow(false));
                effect.addLast(new InsertDice(true,false,true,false));
                newToolCard.setListEffect(effect);
                newToolCard.setCheck(false,1);
                return newToolCard;
            case 2:
                newToolCard.setId(2);
                newToolCard.setName("Alesatore per lamina di rame");
                newToolCard.setDescription("Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore\n" +
                        "Devi rispettare tutte le altre restrizioni di piazzamento");
                effect.addLast(new RemoveDiceFromWindow(false));
                effect.addLast(new InsertDice(true,true,false,false));
                newToolCard.setListEffect(effect);
                newToolCard.setCheck(false,1);
                return newToolCard;
            case 3:
                newToolCard.setId(3);
                newToolCard.setName("Lathekin");
                newToolCard.setDescription("Muovi  esattamente  due  dadi,  rispettando  tutte  le  restrizioni  di  piazzamento");
                effect.addLast(new RemoveDiceFromWindow(false));
                effect.addLast(new InsertDice(true,true,true,false));
                effect.addLast(new RemoveDiceFromWindow(false));
                effect.addLast(new InsertDice(true,true,true,false));
                newToolCard.setListEffect(effect);
                newToolCard.setCheck(false,2);
                return newToolCard;
            case 4:
                newToolCard.setId(4);
                newToolCard.setName("Taglierina Circolare");
                newToolCard.setDescription("Dopo aver scelto un dado, scambia quel dado con un dado sul Tracciato dei Round");
                effect.addLast(new DicePoolEffect(true));
                effect.addLast(new RoundTrackEffect(true));
                effect.addLast(new InsertDice(true,true,true,true));
                newToolCard.setListEffect(effect);
                newToolCard.setCheck(true,true,true);
                return newToolCard;
            case 5:
                newToolCard.setId(5);
                newToolCard.setName("Pennello per Pasta Salda");
                newToolCard.setDescription("Dopo aver scelto un dado, tira nuovamente quel dado\n" +
                        "Se non puoi piazzarlo, riponilo nella Riserva");
                effect.addLast(new DicePoolEffect(true));
                effect.addLast(new ChangeDiceValue(true));
                newToolCard.setCheck(true,true);
                newToolCard.setListEffect(effect);
                return newToolCard;
            case 6:
                newToolCard.setId(6);
                newToolCard.setName("Martelletto");
                newToolCard.setDescription("Tira nuovamente tutti i dadi della Riserva\n" +
                        "Questa carta può essera usata solo durante il tuo secondo turno, prima di scegliere il secondo dado");
                effect.addLast(new DicePoolEffect(false));
                newToolCard.setListEffect(effect);
                newToolCard.setCheck(true,false,false,false);
                return newToolCard;
            case 7:
                newToolCard.setId(7);
                newToolCard.setName("Tenaglia a Rotelle");
                newToolCard.setDescription("Dopo il tuo primo turno scegli immediatamente un altro dado\n" +
                        "Salta il tuo secondo turno in questo round");
                effect.addLast(new EndTurn(true));
                effect.addLast(new DicePoolEffect(true));
                effect.addLast(new InsertDice(true,true,true,true));
                newToolCard.setListEffect(effect);
                newToolCard.setCheck(false,false,false,true);
                return newToolCard;
            case 8:
                newToolCard.setId(8);
                newToolCard.setName("Riga in Sughero");
                newToolCard.setDescription("Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente a un altro dado\n" +
                        "Devi rispettare tutte le restrizioni di piazzamento");
                effect.addLast(new DicePoolEffect(true));
                effect.addLast(new InsertDice(false,true,true,true));
                newToolCard.setListEffect(effect);
                newToolCard.setCheck(true,true);
                return newToolCard;
            case 9:
                newToolCard.setId(9);
                newToolCard.setName("Tampone Diamantato");
                newToolCard.setDescription("Dopo aver scelto un dado, giralo sulla faccia opposta \n" +
                        "6 diventa 1, 5 diventa 2, 4 diventa 3 ecc.");
                effect.addLast(new DicePoolEffect(true));
                effect.addLast(new ChangeDiceValue(false));
                effect.addLast(new InsertDice(true,true,true,true));
                newToolCard.setListEffect(effect);
                newToolCard.setCheck(true,true);
                return newToolCard;
            case 10:
                newToolCard.setId(10);
                newToolCard.setName("Diluente per Pasta Salda");
                newToolCard.setDescription("Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Sacchetto\n" +
                        "Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento");
                effect.addLast(new DicePoolEffect(true));
                effect.addLast(new FactoryEffect());
                effect.addLast(new SelectValue(true));
                effect.addLast(new InsertDice(true,true,true,true));
                newToolCard.setListEffect(effect);
                newToolCard.setCheck(true,true);
                return newToolCard;
            case 11:
                newToolCard.setId(11);
                newToolCard.setName("Taglierina Manuale");
                newToolCard.setDescription("Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato dei Round\n" +
                        "Devi rispettare tutte le restrizioni di piazzamento");

                effect.addLast(new RoundTrackEffect(false));
                effect.addLast(new RemoveDiceFromWindow(true));
                effect.addLast(new InsertDice(true,true,true,false));
                effect.addLast(new RemoveDiceFromWindow(true));
                effect.addLast(new InsertDice(true,true,true,false));
                newToolCard.setListEffect(effect);
                newToolCard.setCheck(false,2);
                return newToolCard;
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