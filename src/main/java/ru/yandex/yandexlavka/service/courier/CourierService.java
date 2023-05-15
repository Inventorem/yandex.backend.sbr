package ru.yandex.yandexlavka.service.courier;

import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import ru.yandex.yandexlavka.model.courier.*;

import java.time.LocalDate;

public interface CourierService {
    @NotNull
    CourierDto findById(@NotNull Integer courierId);

    @NotNull
    GetCouriersResponse findAll(@NotNull Integer limit, @NotNull Integer offset);

    @NotNull
    CreateCouriersResponse createCouriers(@Valid @NotNull CreateCourierRequest couriers);

    @NotNull
    GetCourierMetaInfoResponse getMetabyId(@NotNull Integer courierId, @NotNull LocalDate startDate, @NotNull LocalDate endDate);
}
