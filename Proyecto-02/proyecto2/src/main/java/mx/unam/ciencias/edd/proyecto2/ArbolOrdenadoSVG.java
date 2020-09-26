package mx.unam.ciencias.edd.proyecto2;


import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.VerticeArbolBinario;
/**
 * Clase para construir un árbol ordenado
 */
public class ArbolOrdenadoSVG extends ArbolSVG{
    @Override
    public String toSVG(Coleccion<Integer> arbol){
        ArbolBinarioOrdenado<Integer> tree = new ArbolBinarioOrdenado<>(arbol);
        int r = setRadio(tree);
        String svg = setDimensiones(tree);
        // Mantiene las proporciones
        int n = tree.altura();
        if(tree.altura() > 5 && tree.getElementos() < 10){
            n = tree.altura()/2;
        }
        int width = width(3*r/2, n+2);
        svg += vertices(tree.raiz(), 100 + width/2 , 175, tree, width/2);
        svg += "\n" + dibujarTexto(100 + width/2, 140, "RAIZ") + "\n";
        svg += "\n" + "</svg>";
        return svg;
    }
    @Override
    public String vertices(VerticeArbolBinario vertice, int cx, int cy, ArbolBinario<Integer> arbol, int indicador){
        String s = "";
        // Sirve para tener un espacio considerable entre vértice y vértice
        int r = setRadio(arbol);
        int posY = r + cy + 80;
        if(vertice.hayIzquierdo()){
            s += ABRE_ETIQUETA + "\n";
            if(arbol.altura() > 5){
                s += dibujaLineas(cx, cy, (cx - indicador/6), posY, 4);
                s += CIERRA_ETIQUETA + "\n";

                s += vertices(vertice.izquierdo(), (cx - indicador/6), posY, arbol, indicador/2);
            }else{
                s += dibujaLineas(cx, cy, (cx - indicador/2), posY, 4);
                s += CIERRA_ETIQUETA + "\n";
                s += vertices(vertice.izquierdo(), (cx - indicador/2), posY, arbol, indicador/2);
            }
        }
        if(vertice.hayDerecho()){
            s += ABRE_ETIQUETA + "\n";
            if(arbol.altura() > 5){
                s += dibujaLineas(cx, cy, (cx + indicador/6), posY, 4);
                s += CIERRA_ETIQUETA + "\n";
                s += vertices(vertice.derecho(), (cx + indicador/6), posY, arbol, indicador/2);
            }else{
                s += dibujaLineas(cx, cy, (cx + indicador/2), posY, 4);
                s += CIERRA_ETIQUETA + "\n";
        
                s += vertices(vertice.derecho(), (cx + indicador/2), posY, arbol, indicador/2);
            }

        }
        s += dibujarCirculos(cx, cy, r, "green");
        s += dibujarTexto(cx - 5, cy + 3, vertice.toString());
        return s;
    }
    @Override
    public String setDimensiones(ArbolBinario<Integer> arbol){
        int width = arbol.altura() * arbol.altura();
        width = width * 70;
        width = width + 230;
        if(arbol.altura() >= 8){
            width *= 3;
        }
        return APERTURA + "\n" + "<svg height=" + '"' + (arbol.altura()*arbol.altura())*50+ '"' + " width=" + '"' + width*2 + '"' + ">" + "\n";
    }
}