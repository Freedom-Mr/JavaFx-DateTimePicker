package org.casic.javafx.control;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Date;

public class DateTimePickerTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		final VBox vBox = new VBox();
		vBox.getChildren().add(new DateTimePicker());
		final Scene scene = new Scene(vBox);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
