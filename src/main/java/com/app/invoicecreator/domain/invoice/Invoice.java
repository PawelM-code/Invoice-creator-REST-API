package com.app.invoicecreator.domain.invoice;

import com.app.invoicecreator.domain.item.Item;
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

    @Column(name = "baseTotal")
    private BigDecimal baseTotal;

    @Column(name = "plnTotal")
    private BigDecimal plnTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private InvoiceCurrency invoiceCurrency;

//    public Invoice(Long id, String number, String issueDate, Taxpayer taxpayer, String comments, InvoiceCurrency invoiceCurrency) {
//        this.id = id;
//        this.number = number;
//        this.issueDate = issueDate;
//        this.taxpayer = taxpayer;
//        this.comments = comments;
//        this.invoiceCurrency = invoiceCurrency;
//    }
}
