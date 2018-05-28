package it.polimi.se2018.network;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.event.list_event.EventView;
import it.polimi.se2018.network.server.ServerController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Class that define a single GameRoom on the Server.
 * Every GameRoom has one Game (Controller) and multiple players.
 *
 * @author DavideMammarella
 */

public class GameRoom implements Runnable {


    public void run() {
        System.out.println("Room started...");

        // FAR PARTIRE THREAD
        /*
        while (System.currentTimeMillis() - roomWaitingTime <= roomStartTimeout) {
            try {
                if (this.roomStartTimeout > 0)
                    Thread.sleep(this.roomStartTimeout);
                if (!Thread.currentThread().isInterrupted())
                    //ROOM STARTER
                    GameRoom.this.startRoom();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }*/


    }
}
