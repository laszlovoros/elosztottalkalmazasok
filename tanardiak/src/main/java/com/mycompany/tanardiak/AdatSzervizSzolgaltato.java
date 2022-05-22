/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tanardiak;

import java.util.Vector;

/**
 *
 * @author Laci
 * Ez az interfész írja le, hogy miket kell csinálnia az adatmodellt kezelő
 * osztálynak. A GUI ezeket a függvényeket fogja hívogatni a működése közben.
 */

public interface AdatSzervizSzolgaltato {
    public String[] getEmberTipusok(); // visszaad egy kételemű tömböt az embertipusokkal (Tanár, Diák)
    
    public Vector <Ember> getEmberLista(); // visszaad egy Vectort, amit a GUI a JListhez csatolhat. Ebben lesznek az adatok.
    
    public Ember getEmberBySorszam(int sorszam); // Átadja a lista sorszam pozíción levő elemét
    
    public String getRealClass(Ember objektum); // Visszaadja, hogy az Ember az Tanar vagy Diak
    
    public int getEmberTipus(Ember objektum); // Visszaadja hogy az Ember az emberTipusok tömbben melyik
    
    public String deleteByAzonosito(String azonosito); /* Törli a lista azon elemét, amelyiknek 
    az azonosítóját megadtuk. Visszaad egy üzenetet a törlés sikerességéről;*/
    
    public String addNewEmber(int emberTipusIndex,String nev,String azonosito,String targy,
            String osztaly,String atlag);
    /*Létrehoz egy uj Tanar vagy Diak objektumot a megadott adatok alapján, és berakja az emberListába
    Ellenőrzéseket végez, ha hiba van, akkor hibaüzenetet ad vissza, és nem hozza létre az objektumot.
    Ha már létezik a név, azonosító, akkor generál egy olyat, ami az eredeti, de mellérak egy sorszámot.
    Azok a paraméterek, amelyek nem relevánsak, lehetnek "" értékűek, vagy null-ok is.
    (pld. Tanár esetében az átlag nem releváns) */
    
    public String modifyEmber(String nev,String azonosito,String targy,
            String osztaly,String atlag);
    /*Módosít egy meglevő Tanar vagy Diak objektumot a megadott adatok alapján
    Ellenőrzéseket végez, ha hiba van, akkor hibaüzenetet ad vissza, és nem módosítja az objektumot.
    Ha már létezik a név, azonosító, akkor generál egy olyat, ami az eredeti, de mellérak egy sorszámot.
    Azok a paraméterek, amelyek nem relevánsak, lehetnek "" értékűek, vagy null-ok is.
    (pld. Tanár esetében az átlag nem releváns) */
    
    public Diak[] top3Diak(String osztaly); // visszaadja az adott osztály 3 legjobb diákját
    
    public String saveTop3(String osztaly); // lementi a megadott osztály 3 legjobb diákját. Sikerességről
    // üzenetet ad vissza
    
    public Diak[] loadTop3(String fajlNev); //Megadott fajlnevet betölti, és a benne levő 3 diákot 
    //ArrayList-ben visszaadja
    
    public int listaMeret(); // Hány ember van a listánkban?
    
    public int getEmberTipusIndex(String emberTipus); // Visszaadja, hogy az adott embertípus a tipusok
    // tömbjében hányadik. (pld. "Tanár" esetén 0.)
}
