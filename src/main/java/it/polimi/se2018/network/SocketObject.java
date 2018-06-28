package it.polimi.se2018.network;

import java.io.Serializable;

public class SocketObject implements Serializable {

    private String type;
    /*
    Type:
     - Ack
     - Nack
     - EventView
     - EventController

     */

    private String stringField; //Contains one string, for example nickname

    private Object object;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

}
