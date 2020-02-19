package com.app.invoicecreator.domain.invoice;

import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.taxpayer.Taxpayer;
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
    private String issueDate;

    @ManyToOne
    @JoinColumn(name = "taxpayer_id")
    private Taxpayer taxpayer;

    @OneToMany(
            targetEntity = Item.class,
            mappedBy = "invoice",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Item> items = new ArrayList<>();

    @Column(name = "comments")
    private String comments;

    public Invoice(Long id, String number, String issueDate, Taxpayer taxpayer, String comments) {
        this.id = id;
        this.number = number;
        this.issueDate = issueDate;
        this.taxpayer = taxpayer;
        this.comments = comments;
    }

    public Invoice(String number, String issueDate, Taxpayer taxpayer, String comments) {
        this.number = number;
        this.issueDate = issueDate;
        this.taxpayer = taxpayer;
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;

        Invoice invoice = (Invoice) o;

        return getTaxpayer().equals(invoice.getTaxpayer());
    }

    @Override
    public int hashCode() {
        return getTaxpayer().hashCode();
    }
}
