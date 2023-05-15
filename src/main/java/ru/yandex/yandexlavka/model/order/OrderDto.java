package ru.yandex.yandexlavka.model.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderDto {
    @NotNull
    @Min(0)
    private Integer cost;

    @NotEmpty
    private String[] delivery_hours;

    @Min(1)
    @NotNull
    private Long order_id;

    @Min(1)
    @NotNull
    private Integer regions;

    @NotNull
    @PositiveOrZero
    private Float weight;

    private String completed_time;
}
