package org.casic.javafx.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * @beLongProjecet: JavaFx-DateTimePicker
 * @description:
 * @author: wzy
 * @createTime: 2023/04/18 15:58
 */
class DateTimePickerSelect extends VBox implements Initializable {
    private final DateTimePicker dateTimePicker;

    private List<Button> dayList;
    private Calendar calendar;
    private LocalDateTime cursorDateTime;
    @FXML
    private FlowPane flowPane;
    @FXML
    private FlowPane flow;
    @FXML
    private Label labelYear;
    @FXML
    private Label labelMouth;
    @FXML
    private Button previousYear;
    @FXML
    private Button nextYear;
    @FXML
    private Button previousMonth;
    @FXML
    private Button nextMonth;
    @FXML
    private ComboBox<String> hour;
    @FXML
    private ComboBox<String> minute;
    @FXML
    private ComboBox<String> second;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonOK;
    @FXML
    private Button buttonReset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dayList = new ArrayList<Button>();
        /*String[] weeks = new String[]{"一","二","三","四","五","六","日"};
        for (String week : weeks) {
            Label label = new Label(week);
            label.setStyle("-fx-font-size: 9;-fx-font-family: Microsoft YaHei;");
            flowPane.getChildren().add(label);
        }*/
    }
    public DateTimePickerSelect(final DateTimePicker parentCont){
        this.dateTimePicker = parentCont;
        // Load FXML
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DateTimePickerSelect.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            // Should never happen.  If it does however, we cannot recover
            // from this
            throw new RuntimeException(ex);
        }
        if( this.dateTimePicker.dateTimeProperty()!=null ){
            this.cursorDateTime = this.dateTimePicker.dateTimeProperty().getValue();
        }
        //生成日期部分
        upDataCalendar(true);
    }
    public String strValue(int i){
        String res;
        if (i <10){
            res="0"+i;
        }else {
            res = i+"";
        }
        return res;
    }
    /**
     * 用于更新年月展示
     */
    public void upDataLab(){
        if( calendar != null ){
            labelMouth.setText(calendar.get(Calendar.MONTH)+1+"");
            labelYear.setText(calendar.get(Calendar.YEAR)+"");
        }
    }
    /**
     * 更新日期部分的数据
     */
    public void upDataCalendar(boolean open){
        dayList.clear();
        flow.getChildren().clear();

        this.cursorDateTime = this.dateTimePicker.dateTimeProperty()!=null? this.dateTimePicker.dateTimeProperty().get() : null;
        int nowYear = this.cursorDateTime!=null?this.cursorDateTime.getYear():-1;
        int nowMonth = this.cursorDateTime!=null?this.cursorDateTime.getMonthValue():-1;

        Calendar tmpCalendar = this.cursorDateTime!=null?
                GregorianCalendar.from(ZonedDateTime.of(this.cursorDateTime, ZoneId.systemDefault())) :
                GregorianCalendar.from(ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()));
        if( open ){
            calendar = tmpCalendar;
        }else {
            tmpCalendar = this.calendar;
        }

        int mouthDays = tmpCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        tmpCalendar.set(tmpCalendar.get(Calendar.YEAR),tmpCalendar.get(Calendar.MONTH),mouthDays,tmpCalendar.get(Calendar.HOUR_OF_DAY),tmpCalendar.get(Calendar.MINUTE),tmpCalendar.get(Calendar.SECOND));
        int weekMouthLastDay = tmpCalendar.get(Calendar.DAY_OF_WEEK);
        tmpCalendar.set(tmpCalendar.get(Calendar.YEAR),tmpCalendar.get(Calendar.MONTH),1,tmpCalendar.get(Calendar.HOUR_OF_DAY),tmpCalendar.get(Calendar.MINUTE),tmpCalendar.get(Calendar.SECOND));
        int weekMouthFirstDay = tmpCalendar.get(Calendar.DAY_OF_WEEK);
        tmpCalendar.add(Calendar.MONTH,-1);
        int lastMouthDays = tmpCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        tmpCalendar.add(Calendar.MONTH,1);

        //System.out.println("本月天数："+mouthDays+"   上月天数"+lastMouthDays);
        if (weekMouthFirstDay == 1){
            //System.out.println("本月第一天是周日，前面有6天");
            for (int i = lastMouthDays-5;i<=lastMouthDays;i++){
                //dayList.add(i);
                Button btn = new Button(strValue(i));
                setDisable(btn);
                flow.getChildren().add(btn);
            }
            for (int i = 1;i<=mouthDays;i++){
                Button btn = new Button(strValue(i));
                dayList.add(btn);
                setAble(btn);
                flow.getChildren().add(btn);
            }
        }else if (weekMouthFirstDay == 2){
            //System.out.println("本月第一天是周一，前面没有");
            for (int i = 1;i<=mouthDays;i++){
                Button btn = new Button(strValue(i));
                dayList.add(btn);
                setAble(btn);
                if ( this.cursorDateTime!=null && this.cursorDateTime.getDayOfMonth() == i && this.calendar!=null &&
                        this.calendar.get(Calendar.YEAR)==nowYear && this.calendar.get(Calendar.MONTH)+1==nowMonth ){
                    btn.setStyle("-fx-text-fill: white;-fx-background-color: #5b8cff;-fx-font-size: 10");
                    this.calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),this.cursorDateTime.getDayOfMonth());
                }
                flow.getChildren().add(btn);
            }
        }else{
            //System.out.println("本月第一天不是周日，也不是周一");
            for (int i = lastMouthDays-weekMouthFirstDay+3;i<=lastMouthDays;i++){
                //dayList.add(i);
                Button btn = new Button(strValue(i));
                setDisable(btn);
                flow.getChildren().add(btn);
            }
            for (int i = 1;i<=mouthDays;i++){
                Button btn = new Button(strValue(i));
                dayList.add(btn);
                setAble(btn);
                if ( this.cursorDateTime!=null && this.cursorDateTime.getDayOfMonth() == i && this.calendar!=null &&
                        this.calendar.get(Calendar.YEAR)==nowYear && this.calendar.get(Calendar.MONTH)+1==nowMonth){
                    btn.setStyle("-fx-text-fill: white;-fx-background-color: #5b8cff;-fx-font-size: 10");
                    this.calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),this.cursorDateTime.getDayOfMonth());
                }
                flow.getChildren().add(btn);
            }
        }
        if (weekMouthLastDay != 1){
            for (int i = 1;i<=8-weekMouthLastDay;i++){
                //dayList.add(i);
                Button btn = new Button(strValue(i));
                setDisable(btn);
                flow.getChildren().add(btn);
            }
        }
        //生成时间部分
        setTime();
        //显示当前年月
        upDataLab();
    }
    /**
     * 设置上月和下月在本月显示的日期样式，并设置为不可点击
     * @param btn  日期按钮Button
     */
    public void setDisable(Button btn){
        btn.setDisable(true);
        btn.setStyle("-fx-text-fill: black;-fx-background-color: transparent;;-fx-font-size: 10");
    }
    /**
     * 设置本月日期的点击事件和样式，其中点击时间后，自动记录时间
     * @param btn  日期按钮Button
     */
    public void setAble(Button btn){
        btn.setStyle("-fx-text-fill: black;-fx-background-color: #fff;-fx-font-size: 10");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dayList.forEach(e->{e.setStyle("-fx-text-fill: black;-fx-background-color: #fff;-fx-font-size: 10");});
                btn.setStyle("-fx-text-fill: white;-fx-background-color: #5b8cff;-fx-font-size: 10");
                calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),Integer.valueOf(btn.getText()),
                        calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND));;
            }
        });
    }
    @FXML
    public void previousYear(){
        calendar.add(Calendar.YEAR,-1);
        upDataCalendar(false);
    }
    @FXML
    public void previousMonth(){
        calendar.add(Calendar.MONTH,-1);
        upDataCalendar(false);
    }
    @FXML
    public void nextYear(){
        calendar.add(Calendar.YEAR,1);
        upDataCalendar(false);
    }
    @FXML
    public void nextMonth(){
        calendar.add(Calendar.MONTH,1);
        upDataCalendar(false);
    }
    /**
     * @Description:
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:24
     **/
    private void setTime(){
        for (int i = 1;i < 25;i++){
            hour.getItems().add(strValue(i));
        }
        hour.getSelectionModel().select(calendar.get(Calendar.HOUR_OF_DAY)==0? 24 : calendar.get(Calendar.HOUR_OF_DAY)-1);

        for (int i = 0;i < 60;i++){
            minute.getItems().add(strValue(i));
        }
        minute.getSelectionModel().select(calendar.get(Calendar.MINUTE));

        for (int i = 0;i < 60;i++){
            second.getItems().add(strValue(i));
        }
        second.getSelectionModel().select(calendar.get(Calendar.SECOND));
    }


    /**
     * @Description: 确认按钮按下样式
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:40
     **/
    @FXML
    private void buttonOKOnMousePressed(){
        buttonOK.setStyle("-fx-background-color:#FFFFB9;-fx-font-size: 12;-fx-cursor: HAND;");
    }
    /**
     * @Description: 确认按钮恢复样式
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:40
     **/
    @FXML
    private void buttonOKOnMouseReleased(){
        buttonOK.setStyle("-fx-background-color:#ACD6FF;-fx-font-size: 12;-fx-cursor: HAND;");
    }
    /**
     * @Description: 点击确认按钮事件处理
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:42
     **/
    @FXML
    private void buttonOKOnAction(){
        if (this.calendar!=null){
            calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),
                    Integer.parseInt(hour.getSelectionModel().getSelectedItem()),Integer.parseInt(minute.getSelectionModel().getSelectedItem()),Integer.parseInt(second.getSelectionModel().getSelectedItem()));
            LocalDateTime localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
            this.dateTimePicker.setTimeProperty(localDateTime);
        }else {
//            System.out.println("请先选择日期");
        }
        this.dateTimePicker.hide();
    }
    /**
     * @Description: 此刻 按钮按下样式
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:40
     **/
    @FXML
    private void buttonNowOnMousePressed(){
        buttonOK.setStyle("-fx-background-color:#FFFFB9;-fx-font-size: 12;-fx-cursor: HAND;");
    }
    /**
     * @Description: 此刻 按钮恢复样式
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:40
     **/
    @FXML
    private void buttonNowOnMouseReleased(){
        buttonOK.setStyle("-fx-background-color:#e7c269;-fx-font-size: 12;-fx-cursor: HAND;");
    }
    /**
     * @Description: 点击此刻 按钮事件处理
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:42
     **/
    @FXML
    private void buttonNowOnAction(){
        LocalDateTime localDateTime = LocalDateTime.now();
        calendar = Calendar.getInstance();
        if (this.calendar!=null){
            calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),
                    Integer.parseInt(hour.getSelectionModel().getSelectedItem()),Integer.parseInt(minute.getSelectionModel().getSelectedItem()),Integer.parseInt(second.getSelectionModel().getSelectedItem()));
        }else {
//            System.out.println("请先选择日期");
        }
        this.dateTimePicker.setTimeProperty(localDateTime);
        this.dateTimePicker.hide();
    }
    /**
     * @Description: 取消按钮按下样式
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:40
     **/
    @FXML
    private void buttonCancelOnMousePressed(){
        buttonCancel.setStyle("-fx-background-color:#FFFFB9;-fx-font-size: 12;-fx-cursor: HAND;");
    }
    /**
     * @Description: 取消按钮恢复样式
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:40
     **/
    @FXML
    private void buttonCancelOnMouseReleased(){
        buttonCancel.setStyle("-fx-background-color:#FFD2D2;-fx-font-size: 12;-fx-cursor: HAND;");
    }
    /**
     * @Description: 点击取消按钮事件处理
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:42
     **/
    @FXML
    private void buttonCancelOnAction(){
        this.dateTimePicker.hide();
    }
    /**
     * @Description: 重置按钮按下样式
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:40
     **/
    @FXML
    private void buttonResetOnMousePressed(){
        buttonReset.setStyle("-fx-background-color:#FFFFB9;-fx-font-size: 12;-fx-cursor: HAND;");
    }
    /**
     * @Description: 重置按钮恢复样式
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:40
     **/
    @FXML
    private void buttonResetOnMouseReleased(){
        buttonReset.setStyle("-fx-background-color:#96e561;-fx-font-size: 12;-fx-cursor: HAND;");
    }
    /**
     * @Description: 点击重置按钮事件处理
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 10:42
     **/
    @FXML
    private void buttonResetOnAction(){
        this.calendar = null;
        this.cursorDateTime = null;
        this.dateTimePicker.clearTimeProperty();
    }
//    /**
//     *
//     * 设置年月左右选择按钮被按下时和弹起时的颜色
//     * @param btn  年月左右选择按钮Button
//     */
//    public void btnMouthPress(Button btn){
//        btn.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                btn.setStyle("-fx-text-fill: black;-fx-background-color: #FFD306;");
//            }
//        });
//        btn.setOnMouseReleased(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                btn.setStyle("-fx-text-fill: black;-fx-background-color: #c66;");
//            }
//        });
//    }
//    public void btnYearPress(Button btn){
//        btn.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                btn.setStyle("-fx-text-fill: black;-fx-background-color:#FF0000;");
//            }
//        });
//        btn.setOnMouseReleased(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                btn.setStyle("-fx-text-fill: black;-fx-background-color: #6cc;");
//            }
//        });
//    }
}