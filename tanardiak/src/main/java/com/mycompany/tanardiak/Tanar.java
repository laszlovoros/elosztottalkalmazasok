package com.mycompany.tanardiak;

/**
 *
 * @author Kriszti Tanar osztaly, mely az Ember osztalybol oroklodik
 */
public class Tanar extends Ember {

    private String targy;

    public Tanar(String nev, String azonosito, String targy) {
        super(nev, azonosito);
        this.targy = targy;
    }

    public void setTargy(String targy) {
        this.targy = targy;
    }

    public String getTargy() {
        return targy;
    }

    public String toString() {
        return nev + " [" + azonosito + ";" + targy + "]";
    }

}
