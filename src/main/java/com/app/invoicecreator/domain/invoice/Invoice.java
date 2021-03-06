package com.app.invoicecreator.domain.invoice;

import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.owner.Owner;
import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Builder
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", unique = true)
    private String number;

    @Column(name = "issueDate")
    private String issueDate;

    @ManyToOne
    @JoinColumn(name = "Owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "taxpayer_id")
    private Taxpayer taxpayer;

    @OneToMany(
            targetEntity = Item.class,
            mappedBy = "invoice",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @Builder.Default
    private List<Item> items = new ArrayList<>();

    @Column(name = "comments")
    private String comments;

    @Column(name = "netTotal")
    private BigDecimal netTotal;

    @Column(name = "vatTotal")
    private BigDecimal vatTotal;

    @Column(name = "grossTotal")
    private BigDecimal grossTotal;

    @Column(name = "currencyGrossTotal")
    private BigDecimal currencyGrossTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private InvoiceCurrency invoiceCurrency;

    @Column(name = "dateOfPayment")
    private String dateOfPayment;
}
