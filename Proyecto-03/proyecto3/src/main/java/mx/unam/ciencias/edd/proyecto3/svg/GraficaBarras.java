package main.java.mx.unam.ciencias.edd.proyecto3.svg;

import java.text.DecimalFormat;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.proyecto3.svg.EstructuraSVG;
import mx.unam.ciencias.edd.Lista;

public class GraficaBarras extends EstructuraSVG {
    @Override
    public String toSVG(Coleccion<Integer> coleccion){
        return "";
    }
    /**
     * Metodo que genera la grafica de barras
     * @param lista - lista a usar
     * @return - grafica de barras en SVG
     */
    public String toSVGFloat(Lista<Float> lista){
        String[] colores = {"#FF8C00","#B0E0E6","#008080","#800000","#FF69B4","#efc050", "#5b5ea6", "#9b2335", "#55b4b0", "#6b5b95", "#88b04b", "#f7cac9", "#955251", "#2a4b7c", "#264e36", "#f06"};
        String svg = "<svg width=" + '"' + "420" + '"' + " height=" + '"' + "500" + '"' + " font-family=" + '"' + "sans-serif" + '"' + " font-size=" + '"' + "10" + '"' + " text-anchor=" + '"' + "end" + '"' + ">" + "\n";
        int deltaY = 0;
        int i = 0;
        if(lista.getLongitud() >= 15){
            for(Float p : lista){
                float text = (p*10) +29;
                String textFormat = String.format("%.2f", p);
                svg += "\t<g transform=" + '"' + "translate(0," + deltaY + ")" + '"' + ">" + "\n";
                svg += "\t\t<rect fill=" + '"' + colores[i] + '"' +  " width=" + '"' + p*10 + '"' + " height=" + '"' + "25" + '"' + "></rect>" + "\n";
                svg += "\t\t<text fill=" + '"' + "white" + '"' + " x=" + '"' + text + '"' +  " y=" + '"' + "9.5" + '"' + " dy=" + '"' + ".35em" + '"' + ">" + textFormat  + "%" + "</text>" + "\n";
                svg += "\t</g>" + "\n";
                i++;
                deltaY += 30;
            }
        }else{
            for(int j = 0; j < lista.getLongitud()-1; j++){
                Float p = lista.get(j);
                float text = (p*10) +29;
                String textFormat = String.format("%.2f", p);
                svg += "\t<g transform=" + '"' + "translate(0," + deltaY + ")" + '"' + ">" + "\n";
                svg += "\t\t<rect fill=" + '"' + colores[i] + '"' +  " width=" + '"' + p*10 + '"' + " height=" + '"' + "25" + '"' + "></rect>" + "\n";
                svg += "\t\t<text fill=" + '"' + "white" + '"' + " x=" + '"' + text + '"' +  " y=" + '"' + "9.5" + '"' + " dy=" + '"' + ".35em" + '"' + ">" + textFormat  + "%" + "</text>" + "\n";
                svg += "\t</g>" + "\n";
                i++;
                deltaY += 30;
            }
            Float p = lista.getUltimo();
            String textFormat = String.format("%.2f", p);
            float text = (p*10) +29;
            svg += "\t<g transform=" + '"' + "translate(0," + deltaY + ")" + '"' + ">" + "\n";
            svg += "\t\t<rect fill=" + '"' + "#f06" + '"' +  " width=" + '"' + p*10 + '"' + " height=" + '"' + "25" + '"' + "></rect>" + "\n";
            svg += "\t\t<text fill=" + '"' + "white" + '"' + " x=" + '"' + text + '"' +  " y=" + '"' + "9.5" + '"' + " dy=" + '"' + ".35em" + '"' + ">" + textFormat + "%" +  "</text>" + "\n";
            svg += "\t</g>" + "\n";
            deltaY += 30;
        }
        return svg + "</svg>" + "\n";
    }
}