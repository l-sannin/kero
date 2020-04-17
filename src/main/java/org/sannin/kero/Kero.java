package org.sannin.kero;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sannin.kero.api.RestGateway;
import org.sannin.kero.commons.KeroProperties;
import org.sannin.kero.db.DatabasePool;


public class Kero {
    private static final Logger log = LogManager.getLogger(Kero.class);

    public static void main(String[] args) {
        KeroProperties.loadProperties();
        DatabasePool.intialize();
        RestGateway.initialize();
        log.info("KERO ONLINE...");
    }
}
