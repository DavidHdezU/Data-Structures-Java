package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.text.Normalizer;
import main.java.mx.unam.ciencias.edd.proyecto3.GeneradorDocumento;
import mx.unam.ciencias.edd.Conjunto;

import mx.unam.ciencias.edd.Diccionario;

public class EntradaConsola {
    private static final String BANDERA = "-o";
    public String directorio;
    public boolean seGuarda;
    private Conjunto<String> archivos;

    /**
     * Constructor por omisión
     * @param args - argumentos de la consola
     */
    public EntradaConsola(String[] args) {
        this.archivos = new Conjunto<>();
        verificaArgumentos(args);
    }


    /**
     * Veriica las banderas
     * @param args - argumentos
     */
    public void verificaArgumentos(String[] args){
        if(args.length >= 3){
            for(int i = 0; i < args.length; i++){
                if(args[i].equals(BANDERA)){
                    if(i + 1 < args.length){
                        this.seGuarda = true;
                        this.directorio = args[i+1];
                        i++;
                    }
                }else{
                    this.archivos.agrega(args[i]);
                }
            }
            if(this.seGuarda == false){
                System.err.println("No ingreso el directorio a usar");
                uso();
                System.exit(-1);
            }
        }
    }
    private void uso(){
        String res = "Para poder usar el programa tenemos que pasar los archivos a analizar y también añadir la bandera -o y después de ella el directorio" + "\n";
        res += "También se puede poner los archivos despues del directorio :)";
        System.out.println(res);
    }
    /**
     * Regresa el conjunto de archivos a analizar
     * @return - archivos a analizar
     */
    public Conjunto<String> getArchivos(){
        return this.archivos;
    }

    public String creaDirectorio(){
        File dir = new File(this.directorio);
        if(!dir.exists() || !dir.isDirectory()) {
            if(!dir.mkdirs()){
                System.err.println("Hubo un error al crear el directorio");
                System.exit(-1);
            }
        }
        return dir.getAbsolutePath();
    }

    /**
     * Metodo que lee los archivos y genera nuevos con el analisis hecho
     * @return - conjunto de archivos analizados
     */
    public Conjunto<GeneradorDocumento> generaArchivos(){
        Conjunto<GeneradorDocumento> docs = new Conjunto<>();

        int cont = 1;
        for(String archivo :  this.archivos){
            Lista<String> lineas = new Lista<>();
            File file = new File(archivo);
            try{
                FileInputStream fileI = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fileI);
                BufferedReader bf = new BufferedReader(isr);
                String cadena;
                while((cadena = bf.readLine()) != null){
                    lineas.agrega(cadena);
                }
                bf.close();
                cont++;
                GeneradorDocumento doc = new GeneradorDocumento(file.getName(), lineas, cont);
                docs.agrega(doc);
            }catch(IOException e){
                System.err.println("No existe el archivo: " + file.getAbsolutePath());
                uso();

                System.exit(-1);
            }
        }
        return docs;
    }
}