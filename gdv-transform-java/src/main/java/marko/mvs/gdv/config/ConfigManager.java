package marko.mvs.gdv.config;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ConfigManager {
    private static final Logger logger = Logger.getLogger(ConfigManager.class.getName());
    private static ConfigManager instance;
    @Getter
    private final Map<String, String> configMap;
    private boolean loadConfig;

    // Private constructor to prevent instantiation
    private ConfigManager() {
        configMap = loadConfig();
    }

    // Public method to provide access to the singleton instance
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    // Method to load the configuration
    private Map<String, String> loadConfig() {
        Map<String, String> configMap = new HashMap<>();
        String configPath = "./config/config.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(configPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) continue;
                String[] parts = line.split("=", 2); // Split only once on first '='
                if (parts.length == 2) {
                    configMap.put(parts[0].trim(), parts[1].trim());
                } else {
                    logger.warning("Ignoring malformed line: " + line);
                }
            }
            loadConfig = true;
        } catch (IOException e) {
            loadConfig = false;
            logger.severe("Load Config Error: " + e.getMessage());
        }
        return configMap;
    }

    // Method to get a configuration value by key
    public String getConfigValue(String key) {
        return configMap.getOrDefault(key, null);
    }

    // Method to check if the config was loaded successfully
    public boolean isConfigLoaded() {
        return loadConfig;
    }
}
