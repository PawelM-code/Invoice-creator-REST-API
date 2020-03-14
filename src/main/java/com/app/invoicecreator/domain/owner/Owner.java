package com.app.invoicecreator.domain.owner;

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
@Table(name = "owner")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "nip")
    private Long nip;

    @Column(name = "regon")
    private Long regon;

    @Column(name = "workingAddress")
    private String workingAddress;

    @Column(name = "bankAccount")
    private String bankAccount;

    @Column(name = "emial")
    private String email;

    @OneToMany(
            targetEntity = Invoice.class,
            mappedBy = "owner",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    List<Invoice> invoices = new ArrayList<>();

    public Owner(Long id, String name, Long nip, Long regon, String workingAddress, String bankAccount, String email) {
        this.id = id;
        this.name = name;
        this.nip = nip;
        this.regon = regon;
        this.workingAddress = workingAddress;
        this.bankAccount = bankAccount;
        this.email = email;
    }

    public Owner(String name, Long nip, Long regon, String workingAddress, String bankAccount, String email) {
        this.name = name;
        this.nip = nip;
        this.regon = regon;
        this.workingAddress = workingAddress;
        this.bankAccount = bankAccount;
        this.email = email;
    }
}
