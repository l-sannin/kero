package org.sannin.kero.api;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sannin.kero.commons.KeroProperties;
import org.sannin.kero.db.DatabasePool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import static spark.Spark.port;
import static spark.route.HttpMethod.get;

public class TrackProcess {
    private static final Logger log = LogManager.getLogger(RestGateway.class);
    private static final String FAILED = "{\"result\": \"failed\"}";
    private static final String OK = "{\"result\": \"ok\"}";
    private Connection con;
    public String track(String token,String id, String lat, String lon, String ip){
        try {
            con = DatabasePool.getConnection();
            String sql = "Select count(*) as is_valid from device where device_id=? and token=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,id);
            ps.setString(2,token);
            ResultSet result =ps.executeQuery();
            if(result.next()){
                do{
                    int is_valid = result.getInt("is_valid");
                    if(is_valid==1){
                        sql = "insert into track_data values(?,?,?,?,?,now(),?)";
                        ps = con.prepareStatement(sql);
                        String uniqueID = UUID.randomUUID().toString();
                        ps.setString(1,uniqueID);
                        ps.setString(2,token);
                        ps.setFloat(3,Float.parseFloat(lat));
                        ps.setFloat(4,Float.parseFloat(lon));
                        ps.setString(5,ip);
                        ps.setString(6,id);
                        ps.execute();
                        return OK;
                    }else{
                        log.warn("Invalid token or id ->"+id+" - "+token);
                        return FAILED;
                    }
                }while (result.next());
            }
            return FAILED;
        }catch(Exception e){
            log.error("Error on track process",e);
            return FAILED;
        }finally {
            try{
                con.close();
            }catch (Exception ex){
                //ignored
            }

        }
    }
}
