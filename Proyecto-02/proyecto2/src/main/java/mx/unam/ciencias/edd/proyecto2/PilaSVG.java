package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Pila;

/**
 * Clase que construye una pila
 */
public class PilaSVG extends EstructuraSVG{
    private static String APERTURA = "<?xml version='1.0' encoding='UTF-8' ?>\n";
    protected static String ABRE_ETIQUETA = "<g>";
    private static String CIERRA_ETIQUETA = "</g>";

    @Override
    public String toSVG(Coleccion<Integer> coleccion){
        Pila<Integer> pila = new Pila<>();
        for(Integer n : coleccion){
            pila.mete(n);
        }
        String svg = "";
        int x = 50, y = 60;
        while(!pila.esVacia()){
            int elemento = pila.saca();
            svg += dibujarTexto(x, y, String.valueOf(elemento));
            y += 30;
            
        }
        String rectangulo = dibujarRectangulo(40, 20, 5, 5, y, 60, 2, "white", "black") + CIERRA_ETIQUETA + "\n";
        String canvas = "<svg height=" + '"' + y+30 + '"' + " width=" + '"' + 800 + '"' + ">" + "\n";
        return APERTURA + "\n" + canvas + rectangulo + svg + "\n" + "</svg>";
    }
}