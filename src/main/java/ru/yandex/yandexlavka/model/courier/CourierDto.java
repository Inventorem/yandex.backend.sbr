package ru.yandex.yandexlavka.model.courier;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CourierDto {
    @NotNull
    @PositiveOrZero
    private Integer courier_id;

    @NotNull
    private CourierType courier_type;

    @NotEmpty
    private Integer [] regions;

    @NotEmpty
    private String [] working_hours;
}
