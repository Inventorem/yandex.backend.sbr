package ru.yandex.yandexlavka.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Accessors(chain = true)
@Table(name="hours")
public class TimeInterval {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public TimeInterval(String hours_interval) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String [] hours = hours_interval.split("-");
        this.start_time = LocalTime.parse(hours[0],formatter);
        this.end_time = LocalTime.parse(hours[1],formatter);
    }


    @Override
    public String toString() {
        return start_time + "-" + end_time;
    }

    @Column(name="start_time")
    private LocalTime start_time;

    @Column(name="end_time")
    private LocalTime end_time;

}
