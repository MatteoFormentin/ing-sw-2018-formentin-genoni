package it.polimi.se2018.view.prova_gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * stage for show/read the cards
 *
 * @author Luca Genoni
 */
public class ShowCardBox {
    public static void displayCard(ImageView imageViewToShow, boolean canCanUseCard){
        Stage stageCard = new Stage(StageStyle.TRANSPARENT);
        stageCard.initModality(Modality.APPLICATION_MODAL);
        ImageView imageViewCard = new ImageView(imageViewToShow.getImage());
        Button cardButton =new Button();
        cardButton.setOnMouseExited(e->{
            stageCard.close();
        });
        cardButton.setOnAction(e->{
            System.out.println("è stata cliccata la carta ");
        });

        imageViewCard.setFitHeight(600);
        imageViewCard.setFitWidth(700);

        //Setting the preserve ratio of the image view
        imageViewCard.setPreserveRatio(true);
        cardButton.setGraphic(imageViewCard);
        Scene boxMessage =new Scene(cardButton);
        stageCard.setScene(boxMessage);
        stageCard.showAndWait();
    }
}
