package main.java.mx.unam.ciencias.edd.proyecto3;
public class Palabra implements Comparable<Palabra>{
    public String palabra;
    public int cont;
    public float porcentaje;

    /**
     * Constructor comun
     * @param palabra - palabra
     * @param cont - numero de apariciones
     * @param porcentaje - porcentaje de la palabra
     */
    public Palabra(String palabra, int cont, float porcentaje){
        this.palabra = palabra;
        this.cont = cont;
        this.porcentaje = porcentaje;
    }
    @Override
    public int compareTo(Palabra palabra){
        return palabra.cont - this.cont;
    }
    @Override
    public String toString(){
        return this.palabra;
    }
}