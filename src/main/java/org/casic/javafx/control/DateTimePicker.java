package org.casic.javafx.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Window;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

/**
 * @beLongProjecet: JavaFx-DateTimePicker
 * @description: DateTimePicker plugin
 * @author: wzy
 * @createTime: 2023/04/18 18:10
 */
public class DateTimePicker extends HBox implements Initializable {
    private final DateTimeFormatter formatter;
    // DateTime value
    private ObjectProperty<LocalDateTime> dateTime;
    private final Popup popupContainer;
    private final DateTimePickerSelect dateTimePickerSelect;
    public Boolean showLocalizedDateTime = false;
    @FXML
    private TextField textField;
    @FXML
    private Button button;

    public DateTimePicker(){
        if(showLocalizedDateTime){this.dateTime = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());}
        this.formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        this.popupContainer = new Popup();
        this.dateTimePickerSelect = new DateTimePickerSelect(this);
        // Load FXML
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DateTimePicker.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            // Should never happen.  If it does however, we cannot recover
            // from this
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(showLocalizedDateTime){textField.setText(formatter.format(dateTime.get()));}
//        dateTime.addListener(
//                (observable, oldValue, newValue) -> {
//                    textField.setText(formatter.format(newValue));
//                });
        button.prefHeightProperty().bind(textField.heightProperty());
        popupContainer.getContent().add(dateTimePickerSelect);
        popupContainer.autoHideProperty().set(true);
    }

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (popupContainer.isShowing()) {
            popupContainer.hide();
        } else {
            final Window window = button.getScene().getWindow();
            final double x = window.getX()
                    + textField.localToScene(0,0).getX()
                    + textField.getScene().getX();
            final double y = window.getY()
                    + button.localToScene(0,0).getY()
                    + button.getScene().getY()
                    + button.getHeight();
            popupContainer.show(this.getParent(), x, y);
            dateTimePickerSelect.upDataCalendar(true);
        }
    }
    /**
     * Gets the current LocalDateTime value
     * @return The current LocalDateTime value
     */
    public ObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }
    /**
     * @Description: setTimeProperty
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 11:11
     **/
    public void setTimeProperty(LocalDateTime localDateTime){
        this.dateTime = new SimpleObjectProperty<LocalDateTime>(localDateTime);
        textField.setText(formatter.format(this.dateTime.get()));
    }
    /**
     * @Description: clearTimeProperty
     * @Params
     * @Return
     * @Author wzy
     * @Date 2023/4/19 11:11
     **/
    public void clearTimeProperty(){
        this.dateTime = null;
        textField.setText("");
    }
    public void hide(){
        if (popupContainer.isShowing()) {
            popupContainer.hide();
        }
    }

    public Boolean getShowLocalizedDateTime() {
        return showLocalizedDateTime;
    }

    public void setShowLocalizedDateTime(Boolean show) {
        this.showLocalizedDateTime = show;
        if(show){
            setTimeProperty(LocalDateTime.now());
        }

    }
}
