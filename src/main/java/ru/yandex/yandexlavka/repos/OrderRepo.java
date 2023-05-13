package ru.yandex.yandexlavka.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.yandexlavka.domain.ClientOrder;

@Repository
public interface OrderRepo extends JpaRepository<ClientOrder, Long> {
}
