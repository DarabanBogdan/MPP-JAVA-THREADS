package Domain;

import java.io.Serializable;

public class Rezervare implements Serializable {
    private Integer id;
    private String numeClient;
    private String numarTelefon;
    private Integer numarBilete;

    public Rezervare(Integer id, String numeClient, String numarTelefon, Integer numarBilete) {
        this.id = id;
        this.numeClient = numeClient;
        this.numarTelefon = numarTelefon;
        this.numarBilete = numarBilete;
    }

    public Integer getId() {
        return id;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public Integer getNumarBilete() {
        return numarBilete;
    }
}
