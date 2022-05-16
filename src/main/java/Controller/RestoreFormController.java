package Controller;

import Model.ImageSelector;
import Model.Manager;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RestoreFormController {
    public AnchorPane PopUi;
    public ImageView imgClose;
    public ImageView imgRestore;
    public JFXButton btnLeft;
    public JFXButton btnRight;
    public AnchorPane slider2;
    public AnchorPane slider1;
    public AnchorPane slider3;
    public AnchorPane slider4;
    public AnchorPane slider5;
    public ImageView i1;
    public Label d1;
    public Label c1;
    public Label f1;
    public Label s1;
    public ImageView i2;
    public Label d2;
    public Label c2;
    public Label f2;
    public Label s2;
    public ImageView i3;
    public Label d3;
    public Label c3;
    public Label f3;
    public Label s3;
    public ImageView i4;
    public Label d4;
    public Label c4;
    public Label f4;
    public Label s4;
    public ImageView i5;
    public Label d5;
    public Label c5;
    public Label f5;
    public Label s5;

    public void initialize() {

        imgRestore.setOnMouseClicked(event -> {
            try {
                Stage stage = (Stage) PopUi.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/views/MainForm.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        imgClose.setOnMouseClicked(event -> {
            Platform.exit();
        });
////////////////////////////////////////////////////////////

         slider4.setTranslateX(-180);
        btnRight.setOnMouseClicked(event -> {
            TranslateTransition slide1 = new TranslateTransition();
            TranslateTransition slide2 = new TranslateTransition();
            TranslateTransition slide3 = new TranslateTransition();
            TranslateTransition slide4 = new TranslateTransition();
            TranslateTransition slide5 = new TranslateTransition();
            slider(slide1,-180,slider1);
            slider(slide2,-180,slider2);
            slider(slide3,-180,slider3);
            slider(slide4,-180,slider4);
            slider(slide5,0,slider5);
            btnRight.setDisable(true);
            btnLeft.setDisable(false);
        });
            slider5.setTranslateX(180);
        btnLeft.setOnMouseClicked(event -> {
            TranslateTransition slide1 = new TranslateTransition();
            TranslateTransition slide2 = new TranslateTransition();
            TranslateTransition slide3 = new TranslateTransition();
            TranslateTransition slide4 = new TranslateTransition();
            TranslateTransition slide5 = new TranslateTransition();
            slider(slide1,180,slider1);
            slider(slide2,180,slider2);
            slider(slide3,180,slider3);
            slider(slide4,0,slider4);
            slider(slide5,180,slider5);
            btnLeft.setDisable(true);
            btnRight.setDisable(false);
        });
        ////////////////////////////////////////////////////
Manager manager = new Manager();

String[] code = manager.readJsonFromFile();
        for (int i = 0; i < code.length-1; i++) {
            manager.getWeather(code[i]);
            switch (i){
                case 0:
                    setDataToSlider(i1,d1,c1,f1,s1);
                    break;
                case 1:
                    setDataToSlider(i2,d2,c2,f2,s2);
                    break;
                case 2:
                    setDataToSlider(i3,d3,c3,f3,s3);
                    break;
                case 4:
                    setDataToSlider(i4,d4,c4,f4,s4);
                    break;
                case 5:
                    setDataToSlider(i5,d5,c5,f5,s5);
                    break;
                default:
                    break;
            }

        }

    }
    public void setDataToSlider(ImageView i,Label l1, Label l2, Label l3, Label l4){
       try {
           i.setImage(new Image(ImageSelector.getImage(Manager.getIcon())));

        l1.setText(Manager.getDay());
        l2.setText(Manager.getCity());
        l2.setTextFill(Color.TOMATO);
        l3.setText(Manager.getTemperature()+"°C");
        l4.setText(Manager.getDescription());
       }catch (RuntimeException r ){
           new Alert(Alert.AlertType.ERROR,"Check Internet Connection", ButtonType.OK).show();

       }
    }



    public void slider(TranslateTransition slide, int range, AnchorPane pane) {

        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pane);
        slide.setToX(range);
        slide.play();
    }

}