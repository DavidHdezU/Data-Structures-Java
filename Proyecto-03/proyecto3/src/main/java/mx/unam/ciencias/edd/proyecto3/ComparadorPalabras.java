package main.java.mx.unam.ciencias.edd.proyecto3;
import main.java.mx.unam.ciencias.edd.proyecto3.Palabra;
public class ComparadorPalabras implements Comparable<ComparadorPalabras>{
    private Palabra p;
    /**
     * Constructor comun
     * @param p - palabra a comparar
     */
    public ComparadorPalabras(Palabra p){
        this.p = p;
    }

    @Override
    public int compareTo(ComparadorPalabras comp){
        return this.p.cont - comp.p.cont;
    }

    @Override 
    public String toString(){
        return this.p.toString();
    }
}