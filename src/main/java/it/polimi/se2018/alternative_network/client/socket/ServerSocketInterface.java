package it.polimi.se2018.alternative_network.client.socket;

import it.polimi.se2018.network.SocketObject;

public interface ServerSocketInterface {

    void send ( SocketObject socketObject );

    void start();
}
