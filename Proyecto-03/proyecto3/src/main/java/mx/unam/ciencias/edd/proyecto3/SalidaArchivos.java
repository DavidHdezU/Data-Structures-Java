package main.java.mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.proyecto3.EntradaConsola;
import mx.unam.ciencias.edd.Conjunto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.io.FileReader;
import java.io.IOException;
public class SalidaArchivos {
    private EntradaConsola consola;

    /**
     * Constructor comun
     * @param args - argumentos de entrada
     */
    public SalidaArchivos(String[] args){
        this.consola = new EntradaConsola(args);
    }
    /**
     * Metodo que genera el HTML de un archivp
     * @param doc
     * @param dir
     */
    private void generaHTML(GeneradorDocumento doc, String dir) {
        String reportFilename = doc.nombre + ".html";
        File file = new File(dir, reportFilename);
        String content = doc.generaArchivo();
        doc.file = file;
        try {
            FileWriter fw = new FileWriter(file);
                fw.write(content);
                fw.close();
        } catch (Exception e) {
            System.err.println("Hubo un error al crear " + file.getAbsolutePath());
        }
    }

    /**
     * Metodo que se encarga de generar el proyecto
     */
    public void generarProyecto(){
        Conjunto<GeneradorDocumento> docs = this.consola.generaArchivos();

        for(GeneradorDocumento doc : docs){
            doc.cuentaPalabras();
        }

        String directorio = this.consola.creaDirectorio();

        for(GeneradorDocumento doc : docs){
            System.out.println("Escribiendo reporte para: " + doc.nombre);
            generaHTML(doc, directorio);
        }

        Index index =  new Index(docs);
        File indexArchivo = new File(directorio, "index.html");
        String contenidoIndex = index.generaArchivo();

        try {
            FileWriter fw = new FileWriter(indexArchivo);
                fw.write(contenidoIndex);
                fw.close();
        } catch (Exception e) {
            System.err.println("Hubo un error al crear: " + indexArchivo.getAbsolutePath());
        }
        getRecursosArchivos(directorio, "css/main.css", "main.css");
        cargaImagenes(directorio, "images/bg.jpg", "bg.jpg");
        for(int i = 1; i <= 5; i++){
            cargaImagenes(directorio, "images/image0" + i + ".jpg", "image0" + i + ".jpg");
        }

    }

    /**
     * Metodo que crea el CSS
     * @param dir - directorio
     * @param archivo - archivo a buscar
     * @param nombre - nuevo nombre
     */
    private void getRecursosArchivos(String dir, String archivo, String nombre){
        try{
            InputStream inputStream = getClass()
            .getClassLoader().getResourceAsStream(archivo);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            File css = new File(dir,nombre);
            FileWriter fw = new FileWriter(css);
            String lineas;
            while((lineas = bf.readLine()) != null){
                fw.write(lineas);
            }
            fw.close();
            bf.close();
        }catch(Exception e){
            System.err.println("Error al leer: " + archivo);
        }
    }

    /**
     * Metodo que crea imagenes
     * @param dir - directorio
     * @param archivo - archivo a buscar
     * @param nombre - nuevo nombre
     */
    private void cargaImagenes(String dir, String archivo, String nombre){
        try{
            InputStream inputStream = getClass()
            .getClassLoader().getResourceAsStream(archivo);
            File css = new File(dir,nombre);
            int c;
            OutputStream destino =  new FileOutputStream(css);
            while((c = inputStream.read()) != -1){
                destino.write(c);
            }
            destino.close();
        }catch(Exception e){
            System.err.println("Error al leer: " + archivo);
        }
    }
}