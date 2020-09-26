package mx.unam.ciencias.edd.proyecto3.svg;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import main.java.mx.unam.ciencias.edd.proyecto3.ComparadorPalabras;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolRojinegro;
/**
 * Clase para construir un árbol rojinegro
 */
public class ArbolRojiNegroSVG extends ArbolSVG{
    @Override
    public String toSVG(Coleccion<Integer> arbol){
        ArbolRojinegro<Integer> tree = new ArbolRojinegro<>(arbol);
        int r = setRadio(tree);
        String svg = setDimensiones(tree);
        int width;
        // Para que no se pierdan las proporciones 
        if(tree.altura() <= 5){
            width = width(r/2, tree.altura() + 1);
        }else if(tree.altura() <= 3){
            width = width(r/3, tree.altura());
        }
        else{
            width = width(2*r, tree.altura());
        }
        svg += vertices(tree.raiz(),  4*width/7 , 35, tree, width/2);
        svg += "\n" + "</svg>";
        return svg;
    }
    @Override
    public String vertices(VerticeArbolBinario vertice, int cx, int cy, ArbolBinario<Integer> arbol, int indicador){
        String[] colores = {"#FF8C00","#B0E0E6","#008080","#800000","#FF69B4","#efc050", "#5b5ea6", "#9b2335", "#55b4b0", "#6b5b95", "#88b04b", "#f7cac9", "#955251", "#2a4b7c", "#264e36", "#f06"};
        String s = "";
        int i = 0;
        // Sirve para tener un espacio considerable entre vértice y vértice
        int r = setRadio(arbol);
        int posY = r + cy + + 80;
        if(vertice.hayIzquierdo()){
            s += ABRE_ETIQUETA + "\n";
            s += dibujaLineas(cx, cy, (cx - indicador/2), posY, 4);
            s += CIERRA_ETIQUETA + "\n";
            s += vertices(vertice.izquierdo(), (cx - indicador/2), posY, arbol, indicador/2);
            i++;
        }
        if(vertice.hayDerecho()){
            s += ABRE_ETIQUETA + "\n";
            s += dibujaLineas(cx, cy, (cx + indicador/2), posY, 4);
            s += CIERRA_ETIQUETA + "\n";
            i++;
            s += vertices(vertice.derecho(), (cx + indicador/2), posY, arbol, indicador/2);
        }
        s += dibujarCirculos(cx, cy, r, getColor(vertice));
        s += dibujarTextoConColor(cx - 5, cy + 3, splitString(vertice), colores[i]);
        return s;
    }
    public String setDimensiones(ArbolBinario<Integer> arbol){
        int width = arbol.altura() * arbol.altura();
        width = width * 145;
        width = width + 230;
        int height = arbol.getElementos()*45;
        if(arbol.altura() > 7){
            width *= 3;
        }
        if(arbol.altura() < 5){
            height *= 2;
        }
        return APERTURA + "\n" + "<svg height=" + '"' + height + '"' + " width=" + '"' + width/2 + '"' + ">" + "\n";
    }
    /**
     * Obtiene el color del vértice
     * @param vertice - vértice a usar
     * @return - String "black" o "red" dependiendo del color del vértice
     */
    public String getColor(VerticeArbolBinario vertice){
        String color = "";
        if(vertice.toString().contains("R")){
            color = "red";
        }else{
            color =  "black";
        }
        return color;
    }
    /**
     * Obtiene el elemento del vértice
     * @param vertice
     * @return
     */
    public String splitString(VerticeArbolBinario vertice){
        String res = vertice.toString();
        res = res.replace("{", "");
        res = res.replace("}", "");
        res = res.replace("R", "");
        res = res.replace("N", "");
        return res;
    }

    public String verticesString(VerticeArbolBinario vertice, int cx, int cy, ArbolBinario<ComparadorPalabras> arbol, int indicador){
        String s = "";
        // Sirve para tener un espacio considerable entre vértice y vértice
        int r = setRadioString(arbol);
        int posY = r + cy + + 80;
        if(vertice.hayIzquierdo()){
            s += ABRE_ETIQUETA + "\n";
            s += dibujaLineas(cx, cy, (cx - indicador/2), posY, 4);
            s += CIERRA_ETIQUETA + "\n";
            s += verticesString(vertice.izquierdo(), (cx - indicador/2), posY, arbol, indicador/2);

        }
        if(vertice.hayDerecho()){
            s += ABRE_ETIQUETA + "\n";
            s += dibujaLineas(cx, cy, (cx + indicador/2), posY, 4);
            s += CIERRA_ETIQUETA + "\n";
      
            s += verticesString(vertice.derecho(), (cx + indicador/2), posY, arbol, indicador/2);

        }
        s += dibujarCirculos(cx, cy, r, getColor(vertice));
        s += dibujarTexto(cx - 14, cy + 3, splitString(vertice));
        return s;
    }
    public String setDimensionesString(ArbolBinario<ComparadorPalabras> arbol){
        int width = arbol.altura() * arbol.altura();
        width = width * 145;
        width = width + 230;
        int height = arbol.getElementos()*45;
        if(arbol.altura() > 7){
            width *= 3;
        }
        if(arbol.altura() < 5){
            height *= 1.2;
        }
        return APERTURA + "\n" + "<svg height=" + '"' + height + '"' + " width=" + '"' + width + '"' + ">" + "\n";
    }

    public String toSVGString(Coleccion<ComparadorPalabras> arbol){
        ArbolRojinegro<ComparadorPalabras> tree = new ArbolRojinegro<>(arbol);
        int r = setRadioString(tree);
        String svg = setDimensionesString(tree);
        int width;
        // Para que no se pierdan las proporciones 
        if(tree.altura() <= 5){
            width = width(3*r/2, tree.altura() + 1);
        }else if(tree.altura() <= 3){
            width = width(r/3, tree.altura());
        }
        else{
            width = width(2*r, tree.altura());
        }
        svg += verticesString(tree.raiz(),  4*width/7 , 35, tree, width/2);
        svg += "\n" + "</svg>";
        return svg;
    }
}
