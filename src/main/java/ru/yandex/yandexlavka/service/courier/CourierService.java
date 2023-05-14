package ru.yandex.yandexlavka.service.courier;

import org.jetbrains.annotations.NotNull;
import ru.yandex.yandexlavka.model.courier.*;

public interface CourierService {
    @NotNull
    CourierDto findById(@NotNull Integer courierId);

    @NotNull
    GetCouriersResponse findAll(@NotNull Integer limit, @NotNull Integer offset);

    @NotNull
    CreateCouriersResponse createCouriers(@NotNull CreateCourierRequest couriers);

    @NotNull
    GetCourierMetaInfoResponse getMetabyId(@NotNull Integer courierId, @NotNull String startDate, @NotNull String endDate);
}
