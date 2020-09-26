package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Coleccion;

/**
 * Clase para construir una cola
 */
public class ColaSVG extends EstructuraSVG{
    public String toSVG(Coleccion<Integer> coleccion){
        Cola<Integer> cola = new Cola<>();
        for(Integer n : coleccion){
            cola.mete(n);
        }
        String svg = "";
        int x = 50, y = 60;
        while(!cola.esVacia()){
            int elemento = cola.saca();
            svg += dibujarTexto(x, y, String.valueOf(elemento));
            x += 30;
        }  
        String rectangulo = dibujarRectangulo(45, 20, 5, 5, 60, x, 2, "white", "black") + CIERRA_ETIQUETA + "\n";
        String canvas = "<svg height=" + '"' + 800 + '"' + " width=" + '"' + x+30 + '"' + ">" + "\n";
        return APERTURA + "\n" + canvas + rectangulo + svg + "\n" + "</svg>";
    }
}