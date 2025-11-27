package com.mindreader007.nsucash.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class MenuScreen {
    @FXML
    private AnchorPane menuArea;

    private String onHoverStyle = "-fx-background-color: #194a7a; -fx-background-radius: 30; -fx-text-fill: #d1dbe4;";
    private String defaultStyle = "-fx-background-radius: 30; -fx-background-color: #061742; -fx-text-fill: #d1dbe4;";

    public MenuScreen(){

    }

    public void onHoverButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle(onHoverStyle);
    }

    public void onHoverExitButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle(defaultStyle);
    }

    private void loadPage(String fxml){
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
            menuArea.getChildren().setAll(pane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bookshopButtonClicked(){
        loadPage("/com/mindreader007/nsucash/fxml/BookShopScreen.fxml");
    }

    public void canteenButtonClicked(){
        loadPage("/com/mindreader007/nsucash/fxml/CanteenScreen.fxml");
    }

    public void transferButtonClicked(){
        loadPage("/com/mindreader007/nsucash/fxml/TransferScreen.fxml");
    }

    public void advisingButtonClicked(){
        loadPage("/com/mindreader007/nsucash/fxml/AdvisingScreen.fxml");
    }

    public void busButtonClicked(){
        loadPage("/com/mindreader007/nsucash/fxml/BusScreen.fxml");
    }

    public void calculatorButtonClicked(){
        loadPage("/com/mindreader007/nsucash/fxml/CalculatorScreen.fxml");
    }


}
