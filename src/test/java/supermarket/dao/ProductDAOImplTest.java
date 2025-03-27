package supermarket.dao;

import org.junit.jupiter.api.Test;
import supermarket.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private List<Product> filterProductsByPriceRange(List<Product> products, double min, double max) {
        return products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    @Test
    void testFilterProductsByPriceRange() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Chips", "Snacks", 1.5, 30));
        products.add(new Product(2, "Fanta", "Drinks", 2.5, 10));
        products.add(new Product(3, "Pain", "Bakery", 0.9, 20));

        List<Product> filtered = filterProductsByPriceRange(products, 1.0, 2.0);

        assertEquals(1, filtered.size());
        assertEquals("Chips", filtered.get(0).getName());
    }
}
