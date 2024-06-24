package org.example.dto;

public class CartTbl {
        private String productCode;
        private String desc;
        private Integer qty;
        private Double unitPrice;
        private Double total;
        private Double discount;

        // Constructor
        public CartTbl(String productCode, String desc, Integer qty, Double unitPrice, Double total, Double discount) {
                this.productCode = productCode;
                this.desc = desc;
                this.qty = qty;
                this.unitPrice = unitPrice;
                this.total = total;
                this.discount = discount;
        }

        // Getter and Setter methods
        public String getProductCode() {
                return productCode;
        }

        public void setProductCode(String productCode) {
                this.productCode = productCode;
        }

        public String getDesc() {
                return desc;
        }

        public void setDesc(String desc) {
                this.desc = desc;
        }

        public Integer getQty() {
                return qty;
        }

        public void setQty(Integer qty) {
                this.qty = qty;
        }

        public Double getUnitPrice() {
                return unitPrice;
        }

        public void setUnitPrice(Double unitPrice) {
                this.unitPrice = unitPrice;
        }

        public Double getTotal() {
                return total;
        }

        public void setTotal(Double total) {
                this.total = total;
        }

        public Double getDiscount() {
                return discount;
        }

        public void setDiscount(Double discount) {
                this.discount = discount;
        }
}
