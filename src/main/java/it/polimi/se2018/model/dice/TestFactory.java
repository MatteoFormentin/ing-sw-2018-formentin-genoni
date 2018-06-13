package it.polimi.se2018.model.dice;


public class TestFactory implements FactoryDice {
    private int value;
    private DiceColor color;
    /**
     * create a dice without any logic
     *
     * @return the dice created
     */
    @Override
    public Dice createDice() {
        if(color==null ||value==0){
            System.err.println("Non hai settato il colore o il valore");
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
    public void removeDice(Dice dice){}

    public void setDiceValueColor(int value, DiceColor color) {
        this.value = value;
        this.color = color;
    }
}
