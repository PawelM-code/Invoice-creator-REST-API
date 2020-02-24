package com.app.invoicecreator.domain.taxpayer;

import com.app.invoicecreator.domain.invoice.Invoice;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Taxpayer)) return false;

        Taxpayer taxpayer = (Taxpayer) o;

        if (!getName().equals(taxpayer.getName())) return false;
        if (!getNip().equals(taxpayer.getNip())) return false;
        if (!getRegon().equals(taxpayer.getRegon())) return false;
        return getWorkingAddress().equals(taxpayer.getWorkingAddress());
    }
}
