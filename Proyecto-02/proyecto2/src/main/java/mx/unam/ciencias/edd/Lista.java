package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Clase genérica para listas doblemente ligadas.
 * </p>
 *
 * <p>
 * Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.
 * </p>
 *
 * <p>
 * Las listas no aceptan a <code>null</code> como elemento.
 * </p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            // Aquí va su código.
            anterior = null;
            siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            // Aquí va su código.
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override
        public T next() {
            // Aquí va su código.
            if (siguiente == null) {
                throw new NoSuchElementException();
            }
            anterior = siguiente;
            siguiente = siguiente.siguiente;

            return anterior.elemento;

        }

        /* Nos dice si hay un elemento anterior. */
        @Override
        public boolean hasPrevious() {
            // Aquí va su código.
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override
        public T previous() {
            // Aquí va su código.
            if (anterior == null) {
                throw new NoSuchElementException();
            }
            siguiente = anterior;
            anterior = anterior.anterior;

            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override
        public void start() {
            // Aquí va su código.
            anterior = null;
            siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override
        public void end() {
            // Aquí va su código.
            anterior = rabo;
            siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a
     * {@link #getElementos}.
     * 
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
        return getElementos();
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a
     * {@link #getLongitud}.
     * 
     * @return el número elementos en la lista.
     */
    @Override
    public int getElementos() {
        // Aquí va su código.
        return this.longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * 
     * @return <code>true</code> si la lista es vacía, <code>false</code> en otro
     *         caso.
     */
    @Override
    public boolean esVacia() {
        // Aquí va su código.
        return rabo == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el elemento a
     * agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void agrega(T elemento) {
        // Aquí va su código.
        if (elemento == null) {
            throw new IllegalArgumentException();
        }

        Nodo nuevoElemento = new Nodo(elemento);
        longitud++;
        if (rabo == null) {
            rabo = cabeza = nuevoElemento;
        } else {
            rabo.siguiente = nuevoElemento;
            nuevoElemento.anterior = rabo;
            rabo = nuevoElemento;
        }
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        // Aquí va su código.
        agrega(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último.
     * 
     * @param elemento el elemento a agregar.
     * @th rows IllegalArgumentException si <code>elemento</code> es
     *     <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        // Aquí va su código.
        if (elemento == null) {
            throw new IllegalArgumentException();
        }

        Nodo nuevoElemento = new Nodo(elemento);
        longitud++;
        // Hay 2 casos: Lista vacia y lista con elementos

        // Lista vacia

        if (rabo == null) {
            rabo = cabeza = nuevoElemento;
        } else {
            cabeza.anterior = nuevoElemento;
            nuevoElemento.siguiente = cabeza;
            cabeza = nuevoElemento;
        }

        // Lista con elementos
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio de la
     * lista. Si el índice es mayor o igual que el número de elementos en la lista,
     * el elemento se agrega al fina de la misma. En otro caso, después de mandar
     * llamar el método, el elemento tendrá el índice que se especifica en la lista.
     * 
     * @param i        el índice dónde insertar el elemento. Si es menor que 0 el
     *                 elemento se agrega al inicio de la lista, y si es mayor o
     *                 igual que el número de elementos en la lista se agrega al
     *                 final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        // Aquí va su código.
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
        if (i <= 0) {
            agregaInicio(elemento);
        } else if (i >= longitud) {
            agregaFinal(elemento);
        } else {
            Nodo recorrido = getIndex(i);
            Nodo nuevo = new Nodo(elemento);
            longitud++;

            nuevo.anterior = recorrido.anterior;
            nuevo.anterior.siguiente = nuevo;
            nuevo.siguiente = recorrido;
            recorrido.anterior = nuevo;

        }

    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        // Aquí va su código.
        Nodo eliminado = buscaNodo(elemento);
        // 4 casos a cosiderar
        if (eliminado == null) {
            return;
        }

        if (longitud == 1) { // 1
            rabo = cabeza = null;
        } else if (eliminado == cabeza) { // 2
            eliminado.siguiente.anterior = null;
            cabeza = eliminado.siguiente;
        } else if (eliminado == rabo) { // 3
            eliminado.anterior.siguiente = null;
            rabo = eliminado.anterior;
        } else { // 4
            eliminado.anterior.siguiente = eliminado.siguiente;
            eliminado.siguiente.anterior = eliminado.anterior;
        }
        longitud--;
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * 
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        // Aquí va su código.
        if (rabo == null) {
            throw new NoSuchElementException();
        }

        T element = cabeza.elemento;
        elimina(element);

        return element;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * 
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        // Aquí va su código.
        if (rabo == null) {
            throw new NoSuchElementException();
        }

        T element = rabo.elemento;
        if (longitud == 1) {
            rabo = cabeza = null;
        } else {
            rabo.anterior.siguiente = null;
            rabo = rabo.anterior;
        }
        longitud--;
        return element;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * 
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        // Aquí va su código.
        return buscaNodo(elemento) != null;
    }

    /**
     * Busca el indice de un Nodo, si este se encuentra em la lista
     * 
     * @param i - el indice que se quiere buscar
     * @return el nodo del indice dado
     */
    private Nodo getIndex(int i) {
        Nodo nodo = cabeza;
        int cont = 0;

        while (cont++ != i) {
            if (nodo != null) {
                nodo = nodo.siguiente;
            }
        }
        return nodo;

    }

    /**
     * Busca un nodo dado un elemento
     * 
     * @param elemento - el elemento que se quiere buscar
     * @return el nodo dado el elemento
     */
    private Nodo buscaNodo(T elemento) {
        if (elemento == null) {
            return null;
        }

        Nodo buscado = cabeza;
        while (buscado != null) {
            if (buscado.elemento.equals(elemento)) {
                return buscado;
            } else {
                buscado = buscado.siguiente;
            }
        }
        return null;
    }

    /**
     * Regresa la reversa de la lista.
     * 
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        // Aquí va su código.
        Lista<T> lista = new Lista<T>();
        Nodo r = rabo;
        while (r != null) {
            lista.agregaFinal(r.elemento);
            r = r.anterior;
        }
        return lista;

    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * 
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        // Aquí va su código.
        Lista<T> copia = new Lista<T>();
        Nodo c = cabeza;
        while (c != null) {
            copia.agregaFinal(c.elemento);
            c = c.siguiente;
        }
        return copia;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override
    public void limpia() {
        // Aquí va su código.
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * 
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        // Aquí va su código.
        if (rabo == null) {
            throw new NoSuchElementException();
        }

        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * 
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        // Aquí va su código.
        if (rabo == null) {
            throw new NoSuchElementException();
        }

        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * 
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *                                 igual que el número de elementos en la lista.
     */
    public T get(int i) {
        // Aquí va su código.
        if (i < 0 || i >= longitud) {
            throw new ExcepcionIndiceInvalido();
        }
        Nodo buscado = getIndex(i);
        return buscado.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * 
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento no
     *         está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        // Aquí va su código.
        Nodo buscado = cabeza;
        int cont = 0;
        while (buscado != null) {
            if (buscado.elemento.equals(elemento)) {
                return cont;
            } else {
                buscado = buscado.siguiente;
                cont++;
            }
        }
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * 
     * @return una representación en cadena de la lista.
     */
    @Override
    public String toString() {
        // Aquí va su código.
        if (rabo == null) {
            return "[]";
        }
        String res = "[";
        IteradorLista<T> ite = iteradorLista();
        ite.start();
        int cont = 0;
        while (ite.hasNext()) {
            if (cont++ == getElementos() - 1) {
                res += ite.next().toString();
            } else {
                res += ite.next().toString() + ", ";
            }
        }
        return res + "]";
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * 
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Lista<T> lista = (Lista<T>) objeto;
        // Aquí va su código.
        if (longitud != lista.getLongitud())
            return false;

        Nodo c = cabeza;
        int cont = 0;
        while (c != null) {
            if (!(lista.getIndex(cont).elemento.equals(c.elemento))) {
                return false;
            } else {
                c = c.siguiente;
                cont++;
            }
        }
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * 
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * 
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * 
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        // Aquí va su código.
        return mergeSort(this, comparador);
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz
     * {@link Comparable}.
     * 
     * @param <T>   tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * 
     * @param elemento   el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        // Aquí va su código.
        if (elemento == null) {
            return false;
        }
        if (rabo == null) {
            return false;
        }

        for (T elementos : this) {
            if (comparador.compare(elementos, elemento) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que contener
     * nada más elementos que implementan la interfaz {@link Comparable}, y se da
     * por hecho que está ordenada.
     * 
     * @param <T>      tipo del que puede ser la lista.
     * @param lista    la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>> boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }

    /**
     * Mezcla dos listas que se ordenan en una sola lista
     * 
     * @param l1         lista 1 a ordenar
     * @param l2         lista 2 a ordenar
     * @param comparador comparador que ayuda a ordenar las listas
     * @return una lista ordenada
     */
    private Lista<T> mezcla(Lista<T> l1, Lista<T> l2, Comparator<T> comparador) {
        Lista<T> nueva = new Lista<T>();
        Nodo i = l1.cabeza;
        Nodo j = l2.cabeza;

        while (i != null && j != null) {
            if (comparador.compare(i.elemento, j.elemento) <= 0) {
                nueva.agrega(i.elemento);
                i = i.siguiente;
            } else {
                nueva.agrega(j.elemento);
                j = j.siguiente;
            }
        }
        while (j != null && i == null) {
            nueva.agrega(j.elemento);
            j = j.siguiente;
        }
        while (i != null && j == null) {
            nueva.agrega(i.elemento);
            i = i.siguiente;
        }
        return nueva;
    }

    /**
     * Ordena una lista con mergeSort
     * 
     * @param l          lista a ordenar
     * @param comparador comparador que ayuda a ordenar una lista
     * @return una lista ordenada
     */
    private Lista<T> mergeSort(Lista<T> l, Comparator<T> comparador) {
        int longitudNueva = l.longitud;
        int longitudL1;
        int longitudL2;
        Nodo n = l.cabeza;
        Lista<T> l1 = new Lista<T>();
        Lista<T> l2 = new Lista<T>();
        // Cask base
        if (longitudNueva == 1 || longitudNueva == 0) {
            return l;
        }

        if (longitudNueva % 2 == 0) {
            longitudL1 = longitudL2 = longitudNueva / 2;
        } else {
            longitudL1 = (longitudNueva - 1) / 2;
            longitudL2 = ((longitudNueva - 1) / 2) + 1;
        }
        // Aquí agregamos la primera mitad de la lista
        for (int i = 0; i < longitudL1; i++) {
            l1.agrega(n.elemento);
            n = n.siguiente;
        }
        // Agregamos la segunda mitad
        for (int j = 0; j < longitudL2; j++) {
            l2.agrega(n.elemento);
            n = n.siguiente;
        }
        // Creamos varias lista, de tal forma que nos queden listas de longitud 1, para
        // llegar al caso base
        l1 = mergeSort(l1, comparador);
        l2 = mergeSort(l2, comparador);
        // Mezclamos todas las listas de tal forma que tengamos al final una lista
        // ordenada
        return mezcla(l1, l2, comparador);

    }

}
