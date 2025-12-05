package com.mindreader007.nsucash.controller;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class CalculatorScreen {
    private String onHoverStyle = "-fx-background-color: #d1dbe4; -fx-background-radius: 20; -fx-text-fill: #061742;";
    private String defaultStyle = "-fx-background-radius: 20; -fx-background-color: #a3b7ca; -fx-text-fill: #061742;";

    public void onHoverButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle(onHoverStyle);
    }

    public void onHoverExitButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle(defaultStyle);
    }
}
