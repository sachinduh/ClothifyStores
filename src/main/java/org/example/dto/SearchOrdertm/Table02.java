package org.example.dto.SearchOrdertm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Table02 {

    private String orderId;
    private String ProductCode;
    private Integer OrderQty;
    private Integer discount;
}