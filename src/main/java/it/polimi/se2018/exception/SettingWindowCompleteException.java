package it.polimi.se2018.exception;
/**
 * The class {@code NumberOfPlayerException} is a subclass of {@code Exception}
 *
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class SettingWindowCompleteException  extends Exception {

    public  SettingWindowCompleteException(){
        super("the window of all players are s");
    }
}

