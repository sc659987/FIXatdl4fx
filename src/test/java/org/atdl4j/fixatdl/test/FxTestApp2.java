package org.atdl4j.fixatdl.test;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

public class FxTestApp2 extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Validation Demo");
		BorderPane borderPane = new BorderPane();

		borderPane.setCenter(loadLoginScreen());
		Scene scene = new Scene(borderPane, 700, 500);
		URL url = this.getClass().getResource("/context.css");
		scene.getStylesheets().add(url.toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private GridPane loadLoginScreen() {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		final TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		final PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);

		// Context Menu for error messages
		final ContextMenu usernameValidator = new ContextMenu();
		usernameValidator.setAutoHide(false);
		final ContextMenu passValidator = new ContextMenu();
		passValidator.setAutoHide(false);

		// Action on button press
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// Clearing message if any
				actiontarget.setText("");

				// Checking if the userTextField is empty
				if (userTextField.getText().equals("")) {
					usernameValidator.getItems().clear();
					usernameValidator.getItems().add(
							new MenuItem("Please enter username"));
					usernameValidator.show(userTextField, Side.RIGHT, 10, 0);
				}
				// Checking if the pwBox is empty
				if (pwBox.getText().equals("")) {
					passValidator.getItems().clear();
					passValidator.getItems().add(
							new MenuItem("Please enter Password"));
					passValidator.show(pwBox, Side.RIGHT, 10, 0);
				}
				// If both of the above textFields have values
				if (!pwBox.getText().equals("")
						&& !userTextField.getText().equals("")) {
					actiontarget.setFill(Color.GREEN);
					actiontarget.setText("Welcome");
				}
			}
		});

		userTextField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (newPropertyValue) {
							// Clearing message if any
							actiontarget.setText("");
							// Hiding the error message
							usernameValidator.hide();
						}
					}
				});

		pwBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean oldPropertyValue, Boolean newPropertyValue) {
				if (newPropertyValue) {
					// Clearing message if any
					actiontarget.setText("");
					// Hiding the error message
					passValidator.hide();
				}
			}
		});
		return grid;
	}

}