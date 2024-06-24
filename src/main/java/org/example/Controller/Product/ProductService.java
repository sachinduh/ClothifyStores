package org.example.Controller.Product;

import javafx.collections.ObservableList;
import org.example.dto.OrderDetail;
import org.example.dto.Product;

import java.util.List;

public interface ProductService {

        Product searchProduct(String ProductCode);
        ObservableList<Product> getAllProduct();
        boolean addProduct(Product product);

        boolean updateProduct(Product product);

        boolean removeProduct(String ProductCode);

        public boolean updateStock(List<OrderDetail> orderDetailList);
}
