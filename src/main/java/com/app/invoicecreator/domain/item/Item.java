package com.app.invoicecreator.domain.item;

import com.app.invoicecreator.domain.invoice.Invoice;
import com.app.invoicecreator.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;

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

    @Column(name = "netPrice")
    private BigDecimal netPrice;

    @Column(name = "grossPrice")
    private BigDecimal grossPrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "value")
    private BigDecimal value;

    public Item(Long id, Product product, Invoice invoice, BigDecimal netPrice, int quantity) {
        this.id = id;
        this.product = product;
        this.invoice = invoice;
        this.netPrice = netPrice;
        this.grossPrice = netPrice.multiply(BigDecimal.ONE.add(new BigDecimal(product.getVat()).divide(new BigDecimal(100),new MathContext(2))));
        this.quantity = quantity;
        this.value = new BigDecimal(quantity).multiply(grossPrice);
    }

    public Item(Product product, BigDecimal netPrice, int quantity) {
        this.product = product;
        this.netPrice = netPrice;
        this.grossPrice = netPrice.multiply(BigDecimal.ONE.add(new BigDecimal(product.getVat()).divide(new BigDecimal(100),new MathContext(2))));
        this.quantity = quantity;
        this.value = new BigDecimal(quantity).multiply(grossPrice);
    }
}
