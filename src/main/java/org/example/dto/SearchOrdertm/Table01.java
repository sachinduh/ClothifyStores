package org.example.dto.SearchOrdertm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Table01{

    private String orderId;
    private String ProductCode;
    private Date Date;

}