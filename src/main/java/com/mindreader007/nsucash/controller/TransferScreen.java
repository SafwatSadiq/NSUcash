package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.services.UserDAO;
import com.mindreader007.nsucash.services.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TransferScreen implements Initializable {
    private String onHoverStyle = "-fx-background-color: #a3b7ca; -fx-background-radius: 20; -fx-text-fill: #061742;";
    private String defaultStyle = "-fx-background-radius: 20; -fx-background-color: #d1dbe4; -fx-text-fill: #061742;";

    @FXML
    private Label balanceLabel;
    @FXML
    private TextField transferAmountInputField;
    @FXML
    private TextField usernameSearchField;
    @FXML
    private ListView<String> usernameSuggestionList;
    @FXML
    private PasswordField passwordInputField;
    @FXML
    private Label paymentStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        balanceLabel.setText(String.valueOf(UserSession.getUser().getBalance()));

        setupSuggestionListAppearance();
        setupSuggestionListBehavior();
        setupSearchFieldBehavior();
    }

    private void setupSuggestionListAppearance() {
        usernameSuggestionList.setStyle(
                "-fx-background-color: #d1dbe4;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 5;"
        );

        usernameSuggestionList.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                setFont(Font.font("Arial Rounded MT Bold", 16));
                setTextFill(Color.web("#061742"));

                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setText(item);
                    setStyle(
                            "-fx-background-color: #d1dbe4;" +
                                    "-fx-background-radius: 20;" +
                                    "-fx-background-insets: 5;" +
                                    "-fx-padding: 7;"
                    );
                }
            }
        });
    }

    private void setupSuggestionListBehavior() {
        usernameSuggestionList.setOnMouseClicked(event -> {
            String selected = usernameSuggestionList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                usernameSearchField.setText(selected);
                usernameSuggestionList.setVisible(false);
            }
        });
    }

    private void setupSearchFieldBehavior() {
        usernameSearchField.textProperty().addListener((obs, oldText, newText) -> {

            if (newText == null || newText.isEmpty()) {
                usernameSuggestionList.setVisible(false);
                return;
            }

            List<String> results = UserDAO.searchInDatabase(newText);

            if (results.isEmpty()) {
                usernameSuggestionList.setVisible(false);
            } else {
                usernameSuggestionList.getItems().setAll(results);
                usernameSuggestionList.setVisible(true);
            }
        });
        usernameSearchField.setOnAction(e -> usernameSuggestionList.setVisible(false));
    }


    public void calculateBalanceAfterTransaction(){
        if(!isNumeric(transferAmountInputField.getText())) {
            balanceLabel.setText("Only Numbers Are Valid");
            return;
        }

        double balance = UserSession.getUser().getBalance();
        double transferAmount = Double.parseDouble(transferAmountInputField.getText());

        if(transferAmount < 0) {
            balanceLabel.setText("Invalid Amount");
            return;
        }

        double balanceAfterTransaction = balance - transferAmount;
        if(balanceAfterTransaction < 0)
            balanceLabel.setText("Not Enough Balance");
        else
            balanceLabel.setText(String.valueOf(balanceAfterTransaction));
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

    public void onHoverButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle(onHoverStyle);
    }

    public void onHoverExitButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle(defaultStyle);
    }

    public boolean checkPassword(){
        String password = passwordInputField.getText();
        String username = UserSession.getUser().getUsername();

        return UserDAO.validateLogin(username, password);
    }

    public boolean checkIfUsernameExists(){
        String username = usernameSearchField.getText();
        return UserDAO.usernameExists(username);
    }

    public boolean isTransferAmountValid(){
        return isNumeric(balanceLabel.getText());
    }

    public boolean areAllInfoValid(){
        if(!checkPassword()){
            paymentStatus.setText("Wrong Password");
            return false;
        }
        else if(!checkIfUsernameExists()){
            paymentStatus.setText("Username Not Found");
            return false;
        }
        else if(!isTransferAmountValid()){
            paymentStatus.setText("Invalid Amount");
            return false;
        }
        else {
            paymentStatus.setText("Success");
            return true;
        }
    }

    public void proceedPayment(){
        if(areAllInfoValid()){
            System.out.println("Payment Done");
        }
    }
}
