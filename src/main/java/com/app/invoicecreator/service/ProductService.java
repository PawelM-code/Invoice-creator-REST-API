package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.product.Product;
import com.app.invoicecreator.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public Product saveProduct(final Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (ConstraintViolationException e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
