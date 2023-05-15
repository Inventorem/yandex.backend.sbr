package ru.yandex.yandexlavka.model.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderDto {
    @NotNull
    @Positive
    private Integer cost;

    @NotEmpty
    private String[] delivery_hours;

    @PositiveOrZero
    @NotNull
    private Long order_id;

    @PositiveOrZero
    @NotNull
    private Integer regions;

    @NotNull
    @Positive
    private Float weight;

    private String completed_time;
}
