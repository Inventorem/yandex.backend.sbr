package ru.yandex.yandexlavka.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class OrderDto {
        private Integer cost;
        private String[] delivery_hours;
        private Long order_id;
        private Integer regions;
        private Float weight;
        private String completed_time;
}
