package com.ilemgroup.inventory.product;

import java.util.List;

public interface ProductDao {

    Product findById(Long id);
    List<Product> findAll();
    void save(Product product);
    void update(Product product);
    void delete(Long id);
}
