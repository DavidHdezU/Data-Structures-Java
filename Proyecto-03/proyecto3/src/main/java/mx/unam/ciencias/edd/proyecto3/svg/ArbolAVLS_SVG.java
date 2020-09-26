package mx.unam.ciencias.edd.proyecto3.svg;

import main.java.mx.unam.ciencias.edd.proyecto3.ComparadorPalabras;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.VerticeArbolBinario;
/**
 * Clase para construir un árbol AVL 
 */
public class ArbolAVLS_SVG extends ArbolSVG{
    @Override
    public String toSVG(Coleccion<Integer> arbol){
        ArbolAVL<Integer> tree = new ArbolAVL<>(arbol);
        int r = setRadio(tree);
        String svg = setDimensiones(tree);
        int width;
        if(tree.altura() <= 5){
            width = width(2*r, tree.altura() + 1);
        }else{
            width = width(2*r + r/2, tree.altura());
        }
        svg += vertices(tree.raiz(),  4*width/7 , 50, tree, width/2);
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
        s += dibujarTexto(cx - 6, cy + 3, splitString(vertice));
       
      // Esta parte es para que las alturas/balance no se sobrepongan con las lineas, y se vea mejor
        if(vertice.hayPadre()){
            if(vertice.padre().hayIzquierdo()) {
                if(vertice.padre().izquierdo() == vertice){
                    if(vertice.altura() == 0){
                        s += dibujarTexto(cx - 13, cy - 27, balanceAltura(vertice));
                    }else{
                        s += dibujarTexto(cx - 9, cy - 27, balanceAltura(vertice));
                    }
                }
            }
            if(vertice.padre().hayDerecho()){
                if(vertice.padre().derecho() == vertice){
                    s += dibujarTexto(cx + 10, cy - 27, balanceAltura(vertice));
                }
            }
        }else{ // Dibujamos la raiz, ya que la raíz no tiene padre
            s += dibujarTexto(cx - 9, cy - 25, balanceAltura(vertice));
        }
        
        return s;
    }

    @Override
    public String setDimensiones(ArbolBinario<Integer> arbol){
        int width = arbol.altura() * arbol.altura();
        width = width * 145;
        width = width + 230;
        int height = (arbol.altura()*arbol.altura())*30;
        if(arbol.altura() < 5){
            height *= 15;
        }
        return APERTURA + "\n" + "<svg height=" + '"' + height + '"' + " width=" + '"' + width*1.5 + '"' + ">" + "\n";
    }

    /**
     * Obtiene el elemento del vértice
     * @param vertice - vértice a usar
     * @return String - elemento del vértice
     */
    public String splitString(VerticeArbolBinario vertice){
        String res = vertice.toString();
        String[] arreglo = res.split(" ", 2);
        res = res.replace(arreglo[1], "");
        res = res.replace(" ", "");
        return res;
    }
    /**
     * Obtiene el balance y altura del vértice
     * @param vertice - vértice a usar
     * @return String - altura y balance del vértices
     */
    public String balanceAltura(VerticeArbolBinario vertice){
        String res = vertice.toString();
        String[] arreglo = res.split(" ", 2);
        res = res.replace(arreglo[0], "");
        return arreglo[1];

    }

    public String verticesString(VerticeArbolBinario vertice, int cx, int cy, ArbolBinario<ComparadorPalabras> arbol, int indicador){
        String s = "";
        // Sirve para tener un espacio considerable entre vértice y vértice
        int r = setRadioString(arbol);
        int posY = r + cy + 80;
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
        s += dibujarCirculos(cx, cy, r, "green");
        s += dibujarTexto(cx - 18, cy + 3, splitString(vertice));
       
      // Esta parte es para que las alturas/balance no se sobrepongan con las lineas, y se vea mejor
        if(vertice.hayPadre()){
            if(vertice.padre().hayIzquierdo()) {
                if(vertice.padre().izquierdo() == vertice){
                    if(vertice.altura() == 0){
                        s += dibujarTexto(cx - 13, cy - 32, balanceAltura(vertice));
                    }else{
                        s += dibujarTexto(cx - 14, cy - 30, balanceAltura(vertice));
                    }
                }
            }
            if(vertice.padre().hayDerecho()){
                if(vertice.padre().derecho() == vertice){
                    s += dibujarTexto(cx + 14, cy - 32, balanceAltura(vertice));
                }
            }
        }else{ // Dibujamos la raiz, ya que la raíz no tiene padre
            s += dibujarTexto(cx - 9, cy - 35, balanceAltura(vertice));
        }
        
        return s;
    }

    public String setDimensionesString(ArbolBinario<ComparadorPalabras> arbol){
        int width = arbol.altura() * arbol.altura();
        width = width * 145;
        width = width + 230;
        int height = (arbol.altura()*arbol.altura())*30;
        if(arbol.altura() < 5){
            height *= 2;
        }
        return APERTURA + "\n" + "<svg height=" + '"' + height + '"' + " width=" + '"' + width*1.5 + '"' + ">" + "\n";
    }

    public String toSVGPalabras(Coleccion<ComparadorPalabras> arbol){
        ArbolAVL<ComparadorPalabras> tree = new ArbolAVL<>(arbol);
        int r = setRadioString(tree);
        String svg = setDimensionesString(tree);
        int width;
        if(tree.altura() <= 5){
            width = width(2*r, tree.altura() + 1);
        }else{
            width = width(2*r + r/2, tree.altura());
        }
        svg += verticesString(tree.raiz(),  4*width/7 , 50, tree, width/2);
        svg += "\n" + "</svg>";
        return svg;
    }
}
