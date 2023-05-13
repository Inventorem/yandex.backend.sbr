package ru.yandex.yandexlavka.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class CompleteOrderRequestDto {
    private CompleteOrder [] complete_info;
}
