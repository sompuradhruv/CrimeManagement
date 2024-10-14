package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {
    // Method to retrieve the database connection string from the properties file
    public static String getConnectionString(String propertiesFile) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(propertiesFile)) {
            properties.load(input);
            return properties.getProperty("db.url");
        } catch (IOException ex) {
            System.err.println("Error reading properties file: " + ex.getMessage());
            return null; // Return null if there's an error
        }
    }
}
