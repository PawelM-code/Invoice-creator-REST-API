package com.app.invoicecreator.domain.item;

import com.app.invoicecreator.domain.invoice.Invoice;
import com.app.invoicecreator.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "value")
    private BigDecimal value;

    public Item(Long id, Product product, Invoice invoice, BigDecimal price, int quantity) {
        this.id = id;
        this.product = product;
        this.invoice = invoice;
        this.price = price;
        this.quantity = quantity;
        this.value = new BigDecimal(quantity).multiply(price);
    }
}
