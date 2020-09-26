package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Lista;
/**
 * Clase para construir un arreglo
 */
public class ArregloSVG extends EstructuraSVG{
    @Override
    public String toSVG(Coleccion<Integer> lista){
         int x1 = 50, y1 = 30;
         String res = "";
        for(Integer elemento : lista){
                res += dibujarRectangulo(x1, y1, 0, 0, 35, 45, 3, "orange", "black");
                x1 += 27;
                res += dibujarTexto(x1-13, 55, String.valueOf(elemento));
                x1 += 20;           
        }
        int width = lista.getElementos()*125;
        String canvas = "<svg height=" + '"' + 800 + '"' + " width=" + '"' + width + '"' + ">" + "\n";
        return APERTURA + canvas + res + "\n" + "</svg>";   
    }
}