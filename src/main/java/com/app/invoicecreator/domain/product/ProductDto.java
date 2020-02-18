package com.app.invoicecreator.domain.product;

import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.item.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String description;
}
