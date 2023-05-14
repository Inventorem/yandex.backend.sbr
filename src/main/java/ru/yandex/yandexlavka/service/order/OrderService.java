package ru.yandex.yandexlavka.service.order;

import org.jetbrains.annotations.NotNull;
import ru.yandex.yandexlavka.model.order.CompleteOrderRequestDto;
import ru.yandex.yandexlavka.model.order.CreateOrderRequest;
import ru.yandex.yandexlavka.model.order.OrderDto;

import java.util.List;

public interface OrderService {
    @NotNull
    OrderDto findById(Long orderId);

    @NotNull
    List<OrderDto> findAll(@NotNull Integer limit, @NotNull Integer offset);

    @NotNull
    List<OrderDto> createOrders(@NotNull CreateOrderRequest orders);

    @NotNull
    List<OrderDto> createComplete(@NotNull CompleteOrderRequestDto completedOrders);
}
