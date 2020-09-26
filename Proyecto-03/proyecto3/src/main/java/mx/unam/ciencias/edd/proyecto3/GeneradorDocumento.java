package main.java.mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Conjunto;
import mx.unam.ciencias.edd.proyecto3.svg.ArbolAVLS_SVG;
import mx.unam.ciencias.edd.proyecto3.svg.ArbolRojiNegroSVG;
import main.java.mx.unam.ciencias.edd.proyecto3.svg.GraficaBarras;
import main.java.mx.unam.ciencias.edd.proyecto3.Palabra;
import main.java.mx.unam.ciencias.edd.proyecto3.svg.GraficaPastel;
import main.java.mx.unam.ciencias.edd.proyecto3.ComparadorPalabras;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.text.Normalizer;
import java.util.Iterator;
import mx.unam.ciencias.edd.Diccionario;

public class GeneradorDocumento {
    private Diccionario<String, Integer> diccionario;
    private Lista<String> lineas;
    public String nombre;
    public int numero;
    public int contadorPalabras = 0;
    private ArbolRojiNegroSVG arbolRN;
    private ArbolAVLS_SVG arbolAVL;
    public Lista<Palabra> palabras;
    private GraficaPastel graficaPastel;
    private GraficaBarras graficaBarras;
    public Conjunto<String> palabrasLongitudSiete;
    public File file;
    public String directorio;
    /**
     * Constructor comun
     * @param nombre - nombre del archivo
     * @param lineas - lista de palabras
     * @param numero - numero de archivo
     */
    public GeneradorDocumento(String nombre, Lista<String> lineas, int numero){
        this.diccionario = new Diccionario<>();
        this.palabras = new Lista<>();
        this.arbolRN = new ArbolRojiNegroSVG();
        this.arbolAVL = new ArbolAVLS_SVG();
        this.graficaPastel = new GraficaPastel();
        this.graficaBarras = new GraficaBarras();
        this.palabrasLongitudSiete = new Conjunto<>();
        this.nombre = nombre;
        this.lineas = lineas;
        this.numero = numero;
    }

