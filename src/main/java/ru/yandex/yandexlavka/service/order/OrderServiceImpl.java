package ru.yandex.yandexlavka.service.order;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.yandexlavka.domain.ClientOrder;
import ru.yandex.yandexlavka.domain.Courier;
import ru.yandex.yandexlavka.domain.TimeInterval;
import ru.yandex.yandexlavka.model.*;
import ru.yandex.yandexlavka.repos.OrderRepo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepo orderRepo;
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
        for (int i = 0; i < orders.getOrders().length; i ++){
            ClientOrder clientOrder = buildOrder(orders.getOrders()[i]);
            dtolist.add(buildOrderDto(orderRepo.save(clientOrder)));
        }
        return dtolist;
    }

    @Override
    @Transactional
    public @NotNull List<OrderDto> createComplete(@NotNull CompleteOrderRequestDto completedOrders) {
        List<OrderDto> dtolist = new ArrayList<>();
        for (int i = 0; i < completedOrders.getComplete_info().length; i++){
            Long order_id = completedOrders.getComplete_info()[i].getOrder_id();
            ClientOrder clientOrder =  orderRepo.findById(order_id)
                    .orElseThrow(() -> new EntityNotFoundException("Order " + order_id + " is not found"));
            orderUpdateCompletion(clientOrder, completedOrders.getComplete_info()[i]);
            dtolist.add(buildOrderDto(orderRepo.save(clientOrder)));
        }
        return dtolist;
    }

    private void orderUpdateCompletion(@NotNull ClientOrder clientOrder, @NotNull CompleteOrder completeOrder) {
        Integer courier_id = completeOrder.getCourier_id();
        if (completeOrder.getCompleted_time() != null){
            Timestamp completed_time = Timestamp.valueOf(completeOrder.getCompleted_time());
            clientOrder.setCompleted_time(completed_time);
        }
        clientOrder.setCourier(new Courier().setId(courier_id));
    }


    @NotNull
    private ClientOrder buildOrder(@NotNull CreateOrderDto orderdto) {
        ClientOrder clientOrder = new ClientOrder()
                .setCost(orderdto.getCost())
                .setRegions(orderdto.getRegions())
                .setWeight(orderdto.getWeight());
        for (int i = 0; i < orderdto.getDelivery_hours().length;i++){
            TimeInterval interval = new TimeInterval(orderdto.getDelivery_hours()[i]);
            clientOrder.getHours().add(interval);
        }
        return clientOrder;
    }

    @NotNull
    private OrderDto buildOrderDto(@NotNull ClientOrder clientOrder) {
        OrderDto orderDto = new OrderDto()
                .setOrder_id(clientOrder.getId())
                .setCost(clientOrder.getCost())
                .setWeight(clientOrder.getWeight())
                .setRegions(clientOrder.getRegions());
        String [] delivery_hours = new String [clientOrder.getHours().size()];
        TimeInterval [] current = clientOrder.getHours().toArray(new TimeInterval[0]);
        for (int i = 0; i < clientOrder.getHours().size(); i ++){
            delivery_hours[i]=(String.valueOf(current[i].toString()));
        }
        orderDto.setDelivery_hours(delivery_hours);
        return orderDto;
    }

}
