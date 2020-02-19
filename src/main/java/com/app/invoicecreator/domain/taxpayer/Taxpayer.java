package com.app.invoicecreator.domain.taxpayer;

import com.app.invoicecreator.domain.invoice.Invoice;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "taxpayers")
public class Taxpayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "nip")
    private Long nip;

    @Column(name = "regon")
    private Long regon;

    @Column(name = "address")
    private String workingAddress;

    @OneToMany(
            targetEntity = Invoice.class,
            mappedBy = "taxpayer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    List<Invoice> invoices = new ArrayList<>();

    public Taxpayer(Long id, String name, Long nip, Long regon, String workingAddress) {
        this.id = id;
        this.name = name;
        this.nip = nip;
        this.regon = regon;
        this.workingAddress = workingAddress;
    }

    public Taxpayer(String name, Long nip, Long regon, String workingAddress) {
        this.name = name;
        this.nip = nip;
        this.regon = regon;
        this.workingAddress = workingAddress;
    }
}
