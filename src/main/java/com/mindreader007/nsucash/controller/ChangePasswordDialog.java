package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.services.UserDAO;
import com.mindreader007.nsucash.services.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class ChangePasswordDialog {
    @FXML private PasswordField currentPassword;
    @FXML private PasswordField newPassword;
    @FXML private PasswordField confirmPassword;

    private Stage dialogStage;

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    @FXML
    private void onChangePassword() {

        String current = currentPassword.getText();
        String newPass = newPassword.getText();
        String confirm = confirmPassword.getText();

        if (!UserDAO.validateLogin(UserSession.getUser().getUsername(), current)) {
            showError("Current password is incorrect");
            currentPassword.getStyleClass().add("error");
            return;
        }
        currentPassword.getStyleClass().remove("error");

        if (newPass.length() < 6) {
            showError("Password must be at least 6 characters");
            newPassword.getStyleClass().add("error");
            return;
        }
        newPassword.getStyleClass().remove("error");

        if (!newPass.equals(confirm)) {
            showError("Passwords do not match");
            confirmPassword.getStyleClass().add("error");
            return;
        }
        confirmPassword.getStyleClass().remove("error");

        if(UserDAO.changePassword(UserSession.getUser().getUsername(), newPass))
            showSuccess("Password changed successfully");
        else
            showError("Something Went Wrong");
        dialogStage.close();
    }

    @FXML
    private void onCancel() {
        dialogStage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert);
        alert.showAndWait();
    }

    @FXML
    private void moveToNewPassword(){
        newPassword.requestFocus();
    }

    @FXML
    private void moveToConfirmPassword(){
        confirmPassword.requestFocus();
    }

    private void setAlertIcon(Alert alert){
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream("/com/mindreader007/nsucash/image/NSUCash.png")
                        )
                )
        );
    }
}
