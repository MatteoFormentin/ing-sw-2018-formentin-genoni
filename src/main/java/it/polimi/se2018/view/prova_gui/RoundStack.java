package it.polimi.se2018.view.prova_gui;

import it.polimi.se2018.model.dice.DiceColor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.util.Random;

public class RoundStack {

    private ImageView[] diceStackThisRound;
    private static int index;
/*
    public static int displayDiceStack(ImageView[] diceStackThisRound){
        Stage stageMessage = new Stage(StageStyle.UTILITY);
        stageMessage.initModality(Modality.APPLICATION_MODAL);
        Background confirmBackground = new Background(new BackgroundFill(Color.web("#bbb"), CornerRadii.EMPTY, Insets.EMPTY));
        stageMessage.setMinWidth(250);
        stageMessage.setMinHeight(250);

        Label confirmMessage =new Label();
        confirmMessage.setText(message);
        Button yesButton =new Button("Si");
        Button noButton =new Button("No");
        yesButton.setOnAction(e->{
            return index;
            stageMessage.close();
        });
        noButton.setOnAction(e->{
            stageMessage.close();
        });
        yesButton.setDefaultButton(false);
        noButton.setDefaultButton(false);
        VBox layoutMessage = new VBox(20);
        HBox buttonLine =new HBox(20);
        buttonLine.getChildren().addAll(yesButton,noButton);
        buttonLine.setAlignment(Pos.CENTER);
        buttonLine.backgroundProperty().setValue(confirmBackground);
        layoutMessage.getChildren().addAll(confirmMessage,buttonLine);
        layoutMessage.setAlignment(Pos.CENTER);
        layoutMessage.backgroundProperty().setValue(confirmBackground);
        // group.getChildren().add(layoutMessage);
        Scene boxMessage =new Scene(layoutMessage,400,200,Color.BLACK);
        boxMessage.setFill(Color.BROWN);
        stageMessage.setScene(boxMessage);
        stageMessage.showAndWait();


        return answer;
    }




    public void activeCell( ImageView[])  {
        imageViewCell[indexWindow][indexRow][indexColumn].setOnMouseClicked(e -> {
            System.out.println("è stata cliccata la window: " + indexWindow + " row: " + indexRow + " column " + indexColumn);
            Random rand = new Random();
            int  value = rand.nextInt(6) + 1;
            int  color = rand.nextInt(5);
            try{
                Image newImage = new Image(new FileInputStream("src/resources/dadijpg/" + DiceColor.getDiceColor(color)+"Dice"+value+".jpg"));
                imageViewCell[indexWindow][indexRow][indexColumn].setImage(newImage);
            }catch (Exception exception){

            }
            System.out.println("src/resources/dadi/"+DiceColor.getDiceColor(color)+"Dice"+value+".jpg");
        });

    }*/

}
