package com.ilyaevteev.shopapp.service;


import com.ilyaevteev.shopapp.model.Product;

import java.util.List;

public interface ProductService {
    Product findByName(String name);

    Product findById(Long id);

    void saveProduct(Product product);

    void deleteProduct(String name);

    List<Product> getAllNotFrozenDeleted();

}
