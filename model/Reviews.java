package com.ordermanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Reviews implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String date;
    private int score;

    @Column(length = 5000)
    private String review;

    @ManyToOne
    private Sups sup;
    @ManyToOne
    private Users owner;

    public Reviews(String review, String date, int score, Sups sup, Users owner) {
        this.review = review;
        this.date = date;
        this.score = score;
        this.sup = sup;
        this.owner = owner;
    }

    public String getDate() {
        String[] temp = date.split("-");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        Calendar calendar = new GregorianCalendar(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        return dateFormat.format(calendar.getTime());
    }
}