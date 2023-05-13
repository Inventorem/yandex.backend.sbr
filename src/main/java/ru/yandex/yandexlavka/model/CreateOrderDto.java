package ru.yandex.yandexlavka.model;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class CreateOrderDto {
    private Integer cost;
    private String [] delivery_hours;
    private Integer regions;
    private float weight;
}
