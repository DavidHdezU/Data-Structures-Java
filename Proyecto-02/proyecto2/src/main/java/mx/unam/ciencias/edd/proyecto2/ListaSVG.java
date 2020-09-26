package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.MeteSaca;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase para construir un Lista
 */
public class ListaSVG extends EstructuraSVG{
    @Override
    public String toSVG(Coleccion<Integer> lista){
        String res = "";
         int x1 = 10, y1 = 30, x2 = 30, y2 = 50;
        for(Integer elemento : lista){
                res += dibujarRectangulo(x1, y1, 10, 10, 35, 45, 3, "orange", "black");
                x1 += 65;
                x2 += 65;
                res += dibujarTexto(x1-50, 55, String.valueOf(elemento));
                x1 += 10;
                x2 += 10;
                res += dibujarFlechas(x1-19, y2, x2-19, y2);
                x1 += 10;
                x2 += 10;
                
        }
        String ultimaFlecha = dibujarFlechas(x1 - 10 - 19, y2, x2 - 10 - 19, y2);
        res = res.replace(ultimaFlecha, "");
        int width = lista.getElementos()*125;
        String canvas = "<svg height=" + '"' + 800 + '"' + " width=" + '"' + width + '"' + ">" + "\n";
        String marker = marcadorFlechas() + "\n";
        return canvas + marker + res + CIERRA_ETIQUETA + "\n" + "</svg>";

    }
    /**
     * Crea un marcador para poder visualizar las flechas
     * @return String - marcador de flechas
     */
    public String marcadorFlechas(){
        String marcador =  "<marker id=" + '"' + "triangle" + '"' + "\n" +
        "viewBox=" + '"' + "0 0 10 10" + '"' + " refX=" + '"' + "0" + '"' + 
        " refY=" + '"' + "5" + '"' + "\n" +
        "markerUnits=" + '"' + "strokeWidth" + '"' + "\n" + 
        "markerWidth=" + '"' + "4" + '"' + " markerHeight=" + '"' + "3" + '"' + "\n" + 
        "orient=" + '"' + "auto" + '"' + ">" + "\n" + 
        "<path d=" + '"' + "M 0 0 L 10 5 L 0 10 z" + '"' + "/>" + "\n" + 
        "</marker>" + "\n";
        return marcador;

    }
    /**
     * Dibuja las flechas
     * @param x1 - coordenada x1
     * @param y1 - coordenada y1
     * @param x2 - coordenada x2 
     * @param y2 - coordenada y2
     * @return String - cadena svg de las flechas
     */
    public String dibujarFlechas(int x1, int y1, int x2, int y2){
        String flecha1 = "<line x1=" + '"' + x1 + '"' + " y1=" + '"' + y1 + '"' + " x2=" + '"' + x2 + '"' + " y2=" + '"' + y2 + '"' + 
        " marker-end=" + '"' + "url(#triangle)" + '"' + " stroke=" + '"' + "black" + '"' + " stroke-width=" + '"' + "2" + '"' + "/>" + "\n";
        String flecha2 = "<line x1=" + '"' + x2 + '"' + " y1=" + '"' + y1 + '"' + " x2=" + '"' + x1 + '"' + " y2=" + '"' + y2 + '"' + 
        " marker-end=" + '"' + "url(#triangle)" + '"' + " stroke=" + '"' + "black" + '"' + " stroke-width=" + '"' + "2" + '"' + "/>" + "\n";
        return flecha1 + flecha2 + CIERRA_ETIQUETA + "\n";
    }
}


