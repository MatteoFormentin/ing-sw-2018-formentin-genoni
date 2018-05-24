package it.polimi.se2018.model.dice;


public class TestFactory extends FactoryDice {
    private int value;
    private DiceColor color;
    /**
     * create a dice without any logic
     *
     * @return the dice created
     */
    @Override
    public Dice createDice() {
        if(color==null){
            System.err.println("Non hai settato il colore del nuovo dado, fai TestFactory.setColor, volendo puoi anche settare il valore con TestFactory.setValue");
            return null;
        }
        if(value==0) return new Dice(color);
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

    public void setValue(int value) {
        this.value = value;
    }

    public void setColor(DiceColor color) {
        this.color = color;
    }
}
