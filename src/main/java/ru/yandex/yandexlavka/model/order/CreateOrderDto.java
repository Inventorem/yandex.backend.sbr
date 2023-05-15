package ru.yandex.yandexlavka.model.order;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateOrderDto {
    @NotNull
    @Positive
    private Integer cost;

    @NotEmpty
    private String[] delivery_hours;

    @NotNull
    @PositiveOrZero
    private Integer regions;

    @NotNull
    @Positive
    private float weight;
}
