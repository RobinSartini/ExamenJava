package supermarket.service;

import org.junit.jupiter.api.Test;
import supermarket.model.Order;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void testFilterRecentOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order(1, LocalDate.now().minusMonths(6), 1, 2)); // récente
        orders.add(new Order(2, LocalDate.now().minusYears(2), 2, 3));  // trop vieille

        ArrayList<Order> filtered = OrderService.filterRecentOrders(orders);

        assertEquals(1, filtered.size());
        assertEquals(1, filtered.get(0).getOrderId());
    }
}
