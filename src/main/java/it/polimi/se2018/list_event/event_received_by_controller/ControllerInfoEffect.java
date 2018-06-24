package it.polimi.se2018.list_event.event_received_by_controller;

/**
 * Extends EventController, the controller receives the selected window's coordinates
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class ControllerInfoEffect extends EventController {
    private int[] Info;


    public int[] getInfo() {
        return Info;
    }

    public void setInfo(int[] info) {
        Info = info;
    }

    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
