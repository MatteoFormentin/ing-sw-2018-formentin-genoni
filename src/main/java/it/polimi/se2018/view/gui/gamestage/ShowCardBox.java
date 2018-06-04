package it.polimi.se2018.view.gui.gamestage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * stage for show/read the cards
 *
 * @author Luca Genoni
 */
public class ShowCardBox {
    public void displayCard(ImageView imageViewToShow, boolean canCanUseCard){
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        ImageView imageViewCard = new ImageView(imageViewToShow.getImage());
        Button cardButton =new Button();
        cardButton.setOnMouseExited(e->{
            stage.close();
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
        stage.setScene(boxMessage);
        stage.showAndWait();
    }
}
