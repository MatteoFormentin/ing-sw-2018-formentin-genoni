package it.polimi.se2018.view.gui.stage;

import it.polimi.se2018.view.gui.classes_database.PlayerOnline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Class for handle the waiting room
 *
 * @author Luca Genoni
 */
public class WaitGame {
    // private
    TableView<PlayerOnline> tableView;
    TextField nameInput;
    ObservableList<PlayerOnline> listPlayer;
    private Stage stage;
    Scene scene;

    /**
     * Constructor
     *
     * @param messageWait the owner of the waiting stage
     */
    public WaitGame(String messageWait) {
        Label waitMessage = new Label(messageWait);
        waitMessage.setAlignment(Pos.CENTER);

        TableColumn<PlayerOnline, String> indexPlayer = new TableColumn<>("index");
        indexPlayer.setMinWidth(200);
        indexPlayer.setCellValueFactory(new PropertyValueFactory<>("index"));

        TableColumn<PlayerOnline, String> nameColumn = new TableColumn<>("Nickname");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));


        TableColumn<PlayerOnline, String> stateConnection = new TableColumn<>("connected");
        stateConnection.setMinWidth(100);
        stateConnection.setCellValueFactory(new PropertyValueFactory<>("connected"));

        tableView = new TableView<>(getPlayerOnlineSingleton());
        tableView.getColumns().addAll(indexPlayer, nameColumn, stateConnection);
        nameInput = new TextField();
        nameInput.setMinWidth(100);

        //Button
        Button login = new Button("EventPreGame");
        Button disconnect = new Button("Disconnect");


        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, login, disconnect);

        VBox pane = new VBox();
        pane.getChildren().addAll(waitMessage, tableView, hBox);
        scene = new Scene(pane, 400, 200, Color.RED);
        scene.setCursor(Cursor.WAIT);

    }

    /**
     * for get the stage of the waiting
     *
     * @return the stage where the waiting is running
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * show the stage for wait something
     *
     * @param owner the message to show while the waiting is shown
     */
    public void displayWaiting(Stage owner) {
        stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.setTitle("Waiting Room");
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
            e.consume();
        });
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Method for add the player to the list of the player connected
     *
     * @param index     of the player in the game
     * @param nickname  of the player in the game
     * @param connected true if the player is connected, false otherwise
     */
    public void addPlayerOnline(int index, String nickname, boolean connected) {
        PlayerOnline player = new PlayerOnline();
        player.setIndex(index);
        player.setNickname(nickname);
        player.setConnected(connected);
        tableView.getItems().add(player);
    }

    /**
     * Method for add the player to the list of the player connected
     *
     * @param names an array of name to add as connected
     */
    public void addPlayerOnline(String[] names) {
        for (int i = 0; i < names.length; i++) {
            PlayerOnline player = new PlayerOnline(i, names[i], true);
            tableView.getItems().add(player);
        }
    }

    /**
     * Method for delete the player from the list
     *
     * @param nickname of the player that need to remove
     */
    public void deletePlayerKicked(String nickname) {
        ObservableList<PlayerOnline> allPlayerOnline;
        allPlayerOnline = tableView.getItems();
        for (PlayerOnline x : allPlayerOnline) {
            if (x.getNickname().equals(nickname)) allPlayerOnline.remove(x);
        }
    }

    /**
     * Method for delete All the player from the list
     */
    public void deletePlayerKicked() {
        ObservableList<PlayerOnline> allPlayerOnline;
        allPlayerOnline = tableView.getItems();
        for (PlayerOnline x : allPlayerOnline) {
            allPlayerOnline.remove(x);
        }
    }


    /**
     * Method for get all the player in the game
     *
     * @return a ObservableList<PlayerOnline> of all the player in game
     */
    public ObservableList<PlayerOnline> getPlayerOnlineSingleton() {
        if (listPlayer == null) {
            listPlayer = FXCollections.observableArrayList();
        }
        return listPlayer;
    }

    /**
     * close the stage of the waiting room
     */
    public void closeWait() {
        stage.close();
    }
}
