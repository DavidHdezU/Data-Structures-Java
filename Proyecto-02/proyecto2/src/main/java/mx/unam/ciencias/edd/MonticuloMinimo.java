package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return indice < elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            if(fueraArreglo(indice))
                throw new NoSuchElementException();
            return arbol[indice++];
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            this.indice = -1;

        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return this.indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            // Aquí va su código.
            return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        // Aquí va su código.
        arbol = nuevoArreglo(100);
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        // Aquí va su código.
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        // Aquí va su código.
        arbol = nuevoArreglo(n);
        int i = 0;
        elementos = n;
        for(T elemento : iterable){
            arbol[i] = elemento;
            elemento.setIndice(i);
            i++;
        }
        for(int j = (n/2)-1; j >= 0 ; j--){
            heapifyDown(arbol[j]);
        }
    }
    private void heapifyDown(T e) {
        if (e == null) { 
            return; 
        }
        int izq = e.getIndice() * 2 + 1;
        int der = e.getIndice() * 2 + 2;

        if(fueraArreglo(izq) && fueraArreglo(der)){
            return;
        }

        int minimo = der;
        if(!fueraArreglo(izq)) { 
            if(!fueraArreglo(der)) {
                if(arbol[izq].compareTo(arbol[der]) < 0) { 
                    minimo = izq; 
                }
            } else {
                 minimo = izq; 
                }
        }

        if(arbol[minimo].compareTo(e) < 0) {
            swap(e, arbol[minimo]);
            heapifyDown(e);
        }
    }

    /**
     * Heapify-up algorithm
     * @param e element being heapified up
     */
    private void heapifyUp(T e) {
        int p = e.getIndice() - 1;
        if(p == -1){
            p = -1;
        }else{
            p = p/2;
        }
        if(fueraArreglo(p) || arbol[p].compareTo(e) < 0) {
            return;
        }
        swap(arbol[p], e);
        heapifyUp(e);
    }

    /**
     * Swap two given elements holding their indices
     * @param e1
     * @param e2
     */
    private void swap(T i, T j) {
        int a = j.getIndice();
        arbol[i.getIndice()] = j;
        arbol[j.getIndice()] = i;
        j.setIndice(i.getIndice());
        i.setIndice(a);
}
    private boolean fueraArreglo(int i){
        return (i < 0 || i >= this.elementos);
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elementos == arbol.length){
            T[] nuevo = nuevoArreglo(2*elementos);
            for(int i = 0; i < elementos; i++){
                nuevo[i] = arbol[i];
            }
            arbol = nuevo;
        }
        arbol[elementos] = elemento;
        elemento.setIndice(elementos);
        elementos++;
        heapifyUp(elemento);
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        if(elementos == 0){ 
            throw new IllegalStateException(); 
        }
        T raiz = arbol[0];

        
        swap(arbol[0], arbol[elementos - 1]);
        elementos--;
        arbol[this.elementos].setIndice(-1);
        arbol[this.elementos] = null;
        heapifyDown(this.arbol[0]);

        return raiz;
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        int i = elemento.getIndice();
        if(fueraArreglo(i)) { 
            return; 
        }
        swap(arbol[i], arbol[elementos - 1]);
        elementos--;
        arbol[elementos] = null;
        elemento.setIndice(-1);
        reordena(arbol[i]);
}

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        int i = elemento.getIndice();
        if(fueraArreglo(i))
            return false;
        return arbol[i].compareTo(elemento) == 0;
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return elementos == 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
        for(int i = 0; i < elementos; i++){
            arbol[i] = null;
        }
        elementos = 0;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            return;
        int p = elemento.getIndice() - 1;
        if(p == -1){
            p = -1;
        }else{
            p = p/2;
        }
        if(fueraArreglo(p) || arbol[p].compareTo(elemento) <= 0){
            heapifyDown(elemento);
        }else{
            heapifyUp(elemento);
        }
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return this.elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        // Aquí va su código.
        if(fueraArreglo(i))
            throw new NoSuchElementException();
        return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        // Aquí va su código.
        String res = "";
        for(T e : this){
            res += e.toString() + ", ";
        }
        return res;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
            if(getElementos() != monticulo.getElementos()){
                return false;
            }
            for(int i = 0; i < getElementos(); i++){
                if(!get(i).equals(monticulo.get(i))){
                    return false;
                }
            }
            return true;
        // Aquí va su código.
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
        // Aquí va su código.
        Lista<Adaptador<T>> lista = new Lista<Adaptador<T>>();
        for(T elemento: coleccion){
            lista.agrega(new Adaptador<T>(elemento));
        }

        Lista<T> lista2 = new Lista<T>();

        MonticuloMinimo<Adaptador<T>> monticuloMinimo = new MonticuloMinimo<Adaptador<T>>(lista);

        while(!monticuloMinimo.esVacia()) {
            Adaptador<T> minimo = monticuloMinimo.elimina();
            lista2.agrega(minimo.elemento);
        }

        return lista2;
    }

}
