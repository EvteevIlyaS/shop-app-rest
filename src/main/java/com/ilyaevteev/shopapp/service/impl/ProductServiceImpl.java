package com.ilyaevteev.shopapp.service.impl;

import com.ilyaevteev.shopapp.model.Product;
import com.ilyaevteev.shopapp.repository.ProductRepository;
import com.ilyaevteev.shopapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    @Transactional
    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public void saveProduct(Product product) {
        productRepository.saveProduct(product);
    }

    @Override
    @Transactional
    public void deleteProduct(String name) {
        productRepository.deleteProduct(name);
    }

    @Override
    public List<Product> getAllNotFrozenDeleted() {
        return productRepository.getAll().stream().filter(el -> el.getOrganization().getUser() != null).collect(Collectors.toList());
    }
}
