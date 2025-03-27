package supermarket;

import supermarket.dao.order.OrderDAO;
import supermarket.dao.order.OrderDAOImpl;
import supermarket.dao.product.ProductDAO;
import supermarket.dao.product.ProductDAOImpl;
import supermarket.model.Product;
import supermarket.model.Order;
import supermarket.service.OrderService;
import supermarket.util.FileReaderUtil;
import supermarket.util.LoggerUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;


public class Main {

    private static final Logger logger = LoggerUtil.getLogger();
    public static void main(String[] args) {
        

        // DAO d'abord
        ProductDAO productDAO = new ProductDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();

        // Vider la base pour éviter les doublons
        productDAO.clearAllProducts();
        orderDAO.clearAllOrders();

        // Initialisation
        ArrayList<Product> products = FileReaderUtil.readProductsFromFile("data/products.txt");
        ArrayList<Order> orders = FileReaderUtil.readOrdersFromFile("data/orders.txt");

        // Charger en base
        products.forEach(productDAO::insertProduct);

        ArrayList<Order> recentOrders = OrderService.filterRecentOrders(orders);
        recentOrders.forEach(orderDAO::insertOrder);

        // === MENU ===
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Afficher tous les produits");
            System.out.println("2. Ajouter un produit");
            System.out.println("3. Supprimer une commande");
            System.out.println("4. Afficher les produits d'une catégorie");
            System.out.println("5. Afficher les produits avec un stock < 20");
            System.out.println("6. Afficher les produits entre un prix min et un prix max");
            System.out.println("7. Quitter");

            System.out.print("Choix : ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    List<Product> allProducts = productDAO.getAllProducts();
                    logger.info("Choix menu : 1 - Affichage de tous les produits");
                    System.out.println("Liste des produits :");
                    allProducts.forEach(System.out::println);
                    break;
                case 7:
                    System.out.println("À bientôt !");
                    break;
                default:
                    System.out.println("Choix invalide.");
                    break;
                    case 2:
                    try {
                        System.out.print("ID produit : ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                
                        // Vérification d'existence
                        if (productDAO.productExists(id)) {
                            System.out.println("Un produit avec cet ID existe déjà. Choisissez un autre ID.");
                            break;
                        }
                
                        System.out.print("Nom : ");
                        String name = scanner.nextLine();
                
                        System.out.print("Catégorie : ");
                        String category = scanner.nextLine();
                
                        System.out.print("Prix : ");
                        double price = scanner.nextDouble();
                
                        System.out.print("Stock : ");
                        int stock = scanner.nextInt();
                        scanner.nextLine();
                
                        Product newProduct = new Product(id, name, category, price, stock);
                        productDAO.insertProduct(newProduct);
                        System.out.println("Produit ajouté avec succès !");
                        logger.info("Choix menu : 3 - Ajout d’un produit");
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'ajout du produit. Vérifiez vos saisies.");
                        scanner.nextLine();
                    }
                    break;
                case 3:
                    System.out.print("Entrez l'ID de la commande à supprimer : ");
                    int orderId = scanner.nextInt();
                    logger.info("Commande ID " + orderId + " supprimée");
                    scanner.nextLine();
                    orderDAO.deleteOrderById(orderId);
                break;

                case 4:
                    System.out.print("Entrez le nom de la catégorie : ");
                    String category = scanner.nextLine();
                    List<Product> productsByCategory = productDAO.getProductsByCategory(category);

                    if (productsByCategory.isEmpty()) {
                        System.out.println("Aucun produit trouvé dans cette catégorie.");
                    } else {
                        System.out.println("Produits de la catégorie '" + category + "' :");
                        productsByCategory.forEach(System.out::println);
                    }
                break;

                case 5:
                    List<Product> lowStockProducts = productDAO.getProductsWithLowStock();

                    if (lowStockProducts.isEmpty()) {
                        System.out.println("Aucun produit avec un stock inférieur à 20.");
                    } else {
                        System.out.println("Produits avec un stock < 20 :");
                        lowStockProducts.forEach(System.out::println);
                    }
                break;

                case 6:
                    try {
                        System.out.print("Prix minimum : ");
                        double minPrice = scanner.nextDouble();

                        System.out.print("Prix maximum : ");
                        double maxPrice = scanner.nextDouble();
                        scanner.nextLine(); // vider le buffer

                        List<Product> filteredProducts = productDAO.filterProductsByPriceRange(minPrice, maxPrice);

                        if (filteredProducts.isEmpty()) {
                            System.out.println("Aucun produit trouvé dans cette fourchette de prix.");
                        } else {
                            System.out.println("Produits entre " + minPrice + "€ et " + maxPrice + "€ :");
                            filteredProducts.forEach(System.out::println);
                        }

                    } catch (Exception e) {
                        System.out.println("Erreur de saisie. Veuillez entrer des valeurs valides.");
                        scanner.nextLine(); // en cas d'erreur
                    }
                break;
            }                
        } while (choice != 2);
        scanner.close();
    }
}
