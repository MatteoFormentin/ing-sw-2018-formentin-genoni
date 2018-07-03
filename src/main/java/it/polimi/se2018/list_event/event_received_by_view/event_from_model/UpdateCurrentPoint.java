package it.polimi.se2018.list_event.event_received_by_view.event_from_model;

/**
 * Extends EventView, updates all the tool cards
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateCurrentPoint extends EventViewFromModel {

    private String[] description;
    private int[] info;

    public UpdateCurrentPoint(int[] info, String[] description ) {
        this.description =description;
        this.info = info;
    }

    public String[] getDescription() {
        return description;
    }

    public String getDescription(int index) {
        return description[index];
    }

    public int[] getInfo() {
        return info;
    }

    public int getInfo(int index) {
        return info[index];
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}
