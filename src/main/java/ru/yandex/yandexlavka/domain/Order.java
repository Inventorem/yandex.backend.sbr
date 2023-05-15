package ru.yandex.yandexlavka.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Accessors(chain = true)
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "weight")
    private float weight;

    @Column(name = "regions")
    private Integer regions;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "clientorder_id")
    private Set<TimeInterval> hours = new HashSet<>();


    @Column(name = "cost")
    private Integer cost;

    @Column(name = "completed_time")
    private LocalDateTime completed_time;

    @ManyToOne(fetch = FetchType.LAZY)
    private Courier courier;


}

