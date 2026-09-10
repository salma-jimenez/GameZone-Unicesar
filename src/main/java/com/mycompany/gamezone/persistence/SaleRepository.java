package com.mycompany.gamezone.persistence;

import com.mycompany.gamezone.model.Sale;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles persistence operations for Sale entities.
 */
public class SaleRepository {
    private final List<Sale> sales = new ArrayList<>();

    public void save(Sale sale) {
        sales.add(sale);
    }

    public List<Sale> findAll() {
        return new ArrayList<>(sales);
    }

    public Sale findById(String id) {
        return sales.stream()
                .filter(s -> s.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
}