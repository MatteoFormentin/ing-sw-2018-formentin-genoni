package it.polimi.se2018.alternative_network.newserver.rmi;

import it.polimi.se2018.alternative_network.newserver.AbstractServer2;
import it.polimi.se2018.alternative_network.newserver.Server2;
import it.polimi.se2018.exception.network_exception.server.ServerStartException;
import org.fusesource.jansi.AnsiConsole;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import static org.fusesource.jansi.Ansi.Color.BLUE;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * @author DavideMammarella
 */
public class RMIServer extends AbstractServer2 {

    private boolean started;
    private RMIClientGatherer clientGatherer;

    public RMIServer(Server2 serverController, String host, int port) {
        super(serverController,host,port);
    }

    @Override
    public void startServer() throws ServerStartException {
        setStarted(true);
        try {
            /* Se ci sono problemi di connessione prova ad utilizzare le Factory di socket per creare il registro
            LocateRegistry.createRegistry(getPort(), new RMIClientSocketFactory() {
                @Override
                public Socket createSocket(String host, int port) throws IOException {
                    return null;
                }
            }, new RMIServerSocketFactory() {
                @Override
                public ServerSocket createServerSocket(int port) throws IOException {
                    return null;
                }
            });*/
            LocateRegistry.createRegistry(getPort());
        } catch (RemoteException ex) {
            AnsiConsole.out.println(ansi().fg(BLUE).a("In questo PC è già attivo un Registry su questa porta").reset());
            setStarted(false);
            throw new ServerStartException();
        }
        try {
            clientGatherer = RMIClientGatherer.getSingletonClientGatherer(getServer(),getPort());
        } catch (RemoteException ex) {
            AnsiConsole.out.println(ansi().fg(BLUE).a("RMIClientGatherer non può essere istanziato").reset());
            setStarted(false);
            throw new ServerStartException();
        }
        try {
            Naming.bind("//" + getHost() + ":" + getPort() + "/MyServer", clientGatherer);
        } catch (AlreadyBoundException ex) {
            AnsiConsole.out.println(ansi().fg(BLUE).a("La porta è stata già assegnata").reset());
            setStarted(false);
            throw new ServerStartException();
        } catch (MalformedURLException ex) {
            AnsiConsole.out.println(ansi().fg(BLUE).a("l'URL non è stato inserito correttamente").reset());
            setStarted(false);
            throw new ServerStartException();
        } catch (RemoteException ex) {
            AnsiConsole.out.println(ansi().fg(BLUE).a("Anomalia nel Registry").reset());
            setStarted(false);
            throw new ServerStartException();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // SERVER STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Questo metodo ferma il chiude correttamente il server mantenendo solo il segistry attivo
     */
    public void stopServer() {
        if (isStarted()) {
            try {
                Naming.unbind("//" + getHost() + ":" + getPort() + "/MyServer");
                UnicastRemoteObject.unexportObject(clientGatherer, true);
            } catch (NoSuchObjectException ex) {
                AnsiConsole.out.println(ansi().fg(BLUE).a("Client gatherer è stato già chiuso").reset());
            } catch (MalformedURLException ex) {
                AnsiConsole.out.println(ansi().fg(BLUE).a("l'URL non è stato inserito correttamente").reset());
            } catch (RemoteException ex) {
                AnsiConsole.out.println(ansi().fg(BLUE).a("Anomalia nel Registry").reset());
            } catch (NotBoundException ex) {
                AnsiConsole.out.println(ansi().fg(BLUE).a("Il servizio su questa porta è stato già disattivato").reset());
            }
        }
    }

}