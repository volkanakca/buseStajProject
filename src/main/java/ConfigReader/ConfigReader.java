package ConfigReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPhoneNo() {
        return properties.getProperty("phoneNo");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }
    public static String getNewJob() {
        return properties.getProperty("newJob");
    }
}
