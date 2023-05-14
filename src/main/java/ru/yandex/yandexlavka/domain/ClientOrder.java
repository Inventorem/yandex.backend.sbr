package ru.yandex.yandexlavka.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Accessors(chain = true)
@Table(name="clientorder")
public class ClientOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="weight")
    private float weight;

    @Column(name="regions")
    private Integer regions;

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<TimeInterval> hours = new HashSet<>();


    @Column(name="cost")
    private Integer cost;

    @Column(name="completed_time")
    private Timestamp completed_time;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Courier courier;


}

