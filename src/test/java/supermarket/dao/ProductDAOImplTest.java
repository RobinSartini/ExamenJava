package supermarket.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import supermarket.dao.product.ProductDAO;
import supermarket.dao.product.ProductDAOImpl;
import supermarket.model.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOImplTest {

    private final ProductDAO productDAO = new ProductDAOImpl();

    @BeforeEach
    void setup() {
        // On nettoie la base avant chaque test
        productDAO.clearAllProducts();

        productDAO.insertProduct(new Product(100, "Test A", "Test", 1.50, 10));
        productDAO.insertProduct(new Product(101, "Test B", "Test", 2.50, 10));
        productDAO.insertProduct(new Product(102, "Test C", "Test", 3.50, 10));
    }

    @Test
    void testFilterProductsByPriceRange() {
        List<Product> filtered = productDAO.filterProductsByPriceRange(1.00, 2.00);

        assertEquals(1, filtered.size());
        assertEquals("Test A", filtered.get(0).getName());
    }
}
