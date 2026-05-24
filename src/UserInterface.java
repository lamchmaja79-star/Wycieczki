import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class UserInterface extends Application {

    private Stage primaryStage;
    private final GridPane grid = new GridPane();
    private final GridPane gridNext = new GridPane();
    private final BorderPane pane = new BorderPane();

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        buildUI();
        setupStage();
    }

    private void buildUI() {
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        Label userLabel = new Label("User:");
        TextField userField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button okButton = new Button("zaloguj");

        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(okButton, 0, 2);

        okButton.setOnAction(e -> goToMainScreen());

    }

    private void setupStage() {
        Scene scene = new Scene(grid, 800, 450);
        primaryStage.setTitle("Logowanie");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void goToMainScreen() {

        Label label = new Label("strona główna");
        Rectangle listPlacement = new Rectangle(140, 320);
        VBox leftBox = new VBox(listPlacement);
        BorderPane layout = new BorderPane();

        listPlacement.setFill(Color.LIGHTGREEN);

        leftBox.setAlignment(Pos.CENTER_LEFT);
        leftBox.setPrefHeight(450);

        layout.setTop(label);
        BorderPane.setAlignment(label, Pos.CENTER);

        layout.setLeft(leftBox);

        Button history = new Button("Historia");
        Button entertainment = new Button("Rozrywka");
        Button art = new Button("Sztuka");
        Button sport = new Button("Sport");
        Button hardcore = new Button("Hardcore");
        Button recreation = new Button("Rekreacja");
        Button dining = new Button("Posiłki");
        Button wellness = new Button("Wellness");

        Button[] tabButton = {history,entertainment,art,sport,hardcore,recreation,dining,wellness};
        //gridNext.add(layout, 0, 0);

        gridNext.setAlignment(Pos.CENTER);
        gridNext.setVgap(10);
        for(int i = 0; i < tabButton.length; i++){
            gridNext.add(tabButton[i], 4, i);
        }



        Scene mainScene = new Scene(gridNext, 800, 450);
        primaryStage.setScene(mainScene);
    }
}