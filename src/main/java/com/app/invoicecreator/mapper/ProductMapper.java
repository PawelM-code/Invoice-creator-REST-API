package com.app.invoicecreator.mapper;

import com.app.invoicecreator.domain.product.Product;
import com.app.invoicecreator.domain.product.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    public Product mapToProduct(ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getDescription(),
                productDto.getVat());
    }

    public ProductDto mapToProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getDescription(),
                product.getVat());
    }

    public List<Product> mapToProductList(List<ProductDto> productDtoList) {
        return productDtoList.stream()
                .map(p -> new Product(
                        p.getId(),
                        p.getDescription(),
                        p.getVat()))
                .collect(Collectors.toList());
    }

    public List<ProductDto> mapToProductDtoList(List<Product> productDtoList) {
        return productDtoList.stream()
                .map(p -> new ProductDto(
                        p.getId(),
                        p.getDescription(),
                        p.getVat()))
                .collect(Collectors.toList());
    }
}
