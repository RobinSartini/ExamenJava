package supermarket.service;

import supermarket.model.Order;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrderService {

    public static ArrayList<Order> filterRecentOrders(ArrayList<Order> orders) {
        //Filtre les commandes de -1 an
        ArrayList<Order> recentOrders = new ArrayList<>();
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        for (Order order : orders) {
            if (!order.getOrderDate().isBefore(oneYearAgo)) {
                recentOrders.add(order);
            }
        }

        return recentOrders;
    }
}
