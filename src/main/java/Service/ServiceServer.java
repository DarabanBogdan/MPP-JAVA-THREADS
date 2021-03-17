package Service;

import Domain.Angajat;
import Domain.Excursie;
import Domain.Rezervare;
import Repo.IRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ServiceServer {
    private IRepository<String, Angajat> repoAngajat;
    private IRepository<Integer, Rezervare> repoRezervare;
    private IRepository<Integer, Excursie> repoExcursie;
    private static final Logger logger = LogManager.getLogger();

    public ServiceServer(IRepository<String, Angajat> repoAngajat, IRepository<Integer, Rezervare> repoRezervare, IRepository<Integer, Excursie> repoExcursie){
        this.repoAngajat=repoAngajat;
        this.repoExcursie=repoExcursie;
        this.repoRezervare=repoRezervare;
    }
    public boolean login(Angajat angajat){

        logger.traceEntry("Login Angajat: {}",angajat);
        Angajat temp =repoAngajat.findOne(angajat.getUsername());
        if(temp!=null){
            if(temp.getPassword().equals(angajat.getPassword())){
                logger.traceExit("Succes!");
                return true;}
        }
        logger.traceExit("Failed!");
        return false;

    }

    private class Ora{
        int ora,minut;
        Ora(int o,int m){
            ora=o;
            minut=m;
        }

    }

    private Ora StringToOra(String s){
        String[] spliting=s.split(":",2);
        return new Ora(Integer.parseInt(spliting[0]),Integer.parseInt(spliting[1]));

    }


    public Iterable<Excursie> findAllMatch(String numeObiectivTuristic, String oraInceput, String oraSfarsit){
        logger.traceEntry("find all match excursie");
        List<Excursie> tasks=new ArrayList<>();
        List<Excursie> all= (List<Excursie>) repoExcursie.findAllMatch(numeObiectivTuristic);
        Ora inceput=StringToOra(oraInceput);
        Ora sfarsit=StringToOra(oraSfarsit);
        for(Excursie curent:all){
            Ora curentOra=StringToOra(curent.getOraPlecare());
            if(curentOra.ora>inceput.ora && curentOra.ora<sfarsit.ora )
                tasks.add(curent);
            else if(curentOra.ora==inceput.ora && curentOra.minut>=inceput.minut && curentOra.ora<sfarsit.ora)
                tasks.add(curent);
            else if(curentOra.ora==inceput.ora && curentOra.minut>=inceput.minut && curentOra.ora==sfarsit.ora && curentOra.minut<=sfarsit.minut)
                tasks.add(curent);
            else if(curentOra.ora>inceput.ora &&  curentOra.ora==sfarsit.ora && curentOra.minut<=sfarsit.minut)
                tasks.add(curent);
        }
        logger.traceExit(tasks);
        return tasks;

    }

    public boolean addRezervare(Rezervare rezervare, int idExcursie){
        logger.traceEntry("add rezervare {}",rezervare);
        int locuri=repoExcursie.findOne(idExcursie).getNumarLocuriDisponibile()-rezervare.getNumarBilete();
        if(locuri>=0) {
            repoRezervare.save(rezervare);
            repoExcursie.update(idExcursie, new Excursie(0, "", "", "",0,locuri));
            logger.traceExit("Succes!");
            return true;
        }

        logger.traceExit("Failed!");
        return false;
    }
    public Iterable<Rezervare> findAllRezervare(Excursie excursie){
        logger.traceEntry("find all rezervare");
        logger.traceExit();
        return repoRezervare.findAllMatch(excursie.getId());

    }
    public Iterable<Excursie> findAllExcursie(){
        logger.traceEntry("find all excursie");
        logger.traceExit();
        return repoExcursie.findAll();

    }
}
