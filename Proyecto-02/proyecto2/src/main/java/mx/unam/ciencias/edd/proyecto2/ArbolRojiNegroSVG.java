package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.VerticeArbolBinario;
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
            width = width(r, tree.altura() + 1);
        }else{
            width = width(2*r, tree.altura());
        }
        svg += vertices(tree.raiz(),  width/2 , 175, tree, width/2);
        svg += "\n" + dibujarTexto(100 + width/2, 140, "RAIZ") + "\n";
        svg += "\n" + "</svg>";
        return svg;
    }
    @Override
    public String vertices(VerticeArbolBinario vertice, int cx, int cy, ArbolBinario<Integer> arbol, int indicador){
        String s = "";
        // Sirve para tener un espacio considerable entre vértice y vértice
        int r = setRadio(arbol);
        int posY = r + cy + + 80;
        if(vertice.hayIzquierdo()){
            s += ABRE_ETIQUETA + "\n";
            s += dibujaLineas(cx, cy, (cx - indicador/2), posY, 4);
            s += CIERRA_ETIQUETA + "\n";
            s += vertices(vertice.izquierdo(), (cx - indicador/2), posY, arbol, indicador/2);

        }
        if(vertice.hayDerecho()){
            s += ABRE_ETIQUETA + "\n";
            s += dibujaLineas(cx, cy, (cx + indicador/2), posY, 4);
            s += CIERRA_ETIQUETA + "\n";
      
            s += vertices(vertice.derecho(), (cx + indicador/2), posY, arbol, indicador/2);

        }
        s += dibujarCirculos(cx, cy, r, getColor(vertice));
        s += dibujarTexto(cx - 5, cy + 3, splitString(vertice));
        return s;
    }
    public String setDimensiones(ArbolBinario<Integer> arbol){
        int width = arbol.altura() * arbol.altura();
        width = width * 145;
        width = width + 230;
        if(arbol.altura() > 7){
            width *= 3;
        }
        return APERTURA + "\n" + "<svg height=" + '"' + arbol.getElementos()*100+ '"' + " width=" + '"' + width*2 + '"' + ">" + "\n";
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
}