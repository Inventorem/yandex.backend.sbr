package ru.yandex.yandexlavka.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateCourierDto {

    private CourierType courier_type;
    private Integer [] regions;
    private String [] working_hours;
}