    /**
     * Metodo que genera el reporte HTML
     * @return - reporte HTML del archivo
     */
    public String generaArchivo(){
        String res = "<!DOCTYPE HTML>" + "\n";
        res += "<html>" + "\n";
        res += "\t<head>" + "\n";
        res += "\t<style>" + "\n";
        res += "\t\t" + estilosBotones() + "\n";
        res += "\t\t" + stylePastel() + "\n";
        res += "\t</style>" + "\n";
        res += "\t\t<title>" + this.nombre + "</title>" + "\n";
        res += "\t\t<meta charset=" + '"' + "utf-8" + '"' + " />" + "\n";
        res += "\t\t<meta name=" + '"' + "viewport"+ '"' +" content=" + '"' + "width=device-width, initial-scale=1" +'"' + " />" + "\n";
        res += "\t\t<link rel=" + '"' + "stylesheet" + '"' + " href=" + '"' +  "main.css" + '"' +" />" + "\n";
        res += "\t</head>" + "\n";
        res += "\t<body>" + "\n";
        res += "\t\t<header id=" + '"' + "header" + '"' + ">" + "\n";
        res += "\t\t\t<div class=" + '"' + "logo" + '"' + "><a href=" + '"' + "#" + '"' +">" + this.nombre + "<span></span></a></div>" + "\n";
        res += "\t\t</header>" + "\n";
        res += "\t\t<section id="+ '"' + "main" + '"' + ">" +"\n";
        res += "\t\t\t<div class= " + '"' + "inner" + '"' + ">" + "\n";
        res += "\t\t\t\t<section id=" + '"' + "two" + '"' + " class=" + '"' + "wrapper style1" + '"' + ">" + "\n";
        res += "\t\t\t\t\t<header>" + "\n";
        res += "\t\t\t\t\t\t<div class=" + '"' + "row 200%" + '"' + ">" + "\n";
        res += "\t\t\t\t\t\t\t<div class=" + '"' + "6u 12u$(medium)" + '"' +  ">" + "\n";
        res += "\t\t\t\t\t\t\t\t" + tabla();
        res += "\t\t\t\t\t\t\t</div>" + "\n";
        res += "\t\t\t\t\t\t\t<div class=" + '"' + "6u$ 12u$(medium)" + '"' +  ">" + "\n";
        res += "\t\t\t\t\t\t\t\t" + botonesPalabrasComunes() + "\n";
        res += "\t\t\t\t\t\t\t\t\t<section id=" + '"' + "barras" + '"' + " class=" + '"' + "wrapper style2" + '"' + ">" + "\n";
        res += "\t\t\t\t\t\t\t\t\t\t" + "<a href= " + '"' + "#" + '"' + " class=" + '"' + "button fit" + '"' + "> Gráfica barras" + "</a>" + "\n";
        res += this.graficaBarras.toSVGFloat(listaPorcentaje()) + "\n";
        res += "\t\t\t\t\t\t\t\t\t</section>" + "\n";
        res += "\t\t\t\t\t\t\t\t\t<section id=" + '"' + "pastel" + '"' + " class=" + '"' + "wrapper style1" + '"' + ">" + "\n";
        res += "\t\t\t\t\t\t\t\t\t\t" + "<a href= " + '"' + "#" + '"' + " class=" + '"' + "button fit" + '"' + "> Gráfica pastel" + "</a>" + "\n";
        res += this.graficaPastel.toSVGFloat(listaPorcentaje()) + "\n";
        res += "\t\t\t\t\t\t\t\t\t</section>" + "\n";
        res += "\t\t\t\t\t\t\t</div>" + "\n";
        res += "\t\t\t\t\t\t</div>" + "\n";
        res += "\t\t\t\t\t</header>" + "\n";
        res += "\t\t\t\t</section>" + "\n";
        res += "\t\t\t</div>" + "\n";
        res += "\t\t</section>" + "\n";
        res += arboles() + "\n";
        res += "\t</body>" + "\n";
        res += "</html>";


        return res;
    }

    /**
     * Metodo que da una tabla de las palabras y su conteo
     * @return - tabla de palabras
     */
    public String tabla(){
        String res = "<h3> Palabras y veces que aparecen</h3>" + "\n";
        res += "<div class = " + '"' + "table-wrapper" +'"' +">" + "\n";
        res += "\t<table>" + "\n";
        res += "\t\t<thead>" + "\n";
        res += "\t\t\t<tr>" + "\n";
        res += "\t\t\t\t<th>Palabra</th>" + "\n";
        res += "\t\t\t\t<th>Veces repetidas</th>" + "\n";
        res += "\t\t\t</tr>" + "\n";
        res += "\t\t</thead>" + "\n";
        res += "\t\t<tbody>" + "\n";

        for(Palabra p : this.palabras){
            res += "\t\t\t<tr>" + "\n" + "\t\t\t\t<td>" + p.palabra + "</td>" + "\n" + "\t\t\t\t<td>" + p.cont + "</td>" + "\n" + "\t\t\t</tr>" + "\n"; 
        }
        res += "\t\t</tbody>" + "\n";
        res += "\t\t<tfoot>" + "\n";
        res += "\t\t</tfoot>" + "\n";
        res += "\t</table>" + "\n";
        res += "</div>" + "\n";
        
        return res;
    }

