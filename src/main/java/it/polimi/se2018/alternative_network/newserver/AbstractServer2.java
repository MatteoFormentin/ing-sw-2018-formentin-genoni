package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.exception.network_exception.server.ServerStartException;

/**
 * @author DavideMammarella
 * @author Luca Genoni
 */
public abstract class AbstractServer2 {

    private final Server2 server;
    private final String host;
    private final int port;
    private boolean started;

    public AbstractServer2(Server2 server, String host, int port) {
        this.server = server;
        this.host = host;
        this.port = port;
    }

    public abstract void startServer() throws ServerStartException;

    public abstract void stopServer();

    public Server2 getServer() {
        return server;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
