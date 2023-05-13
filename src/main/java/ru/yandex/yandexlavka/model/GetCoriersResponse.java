package ru.yandex.yandexlavka.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetCoriersResponse {
    private CourierDto [] couriers;
    private Integer limit;
    private Integer offset;
}
