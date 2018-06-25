package it.polimi.se2018.view.gui.gamestage;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * stage for show/read the cards in a better resolution
 *
 * @author Luca Genoni
 */
class ShowValue {
    private int value;
    private Stage stage;

    ShowValue(Stage owner, int maxWidth, int maxHeight) {
        stage = new Stage(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
    }

    int displayCard(ImageView imageViewToShow, boolean canCanUseCard) {
      /*  clicked = false;
        Image big = imageViewToShow.getImage();
        ImageView imageViewCard = new ImageView(big);

        Pane cardPane = new Pane(imageViewCard);
        imageViewCard.setOnMouseExited(e -> stage.close());
        if (canCanUseCard && clickIsOn) {
            imageViewCard.setOnMouseClicked(e -> {
                clicked = true;
                setClickIsOn(false);
                stage.close();
            });
        } else {
            imageViewCard.setOnMouseClicked(e -> stage.close());
        }
        imageViewCard.setFitHeight(maxHeight);
        imageViewCard.setFitWidth(maxWidth);
        //Setting the preserve ratio of the image view
        imageViewCard.setPreserveRatio(true);
        Scene scene = new Scene(cardPane);
        stage.setScene(scene);
        stage.showAndWait();*/
        return value;
    }


}
