package com.ordermanagement.model;

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
public class Categories implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;

    @ManyToOne
    private Sups sup;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Products> products= new ArrayList<>();

    public Categories(String name, Sups sup) {
        this.name = name;
        this.sup = sup;
    }

    public float getTotalPrice() {
        return products.stream().reduce(0f, (i, product) -> i + product.getTotalPrice(), Float::sum);
    }
}