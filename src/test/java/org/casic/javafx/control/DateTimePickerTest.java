package org.casic.javafx.control;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Date;

public class DateTimePickerTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//��ʼ��һ��ʱ��ѡ����
		DateTimePicker dateTimePicker = new DateTimePicker();

		//����ѡ��ʱ�� Ϊ ��ǰʱ��
		dateTimePicker.setTimeProperty( LocalDateTime.now() );

		//��ȡѡ��ʱ��
		dateTimePicker.dateTimeProperty().get();

		//����һ������
		final VBox vBox = new VBox();
		vBox.getChildren().add(dateTimePicker);
		final Scene scene = new Scene(vBox);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
