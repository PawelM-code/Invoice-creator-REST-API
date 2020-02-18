package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.product.ProductDto;
import com.app.invoicecreator.exception.ProductNotFoundException;
import com.app.invoicecreator.mapper.ProductMapper;
import com.app.invoicecreator.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductService productService;

    @PostMapping(value = "/products")
    public void createProduct (@RequestBody ProductDto productDto) {
        productService.saveProduct(productMapper.mapToProduct(productDto));
    }

    @PutMapping(value = "/products")
    public ProductDto updateProduct (@RequestBody ProductDto productDto){
        return productMapper.mapToProductDto(productService.saveProduct(productMapper.mapToProduct(productDto)));
    }

    @DeleteMapping(value = "/products/{id}")
    public void deleteProduct (@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @GetMapping(value = "/products/{id}")
    public ProductDto getProduct(@PathVariable Long id) throws ProductNotFoundException{
        return productMapper.mapToProductDto(productService.getProduct(id).orElseThrow(ProductNotFoundException::new));
    }

    @GetMapping(value = "/products")
    public List<ProductDto> getProducts(){
        return productMapper.mapToProductDtoList(productService.getProducts());
    }
}
