package it.polimi.se2018.view;

import it.polimi.se2018.view.prova_gui.GUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.awt.*;


import static javafx.application.Application.launch;
import static javafx.application.Platform.exit;

public class Main{



    public static void main(String[] args) {
        System.out.println("Vuogi giocare con GUI o CLI?");
        //se GUI creami un'istanza dell'applicazione gui
        GUI gui= new GUI();
        gui.setUpGUI(args);
    }



}
