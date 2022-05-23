package com.mycompany.tanardiak;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 *
 * @author Kriszti AdatSzervizSzolgaltato interfész implementálása
 *
 */
public class AdatKapcsolat implements AdatSzervizSzolgaltato {

    private String[] emberTipusok = {"Tanár", "Diák"};
    private Vector<Ember> emberLista = new Vector<>();

    public String[] getEmberTipusok() {
        return emberTipusok;
    }

    public Vector<Ember> getEmberLista() {
        return emberLista;
    }

    public Ember getEmberBySorszam(int sorszam) {
        return emberLista.get(sorszam);
    }

    public String getRealClass(Ember objektum) {
        if (objektum == null) {
            return null;
        }
        if (objektum instanceof Tanar) {
            return "Tanár";
        }
        if (objektum instanceof Diak) {
            return "Diák";
        }
        return "Ember";
    }

    public int getEmberTipus(Ember objektum) {
        if (objektum == null) {
            return -1;
        }
        if (objektum instanceof Tanar) {
            return 0;
        }
        if (objektum instanceof Diak) {
            return 1;
        }
        return -1;
    }

    public String deleteByAzonosito(String azonosito) {
        boolean talalat = false;
        for (int i = 0; i < emberLista.size(); i++) {
            if (emberLista.get(i).getAzonosito().equals(azonosito)) {
                talalat = true;
                emberLista.remove(i);
                break;
            }
        }
        if (talalat) {
            return azonosito + " azonosítójú elem törölve.";
        }
        return azonosito + " azonosítójú elem nem létezik.";
    }

    private String nevEllenor(String nev) //ellenőrzi, hogy van-e már ilyen név, és ha igen, akkor újat ad
    {
        boolean vanIlyenNev = false;
        int ujNevIndex = 0;
        // ha van ilyen név, akkor újat generálunk a feladat szabályai szerint.
        for (int i = 0; i < emberLista.size(); i++) {
            String emberNev = emberLista.get(i).getNev();
            if (emberNev.startsWith(nev)) {
                if (emberNev.equals(nev)) {
                    if (ujNevIndex == 0) {
                        ujNevIndex = 1;
                    }
                    vanIlyenNev = true;
                } else {
                    try {
                        int nevIndex = Integer.parseInt(emberNev.substring(nev.length()));
                        vanIlyenNev = true;
                        if (ujNevIndex <= nevIndex) {
                            ujNevIndex = nevIndex + 1;
                        }
                    } catch (NumberFormatException e) {
                        // csak hasonló név volt, de nem ennek a verziója
                    }
                }
            }
        }
        if (vanIlyenNev) {
            return nev + ujNevIndex;
        } else {
            return nev;
        }
    }

    private String azonositoEllenor(String azonosito) //ellenőrzi, hogy van-e már ilyen név, és ha igen, akkor újat ad
    {
        boolean vanIlyenAzonosito = false;
        int ujAzonositoIndex = 0;
        // ha van ilyen név, akkor újat generálunk a feladat szabályai szerint.
        for (int i = 0; i < emberLista.size(); i++) {
            String emberAzonosito = emberLista.get(i).getAzonosito();
            if (emberAzonosito.startsWith(azonosito)) {
                if (emberAzonosito.equals(azonosito)) {
                    if (ujAzonositoIndex == 0) {
                        ujAzonositoIndex = 1;
                    }
                    vanIlyenAzonosito = true;
                } else {
                    try {
                        int azonositoIndex = Integer.parseInt(emberAzonosito.substring(azonosito.length()));
                        vanIlyenAzonosito = true;
                        if (ujAzonositoIndex <= azonositoIndex) {
                            ujAzonositoIndex = azonositoIndex + 1;
                        }
                    } catch (NumberFormatException e) {
                        // csak hasonló név volt, de nem ennek a verziója
                    }
                }
            }
        }
        if (vanIlyenAzonosito) {
            return azonosito + ujAzonositoIndex;
        } else {
            return azonosito;
        }
    }

    public String addNewEmber(int emberTipusIndex, String nev, String azonosito, String targy,
            String osztaly, String tanulmanyiAtlag) {
        String valasz = "";
        if (nev == null) {
            return "A név megadása kötelező!";
        } else if (nev.equals("")) {
            return "A név megadása kötelező!";
        }
        if (azonosito == null) {
            return "Az azonosító megadása kötelező!";
        } else if (azonosito.equals("")) {
            return "Az azonosító megadása kötelező!";
        }
        nev = nevEllenor(nev);
        azonosito = azonositoEllenor(azonosito);
        boolean vanIlyenAzonosito = false;
        if (emberTipusIndex == 0) { // Tanárról van szó
            if (targy == null) {
                valasz = "A tantárgyat ki kellene tölteni!\n";
                targy = "";
            }

            if (targy.equals("")) {
                if (valasz == null) {
                    valasz = "A tantárgyat ki kellene tölteni!\n";
                }
            }

            Tanar t = new Tanar(nev, azonosito, targy);
            emberLista.add(t);
            valasz = valasz + t.toString() + " a listához hozzá lett adva!";
        }
        if (emberTipusIndex == 1) { // Diákról van szó
            float atlag = getEllenorzottAtlag(tanulmanyiAtlag);
            if (atlag < 1f) {
                return "A tanulmányi átlag mezőben egy 1-5 tartomyánban levő szám szerepeljen!";
            }
            if (osztaly == null) {
                valasz = "Az osztályt ki kellene tölteni!\n";
                osztaly = "";
            }
            if (osztaly.equals("")) {
                if (valasz == null) {
                    valasz = "Az osztályt ki kellene tölteni!\n";
                }
            }
            Diak d = new Diak(nev, azonosito, osztaly, atlag);
            emberLista.add(d);
            valasz = valasz + d.toString() + " a listához hozzá lett adva!";
        }
        return valasz;
    }

