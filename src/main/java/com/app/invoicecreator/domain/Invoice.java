package com.app.invoicecreator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "issueDate")
    private Date issueDate;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @OneToMany(
            targetEntity = Item.class,
            mappedBy = "invoice",
            fetch = FetchType.LAZY
    )
    private List<Item> items = new ArrayList<>();

    @Column(name = "comments")
    private String comments;

    public Invoice(String number, Date issueDate, Buyer buyer, List<Item> items, String comments) {
        this.number = number;
        this.issueDate = issueDate;
        this.buyer = buyer;
        this.items = items;
        this.comments = comments;
    }
}
