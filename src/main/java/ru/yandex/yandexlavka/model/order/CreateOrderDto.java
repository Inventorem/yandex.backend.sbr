package ru.yandex.yandexlavka.model.order;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateOrderDto {
    @NotNull
    @Min(0)
    private Integer cost;

    @NotEmpty
    private String[] delivery_hours;

    @NotNull
    @Min(1)
    private Integer regions;

    @NotNull
    @PositiveOrZero
    private float weight;
}
