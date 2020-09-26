package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class EntradaConsola {
    private Lista<String> lista;
        /**
     * Constructor por omisión
     */
    public EntradaConsola(Lista<String> lista) {
        this.lista = lista;
    }

    /**
     * Lee la consola y ordena lo que se le pase
     * 
     * @return lista ordenada
     * @throws IOException si hay un error en la entrada
     */
    public void leer() {
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            String s;
            while ((s = br.readLine()) != null) {
                if(!gatitoIgnora(s).equals("")){
                    this.lista.agrega(s);
                }
            }
            br.close();
        } catch (IOException ioe) {
            System.err.println("Error al momento de leer entrada");
            System.exit(-1);
        }
    }
    private String gatitoIgnora(String s){
        String res = s.replaceAll("\t", "");
        res = res.replaceAll(" ", "");
        if(res.length() > 0){
            if(res.substring(0, 1).equals("#")){
                return "";
            }
        }
        return s;
    }

}