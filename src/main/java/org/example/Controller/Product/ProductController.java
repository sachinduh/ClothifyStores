package org.example.Controller.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.db.DBConnection;
import org.example.dto.OrderDetail;
import org.example.dto.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductController {

    private static ProductController instance;

    private ProductController() {
    }

    public static ProductController getInstance() {
        if (instance == null) {
            instance = new ProductController();
        }
        return instance;
    }

    public ObservableList<Product> getAllProduct() {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM product");
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getString("ProductCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("Category"),
                        resultSet.getString("Size"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                );
                productList.add(product);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public boolean addProduct(Product product) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, product.getProductCode());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setString(3, product.getCategory());
            preparedStatement.setString(4, product.getSize());
            preparedStatement.setDouble(5, product.getUnitPrice());
            preparedStatement.setInt(6, product.getQtyOnHand());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProduct(Product product) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE product SET Description=?, Category=?, Size=?, UnitPrice=?, QtyOnHand=? WHERE ProductCode=?");
            preparedStatement.setString(1, product.getDescription());
            preparedStatement.setString(2, product.getCategory());
            preparedStatement.setString(3, product.getSize());
            preparedStatement.setDouble(4, product.getUnitPrice());
            preparedStatement.setInt(5, product.getQtyOnHand());
            preparedStatement.setString(6, product.getProductCode());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeProduct(String productCode) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM product WHERE ProductCode=?");
            preparedStatement.setString(1, productCode);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Product searchProduct(String productCode) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product WHERE ProductCode=?");
            preparedStatement.setString(1, productCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Product(
                        resultSet.getString("ProductCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("Category"),
                        resultSet.getString("Size"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStock(List<OrderDetail> orderDetailList) {
        boolean isUpdate = false;
        for (OrderDetail orderDetail : orderDetailList) {
            List<OrderDetail> OrderDetail = null;
            isUpdate = updateStock(OrderDetail);
        }
        if (isUpdate){
            return true;
        }
        return false;
    }

}
