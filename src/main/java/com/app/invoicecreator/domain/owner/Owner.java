package com.app.invoicecreator.domain.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
