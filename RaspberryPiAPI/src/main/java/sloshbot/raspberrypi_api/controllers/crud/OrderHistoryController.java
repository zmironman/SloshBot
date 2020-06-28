package sloshbot.raspberrypi_api.controllers.crud;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sloshbot.raspberrypi_api.models.DAOs.OrderHistory;
import sloshbot.raspberrypi_api.repositories.OrderHistoryRepository;
import sloshbot.raspberrypi_api.util.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/crud/v1/orderhistory")
public class OrderHistoryController {
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public List<OrderHistory> getAllOrderHistorys() {
        return orderHistoryRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<OrderHistory> getOrderHistoryById(@PathVariable(value = "id") int orderHistoryId)
            throws ResourceNotFoundException {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderHistory not found for this id ::" + orderHistoryId));
        return ResponseEntity.ok().body(orderHistory);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public OrderHistory createOrderHistory(@RequestBody OrderHistory orderHistory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        orderHistory.setCreatedDate(new Timestamp(DateTime.now().getMillis()));
        return orderHistoryRepository.save(orderHistory);
    }

    //This does nothing right now.  There should be no reason we would update a history record
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<OrderHistory> updateOrderHistory(@PathVariable(value = "id") int orderHistoryId,
                                                     @RequestBody OrderHistory orderHistoryDetails)
            throws ResourceNotFoundException {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderHistory not found for this id ::" + orderHistoryId));
        final OrderHistory updatedOrderHistory = orderHistoryRepository.save(orderHistory);
        return ResponseEntity.ok(updatedOrderHistory);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERUSER')")
    public boolean deleteOrderHistory(@PathVariable(value = "id") int orderHistoryId)
            throws ResourceNotFoundException {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderHistory not found for this id :: " + orderHistoryId));
        orderHistoryRepository.delete(orderHistory);
        return true;
    }
}
