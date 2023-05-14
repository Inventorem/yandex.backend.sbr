package ru.yandex.yandexlavka.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.model.order.CompleteOrderRequestDto;
import ru.yandex.yandexlavka.model.order.CreateOrderRequest;
import ru.yandex.yandexlavka.model.order.OrderDto;
import ru.yandex.yandexlavka.service.order.OrderService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping(value = "/{order_id}", produces = APPLICATION_JSON_VALUE)
    OrderDto getOrderbyId(@Min(0) @PathVariable Long order_id){
        return orderService.findById(order_id);
    }

    @GetMapping(produces =  APPLICATION_JSON_VALUE)
    List<OrderDto> getOrders(@Min(1) @RequestParam(required = false, defaultValue =  "1") Integer limit,
                             @Min(0) @RequestParam(required = false, defaultValue =  "0") Integer offset){
        return orderService.findAll(limit, offset);
    }
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    List<OrderDto> postOrders(@Valid @RequestBody CreateOrderRequest orders){
        return orderService.createOrders(orders);
    }

    @PostMapping(value = "/complete",produces = APPLICATION_JSON_VALUE)
    List<OrderDto> postComplete(@Valid @RequestBody CompleteOrderRequestDto completed_orders){
        return orderService.createComplete(completed_orders);
    }
}