    /**
     * Método que da botones estilizados de las 15 palabras más comunes 
     * @return - botones de las palabras
     */
    public String botonesPalabrasComunes(){
        int id = 1;
        String res = "<h3>Palabras más comunes</h3>" + "\n";
        float cont = 0;
        if(getPalabrasComunes().getLongitud() >= 15){
            for(int i = 0; i < getPalabrasComunes().getLongitud(); i++){
                String textFormat = String.format("%.2f", getPalabrasComunes().get(i).porcentaje);
                res += "\t<a href = " + '"' + "#" + '"' + " class = " + '"' + "button special" +  id + '"' + ">" + getPalabrasComunes().get(i).palabra + ": " + textFormat + "%" + "</a>" + "\n";
                id++;
                cont += getPalabrasComunes().get(i).porcentaje;

            }
            String resto = String.format("%.2f", 100-cont);
            res += "\t<a href = " + '"' + "#" + '"' + " class = " + '"' + "button special" +  16 + '"' + ">" + "Otras palabras: " + resto + "%" + "</a>" + "\n";
        }else{
            for(int i = 0; i < getPalabrasComunes().getLongitud() - 1; i++){
                String textFormat = String.format("%.2f", getPalabrasComunes().get(i).porcentaje);
                res += "\t<a href = " + '"' + "#" + '"' + " class = " + '"' + "button special" +  id + '"' + ">" + getPalabrasComunes().get(i).palabra + ": " + textFormat + "%" + "</a>" + "\n";
                id++;
            }
            String ultimo = String.format("%.2f", getPalabrasComunes().getUltimo().porcentaje);
            res += "\t<a href = " + '"' + "#" + '"' + " class = " + '"' + "button special" +  16 + '"' + ">" + getPalabrasComunes().getUltimo().palabra +": " +  ultimo + "%" +"</a>" + "\n";

        }
        return res;
    }

    /**
     * Metodo que normaliza una linea de palabras y nos regresa un arreglo de las palabras en la linea
     * @param lineas - linea a normalizar
     * @return - un arreglo de las palabras en la linea
     */
    public String[] textoIgual(String lineas){
        lineas = Normalizer.normalize(lineas, Normalizer.Form.NFKD);
        lineas = lineas.replaceAll("[^\\p{IsAlphabetic}\\s]", "");
        lineas = lineas.toLowerCase();
        lineas = lineas.trim();
        if(lineas.equals("")){
            return new String[0];
        }
        return lineas.split(" ");
    }
    /**
     * Metodo que cuenta cuantas veces aparecen las palabras en el archivo
     */
    public void cuentaPalabras(){
        for(String lineas: this.lineas){
            for(String palabra: textoIgual(lineas)){
                this.contadorPalabras++;
                if(palabra.length() >= 7){
                    this.palabrasLongitudSiete.agrega(palabra);
                }
                if(this.diccionario.contiene(palabra)){
                    int cont = this.diccionario.get(palabra);
                    this.diccionario.agrega(palabra, cont + 1);
                }else{
                    this.diccionario.agrega(palabra, 1);
                }
            }
        }

        agregaPalabras();  

    }
    /**
     * Metodo que agrega las palabras a la lista de palabras
     */
    private void agregaPalabras(){
        Iterator<String> iteLlaves = this.diccionario.iteradorLlaves();
        while(iteLlaves.hasNext()){
            String llave = iteLlaves.next();
            int valorLlave = this.diccionario.get(llave);
            float porcentaje = (float) valorLlave/this.contadorPalabras;
            Palabra p = new Palabra(llave, valorLlave, porcentaje*100);
            this.palabras.agrega(p);
        }
        this.palabras = Lista.mergeSort(this.palabras);
    }
    /**
     * Metodo que genera una lista con las 15 palabras mas comunes
     * @return
     */
    public Lista<Palabra> getPalabrasComunes(){
        Lista<Palabra> lista = new Lista<>();
        int t;
        if(this.palabras.getLongitud() < 15){
            t = this.palabras.getLongitud();
        }else{
            t = 15;
        }

        for(int i = 0; i < t; i++){
            lista.agrega(this.palabras.get(i));
        }
   
        return lista.reversa();
    }
    /**
     * Metodo que genera una lista de las palabras mas comunes comparables segun su conteo
     * @return
     */
    public Lista<ComparadorPalabras> palabrasArboles(){
        Lista<ComparadorPalabras> lista = new Lista<>();
        for(Palabra p :getPalabrasComunes()){
            lista.agrega(new ComparadorPalabras(p));
        }
   
        return lista;
    }
    /**
     * Metodo que genera el estilo para la grafica Pastel
     * @return - estilo para la grafica pastel
     */
    private String stylePastel(){
        String res = ".pie{" + "\n";
        res += "\twidth: 300px;" + "\n";
        res += "\tbackground: #f06;" + "\n";
        res += "\tborder-radius: 50%;" + "\n";
        res += "}" + "\n\n";
        res += ".pie circle{" + "\n";
        res += "\tfill: none;" + "\n";
        res += "\tstroke: gold;" + "\n";
        res += "\tstroke-width: 32;" + "\n";
        res += "\tanimation: rotate 1.5s ease-in;" + "\n";
        res += "}" + "\n"; 
        return res;
    }

