package supermarket.dao.order;

import supermarket.model.Order;
import supermarket.util.DBConfig;
import supermarket.util.LoggerUtil;
import java.util.logging.Logger;
import java.sql.*;


public class OrderDAOImpl implements OrderDAO {

    private static final Logger logger = LoggerUtil.getLogger();

    @Override
    public void clearAllOrders() {
        try (Connection conn = DBConfig.getConnection();
            Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM orders");
        } catch (Exception e) {
            System.err.println("❌ Erreur suppression des commandes");
            e.printStackTrace();
        }
    }


    @Override
    public void insertOrder(Order order) {
        String sql = "INSERT INTO orders (order_id, order_date, product_id, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getOrderId());
            stmt.setDate(2, Date.valueOf(order.getOrderDate()));
            stmt.setInt(3, order.getProductId());
            stmt.setInt(4, order.getQuantity());

            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("❌ Erreur insertion commande ID " + order.getOrderId());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderById(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = DBConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Commande supprimée avec succès !");
                logger.info("🗑️ Commande supprimée avec ID : " + orderId);
            } else {
                System.out.println("Aucune commande avec cet ID.");
                logger.info("ℹ️ Aucune commande supprimée - ID non trouvé : " + orderId);
            }
        } catch (Exception e) {
            logger.severe("❌ Erreur suppression commande ID " + orderId + " - " + e.getMessage());
            System.err.println(" Erreur suppression commande ID " + orderId);
            e.printStackTrace();
        }
    }
    
}
