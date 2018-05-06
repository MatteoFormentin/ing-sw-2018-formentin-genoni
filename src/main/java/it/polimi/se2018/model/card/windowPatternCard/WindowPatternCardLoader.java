package it.polimi.se2018.model.card.windowPatternCard;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;


import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import static it.polimi.se2018.model.dice.DiceColor.*;

/**
 * Provide JSON window pattern card import functions.
 *
 * @author Matteo Formentin
 */

public class WindowPatternCardLoader {

    /**
     * Load all card from json_window_pattern_card folder.
     *
     * @return cards ArrayList<WindowPatternCard>.
     */
    public ArrayList<WindowPatternCard> initCard() {
        final String path = "src/main/java/it/polimi/se2018/resources/json_window_pattern_card/";

        final File folder = new File(path);
        ArrayList<WindowPatternCard> cards = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            try {
                cards.add(jsonCardLoader(new FileReader(path + fileEntry.getName())));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        /*
        //Test lettura carte
        for (WindowPatternCard card : cards) {
            System.out.print(card.getName());
            System.out.print(" - ");
            System.out.println(card.getLevel());

            Cell[][] matrix = card.getMatrix();

            for (int m = 0; m < 4; m++) {
                for (int n = 0; n < 5; n++) {
                    System.out.print(matrix[m][n].getColorRestriction());
                    System.out.print(matrix[m][n].getNumberRestriction());
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
        */

        return cards;
    }

    /**
     * Load card from single JSON file.
     *
     * @param file FileReader object generated from path.
     * @return card WindowPatternCard.
     * @throws Exception file not found or json parsing error.
     */
    private WindowPatternCard jsonCardLoader(FileReader file) throws Exception {
        Cell[][] matrix = new Cell[4][5];

        JSONObject card = (JSONObject) new JSONParser().parse(file);


        String cardName = (String) card.get("name");
        Long cardLevelLong = (Long) card.get("difficult");
        int cardLevel = cardLevelLong.intValue();

        JSONArray row = (JSONArray) card.get("board");
        Iterator rowIterator = row.iterator();
        int i = 0;
        while (rowIterator.hasNext()) {
            int j = 0;
            JSONArray col = (JSONArray) rowIterator.next();
            Iterator colIterator = col.iterator();

            while (colIterator.hasNext()) {
                String read = (String) colIterator.next();

                matrix[i][j] = new Cell();

                switch (read) {
                    case "false":
                        break;
                    case "1":
                        matrix[i][j].setNumberRestriction(1);
                        break;
                    case "2":
                        matrix[i][j].setNumberRestriction(2);
                        break;
                    case "3":
                        matrix[i][j].setNumberRestriction(3);
                        break;
                    case "4":
                        matrix[i][j].setNumberRestriction(4);
                        break;
                    case "5":
                        matrix[i][j].setNumberRestriction(5);
                        break;
                    case "6":
                        matrix[i][j].setNumberRestriction(6);
                        break;
                    case "Red":
                        matrix[i][j].setColorRestriction(Red);
                        break;
                    case "Yellow":
                        matrix[i][j].setColorRestriction(Yellow);
                        break;
                    case "Green":
                        matrix[i][j].setColorRestriction(Green);
                        break;
                    case "Blue":
                        matrix[i][j].setColorRestriction(Blue);
                        break;
                    case "Purple":
                        matrix[i][j].setColorRestriction(Purple);
                        break;
                }
                j++;
            }
            i++;
        }
        WindowPatternCard windowPatternCard = new WindowPatternCard();
        windowPatternCard.setName(cardName);
        windowPatternCard.setLevel(cardLevel);
        windowPatternCard.setMatrix(matrix);

        return windowPatternCard;
    }
}
