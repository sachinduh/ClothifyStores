package org.example.Controller.Supplier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.db.DBConnection;
import org.example.dto.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierController {

    private static SupplierController instance;

    private SupplierController() {
    }

    public static SupplierController getInstance() {
        if (instance == null) {
            instance = new SupplierController();
        }
        return instance;
    }


    public ObservableList<Supplier> getAllSupplier() {
        ObservableList<Supplier> suppliersList = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM supplier");
            while (resultSet.next()) {
                Supplier supplier = new Supplier(
                        resultSet.getString("SupplierId"),
                        resultSet.getString("Name"),
                        resultSet.getString("Company"),
                        resultSet.getString("Email"),
                        resultSet.getString("Products"),
                        resultSet.getInt("Qty")
                );
                suppliersList.add(supplier);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return suppliersList;
    }

    public boolean addSupplier(Supplier supplier) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO supplier VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, supplier.getSupplierId());
            preparedStatement.setString(2, supplier.getName());
            preparedStatement.setString(3, supplier.getCompany());
            preparedStatement.setString(4, supplier.getEmail());
            preparedStatement.setString(5, supplier.getProducts());
            preparedStatement.setInt(6, supplier.getQty());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSupplier(Supplier supplier) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE supplier SET Name=?, Company=?, Email=?, Products=?, Qty=? WHERE SupplierId=?");
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2,supplier.getCompany());
            preparedStatement.setString(3, supplier.getEmail());
            preparedStatement.setString(4, supplier.getProducts());
            preparedStatement.setInt(5, supplier.getQty());
            preparedStatement.setString(6, supplier.getSupplierId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeSupplier(String supplierId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM supplier WHERE Supplierid=?");
            preparedStatement.setString(1, supplierId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Supplier searchSupplier(String supplierId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM supplier WHERE SupplierId=?");
            preparedStatement.setString(1, supplierId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Supplier(
                        resultSet.getString("SupplierId"),
                        resultSet.getString("Name"),
                        resultSet.getString("Company"),
                        resultSet.getString("Email"),
                        resultSet.getString("Products"),
                        resultSet.getInt("Qty")
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
