package it.polimi.se2018.list_event.event_received_by_server.event_for_server.event_pre_game;

import it.polimi.se2018.list_event.event_received_by_server.event_for_game.event_controller.ControllerSelectInitialWindowPatternCard;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.EventPreGame;
import it.polimi.se2018.network.RemotePlayer;

public class LoginRequest extends EventPreGame {

    private RemotePlayer remotePleyer;
    private String nickname;

    public LoginRequest(String nickname) {
        this.nickname = nickname;
    }

    public RemotePlayer getRemotePleyer() {
        return remotePleyer;
    }

    public void setRemotePleyer(RemotePlayer remotePlayer) {
        this.remotePleyer = remotePleyer;
    }

    public String getNickname() {
        return nickname;
    }

}
