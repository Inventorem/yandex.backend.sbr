package ru.yandex.yandexlavka.model.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class CompleteOrder {

    @DateTimeFormat
    @NotNull
    private LocalDateTime complete_time;

    @PositiveOrZero
    @NotNull
    private Integer courier_id;

    @PositiveOrZero
    @NotNull
    private Long order_id;
}
