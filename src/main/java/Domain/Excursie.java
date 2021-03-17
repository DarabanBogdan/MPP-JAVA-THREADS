package Domain;

import java.io.Serializable;

public class Excursie implements Serializable {
    private Integer id;
    private String numeObiectivTuristic;
    private String numeFirma;
    private String oraPlecare;
    private Integer pret;
    private Integer numarLocuriDisponibile;

    public Excursie(Integer id, String numeObiectivTuristic, String numeFirma, String oraPlecare, Integer pret, Integer numarLocuriDisponibile) {
        this.id = id;
        this.numeObiectivTuristic = numeObiectivTuristic;
        this.numeFirma = numeFirma;
        this.oraPlecare = oraPlecare;
        this.pret = pret;
        this.numarLocuriDisponibile = numarLocuriDisponibile;
    }

    public Integer getId() {
        return id;
    }

    public String getNumeObiectivTuristic() {
        return numeObiectivTuristic;
    }

    public String getNumeFirma() {
        return numeFirma;
    }

    public String getOraPlecare() {
        return oraPlecare;
    }

    public Integer getPret() {
        return pret;
    }

    public Integer getNumarLocuriDisponibile() {
        return numarLocuriDisponibile;
    }

    public void setNumarLocuriDisponibile(Integer numar){
        this.numarLocuriDisponibile=numar;

    }
}
