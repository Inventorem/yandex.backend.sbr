package ru.yandex.yandexlavka.model.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompleteOrderRequestDto {
    @NotEmpty
    @Valid
    private CompleteOrder[] complete_info;
}
