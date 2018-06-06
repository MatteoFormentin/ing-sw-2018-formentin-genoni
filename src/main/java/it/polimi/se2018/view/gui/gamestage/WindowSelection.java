package it.polimi.se2018.view.gui.gamestage;

import it.polimi.se2018.view.gui.stage.AlertMessage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * stage for select the window.
 *
 * @author Luca Genoni
 */
public class WindowSelection {
    private int selected;
    private Stage stage;
    private int index;
    private HBox windowChoise;
    private AlertMessage popup;

    WindowSelection() {

        popup = new AlertMessage();
    }

    public void displayInit(HBox tool,HBox publicO, HBox privateO){

    }

    int displayCard(ImageView imageViewToShow, boolean canCanUseCard){
        ImageView imageViewCard = new ImageView(imageViewToShow.getImage());
        Pane cardPane =new Pane(imageViewCard);
        imageViewCard.setOnMouseExited(e->stage.close());
        if (canCanUseCard==true){
            imageViewCard.setOnMouseClicked(e->{
                stage.close();
            });
        }else{
            imageViewCard.setOnMouseClicked(e->{
                stage.close();
            });
        }
        imageViewCard.setFitHeight(500);
        imageViewCard.setFitWidth(1200);

        //Setting the preserve ratio of the image view
        imageViewCard.setPreserveRatio(true);
        Scene scene =new Scene(cardPane);
        stage.setScene(scene);
        stage.showAndWait();
        return index;
    }

}
