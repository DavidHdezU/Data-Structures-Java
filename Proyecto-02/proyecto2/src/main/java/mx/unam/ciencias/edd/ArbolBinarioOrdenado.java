package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>
 * Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.
 * </p>
 *
 * <p>
 * Un árbol instancia de esta clase siempre cumple que:
 * </p>
 * <ul>
 * <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 * descendientes por la izquierda.</li>
 * <li>Cualquier elemento en el árbol es menor o igual que todos sus
 * descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        public Iterador() {
            // Aquí va su código.
            this.pila = new Pila<>();
            Vertice v = raiz;
            while (v != null) {
                pila.mete(v);
                v = v.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            // Aquí va su código.
            return this.pila.cabeza != null;
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override
        public T next() {
            // Aquí va su código.
            Vertice v = pila.saca();
            if (v.derecho != null) {
                Vertice rigth = v.derecho;
                while (rigth != null) {
                    pila.mete(rigth);
                    rigth = rigth.izquierdo;
                }
            }
            return v.elemento;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede garantizar
     * que existe <em>inmediatamente</em> después de haber agregado un elemento al
     * árbol. Si cualquier operación distinta a agregar sobre el árbol se ejecuta
     * después de haber agregado un elemento, el estado de esta variable es
     * indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros de
     * {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() {
        super();
    }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol binario
     *                  ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException();
        Vertice agregado = nuevoVertice(elemento);
        if (raiz == null) {
            elementos = 1;
            ultimoAgregado = agregado;
            raiz = agregado;
            return;
        }
        agrega(raiz, elemento);
    }

    private void agrega(Vertice v, T e) {
        if (raiz == null || v == null || e == null) {
            return;
        }
        if (e.compareTo(v.elemento) <= 0) {
            if (v.izquierdo == null) {
                Vertice agregado = nuevoVertice(e);
                elementos++;
                ultimoAgregado = agregado;
                v.izquierdo = agregado;
                agregado.padre = v;
                return;
            } else {
                agrega(v.izquierdo, e);
            }
        } else {
            if (v.derecho == null) {
                Vertice agregado = nuevoVertice(e);
                elementos++;
                ultimoAgregado = agregado;
                v.derecho = agregado;
                agregado.padre = v;
                return;
            } else {
                agrega(v.derecho, e);
            }
        }
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        // Aquí va su código.
        Vertice buscado = vertice(busca(elemento));
        if (buscado == null) {
            return;
        }
        if (buscado == raiz && elementos == 1) {
            super.limpia();
            return;
        }
        if (buscado.izquierdo == null && buscado.derecho == null) {
            eliminaHoja(buscado);
        }
        if ((buscado.izquierdo != null && buscado.derecho == null)
                || (buscado.izquierdo == null && buscado.derecho != null)) {
            eliminaVertice(buscado);
            elementos--;
            return;
        }
        if (buscado.izquierdo != null && buscado.derecho != null) {
            eliminaVertice(intercambiaEliminable(buscado));
            elementos--;
            return;
        }
    }

    protected Vertice maximoEnSubArbol(Vertice v) {
        if (v.derecho == null) {
            return v;
        }
        return maximoEnSubArbol(v.derecho);
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más un
     * hijo.
     * 
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se intercambió.
     *         El vértice regresado tiene a lo más un hijo distinto de
     *         <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        // Aquí va su código.
        if (vertice.izquierdo == null || vertice.derecho == null)
            return null;
        Vertice intercambia = maximoEnSubArbol(vertice.izquierdo);
        T e = vertice.elemento;
        vertice.elemento = intercambia.elemento;
        intercambia.elemento = e;

        return intercambia;

    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de <code>null</code>
     * subiendo ese hijo (si existe).
     * 
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo distinto de
     *                <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        // Aquí va su código.
        Vertice p = vertice.padre;
        Vertice u = null;
        if (vertice.derecho != null && vertice.izquierdo != null) {
            return;
        }
        if (vertice.derecho != null)
            u = vertice.derecho;
        if (vertice.izquierdo != null)
            u = vertice.izquierdo;
        if (p != null) {
            if (vertice.padre.izquierdo == vertice) {
                vertice.padre.izquierdo = u;
            }
            if (vertice.padre.derecho == vertice) {
                vertice.padre.derecho = u;
            }
        }
        if (p == null) {
            raiz = u;
        }
        if (u != null) {
            u.padre = p;
        }
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * 
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    @Override
    public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código.
        return busca(raiz, elemento);
    }

    private VerticeArbolBinario<T> busca(Vertice v, T e) {
        if (raiz == null || v == null || e == null) {
            return null;
        }
        if (e.equals(v.elemento)) {
            return v;
        }
        if (e.compareTo(v.elemento) < 0) {
            return busca(v.izquierdo, e);
        } else {
            return busca(v.derecho, e);
        }
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al árbol. Este
     * método sólo se puede garantizar que funcione <em>inmediatamente</em> después
     * de haber invocado al método {@link agrega}. Si cualquier operación distinta a
     * agregar sobre el árbol se ejecuta después de haber agregado un elemento, el
     * comportamiento de este método es indefinido.
     * 
     * @return el vértice que contiene el último elemento agregado al árbol, si el
     *         método es invocado inmediatamente después de agregar un elemento al
     *         árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        // Aquí va su código.
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no tiene
     * hijo izquierdo, el método no hace nada.
     * 
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        Vertice girar = vertice(vertice);
        Vertice p, izq, iP;
        if (girar.izquierdo == null) {
            return;
        }
        if (girar == raiz) {
            raiz = girar.izquierdo;
            p = girar.padre;
            iP = girar.izquierdo.derecho;
            izq = girar.izquierdo;
            girar.padre = izq;
            girar.padre.derecho = girar;
            girar.izquierdo = iP;
            girar.padre.padre = p;
            if (girar.izquierdo != null) {
                girar.izquierdo.padre = girar;
            }
            return;
        } else {
            boolean hayIzquierdo;
            p = girar.padre;
            iP = girar.izquierdo.derecho;
            izq = girar.izquierdo;
            if (girar.padre.izquierdo == girar) {
                hayIzquierdo = true;
            } else {
                hayIzquierdo = false;
            }
            girar.padre = izq;
            girar.padre.derecho = girar;
            girar.izquierdo = iP;
            girar.padre.padre = p;
            if (hayIzquierdo == true) {
                p.izquierdo = girar.padre;
            } else {
                p.derecho = girar.padre;
            }
            if (girar.izquierdo != null) {
                girar.izquierdo.padre = girar;
            }
        }
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * 
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        Vertice girar = vertice(vertice);
        Vertice p, der, iD;
        if (girar.derecho == null) {
            return;
        }
        if (girar == raiz) {
            raiz = girar.derecho;
            p = girar.padre;
            iD = girar.derecho.izquierdo;
            der = girar.derecho;
            girar.padre = der;
            girar.padre.izquierdo = girar;
            girar.derecho = iD;
            girar.padre.padre = p;
            if (girar.derecho != null) {
                girar.derecho.padre = girar;
            }
            return;
        } else {
            boolean hayDerecho;
            if (girar.padre.izquierdo == girar) {
                hayDerecho = true;
            } else {
                hayDerecho = false;
            }
            p = girar.padre;
            iD = girar.derecho.izquierdo;
            der = girar.derecho;
            girar.padre = der;
            girar.padre.izquierdo = girar;
            girar.derecho = iD;
            girar.padre.padre = p;
            if (girar.derecho != null) {
                girar.derecho.padre = girar;
            }
            if (hayDerecho == true) {
                p.izquierdo = girar.padre;
            } else {
                p.derecho = girar.padre;
            }
        }
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la acción
     * recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsPreOrder(raiz, accion);
    }

    private void dfsPreOrder(Vertice v, AccionVerticeArbolBinario<T> accion) {
        if (v == null) {
            return;
        }
        accion.actua(v);
        dfsPreOrder(v.izquierdo, accion);
        dfsPreOrder(v.derecho, accion);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la acción
     * recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsInOrder(raiz, accion);
    }

    private void dfsInOrder(Vertice v, AccionVerticeArbolBinario<T> accion) {
        if (v == null) {
            return;
        }
        dfsInOrder(v.izquierdo, accion);
        accion.actua(v);
        dfsInOrder(v.derecho, accion);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsPostOrder(raiz, accion);
    }

    private void dfsPostOrder(Vertice v, AccionVerticeArbolBinario<T> accion) {
        if (v == null) {
            return;
        }
        dfsPostOrder(v.izquierdo, accion);
        dfsPostOrder(v.derecho, accion);
        accion.actua(v);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * 
     * @return un iterador para iterar el árbol.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    protected void eliminaHoja(Vertice v) {
        if (v.padre.izquierdo == v) {
            elementos--;
            v.padre.izquierdo = null;
            return;
        }
        if (v.padre.derecho == v) {
            elementos--;
            v.padre.derecho = null;
            return;
        }
    }
}
