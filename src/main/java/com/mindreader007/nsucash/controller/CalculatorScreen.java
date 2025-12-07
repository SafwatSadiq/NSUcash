package com.mindreader007.nsucash.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.swing.*;

public class CalculatorScreen {
    private String onHoverStyle = "-fx-background-color: #d1dbe4; -fx-background-radius: 20; -fx-text-fill: #061742;";
    private String defaultStyle = "-fx-background-radius: 20; -fx-background-color: #a3b7ca; -fx-text-fill: #061742;";

    private double FirstNumber;
    private double SecondNumber;
    private String Operator;
    private boolean startNew;

    @FXML
    private TextField calculateBox;

    public void onHoverButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle(onHoverStyle);
    }

    public void onHoverExitButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle(defaultStyle);
    }

    public String getActionValue(ActionEvent event){
        return ((Button) event.getSource()).getText();
    }

    public void onNumberClick(ActionEvent event){
        if(startNew){
            calculateBox.setText("");
            startNew = false;
        }
        String value = getActionValue(event);
        calculateBox.setText(calculateBox.getText() + value);
    }

    public void onOperatorClick(ActionEvent event){
        String newOperator = getActionValue(event);
        if(Operator != null && isNumeric(calculateBox.getText())){
            FirstNumber = calculate(FirstNumber, Operator);
        }
        else if(isNumeric(calculateBox.getText())){
            FirstNumber = Double.parseDouble(calculateBox.getText());
        }

        calculateBox.setText(newOperator);
        Operator = newOperator;
        startNew = true;
    }

    public void onEqualClick(ActionEvent event) {
        String value = getActionValue(event);
        if (isNumeric(calculateBox.getText())) {
            calculateBox.setText(String.valueOf((int) calculate(FirstNumber, Operator)));
            startNew = true;
        }
    }

    public void onCClick(ActionEvent event){
        String value = getActionValue(event);
        System.out.println(value);
    }

    public void onACClick(ActionEvent event){
        String value = getActionValue(event);
        System.out.println(value);
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public double calculate(double firstNumber, String operator) {
        double secondNumber = Double.parseDouble(calculateBox.getText());
        double result = 0;
        switch (operator) {
            case "+" -> result = firstNumber + secondNumber;
            case "-" -> result = firstNumber - secondNumber;
            case "X" -> result = firstNumber * secondNumber;
            case "/" -> result = firstNumber / secondNumber;
            case "%" -> result = firstNumber % secondNumber;
        }
        Operator = null;

        return result;
    }
}
