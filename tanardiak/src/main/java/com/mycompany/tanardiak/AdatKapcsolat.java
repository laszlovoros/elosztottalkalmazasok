package com.mycompany.tanardiak;

import java.util.Vector;

/**
 *
 * @author Kriszti
 * AdatSzervizSzolgaltato interfész implementálása
 * a load,save,top3 funkciók nem működnek, de a GUI-t el lehet kezdeni fejleszteni ez alapján
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
        String valasz = null;
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
            float atlag;
            try {
                atlag = Float.parseFloat(tanulmanyiAtlag);
            } catch (NumberFormatException e) {
                return "A tanulmányi átlag kitöltése kötelező!";
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

    public String modifyEmber(int emberTipusIndex, String nev, String azonosito, String targy,
            String osztaly, String atlag) {
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
        return valasz;
    }

    public String[] top3Diak(String osztaly) {
        String[] vissza = {"", "", ""};
        return vissza;
    }

    public String saveTop3(String osztaly) {
        return "";
    }

    public String[] loadTop3(String fajlNev) {
        String[] vissza = {"", "", ""};
        return vissza;
    }
}
