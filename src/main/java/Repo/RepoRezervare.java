package Repo;

import Domain.Rezervare;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public  class RepoRezervare implements IRepository<Integer, Rezervare> {
private JdbcUtils dbUtils;
private static final Logger logger = LogManager.getLogger();

public RepoRezervare(){
        logger.info("Initializing RepoRezervare with properties: {} ");
        dbUtils = new JdbcUtils();
        }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public synchronized void save(Rezervare entity) {
    logger.traceEntry("saving Rezervare {}",entity);
    Connection con=dbUtils.getConnection();
    try(PreparedStatement preStmt=con.prepareStatement("insert into Rezervare(NumeClient,NumarTelefon,NumarBilete,idExcursie)  values (?,?,?,?)")){
        preStmt.setString(1,entity.getNumeClient());
        preStmt.setString(2,entity.getNumarTelefon());
        preStmt.setInt(3,entity.getNumarBilete());
        preStmt.setInt(4,entity.getId());
        int result=preStmt.executeUpdate();
    } catch (SQLException ex) {
        logger.error(ex);
        System.out.println("Error DB "+ex);
    }
    logger.traceExit();

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Rezervare entity) {

    }

    @Override
    public Rezervare findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Rezervare> findAll() {
    return null;
    }
    public Iterable<Rezervare> findAllMatch(Integer idExcursie){
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Rezervare> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Rezervare where idExcursie=?")) {
            preStmt.setInt(1,idExcursie);
            try(ResultSet result=preStmt.executeQuery()) {

                while (result.next()) {

                    String numeClient=result.getString("NumeClient");
                    String numarTelefon=result.getString("NumarTelefon");
                    Integer numarBilete=result.getInt("NumarBilete");
                    tasks.add(new Rezervare(idExcursie,numeClient,numarTelefon,numarBilete));


                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(tasks);
        return tasks;
    }

    @Override
    public Iterable<Rezervare> findAllMatch(String numeObiectivTuristic) {
        return null;
    }


}

