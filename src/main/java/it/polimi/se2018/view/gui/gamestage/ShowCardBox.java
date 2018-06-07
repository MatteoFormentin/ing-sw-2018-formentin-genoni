package it.polimi.se2018.view.gui.gamestage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * stage for show/read the cards
 *
 * @author Luca Genoni
 */
class ShowCardBox {
    private boolean clicked;
    private Stage stage;
    private int maxWidth,maxHeigh;
    ShowCardBox(int maxWidth, int maxHeigh) {
        this.maxWidth=maxWidth;
        this.maxHeigh=maxHeigh;
        stage = new Stage(StageStyle.TRANSPARENT);
        clicked=false;
        stage.initModality(Modality.APPLICATION_MODAL);
    }

     boolean displayCard(ImageView imageViewToShow, boolean canCanUseCard){
        clicked=false;
        stage.setAlwaysOnTop(true);
        Image big = imageViewToShow.getImage();
        ImageView imageViewCard = new ImageView(big);

        Pane cardPane =new Pane(imageViewCard);
        imageViewCard.setOnMouseExited(e->stage.close());
        if (canCanUseCard==true){
            imageViewCard.setOnMouseClicked(e->{
                clicked=true;
                stage.close();
            });
        }else{
            imageViewCard.setOnMouseClicked(e->{
                stage.close();
            });
        }
        imageViewCard.setFitHeight(maxHeigh);
        imageViewCard.setFitWidth(maxWidth);

        //Setting the preserve ratio of the image view
        imageViewCard.setPreserveRatio(true);
        Scene scene =new Scene(cardPane);
        stage.setScene(scene);
        stage.showAndWait();
        return clicked;
    }


}
