package org.example.Controller.Login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    public JFXTextField txtUserName;
    @FXML
    public JFXPasswordField txtPassword;

    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clothifystores";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "688432";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private String getUserRole(String username, String password) {
        String role = null;

        try (Connection conn = connect()) {
            String sqlAdmin = "SELECT * FROM Admin WHERE Username = ? AND Password = ?";
            String sqlEmployee = "SELECT * FROM Employee WHERE username = ? AND password = ?";

            // Check admin table
            try (PreparedStatement pstmt = conn.prepareStatement(sqlAdmin)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        role = "admin";
                        System.out.println("Admin login successful for username: " + username);
                    }
                }
            }

            // If not found in admin table, check employee table
            if (role == null) {
                try (PreparedStatement pstmt = conn.prepareStatement(sqlEmployee)) {
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            role = "user";
                            System.out.println("Employee login successful for username: " + username);
                        } else {
                            System.out.println("No matching employee found for username: " + username);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
    }


    public void btnClickOnAction(ActionEvent actionEvent) {
        try {
            // Load the Password Reset Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PasswordReset.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("PasswordReset Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        String role = getUserRole(username, password);

        if (role == null) {
            // Authentication failed
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password!");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader;
                if ("admin".equals(role)) {
                    // Load the Admin Dashboard
                    loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
                } else {
                    // Load the Employee Dashboard
                    loader = new FXMLLoader(getClass().getResource("/view/EmployeeDashboard.fxml"));
                }
                Parent root = loader.load();

                // Get the current stage (window) and set the new scene
                Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle(role.equals("admin") ? "Admin Dashboard" : "Employee Dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully logged out!");

        alert.showAndWait();

        // Close the window
        Stage stage = (Stage) txtUserName.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void btnClickHereOnAction(ActionEvent actionEvent) {
        try {
            // Load the Register Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Registration.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Register Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
