# JavaFx-DateTimePicker
- javaFx customizationDate DateTimePicker，support hour and minute and seconds！
- javaFx 自定义时间选择组件 DateTimePicker， javaFx 源代码不支持时分秒选择，该代码组件支持时分秒选择，同时也支持清空、取消、此刻等操作！
- 效果如下图所示：

    ![image](static/1.gif)
- 使用将工程打包后引用到自己项目中
- 使用方式一（fxml引用）： 

    ```
      <DateTimePicker>
         <VBox.margin>
            <Insets bottom="6.0" left="6.0" right="6.0" top="6.0" />
         </VBox.margin>
      </DateTimePicker>
    ```
  
- 使用方式二（Stage实现）：
     ```
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
```