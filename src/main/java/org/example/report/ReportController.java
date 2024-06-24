package org.example.report;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.dto.Employee;
import org.example.dto.Product;
import org.example.dto.SearchOrder;
import org.example.dto.Supplier;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportController {
    public Label lblReports;
    public DatePicker dateDatePicker;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clothifystores";
    private static final String USER = "root";
    private static final String PASS = "688432";

    public void btnInventoryOnAction(ActionEvent actionEvent) {
        System.out.println("Generating Inventory Report...");
        generateReport("/reports/InventoryR.jrxml", getProductDataSource());
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) {
        System.out.println("Generating Supplier Report...");
        generateReport("/reports/Supplier.jrxml", getSupplierDataSource());
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) {
        System.out.println("Generating Employee Report...");
        generateReport("/reports/Employee.jrxml", getEmployeeDataSource());
    }

    public void btnSalesOnAction(ActionEvent actionEvent) {
        System.out.println("Generating Sales Report...");
        generateReport("/reports/SalesR.jrxml", getSalesDataSource());
    }

    private void generateReport(String reportPath, JRBeanCollectionDataSource dataSource) {
        try {
            System.out.println("Compiling report...");
            // Using class loader to get the resource
            InputStream reportStream = getClass().getResourceAsStream(reportPath);
            if (reportStream == null) {
                throw new FileNotFoundException("Report file not found: " + reportPath);
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            System.out.println("Report compiled successfully.");

            Map<String, Object> parameters = new HashMap<>();
            System.out.println("Filling report...");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            System.out.println("Report filled successfully.");

            System.out.println("Exporting report to PDF...");
            JasperExportManager.exportReportToPdfFile(jasperPrint, "reportpdf/report.pdf");
            System.out.println("Report exported successfully!");
        } catch (FileNotFoundException e) {
            System.err.println("Report file not found: " + reportPath);
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }


    private JRBeanCollectionDataSource getProductDataSource() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ProductCode, Description, Category, Size, unitPrice, qtyOnHand FROM product")) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getString("ProductCode"),
                        rs.getString("Description"),
                        rs.getString("Category"),
                        rs.getString("Size"),
                        rs.getDouble("unitPrice"),
                        rs.getInt("qtyOnHand")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JRBeanCollectionDataSource(products);
    }

    private JRBeanCollectionDataSource getSupplierDataSource() {
        List<Supplier> suppliers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SupplierId, Name, Company, Email, Products, Qty FROM supplier")) {

            while (rs.next()) {
                suppliers.add(new Supplier(
                        rs.getString("SupplierId"),
                        rs.getString("Name"),
                        rs.getString("Company"),
                        rs.getString("Email"),
                        rs.getString("Products"),
                        rs.getInt("Qty")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JRBeanCollectionDataSource(suppliers);
    }

    private JRBeanCollectionDataSource getEmployeeDataSource() {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT EmployeeId, Title, Name, Email, Password, Username, Salary, Position FROM employee")) {

            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("EmployeeId"),
                        rs.getString("Title"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("Username"),
                        rs.getDouble("Salary"),
                        rs.getString("Position")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JRBeanCollectionDataSource(employees);
    }

    private JRBeanCollectionDataSource getSalesDataSource() {
        List<SearchOrder> searchOrders = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT orderId, ProductCode, Date, OrderQty, Discount FROM searchorder")) {
            while (rs.next()) {
                searchOrders
                        .add(new SearchOrder(
                        rs.getString("orderId"),
                        rs.getString("ProductCode"),
                        rs.getDate("Date"),
                        rs.getInt("OrderQty"),
                        rs.getInt("Discount")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JRBeanCollectionDataSource(searchOrders);
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully logged out!");

        alert.showAndWait();

        // Close the window
        Stage stage = (Stage) lblReports.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
