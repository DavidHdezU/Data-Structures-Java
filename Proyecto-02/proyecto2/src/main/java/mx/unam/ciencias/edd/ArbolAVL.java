package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            // Aquí va su código.
            super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
            return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            // Aquí va su código.
            
            return super.toString() + " " + altura() + "/" + balanceVertice(this);

        }
        public String toCadena(){
            return super.toString();
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            // Aquí va su código.
            return altura == vertice.altura && super.equals(vertice);
        }
    }
    /**
     * Regresa la altura de un vertice
     * @param vertice vertice a verificar
     * @return altura del vertice
     */
    private int calculaAltura(VerticeAVL vertice){
        if(vertice == null)
            return -1;
        return vertice.altura;
    }
    /**
     * Calcula el balance de un vertice
     * @param v vertice a verificar
     * @return balance del vertice
     */
    private int balanceVertice(VerticeAVL v){
        return (calculaAltura((VerticeAVL)v.izquierdo) - calculaAltura((VerticeAVL)v.derecho));
    }
    /**
     * Reasignamos la altura de un vertice
     * @param v vertice a verificar
     * @return nueva altura del vertice
     */
    private int nuevaAltura(VerticeAVL v){
        v.altura = 1 + Math.max(calculaAltura((VerticeAVL)v.izquierdo), calculaAltura((VerticeAVL)v.derecho));
        return v.altura;
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);
        rebalacear((VerticeAVL)ultimoAgregado.padre);

    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        VerticeAVL buscado = (VerticeAVL)busca(elemento);
        if(buscado == null)
            return;
        elementos--;
        // Checamos que el buscado sólo tenga un hija, si no lo intercambiamos
        if(buscado.derecho != null && buscado.izquierdo != null){
            buscado = (VerticeAVL)intercambiaEliminable(buscado);
        }
        eliminaVertice(buscado);
        rebalacear((VerticeAVL)buscado.padre);
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
    private void rebalacear(VerticeAVL vertice){
        if(vertice == null)
            return;
        vertice.altura = nuevaAltura(vertice);
        // Los únicos que cambian de altura en este caso son el padre de v, el derecho y el derecho del derecho
        if(balanceVertice(vertice) == -2){
            VerticeAVL derecho = (VerticeAVL) vertice.derecho;
            VerticeAVL hDer = (VerticeAVL) derecho.derecho;
            if(balanceVertice(derecho) == 1){
                super.giraDerecha(derecho);
                derecho.altura = nuevaAltura(derecho);
                VerticeAVL padreDer = (VerticeAVL)derecho.padre;
                padreDer.altura = nuevaAltura(padreDer);
            }
            super.giraIzquierda(vertice);
            vertice.altura = nuevaAltura(vertice);
        }
        // Em este caso los únicos que pueden cambiar de áltura son el padre de v, el izquierdo y el izquierdo del izquierdo
        if(balanceVertice(vertice) == 2){
            VerticeAVL izquierdo = (VerticeAVL) vertice.izquierdo;
            VerticeAVL hIzq = (VerticeAVL) izquierdo.izquierdo;
            if(balanceVertice(izquierdo) == -1){
                super.giraIzquierda(izquierdo);
                izquierdo.altura = nuevaAltura(izquierdo);
                VerticeAVL padreIzq = (VerticeAVL)izquierdo.padre;
                padreIzq.altura = nuevaAltura(padreIzq);
            }
            super.giraDerecha(vertice);
            vertice.altura = nuevaAltura(vertice);
        }
        rebalacear((VerticeAVL)vertice.padre);
    }
}
