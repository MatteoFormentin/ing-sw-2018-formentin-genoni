package it.polimi.se2018;

import it.polimi.se2018.model.card.Deck;
import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestDeck {
    private Deck deck;

    @Before
    public void initDeck() {
        deck = Deck.getDeck();
        deck.resetDeck();
    }

    //Testano unicità carte estratte
    @Test
    public void testdDrawObjectivePublicCard() {
        ArrayList<ObjectivePublicCard> cards = new ArrayList<>();
        for (int i = 0; i < deck.getObjectivePublicCardNumber(); i++) {
            ObjectivePublicCard draw = deck.drawObjectivePublicCard();
            assertFalse(cards.contains(draw));
            cards.add(draw);
        }
        assertEquals(cards.size(), deck.getObjectivePublicCardNumber());
    }

    @Test
    public void testdDrawObjectivePrivateCard() {
        ArrayList<ObjectivePrivateCard> cards = new ArrayList<>();
        for (int i = 0; i < deck.getObjectivePrivatedNumber(); i++) {
            ObjectivePrivateCard draw = deck.drawObjectivePrivateCard();
            assertFalse(cards.contains(draw));
            cards.add(draw);
        }
        assertEquals(cards.size(), deck.getObjectivePrivatedNumber());
    }

    @Test
    public void testDrawToolCardCard() {
        ArrayList<ToolCard> cards = new ArrayList<>();
        for (int i = 0; i < deck.getToolCardNumber(); i++) {
            ToolCard draw = deck.drawToolCard();
            assertFalse(cards.contains(draw));
            cards.add(draw);
        }
        assertEquals(cards.size(), deck.getToolCardNumber());
    }

    @Test
    public void testDrawWindowPatternCard() {
        ArrayList<WindowPatternCard> cards = new ArrayList<>();
        for (int i = 0; i < deck.getWindowPatternCardNumber(); i++) {
            WindowPatternCard draw = deck.drawWindowPatternCard();
            assertFalse(cards.contains(draw));
            cards.add(draw);
        }
        assertEquals(cards.size(), deck.getWindowPatternCardNumber());
    }

    @After
    public void resetDeck() {
        deck.resetDeck();
    }

}
