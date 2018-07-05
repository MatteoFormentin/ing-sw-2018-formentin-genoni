package it.polimi.se2018.network;

import java.io.Serializable;

/**
 * Class that define a Socket Object.
 * This class will be used to manage every socket object.
 *
 * @author Matteo Formentin
 * @author DavideMammarella
 */
public class SocketObject implements Serializable {

    //------------------------------------------------------------------------------------------------------------------
    // TYPE STRING ACCEPTED
    // - Ack
    // - Nack
    // - EventClient
    // - EventController
    //------------------------------------------------------------------------------------------------------------------

    private String type;
    private String stringField; //Contains one string, for example nickname
    private Object object;

    //------------------------------------------------------------------------------------------------------------------
    // GETTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for Socket Object type.
     * Only a set of type will be accepted (look at the class for the legend)
     *
     * @return string that link an event (that will be unleashed after translation).
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for Socket Object object.
     *
     * @return object written on the OutputStream.
     */
    public Object getObject() {
        return object;
    }

    /**
     * Getter for Socket Object string field.
     *
     * @return string written in the Object (like a nickname).
     */
    public String getStringField() {
        return stringField;
    }

    //------------------------------------------------------------------------------------------------------------------
    // SETTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Setter for Socket Object type.
     * Only a set of type will be accepted (look at the class for the legend)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Setter for Socket Object object.
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * Setter for Socket Object string field.
     */
    public void setStringField(String stringField) {
        this.stringField = stringField;
    }
}
