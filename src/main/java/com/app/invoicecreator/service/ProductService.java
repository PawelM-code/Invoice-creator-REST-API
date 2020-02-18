package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.product.Product;
import com.app.invoicecreator.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product saveProduct(final Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
