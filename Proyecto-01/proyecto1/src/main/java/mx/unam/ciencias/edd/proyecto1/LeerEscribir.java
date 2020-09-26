package mx.unam.ciencias.edd.proyecto1;

import java.io.*;
import mx.unam.ciencias.edd.*;

/**
 * <p>
 * Clase que modela un archivo para leer o escribir
 * </p>
 */
public class LeerEscribir {
    /** Lista que guarda las lineas de los archivos leidos */
    private Lista<CadenaP1> archivo;
    /** Verifica si existe la bandera -r */
    private boolean reversa;
    /** Verifica si existe la bandera -o */
    private boolean escribe;
    /** El nombre del archivo a crear */
    private static String guardar;

    public LeerEscribir() {
    }

    /**
     * Crea un nuevo archivo
     * 
     * @param lista la lista que se usará para imprimir los renglones en el nuevo
     *              archivo
     * @param nueva el nombre del archivo a crear
     * @throws IOException si hay un error en la escritura
     */
    public void archivoNuevo(Lista<CadenaP1> lista, String nueva) {
        File nuevo = new File(nueva);
        nuevo.delete();
        try {

            FileWriter fw = new FileWriter(new File(nueva));
            BufferedWriter bf = new BufferedWriter(fw);
            for (CadenaP1 cadenaP1 : lista) {
                bf.append(cadenaP1.toString() + "\n");
            }
            bf.close();
            System.out.print("Se ha creado o reescrito el " + nueva + " archivo");

        } catch (IOException ioe) {
            System.out.println("Error al guardar archivo...");
        }
    }

    /**
     * Lee un o más archivos y guarda sus lineas en una lista
     * 
     * @param args Los argumentos de la linea de comandos
     * @param s    el nombre del archivo a crear, en caso de que se use el metodo
     *             archivoNevo
     * @return Lista<CadenaP1> una lista ordenada
     */
    public Lista<CadenaP1> leeArchivo(String[] args, String s) {
        Lista<CadenaP1> lista = new Lista<>();
        Lexicografico ordenador = new Lexicografico();
        lista = ordenador.agrega(args, s);
        return lista;
    }

    /**
     * Verifica si existe la bandera -r
     * 
     * @param s Cadena a verificar
     * @return <code>true</code> si <code>s</code> es -r, <code>false</code> en otro
     *         caso.
     */
    private boolean reversa(String s) {

        if (s.equals("-r")) {
            return true;
        }

        return false;
    }

    /**
     * Verifica si existe la bandera -o
     * 
     * @param s Cadena a verificar
     * @return <code>true</code> si <code>s</code> es -o, <code>false</code> en otro
     *         caso.
     */
    private boolean guarda(String s) {

        if (s.equals("-o")) {
            return true;
        }

        return false;
    }

    /**
     * Verifica si existen las banderas y el archivo a guardar
     * 
     * @param args la linea de comandos
     */
    private void banderas(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (reversa(args[i])) {
                reversa = true;
            } else {
                if (guarda(args[i])) {
                    escribe = true;
                    if (i + 1 < args.length) {
                        guardar = args[i + 1];
                        i++;
                    } else {
                        guardar = null;
                    }
                }
            }
        }
    }

    /**
     * Verifica que <code>guardar</code> no sea null
     * 
     * @param guardar el archivo a guardar
     */
    private void verficaGuarda(String guardar) {
        if (guardar == null) {
            System.err.println("Para guardar un archivo la bandera -o necesita un argumeto...");
            System.exit(1);
        }
    }

    /**
     * Verifica que no haya error dependiendo de la longitud de los argumentos y las
     * bamderas
     * 
     * @param args - Argumentos
     */
    private void longitudArgumentos(String[] args) {
        EntradaConsola consola = new EntradaConsola();
        if (args.length == 0) {
            archivo = consola.leer();
        } else if (args.length == 1) {
            if (reversa) {
                archivo = consola.leer();
                archivo = archivo.reversa();
                return;
            } else if (escribe) {
                verficaGuarda(guardar);
                archivo = consola.leer();
                archivoNuevo(archivo, guardar);
                return;
            }
        } else if (args.length == 2) {
            if (reversa(args[0]) && guarda(args[1])) {
                verficaGuarda(guardar);
            } else if (reversa(args[1]) && guarda(args[0])) {
                guardar = "-r";
                archivo = consola.leer();
                archivo = archivo.reversa();
                archivoNuevo(archivo, guardar);
                return;
            } else if (guarda(args[0]) && guarda(args[1])) {
                guardar = "-o";
                archivo = consola.leer();
                archivoNuevo(archivo, guardar);
                return;
            } else if (guarda(args[0]) && args[1].equals(guardar)) {
                archivo = consola.leer();
                archivoNuevo(archivo, guardar);
                return;
            }
        } else if (args.length == 3) {
            if (reversa(args[0]) && guarda(args[1])) {
                if (reversa(args[2]))
                    guardar = "-r";
                if (guarda(args[2]))
                    guardar = "-o";
                archivo = consola.leer();
                archivo = archivo.reversa();
                archivoNuevo(archivo, guardar);
                return;
            } else if (reversa(args[1]) && guarda(args[0])) {
                guardar = "-r";
                if (reversa(args[2])) {
                    archivo = consola.leer();
                    archivo = archivo.reversa();
                    archivoNuevo(archivo, guardar);
                    return;
                }
            }
        }
    }

    /**
     * Verifica los parametros de la linea de comandos
     * 
     * @param args linea de comandos
     */
    public void verificaArgumentos(String[] args) {
        Lexicografico oLexicografico = new Lexicografico();
        banderas(args);
        archivo = leeArchivo(args, guardar);

        longitudArgumentos(args);

        archivo = oLexicografico.ordena(archivo);
        if (reversa && escribe) {
            verficaGuarda(guardar);
            archivoNuevo(archivo.reversa(), guardar);
            return;
        } else if (reversa && !escribe) {
            for (CadenaP1 s : archivo.reversa()) {
                System.out.println(s.toString());
            }
            return;
        } else if (!reversa && escribe) {
            verficaGuarda(guardar);
            archivoNuevo(archivo, guardar);
            return;
        } else {
            for (CadenaP1 s : archivo) {
                System.out.println(s.toString());
            }
            return;
        }

    }
}