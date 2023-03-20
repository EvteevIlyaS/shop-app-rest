package com.ilyaevteev.shopapp.repository;


import com.ilyaevteev.shopapp.model.Product;

import java.util.List;

public interface ProductRepository {
    Product findByName(String name);

    Product findById(Long id);

    void saveProduct(Product product);

    void deleteProduct(String name);

    List<Product> getAll();

}
