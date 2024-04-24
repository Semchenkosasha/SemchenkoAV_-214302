package com.ordermanagement.model;

import com.ordermanagement.model.enums.ContractStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Products implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private String warehouse;
    private String photo;
    private float price;
    private int year;
    private int count;
    private int term;

    @Column(length = 5000)
    private String description;

    @ManyToOne
    private Sups sup;
    @ManyToOne
    private Categories category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Contracts> contracts = new ArrayList<>();

    public Products(String name, String warehouse, String description, String photo, float price, Sups sup, Categories category, int year, int term, int count) {
        this.name = name;
        this.warehouse = warehouse;
        this.description = description;
        this.photo = photo;
        this.price = price;
        this.count = count;
        this.sup = sup;
        this.category = category;
        this.year = year;
        this.term = term;
    }

    public int getTotalQuantity() {
        return contracts.stream().reduce(0, (i, contract) -> {
            if (contract.getStatus() == ContractStatus.DELIVERED) {
                return i + contract.getQuantity();
            }
            return i;
        }, Integer::sum);
    }

    public float getTotalPrice() {
        return contracts.stream().reduce(0f, (i, contract) -> {
            if (contract.getStatus() == ContractStatus.DELIVERED) {
                return i + contract.getPrice();
            }
            return i;
        }, Float::sum);
    }
}