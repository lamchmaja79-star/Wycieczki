import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UserInterface extends Application {

    private Stage primaryStage;
    private final BorderPane mainLayout = new BorderPane();
    private final HBox bottomSelectionBar = new HBox();

    private final TripPlanner planner = new TripPlanner();
    private final TripSchedule schedule = new TripSchedule();
    private final Label timeInfoLabel = new Label();

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        initData();
        buildUI();
        setupStage();
    }

    private void initData() {
        planner.addAttraction(new Attraction("Muzeum Narodowe", List.of(Categories.HISTORIA, Categories.SZTUKA), LocalTime.of(9, 0), LocalTime.of(17, 0), 90, new Location(1.0, 2.0)));
        planner.addAttraction(new Attraction("Zamek Królewski", List.of(Categories.HISTORIA), LocalTime.of(9, 0), LocalTime.of(18, 0), 100, new Location(1.5, 3.0)));
        planner.addAttraction(new Attraction("Podziemia Rynku", List.of(Categories.HISTORIA, Categories.ROZRYWKA), LocalTime.of(9, 0), LocalTime.of(19, 0), 60, new Location(0.5, 0.5)));

        planner.addAttraction(new Attraction("Koło młyńskie", List.of(Categories.ROZRYWKA, Categories.HARDCORE), LocalTime.of(10, 0), LocalTime.of(22, 0), 60, new Location(5.0, 5.0)));
        planner.addAttraction(new Attraction("Kino", List.of(Categories.ROZRYWKA), LocalTime.of(12, 0), LocalTime.of(23, 0), 130, new Location(2.0, 1.0)));
        planner.addAttraction(new Attraction("Escape Room", List.of(Categories.ROZRYWKA), LocalTime.of(8, 0), LocalTime.of(21, 0), 60, new Location(1.2, 1.8)));

        planner.addAttraction(new Attraction("Galeria Obrazów", List.of(Categories.SZTUKA), LocalTime.of(10, 0), LocalTime.of(18, 0), 90, new Location(0.8, 2.2)));
        planner.addAttraction(new Attraction("Teatr", List.of(Categories.SZTUKA), LocalTime.of(18, 0), LocalTime.of(22, 0), 150, new Location(1.1, 0.9)));

        planner.addAttraction(new Attraction("Basen", List.of(Categories.SPORT, Categories.WELLNESS), LocalTime.of(6, 0), LocalTime.of(22, 0), 90, new Location(3.0, 4.0)));

        planner.addAttraction(new Attraction("Skok na Bungee", List.of(Categories.HARDCORE), LocalTime.of(10, 0), LocalTime.of(18, 0), 45, new Location(6.0, 2.0)));

        planner.addAttraction(new Attraction("Zoo", List.of(Categories.REKREACJA), LocalTime.of(9, 0), LocalTime.of(17, 0), 240, new Location(4.5, 4.5)));

        planner.addAttraction(new Attraction("Restauracja", List.of(Categories.POSIŁKI), LocalTime.of(11, 0), LocalTime.of(23, 0), 60, new Location(0.2, 0.3)));

        planner.addAttraction(new Attraction("Masaż", List.of(Categories.WELLNESS, Categories.REKREACJA), LocalTime.of(9, 0), LocalTime.of(20, 0), 180, new Location(7.0, 7.0)));
    }

    private void buildUI() {
        VBox topPanel = new VBox(5);

        topPanel.setAlignment(Pos.CENTER);
        topPanel.setPadding(new Insets(10));

        Label titleLabel = new Label("Tworzenie planu wycieczki");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        timeInfoLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555555;");
        updateTimeLabel();

        topPanel.getChildren().addAll(titleLabel, timeInfoLabel);
        mainLayout.setTop(topPanel);

        VBox contentContainer = new VBox(20);
        contentContainer.setPadding(new Insets(15));
        contentContainer.setAlignment(Pos.TOP_CENTER);

        for (Categories c : Categories.values()) {
            List<Attraction> catAttractions = planner.filterByCategory(c);
            if (!catAttractions.isEmpty()) {
                createCategorySection(contentContainer, c.toString(), catAttractions);
            }
        }

        ScrollPane scrollPane = new ScrollPane(contentContainer);
        scrollPane.setFitToWidth(true);
        mainLayout.setCenter(scrollPane);

        bottomSelectionBar.setPrefHeight(60);
        bottomSelectionBar.setMinHeight(60);
        bottomSelectionBar.setAlignment(Pos.CENTER_LEFT);
        bottomSelectionBar.setPadding(new Insets(10));
        bottomSelectionBar.setSpacing(10);
        bottomSelectionBar.setStyle("-fx-background-color: lightgreen;");
        HBox.setHgrow(bottomSelectionBar, Priority.ALWAYS);

        Button submitButton = new Button("Zatwierdź");
        submitButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        submitButton.setPrefHeight(50);
        submitButton.setPrefWidth(100);
        submitButton.setOnAction(e -> showOptimalScheduleScreen());

        BorderPane footerPanel = new BorderPane();
        footerPanel.setCenter(bottomSelectionBar);
        footerPanel.setRight(submitButton);
        BorderPane.setMargin(submitButton, new Insets(0, 0, 0, 10));
        footerPanel.setPadding(new Insets(5, 10, 10, 10));

        mainLayout.setBottom(footerPanel);
    }

    private void createCategorySection(VBox container, String categoryName, List<Attraction> attractions) {
        VBox section = new VBox(5);
        section.setAlignment(Pos.TOP_CENTER);

        Label categoryLabel = new Label(categoryName);
        categoryLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        FlowPane attractionsPane = new FlowPane();
        attractionsPane.setHgap(10);
        attractionsPane.setVgap(10);
        attractionsPane.setAlignment(Pos.CENTER);

        for (Attraction attraction : attractions) {
            Button attractionButton = new Button(attraction.getName() + " (" + attraction.getDurationMinutes() + " min)");
            attractionButton.setStyle("-fx-cursor: hand;");

            attractionButton.setOnAction(e -> tryAddAttraction(attraction));

            attractionsPane.getChildren().add(attractionButton);
        }

        section.getChildren().addAll(categoryLabel, attractionsPane);
        container.getChildren().add(section);
    }

    private void tryAddAttraction(Attraction attraction) {
        int currentTotal = calculateTotalMinutes();
        int maxAllowedMinutes = (int) java.time.Duration.between(schedule.getStartTime(), schedule.getEndTime()).toMinutes();

        if (currentTotal + attraction.getDurationMinutes() > maxAllowedMinutes) {
            return;
        }

        schedule.addToPlan(attraction);
        refreshBottomBar();
        updateTimeLabel();
    }

    private void refreshBottomBar() {
        bottomSelectionBar.getChildren().clear();
        for (Attraction a : schedule.getSelectedAttractions()) {
            Label selectedLabel = new Label(a.getName() + " (" + a.getDurationMinutes() + " min)");
            selectedLabel.setStyle("-fx-background-color: white; -fx-padding: 5px 10px; -fx-background-radius: 5px; -fx-border-color: gray; -fx-border-radius: 5px; -fx-cursor: hand;");

            selectedLabel.setOnMouseClicked(e -> {
                schedule.deleteAttraction(a);
                refreshBottomBar();
                updateTimeLabel();
            });

            bottomSelectionBar.getChildren().add(selectedLabel);
        }
    }

    private int calculateTotalMinutes() {
        int sum = 0;
        for (Attraction a : schedule.getSelectedAttractions()) {
            sum += a.getDurationMinutes();
        }
        return sum;
    }

    private void updateTimeLabel() {
        int total = calculateTotalMinutes();
        int maxAllowedMinutes = (int) java.time.Duration.between(schedule.getStartTime(), schedule.getEndTime()).toMinutes();
        int hours = total / 60;
        int minutes = total % 60;

        timeInfoLabel.setText(String.format("Łączny czas: %d min (%dh %dmin) / %d min (max od %s do %s)",
                total, hours, minutes, maxAllowedMinutes, schedule.getStartTime(), schedule.getEndTime()));
    }

    private void showOptimalScheduleScreen() {
        BorderPane optimalLayout = new BorderPane();
        optimalLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Twój Optymalny Plan Zwiedzania");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        optimalLayout.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        VBox listContainer = new VBox(5);
        listContainer.setPadding(new Insets(15));
        listContainer.setAlignment(Pos.TOP_CENTER);

        List<Attraction> optimalRoute = schedule.createSchedule();

        if (optimalRoute.isEmpty()) {
            Label emptyLabel = new Label("Nie udało się ułożyć żadnego planu. Dodaj atrakcje, które można zwiedzić w wyznaczonym czasie!");
            emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");
            listContainer.getChildren().add(emptyLabel);
        } else {
            LocalTime currentTime = schedule.getStartTime();
            Location currentLocation = new Location(0.0, 0.0);

            for (int i = 0; i < optimalRoute.size(); i++) {
                Attraction a = optimalRoute.get(i);

                int travelTime = currentLocation.travelTime(a.getLocation());
                currentTime = currentTime.plusMinutes(travelTime);

                if (i > 0 || travelTime > 0) {
                    Label travelLabel = new Label(String.format("czas na dojazd: %d minut", travelTime));
                    travelLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555555; -fx-font-style: italic; -fx-padding: 5px 0;");
                    listContainer.getChildren().add(travelLabel);
                }

                int waitTime = schedule.getWaitingTime(a, currentTime);
                currentTime = currentTime.plusMinutes(waitTime);

                LocalTime startTimeOfAttraction = currentTime;
                currentTime = currentTime.plusMinutes(a.getDurationMinutes());
                LocalTime endTimeOfAttraction = currentTime;

                String text = String.format("%d. %s (%d min)  |  Godziny: %s - %s  (Lokalizacja: %s)",
                        (i + 1), a.getName(), a.getDurationMinutes(), startTimeOfAttraction, endTimeOfAttraction, a.getLocation());

                Label itemLabel = new Label(text);
                itemLabel.setStyle("-fx-font-size: 14px; -fx-padding: 8px 0;");
                itemLabel.setMaxWidth(800);
                listContainer.getChildren().add(itemLabel);

                currentLocation = a.getLocation();
            }
        }

        ScrollPane scrollPane = new ScrollPane(listContainer);
        scrollPane.setFitToWidth(true);
        optimalLayout.setCenter(scrollPane);

        Button backButton = new Button("Powrót do edycji");
        backButton.setStyle("-fx-cursor: hand; -fx-font-size: 13px; -fx-padding: 8px 20px;");
        backButton.setOnAction(e -> {
            Scene mainScene = new Scene(mainLayout, 1100, 600);
            primaryStage.setScene(mainScene);
        });

        VBox bottomPanel = new VBox(backButton);
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.setPadding(new Insets(15, 0, 0, 0));
        optimalLayout.setBottom(bottomPanel);

        Scene optimalScene = new Scene(optimalLayout, 1100, 600);
        primaryStage.setScene(optimalScene);
    }

    private void setupStage() {
        Scene scene = new Scene(mainLayout, 1100, 600);
        primaryStage.setTitle("Planowanie Wycieczki");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}