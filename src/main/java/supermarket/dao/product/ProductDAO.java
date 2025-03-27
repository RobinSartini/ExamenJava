package supermarket.dao.product;

import supermarket.model.Product;

import java.util.List;

public interface ProductDAO {
    void insertProduct(Product product);
    void clearAllProducts();
    boolean productExists(int productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsWithLowStock();
    List<Product> filterProductsByPriceRange(double minPrice, double maxPrice);
}
