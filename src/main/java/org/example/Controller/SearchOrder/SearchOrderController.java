package org.example.Controller.SearchOrder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.db.DBConnection;
import org.example.dto.SearchOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchOrderController {

    private static SearchOrderController instance;

    private SearchOrderController() {
    }

    public static SearchOrderController getInstance() {
        if (instance == null) {
            instance = new SearchOrderController();
        }
        return instance;
    }

    public ObservableList<SearchOrder> getAllSearchOrder() {
        ObservableList<SearchOrder> searchOrdersList = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM SearchOrder");
            while (resultSet.next()) {
                SearchOrder searchOrder = new SearchOrder(
                        resultSet.getString("OrderId"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDate("Date"),
                        resultSet.getInt("OrderQty"),
                        resultSet.getInt("Discount")
                );
                searchOrdersList.add(searchOrder);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return searchOrdersList;
    }

    public SearchOrder searchSearchOrder(String OrderId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SearchOrder WHERE OrderId=?");
            preparedStatement.setString(1, OrderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new SearchOrder(
                        resultSet.getString("OrderId"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDate("Date"),
                        resultSet.getInt("OrderQty"),
                        resultSet.getInt("Discount")
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
