package ru.yandex.yandexlavka.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.yandexlavka.domain.Courier;

@Repository
public interface CourierRepo extends JpaRepository<Courier, Long> {
}
