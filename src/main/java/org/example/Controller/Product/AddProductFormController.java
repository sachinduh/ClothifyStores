package org.example.Controller.Product;

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
import org.example.dto.Product;
import org.example.dto.Producttm.Table01;
import org.example.dto.Producttm.Table02;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable {

    public JFXTextField txtProductCode;
    public JFXTextField txtCategoryField;
    public TableView<Table01> tblProduct1;
    public TableColumn<Table01, String> colProductCode;
    public TableColumn<Table01, String> colCategory;
    public TableView<Table02> tblProduct2;
    public TableColumn<Table02, String> colProductCodeTbl2;
    public JFXTextField txtDescription;
    public JFXTextField txtSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public TableColumn<Table01, String> colDescription;
    public TableColumn<Table01, String> colSize;
    public TableColumn<Table02, Double> colUnitPrice;
    public TableColumn<Table02, Integer> colQtyOnHand;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up table columns
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colProductCodeTbl2.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        // Load initial data into tables
        loadTable01();
        loadTable02();
    }

    private void loadTable01() {
        ObservableList<Table01> tbl01 = FXCollections.observableArrayList();
        ObservableList<Product> allProduct = ProductController.getInstance().getAllProduct();

        allProduct.forEach(product -> {
            tbl01.add(new Table01(
                    product.getProductCode(),
                    product.getDescription(),
                    product.getCategory(),
                    product.getSize()
            ));
        });
        tblProduct1.setItems(tbl01);
    }

    private void loadTable02() {
        ObservableList<Table02> table02s = FXCollections.observableArrayList();
        ObservableList<Product> allProduct = ProductController.getInstance().getAllProduct();

        allProduct.forEach(product -> {
            table02s.add(new Table02(
                    product.getProductCode(),
                    product.getUnitPrice(),
                    product.getQtyOnHand()
            ));
        });
        tblProduct2.setItems(table02s);
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully logged out!");

        alert.showAndWait();

        // Close the window
        Stage stage = (Stage) txtProductCode.getScene().getWindow();
        stage.close();
    }

    public void btnAddProductOnAction(ActionEvent actionEvent) {
        try {
            Product product = new Product(
                    txtProductCode.getText(),
                    txtDescription.getText(),
                    txtCategoryField.getText(),
                    txtSize.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQtyOnHand.getText())
            );

            boolean isAdded = ProductController.getInstance().addProduct(product);
            if (!isAdded) {
                new Alert(Alert.AlertType.ERROR, "Product Not Added!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Product Added!").show();
                loadTable01(); // Reload table data
                loadTable02();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input! Please check your data.").show();
        }
    }

    public void btnUpdateProductOnAction(ActionEvent actionEvent) {
        try {
            Product product = new Product(
                    txtProductCode.getText(),
                    txtDescription.getText(),
                    txtCategoryField.getText(),
                    txtSize.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQtyOnHand.getText())
            );

            boolean isUpdated = ProductController.getInstance().updateProduct(product);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Product Updated!").show();
                loadTable01(); // Reload table data
                loadTable02();
            } else {
                new Alert(Alert.AlertType.ERROR, "Product Not Updated!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input! Please check your data.").show();
        }
    }

    public void btnRemoveProductOnAction(ActionEvent actionEvent) {
        boolean isRemoved = ProductController.getInstance().removeProduct(txtProductCode.getText());
        if (isRemoved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Product Removed!").show();
            loadTable01(); // Reload table data
            loadTable02();
        } else {
            new Alert(Alert.AlertType.ERROR, "Product Not Removed!").show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        Product product = ProductController.getInstance().searchProduct(txtProductCode.getText());
        if (product != null) {
            txtDescription.setText(product.getDescription());
            txtCategoryField.setText(product.getCategory());
            txtSize.setText(product.getSize());
            txtUnitPrice.setText(String.valueOf(product.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(product.getQtyOnHand()));
        } else {
            new Alert(Alert.AlertType.ERROR, "Product Not Found!").show();
        }
    }
}