    /**
     * Metodo que genera una lista de los porcentajes de las 15 palabras mas comunes
     * @return - lista de porcentajes de las 15 palabras mas comunes
     */
    private Lista<Float> listaPorcentaje(){
        Lista<Float> porcentajes = new Lista<>();
        if(this.palabras.getLongitud() <= 15){
            for(Palabra p : this.palabras){
                porcentajes.agrega(p.porcentaje);
            }
        }else{
            for(int i = 0; i < 15; i++){
                Palabra p = this.palabras.get(i);
                porcentajes.agrega(p.porcentaje);
            }
            float cont = 0;
            for(int j = 15; j < this.palabras.getLongitud(); j++){
                cont += this.palabras.get(j).cont;
            }
            porcentajes.agregaInicio(cont);
        }
        return porcentajes.reversa();
    }

    /**
     * Metodo que genera una grafica de barras a partir de JS
     * @return - grafica de barras generada por JS
     */
    public String graficaBarrasJavaScript(){

        String res = "<div id = " + '"' + "chartContainer" + '"' + " style = " + '"' + "height: 200px; max-width: 500px; margin: 0px auto;" + '"' + "></div>" + "\n";
        res += "<script src = " + '"' + "https://canvasjs.com/assets/script/canvasjs.min.js" + '"' + "> </script>" + "\n";
        res += "<script type = " + '"' + "text/javascript" + '"' + ">" + "\n";
        res += "window.onload = function () { " + "\n";
        res += "var chart = new CanvasJS.Chart(" + '"' + "chartContainer" + '"' + ", {" + "\n";
        res += "\ttheme: " + '"' + "dark1" + '"' + "," + "\n";
        res += "\tanimationEnabled: " + "true" + "," + "\n";
        res += "\ttitle:{" + "\n";
        res += "\t\ttext: " + '"' + "Gráfica de barras" + '"' + "\n";
        res += "\t}," + "\n";
        res += "\tdata: " + "[" + "\n";
        res += "\t{" + "\n";
        res += "\t\ttype: " + '"' + "bar" + '"' + "," + "\n";
        res += "\t\tdataPoints: " + "[" + "\n";
        //for(Palabra p : listaPorcentaje()){
          //  res += "\t\t\t{ label: " + '"' + p.palabra + '"' + "," + " y: " + p.cont + "  }," + "\n";
        //}
        res += "\t\t]" + "\n";
        res += "\t}" + "\n";
        res += "\t]" + "\n";
        res += "});" + "\n";
        res += "chart.render();" + "\n";
        res += "}" + "\n";
        res += "</script>" + "\n";

        return res;
    }
    /**
     * Metodo que indica si 2 archivos comparten palabras
     * @param archivo - archivo a comparar
     * @return <code>true</code> si comparten palabras, <code>false</code> en otro caso
     */
    public boolean compartenPalabrasComunes(GeneradorDocumento archivo){
        return !this.palabrasLongitudSiete.interseccion(archivo.palabrasLongitudSiete).esVacia();
    }
    /**
     * Metodo que genera todos los estilos de colores
     * @return - estilos de colores
     */
    private String estilosBotones(){
        String res = "";
        String[] colores = {"#FF8C00","#B0E0E6","#008080","#800000","#FF69B4","#efc050", "#5b5ea6", "#9b2335", "#55b4b0", "#6b5b95", "#88b04b", "#f7cac9", "#955251", "#2a4b7c", "#264e36", "#f06"};
        int id = 1;
        for(int i = 0; i < colores.length; i++){
            res += "\t\tinput[type=" + '"' + "submit" + '"' + "].special," + "\n";
            res += "\t\tinput[type=" + '"' + "reset" + '"' + "].special," + "\n";
            res += "\t\tinput[type=" + '"' + "button" + '"' + "].special," + "\n";
            res += "\t\tbutton.special," + "\n";
            res += "\t\t.button.special" + id + " {" + "\n";
            res += "\t\t\tbackground-color: " + colores[i] + ";" +"\n";
            res += "\t\t\tcolor: #ffffff !important;" + "\n";
            res += "\t\t}" + "\n"; 

            res += "\t\tinput[type=" + '"' + "submit" + '"' + "].special:hover," + "\n";
            res += "\t\tinput[type=" + '"' + "reset" + '"' + "].special:hover," + "\n";
            res += "\t\tinput[type=" + '"' + "button" + '"' + "].special:hover," + "\n";
            res += "\t\tbutton.special:hover," + "\n";
            res += "\t\t.button.special" + id + ":hover {" + "\n";
            res += "\t\t\tbackground-color: " + colores[i] + ";" +"\n";
            res += "\t\t}" + "\n"; 

            res += "\t\tinput[type=" + '"' + "submit" + '"' + "].special:active," + "\n";
            res += "\t\tinput[type=" + '"' + "reset" + '"' + "].special:active," + "\n";
            res += "\t\tinput[type=" + '"' + "button" + '"' + "].special:active," + "\n";
            res += "\t\tbutton.special:active," + "\n";
            res += "\t\t.button.special" + id + ":active {" + "\n";
            res += "\t\t\tbackground-color: " + colores[i] + ";" +"\n";
            res += "\t\t}" + "\n"; 

            id++;
        }
        
        return res;
    }

