package ru.yandex.yandexlavka.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Entity
@Accessors(chain = true)
@Data
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @Column(name = "index")
    private Integer index;
}
