package com.gamezone.persistence;

import com.gamezone.model.Sale;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles persistence operations for Sale entities.
 * Implements ISaleRepository to support Dependency Inversion (DIP).
 * 
 * @author Salma Jiménez Vega
 */
public class SaleRepository implements ISaleRepository {
    private List<Sale> sales = new ArrayList<>();

    @Override
    public void save(Sale sale) {
        sales.add(sale);
    }

    @Override
    public List<Sale> findAll() {
        return new ArrayList<>(sales);
    }

    @Override
    public Sale findById(String id) {
        return sales.stream()
                .filter(s -> s.getIdSale().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
}