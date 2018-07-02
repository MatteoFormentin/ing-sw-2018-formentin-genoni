package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.exception.network_exception.server.ServerStartException;

public abstract class AbstractServer2  {

    private final Server2 serverController;
    private final String host;
    private final int port;
    private boolean started;

    protected AbstractServer2(Server2 serverController, String host, int port) {
        this.serverController = serverController;
        this.host = host;
        this.port = port;
        this.started=false;
    }

    public abstract void startServer()throws ServerStartException;

    public abstract void stopServer();

    public Server2 getServerController() {
        return serverController;
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
