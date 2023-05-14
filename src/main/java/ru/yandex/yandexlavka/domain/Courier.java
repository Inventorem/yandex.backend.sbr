package ru.yandex.yandexlavka.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.yandex.yandexlavka.model.courier.CourierType;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Data
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
