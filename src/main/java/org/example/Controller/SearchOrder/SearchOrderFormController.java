package org.example.Controller.SearchOrder;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import org.example.dto.SearchOrdertm.Table01;
import org.example.dto.SearchOrdertm.Table02;
import org.example.dto.SearchOrder;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchOrderFormController implements Initializable {
    public TableColumn colOrderIdtbl02;
    public TableColumn colProductCodetbl02;
    public TableColumn colOrderQty;
    public TableColumn colDiscount;
    public TableView tblSearchOrder;
    public TableColumn colOrderId;
    public TableColumn colProductCode;
    public TableColumn colDate;
    public DatePicker dateDate;
    public JFXTextField txtOrderId;
    public JFXTextField txtProductCode;
    public JFXTextField txtOrderQty;
    public JFXTextField txtDiscount;
    public TableView tblSearchOrder01;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up table columns
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colOrderIdtbl02.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colProductCodetbl02.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        // Load initial data into tables
        loadTable01();
        loadTable02();
    }

    private void loadTable01() {
        ObservableList<Table01> tbl01 = FXCollections.observableArrayList();
        ObservableList<SearchOrder> allSearchOrder = SearchOrderController.getInstance().getAllSearchOrder();

        allSearchOrder.forEach(searchOrder -> {
            tbl01.add(new Table01(
                    searchOrder.getOrderId(),
                    searchOrder.getProductCode(),
                    searchOrder.getDate()
            ));
        });
        tblSearchOrder.setItems(tbl01);
    }

    private void loadTable02() {
        ObservableList<Table02> table02s = FXCollections.observableArrayList();
        ObservableList<SearchOrder> allSearchOrder = SearchOrderController.getInstance().getAllSearchOrder();

        allSearchOrder.forEach(searchOrder -> {
            table02s.add(new Table02(
                    searchOrder.getOrderId(),
                    searchOrder.getProductCode(),
                    searchOrder.getOrderQty(),
                    searchOrder.getDiscount()
            ));
        });
        tblSearchOrder01.setItems(table02s);
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully logged out!");

        alert.showAndWait();

        // Close the window
        Stage stage = (Stage) txtOrderId.getScene().getWindow();
        stage.close();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        SearchOrder searchOrder = SearchOrderController.getInstance().searchSearchOrder(txtOrderId.getText());
        if (searchOrder != null) {
            txtProductCode.setText(searchOrder.getProductCode());
            txtOrderQty.setText(String.valueOf(searchOrder.getOrderQty()));
            txtDiscount.setText(String.valueOf(searchOrder.getDiscount()));
        } else {
            new Alert(Alert.AlertType.ERROR, "Order Not Found!").show();
        }
    }
}
