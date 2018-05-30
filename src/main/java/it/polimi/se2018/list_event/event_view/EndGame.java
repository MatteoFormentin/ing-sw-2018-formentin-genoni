package it.polimi.se2018.list_event.event_view;

public class EndGame extends EventView {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;
    @Override
    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
