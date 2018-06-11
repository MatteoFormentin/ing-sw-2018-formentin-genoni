package it.polimi.se2018;

import static org.junit.Assert.*;

import it.polimi.se2018.model.dice.BalancedFactoryDice;
import it.polimi.se2018.model.dice.Dice;
import org.junit.*;

import java.util.ArrayList;

public class TestBalancedFactoryDice {
    private BalancedFactoryDice factoryBalancedDice;
    private int[] currentNumberOfEachColor;


    @Before
    public void initDeck() {
        factoryBalancedDice = new BalancedFactoryDice();
        currentNumberOfEachColor = new int[factoryBalancedDice.getAvailableColours().size()];
    }

    @Test
    public void testCreateDice() {
        for (int i = 0; i < factoryBalancedDice.getMaxNumberOfDice(); i++) {
            Dice extract = factoryBalancedDice.createDice();
            switch (extract.getColor()) {
                case YELLOW:
                    currentNumberOfEachColor[0]++;
                    break;
                case PURPLE:
                    currentNumberOfEachColor[1]++;
                    break;
                case RED:
                    currentNumberOfEachColor[2]++;
                    break;
                case GREEN:
                    currentNumberOfEachColor[3]++;
                    break;
                case BLUE:
                    currentNumberOfEachColor[4]++;
                    break;
            }
        }
        assertEquals(currentNumberOfEachColor[0] +
                        currentNumberOfEachColor[1] +
                        currentNumberOfEachColor[2] +
                        currentNumberOfEachColor[3] +
                        currentNumberOfEachColor[4],
                factoryBalancedDice.getMaxNumberOfDice()
        );

        for (int i :
                currentNumberOfEachColor) {
            assertEquals(18, i);
        }
    }

    @Test
    public void testRemoveDice() {
        ArrayList<Dice> diceArrayList = new ArrayList<>();
        for (int i = 0; i < factoryBalancedDice.getMaxNumberOfDice(); i++) {
            diceArrayList.add(factoryBalancedDice.createDice());
        }

        for (Dice dice : diceArrayList) {
            factoryBalancedDice.removeDice(dice);
        }

        assertEquals(90, factoryBalancedDice.getCurrentNumberOfDice());
    }
}
