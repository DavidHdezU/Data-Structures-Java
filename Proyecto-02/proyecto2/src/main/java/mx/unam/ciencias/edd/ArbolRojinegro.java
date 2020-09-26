package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 * <li>Todos los vértices son NEGROS o ROJOS.</li>
 * <li>La raíz es NEGRA.</li>
 * <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la
 * raíz).</li>
 * <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 * <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 * mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>> extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            // Aquí va su código.
            super(elemento);
            this.color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * 
         * @return una representación en cadena del vértice rojinegro.
         */
        @Override
        public String toString() {
            // Aquí va su código.
            if (color == Color.ROJO)
                return "R{" + elemento.toString() + "}";
            return "N{" + elemento.toString() + "}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es <em>recursiva</em>.
         * 
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente iguales, y los
         *         colores son iguales; <code>false</code> en otro caso.
         */
        @Override
        public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeRojinegro vertice = (VerticeRojinegro) objeto;
            // Aquí va su código.
            return (this.color == vertice.color && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros de
     * {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol rojinegro
     * tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeRojinegro}.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override
    protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * 
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de
     *                            {@link VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if (vertice.getClass() != VerticeRojinegro.class)
            throw new ClassCastException();
        return ((VerticeRojinegro) vertice).color;

    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método
     * {@link ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);
        VerticeRojinegro agregado = (VerticeRojinegro) ultimoAgregado;
        agregado.color = Color.ROJO;
        rebalancearAgrega(agregado);
    }

    /**
     * Método auxiliar que rebalance el árbol cuando se le agrega un elemento
     * 
     * @param vertice - vértice agregado
     */
    private void rebalancearAgrega(VerticeRojinegro vertice) {
        if (vertice.padre == null) {// Caso 1 - si el vertice no tiene padre
            vertice.color = Color.NEGRO;
            return;
        }

        VerticeRojinegro padre = (VerticeRojinegro) vertice.padre;
        if (esNegro(padre)) // Caso 2 - Si el padre es negro
            return;
        VerticeRojinegro abuelo = abuelo(vertice);
        VerticeRojinegro tioVertice = hermano(padre);
        VerticeRojinegro aux;

        // Caso 3
        if (esRojo(tioVertice)) {
            tioVertice.color = Color.NEGRO;
            padre.color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            rebalancearAgrega(abuelo);
            return;
        }
        // Los vertices puedene estar cruzados - Caso 4
        if (cruzados(padre, vertice)) {
            if (esIzquierdo(padre)) {
                super.giraIzquierda(padre);
            } else {
                super.giraDerecha(padre);
            }
            aux = padre;
            padre = vertice;
            vertice = aux;
        }
        // Si no están cruzados llegamos al Caso 5
        padre.color = Color.NEGRO;
        abuelo.color = Color.ROJO;
        if (esIzquierdo(vertice)) {
            super.giraDerecha(abuelo);
        } else {
            super.giraIzquierda(abuelo);
        }
    }

    /**
     * Nos indica si un vértice es hijo derecho
     * 
     * @param v vértice a verificar
     * @return <code>true</code> si es hijo derecho <code>false</code> en otro caso
     */
    private boolean esDerecho(Vertice v) {
        return v.padre.derecho == v && v != null;
    }

    /**
     * Nos indica si un vértice es hijo izquierdo
     * 
     * @param v vértice a verificar
     * @return <code>true</code> si es hijo izquierdo <code>false</code> en otro
     *         caso
     */
    private boolean esIzquierdo(Vertice v) {
        return v.padre.izquierdo == v && v != null;
    }

    /**
     * Nos indica si 2 vértices están cruzados
     * 
     * @param padre   - el padre
     * @param vertice - el hijo
     * @return <code>true</code> si están cruzados <code>false</code> en otro caso
     */
    private boolean cruzados(VerticeRojinegro padre, VerticeRojinegro vertice) {
        return ((esIzquierdo(vertice) && esDerecho(padre)) || (esDerecho(vertice) && esIzquierdo(padre)));
    }

    /**
     * Nos regresa el abuelo de un <code>v</code>
     * 
     * @param v - nieto
     * @return - el abuelo de <code>v</code>
     */
    private VerticeRojinegro abuelo(VerticeRojinegro v) {
        VerticeRojinegro padre = (VerticeRojinegro) v.padre;
        return (VerticeRojinegro) padre.padre;
    }

    /**
     * Nos dice si un Vértice es rojo
     * 
     * @param vertice - vértice a verificar
     * @return - <code>true</code> si es rojo <code>false</code> en otro caso
     */
    private boolean esRojo(VerticeRojinegro vertice) {
        return (vertice != null && vertice.color == Color.ROJO);
    }

    /**
     * Nos dice si un Vértice es negro
     * 
     * @param vertice - vértice a verificar
     * @return - <code>true</code> si es negro <code>false</code> en otro caso
     */
    private boolean esNegro(VerticeRojinegro vertice) {
        return (vertice == null || vertice.color == Color.NEGRO);
        // Es "or" porque por definición los vertices negros pueden ser null
    }

    /**
     * Regresa el hermano del vértice <code>v</code>
     * 
     * @param v - vértice a buscar
     * @return - el hermano de <code>v</code>
     */
    private VerticeRojinegro hermano(VerticeRojinegro v) {
        if (esIzquierdo(v)) {
            return (VerticeRojinegro) v.padre.derecho;
        }
        return (VerticeRojinegro) v.padre.izquierdo;

    }

    /**
     * Asignamos un valor al hijo de <code>v</code>
     * 
     * @param v - Padre
     * @return - El hijo dependiendo si hay izquierdo o derecho
     */
    private VerticeRojinegro hijoInsertar(VerticeRojinegro v) {
        if (v.izquierdo != null) {
            return (VerticeRojinegro) v.izquierdo;
        } else {
            return (VerticeRojinegro) v.derecho;
        }
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene el
     * elemento, y recolorea y gira el árbol como sea necesario para rebalancearlo.
     * 
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override
    public void elimina(T elemento) {
        // Aquí va su código.
        VerticeRojinegro buscado = (VerticeRojinegro) busca(elemento);
        if (buscado == null)
            return;
        elementos--; // Poner aquí, si lo ponemos hasta abajo no funciona :(
        VerticeRojinegro fantasma = null;
        if (buscado.izquierdo != null && buscado.derecho != null) {
            buscado = (VerticeRojinegro) intercambiaEliminable(buscado);
        }
        if (buscado.derecho == null && buscado.izquierdo == null) {
            fantasma = (VerticeRojinegro) nuevoVertice(null);
            fantasma.color = Color.NEGRO;
            fantasma.padre = buscado;
            buscado.izquierdo = fantasma;
        }
        VerticeRojinegro hijo = hijoInsertar(buscado);
        eliminaVertice(buscado);
        if (esRojo(hijo)) {
            hijo.color = Color.NEGRO;
            return;
        }
        if (esNegro(hijo) && esNegro(buscado)) {
            rebalancearElimina(hijo);
        }

        eliminaFantasma(fantasma);
    }

    /**
     * Elimina el vértice fantasma
     * 
     * @param fantasma - vértice fantasma
     */
    private void eliminaFantasma(VerticeRojinegro fantasma) {
        if (fantasma != null) {
            if (fantasma.padre == null && fantasma != null) {
                raiz = fantasma = null;
            } else if (esIzquierdo(fantasma)) {
                fantasma.padre.izquierdo = null;
            } else {
                fantasma.padre.derecho = null;
            }
        }
    }

    /**
     * Método auxiliar para rebalancear un árbol al momento de eliminar un vértice
     * 
     * @param vertice - vértice eliminado
     */
    private void rebalancearElimina(VerticeRojinegro vertice) {
        if (vertice.padre == null)// Caso 1
            return;
        VerticeRojinegro padre = (VerticeRojinegro) vertice.padre;
        VerticeRojinegro hermano = hermano(vertice);

        if (esRojo(hermano)) { // Caso 2
            padre.color = Color.ROJO;
            hermano.color = Color.NEGRO;
            if (esIzquierdo(vertice)) {
                super.giraIzquierda(padre);
            } else {
                super.giraDerecha(padre);
            }
            hermano = hermano(vertice);
        }
        VerticeRojinegro hIzq = (VerticeRojinegro) hermano.izquierdo;
        VerticeRojinegro hDer = (VerticeRojinegro) hermano.derecho;

        if (esNegro(padre) && esNegro(hermano) && esNegro(hIzq) && esNegro(hDer)) { // Caso 3
            hermano.color = Color.ROJO;
            rebalancearElimina(padre);
            return;
        }
        if (esRojo(padre) && esNegro(hermano) && esNegro(hIzq) && esNegro(hDer)) { // Caso 4
            hermano.color = Color.ROJO;
            padre.color = Color.NEGRO;
            return;
        }
        // Caso 5
        if ((esIzquierdo(vertice) && esRojo(hIzq) && esNegro(hDer))
                || (esDerecho(vertice) && esNegro(hIzq) && esRojo(hDer))) {
            hermano.color = Color.ROJO;
            if (esRojo(hDer)) {
                hDer.color = Color.NEGRO;
            } else {
                hIzq.color = Color.NEGRO;
            }

            if (esIzquierdo(vertice)) {
                super.giraDerecha(hermano);
            } else {
                super.giraIzquierda(hermano);
            }
            hermano = hermano(vertice);
            hIzq = (VerticeRojinegro) hermano.izquierdo;
            hDer = (VerticeRojinegro) hermano.derecho;
        }
        // Caso 6
        hermano.color = padre.color;
        padre.color = Color.NEGRO;
        if (esIzquierdo(vertice)) {
            hDer.color = Color.NEGRO;
            super.giraIzquierda(padre);
        } else {
            hIzq.color = Color.NEGRO;
            super.giraDerecha(padre);
        }

    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la clase,
     * porque se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException(
                "Los árboles rojinegros no " + "pueden girar a la izquierda " + "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la clase,
     * porque se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException(
                "Los árboles rojinegros no " + "pueden girar a la derecha " + "por el usuario.");
    }
}
