package com.mycompany.tanardiak;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;

/**
 *
 * @author Kriszti Diák osztály, mely az Ember osztályból öröklődik. Átlag és
 * osztály változók
 */
public class Diak extends Ember implements Serializable {

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
        return nev + " [ID:" + azonosito + "; Osztály:" + osztaly + "; Átlag:" + atlag + "]";
    }

    public static Comparator AtlagRendezo() {
        return new AtlagComparator();
    }

    public int compareTo(Ember e) {
        Collator col = Collator.getInstance();
        return col.compare(this.nev, e.nev);
    }

}
