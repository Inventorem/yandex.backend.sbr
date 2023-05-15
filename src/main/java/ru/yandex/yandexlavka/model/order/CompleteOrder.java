package ru.yandex.yandexlavka.model.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class CompleteOrder {

    @DateTimeFormat
    @NotNull
    private LocalDateTime complete_time;

    @Min(1)
    @NotNull
    private Integer courier_id;

    @Min(1)
    @NotNull
    private Long order_id;
}
