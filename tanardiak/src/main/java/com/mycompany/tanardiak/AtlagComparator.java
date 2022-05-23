package com.mycompany.tanardiak;

/**
 *
 * @author Kriszti
 */
import java.text.Collator;
import java.util.Comparator;

public class AtlagComparator implements Comparator<Diak> {

    public int compare(Diak egyik, Diak masik) {
        if (egyik.getAtlag() < masik.getAtlag()) {
            return 1;
        }
        if (egyik.getAtlag() == masik.getAtlag()) {
            return 0;
        }
        return -1;
    }

}
