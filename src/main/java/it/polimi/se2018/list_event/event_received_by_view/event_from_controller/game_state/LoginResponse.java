package it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state;

import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventClientFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;

public class LoginResponse extends EventClientFromController {

    private boolean loginSuccessFull;
    private String cause;

    public LoginResponse(boolean loginSuccessFull, String cause) {
        this.loginSuccessFull = loginSuccessFull;
        this.cause = cause;
    }

    public boolean isLoginSuccessFull() {
        return loginSuccessFull;
    }

    public String getCause() {
        return cause;
    }

    @Override
    public void acceptControllerEvent(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}