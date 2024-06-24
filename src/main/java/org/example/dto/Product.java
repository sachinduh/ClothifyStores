package org.example.dto;

import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    // Setter for productCode, if needed
    // Getter for productCode
    @Setter
    @Getter
    private String productCode;
    private String Description;
    private String Category;
    private String Size;
    private Double UnitPrice;
    private Integer QtyOnHand;


    public Product(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    //    public Product(String productCode, String description, String category, String size, double unitPrice, int qtyOnHand) {
//        this.productCode = productCode;
//        this.description = description;
//        this.category = category;
//        this.size = size;
//        this.unitPrice = unitPrice;
//        this.qtyOnHand = qtyOnHand;
//    }
//
//    public String getProductCode() {
//        return productCode;
//    }
//
//    public void setProductCode(String productCode) {
//        this.productCode = productCode;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getSize() {
//        return size;
//    }
//
//    public void setSize(String size) {
//        this.size = size;
//    }
//
//    public double getUnitPrice() {
//        return unitPrice;
//    }
//
//    public void setUnitPrice(double unitPrice) {
//        this.unitPrice = unitPrice;
//    }
//
//    public int getQtyOnHand() {
//        return qtyOnHand;
//    }
//
//    public void setQtyOnHand(int qtyOnHand) {
//        this.qtyOnHand = qtyOnHand;
//    }

}