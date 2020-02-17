package com.app.invoicecreator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "issueDate")
    private Date issueDate;

    @Column(name = "dealerData")
    private CompanyData dealerData;

    @Column(name = "buyerData")
    private CompanyData buyerData;

    @OneToMany(
            targetEntity = Item.class,
            mappedBy = "invoice",
            fetch = FetchType.LAZY
    )
    private List<Item> items = new ArrayList<>();

    @Column(name = "comments")
    private String comments;
}
