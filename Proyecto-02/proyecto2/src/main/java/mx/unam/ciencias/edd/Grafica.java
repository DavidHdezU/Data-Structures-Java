package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

import mx.unam.ciencias.edd.ArbolBinario.Vertice;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La lista de vecinos del vértice. */
        public Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            this.color = Color.NINGUNO;
            this.vecinos = new Lista<Vertice>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            // Aquí va su código.
            return this.elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            // Aquí va su código.
            return this.vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            // Aquí va su código.
            return this.color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return this.vecinos;

        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
        this.vertices = new Lista<Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return this.vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        // Aquí va su código.
        return this.aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento es <code>null</code> o ya
     *         había sido agregado a la gráfica.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(contiene(elemento) || elemento == null)
            throw new IllegalArgumentException();
        Vertice v = new Vertice(elemento);
        vertices.agrega(v);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        // Aquí va su código.
        Vertice v1 = buscaElemento(a);
        Vertice v2 = buscaElemento(b);
        if(v1 == null || v2 == null){
            throw new NoSuchElementException();
        }
        if(v1.equals(v2)){
            throw new IllegalArgumentException();
        }
        if(sonVecinos(a, b)){
            throw new IllegalArgumentException();
        }
        aristas++;
        v1.vecinos.agrega(v2);
        v2.vecinos.agrega(v1);
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        // Aquí va su código.
        Vertice v1 = buscaElemento(a);
        Vertice v2 = buscaElemento(b);
        if(v1 == null || v2 == null){
            throw new NoSuchElementException();
        }
        if(!sonVecinos(a, b)){
            throw new IllegalArgumentException();
        }
        aristas--;
        v1.vecinos.elimina(v2);
        v2.vecinos.elimina(v1);
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        return buscaElemento(elemento) != null;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        Vertice v = buscaElemento(elemento);
        if(v == null)
            throw new NoSuchElementException();
        vertices.elimina(v);
        for(Vertice u : v.vecinos){
            u.vecinos.elimina(v);
            aristas--;
        }
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        // Aquí va su código.
        Vertice v1 = buscaElemento(a);
        Vertice v2 = buscaElemento(b);
        if(v1 == null || v2 == null){
            throw new NoSuchElementException();
        }
        if(v1.equals(v2)){
            return false;
        }

        return v1.vecinos.contiene(v2) && v2.vecinos.contiene(v1);
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        // Aquí va su código.
        Vertice v = buscaElemento(elemento);
        if(v == null)
            throw new NoSuchElementException();
        return v;
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        // Aquí va su código.
        if (vertice.getClass() != Vertice.class || vertice == null)
            throw new IllegalArgumentException();
        Vertice v = (Vertice)vertice; 
        v.color = color;
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        // Aquí va su código.
        if(getElementos() == 0)
            return true;
        Cola<Vertice> metesaca = new Cola<Vertice>();
        Vertice vertice = vertices.getPrimero();
        paraCadaVertice((v) -> setColor(v, Color.ROJO));
        vertice.color = Color.NEGRO;
        metesaca.mete(vertice);
        while(!metesaca.esVacia()){
            Vertice u = metesaca.saca();
            for(Vertice vecino : u.vecinos){
                if(vecino.color == Color.ROJO){
                     metesaca.mete(vecino);
                     vecino.color = Color.NEGRO;
                }
            }
        }
        for(Vertice vecinos : vertices){
            if(vecinos.color.equals(Color.ROJO))
                return false;
        }
        paraCadaVertice(u -> setColor(u, Color.NINGUNO));
        return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        for(Vertice v : vertices){
            accion.actua(v);
        }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    private void recorridos(T elemento, AccionVerticeGrafica<T> accion, MeteSaca<Vertice> metesaca){
        Vertice vertice = buscaElemento(elemento);
        if(vertice == null)
            throw new NoSuchElementException();
        paraCadaVertice((v) -> setColor(v, Color.ROJO));
        vertice.color = Color.NEGRO;
        metesaca.mete(vertice);
        while(!metesaca.esVacia()){
            Vertice u = metesaca.saca();
            accion.actua(u);
            for(Vertice vecino : u.vecinos){
                if(vecino.color == Color.ROJO){
                    vecino.color = Color.NEGRO;
                    metesaca.mete(vecino);
                }
            }
        }
        paraCadaVertice(v -> setColor(v, Color.NINGUNO));
    }
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        Cola<Vertice> cola = new Cola<Vertice>();
        recorridos(elemento, accion, cola);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        Pila<Vertice> pila = new Pila<Vertice>();
        recorridos(elemento, accion, pila);
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return this.vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        this.aristas = 0;
        this.vertices.limpia();
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        // Aquí va su código.
        String res = "{";
        
        for(Vertice v : vertices){
            res += v.elemento.toString() + ", ";
        }
        res += "}, {";
        // Evita que haya vertices repetidos
        paraCadaVertice(v -> setColor(v, Color.ROJO));

        for(Vertice v1 : vertices){
            v1.color = Color.NEGRO;
            for(Vertice vecino : v1.vecinos){
                if(vecino.color != Color.NEGRO){
                    res += "(" + v1.elemento.toString() + ", " + vecino.elemento.toString() + "), ";
                }
            }
        }
        res += "}";     
        return res;
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        // Aquí va su código.
        if(this.getAristas() != grafica.getAristas() || this.getElementos() != grafica.getElementos()){
            return false;
        }
        for(Vertice v : vertices){
            Vertice aux = contieneLista(grafica.vertices, v);
            if(aux == null)
                return false;
            else{
                for(Vertice vecinos : v.vecinos){
                    if(contieneLista(aux.vecinos, vecinos) == null)
                        return false;
                }
            }
        }
        return true;
    }
    private Vertice buscaElemento(T elemento){
        for(Vertice v: vertices){
            if(v.elemento.equals(elemento))
                return v;
        }
        return null;
    }
    private Vertice contieneLista(Lista<Vertice> lista,Vertice vertice) {
        for (Vertice w: lista) {
            if (vertice.elemento.equals(w.elemento)) 
                return w;
        }
        return null;
}

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
