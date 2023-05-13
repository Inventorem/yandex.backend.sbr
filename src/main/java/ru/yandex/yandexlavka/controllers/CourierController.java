package ru.yandex.yandexlavka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.model.*;
import ru.yandex.yandexlavka.service.courier.CourierService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/couriers")
public class CourierController {
    @Autowired
    private CourierService courierService;

    @GetMapping(value = "/{courier_id}", produces = APPLICATION_JSON_VALUE)
    CourierDto getCourierbyId(@PathVariable Integer courier_id) {

        return courierService.findById(courier_id);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    GetCoriersResponse getCouriers(@RequestParam(required = false, defaultValue = "1") Integer limit,
                                   @RequestParam(required = false, defaultValue = "0") Integer offset) {
        return courierService.findAll(limit, offset);
    }

    @PostMapping
    CreateCouriersResponse postCouriers(@RequestBody CreateCourierRequest couriers) {
        return courierService.createCouriers(couriers);
    }


    @GetMapping(value = "/meta-info/{courier_id}", produces = APPLICATION_JSON_VALUE)
    GetCourierMetaInfoResponse getMetabyId(@PathVariable Integer courier_id,
                                           @RequestParam(required = true, defaultValue = "2023-01-20") String startDate,
                                           @RequestParam(required = true, defaultValue = "2023-01-21") String endDate) {


        return courierService.getMetabyId(courier_id, startDate, endDate);
    }

}
