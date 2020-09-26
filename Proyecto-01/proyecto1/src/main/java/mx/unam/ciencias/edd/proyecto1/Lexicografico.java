package mx.unam.ciencias.edd.proyecto1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import mx.unam.ciencias.edd.*;;

/**
 * 
 * <p>
 * Clase que ordena una lista, linea por linea lexiográficamente
 * </p>
 */
public class Lexicografico {
    /**
     * Constructor por omisión
     */
    public Lexicografico() {
    }

    /** Compardor que compara lexiográficamente las lineas de la lista */
    public Comparator<CadenaP1> comparator = new Comparator<CadenaP1>() {
        /**
         * Compara 2 lineas lexiográficamente
         * 
         * @param p1 - Linea 1
         * @param p2 - Linea2
         */
        @Override
        public int compare(CadenaP1 p1, CadenaP1 p2) {
            return p1.compareTo(p2);
        }
    };

    /**
     * Ordena una lista mediante mergeSort
     * 
     * @param l - Lista a ordenar
     * @return - Una lista ordenada lexiográficamente
     */
    public Lista<CadenaP1> ordena(Lista<CadenaP1> l) {
        return l.mergeSort(comparator);
    }

    /**
     * Lee archivos y agraga todas sus lineas a una sola lista
     * 
     * @param args - Los argumentos de la linea de comandos, sólo se toma en cuenta
     *             los archivos existentes
     * @return - Una lista con todas las lineas de los archivos leidos
     * @throws IOException si el archivo no tiene permisos de lectura
     */
    public Lista<CadenaP1> agrega(String[] args, String guarda) {
        Lista<CadenaP1> lista = new Lista<>();
        File archivos = null;
        try {
            for (String s : args) {
                archivos = new File(s);
                if (archivos.exists() && !(s.equals("-r") || s.equals("-o"))) {
                    FileInputStream fis = new FileInputStream(archivos);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader bf = new BufferedReader(isr);
                    String cadena;
                    while ((cadena = bf.readLine()) != null) {
                        lista.agrega(new CadenaP1(cadena));
                    }
                    bf.close();
                } else {
                    if (!archivos.canRead()) {
                        if (s.equals("-o") || s.equals("-r") || s.equals(guarda))
                            continue;
                        System.out.println("No existe el archivo: " + archivos.getAbsolutePath() + "\n");
                    }
                }
            }
        } catch (IOException ioe) {
            System.err.println("El archivo: " + archivos.getAbsolutePath() + " no tiene permisos de lectura" + "\n");
            System.exit(1);
        }

        return lista;
    }

}
