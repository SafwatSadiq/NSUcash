package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.Main;
import com.mindreader007.nsucash.services.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginScreen {
    public LoginScreen(){

    }

    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordInputField;
    @FXML
    private TextField usernameInputField;
    @FXML
    private Label loginStatusMessage;

    public void userLogin(ActionEvent event) throws IOException {
        checkLogin();
    }

    public void checkLogin() throws IOException{

        String username = usernameInputField.getText();
        String password = passwordInputField.getText();

        if(UserDAO.validateLogin(username, password)){
            Main m = new Main();
            m.changeScene("fxml/MainScreen.fxml", 1280, 720);
        }
        else loginStatusMessage.setText("Wrong Credential");
    }

    public void loginButtonColorChangeOnMove(){
        loginButton.setStyle("-fx-background-color: #0D2A75; -fx-background-radius: 5;");
    }

    public void loginButtonColorChangeOnExit(){
        loginButton.setStyle("-fx-background-color: #061742; -fx-background-radius: 5;");
    }

}
