package ru.yandex.yandexlavka.model.courier;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetCouriersResponse {

    private CourierDto[] couriers;

    @NotNull
    @PositiveOrZero
    private Integer limit;

    @NotNull
    @PositiveOrZero
    private Integer offset;
}
