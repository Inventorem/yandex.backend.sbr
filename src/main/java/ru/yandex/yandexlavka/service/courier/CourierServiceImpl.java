package ru.yandex.yandexlavka.service.courier;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.yandexlavka.domain.Courier;
import ru.yandex.yandexlavka.domain.Region;
import ru.yandex.yandexlavka.domain.TimeInterval;
import ru.yandex.yandexlavka.model.courier.*;
import ru.yandex.yandexlavka.repos.CourierRepo;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService{
    @Autowired
    private CourierRepo courierRepo;
    @Override
    @Transactional(readOnly = true)
    public @NotNull CourierDto findById(@NotNull Integer courierId) {
        return courierRepo.findById(Long.valueOf(courierId))
                .map(this::buildCourierDto)
                .orElseThrow(() -> new EntityNotFoundException("Courier " + courierId + " is not found"));
    }

    private CourierDto buildCourierDto(Courier courier) {
        CourierDto courierdto = new CourierDto().setCourier_id(courier.getId())
                .setCourier_type(courier.getType());
        setRegionsFromCourier(courier, courierdto);
        setWorkingHoursFromCourier(courier, courierdto);

        return courierdto;
    }

    private void setWorkingHoursFromCourier(Courier courier, CourierDto courierdto) {
        if (courier.getHours() == null){
            throw new EntityNotFoundException("Courier #" + courier.getId() + ", working_hours is null");
        }
        TimeInterval[] courier_intervals = courier.getHours().toArray(new TimeInterval [0]);
        courierdto.setWorking_hours(new String [courier.getHours().size()]);
        for (int i = 0; i < courier.getHours().size(); i++){
            courierdto.getWorking_hours()[i] = courier_intervals[i].toString();
        }
    }

    private void setRegionsFromCourier(Courier courier, CourierDto courierdto) {
        if (courier.getRegions() == null){
            throw new EntityNotFoundException("Courier #" + courier.getId() + ", regions is null");
        }
        Region [] regions = courier.getRegions().toArray(new Region[0]);
        courierdto.setRegions(new Integer [courier.getRegions().size()]);
        for (int i = 0; i < courier.getRegions().size(); i++){
            courierdto.getRegions()[i] = regions[i].getIndex();
        }
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
    public @NotNull CreateCouriersResponse createCouriers(@NotNull CreateCourierRequest couriers) {
        CourierDto[] dtoarray = new CourierDto[couriers.getCouriers().length];
        for (int i = 0; i < couriers.getCouriers().length; i ++){
            Courier courier = buildCourier(couriers.getCouriers()[i]);
            System.out.println("Created courier: "+courier);
            System.out.println("Sent courier dto:"+buildCourierDto(courier));
            dtoarray[i] = (buildCourierDto(courierRepo.save(courier)));
        }
        return new CreateCouriersResponse().setCouriers(dtoarray);
    }

    private Courier buildCourier(CreateCourierDto courierdto) {
        Courier courier = new Courier().setType(courierdto.getCourier_type());
        setWorkingHoursFromDto(courierdto, courier);
        setRegionsFromDto(courierdto, courier);
        return courier;
    }

    private void setRegionsFromDto(CreateCourierDto courierdto, Courier courier) {
        for (int i = 0; i < courierdto.getRegions().length; i++){
            Integer region_index = courierdto.getRegions()[i];
            courier.getRegions().add(new Region().setIndex(region_index));
        }
    }

    private void setWorkingHoursFromDto(CreateCourierDto courierdto, Courier courier) {
        for (int i = 0; i < courierdto.getWorking_hours().length; i++){
            String working_interval = courierdto.getWorking_hours()[i];
            courier.getHours().add(new TimeInterval(working_interval));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public @NotNull GetCourierMetaInfoResponse getMetabyId(@NotNull Integer courierId, @NotNull String startDate, @NotNull String endDate) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Date start = df.parse(startDate);
//        Date end = df.parse(endDate);
//

        return null;
    }
}
