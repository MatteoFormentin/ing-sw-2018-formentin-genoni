package it.polimi.se2018.model.card.windowPatternCard;

import it.polimi.se2018.model.card.windowPatternCard.Cell;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.util.Iterator;

import static it.polimi.se2018.model.dice.DiceColor.*;

public class WindowPatternCardLoader {
    public void load() {
        Cell matrix[][] = new Cell[4][5];
        try {
            JSONObject card = (JSONObject) new JSONParser().parse(new FileReader(
                    "/Users/matteo/Desktop/ing-sw-2018-formentin-genoni-mammarella/src/main/java/it/polimi/se2018/model/card/test.json"
            ));

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

            /*
            //Test Carte
            for (int m = 0; m < 4; m++) {
                for (int n = 0; n < 5; n++) {
                    System.out.print(matrix[m][n].getColorRestriction());
                    System.out.print(matrix[m][n].getNumberRestriction());
                    System.out.print(" ");
                }
                System.out.println();
            }*/

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
