package supermarket.util;

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {
    private static final Logger LOGGER = Logger.getLogger("SupermarketLogger");

    static {
        try {
            // Définir un fichier log
            Handler fileHandler = new FileHandler("Info.log", true); // append = true
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO);
            LOGGER.setUseParentHandlers(false); 
        } catch (IOException e) {
            System.err.println("Impossible d'initialiser le logger : " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
