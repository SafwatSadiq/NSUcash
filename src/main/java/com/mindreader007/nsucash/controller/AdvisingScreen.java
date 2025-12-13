package com.mindreader007.nsucash.controller;

import com.mindreader007.nsucash.model.Course;
import com.mindreader007.nsucash.model.AdvisingStudent;
import com.mindreader007.nsucash.services.CourseAdvisingService;
import com.mindreader007.nsucash.services.UserSession;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class AdvisingScreen {

    @FXML private TableView<Course> tblAvailable;
    @FXML private TableColumn<Course, String> colCode;
    @FXML private TableColumn<Course, String> colName;
    @FXML private TableColumn<Course, Integer> colCredits;
    @FXML private TableColumn<Course, Integer> colFee;

    @FXML private ListView<String> lvSelected; // show course codes + names
    @FXML private Label lblTotal;
    @FXML private Button btnAdd;
    @FXML private Button btnRemove;
    @FXML private Button btnPay;

    private final CourseAdvisingService service = new CourseAdvisingService();
    private final ObservableList<Course> availableObs = FXCollections.observableArrayList();
    private final ObservableList<String> selectedObs = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colCode.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getCode()));
        colName.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getName()));
        colCredits.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getCredits()));
        colFee.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getFee()));

        availableObs.setAll(service.getCatalog());
        tblAvailable.setItems(availableObs);
        lvSelected.setItems(selectedObs);

        refreshSelected();
    }

    private String username() {
        if (UserSession.getUser() == null) return "guest";
        return UserSession.getUser().getUsername();
    }
    private String displayName() {
        if (UserSession.getUser() == null) return "Guest";
        return UserSession.getUser().getName();
    }

    private void refreshSelected() {
        selectedObs.clear();
        service.getSelectedCoursesFor(username()).forEach(c -> selectedObs.add(c.getCode() + " - " + c.getName() + " (" + c.getCredits() + "cr) : " + c.getFee() + " BDT"));
        lblTotal.setText("Total: " + service.calculateTotalAmountForUser(username()) + " BDT");
    }

    @FXML
    private void onAdd() {
        Course sel = tblAvailable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert(AlertType.INFORMATION, "Select a course from the table first.");
            return;
        }
        try {
            service.addCourseForUser(username(), displayName(), sel);
            refreshSelected();
        } catch (Exception e) {
            showAlert(AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    private void onRemove() {
        String sel = lvSelected.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert(AlertType.INFORMATION, "Select a course in the selected list to remove.");
            return;
        }
        String code = sel.split(" - ", 2)[0].trim();
        service.removeCourseForUser(username(), code);
        refreshSelected();
    }

    @FXML
    private void onPay() {
        if (service.getSelectedCoursesFor(username()).isEmpty()) {
            showAlert(AlertType.WARNING, "No courses selected. Add some courses first.");
            return;
        }

        int total = service.calculateTotalAmountForUser(username());
        Alert confirm = new Alert(AlertType.CONFIRMATION, "Proceed to pay " + total + " BDT?", ButtonType.OK, ButtonType.CANCEL);
        confirm.setHeaderText("Confirm Payment");
        confirm.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                String receipt = service.confirmPayment(username(), displayName());
                if (receipt != null) {
                    showAlert(AlertType.INFORMATION, "Payment successful! Receipt: " + receipt);
                    refreshSelected();
                } else {
                    showAlert(AlertType.ERROR, "Payment recorded but failed to write receipt.");
                }
            }
        });
    }

    private void showAlert(AlertType t, String msg) {
        Alert a = new Alert(t, msg);
        a.showAndWait();
    }
}
