package Main;

import Domain.Angajat;
import Domain.Excursie;
import Domain.Rezervare;
import Service.ServiceServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    final Socket s;
    private  ServiceServer service;

    public ClientHandler(Socket s, ServiceServer service)
    {
        this.s = s;
        this.service=service;
    }

    @Override
    public void run()
    {    try {
        ObjectOutputStream toClientObject = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream fromClientObject = new ObjectInputStream(s.getInputStream());
        while(!s.isClosed()) {


            String comand = (String) fromClientObject.readObject();

            if (comand.equals("login")) {

                Angajat angajat = (Angajat) fromClientObject.readObject();

                if (service.login(angajat))
                    toClientObject.writeBoolean(true);
                else
                    toClientObject.writeBoolean(false);

                toClientObject.flush();
            } else if (comand.equals("findAllMatch")) {

                String numeObiectivTuristic = (String) fromClientObject.readObject();
                String oraInceput = (String) fromClientObject.readObject();
                String oraSfarsit = (String) fromClientObject.readObject();

                List<Excursie> tasks = (List<Excursie>) service.findAllMatch(numeObiectivTuristic, oraInceput, oraSfarsit);
                toClientObject.writeObject(tasks);
                toClientObject.flush();
            } else if (comand.equals("addRezervare")) {

                Rezervare rez = (Rezervare) fromClientObject.readObject();
                int id = fromClientObject.read();
                toClientObject.writeBoolean(service.addRezervare(rez, id));
                toClientObject.flush();
            } else if (comand.equals("findAllRezervare")) {
                Excursie ex = (Excursie) fromClientObject.readObject();
                toClientObject.writeObject(service.findAllRezervare(ex));
                toClientObject.flush();
            } else if (comand.equals("findAllExcursie")) {

                toClientObject.writeObject(service.findAllExcursie());
                toClientObject.flush();
            }

        }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }




    }
}
