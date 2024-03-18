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
		//初始化一个时间选择器
		DateTimePicker dateTimePicker = new DateTimePicker();

		//设置选中时间 为 当前时间
//		dateTimePicker.setTimeProperty( LocalDateTime.now() );

		//是否默认显示当前时间
		dateTimePicker.setShowLocalizedDateTime(false);


		//设置一个容器
		final VBox vBox = new VBox();
		vBox.getChildren().add(dateTimePicker);
		final Scene scene = new Scene(vBox);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();


		//循环获取选中时间-打印
		Thread thread = new Thread() {
			@Override
			public void run() {
				while (true){
					try {
						if( dateTimePicker.dateTimeProperty()==null ){
							System.out.println("no check date！");
						}else{
							//获取选中时间
							LocalDateTime dateTime = dateTimePicker.dateTimeProperty().get();
							System.out.println(dateTime.getYear()+"-"+dateTime.getMonthValue()+"-"+dateTime.getDayOfMonth()+" "+dateTime.getHour()+":"+dateTime.getMinute()+":"+dateTime.getSecond());
						}
						Thread.sleep(1000);
					} catch (Exception e) {
//						throw new RuntimeException(e);
						continue;
					}
				}
			}
		};
		thread.start();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
