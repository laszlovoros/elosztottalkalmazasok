
package com.mycompany.tanardiak;

import java.text.Collator;
import java.util.Comparator;

/**
 *
 * 
 * @author Kriszti
 */
public class Ember implements Comparable<Ember>{


    protected String nev;
    protected String azonosito;

    public Ember() {
        nev = null;
        azonosito = null;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setAzonosito(String azonosito) {
        this.azonosito = azonosito;
    }

    public String getNev() {
        return nev;
    }

    public String getAzonosito() {
        return azonosito;
    }

    public Ember(String name, String ID) {
        this.nev = name;
        this.azonosito = ID;
    }

    public int compareTo(Ember e){
        Collator col=Collator.getInstance();
        return col.compare(this.nev,e.nev);
    }

    public String toString() {
        return nev + " [" + azonosito + "]";
    }
}

