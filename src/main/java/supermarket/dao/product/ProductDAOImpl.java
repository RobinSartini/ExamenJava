package supermarket.dao.product;

import supermarket.model.Product;
import supermarket.util.DBConfig;
import supermarket.util.LoggerUtil;
import java.util.logging.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

private static final Logger logger = LoggerUtil.getLogger();

@Override
public void clearAllProducts() {
    try (Connection conn = DBConfig.getConnection();
        Statement stmt = conn.createStatement()) {
        stmt.executeUpdate("DELETE FROM orders"); // d'abord les commandes (clé étrangère)
        stmt.executeUpdate("DELETE FROM products");
    } catch (Exception e) {
        System.err.println("❌ Erreur suppression des produits");
        e.printStackTrace();
    }
}

@Override
public boolean productExists(int productId) {
    String sql = "SELECT COUNT(*) FROM products WHERE product_id = ?";
    try (Connection conn = DBConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, productId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (Exception e) {
        System.err.println("❌ Erreur vérification existence produit ID " + productId);
        e.printStackTrace();
    }
    return false;
}

@Override
public void insertProduct(Product product) {
    String sql = "INSERT INTO products (product_id, name, category, price, stock) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DBConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, product.getProductId());
        stmt.setString(2, product.getName());
        stmt.setString(3, product.getCategory());
        stmt.setDouble(4, product.getPrice());
        stmt.setInt(5, product.getStock());

        stmt.executeUpdate();
        logger.info("Produit inséré : " + product.getName());
    } catch (Exception e) {
        System.err.println("❌ Erreur insertion produit : " + product.getName());
        e.printStackTrace();
    }
}

@Override
public List<Product> getAllProducts() {
    List<Product> products = new ArrayList<>();
    String sql = "SELECT * FROM products";

    try (Connection conn = DBConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Product product = new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
            );
            products.add(product);
        }
    } catch (Exception e) {
        System.err.println("❌ Erreur récupération produits");
        e.printStackTrace();
    }

    return products;
}

@Override
public List<Product> getProductsByCategory(String category) {
    List<Product> products = new ArrayList<>();
    String sql = "SELECT * FROM products WHERE LOWER(category) = LOWER(?)";

    try (Connection conn = DBConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, category);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Product product = new Product(
                rs.getInt("product_id"),
                rs.getString("name"),
                rs.getString("category"),
                rs.getDouble("price"),
                rs.getInt("stock")
            );
            products.add(product);
        }

    } catch (Exception e) {
        System.err.println("Erreur récupération des produits par catégorie");
        e.printStackTrace();
    }

    return products;
  }

  @Override
    public List<Product> getProductsWithLowStock() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE stock < 20";

        try (Connection conn = DBConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                );
                products.add(product);
            }

        } catch (Exception e) {
            System.err.println("Erreur récupération produits avec stock faible");
            e.printStackTrace();
        }

        return products;
    }

    @Override
      public List<Product> filterProductsByPriceRange(double minPrice, double maxPrice) {
          List<Product> products = new ArrayList<>();
          String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ?";

          try (Connection conn = DBConfig.getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql)) {

              stmt.setDouble(1, minPrice);
              stmt.setDouble(2, maxPrice);
              ResultSet rs = stmt.executeQuery();

              while (rs.next()) {
                  Product product = new Product(
                      rs.getInt("product_id"),
                      rs.getString("name"),
                      rs.getString("category"),
                      rs.getDouble("price"),
                      rs.getInt("stock")
                  );
                  products.add(product);
              }

          } catch (Exception e) {
              System.err.println("Erreur filtre produits par prix");
              e.printStackTrace();
          }

          return products;
      }
}
