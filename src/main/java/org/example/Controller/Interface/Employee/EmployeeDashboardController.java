package org.example.Controller.Interface.Employee;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDashboardController implements Initializable {
    @FXML
    public Label lblProduct;
    @FXML
    public Label lblSupplier;
    @FXML
    public Label lblOrder;
    @FXML
    public Label lblReports;
    public JFXCheckBox chboxDo2;
    public JFXCheckBox chboxDo4;
    public JFXCheckBox chboxDo5;
    public JFXCheckBox chboxDo6;
    public JFXCheckBox chboxDo7;
    public JFXCheckBox chboxDo1;
    public JFXCheckBox chboxDo8;
    public JFXCheckBox chboxDo3;
    public JFXCheckBox chboxDoo9;
    public JFXCheckBox chboxDo10;
    public JFXTextField txtEmpId;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblProduct.setOnMouseClicked(mouseEvent -> openAddProductFormController());
        lblSupplier.setOnMouseClicked(mouseEvent -> openAddSupplierFormController());
        lblOrder.setOnMouseClicked(mouseEvent -> openPlaceOrderFormController());
        lblReports.setOnMouseClicked(mouseEvent -> openReportController());

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

    private void openPlaceOrderFormController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PlaceOrderForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Place Order Form");
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

    public void btnLogOutOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully logged out!");

        alert.showAndWait();

        // Close the window
        Stage stage = (Stage) txtEmpId.getScene().getWindow();
        stage.close();
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        try {
            // Load the Register Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
