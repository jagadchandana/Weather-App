package Controller;

import Model.ImageSelector;
import Model.Manager;
import bo.costom.LastWeatherBO;
import bo.costom.impl.LastWeatherBoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.LastWeathTM;
import dto.LastWeatherDTO;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

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
    public Label lblLastDate;
    public Label lblTimeCount;
    public Label lblRefresh;
    public TableView tbltemp;
    public TableColumn coltab;
    public ImageView imgRestore;
    public AnchorPane MainUI;
    public JFXButton btnAdd;

    LastWeatherBO bo = new LastWeatherBoImpl();
    String citySet;
    String name;
    String code;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgRestore.setOnMouseClicked(event -> {
            try {
                Stage stage = (Stage) MainUI.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/views/RestoreForm.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        ////////////////////////////////////////////////////////////////////



        loadAllCodes("");
        cmbCityCode.valueProperty().addListener(observable -> {
          LastWeatherDTO dto  = new LastWeatherDTO();
            try {
                txtLocation.setText((bo.getWeather(String.valueOf(cmbCityCode.getSelectionModel().getSelectedItem()))).getName().toUpperCase());
                btnAdd.setText("Refresh");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
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
        Manager manager = new Manager(txtLocation.getText());
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
    txtLocation.setOnKeyReleased(event -> {
        try {

            if (bo.getWeather(txtLocation.getText())!=null){
                btnAdd.setText("Refresh");
            }else{
                btnAdd.setText("Add");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    });
    }




    public void btnShowOnAction(ActionEvent actionEvent) {
    if (txtLocation.getText().equals("")){
        showToast("Please Enter City Code Or Name!",lblEmptyError);
    }else {
        LastWeatherDTO dto = null;
        try {
            dto = bo.getWeather(txtLocation.getText());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (dto != null) {
            code = String.valueOf(dto.getCode());
            lblCity.setText(dto.getName());
            lblCity.setTextFill(Color.TOMATO);
            lblTemp.setText(dto.getTemp());
            lblDay.setText(dto.getDate());
            lblDesc.setText(dto.getDesc());
            System.out.println("hhhh");
            System.out.println(dto.getImg());

            System.out.println("jjj");
            lblWind.setText(dto.getWind());
            lblCloud.setText(dto.getCloud());
            lblPressure.setText(dto.getPress());
            lblHumidity.setText(dto.getHumidity());
            imgWeather.setImage(new Image(ImageSelector.getImage(dto.getImg())));
            lblLastDate.setText(dto.getDate());
            ///// date count
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy:MM:dd");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date onDate = null;
            Date onTime = null;
            try {
                onDate = dtf.parse(dto.getDate());
                onTime = sdf.parse(dto.getTime());
                Date today = dtf.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd")));
                Date timeNow = sdf.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern(" HH:mm")));
                long days = today.getTime() - onDate.getTime();
                long time = timeNow.getTime() - onTime.getTime();
                lblTimeCount.setText(TimeUnit.DAYS.convert(days, TimeUnit.MILLISECONDS) + "Days" + " : " + TimeUnit.HOURS.convert(time, TimeUnit.MILLISECONDS) + " Hours" + " ago..");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //////
        }

        showWeather();
    }
        }
    private void showWeather(){
        Manager manager = new Manager(txtLocation.getText());
        manager.getWeather(txtLocation.getText());
        lblCity.setText(Manager.getCity().toUpperCase());
        lblCity.setTextFill(Color.TOMATO);
        lblTemp.setText(Manager.getTemperature().toString()+"°C");
        lblDay.setText(Manager.getDay().toUpperCase());
        lblDesc.setText(Manager.getDescription().toUpperCase());
        imgWeather.setImage(new Image(ImageSelector.getImage(Manager.getIcon())));
        lblWind.setText(Manager.getWindSpeed()+" m/s");
        lblCloud.setText(Manager.getCloudiness()+"%");
        lblPressure.setText(Manager.getPressure()+" hpa");
        lblHumidity.setText(Manager.getHumidity()+"%");
        code = Manager.getCode();
        name = lblCity.getText();
        System.out.println("ok");

    }
   ///////////////

    private void reset() {
        lblCity.setText("");
       // lblDay.setText("");
        lblTemp.setText("");
        lblDesc.setText("");
        lblWind.setText("");
        lblCloud.setText("");
        lblPressure.setText("");
        lblHumidity.setText("");
        imgWeather.setImage(null);
    }




    //method to show error messages
    private void showToast(String message, Label lbl) {
        lbl.setText(message);
        lbl.setTextFill(Color.TOMATO);
        lbl.setStyle("/*-fx-background-color: #fff;*/ -fx-background-radius: 20px;");

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



    public void btnAddOnAction(ActionEvent actionEvent) {
        int cod = Integer.parseInt(code);
        String city = lblCity.getText();
        String tem = lblTemp.getText();
        String description = lblDesc.getText();
        String wind = lblWind.getText();
        String cloud = lblCloud.getText();
        String pressure = lblPressure.getText();
        String humidity = lblHumidity.getText();
        String date = lblDate.getText();
        String time = lblTime.getText();
        String img = Manager.getIcon();
        System.out.println(img);
        LastWeatherDTO dto1 = new LastWeatherDTO(cod,city,tem,description,wind,cloud,pressure,humidity,date,time,img);

        if(btnAdd.getText().equalsIgnoreCase("add")){
            try {
                if (bo.saveWeather(dto1)){
                    showToast("Weather Saved",lblError);
                }else{
                    showToast("Weather save Error",lblDesc);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println(e); e.printStackTrace();
            }
        }else{
            try {
                showWeather();
                if (bo.updateWeather(dto1)){
                    showToast("Weather Refresh",lblError);
                }else{
                    showToast("Weather Refresh Error!",lblError);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


        }


    private void loadAllCodes(String text){
         try {
                for (LastWeatherDTO tempdto:bo.getAllWeather("%"+text+"%")
          ) {
              cmbCityCode.getItems().addAll(tempdto.getCode());
           }
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         } catch (ClassNotFoundException e) {
           e.printStackTrace();
         }
    }


}
