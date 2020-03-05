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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "vat")
    private int vat;

    @OneToMany(
            targetEntity = Item.class,
            mappedBy = "product",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Item> items = new ArrayList<>();

    public Product(Long id, String description, int vat) {
        this.id = id;
        this.description = description;
        this.vat = vat;
    }

    public Product(String description, int vat) {
        this.description = description;
        this.vat = vat;
    }

}
