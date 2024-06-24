package org.example.Controller.Employee;

import javafx.collections.ObservableList;
import org.example.dto.Employee;
public interface EmployeeService{

    Employee searchEmployee(String EmployeeId);
    ObservableList<Employee> getAllEmployee();
    boolean submitEmployee(Employee employee);

    boolean clearEmployee(String EmployeeId);
}