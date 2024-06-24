package org.example.Controller.SearchOrder;

import javafx.collections.ObservableList;
import org.example.dto.SearchOrder;

public interface SearchOrderService {

    SearchOrder searchSearchOrder(String OrderId);
    ObservableList<SearchOrder> getAllSearchOrder();

}
