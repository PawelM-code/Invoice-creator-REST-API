package com.app.invoicecreator.domain.product;

import com.app.invoicecreator.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(
            targetEntity = Item.class,
            mappedBy = "product",
            fetch = FetchType.LAZY
    )
    private List<Item> items = new ArrayList<>();

    public Product(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Product(String description) {
        this.description = description;
    }
}
