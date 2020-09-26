package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;


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
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* La lista de vecinos del vértice. */
        public Diccionario<T, Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            this.color = Color.NINGUNO;
            this.vecinos = new Diccionario<T, Vecino>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            // Aquí va su código.
            return this.elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            // Aquí va su código.
            return this.vecinos.getElementos();
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

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return this.indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            // Aquí va su código.
            if(this.distancia != -1 && (vertice.distancia == -1 || this.distancia < vertice.distancia)){
                return -1;
            }
            if(vertice.distancia != -1 && (this.distancia == -1 || this.distancia > vertice.distancia)){
                return 1;
            }
            return 0;
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            // Aquí va su código.
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            // Aquí va su código.
            return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            // Aquí va su código.
            return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            // Aquí va su código.
            return this.vecino.getColor();
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return this.vecino.vecinos;
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Diccionario<T, Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
        this.vertices = new Diccionario<T, Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return this.vertices.getElementos();
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
        vertices.agrega(elemento, v);
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
        this.aristas++;
        v1.vecinos.agrega(b, new Vecino(v2, 1.0));
        v2.vecinos.agrega(a, new Vecino(v1, 1.0));
    }
    /**
     * Busca el elemento dado en la lista de vertices
     * @param elemento - elemento a buscar
     * @return - eñ vértice de la lista
     */
    private Vertice buscaElemento(T elemento){
        for(Vertice v: this.vertices){
            if(v.elemento.equals(elemento))
                return v;
        }
        return null;
    }
        /**
     * Nos dice si un vértice está en la lista de un vértice
     * @param lista - lista a buscar
     * @param vertice - vertice a buscar
     * @return true si está, false en otro caso
     */
    private Vertice contieneLista(Lista<Vertice> lista,Vertice vertice) {
        for (Vertice w: lista) {
            if (vertice.elemento.equals(w.elemento)) 
                return w;
        }
        return null;
    }
    /**
     * Nos dice si un vecino está en la lista de un vértice
     * @param lista - lista a buscar
     * @param vertice - vertice a buscar
     * @return true si está, false en otro caso
     */
    private boolean contieneListaVecino(Lista<Vecino> lista,Vecino vertice) {
        for (Vecino w: lista) {
            if (vertice.vecino.elemento.equals(w.vecino.elemento)) 
                return true;
        }
        return false;
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        // Aquí va su código.
        Vertice s = buscaElemento(a);
        Vertice t = buscaElemento(b);

        if(peso <= 0){
            throw new IllegalArgumentException();
        }
        if(s == null || t == null){
            throw new NoSuchElementException();
        }
        if(s.equals(t)){
            throw new IllegalArgumentException();
        }
        if(sonVecinos(a, b)){
            throw new IllegalArgumentException();
        }
        aristas++;
        s.vecinos.agrega(b, new Vecino(t, peso));
        t.vecinos.agrega(a, new Vecino(s, peso));
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
        this.aristas--;
        v1.vecinos.elimina(esVecino(v1, v2).get());
        v2.vecinos.elimina(esVecino(v2, v1).get());
    }

    /**
     * Regresa el vecino de un vértice si está en la lista
     * @param v1 - lista
     * @param v2 - vertice a buscar
     * @return
     */
    private Vecino esVecino(Vertice v1, Vertice v2){
        for(Vecino v : v1.vecinos){
            if(v.vecino.equals(v2)){
                return v;
            }
        }
        return null;
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
        for(Vecino u : v.vecinos){
            desconecta(v.elemento, u.vecino.elemento);
        }
        vertices.elimina(elemento);
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
        if(esVecino(v1, v2) == null || esVecino(v2, v1) == null){
            return false;
        }
        return true;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        // Aquí va su código.
        Vertice v1 = buscaElemento(a);
        Vertice v2 = buscaElemento(b);
        if (v1 == null || v2 == null) { 
            throw new NoSuchElementException(); 
        }
        if(!sonVecinos(a, b)){
            throw new IllegalArgumentException();
        }
        Vecino vecino = esVecino(v1, v2);
        return vecino.peso;
    }


    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        // Aquí va su código.
        Vertice v1 = buscaElemento(a);
        Vertice v2 = buscaElemento(b);
        if (v1 == null || v2 == null) { 
            throw new NoSuchElementException(); 
        }
        if(!sonVecinos(a, b) || peso <= 0){
            throw new IllegalArgumentException();
        }
        esVecino(v1, v2).peso = peso;
        esVecino(v2, v1).peso = peso;

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
        if (vertice == null ||
        (vertice.getClass() != Vertice.class &&
         vertice.getClass() != Vecino.class)) {
        throw new IllegalArgumentException("Vértice inválido");
        }
        if (vertice.getClass() == Vertice.class) {
            Vertice v = (Vertice) vertice;
            v.color = color;
        }
        if (vertice.getClass() == Vecino.class) {
            Vecino v = (Vecino) vertice;
            v.vecino.color = color;
        }
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        // Aquí va su código.
        if(esVacia())
            return true;
        Cola<Vertice> metesaca = new Cola<Vertice>();
        Iterator<T> iterator = vertices.iteradorLlaves();
        T elemento = iterator.next();
        Vertice vertice = this.vertices.get(elemento);
        paraCadaVertice((v) -> setColor(v, Color.ROJO));
        vertice.color = Color.NEGRO;
        metesaca.mete(vertice);
        while(!metesaca.esVacia()){
            Vertice u = metesaca.saca();
            for(Vecino vecino : u.vecinos){
                if(vecino.vecino.color == Color.ROJO){
                    metesaca.mete(vecino.vecino);
                    vecino.vecino.color = Color.NEGRO;
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
     * Recorridos BFS o DFS
     * @param elemento - elemento a buscar
     * @param accion - accion a hacer
     * @param metesaca - cola o pila
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
            for(Vecino vecino : u.vecinos){
                if(vecino.vecino.color == Color.ROJO){
                    vecino.vecino.color = Color.NEGRO;
                    metesaca.mete(vecino.vecino);
                }
            }
        }
        paraCadaVertice(v -> setColor(v, Color.NINGUNO));
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
            for(Vecino v2 : v1.vecinos){
                if(v2.vecino.color != Color.NEGRO){
                    res += "(" + v1.elemento.toString() + ", " + v2.get().toString() + "), ";
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
        for(Vertice v : this.vertices){
            Vertice aux = grafica.vertices.get(v.elemento);
            if(aux == null)
                return false;
            else{
                for(Vecino vecinos : v.vecinos){
                    if(!aux.vecinos.contiene(vecinos.get()))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        // Aquí va su código.
        Vertice s = buscaElemento(origen);
        Vertice t = buscaElemento(destino);
        if(s == null || t == null){
            throw new NoSuchElementException();
        }
        Lista<VerticeGrafica<T>> trayectoria = new Lista<>();
        if(s == t){
            trayectoria.agrega(s);
            return trayectoria;
        }
        for(Vertice v : this.vertices){
            v.distancia = -1;
        }
        s.distancia = 0;
        Cola<Vertice> cola = new Cola<>();
        cola.mete(s);
        
        while(!cola.esVacia()){
            Vertice v = cola.saca();
            for(Vecino vec : v.vecinos){
                if(vec.vecino.distancia == -1){
                    vec.vecino.distancia = v.distancia + 1;
                    cola.mete(vec.vecino);
                }
            }
        }
        if(t.distancia == -1){
            return trayectoria;
        }
        trayectoria.agrega(t);

        while(!t.elemento.equals(s.elemento)){
            for(Vecino veci : t.vecinos){
                if(t.distancia == veci.vecino.distancia + 1){
                    trayectoria.agrega(veci.vecino);
                    t = veci.vecino;
                }
            }
        }
        return trayectoria.reversa();
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        // Aquí va su código.
        Vertice s = buscaElemento(origen);
        Vertice t = buscaElemento(destino);
        if(s == null || t == null){
            throw new NoSuchElementException();
        }
        Lista<VerticeGrafica<T>> trayectoria = new Lista<>();
        if(s == t){
            trayectoria.agrega(s);
            return trayectoria;
        }
        for(Vertice v : this.vertices){
            v.distancia = -1;
        }
        s.distancia = 0;
        MonticuloMinimo<Vertice> monticulo = new MonticuloMinimo<>(this.vertices, this.vertices.getElementos());
        while(!monticulo.esVacia()){
            Vertice aux = monticulo.elimina();  
            for(Vecino vs : aux.vecinos){
                if(vs.vecino.compareTo(aux) > 0){
                    vs.vecino.distancia = aux.distancia + vs.peso;
                    monticulo.reordena(vs.vecino);
                }
            }
        }
        if(t.distancia == -1){
            return trayectoria;
        }
        trayectoria.agrega(t);

        while(!t.elemento.equals(s.elemento)){
            for(Vecino v : t.vecinos){
                if(t.distancia == v.vecino.distancia + 1){
                    trayectoria.agrega(v.vecino);
                    t = v.vecino;
                }
            }
        }
        return trayectoria.reversa();
    }
}
