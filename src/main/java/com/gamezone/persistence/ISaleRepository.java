package com.gamezone.persistence;

import com.gamezone.model.Sale;
import java.util.List;

/**
 * Interface defining persistence operations for Sale entities,
 * adhering to the Dependency Inversion Principle (DIP).
 * 
 * @author Salma Jiménez Vega
 */
public interface ISaleRepository {
    void save(Sale sale);
    List<Sale> findAll();
    Sale findById(String id);
}
