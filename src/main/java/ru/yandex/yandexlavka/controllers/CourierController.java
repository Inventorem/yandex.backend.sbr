package ru.yandex.yandexlavka.controllers;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.model.courier.*;
import ru.yandex.yandexlavka.service.courier.CourierService;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/couriers")
@Validated
public class CourierController {
    private final CourierService courierService;

    @GetMapping(value = "/{courier_id}", produces = APPLICATION_JSON_VALUE)
    @RateLimiter(name = "rateLimiter")
    CourierDto getCourierbyId(@Min(1) @PathVariable Integer courier_id) {

        return courierService.findById(courier_id);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @RateLimiter(name = "rateLimiter")
    GetCouriersResponse getCouriers(@Min(1) @RequestParam(required = false, defaultValue = "1") Integer limit,
                                    @Min(0) @RequestParam(required = false, defaultValue = "0") Integer offset) {
        return courierService.findAll(limit, offset);
    }

    @PostMapping
    @RateLimiter(name = "rateLimiter")
    CreateCouriersResponse postCouriers(@Valid @RequestBody CreateCourierRequest couriers) {
        return courierService.createCouriers(couriers);
    }


    @GetMapping(value = "/meta-info/{courier_id}", produces = APPLICATION_JSON_VALUE)
    @RateLimiter(name = "rateLimiter")
    GetCourierMetaInfoResponse getMetabyId(@Valid @Min(0) @PathVariable Integer courier_id,
                                           @RequestParam(defaultValue = "2023-01-20") LocalDate startDate,
                                           @RequestParam(defaultValue = "2023-01-21") LocalDate endDate) {


        return courierService.getMetabyId(courier_id, startDate, endDate);
    }

}
