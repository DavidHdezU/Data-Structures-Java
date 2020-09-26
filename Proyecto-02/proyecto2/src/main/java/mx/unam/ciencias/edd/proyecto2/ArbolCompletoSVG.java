package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.VerticeArbolBinario;
/**
 * Clase para construir un árbol completo
 */
public class ArbolCompletoSVG extends ArbolSVG{
    @Override
    public String toSVG(Coleccion<Integer> arbol){
        ArbolBinarioCompleto<Integer> tree = new ArbolBinarioCompleto<>(arbol);
        int r = setRadio(tree);
        String svg = setDimensiones(tree);
        int width = width(2*r, tree.altura());
        svg += vertices(tree.raiz(), 100 + width/2, 175, tree, width/2);
        svg += "\n" + "</svg>";
        return svg;
    }
    @Override
    public String vertices(VerticeArbolBinario vertice, int cx, int cy, ArbolBinario<Integer> arbol, int indicador){
        String s = "";
        // Sirve para tener un espacio considerable entre vértice y vértice
        int r = setRadio(arbol);
        int posY = r + cy + 40;
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
        s += dibujarCirculos(cx, cy, r, "green");
        s += dibujarTexto(cx - 5, cy + 3, vertice.toString());
        return s;
    }
    @Override
    public String setDimensiones(ArbolBinario<Integer> arbol){
        int width = arbol.altura()*arbol.altura();
        width = width * 145;
        width = width + 230;
        if(arbol.altura() > 10){
            width *= 3;
        }
        return APERTURA + "\n" + "<svg height=" + '"' + (arbol.altura()*arbol.altura())*150+ '"' + " width=" + '"' + width*1.5 + '"' + ">" + "\n";
    }
}