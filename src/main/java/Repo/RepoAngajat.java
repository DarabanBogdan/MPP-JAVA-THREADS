package Repo;

import Domain.Angajat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepoAngajat implements IRepository<String, Angajat> {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public RepoAngajat(){
        logger.info("Initializing RepoAngajat with properties: {} ");
        dbUtils = new JdbcUtils();
    }
    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Angajat entity) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void update(String s, Angajat entity) {

    }

    @Override
    public Angajat findOne(String s) {

        logger.traceEntry("find angajat {}",s);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select password  from Angajat where Username=?")) {
            preStmt.setString(1,s);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    String password = result.getString("Password");

                    logger.traceExit();

                    return new Angajat(s,password);
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

        return null;
    }

    @Override
    public Iterable<Angajat> findAll() {
        return null;
    }

    @Override
    public Iterable<Angajat> findAllMatch(Integer idExcursie) {
        return null;
    }

    @Override
    public Iterable<Angajat> findAllMatch(String numeObiectivTuristic) {
        return null;
    }
}
