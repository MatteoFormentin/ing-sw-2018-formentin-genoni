package it.polimi.se2018.alternative_network.client.socket;

import it.polimi.se2018.alternative_network.client.ServerSocketInterface;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.SocketObject;
import it.polimi.se2018.view.UIInterface;

import java.io.*;
import java.net.Socket;


public class NetworkHandler extends Thread implements ServerSocketInterface {

    // comunicazione con il socket
    private Socket clientConnection;

    // stream di input
    private ObjectInputStream inputStream;

    private UIInterface view;


    public NetworkHandler(String host, int port, UIInterface view) {
        try {
            clientConnection = new Socket(host,port);
            System.out.println("Connected.");
            inputStream = new ObjectInputStream(clientConnection.getInputStream());
            this.view =view;
        } catch (Exception ex) {
            //TODO stoppare il ciclo while in attesa degli eventi da cli
            ex.printStackTrace();
            view.errPrintln(ex.getMessage());
        }
    }

    @Override
    public void run () {
        Thread.currentThread().setName("Socket Client Thread");
        boolean loop = true;
        while (loop && !this.clientConnection.isClosed()) {
            try {
                SocketObject received = (SocketObject) inputStream.readObject();
                String type = received.getType();

                if (type==null){
                    loop = false;
                    this.stopConnection();
                }else{
                    if (type.equals("Event")) {
                        view.showEventView((EventView) received.getObject());
                    }
                    if (type.equals("Ack")) {
                        view.showEventView((EventView) received.getObject());
                    }
                    if (type.equals("Event")) {
                        view.showEventView((EventView) received.getObject());
                    }
                    if (type.equals("Ping")) {
                        //TODO for ping
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to write on the output stream the object that will be sent to the server.
     *
     * @param socketObject object that will be traduced on the server (it contain an event).
     */
    public synchronized void send ( SocketObject socketObject ) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientConnection.getOutputStream());
            outputStream.writeObject(socketObject);
            outputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized void stopConnection () {
        if ( !clientConnection.isClosed() ) {
            try {
                this.clientConnection.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Connection closed!");
        }
    }
}