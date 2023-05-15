package ru.yandex.yandexlavka.service.courier;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.yandexlavka.domain.Courier;
import ru.yandex.yandexlavka.domain.Order;
import ru.yandex.yandexlavka.domain.Region;
import ru.yandex.yandexlavka.domain.TimeInterval;
import ru.yandex.yandexlavka.model.courier.*;
import ru.yandex.yandexlavka.repos.CourierRepo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final CourierRepo courierRepo;

    private static int RatingRatiobyType(Courier courier) {
        return (3 - courier.getType().ordinal()) % 3;
    }

    private static int EarningsRatiobyType(Courier courier) {
        return courier.getType().ordinal() + 2;
    }

    @Override
    @Transactional(readOnly = true)
    public @NotNull CourierDto findById(@NotNull Integer courierId) {
        return courierRepo.findById(Long.valueOf(courierId))
                .map(this::buildCourierDto)
                .orElseThrow(() -> new EntityNotFoundException("Courier " + courierId + " is not found"));
    }

    private CourierDto buildCourierDto(Courier courier) {
        CourierDto courierdto = new CourierDto();
        courierdto.setCourier_id(courier.getId());
        courierdto.setCourier_type(courier.getType());
        if (courier.getRegions() == null) {
            throw new EntityNotFoundException("Courier #" + courier.getId() + ", regions is null");
        }
        courierdto.setRegions(ExtractRegionsFromCourier(courier));
        courierdto.setWorking_hours(ExtractHoursFromCourier(courier));

        return courierdto;
    }

    private String[] ExtractHoursFromCourier(Courier courier) {
        if (courier.getHours() == null) {
            throw new EntityNotFoundException("Courier #" + courier.getId() + ", working_hours is null");
        }
        TimeInterval[] courier_intervals = courier.getHours().toArray(new TimeInterval[0]);
        String[] extracted_hours = new String[courier_intervals.length];
        for (int i = 0; i < courier.getHours().size(); i++) {
            extracted_hours[i] = courier_intervals[i].toString();
        }
        return extracted_hours;
    }

    private Integer[] ExtractRegionsFromCourier(Courier courier) {
        Region[] regions = courier.getRegions().toArray(new Region[0]);
        Integer[] extracted_regions = new Integer[regions.length];
        for (int i = 0; i < courier.getRegions().size(); i++) {
            extracted_regions[i] = regions[i].getIndex();
        }
        return extracted_regions;
    }

    @Override
    @Transactional(readOnly = true)
    public @NotNull GetCouriersResponse findAll(@NotNull Integer limit, @NotNull Integer offset) {
        return new GetCouriersResponse().setCouriers(courierRepo.findAll(
                        PageRequest.of(offset, limit)).stream()
                .map(this::buildCourierDto)
                .toList().toArray(new CourierDto[0])).setLimit(limit).setOffset(offset);
    }

    @Override
    @Transactional
    public @NotNull CreateCouriersResponse createCouriers(@Valid @NotNull CreateCourierRequest couriers) {
        CourierDto[] dtoarray = new CourierDto[couriers.getCouriers().length];
        for (int i = 0; i < couriers.getCouriers().length; i++) {
            Courier courier = buildCourier(couriers.getCouriers()[i]);
            dtoarray[i] = (buildCourierDto(courierRepo.save(courier)));
        }
        return new CreateCouriersResponse().setCouriers(dtoarray);
    }

    private Courier buildCourier(@Valid CreateCourierDto courierdto) {
        Courier courier = new Courier().setType(courierdto.getCourier_type());
        setWorkingHoursFromDto(courierdto, courier);
        setRegionsFromDto(courierdto, courier);
        return courier;
    }

    private void setRegionsFromDto(CreateCourierDto courierdto, Courier courier) {
        for (int i = 0; i < courierdto.getRegions().length; i++) {
            Integer region_index = courierdto.getRegions()[i];
            courier.getRegions().add(new Region().setIndex(region_index));
        }
    }

    private void setWorkingHoursFromDto(CreateCourierDto courierdto, Courier courier) {
        for (int i = 0; i < courierdto.getWorking_hours().length; i++) {
            String working_interval = courierdto.getWorking_hours()[i];
            courier.getHours().add(new TimeInterval(working_interval));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public @NotNull GetCourierMetaInfoResponse getMetabyId(@NotNull Integer courierId, @NotNull LocalDate startDate, @NotNull LocalDate endDate) {
        Courier courier = courierRepo.findById(Long.valueOf(courierId)).orElseThrow(() -> new EntityNotFoundException("Courier " + courierId + " is not found"));
        GetCourierMetaInfoResponse meta_info = new GetCourierMetaInfoResponse();
        meta_info.setCourier_id(courier.getId());
        meta_info.setCourier_type(courier.getType());
        meta_info.setWorking_hours(ExtractHoursFromCourier(courier));
        meta_info.setRegions(ExtractRegionsFromCourier(courier));

        //Calculate earnings and count
        Integer earnings = 0;
        Integer count = 0;
        Order[] orders = courier.getOrders().toArray(new Order[0]);
        for (Order order : orders) {
            LocalDate complete_date = order.getCompleted_time().toLocalDate();
            if ((Objects.equals(complete_date, startDate) || (complete_date.isAfter(startDate))) && complete_date.isBefore(endDate)) {
                earnings += order.getCost();
                count++;
            }
        }
        if (count == 0) {
            return meta_info;
        }
//        Recalculate based on type
        earnings *= EarningsRatiobyType(courier);
        meta_info.setEarnings(earnings);

//        Calculate rating

        Long hours_between = hoursBetween(startDate, endDate);
        Integer rating = Math.toIntExact((long) count * RatingRatiobyType(courier) / hours_between);
        meta_info.setRating(rating);
        return meta_info;
    }

    public Long hoursBetween(LocalDate ld1, LocalDate ld2) {
        return Math.abs(ChronoUnit.DAYS.between(ld1, ld2)) * 24;
    }
}
