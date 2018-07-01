package it.polimi.se2018.alternative_network.newserver.rmi;

import it.polimi.se2018.exception.network_exception.server.ServerStartException;
import it.polimi.se2018.alternative_network.newserver.AbstractServer2;
import it.polimi.se2018.alternative_network.newserver.Server2;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * COMPLETO SENZA ERRORI
 *
 */
public class RMIServer extends AbstractServer2  {

    private RMIClientGatherer clientGatherer;

    public RMIServer(Server2 serverController, String host, int port) {
        super(serverController,host,port);
    }

    @Override
    public void startServer() throws ServerStartException {
        try {
            /*todo Se ci sono problemi di connessione prova ad utilizzare le Factory di socket per creare il registro
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
            System.out.println("In questo PC è già attivo un Registry su questa porta");
            throw new ServerStartException();
        }
        try {
            // Creo una istanza di RMIClientGatherer, che rappresenta il "servizio" che voglio offrire ai client
            clientGatherer = RMIClientGatherer.getSingletonClientGatherer(getServerController());
        } catch (RemoteException ex) {
            System.out.println("failed to export object, check the Skeletron, The CLientGatherer");
            throw new ServerStartException();
        }
        try {
            // A questo punto comunico al Registry che ho un nuovo servizio da offrire ai client passando a  Naming.rebind(..) :
            //  - una stringa di tipo "//host:port/name" host:port identifica l'indirizzo del Registry, se port è omesso viene utilizzata la porta di default: 1099
            //    >  name, a nostra  scelta, identifica il servizio ed è univoco all'interno del Registry avviato su host:port
            //  - l'oggetto RMIImplementation
            Naming.bind("//" + getHost() + ":" + getPort() + "/MyServer", clientGatherer);
        } catch(AlreadyBoundException ex){
            System.err.println("Port Alreasy bound");
            throw new ServerStartException();
        }catch (MalformedURLException ex) {
            System.err.println(" the name is not an appropriately formatted URL");
            throw new ServerStartException();
        } catch (RemoteException ex) {
            System.err.println("registry could not be contacted");
            throw new ServerStartException();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // SERVER STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Questo metodo ferma il chiude correttamente il server mantenendo solo il segistry attivo
     */
    public void stopServer(){
        try {
            //Tolgo il servizio dalla porta, non viene eliminato il registry
            Naming.unbind("//" + getHost() + ":" + getPort() + "/MyServer");
            //rimuovo l'oggetto client gatherer
            UnicastRemoteObject.unexportObject(clientGatherer,true);
            //ora posso chiudere il registry
            //TODO creare un nuova classe registry da poter resettare, forse troppo dispendioso per tempo
        }catch(NoSuchObjectException ex){
            System.err.println("non è possibile rimuovere l'oggetto");
        } catch (MalformedURLException ex) {
            System.err.println(" the name is not an appropriately formatted URL");
        } catch (RemoteException ex) {
            System.err.println("registry could not be contacted");
        } catch (NotBoundException ex){
            System.err.println("Il servizio su questa porta è stato già disattivato");
        }
    }
}