    private Ember getEmberByAzonosito(String azonosito) {
        Ember ember = null;
        for (int i = 0; i < emberLista.size(); i++) {
            ember = emberLista.get(i);
            if (ember.getAzonosito().equals(azonosito)) {
                return ember;
            }
        }
        return ember;
    }

    private float getEllenorzottAtlag(String tanulmanyiAtlag) {
        if (tanulmanyiAtlag == null) {
            return -2f;
        }
        float atlag;
        try {
            atlag = Float.parseFloat(tanulmanyiAtlag);
        } catch (NumberFormatException e) {
            return 0;
        }
        if ((atlag > 5f) || (atlag < 1f)) {
            return -1f;
        }
        return atlag;

    }

    public String modifyEmber(String azonosito, String nev, String targy,
            String osztaly, String tanulmanyiAtlag) {
        // ellenőrízzük, hogy az azonosító létezik-e a listában.
        boolean talalt = false;
        for (int i = 0; i < emberLista.size(); i++) {
            if (emberLista.get(i).getAzonosito().equals(azonosito)) {
                talalt = true;
                break;
            }
        }
        if (!talalt) {
            return "A megadott azonosítóhoz nem találtunk elemet!";
        }
        if (nev.equals("")) {
            return "A név kitöltése kötelező";
        }
        String valasz = "";
        Ember ember = getEmberByAzonosito(azonosito);
        if (ember == null) {
            return azonosito + " azonosítójú ember nem létezik a listában!";
        }
        if (getRealClass(ember).equals("Tanár")) {
            Tanar tanar = (Tanar) ember;
            valasz = "Módosítás történt " + tanar.toString() + "-ról";
            tanar.setNev(nev);
            tanar.setTargy(targy);
            valasz = valasz + " " + tanar.toString() + "-ra.";
        } else {
            Diak diak = (Diak) ember;
            float atlag = getEllenorzottAtlag(tanulmanyiAtlag);
            if (atlag < 1f) {
                return "A tanulmányi átlag mezőben egy 1-5 tartomyánban levő szám szerepeljen!";
            }
            valasz = "Módosítás történt " + diak.toString() + "-ról";
            diak.setNev(nev);
            diak.setOsztaly(osztaly);
            diak.setAtlag(atlag);
            valasz = valasz + " " + diak.toString() + "-ra.";
        }
        return valasz;
    }

    public Diak[] top3Diak(String osztaly) {
        ArrayList<Diak> osztalyLista = new ArrayList<>();
        for (int i = 0; i < emberLista.size(); i++) {
            Ember ember = emberLista.get(i);
            if (getRealClass(ember).equals("Diák")) {
                Diak diak = (Diak) ember;
                if (diak.getOsztaly().equals(osztaly)) {
                    osztalyLista.add(diak);
                }
            }
            Collections.sort(osztalyLista, new AtlagComparator());
        }
        Diak[] vissza = {null, null, null};
        for (int i = 0; i < osztalyLista.size(); i++) {
            if (i < 3) {
                vissza[i] = osztalyLista.get(i);
            }
        }
        return vissza;
    }

    public String saveTop3(String osztaly, File savePath) {
        Diak[] top3 = top3Diak(osztaly);
        try {
            FileOutputStream f = new FileOutputStream(savePath);
            ObjectOutputStream o = new ObjectOutputStream(f);
            for (int i = 0; i < 3; i++) {
                if (top3[i] != null) {
                    o.writeObject(top3[i]);
                }
            }
            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            return ("File not found");
        } catch (IOException e) {
            return ("Error initializing stream");
        }
        return savePath.getName() + " elmentve.";
    }

    public Diak[] loadTop3(File fajl) {
        Diak[] top3 = {null, null, null};
        try {
            FileInputStream fi = new FileInputStream(fajl);
            ObjectInputStream oi = new ObjectInputStream(fi);
            top3[0] = (Diak) oi.readObject();
            if (top3[0] != null) {
                top3[1] = (Diak) oi.readObject();
            }
            if (top3[1] != null) {
                top3[2] = (Diak) oi.readObject();
            }
            oi.close();
            fi.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return top3;
    }

    public int listaMeret() {
        return emberLista.size();
    }

    public int getEmberTipusIndex(String emberTipus) {
        for (int i = 0; i < emberTipusok.length; i++) {
            if (emberTipusok[i].equals(emberTipus)) {
                return i;
            }
        }
        return -1;
    }

}
