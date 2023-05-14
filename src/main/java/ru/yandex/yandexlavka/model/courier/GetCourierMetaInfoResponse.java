package ru.yandex.yandexlavka.model.courier;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetCourierMetaInfoResponse {
    private Integer courier_id;
    private CourierType courier_type;
    private Integer[] regions;
    private String [] working_hours;
    private Integer rating;
    private Integer earnings;
}
