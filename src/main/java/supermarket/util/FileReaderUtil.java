package supermarket.util;

import supermarket.model.Product;
import supermarket.model.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileReaderUtil {

    public static ArrayList<Product> readProductsFromFile(String filepath) {
        ArrayList<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String category = parts[2];
                double price = Double.parseDouble(parts[3]);
                int stock = Integer.parseInt(parts[4]);

                products.add(new Product(id, name, category, price, stock));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }

    public static ArrayList<Order> readOrdersFromFile(String filepath) {
        ArrayList<Order> orders = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                int id = Integer.parseInt(parts[0]);
                LocalDate date = LocalDate.parse(parts[1]);
                int productId = Integer.parseInt(parts[2]);
                int quantity = Integer.parseInt(parts[3]);

                orders.add(new Order(id, date, productId, quantity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
