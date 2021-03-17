package Repo;

import Domain.Excursie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepoExcursie implements IRepository<Integer, Excursie> {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public RepoExcursie() {
        logger.info("Initializing RepoExcursie with properties: {} ");
        dbUtils = new JdbcUtils();
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Excursie entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public synchronized void update(Integer integer, Excursie entity) {

    logger.traceEntry("update Excursie with id {}",integer);
    Connection con=dbUtils.getConnection();
    try(PreparedStatement preStmt=con.prepareStatement("update Excursie set NumarLocuriDisponibile =? where id=?")){
        preStmt.setInt(1,entity.getNumarLocuriDisponibile());
        preStmt.setInt(2,integer);
        int res=preStmt.executeUpdate();

    }
     catch (SQLException e) {
        logger.error(e);
        System.out.println("Error DB " + e);
    }
        logger.traceExit();
    }

    @Override
    public  Excursie findOne(Integer integer) {
        logger.traceEntry("finding Excursie with id {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Excursie where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()){
                if (result.next()){
                    int id = result.getInt("id");
                    String numeObiectivTuristic = result.getString("NumeObiectivTuristic");
                    String numeFirma = result.getString("NumeFirma");
                    String oraPlecare = result.getString("OraPlecare");
                    int pret = result.getInt("Pret");
                    int numarLocuriDisponibile = result.getInt("NumarLocuriDisponibile");
                    logger.traceExit();
                    return new Excursie(id, numeObiectivTuristic, numeFirma, oraPlecare, pret, numarLocuriDisponibile);
                }
            }


        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit();
        return  null;
    }

    @Override
    public Iterable<Excursie> findAll() {
        logger.traceEntry("finding all Excursions");
        Connection con = dbUtils.getConnection();
        List<Excursie> tasks = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Excursie")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String numeObiectivTuristic = result.getString("NumeObiectivTuristic");
                    String numeFirma = result.getString("NumeFirma");
                    String oraPlecare = result.getString("OraPlecare");
                    int pret = result.getInt("Pret");
                    int numarLocuriDisponibile = result.getInt("NumarLocuriDisponibile");
                    tasks.add(new Excursie(id, numeObiectivTuristic, numeFirma, oraPlecare, pret, numarLocuriDisponibile));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(tasks);
        return tasks;
    }

    @Override
    public Iterable<Excursie> findAllMatch(Integer idExcursie) {
        return null;
    }

    public Iterable<Excursie> findAllMatch(String numeObiectivTuristic){
        logger.traceEntry("finding all Excursions to {}",numeObiectivTuristic);
        Connection con=dbUtils.getConnection();
        List<Excursie> tasks = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Excursie where NumeObiectivTuristic=?")){
            preStmt.setString(1,numeObiectivTuristic);
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()) {
                    int id=result.getInt("id");
                    String numeFirma =result.getString("NumeFirma");
                    String oraPlecare=result.getString("OraPlecare");
                    int pret=result.getInt("Pret");
                    int numarLocuriDisponibile=result.getInt("NumarLocuriDisponibile");
                    tasks.add(new Excursie(id,numeObiectivTuristic,numeFirma,oraPlecare,pret,numarLocuriDisponibile));

                }
            }

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit();
        return tasks;
    }
}


