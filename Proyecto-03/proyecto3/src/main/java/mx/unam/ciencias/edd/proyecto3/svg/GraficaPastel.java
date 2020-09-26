package main.java.mx.unam.ciencias.edd.proyecto3.svg;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.proyecto3.svg.EstructuraSVG;
import mx.unam.ciencias.edd.Lista;

public class GraficaPastel extends EstructuraSVG{
    @Override
    public String toSVG(Coleccion<Integer> coleccion){  
        return "";
    }
    /**
     * Metodo que dibuja las barras
     * @param cx - coordenada x
     * @param cy - coordenada y
     * @param r - radio
     * @param fill - fill
     * @param stroke - stroke
     * @param valor - valor barra
     * @param valorAnterior - valores anteriores
     * @return - circulos de las barras
     */
    public String dibujarBarrasFloat(int cx, int cy, int r, String stroke, float valor, double valorAnterior){
        String vertice = "<circle r = " + '"' + r  + "%" + '"' + " cx = " + '"' + cx  + "%" + '"' + " cy = " + '"' + cy  + "%" + '"' + " style =" + '"' + "stroke-dasharray: " + valor + " 100" +
        " ;stroke: " + stroke  + "; stroke-dashoffset: " + -valorAnterior + "; animation-delay: 0.25s" + '"' + ">" + "\n";
        vertice += "</circle>";
        return vertice + "\n";
    }

    /**
     * Metodo que genera la grafica Pastel
     * @param coleccion - lista a usar
     * @return - grafica Pastel en SVG
     */
    public String toSVGFloat(Lista<Float> coleccion){
        String[] colores = {"#FF8C00","#B0E0E6","#008080","#800000","#FF69B4","#efc050", "#5b5ea6", "#9b2335", "#55b4b0", "#6b5b95", "#88b04b", "#f7cac9", "#955251", "#2a4b7c", "#264e36", "#f06"};
        String res = "<svg viewBox=" + '"' + "0 0 64 64" + '"' + " class=" + '"' + "pie" + '"' + ">" + "\n";
        double div = 0.0;
        int i = 0;
        if(coleccion.getElementos() > 15){
            for(int j = 0; j < coleccion.getElementos() - 1; j++){
                res += dibujarBarrasFloat(50, 50, 25, colores[i], coleccion.get(j), div) + "\n";
                i++;
                div += coleccion.get(j);
            }
        }else{
            for(int j = 0; j < coleccion.getElementos() - 1; j++){
                res += dibujarBarrasFloat(50, 50, 25, colores[i], coleccion.get(j), div) + "\n";
                i++;
                div += coleccion.get(j);
            }
            res += dibujarBarrasFloat(50, 50, 25, "#f06", coleccion.getUltimo(), div) + "\n";
            div += coleccion.getUltimo();
        }

        return res + "</svg>";
        
    }

}