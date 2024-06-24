package org.example.dto.Suppliertm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Table02 {
    private String SupplierId;
    private String Products;
    private Integer Qty;

}