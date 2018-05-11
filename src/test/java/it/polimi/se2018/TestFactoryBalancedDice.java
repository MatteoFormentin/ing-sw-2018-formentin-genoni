package it.polimi.se2018;

import static org.junit.Assert.*;

import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.FactoryBalancedDice;
import org.junit.*;

public class TestFactoryBalancedDice {
    private FactoryBalancedDice factoryBalancedDice;
    private int[] currentNumberOfEachColor;


    @Before
    public void initDeck() {
        factoryBalancedDice = FactoryBalancedDice.getBalancedDiceFactory();
        currentNumberOfEachColor = new int[factoryBalancedDice.getAvailableColours().size()];
    }

    @Test
    public void testCreateDice() {
        for (int i = 0; i < factoryBalancedDice.getMaxNumberOfDice(); i++) {
            Dice extract = factoryBalancedDice.createDice();
            switch (extract.getColor()) {
                case Yellow:
                    currentNumberOfEachColor[0]++;
                    break;
                case Purple:
                    currentNumberOfEachColor[1]++;
                    break;
                case Red:
                    currentNumberOfEachColor[2]++;
                    break;
                case Green:
                    currentNumberOfEachColor[3]++;
                    break;
                case Blue:
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
}
