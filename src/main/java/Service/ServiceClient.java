package Service;

import Domain.Angajat;
import Domain.Excursie;
import Domain.Rezervare;
import Repo.IRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServiceClient {
    private IRepository<String, Angajat> repoAngajat;
    private IRepository<Integer, Rezervare> repoRezervare;
    private IRepository<Integer, Excursie> repoExcursie;
    private static final Logger logger = LogManager.getLogger();
    private final Socket socket;
    private  ObjectOutputStream toServerObject;
    private ObjectInputStream fromServerObject;

    public ServiceClient(IRepository<String, Angajat> repoAngajat, IRepository<Integer, Rezervare> repoRezervare, IRepository<Integer, Excursie> repoExcursie,Socket socket) throws IOException {
        this.repoAngajat=repoAngajat;
        this.repoExcursie=repoExcursie;
        this.repoRezervare=repoRezervare;
        this.socket=socket;
        this.toServerObject = new ObjectOutputStream(socket.getOutputStream());
        this. fromServerObject = new ObjectInputStream(socket.getInputStream());
    }
    public boolean login(Angajat angajat) throws IOException {

        toServerObject.writeObject(new String("login"));
        toServerObject.flush();
        toServerObject.writeObject(angajat);
        toServerObject.flush();
        boolean val=fromServerObject.readBoolean();
        return val;
    }






    public Iterable<Excursie> findAllMatch(String numeObiectivTuristic, String oraInceput, String oraSfarsit) throws IOException, ClassNotFoundException {

        List<Excursie> tasks=new ArrayList<>();

        toServerObject.writeObject(new String("findAllMatch"));
        toServerObject.flush();
        toServerObject.writeObject(numeObiectivTuristic);
        toServerObject.flush();
        toServerObject.writeObject(oraInceput);
        toServerObject.flush();
        toServerObject.writeObject(oraSfarsit);
        toServerObject.flush();
        tasks= (List<Excursie>) fromServerObject.readObject();


        return tasks;

    }

    public boolean addRezervare(Rezervare rezervare, int idExcursie) throws IOException {

        toServerObject.writeObject(new String("addRezervare"));
        toServerObject.flush();
        toServerObject.writeObject(rezervare);
        toServerObject.flush();
        toServerObject.write(idExcursie);
        toServerObject.flush();
        boolean bool=fromServerObject.readBoolean();


        return bool;
    }


    public Iterable<Rezervare> findAllRezervare(Excursie excursie) throws IOException, ClassNotFoundException {

        toServerObject.writeObject(new String("findAllRezervare"));
        toServerObject.flush();
        toServerObject.writeObject(excursie);
        toServerObject.flush();
        List<Rezervare> rez= (List<Rezervare>) fromServerObject.readObject();

        return rez;

    }
    public Iterable<Excursie> findAllExcursie() throws IOException, ClassNotFoundException {


        toServerObject.writeObject(new String("findAllExcursie"));
        toServerObject.flush();
        List<Excursie> rez= (List<Excursie>) fromServerObject.readObject();

        return  rez;
    }
}
