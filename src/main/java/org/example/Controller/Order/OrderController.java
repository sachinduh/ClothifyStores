package org.example.Controller.Order;

import org.example.Controller.Product.ProductController;
import org.example.db.DBConnection;
import org.example.dto.Orders;
import org.example.dto.Product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderController {
    private static OrderController instance;

    private OrderController() {}

    public Boolean placeOrder(Orders orders) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            // Convert java.util.Date to java.sql.Date
            Date sqlDate = new Date(orders.getOrderDate().getTime());

            // Check if product exists
            if (!isProductExists(connection, orders.getProductCode())) {
                System.err.println("Product with code " + orders.getProductCode() + " does not exist.");
                connection.rollback();
                return false;
            }

            // Insert order
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO orders (orderId, productCode, orderDate) VALUES (?, ?, ?)");
            psTm.setString(1, orders.getOrderId());
            psTm.setString(3, orders.getProductCode());
            psTm.setDate(2, sqlDate);

            int isOrderAdd = psTm.executeUpdate();

            if (isOrderAdd > 0) {
                boolean isOrderDetailAdd = OrderDetailController.getInstance().addOrderDetail(orders.getOrderDetailList());
                if (isOrderDetailAdd) {
                    boolean isUpdateStock = ProductController.getInstance().updateProduct((Product) orders.getOrderDetailList());
                    if (isUpdateStock) {
                        System.out.println("Commit True--------------");
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            connection.rollback();
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
            System.out.println("Connection auto-commit reset to true");
        }
    }

    private boolean isProductExists(Connection connection, String productCode) throws SQLException {
        String query = "SELECT 1 FROM product WHERE productCode = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, productCode);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static OrderController getInstance() {
        if (instance == null) {
            instance = new OrderController();
        }
        return instance;
    }
}
