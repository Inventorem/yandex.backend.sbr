package ru.yandex.yandexlavka.service.order;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.yandexlavka.domain.Order;
import ru.yandex.yandexlavka.domain.Courier;
import ru.yandex.yandexlavka.domain.TimeInterval;
import ru.yandex.yandexlavka.model.order.*;
import ru.yandex.yandexlavka.repos.CourierRepo;
import ru.yandex.yandexlavka.repos.OrderRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final CourierRepo courierRepo;

    @Override
    @Transactional(readOnly = true)
    public @NotNull OrderDto findById(@NotNull Long orderId) {
        return orderRepo.findById(orderId)
                .map(this::buildOrderDto)
                .orElseThrow(() -> new EntityNotFoundException("Order " + orderId + " is not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public @NotNull List<OrderDto> findAll(@NotNull Integer limit, @NotNull Integer offset) {
        return orderRepo.findAll(
                        PageRequest.of(offset, limit)).stream()
                .map(this::buildOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public @NotNull List<OrderDto> createOrders(@NotNull CreateOrderRequest orders) {
        List<OrderDto> dtolist = new ArrayList<>();
        for (int i = 0; i < orders.getOrders().length; i++) {
            Order order = buildOrder(orders.getOrders()[i]);
            dtolist.add(buildOrderDto(orderRepo.save(order)));
        }
        return dtolist;
    }

    @Override
    @Transactional
    public @NotNull List<OrderDto> createComplete(@NotNull CompleteOrderRequestDto completedOrders) {
        List<OrderDto> dtolist = new ArrayList<>();
        for (int i = 0; i < completedOrders.getComplete_info().length; i++) {
            Order order = CompleteOrder(completedOrders, i);
            dtolist.add(buildOrderDto(orderRepo.save(order)));
        }
        return dtolist;
    }

    @NotNull
    private Order CompleteOrder(@NotNull CompleteOrderRequestDto completedOrders, int i) {
        Long order_id = completedOrders.getComplete_info()[i].getOrder_id();
        Order order = orderRepo.findById(completedOrders.getComplete_info()[i].getOrder_id())
                .orElseThrow(() -> new EntityNotFoundException("Order " + order_id + " is not found"));
        @NotNull CompleteOrder completeOrder = completedOrders.getComplete_info()[i];
        if (order.getCompleted_time() == null) {
            order.setCompleted_time(completeOrder.getComplete_time());
        } else {
            throw new EntityExistsException("Order " + order.getId() + " is already completed");
        }
        Courier courier = courierRepo.findById(Long.valueOf(completeOrder.getCourier_id()))
                .orElseThrow(() -> new EntityNotFoundException("Courier " + completeOrder.getCourier_id() + " is not found"));
        courier.addOrder(order);
        courierRepo.save(courier);
        return order;
    }


    @NotNull
    private Order buildOrder(@NotNull CreateOrderDto orderdto) {
        Order order = new Order();
        order.setCost(orderdto.getCost());
        order.setRegions(orderdto.getRegions());
        order.setWeight(orderdto.getWeight());
        for (int i = 0; i < orderdto.getDelivery_hours().length; i++) {
            TimeInterval interval = new TimeInterval(orderdto.getDelivery_hours()[i]);
            order.getHours().add(interval);
        }
        return order;
    }

    @NotNull
    private OrderDto buildOrderDto(@NotNull Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrder_id(order.getId());
        orderDto.setCost(order.getCost());
        orderDto.setWeight(order.getWeight());
        orderDto.setRegions(order.getRegions());
        String[] delivery_hours = new String[order.getHours().size()];
        TimeInterval[] current = order.getHours().toArray(new TimeInterval[0]);
        for (int i = 0; i < order.getHours().size(); i++) {
            delivery_hours[i] = (String.valueOf(current[i].toString()));
        }
        orderDto.setDelivery_hours(delivery_hours);
        if (order.getCompleted_time() != null) {
            orderDto.setCompleted_time(order.getCompleted_time().toString());
        }
        return orderDto;
    }

}
