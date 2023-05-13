package ru.yandex.yandexlavka.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.yandex.yandexlavka.model.CourierType;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Accessors(chain=true)
@Table(name="courier")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="type")
    private CourierType type;

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Region> regions = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<TimeInterval> hours = new HashSet<>();

    @OneToMany(mappedBy = "courier")
    private Set<ClientOrder> clientOrder;



}
