package it.polimi.se2018.list_event.event_received_by_server.event_for_server.event_pre_game;

import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.EventPreGame;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.EventPreGameVisitor;

public class LoginRequest extends EventPreGame {

    private RemotePlayer2 remotePlayer2;
    private String nickname;

    public LoginRequest(String nickname) {
        this.nickname = nickname;
    }

    public RemotePlayer2 getRemotePlayer() {
        return remotePlayer2;
    }

    public void setRemotePleyer(RemotePlayer2 remotePlayer) {
        this.remotePlayer2 = remotePlayer2;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void acceptPreGame(EventPreGameVisitor visitor) {
        visitor.visit(this);
    }
}
