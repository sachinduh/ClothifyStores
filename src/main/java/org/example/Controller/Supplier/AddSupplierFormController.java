package org.example.Controller.Supplier;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.dto.Supplier;
import org.example.dto.Suppliertm.Table01;
import org.example.dto.Suppliertm.Table02;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSupplierFormController implements Initializable {
    public JFXTextField txtSupplierId;
    public JFXTextField txtName;
    public JFXTextField txtCompany;
    public JFXTextField txtEmail;
    public JFXTextField txtQty;
    public TableView<Table01> tblSuplier1;
    public TableColumn<Table01, String> colSupplierId;
    public TableColumn<Table01, String> colName;
    public TableColumn<Table01, String> colCompany;
    public TableColumn<Table01, String> colEmail;
    public TableColumn<Table02, String> colSupplierIdTbl2;
    public TableColumn<Table02, Integer> colQty;
    public TableView<Table02> tblSupplier2;
    public JFXTextField txtProducts;
    public TableColumn colProducts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up table columns
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierIdTbl2.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colProducts.setCellValueFactory(new PropertyValueFactory<>("products"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        // Load initial data into tables
        loadTable01();
        loadTable02();
    }

    private void loadTable01() {
        ObservableList<Table01> tbl01 = FXCollections.observableArrayList();
        ObservableList<Supplier> allSupplier = SupplierController.getInstance().getAllSupplier();

        allSupplier.forEach(supplier -> {
            tbl01.add(new Table01(
                    supplier.getSupplierId(),
                    supplier.getName(),
                    supplier.getCompany(),
                    supplier.getEmail()
            ));
        });
        tblSuplier1.setItems(tbl01);
    }

    private void loadTable02() {
        ObservableList<Table02> tbl02 = FXCollections.observableArrayList();
        ObservableList<Supplier> allSupplier = SupplierController.getInstance().getAllSupplier();

        allSupplier.forEach(supplier -> {
            tbl02.add(new Table02(
                    supplier.getSupplierId(),
                    supplier.getProducts(),
                    supplier.getQty()
            ));
        });
        tblSupplier2.setItems(tbl02);
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully logged out!");

        alert.showAndWait();

        // Close the window
        Stage stage = (Stage) txtSupplierId.getScene().getWindow();
        stage.close();
    }

    public void btnAddSupplierOnAction(ActionEvent actionEvent) {
        try {
            Supplier supplier = new Supplier(
                    txtSupplierId.getText(),
                    txtName.getText(),
                    txtCompany.getText(),
                    txtEmail.getText(),
                    txtProducts.getText(),
                    Integer.parseInt(txtQty.getText())
            );

            boolean isAdded = SupplierController.getInstance().addSupplier(supplier);
            if (!isAdded) {
                new Alert(Alert.AlertType.ERROR, "Supplier Not Added!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added!").show();
                loadTable01(); // Reload table data
                loadTable02();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input! Please check your data.").show();
        }
    }

    public void btnUpdateSupplierOnAction(ActionEvent actionEvent) {
        try {
            Supplier supplier = new Supplier(
                    txtSupplierId.getText(),
                    txtName.getText(),
                    txtCompany.getText(),
                    txtEmail.getText(),
                    txtProducts.getText(),
                    Integer.parseInt(txtQty.getText())
            );

            boolean isUpdated = SupplierController.getInstance().updateSupplier(supplier);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated!").show();
                loadTable01(); // Reload table data
                loadTable02();
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier Not Updated!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input! Please check your data.").show();
        }
    }

    public void btnRemoveSupplierOnAction(ActionEvent actionEvent) {
        boolean isRemoved = SupplierController.getInstance().removeSupplier(txtSupplierId.getText());
        if (isRemoved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Product Removed!").show();
            loadTable01(); // Reload table data
            loadTable02();
        } else {
            new Alert(Alert.AlertType.ERROR, "Product Not Removed!").show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        Supplier supplier = SupplierController.getInstance().searchSupplier(txtSupplierId.getText());
        if (supplier != null) {
            txtName.setText(supplier.getName());
            txtCompany.setText(supplier.getCompany());
            txtEmail.setText(supplier.getEmail());
            txtProducts.setText(supplier.getProducts());
            txtQty.setText(String.valueOf(supplier.getQty()));
        } else {
            new Alert(Alert.AlertType.ERROR, "Supplier Not Found!").show();
        }
    }
}
