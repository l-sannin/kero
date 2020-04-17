package org.sannin.kero.db;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sannin.kero.commons.KeroProperties;

import java.sql.Connection;


public class DatabasePool {
    private static final Logger log = LogManager.getLogger(DatabasePool.class);
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    public static boolean intialize(){
        try {
            String user = KeroProperties.properties.get("kero.db.user");
            String password = KeroProperties.properties.get("kero.db.password");
            String url = KeroProperties.properties.get("kero.db.url");
            int minPoolSize = Integer.parseInt(KeroProperties.properties.get("kero.db.minPoolSize"));
            int maxPoolSize = Integer.parseInt(KeroProperties.properties.get("kero.db.maxPoolSize"));
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(password);
            config.setMinimumIdle(minPoolSize);
            config.setMaximumPoolSize(maxPoolSize);
            ds = new HikariDataSource(config);
            log.info("DataSource for "+url+" on GREEN...");
            return true;
        }catch(Exception e){
            log.error("Unable to start database pool DataSource on RED...",e);
            System.exit(0);
            return false;
        }
    }

    public static Connection getConnection() throws Exception{
        return ds.getConnection();
    }

}
