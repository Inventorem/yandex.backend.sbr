package ru.yandex.yandexlavka.model.courier;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateCourierRequest {

    @NotEmpty
    @Valid
    private CreateCourierDto[] couriers;
}
