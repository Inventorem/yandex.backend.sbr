package ru.yandex.yandexlavka.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class CreateOrderRequest {
    private CreateOrderDto [] orders;
}
