package org.example.Controller.Order;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.example.Controller.Employee.EmployeeController;
import org.example.Controller.Product.ProductController;
import org.example.db.DBConnection;
import org.example.dto.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceOrderFormController implements Initializable {
    public Label lblDate;
    public Label lblTime;
    public ComboBox cmbEmployeeIDs;
    public ComboBox cmbProductCodes;
    public Label lblUsername;

//    public Label lblAddress;
//    public Label lblSalary;
//    public Label lblCity;


    public Label lblDesc;
    public Label lblCategory;
    public Label lblUnitPrice;
    public Label lblQty;
    public Label lblOrderId;
    public TableView tblCart;
    public TableColumn colProductCode;
    public TableColumn colDesc;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TextField txtQtyFroCustomer;
    public Label lblNetTotal;


//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        loadDateAndTime();
//        loadEmployeeIDs();
//        loadProductCodes();
//        generateOrderId();
//        colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
//        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
//        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
//        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
//        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
//
//        cmbEmployeeIDs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            setEmployeeDataFroLbl((String) newValue);
//        });
//        cmbProductCodes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            setProductDataFroLbl((String) newValue);
//        });
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        loadEmployeeIDs();
        loadProductCodes();
        generateOrderId();
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        cmbEmployeeIDs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setEmployeeDataFroLbl(newValue.toString()); // Convert to string as the method expects a string parameter
        });
        cmbProductCodes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setProductDataFroLbl(newValue.toString()); // Convert to string as the method expects a string parameter
        });
    }

    private void setProductDataFroLbl(String ProductCode) {
        Product product = ProductController.getInstance().searchProduct(ProductCode);
        System.out.println(product);
        lblDesc.setText(product.getDescription());
        lblCategory.setText(product.getCategory());
        lblUnitPrice.setText(String.valueOf(product.getUnitPrice()));
        lblQty.setText(String.valueOf(product.getQtyOnHand()));
    }

    private void setEmployeeDataFroLbl(String employeeId) {
        Employee employee = EmployeeController.getInstance().searchEmployee(employeeId);
        System.out.println(employee);
        lblUsername.setText(employee.getUsername());
//        lblAddress.setText(customer.getAddress());
//        lblCity.setText(customer.getCity());
//        lblSalary.setText(String.valueOf(customer.getSalary()));
    }

    private void loadProductCodes() {
        ObservableList<Product> allProduct = ProductController.getInstance().getAllProduct();

        ObservableList<String> productCodes = FXCollections.observableArrayList();

        allProduct.forEach(item -> {
            productCodes.add(item.getProductCode());
        });

        cmbProductCodes.setItems(productCodes);
    }

    private void loadEmployeeIDs() {
        ObservableList<Employee> allEmployee = EmployeeController.getInstance().getAllEmployee();

        ObservableList<String> ids = FXCollections.observableArrayList();

        allEmployee.forEach(employee -> {
            ids.add(employee.getEmployeeId().toString()); // Ensure employee ID is added as a string
        });

        System.out.println(ids);
        cmbEmployeeIDs.setItems(ids);
    }

    private void loadDateAndTime() {
        //Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));


        //Time
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            lblTime.setText(
                    time.getHour() + " : " + time.getMinute() + " : " + time.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void generateOrderId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM orders");
            Integer count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);

            }
            if (count == 0) {
                lblOrderId.setText("O001");
            }
            String lastOrderId = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT OrderID\n" +
                    "FROM orders\n" +
                    "ORDER BY OrderID DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblOrderId.setText(String.format("O%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING, "hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    ObservableList<CartTbl> cartList = FXCollections.observableArrayList();

    public void btnAddToCartOnAction(ActionEvent actionEvent) {

        String productCode = (String) cmbProductCodes.getValue();
        String description = lblDesc.getText();
        Integer qtyFroCustomer = Integer.parseInt(txtQtyFroCustomer.getText());
        Double unitPrice = Double.valueOf(lblUnitPrice.getText());
        Double total = qtyFroCustomer * unitPrice;
        CartTbl cartTbl = new CartTbl(productCode, description, qtyFroCustomer, unitPrice, total, 0.0);
        System.out.println(cartTbl);

        int qtyStock = Integer.parseInt(lblQty.getText());
        if (qtyStock < qtyFroCustomer) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        cartList.add(cartTbl);


        tblCart.setItems(cartList);
        calcNetTotal();

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        cartList.clear(); // Clear the cart list
        tblCart.setItems(cartList); // Refresh the table view
        lblNetTotal.setText("0.0/="); // Reset the net total label
    }

