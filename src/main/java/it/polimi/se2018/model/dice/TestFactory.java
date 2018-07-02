package it.polimi.se2018.model.dice;


import it.polimi.se2018.exception.gameboard_exception.tool_exception.ValueDiceWrongException;

public class TestFactory implements FactoryDice {
    private int value;
    private DiceColor color;
    /**
     * create a dice without any logic
     *
     * @return the dice created
     */
    @Override
    public Dice createDice() throws ValueDiceWrongException{
        if(color==null ||value==0){
            return null;
        }
        Dice dice= new Dice(color);
        dice.setValue(value);
        return dice;
    }

    /**
     * nothing
     *
     * @param dice to delete
     */
    @Override
    public void removeDice(Dice dice){
        throw new UnsupportedOperationException();
    }

    public void setDiceValueColor(int value, DiceColor color) {
        this.value = value;
        this.color = color;
    }
}
