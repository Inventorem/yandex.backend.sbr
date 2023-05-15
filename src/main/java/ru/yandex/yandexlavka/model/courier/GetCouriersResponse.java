package ru.yandex.yandexlavka.model.courier;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetCouriersResponse {

    private CourierDto[] couriers;

    @NotNull
    @Positive
    private Integer limit;

    @NotNull
    @PositiveOrZero
    private Integer offset;
}