//    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
//        try {
//            String orderId = lblOrderId.getText();
//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            Date orderDate = format.parse(lblDate.getText());
//            String employeeId = cmbEmployeeIDs.getValue().toString();
//
//            List<OrderDetail> orderDetailList = new ArrayList<>();
//            for (CartTbl cartTbl : cartList) {
//                String productCode = cartTbl.getProductCode();
//
//                // Log the product code for debugging
//                System.out.println("ProductCode: " + productCode);
//
//                // Validate the length of the ProductCode
//                if (productCode.length() > 50) {
//                    throw new IllegalArgumentException("ProductCode length exceeds the maximum allowed length.");
//                }
//
//                int qty = cartTbl.getQty();
//                double discount = cartTbl.getDiscount();
//                orderDetailList.add(new OrderDetail(orderId, productCode, qty, discount));
//            }
//
//            Orders orders = new Orders(orderId, orderDate, employeeId, orderDetailList);
//            boolean isOrderPlaced = OrderController.getInstance().placeOrder(orders);
//            if (isOrderPlaced) {
//                generateOrderId();
//                new Alert(Alert.AlertType.INFORMATION, "Order Placed!").show();
//            } else {
//                new Alert(Alert.AlertType.ERROR, "Failed to place order.").show();
//            }
//        } catch (ParseException | SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Error occurred: " + e.getMessage()).show();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Validation error: " + e.getMessage()).show();
//        }
//    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        try {

            String orderId = lblOrderId.getText();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate = format.parse(lblDate.getText());
            String employeeId = cmbEmployeeIDs.getValue().toString();

            List<OrderDetail> orderDetailList = new ArrayList<>();

            for (CartTbl cartTbl : cartList ){
                String oID = lblOrderId.getText();
                String productCode = cartTbl.getProductCode();
                Integer qty = cartTbl.getQty();
                Double discount = cartTbl.getDiscount();
                orderDetailList.add(new OrderDetail(oID,productCode,qty,discount));
            }

            Orders orders = new Orders(orderId, orderDate, employeeId, orderDetailList);
            Boolean isOrderPlace = OrderController.getInstance().placeOrder(orders);
            if (isOrderPlace){
                generateOrderId();
                new Alert(Alert.AlertType.INFORMATION,"order Place !!").show();
            }else {
                generateOrderId();
                new Alert(Alert.AlertType.INFORMATION,"order Place !!").show();
            }
            System.out.println(orders);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void txtAddtoCartOnAction(ActionEvent actionEvent) {
        btnAddToCartOnAction(actionEvent);
    }

    public void calcNetTotal() {
        double ttl = 0;
        for (CartTbl cartObj : cartList) {
            ttl += cartObj.getTotal();
        }
        lblNetTotal.setText(String.valueOf(ttl) + "/=");
    }

    public void btnCommitOnAction(ActionEvent actionEvent) {
        try {
            System.out.println("Commit");
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnRollBackOnAction(ActionEvent actionEvent) {
        try {
            System.out.println("RollBack");
            Connection connection = DBConnection.getInstance().getConnection();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnApiCallOnAction(ActionEvent actionEvent) throws IOException {
//        //create URL
//        URL url = new URL("https://jsonplaceholder.typicode.com/todos/1");
//
//        //create a connection
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        //set Request method
//        connection.setRequestMethod("GET");
//        //get Response code
//        int responseCode = connection.getResponseCode();
//        System.out.println(responseCode);
//
//        //read response body
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//        while ((inputLine = bufferedReader.readLine()) != null) {
//            response.append(inputLine);
//        }
//        bufferedReader.close();
//
//        System.out.println(response);
//
//        Gson gson = new Gson();
//        Sample sample = gson.fromJson(response.toString(), Sample.class);
//        System.out.println(sample);
    }
}
