package com.app.invoicecreator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "companies")
public class CompanyData {
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
}
