package Main;

import Repo.RepoAngajat;
import Repo.RepoExcursie;
import Repo.RepoRezervare;
import Service.ServiceServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static void main(String[] args) throws IOException
    {

        RepoAngajat repoAngajat=new RepoAngajat();
        RepoExcursie repoExcursie=new RepoExcursie();
        RepoRezervare repoRezervare=new RepoRezervare();
        ServiceServer service=new ServiceServer(repoAngajat,repoRezervare,repoExcursie);


        ServerSocket ss = new ServerSocket(5056);
        while (true)
        {
            Socket s = null;

            try
            {

                s = ss.accept();

                System.out.println("A new client is connected : " + s);


                System.out.println("Assigning new thread for this client");

                Thread t = new ClientHandler(s,service);

                t.start();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }

    }
}
