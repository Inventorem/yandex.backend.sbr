package ru.yandex.yandexlavka.model.courier;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateCouriersResponse {

    @NotEmpty
    private CourierDto[] couriers;
}
