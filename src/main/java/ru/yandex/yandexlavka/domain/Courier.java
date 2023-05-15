package ru.yandex.yandexlavka.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.yandex.yandexlavka.model.courier.CourierType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "courier")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private CourierType type;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "courier_id")
    private Set<Region> regions = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "courier_id")
    private Set<TimeInterval> hours = new HashSet<>();

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order clientorder) {
        this.orders.add(clientorder);
        clientorder.setCourier(this);
    }


}