    @Override
    public String toString(){
        return this.nombre;
    }

    /**
     * Metodo que genera los arboles binarios para el reporte
     * @return - arboles del reporte en SVG
     */
    public String arboles(){
        String res = "";
        res += "\t\t<section id=" + '"' + "arbolrojinegro" + '"' + " class=" + '"' + "wrapper style1" + '"' + ">" + "\n";
        res += "\t\t<header>" + "\n";
        res += "\t\t\t<h3>Árboles rojinegro</h3>" + "\n";
        res += "\t\t</header>" + "\n";
        res += "\t\t</section>" + "\n";
        res += "\t\t<div class=" + '"' + "content" + '"' + ">" + "\n";
        res += "\t\t\t" + this.arbolRN.toSVGString(palabrasArboles()) + "\n";
        res += "\t\t\t</div>" + "\n";

        res += "\t\t<section id=" + '"' + "arbolavl" + '"' + " class=" + '"' + "wrapper style1" + '"' + ">" + "\n";
        res += "\t\t<header>" + "\n";
        res += "\t\t\t<h3>Árboles AVL</h3>" + "\n";
        res += "\t\t</header>" + "\n";
        res += "\t\t</section>" + "\n";
        res += "\t\t<div class=" + '"' + "content" + '"' + ">" + "\n";
        res += "\t\t\t" + this.arbolAVL.toSVGPalabras(palabrasArboles()) + "\n";
        res += "\t\t\t</div>" + "\n";


        return res;
    }
}