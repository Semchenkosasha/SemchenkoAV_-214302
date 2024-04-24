package com.ordermanagement.model;

import com.ordermanagement.model.enums.ContractStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Contracts implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String dateStart;
    private String dateEnd;
    private int quantity;
    private float price;

    @Column(length = 5000)
    private String description;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @ManyToOne
    private Sups sup;
    @ManyToOne
    private Products product;
    @ManyToOne
    private Users owner;


    public Contracts(Users owner, Sups sup, Products product, String description, String dateStart, String dateEnd, ContractStatus status, int quantity) {
        this.owner = owner;
        this.sup = sup;
        this.product = product;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.status = status;
        this.quantity = quantity;
        this.price = (float) Math.round((product.getPrice() * quantity) * 100) / 100;
    }

    public String getDateStart() {
        String[] temp = dateStart.split("-");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar calendar = new GregorianCalendar(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[2]));
        return dateFormat.format(calendar.getTime());
    }

    public String getDateEnd() {
        String[] temp = dateEnd.split("-");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar calendar = new GregorianCalendar(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[2]));
        return dateFormat.format(calendar.getTime());
    }
}