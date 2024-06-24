package org.example.Controller.Employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.db.DBConnection;
import org.example.dto.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeController {

        private static EmployeeController instance;
        private EmployeeController(){}

        public static EmployeeController getInstance(){
            if(instance==null){
                return instance=new EmployeeController();
            }
            return instance;
        }

        public ObservableList<Employee> getAllEmployee() {
            try {
                ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM employee");
                ObservableList<Employee> listTable = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    listTable.add(
                            new Employee(
                                    resultSet.getInt(1),
                                    resultSet.getString(2),
                                    resultSet.getString(3),
                                    //resultSet.getString(3),
                                    resultSet.getString(4),
                                    resultSet.getString(5),
                                    resultSet.getString(6),
                                    resultSet.getDouble(7),
                                    resultSet.getString(8)
                            )
                    );
                }
                return listTable;

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public Employee searchEmployee(String EmployeeId){
            try {
                PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM employee WHERE EmployeeId=?");

                psTm.setString(1,EmployeeId);
                boolean execute = psTm.execute();
                if(execute){
                    ResultSet resultSet = psTm.getResultSet();
                    ;
                    while (resultSet.next()){
                        return new Employee(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                //resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getDouble(7),
                                resultSet.getString(8)
                        );
                    }
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

    public boolean submitEmployee(Employee employee) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?,?,?)");
            preparedStatement.setInt(1, employee.getEmployeeId());
            preparedStatement.setString(2, employee.getTitle());
            preparedStatement.setString(3, employee.getName());
            //preparedStatement.setString(3, employee.getLastname());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setString(5, employee.getPassword());
            preparedStatement.setString(6, employee.getUsername());
            preparedStatement.setDouble(7, employee.getSalary());
            preparedStatement.setString(8, employee.getPosition());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clearEmployee(String EmployeeId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee WHERE EmployeeId=?");
            preparedStatement.setString(1, EmployeeId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
