package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.Main;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.Objects;

public class MainScreen {
    public MainScreen(){
        homeButtonPressed = true;
        topUpButtonPressed = false;
        transactionsButtonPressed = false;
        summaryButtonPressed = false;
        accountButtonPressed = false;
    }

    @FXML
    private Button LogOutButton;
    @FXML
    private Button viewBalance;
    @FXML
    private Button homeButton;
    @FXML
    private Button topUpButton;
    @FXML
    private Button summaryButton;
    @FXML
    private Button transactionsButton;
    @FXML
    private Button accountButton;
    @FXML
    private AnchorPane contentArea;
    @FXML
    private BorderPane mainPanel;

    private boolean homeButtonPressed;
    private boolean topUpButtonPressed;
    private boolean summaryButtonPressed;
    private boolean transactionsButtonPressed;
    private boolean accountButtonPressed;

    private String onPressedStyle = "-fx-background-color: #061742; -fx-background-radius: 30; -fx-border-width: 5; -fx-border-color: #d1dbe4; -fx-border-radius: 30; -fx-text-fill: #d1dbe4;";
    private String onHoverStyle = "-fx-background-color: #a3b7ca; -fx-background-radius: 30; -fx-text-fill: #061742;";
    private String defaultStyle = "-fx-background-radius: 30; -fx-background-color: #d1dbe4; -fx-text-fill: #061742;";

    public void userLogout() throws IOException {
        Main m = new Main();
        m.changeScene("fxml/LoginScreen.fxml", 800, 400);
    }

    public void onHoverButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        if ((btn.getId() != null && btn.getId().equals("homeButton") && !homeButtonPressed) ||
                (btn.getId() != null && btn.getId().equals("topUpButton") && !topUpButtonPressed) ||
                (btn.getId() != null && btn.getId().equals("summaryButton") && !summaryButtonPressed) ||
                (btn.getId() != null && btn.getId().equals("transactionsButton") && !transactionsButtonPressed) ||
                (btn.getId() != null && btn.getId().equals("accountButton") && !accountButtonPressed) ||
                (btn.getId() != null && btn.getId().equals("LogOutButton")) ||
                (btn.getId() != null && btn.getId().equals("viewBalance")))
            btn.setStyle(onHoverStyle);
    }

    public void onHoverExitButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        if ((btn.getId() != null && btn.getId().equals("homeButton") && !homeButtonPressed) ||
                (btn.getId() != null && btn.getId().equals("topUpButton") && !topUpButtonPressed) ||
                (btn.getId() != null && btn.getId().equals("summaryButton") && !summaryButtonPressed) ||
                (btn.getId() != null && btn.getId().equals("transactionsButton") && !transactionsButtonPressed) ||
                (btn.getId() != null && btn.getId().equals("accountButton") && !accountButtonPressed) ||
                (btn.getId() != null && btn.getId().equals("LogOutButton"))||
                (btn.getId() != null && btn.getId().equals("viewBalance")))
            btn.setStyle(defaultStyle);
    }

    public void showBalance(){
        viewBalance.setText("Tk 1052.25");
        viewBalance.setFont(new Font("Arial Black", 16));
        viewBalance.setStyle("-fx-background-radius: 30; -fx-background-color: #d1dbe4; -fx-text-fill: #061742;");
    }

    private void loadPage(String fxml){
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
            contentArea.getChildren().setAll(pane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void topUpButtonClick(){
        loadPage("/com/mindreader007/nsucash/fxml/TopUpScreen.fxml");

        updateAllButtonsToDefaultState();
        topUpButtonPressed = true;
        updateButtonStyle(topUpButton, onPressedStyle, topUpButtonPressed);
    }

    public void homeButtonClick(){
        loadPage("/com/mindreader007/nsucash/fxml/HomeScreen.fxml");

        updateAllButtonsToDefaultState();
        homeButtonPressed = true;
        updateButtonStyle(homeButton, onPressedStyle, homeButtonPressed);
    }

    public void summaryButtonClick(){
        loadPage("/com/mindreader007/nsucash/fxml/SummaryScreen.fxml");

        updateAllButtonsToDefaultState();
        summaryButtonPressed = true;
        updateButtonStyle(summaryButton, onPressedStyle, summaryButtonPressed);
    }

    public void transactionsButtonClick(){
        loadPage("/com/mindreader007/nsucash/fxml/TransactionsScreen.fxml");

        updateAllButtonsToDefaultState();
        transactionsButtonPressed = true;
        updateButtonStyle(transactionsButton, onPressedStyle, transactionsButtonPressed);
    }

    public void accountButtonClick(){
        loadPage("/com/mindreader007/nsucash/fxml/AccountScreen.fxml");

        updateAllButtonsToDefaultState();
        accountButtonPressed = true;
        updateButtonStyle(accountButton, onPressedStyle, accountButtonPressed);
    }

    public void updateAllButtonsToDefaultState(){
        homeButtonPressed = false;
        topUpButtonPressed = false;
        summaryButtonPressed = false;
        transactionsButtonPressed = false;
        accountButtonPressed = false;

        updateButtonStyle(homeButton, defaultStyle, homeButtonPressed);
        updateButtonStyle(topUpButton, defaultStyle, topUpButtonPressed);
        updateButtonStyle(summaryButton, defaultStyle, summaryButtonPressed);
        updateButtonStyle(transactionsButton, defaultStyle, transactionsButtonPressed);
        updateButtonStyle(accountButton, defaultStyle, accountButtonPressed);
    }

    public void updateButtonStyle(Button btn, String style, Boolean isPressed){
        btn.setStyle(style);
        FontAwesomeIconView icon = (FontAwesomeIconView) btn.lookup(".glyph-icon");
        if(icon != null){
            if(isPressed) icon.setFill(Color.web("#d1dbe4"));
            else icon.setFill(Color.web("#061742"));
        }
    }
}
