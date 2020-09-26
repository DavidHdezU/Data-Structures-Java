package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * 
     * @param <T>        tipo del que puede ser el arreglo.
     * @param arreglo    el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void quickSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        quickSort(arreglo, 0, arreglo.length - 1, comparador);
    }

    /**
     * Ordena el arreglo usando quickSort
     * 
     * @param <T>        el tipo del que puede ser el arreglo
     * @param arreglo    arreglo cuyos elementos son comparables
     * @param a          indice inicial del arreglo
     * @param b          indice final del arreglo
     * @param comparador comparador que ayuda a odenar el arreglo
     */
    private static <T> void quickSort(T[] arreglo, int a, int b, Comparator<T> comparador) {
        // El goal al final es que nuestro pivote tenga a la izquierda elementos <= y a
        // su derecha >
        if (b <= a) {
            return;
        }
        int i = a + 1;
        int j = b;
        while (i < j) {
            if (comparador.compare(arreglo[i], arreglo[a]) > 0 && comparador.compare(arreglo[j], arreglo[a]) <= 0) {
                intercambia(arreglo, i, j);
                i++;
                j--;
            } else if (comparador.compare(arreglo[i], arreglo[a]) <= 0) {
                i++;
            } else {
                j--;
            }
        }
        if (comparador.compare(arreglo[i], arreglo[a]) > 0) {
            i--;
        }
        intercambia(arreglo, a, i);
        quickSort(arreglo, a, i - 1, comparador);
        quickSort(arreglo, i + 1, b, comparador);

    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * 
     * @param <T>     tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * 
     * @param <T>        tipo del que puede ser el arreglo.
     * @param arreglo    el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void selectionSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        int m; // Suponemos que este es el minimo y mientras iteramos si encotramos un elemento
               // el < m hacemos ese elemento el nuevo minimo hasta acabar la iterción
        for (int i = 0; i < arreglo.length; i++) {
            m = i;
            for (int j = i + 1; j < arreglo.length; j++) {
                if (comparador.compare(arreglo[j], arreglo[m]) < 0) {
                    m = j;
                }
            }
            intercambia(arreglo, i, m);
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * 
     * @param <T>     tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice del
     * elemento en el arreglo, o -1 si no se encuentra.
     * 
     * @param <T>        tipo del que puede ser el arreglo.
     * @param arreglo    el arreglo dónde buscar.
     * @param elemento   el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        // Aquí va su código.
        return busquedaBinaria(arreglo, 0, arreglo.length - 1, elemento, comparador);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice del
     * elemento en el arreglo, o -1 si no se encuentra.
     * 
     * @param <T>      tipo del que puede ser el arreglo.
     * @param arreglo  un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice del
     * elemento en el arreglo, o -1 si no se encuentra.
     * 
     * @param <T>        tipo del que puede ser el arreglo
     * @param arreglo    arreglo cuyos elementos son comparables
     * @param a          el indice inicial del arreglo
     * @param b          el indice final del arreglo
     * @param e          el elemento a buscar
     * @param comparador comparador de elementos
     * @return el indice del elemtno del elemento del arreglo, o -1 si no está
     */
    private static <T> int busquedaBinaria(T[] arreglo, int a, int b, T e, Comparator<T> comparador) {
        if (b < a) {
            return -1;
        }
        if (comparador.compare(arreglo[(a + b) / 2], e) == 0) {
            return (a + b) / 2;
        }
        if (comparador.compare(arreglo[(a + b) / 2], e) < 0) {
            return busquedaBinaria(arreglo, ((a + b) / 2) + 1, b, e, comparador);
        }
        return busquedaBinaria(arreglo, a, ((a + b) / 2) - 1, e, comparador);
    }

    /**
     * Intercambia elementos de un arreglo
     * 
     * @param <T>     tipo del que puede ser el arreglo
     * @param arreglo arreglo cuyos elementos son comparables
     * @param i       indice i del arreglo
     * @param j       indice j del arreglo
     */
    private static <T> void intercambia(T[] arreglo, int i, int j) {
        T elementoI = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = elementoI;

    }
}