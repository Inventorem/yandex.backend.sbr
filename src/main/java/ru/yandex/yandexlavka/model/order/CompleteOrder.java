package ru.yandex.yandexlavka.model.order;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Accessors(chain=true)
public class CompleteOrder {

    @DateTimeFormat
    private String completed_time;

    @PositiveOrZero
    private Integer courier_id;

    @PositiveOrZero
    private Long order_id;
}
