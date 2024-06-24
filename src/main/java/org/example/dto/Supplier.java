package org.example.dto;

import lombok.*;

@Setter
@Getter
@Data
//@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Supplier {

    private String supplierId;
    private String name;
    private String company;
    private String email;
    private String products;
    private Integer qty;


    public Supplier(String supplierId, String name, String company, String email, String products, Integer qty) {
        this.supplierId = supplierId;
        this.name = name;
        this.company = company;
        this.email = email;
        this.products = products;
        this.qty = qty;
    }

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getProducts() { return products; }
    public void setProducts(String products) { this.products = products; }

    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }

}