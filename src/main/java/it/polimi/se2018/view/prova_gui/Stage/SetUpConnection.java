package it.polimi.se2018.view.prova_gui.Stage;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SetUpConnection {

    public static void display(){

        /*StageStyle
                    UTILITY only _, x
                    TRANSPARENT NOTHING and no _, o, x
                    UNIFIED boh
                    UNDECORATED simile a TRANSPARENT ma con sfondo
          modality
                    APPLICATION_MODAL can't turn to previus stage and can't move
                    WINDOW_MODAL
         */
        Stage stageConnection = new Stage(StageStyle.UNDECORATED);
        stageConnection.initModality(Modality.WINDOW_MODAL);
        BackgroundImage myBI= new BackgroundImage(new Image("it/polimi/se2018/resources/Immagine.png",32,32,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        BorderPane borderPaneRoot = new BorderPane();
        borderPaneRoot.setBackground(new Background(myBI));
      /*  BackgroundImage myBI= new BackgroundImage(new Image("it/polimi/se2018/resources/Immagine.png",32,32,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);*/
//then you set to your node
      /*  myContainer.setBackground(new Background(myBI));
        Scene InitConnection = new Scene();
        Background focusBackground = new Background(new BackgroundFill(Color.web("#bbb"), CornerRadii.EMPTY, Insets.EMPTY));


        Label errorMessage =new Label();
        errorMessage.setText(message);

        Button closeButton =new Button("Ok");
        closeButton.setOnAction(e->stageMessage.close());


        VBox layoutMessage = new VBox(20);
        layoutMessage.getChildren().addAll(errorMessage,closeButton);
        layoutMessage.setAlignment(Pos.CENTER);
        layoutMessage.setBackground(focusBackground);
        // group.getChildren().add(layoutMessage);*/

        Scene scene =new Scene(borderPaneRoot);
        stageConnection.setScene(scene);
        stageConnection.showAndWait();
    }
    /*do {
            String ip;
            int port;

            cliMessage.showIpRequest();
            ip = cliParser.parseIp();

            if (ip.equals("0")) {
                if (client.startRMIClient()) {
                    flag = true;
                    cliMessage.showConnectionSuccessful();
                    cliMessage.println();
                } else {
                    cliMessage.showConnectionFailed();
                }

            } else {
                cliMessage.showPortRequest();
                port = cliParser.parseInt();

                if (client.startRMIClient(ip, port)) {
                    flag = true;
                    cliMessage.showConnectionSuccessful();
                    cliMessage.println();
                } else {
                    cliMessage.showConnectionFailed();
                }
            }

        } while (!flag);*/
}
