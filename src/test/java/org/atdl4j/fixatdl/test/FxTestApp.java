package org.atdl4j.fixatdl.test;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

// TODO log on debug mode
public class FxTestApp extends Application {

    private Spinner<Double> doubleSpinner = new Spinner<>();

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(getTestBaseScene());
        primaryStage.setTitle("FX8 Atdl Generator Test Interface");
        primaryStage.show();
    }

    private Scene getTestBaseScene() {
        HBox hBox = new HBox();
        doubleSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(0.0, 0.1)));
        hBox.getChildren().add(doubleSpinner);
        doubleSpinner.setId("ajhgduy");
        doubleSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Old GetComparableValue = " + oldValue + "New GetComparableValue = " + newValue);
            if (observable instanceof ReadOnlyProperty) {
                ReadOnlyProperty readOnlyProperty = (ReadOnlyProperty) observable;
                Spinner spinner = (Spinner) readOnlyProperty.getBean();
                System.out.println(spinner.getId());
                System.out.println("it's a read only property " + readOnlyProperty.getBean());
            }
            System.out.println(observable);
        });
        Scene scene = new Scene(hBox, 500, 500);
        return scene;
    }

    private Scene gridTest() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.add(new Label("dddd"), 0, 0, GridPane.REMAINING, 1);
        gridPane.add(new TextField("xxxx"), 0, 1, GridPane.REMAINING, 1);
        TitledPane titledPane = new TitledPane();
        titledPane.setContent(gridPane);
        titledPane.setText("Hi");
        Scene scene = new Scene(titledPane, 500, 500);
        return scene;
    }

    public static void main(String[] args) throws Exception {
        launch(args);

    }
}
