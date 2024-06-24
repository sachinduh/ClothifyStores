package org.example.Controller.Supplier;


import javafx.collections.ObservableList;
import org.example.dto.Supplier;

public interface SupplierService {

    Supplier searchSupplier(String SupplierId);
    ObservableList<Supplier> getAllSupplier();
    boolean addSupplier(Supplier supplier);

    boolean updateSupplier(Supplier supplier);

    boolean removeSupplier(String SupplierId);
}
