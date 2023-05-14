package ru.yandex.yandexlavka.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.model.courier.*;
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
    GetCouriersResponse getCouriers(@Min(1) @RequestParam(required = false, defaultValue = "1") Integer limit,
                                    @Min(0) @RequestParam(required = false, defaultValue = "0") Integer offset) {
        return courierService.findAll(limit, offset);
    }

    @PostMapping
    CreateCouriersResponse postCouriers(@Valid @RequestBody CreateCourierRequest couriers) {
        return courierService.createCouriers(couriers);
    }


    @GetMapping(value = "/meta-info/{courier_id}", produces = APPLICATION_JSON_VALUE)
    GetCourierMetaInfoResponse getMetabyId(@Min(0) @PathVariable Integer courier_id,
                                           @RequestParam(defaultValue = "2023-01-20") String startDate,
                                           @RequestParam(defaultValue = "2023-01-21") String endDate) {


        return courierService.getMetabyId(courier_id, startDate, endDate);
    }

}
