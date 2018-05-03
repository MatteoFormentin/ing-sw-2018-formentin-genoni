package it.polimi.se2018.model.card;

import it.polimi.se2018.model.card.objectivePrivateCard.ObjectivePrivateCard;
import it.polimi.se2018.model.card.objectivePublicCard.ObjectivePublicCard;
import it.polimi.se2018.model.card.toolCard.ToolCard;
import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

import java.util.Random;
import java.util.TreeSet;

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
        objectivePublicCard = new ObjectivePublicCard[11];
        objectivePrivateCard = new ObjectivePrivateCard[4];
        toolCard = new ToolCard[11];
        windowPatternCard = new WindowPatternCard[23];

        extractedPublic = new TreeSet<>();
        extractedPrivate = new TreeSet<>();
        extractedTool = new TreeSet<>();
        extractedWindow = new TreeSet<>();
    }

    public ObjectivePublicCard drawObjectivePublicCard() {
        return null;
    }

    public ObjectivePrivateCard drawObjectivePrivateCard() {
        return null;
    }

    public ToolCard drawToolCard() {
        return toolCard[extract(11, extractedTool)];
    }

    public WindowPatternCard drawWindowPatternCard() {
        return null;
    }

    private int extract(int bound, TreeSet<Integer> extracted) {
        Random random = new Random();
        int rand;
        do {
            rand = random.nextInt(bound);
        } while (!extracted.contains(rand));
        extracted.add(rand);
        return rand;
    }

}