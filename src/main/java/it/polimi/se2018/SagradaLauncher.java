package it.polimi.se2018;

import it.polimi.se2018.alternative_network.newserver.Server2;
import it.polimi.se2018.network.client.Client;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.view.cli.CliController;
import it.polimi.se2018.view.cli.CliParser;
import it.polimi.se2018.view.gui.ControllerGUI;


public class SagradaLauncher {
    public static void main(String[] args) {
        CliParser cliParser = new CliParser();
        System.out.print("Digita 0 per il funzionante, 1 per il testing: ");
        if(cliParser.parseInt(1) == 0){
            System.out.println("Benvenuto su Sagrada.");
            System.out.print("Digita 0 per avviare il server, uno per il client: ");
            if(cliParser.parseInt(1)==0) Server.main(args);
            else{
                String[] args2 = new String[1];
                System.out.print("Digita 0 per avviare il cli, uno per la gui: ");
                switch (cliParser.parseInt(1)) {
                    case 0:
                        args2[0] = "cli";
                        break;
                    case 1:
                        args2[0] = "gui";
                        break;
                }
                Client.main(args2);
            }
        }else{
            System.out.println("TEST.");
            System.out.print("Digita 0 per avviare il server, uno per il client: ");
            if(cliParser.parseInt(1)==0) Server2.main(args);
            else{
                System.out.print("Digita 0 per la cli, 1 per il gui");
                switch (cliParser.parseInt(1)) {
                    case 0:
                        CliController.main(args);
                        break;
                    case 1:
                        ControllerGUI.main(args);
                        break;
                }
            }
        }
    }
}