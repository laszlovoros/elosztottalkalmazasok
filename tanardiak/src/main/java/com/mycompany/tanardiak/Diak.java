package com.mycompany.tanardiak;

/**
 *
 * @author Kriszti 
 * Diák osztály, mely az Ember osztályból öröklődik. Átlag és osztály változók
 */
public class Diak extends Ember {

    private String osztaly;
    private float atlag;

    public Diak(String nev, String ID, String osztaly, float atlag) {
        super(nev, ID);
        this.osztaly = osztaly;
        this.atlag = atlag;
    }

    public String getOsztaly() {
        return osztaly;
    }

    public float getAtlag() {
        return atlag;
    }

    public void setOsztaly(String osztaly) {
        this.osztaly = osztaly;
    }

    public void setAtlag(float atlag) {
        this.atlag = atlag;
    }

    public String toString() {
        return nev + " [" + azonosito + ";" + osztaly + ";" + atlag + "]";
    }

}
