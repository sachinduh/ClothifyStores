package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

        private Integer EmployeeId;
        private String title;
        private String Name;
        //private String Lastname;
        private String Email;
        private String Password;
        private String Username;
        private Double salary;
        private String Position;

    public Employee(int employeeId, String string, String text, String text1, String text2, String text3, String text4, String string1) {
    }

}
