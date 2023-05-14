package ru.yandex.yandexlavka.model.order;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class CreateOrderRequest {
    @NotEmpty
    private CreateOrderDto[] orders;
}
