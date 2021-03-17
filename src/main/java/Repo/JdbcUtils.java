package Repo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcUtils {
    private static final Logger logger= LogManager.getLogger();
    public JdbcUtils(){

    }
    private  Connection instance=null;

    private synchronized Connection getNewConnection(){
        logger.traceEntry();
        Config cfg=new Config();

        logger.info("trying to connect to database ... {}",cfg.getProperty("connectString"));

        Connection con=null;
        try {
            Class.forName("org.sqlite.JDBC");
            logger.info("Loaded driver ...");

                con=DriverManager.getConnection(cfg.getProperty("connectString"));
        } catch (ClassNotFoundException e) {
            logger.error(e);
            System.out.println("Error loading driver "+e);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error getting connection "+e);
        }
        return con;
    }

    public synchronized Connection getConnection(){
        logger.traceEntry();
        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(instance);
        return instance;
    }
}
