package org.example.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SearchOrder{

    private String orderId;
    private String ProductCode;
    private Date Date;
    private Integer orderQty;
    private Integer discount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}