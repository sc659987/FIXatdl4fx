package org.atdl4j.fixatdl.test;

import org.atdl4j.fixatdl.ui.fx8.customelement.doublespinner.DoubleSpinner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by sainik on 4/26/17.
 */
public class DoubleSpinnerTestApp extends Application {

    private DoubleSpinner doubleSpinner;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Validation Demo");
        primaryStage.setScene(test());
        primaryStage.show();
    }

    private Scene test() {
        HBox hBox = new HBox();
        doubleSpinner = new DoubleSpinner(0, 10, 5,0.25,1);
        doubleSpinner.setId("controlId");
        hBox.getChildren().add(doubleSpinner);
        Scene scene = new Scene(hBox, 500, 500);
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
