package mx.unam.ciencias.edd.proyecto3.svg;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.VerticeArbolBinario;

import mx.unam.ciencias.edd.ArbolBinario;

/**
 * Clase abstracta para la construcción de arboles binarios
 */
public abstract class ArbolSVG extends EstructuraSVG{
    @Override
    abstract public String toSVG(Coleccion<Integer> estructura);

    /**
     * Método para definir las dimensiones de la ventana
     * @param arbol - arbol a tomar en cuenta
     * @return - cadena svg con la altura y ancho de la ventana
     */
    abstract public String setDimensiones(ArbolBinario<Integer> arbol);
    /**
     * Método para establecer el radio de los vértices, dependiendo de la cantidad de digitos
     * @param arbol - arbol a usar
     * @return int - radio de los vértices
     */
    public int setRadio(ArbolBinario<Integer> arbol){
        int maximo = 0;
        for(Integer n : arbol){
            if(n.toString().length() > maximo){
                maximo = n.toString().length();
            }
        }
        if(maximo == 1){
            maximo = 2;
        }
        return maximo*6 + 3;
    }

    /**
     * Método auxiliar para mantener las proporciones dada la altura del árbol
     * @param r - radio de los vértices
     * @param altura - altura del aŕbol
     * @return
     */
   protected int width(int r, int altura) {
    int res = r*2 + 10;
    while(altura != 1){
        res = 2*res + 10;
        altura--;
    }
    return res;
}
    /**
     * Método para poder dibujar las aritas y vértices del árbol
     * @param vertice - raíz del árbol
     * @param cx - coordenada x inicial
     * @param cy - coordenada x inicial
     * @param arbol - arbol a usar
     * @param indicador - indicador proporcionnal
     * @return String - cadena svg con los vértices ya aristas
     */
   abstract public String vertices(VerticeArbolBinario vertice, int cx, int cy, ArbolBinario<Integer> arbol, int indicador);
}
