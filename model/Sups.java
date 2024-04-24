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
public class Sups implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name = "";
    private String company = "";
    private String contact = "";
    private String photo;
    private String country = "";
    private String email = "";

    @OneToOne
    private Users user;
    @OneToMany(mappedBy = "sup", cascade = CascadeType.ALL)
    private List<Categories> categories = new ArrayList<>();
    @OneToMany(mappedBy = "sup", cascade = CascadeType.ALL)
    private List<Products> products = new ArrayList<>();
    @OneToMany(mappedBy = "sup", cascade = CascadeType.ALL)
    private List<Reviews> reviews = new ArrayList<>();
    @OneToMany(mappedBy = "sup", cascade = CascadeType.ALL)
    private List<Contracts> contracts = new ArrayList<>();

    public Sups(Users user) {
        this.photo = "def_sup.png";
        this.user = user;
    }

    public int getScore() {
        if (reviews.isEmpty()) return 0;
        return reviews.stream().reduce(0, (i, review) -> i + review.getScore(), Integer::sum) / reviews.size();
    }
}