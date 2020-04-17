package org.sannin.kero.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;


public class KeroProperties {
    private static final Logger log = LogManager.getLogger(KeroProperties.class);
    public static HashMap<String,String> properties = new HashMap<>();
    public static void loadProperties(){
        try {
            String path = System.getProperty("kero.properties");
            InputStream input = new FileInputStream(path);
            Properties prop = new Properties();
            prop.load(input);
            for (String key : prop.stringPropertyNames()) {
                properties.put(key, prop.getProperty(key));
            }
            log.info("KERO properties on GREEN...");
        } catch (Exception e) {
            log.error("Error loading properties, KERO properties on RED...", e);
            System.exit(0);
        }
    }
}
