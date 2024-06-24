package org.example.Controller.PasswordReset;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class PasswordResetController {
    public JFXTextField txtEnterEmail;

    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clothifystores";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "688432";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void sendEmail(String to, String subject, String content) {
        final String from = "clothifystore43@gmail.com"; // Replace with your email
        final String password = "Clothify@06"; // Replace with your email password

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP server
        properties.put("mail.smtp.port", "587"); // Port for TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS
        properties.put("mail.debug", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public void btnLogOutOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully logged out!");

        alert.showAndWait();

        // Close the window
        Stage stage = (Stage) txtEnterEmail.getScene().getWindow();
        stage.close();
    }

    public void btnSendOtpOnAction(ActionEvent actionEvent) {
        String email = txtEnterEmail.getText();
        boolean emailFound = false;

        try (Connection conn = connect()) {
            String sqlAdmin = "SELECT Email FROM Admin WHERE Email = ?";
            String sqlEmployee = "SELECT Email FROM Employee WHERE Email = ?";

            // Check admin table
            try (PreparedStatement pstmt = conn.prepareStatement(sqlAdmin)) {
                pstmt.setString(1, email);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        emailFound = true;
                    }
                }
            }

            // If not found in admin table, check employee table
            if (!emailFound) {
                try (PreparedStatement pstmt = conn.prepareStatement(sqlEmployee)) {
                    pstmt.setString(1, email);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            emailFound = true;
                        }
                    }
                }
            }

            if (emailFound) {
                String otp = generateOtp();
                sendEmail(email, "Your OTP Code", "Your OTP code is: " + otp);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("OTP Sent");
                alert.setHeaderText(null);
                alert.setContentText("An OTP has been sent to your email address.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Email Not Found");
                alert.setHeaderText(null);
                alert.setContentText("The entered email address is not registered.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        try {
            // Load the Register Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
