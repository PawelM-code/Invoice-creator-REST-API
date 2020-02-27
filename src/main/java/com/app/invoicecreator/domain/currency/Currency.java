package com.app.invoicecreator.domain.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private String date;

    @Column(name = "midRate", scale = 4)
    private BigDecimal midRate;

    public Currency(String currency, String code, String date, BigDecimal midRate) {
        this.currency = currency;
        this.code = code;
        this.date = date;
        this.midRate = midRate;
    }
}
