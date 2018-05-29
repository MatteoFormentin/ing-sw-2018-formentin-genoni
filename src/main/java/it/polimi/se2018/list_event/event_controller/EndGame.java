package it.polimi.se2018.list_event.event_controller;

public class EndGame extends EventController {
    //from EventView private String nicknamPlayer;
    //from EventView private Model model;
    @Override
    public void accept(VisitorEventFromController visitor) {
        visitor.visit(this);
    }
}
