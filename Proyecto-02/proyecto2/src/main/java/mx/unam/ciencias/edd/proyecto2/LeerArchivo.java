package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.io.BufferedReader;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase para leer un archivo
 */
public class LeerArchivo {
    /** Lista donde irán todos los argumentos de args[] */
    private Lista<String> lista;
    /** Estructura a dibujar */
    private EstructuraSVG estructuraSVG;
    /** Verifica que queremos guardar el archivo */
    private boolean guardarArchivo;
    /** Nombre del nuevo archivo */
    private String archivoGuardar;
    /** Bandera para saber si queremos guardar */
    private final static String BANDERA = "-o";

    /**
     * Constructor, iniciamos a lista
     * @param lista - lista a usar
     */
    public LeerArchivo(Lista<String> lista){
        this.lista = lista;
    }

    /**
     * Determina la estructura a dibujar
     * @param s - cadena a evaluar
     */
    public void determinaEstructura(String s){
        switch(s){
            case "arbolavl":
                this.estructuraSVG = new ArbolAVLS_SVG();
                break;
            case "arbolrojinegro":
                this.estructuraSVG = new ArbolRojiNegroSVG();
                break;
            case "arbolordenado":
                this.estructuraSVG = new ArbolOrdenadoSVG();
                break;
            case "arbolcompleto":
                this.estructuraSVG = new ArbolCompletoSVG();
                break;
            case "lista":
                this.estructuraSVG = new ListaSVG();
                break;
            case "cola":
                this.estructuraSVG = new ColaSVG();
                break;
            case "pila":
                this.estructuraSVG = new PilaSVG();
                break;
            case "grafica":
                this.estructuraSVG = new GraficaSVG();
                break;
            case "arreglo":
                this.estructuraSVG = new ArregloSVG();
                break;
            case "monticuloarreglo":
                this.estructuraSVG = new MonticuloArregloSVG();
                break;
            case "monticulominimo":
                this.estructuraSVG = new MonticuloMinimoSVG();
                break;
            default:
                menu();
        }
    }
    /**
     * Guarda el archivo
     * @param s - nombre del nuevo archivo
     */
    public void guardarArchivo(String s){
        try{
            File archivo = new File(this.archivoGuardar);
            FileWriter escribe = new FileWriter(archivo + ".html");
            BufferedWriter bf =  new BufferedWriter(escribe);
            bf.write(s + "\n");
            bf.close();
            System.out.println("Se ha creado el nuevo archivo: " + archivo.getAbsolutePath() );

        }catch(IOException ioe){
            System.out.println("Error al guardar archivo...");
        }
    }

    /**
     * Agrega todos los argumentos del archivo a nuestra lista
     * @param args - argumentos de la entrada
     */
    public void agrega(String[] args) {
        File archivos = null;
        int cont = 0;
        try {
            for (String s : args) {
                archivos = new File(s);
                if (archivos.exists() && !s.equals(BANDERA)) {
                    cont++;
                    if(cont > 1){
                        System.err.println("No podemos gráficar 2 estructuras, por favor sólo introduce una!");
                        System.exit(-1);
                    }
                    FileInputStream fis = new FileInputStream(archivos);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader bf = new BufferedReader(isr);
                    String cadena;
                    while ((cadena = bf.readLine()) != null) {
                        if(gatitoIgnora(cadena)){
                            this.lista.agrega(cadena);
                        }
                    }
                    bf.close();
                }else {
                    if (!archivos.canRead()) {
                        if (s.equals(BANDERA) || s.equals(this.archivoGuardar))
                            continue;
                        System.out.println("No existe el archivo: " + archivos.getAbsolutePath() + "\n");
                    }
                }
            }
        } catch (IOException ioe) {
            System.err.println("El archivo: " + archivos.getAbsolutePath() + " no tiene permisos de lectura" + "\n");
            System.exit(1);
        }
    }
    /**
     * Método auxiliar que sirve para saber si hay un "#" en una de las lineas
     * @param s - linea a evaluar
     * @return <code>true</code> si no hay "#" <code>false</code> en otro caso
     */
    private boolean gatitoIgnora(String s){
        String res = s.replaceAll("\t", "");
        res = res.replaceAll(" ", "");
        if(res.length() > 0){
            if(res.substring(0, 1).equals("#")){
                return false;
            }
        }
        return true;
    }

