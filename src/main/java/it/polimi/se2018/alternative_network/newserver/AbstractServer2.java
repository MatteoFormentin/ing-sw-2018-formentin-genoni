package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.exception.network_exception.server.ServerStartException;

/**
 * @author DavideMammarella
 * @author Luca Genoni
 */
public interface AbstractServer2 {

    public void startServer() throws ServerStartException;

    public void stopServer();

    public Server2 getServer();

    public String getHost();

    public int getPort();

    public boolean isStarted();

    public void setStarted(boolean started);
}
