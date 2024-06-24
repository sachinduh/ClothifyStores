package org.example.Controller.Interface.Admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.Controller.Employee.EmployeeController;
import org.example.dto.Employee;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML
    public Label lblManage;
    @FXML
    public Label lblProduct;
    @FXML
    public Label lblSupplier;
    @FXML
    public Label lblOrder;
    @FXML
    public Label lblReports;
    public JFXComboBox cmbPosition;
    public JFXTextField txtName;
    //public JFXTextField txtLastName;
    public JFXTextField txtEmail;
    public JFXTextField txtUserName;
    //public JFXTextField txtPassword;
    //public JFXTextField txtConfirmPassword;
    public JFXTextField txtEmployeeId;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtPassword1;
    public JFXComboBox cmbTitle;
    public JFXTextField txtSalary;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblManage.setOnMouseClicked(mouseEvent -> openEmployeeDashBoard());
        lblProduct.setOnMouseClicked(mouseEvent -> openAddProductFormController());
        lblSupplier.setOnMouseClicked(mouseEvent -> openAddSupplierFormController());
        lblOrder.setOnMouseClicked(mouseEvent -> openSearchOrderFormController());
        lblReports.setOnMouseClicked(mouseEvent -> openReportController());
        loadInitialValues();
        loadInitialValue();
    }

    private void loadInitialValue() {
        cmbTitle.setValue("Select Title");
        ObservableList list = FXCollections.observableArrayList();
        list.add(new String("MR"));
        list.add(new String("MS"));
        list.add(new String("MRS"));
        list.add(new String("MISS"));
        cmbTitle.setItems(list);
    }

    private void openReportController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ReportDashBoard.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Report DashBoard");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Report DashBoard.").show();
        }
    }
    private void openAddSupplierFormController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddSupplierForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Supplier Form");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Add Supplier Form.").show();
        }
    }

    private void openSearchOrderFormController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SearchOrderForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Search Order Form");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Place Order Form.").show();
        }
    }

    private void openAddProductFormController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddProductForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Product Form");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Add Product Form.").show();
        }
    }

    private void openEmployeeDashBoard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/EmployeeDashboard.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Employee Dashboard");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Employee Dashboard.").show();
        }
    }

    private void loadInitialValues() {
        cmbPosition.setValue("Select Position");
        ObservableList list = FXCollections.observableArrayList();
        list.add(new String("Manager"));
        list.add(new String("Sales"));
        list.add(new String("Accountant"));
        list.add(new String("Clerk"));
        list.add(new String("Cashier"));
        list.add(new String("Servant"));
        list.add(new String("Watchman"));
        cmbPosition.setItems(list);
    }

    public void btnSubmitOnAction(ActionEvent actionEvent) {
        try {
            Employee employee = new Employee(
                    Integer.parseInt(txtEmployeeId.getText()),
                    cmbTitle.getValue().toString(),
                    txtName.getText(),
                    txtEmail.getText(),
                    txtPassword.getText(),
                    txtUserName.getText(),
                    txtSalary.getText(),
                    cmbPosition.getValue().toString()

            );

            boolean isAdded = EmployeeController.getInstance().submitEmployee(employee);
            if (!isAdded) {
                new Alert(Alert.AlertType.ERROR, "Employee Not Added!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Added!").show();
                //loadTable01(); // Reload table data
                //loadTable02();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input! Please check your data.").show();
        }
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        boolean isRemoved = EmployeeController.getInstance().clearEmployee(txtEmployeeId.getText());
        if (isRemoved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Data Cleared!").show();
            //loadTable01(); // Reload table data
            //loadTable02();
        } else {
            new Alert(Alert.AlertType.ERROR, "Employee Data Not Cleared!").show();
        }
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully logged out!");

        alert.showAndWait();

        // Close the window
        Stage stage = (Stage) txtEmployeeId.getScene().getWindow();
        stage.close();
    }

}
