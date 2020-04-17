package org.sannin.kero.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sannin.kero.commons.KeroProperties;

import static spark.Spark.*;

public class RestGateway {

    private static final Logger log = LogManager.getLogger(RestGateway.class);

    public static boolean initialize(){
        try {
            String bindIp = KeroProperties.properties.get("kero.rest.bind.ip");
            String bindPort = KeroProperties.properties.get("kero.rest.bind.port");
            int poolSize = Integer.parseInt(KeroProperties.properties.get("kero.rest.threadPool"));
            ipAddress(bindIp); // set ip to start service;
            port(Integer.parseInt(bindPort));
            threadPool(poolSize);
            get("/record/:token/:id/:lat/:long", (request, response) -> {
                TrackProcess track = new TrackProcess();
                return track.track(request.params(":token"),request.params(":id"),request.params(":lat"),request.params(":long"), request.ip());
            });
            log.info("RestGateway on GREEN...");
            return true;
        }catch(Exception e){
            log.error("Unable to start database pool DataSource on RED...",e);
            return false;
        }
    }

}
