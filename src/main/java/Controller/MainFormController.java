package Controller;

import Model.ImageSelector;
import Model.Manager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    public ImageView imgCloseBtn;
    public JFXTextField txtLocation;
    public JFXButton btnShow;
    public Label lblWind;
    public Label lblCloud;
    public Label lblPressure;
    public Label lblTemp;
    public Label lblDesc;
    public Label lblTime;
    public Label lblDate;
    public Label lblDay;
    public Label lblHumidity;
    public ImageView imgWeather;
    public Label lblCity;
    public Label lblError;
    public JFXComboBox cmbCityCode;
    public Label lblEmptyError;

    Manager manager;// = new Manager(txtLocation.getText());
    String citySet;
    int cityCodeSet;
    String arr[] = {"1248991","1850147","2644210","2988507","2147714","4930956","1796236","3143244"};
    private void loadAllCode(){
        for (String code:arr
             ) {
            cmbCityCode.getItems().addAll(code);
        }
    }
    public void btnShowOnAction(ActionEvent actionEvent) {

            if (txtLocation.getText().equals("")) {
                showToast("City Name cannot be blank", lblEmptyError);
            } else {
                try {
                    lblError.setText("");
                    this.citySet = txtLocation.getText().trim();
                    System.out.println(citySet);
                    txtLocation.setText((txtLocation.getText().trim()).toUpperCase());
                    showWeather();
                } catch (Exception e) {
                    lblCity.setText("Error!!");
                    lblCity.setTextFill(Color.TOMATO);
                    showToast("City with the given name was not found.", lblError);
                    reset();
                }
            }
    }
    private void showWeather(){
        manager.getWeather(citySet);
        lblCity.setText(Manager.getCity().toUpperCase());
        lblCity.setTextFill(Color.TOMATO);
        lblTemp.setText(Manager.getTemperature().toString()+"°C");
        lblDay.setText(Manager.getDay().toUpperCase());
        lblDesc.setText(Manager.getDescription().toUpperCase());
        //  imgWeather.setImage(new Image(ImageSelector.getImage(Manager.getIcon())));
        lblWind.setText(Manager.getWindSpeed()+" m/s");
        lblCloud.setText(Manager.getCloudiness()+"%");
        lblPressure.setText(Manager.getPressure()+" hpa");
        lblHumidity.setText(Manager.getHumidity()+"%");

    }
   ///////////////
   private void handleButtonClicks(javafx.event.ActionEvent ae) {
       String initialCity = txtLocation.getText(); //stores the last searched city-name

     /*  if(ae.getSource() == change){
           txtLocation.setText("");
           bottomSet(true);
           txtLocation.requestFocus();
       }else if (ae.getSource() == set) {
           setPressed();
       } else if (ae.getSource() == cancel) {
           txtLocation.setText(initialCity);
           bottomSet(false);
           invis.requestFocus();
       }*/
   }

    //method to clear all the fields
    private void reset() {
      //  bottomSet(false);
       // lblDay.setText("");
        lblTemp.setText("");
        lblDesc.setText("");
        lblWind.setText("");
        lblCloud.setText("");
        lblPressure.setText("");
        lblHumidity.setText("");
        imgWeather.setImage(null);
    }

    //method to set the new entered city


    //method to handle nodes at botton part of the scene
    private void bottomSet(boolean statement){
        txtLocation.setDisable(!statement);
     /*   set.setVisible(statement);
        change.setVisible(!statement);
        cancel.setVisible(statement);*/
    }

    //method to show error messages
    private void showToast(String message, Label lbl) {
        lbl.setText(message);
        lbl.setTextFill(Color.TOMATO);
        lbl.setStyle("-fx-background-color: #fff; -fx-background-radius: 20px;");

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), lbl);
        fadeIn.setToValue(1);
        fadeIn.setFromValue(0);
        fadeIn.play();

        fadeIn.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.play();
            pause.setOnFinished(event2 -> {
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), lbl);
                fadeOut.setToValue(0);
                fadeOut.setFromValue(1);
                fadeOut.play();
            });
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllCode();
        cmbCityCode.valueProperty().addListener(observable -> {
            String code = String.valueOf(cmbCityCode.getSelectionModel().getSelectedItem());
            switch (code){
                case "1248991":
                    txtLocation.setText("Colombo");
                    break;
                case "1850147":
                    txtLocation.setText("Tokyo");
                    break;
                case "2644210":
                    txtLocation.setText("Liverpool");
                    break;
                default:
                    txtLocation.setText("");
            }
        });
        //////////////////////
        imgCloseBtn.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?", ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> confirmation = alert.showAndWait();
            if (confirmation.get().equals(ButtonType.YES)){
                Platform.exit();
            }
        });
        txtLocation.setOnKeyPressed(event -> {
            reset();
        });
        //
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern(" HH:mm")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd")));
        /////////////////////////////////////////////////////////////////////////////////////////

        lblCity.setText(citySet);
        txtLocation.setDisable(false);
        //  set.setVisible(false);
        //cancel.setVisible(false);
        lblError.setText("");
        manager = new Manager(txtLocation.getText());
        //  invis.requestFocus();

        //try catch block to see if there is internet and disabling ecery field
        try{
         //   showWeather();
        } catch (Exception e){
            lblCity.setText("Error!! - No Internet");
            lblError.setText("Error - No Internet");
            lblCity.setTextFill(Color.TOMATO);
            showToast("Internet Down. Please Connect",lblError);
            reset();
            //  change.setDisable(true);
            txtLocation.setText("");
        }

        //Set the city entered into textField on pressing enter on Keyboard

    }

    public void btn(ActionEvent actionEvent) {
      // showWeather();
    }

    /////////////



}