    /**
     * Veriica las banderas
     * @param args - argumentos
     */
    public void verificaArgumentos(String[] args){
        if(args.length > 0){
            for(int i = 0; i < args.length; i++){
                if(args[i].equals(BANDERA)){
                    guardarArchivo = true;
                    if(i + 1 < args.length){
                        archivoGuardar = args[i+1];
                        i++;
                    }
                }
            }
        }
    }
    /**
     * Verfica que exista archivo de salida
     */
    private void verficaGuarda() {
        if (this.archivoGuardar == null) {
            System.err.println("Para guardar un archivo la bandera -o necesita un argumeto...");
            System.exit(1);
        }
    }
    /**
     * Nos da la lista de elementos a partir de nuestra lista original
     * @return Lista<Integer> lista de los elementos a usar para la estructura
     */
    public Lista<Integer> getIntegers(){
        Lista<Integer> numbers = new Lista<>();
        for(String str : this.lista){   
            str = str.replaceAll("[^-?0-9]+", " "); 
            String[] arreglos = str.trim().split(" ");
            if(arreglos.length == 0)
                arreglos = null; // Espero que ocupe menos espacio en memoria
            for(String s : arreglos){
                if(s.length() > 0){
                    try{
                        numbers.agrega(Integer.valueOf(s));
                    }catch(NumberFormatException nfe){
                        System.err.println("No se pueden leer/añadir números con más de 10 dígitos, por favor verifique los datos a agregar");
                        System.exit(-1);
                    }
                }
            }
        }
        return numbers;
    }    
    /**
     * Dibuja la estrucutura ingresada
     * @return String - la cadena svg de la estructura
     */
    public String dibujaEstructura(){
        String s = "";
        try{
            String x = this.lista.getPrimero();
            x = x.replaceAll("[0-9]", "");
            x = x.trim().toLowerCase();
            determinaEstructura(x)  ;
            s = estructuraSVG.toSVG(getIntegers());
        }catch(NoSuchElementException | NullPointerException e){
            System.exit(-1);
        }
        return s;
    }
    /**
     * Procesa toda la entrada de los argumentos
     * @param args -  argumentos de entrada
     */
    public void imprimeSVG(String[] args){
        verificaArgumentos(args);
        longArgs(args);
        if(args.length > 0){
            agrega(args);
            if(guardarArchivo == true){
                guardarArchivo(dibujaEstructura());
                return;
            }else{
                System.out.println(dibujaEstructura());
            }
        }
    }
    /**
     * Verifica la longitud de args[] y manda a llamar a la entrada estandar si se requiere
     * @param args - argumentos de entrada
     */
    private void longArgs(String[] args){
        if(args.length == 0){
            System.out.println("Por favor introduzca los datos a gráficar: ");
            EntradaConsola consola = new EntradaConsola(this.lista);
            consola.leer();
            if(this.lista.getLongitud() == 1){
                String[] arreglo = this.lista.getPrimero().split(" ");
                this.lista.limpia();
                for(int i = 0; i < arreglo.length; i++){
                    this.lista.agrega(arreglo[i]);
                    }
            }
            System.out.println(dibujaEstructura());
            return;
        }else if(args.length == 1){
            if(args[0].equals(BANDERA)){
                verficaGuarda();
            }
        }else if(args.length == 2){
            if(args[0].equals(BANDERA) && args[1].equals(this.archivoGuardar) && guardarArchivo == true){
                EntradaConsola consola = new EntradaConsola(this.lista);
                consola.leer();
                if(this.lista.getLongitud() == 1){
                    String[] arreglo = this.lista.getPrimero().split(" ");
                    this.lista.limpia();
                    for(int i = 0; i < arreglo.length; i++){
                        this.lista.agrega(arreglo[i]);
                        }
                }
                return;
            }
        }
    }
    /**
     * Enseña como debe de usarse el programa
     */
    public void menu(){
        System.out.println("Para usar el prógrama hay 2 formas: ");
        System.out.println("\t" + "- Entrada estandar" + " \n" + "\t" + "- Leyendo un archivo");
        System.out.println("Para usar la entrada estandar debemos especifcar el nombre de la estructura de datos y después separar por espacios los elementos a ingresar, siempre deben de ser enteros");
        System.out.println("\t" + "Ejemplo: " + "ArbolAVL 1 2 3 4 5 6 7 8 9 10");
        System.out.println("Para lectura por archivo necesitamos que dentro del archivo esté escrito el nombre de la estructura y después sus elementos" + "\n" 
         + "el caso de las gráficas el número de elementos debe de ser par");
        System.out.println("Estructuras disponibles para gráficar: ");
        System.out.println("\n\t" + "ArbolCompleto");
        System.out.println("\n\t" + "ArbolAVL");
        System.out.println("\n\t" + "ArbolRojingro");
        System.out.println("\n\t" + "ArbolOrdenado");
        System.out.println("\n\t" + "ArbolCompleto");
        System.out.println("\n\t" + "Listas");
        System.out.println("\n\t" + "Arreglos");
        System.out.println("\n\t" + "Pilas y Colas");
        System.out.println("\n\t" + "Monticulos Minimos y Monticulos Arreglos");
        System.out.println("\n\t" + "Gráficas");
    }
    /**
     * Método main
     * @param args - argumentos de entrada
     */
    public static void main(String[] args) {
        Lista<String> lis = new Lista<>();
        LeerArchivo le = new LeerArchivo(lis);
        le.imprimeSVG(args);
    }
}