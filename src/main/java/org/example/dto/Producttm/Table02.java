package org.example.dto.Producttm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Table02  {

    private String ProductCode;
    private Double UnitPrice;
    private Integer QtyOnHand;

}