
package com.mycompany.tanardiak;

/**
 *
 * @author Kriszti
 * Azonosítók összehasonlítása, Comparator interface segítségével
 */
import java.text.Collator;
import java.util.Comparator;
    
    public class AzonositoComparator implements Comparator<Ember>{
    public int compare(Ember egyik,Ember masik){
        Collator col=Collator.getInstance();
        return col.compare(egyik.getAzonosito(), masik.getAzonosito());
    }
    public AzonositoComparator(){
    
    }
    

}
