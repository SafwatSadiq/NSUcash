package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.services.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AccountScreen implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label deptLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label ageLabel;

    @FXML
    private AnchorPane contentArea;

    private MainScreen mainScreen;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(UserSession.getUser().getName());
        deptLabel.setText(UserSession.getUser().getDepartment());
        balanceLabel.setText("Tk " + UserSession.getUser().getBalance());
        usernameLabel.setText(UserSession.getUser().getUsername());
        phoneLabel.setText(UserSession.getUser().getPhoneNumber());
        ageLabel.setText(String.valueOf(UserSession.getUser().getAge()));
    }

    @FXML
    private void onInstagramClicked() {
        showSocialAlert("Instagram", "Instagram ID: @" + UserSession.getUser().getUsername());
    }

    @FXML
    private void onFacebookClicked() {
        showSocialAlert("Facebook", "Facebook ID: @" + UserSession.getUser().getUsername());
    }

    @FXML
    private void onTelegramClicked() {
        showSocialAlert("Telegram", "Telegram ID: @" + UserSession.getUser().getUsername());
    }

    @FXML
    private void onEmailClicked() {
        showSocialAlert("Email", "Email ID: " + UserSession.getUser().getUsername() + "@gmail.com");
    }

    @FXML
    private void onLinkedinClicked() {
        showSocialAlert("Linkedin", "Linkedin ID: @" + UserSession.getUser().getUsername());
    }


    @FXML
    private void onTopUpClick(ActionEvent event) {
        if (mainScreen != null) {
            mainScreen.topUpButtonClick();
        }
    }

    public void setMainScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    @FXML
    private void onChangePasswordClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mindreader007/nsucash/fxml/ChangePasswordDialog.fxml")
            );

            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change Password");
            dialogStage.getIcons().add(
                    new Image(
                            Objects.requireNonNull(
                                    getClass().getResourceAsStream("/com/mindreader007/nsucash/image/NSUCash.png")
                            )
                    )
            );

            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setScene(new Scene(root));

            ChangePasswordDialog controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onContactSupportClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Support");
        alert.setHeaderText(null);
        alert.setContentText("Support Email: NSUcash@nsucash.com\nSupport Number: 01XXXXXXXXX\n");
        setAlertIcon(alert);
        setAlertCSS(alert);

        alert.showAndWait();
    }

    private void setAlertIcon(Alert alert) {
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream("/com/mindreader007/nsucash/image/NSUCash.png")
                        )
                )
        );
    }

    private void setAlertCSS(Alert alert) {
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/com/mindreader007/nsucash/css/Alert.css").toExternalForm()
        );
    }

    private void showSocialAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        setAlertIcon(alert);
        setAlertCSS(alert);

        alert.showAndWait();
    }
}
