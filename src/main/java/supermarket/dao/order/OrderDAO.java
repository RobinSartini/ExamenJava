package supermarket.dao.order;

import supermarket.model.Order;


public interface OrderDAO {
    void insertOrder(Order order);
    void clearAllOrders();
    void deleteOrderById(int orderId); 
}
