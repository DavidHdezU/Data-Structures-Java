package mx.unam.ciencias.edd.proyecto1;

import java.text.Normalizer;

/**
 * Clase que modela una linea de un archivo
 */
public class CadenaP1 implements Comparable<CadenaP1> {
    private String string;

    /**
     * Constructor general
     * 
     * @param string - Cadenas
     */
    public CadenaP1(String string) {
        this.string = string;
    }

    /**
     * Compara 2 CadenasP1 lexiográficamente
     * 
     * @param cp - Cadena a comparar
     */
    @Override
    public int compareTo(CadenaP1 cp) {
        String s = Normalizer.normalize(this.string, Normalizer.Form.NFD);
        s = this.string.replaceAll("[^a-zA-Z0-9_-]", "");
        s = s.replace(" ", "");
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        String t = Normalizer.normalize(cp.toString(), Normalizer.Form.NFD);
        t = t.replaceAll("[^a-zA-Z0-9_-]", "");
        t = t.replace(" ", "");
        t = t.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s.toLowerCase().compareToIgnoreCase(t.toLowerCase());
    }

    /**
     * Representación en cadena de una CadenaP1
     */
    @Override
    public String toString() {
        return this.string;
    }
}