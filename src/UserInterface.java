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

/**
 * UserInterface class
 * JavaFX application responsible for displaying attractions,
 * allowing user selection, and generating an optimal trip schedule.
 *
 * The UI is divided into:
 * - category-based attraction selection
 * - selected attractions bar
 * - final optimized schedule view
 *
 * @author Karolina Kolarz
 * @author Maja Lamch
 * @version 1.0
 */
public class UserInterface extends Application {

    private Stage primaryStage;
    private Scene mainScene;
    private final BorderPane mainLayout = new BorderPane();
    private final HBox bottomSelectionBar = new HBox();

    private final TripPlanner planner = new TripPlanner();
    private final TripSchedule schedule = new TripSchedule();
    private final Label timeInfoLabel = new Label();

    /**
     * Initializes and starts the JavaFX application.
     *
     * @param stage primary application window
     */
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        buildUI();
        setupStage();
    }

    /**
     * Builds the main user interface layout including:
     * - header panel
     * - category sections with attractions
     * - bottom selection bar
     */
    private void buildUI() {
        VBox topPanel = new VBox(5);

        topPanel.setAlignment(Pos.CENTER);
        topPanel.setPadding(new Insets(10));

        Label titleLabel = new Label("Tworzenie planu wycieczki");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        timeInfoLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555555;");

        HBox timeSelectionBox = new HBox(15);
        timeSelectionBox.setAlignment(Pos.CENTER);

        Spinner<LocalTime> startSpinner = new Spinner<>();
        startSpinner.setEditable(true);
        startSpinner.setPrefWidth(100);
        SpinnerValueFactory<LocalTime> startFactory = new SpinnerValueFactory<LocalTime>() {
            { setValue(schedule.getStartTime()); }
            @Override public void decrement(int i) { if (getValue() != null) setValue(getValue().minusMinutes(i)); }
            @Override public void increment(int i) { if (getValue() != null) setValue(getValue().plusMinutes(i)); }
        };

        startFactory.setConverter(new javafx.util.StringConverter<LocalTime>() {
            @Override
            public String toString(LocalTime value) { return value != null ? value.toString() : ""; }
            @Override
            public LocalTime fromString(String string) {
                try { return LocalTime.parse(string); }
                catch (Exception e) { return startFactory.getValue(); } // w razie błędu przywraca starą wartość
            }
        });
        startSpinner.setValueFactory(startFactory);

        Spinner<LocalTime> endSpinner = new Spinner<>();
        endSpinner.setEditable(true);
        endSpinner.setPrefWidth(100);

        SpinnerValueFactory<LocalTime> endFactory = new SpinnerValueFactory<LocalTime>() {
            { setValue(schedule.getEndTime()); }
            @Override public void decrement(int i) { if (getValue() != null) setValue(getValue().minusMinutes(i)); }
            @Override public void increment(int i) { if (getValue() != null) setValue(getValue().plusMinutes(i)); }
        };

        endFactory.setConverter(new javafx.util.StringConverter<LocalTime>() {
            @Override
            public String toString(LocalTime value) { return value != null ? value.toString() : ""; }
            @Override
            public LocalTime fromString(String string) {
                try { return LocalTime.parse(string); }
                catch (Exception e) { return endFactory.getValue(); }
            }
        });
        endSpinner.setValueFactory(endFactory);

        startSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
           if(newValue != null){
               if(newValue.isBefore(schedule.getEndTime())){
                   schedule.setStartTime(newValue);
                   updateTimeLabel();
               }
               else{
                   javafx.application.Platform.runLater(() -> startSpinner.getValueFactory().setValue(oldValue));
                   showWarningAlert("Błąd czasu", "Czas rozpoczęcia musi być przed czasem zakończenia!");
               }
           }
        });

        endSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                if(newValue.isAfter(schedule.getStartTime())){
                    schedule.setEndTime(newValue);
                    updateTimeLabel();
                }
                else{
                    javafx.application.Platform.runLater(() -> endSpinner.getValueFactory().setValue(oldValue));
                    showWarningAlert("Błąd czasu", "Czas zakończenia musi być po czasie rozpoczęcia!");
                }
            }
        });

        timeSelectionBox.getChildren().addAll(
                new Label("Start:"), startSpinner,
                new Label("Koniec:"), endSpinner
        );

        HBox locationBox = new HBox(10);
        locationBox.setAlignment(Pos.CENTER);

        TextField xField = new TextField("0.0");
        xField.setPrefWidth(50);
        TextField yField = new TextField("0.0");
        yField.setPrefWidth(50);

        Runnable updateStartLocation = () -> {
            try {
                double x = Double.parseDouble(xField.getText());
                double y = Double.parseDouble(yField.getText());
                schedule.setStartLocation(new Location(x, y));
                updateTimeLabel();
            } catch (NumberFormatException ex) {
            }
        };

        xField.textProperty().addListener((obs, old, newVal) -> updateStartLocation.run());
        yField.textProperty().addListener((obs, old, newVal) -> updateStartLocation.run());

        locationBox.getChildren().addAll(
                new Label("Lokalizacja startowa X:"), xField,
                new Label("Y:"), yField
        );

        topPanel.getChildren().addAll(titleLabel, timeSelectionBox, locationBox,timeInfoLabel);
        mainLayout.setTop(topPanel);

        VBox contentContainer = new VBox(20);
        contentContainer.setPadding(new Insets(15));
        contentContainer.setAlignment(Pos.TOP_CENTER);

        for (Categories c : Categories.values()) {
            planner.sortByDuration();
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

    /**
     * Creates UI section for a single category of attractions.
     *
     * @param container parent container
     * @param categoryName name of category
     * @param attractions list of attractions in this category
     */
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
            Button attractionButton = new Button(attraction.getName() + " (" + attraction.getDurationMinutes() + " min) "+attraction.getLocation());
            attractionButton.setStyle("-fx-cursor: hand;");

            attractionButton.setOnAction(e -> tryAddAttraction(attraction));

            attractionsPane.getChildren().add(attractionButton);
        }

        section.getChildren().addAll(categoryLabel, attractionsPane);
        container.getChildren().add(section);
    }

    /**
     * Attempts to add an attraction to the trip plan
     * if it does not exceed allowed time constraints.
     *
     * @param attraction attraction selected by user
     */
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

    /**
     * Refreshes the bottom selection bar UI.
     * Removes all elements and rebuilds it based on currently selected attractions.
     */
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

    /**
     * Calculates total duration of all selected attractions.
     *
     * @return total time in minutes
     */
    private int calculateTotalMinutes() {
        int sum = 0;
        for (Attraction a : schedule.getSelectedAttractions()) {
            sum += a.getDurationMinutes();
        }
        return sum;
    }

    /**
     * Updates the time information label in the UI.
     * Displays total selected attraction time and maximum allowed trip time.
     */
    private void updateTimeLabel() {
        int total = calculateTotalMinutes();
        int maxAllowedMinutes = (int) java.time.Duration.between(schedule.getStartTime(), schedule.getEndTime()).toMinutes();
        int hours = total / 60;
        int minutes = total % 60;

        timeInfoLabel.setText(String.format("Łączny czas: %d min (%dh %dmin) / %d min (max od %s do %s)",
                total, hours, minutes, maxAllowedMinutes, schedule.getStartTime(), schedule.getEndTime()));
    }

    /**
     * Shows a warning alert with given title and message.
     *
     * @param title alert title
     * @param message alert content
     */
    private void showWarningAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays the computed optimal schedule using greedy algorithm.
     * Shows travel time, waiting time and visit duration for each attraction.
     */

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
            Location currentLocation = schedule.getStartLocation();

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

        javafx.scene.canvas.Canvas routeMap = createRouteMap(optimalRoute);
        StackPane mapContainer = new StackPane(routeMap);
        mapContainer.setStyle("-fx-background-color: #EEEEEE; -fx-padding: 10px;");

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(scrollPane, mapContainer);
        splitPane.setDividerPositions(0.5);

        optimalLayout.setCenter(splitPane);

        Button backButton = new Button("Powrót do edycji");
        backButton.setStyle("-fx-cursor: hand; -fx-font-size: 13px; -fx-padding: 8px 20px;");
        backButton.setOnAction(e -> {
            primaryStage.setScene(mainScene);
        });

        Button saveButton = new Button("Zapisz plan do pliku");
        saveButton.setStyle("-fx-cursor: hand; -fx-font-size: 13px; -fx-padding: 8px 20px;");

        saveButton.setOnAction(e -> saveScheduleToFile(optimalRoute));

        HBox buttonsBox = new HBox(15, backButton, saveButton);
        buttonsBox.setAlignment(Pos.CENTER);
        VBox bottomPanel = new VBox(buttonsBox);

        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.setPadding(new Insets(15, 0, 0, 0));
        optimalLayout.setBottom(bottomPanel);

        Scene optimalScene = new Scene(optimalLayout, 1300, 800);
        primaryStage.setScene(optimalScene);
    }

    /**
     * Configures and displays the main application window.
     */
    private void setupStage() {
    mainScene = new Scene(mainLayout, 1300, 1100);

    primaryStage.setTitle("Planowanie wycieczki");
    primaryStage.setScene(mainScene);
    primaryStage.centerOnScreen();
    primaryStage.show();
    }

    /**
     * Saves the optimized trip schedule to a selected text file.
     *
     * @param optimalRoute optimized list of attractions
     */
    private void saveScheduleToFile(List<Attraction> optimalRoute) {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Zapisz plan wycieczki");
        fileChooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Pliki tekstowe (*.txt)", "*.txt"));
        java.io.File file = fileChooser.showSaveDialog(primaryStage);

        if(file == null) return;
        try(java.io.PrintWriter writer = new java.io.PrintWriter(file)){
            writer.println("=========================================");
            writer.println("        TWÓJ PLAN ZWIEDZANIA KRAKOWA     ");
            writer.println("=========================================");
            writer.println("Czas ramowy: " + schedule.getStartTime() + " - " + schedule.getEndTime());
            writer.println("Start z pozycji: " + schedule.getStartLocation());
            writer.println("-----------------------------------------");

            Location currLocation = schedule.getStartLocation();
            LocalTime currentTime = schedule.getStartTime();

            for(int i = 0; i < optimalRoute.size(); i++){
                Attraction a = optimalRoute.get(i);
                int travelTime = currLocation.travelTime(a.getLocation());
                currentTime = currentTime.plusMinutes(travelTime);

                if(i>0 || travelTime>0){
                    writer.printf("  [Czas na dojazd: %d min]\n", travelTime);
                }

                int waitTime = schedule.getWaitingTime(a, currentTime);
                currentTime = currentTime.plusMinutes(waitTime);

                LocalTime startTimeOfAttraction = currentTime;
                currentTime = currentTime.plusMinutes(a.getDurationMinutes());

                writer.printf("%d. %s | Godziny: %s - %s (Czas trwania: %d min)\n", (i + 1), a.getName(), startTimeOfAttraction, currentTime, a.getDurationMinutes());
                currLocation = a.getLocation();
            }
            showWarningAlert("Sukces", "Plan wycieczki został zapisany pomyślnie!");
        }catch (Exception ex) {
            showWarningAlert("Błąd", "Nie udało się zapisać pliku: " + ex.getMessage());
        }
    }

    /**
     * Draws a map of visited attractions.
     *
     * @param optimalRoute selected attractions
     */
    private javafx.scene.canvas.Canvas createRouteMap(List<Attraction> optimalRoute) {
        int width = 500;
        int height = 500;
        javafx.scene.canvas.Canvas canvas = new javafx.scene.canvas.Canvas(width, height);
        javafx.scene.canvas.GraphicsContext gc = canvas.getGraphicsContext2D();

        if (optimalRoute.isEmpty()) {
            gc.setFill(javafx.scene.paint.Color.web("#F5F5F5"));
            gc.fillRect(0, 0, width, height);
            return canvas;
        }

        Location startLoc = schedule.getStartLocation();
        double minX = startLoc.getX();
        double maxX = startLoc.getX();
        double minY = startLoc.getY();
        double maxY = startLoc.getY();

        for (Attraction attr : optimalRoute) {
            double x = attr.getLocation().getX();
            double y = attr.getLocation().getY();
            if (x < minX) minX = x;
            if (x > maxX) maxX = x;
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }

        double dataWidth = maxX - minX;
        double dataHeight = maxY - minY;

        if (dataWidth == 0) dataWidth = 1.0;
        if (dataHeight == 0) dataHeight = 1.0;

        double padding = 50.0; // bezpieczny margines od krawędzi okna w pikselach

        // Skala mówi, ile pikseli przypada na jedną jednostkę współrzędnych
        double scaleX = (width - 2 * padding) / dataWidth;
        double scaleY = (height - 2 * padding) / dataHeight;
        double scale = Math.min(scaleX, scaleY);


        double mapXOffset = padding + (width - 2 * padding - dataWidth * scale) / 2.0;
        double mapYOffset = padding + (height - 2 * padding - dataHeight * scale) / 2.0;

        double finalMinX = minX;
        java.util.function.Function<Double, Double> toPixelX = (x) -> mapXOffset + (x - finalMinX) * scale;
        double finalMinY = minY;
        java.util.function.Function<Double, Double> toPixelY = (y) -> height - (mapYOffset + (y - finalMinY) * scale); // Odwracamy Y

        gc.setFill(javafx.scene.paint.Color.web("#F9F9F9"));
        gc.fillRect(0, 0, width, height);

        gc.setStroke(javafx.scene.paint.Color.web("#EAEAEA"));
        gc.setLineWidth(1);
        for (int i = 0; i <= width; i += 25) {
            gc.strokeLine(i, 0, i, height);
            gc.strokeLine(0, i, width, i);
        }

        gc.setStroke(javafx.scene.paint.Color.web("#2E7D32"));
        gc.setLineWidth(3);

        double lastX = toPixelX.apply(startLoc.getX());
        double lastY = toPixelY.apply(startLoc.getY());

        for (Attraction attr : optimalRoute) {
            double nextX = toPixelX.apply(attr.getLocation().getX());
            double nextY = toPixelY.apply(attr.getLocation().getY());

            gc.strokeLine(lastX, lastY, nextX, nextY);

            lastX = nextX;
            lastY = nextY;
        }

        double pStartX = toPixelX.apply(startLoc.getX());
        double pStartY = toPixelY.apply(startLoc.getY());
        gc.setFill(javafx.scene.paint.Color.web("#D32F2F"));
        gc.fillOval(pStartX - 7, pStartY - 7, 14, 14);
        gc.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 12));
        gc.fillText("START", pStartX + 12, pStartY + 4);

        gc.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.NORMAL, 11));
        for (int i = 0; i < optimalRoute.size(); i++) {
            Attraction attr = optimalRoute.get(i);
            double x = toPixelX.apply(attr.getLocation().getX());
            double y = toPixelY.apply(attr.getLocation().getY());

            // Kropka
            gc.setFill(javafx.scene.paint.Color.web("#1976D2"));
            gc.fillOval(x - 6, y - 6, 12, 12);

            // Biały numer wewnątrz lub tuż obok kropki
            gc.setFill(javafx.scene.paint.Color.WHITE);
            gc.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 10));
            gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);

            // Rysujemy sam numer (np. "1", "2") dokładnie w środku kropki
            gc.fillText(String.valueOf(i + 1), x, y + 3);
        }

        return canvas;
    }
}

