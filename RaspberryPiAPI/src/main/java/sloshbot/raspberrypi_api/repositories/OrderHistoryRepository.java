package sloshbot.raspberrypi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sloshbot.raspberrypi_api.models.hibernateModels.OrderHistory;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
}