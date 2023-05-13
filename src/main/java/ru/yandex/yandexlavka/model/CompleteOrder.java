package ru.yandex.yandexlavka.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class CompleteOrder {
    private String completed_time;
    private Integer courier_id;
    private Long order_id;
}
