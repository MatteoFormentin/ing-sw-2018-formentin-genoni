package it.polimi.se2018.view.gui.gamestage;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.stream.IntStream;

/**
 * stage for show/read the cards in a better resolution
 *
 * @author Luca Genoni
 */
public class ShowValue {
    private Stage stage;
    private int value;
    private boolean displaying;

    public ShowValue(Stage stage) {
        this.stage = stage;
        displaying = false;
    }


    public int displayValuePool() {
        displaying = true;
        value = 0;
        Text text = new Text("Clicca sul valore desiderato");
        GridPane pane = new GridPane();
        VBox vBox = new VBox(text, pane);
        Image valueDice;
        ImageView[] imageView = new ImageView[6];
        for (int i = 0; i < 6; i++) {

            valueDice = new Image("file:src/it.polimi.se2018.resources/dadijpg/" + "Dice" + (i + 1) + ".jpg");
            imageView[i] = new ImageView(valueDice);
            imageView[i].setFitHeight(100);
            imageView[i].setFitWidth(100);
        }
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < 6; i++) {
            pane.add(imageView[i], i % 3, i / 3);
        }
        IntStream.range(0, imageView.length).forEach(i -> imageView[i].setOnMouseClicked(e -> {
            value = i+1;
            stage.close();
        }));
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();
        displaying = false;
        return value;
    }

    public int displayIncreaseDecrease() {
        displaying = true;
        value = 0;
        ImageView[] imageTrun = new ImageView[2];
        imageTrun[0]= new ImageView(new Image("file:src/it.polimi.se2018.resources/utility/TurnUp.png"));
        imageTrun[1]= new ImageView(new Image("file:src/it.polimi.se2018.resources/utility/TurnDown.png"));
        imageTrun[0].setFitWidth(50);
        imageTrun[0].setFitHeight(50);
        imageTrun[1].setFitWidth(50);
        imageTrun[1].setFitHeight(50);
        imageTrun[0].setOnMouseClicked(e -> {
                    value = 1;
                    stage.close();
                }
        );
        imageTrun[1].setOnMouseClicked(e -> {
                    value = -1;
                    stage.close();
                }
        );
        VBox view = new VBox(imageTrun[0],imageTrun[1]);
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.showAndWait();
        displaying = false;
        return value;
    }


    public boolean isDisplaying() {
        return displaying;
    }
}
