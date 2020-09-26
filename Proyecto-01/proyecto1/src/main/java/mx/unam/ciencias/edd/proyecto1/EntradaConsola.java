package mx.unam.ciencias.edd.proyecto1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import mx.unam.ciencias.edd.Lista;

/**
 * <p>
 * Clase que modela la lectura de la entrada estandar
 * </p>
 */
public class EntradaConsola {
    /**
     * Constructor por omisión
     */
    public EntradaConsola() {

    }

    /**
     * Lee la consola y ordena lo que se le pase
     * 
     * @return lista ordenada
     * @throws IOException si hay un error en la entrada
     */
    public Lista<CadenaP1> leer() {
        Lista<CadenaP1> lista = new Lista<>();
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            String s;
            while ((s = br.readLine()) != null) {
                lista.agrega(new CadenaP1(s));
            }
            br.close();
        } catch (IOException ioe) {
            System.err.println("Error al momento de leer entrada");
            System.exit(1);
        }
        return lista;
    }
}