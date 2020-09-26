package main.java.mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.Conjunto;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.svg.GraficaSVG;

public class Index {
    public Conjunto<GeneradorDocumento> archivos;
    private GraficaSVG graficaSVG;
    

    /**
     * Constructor comun
     * @param archivos - archivos a usar
     */
    public Index(Conjunto<GeneradorDocumento> archivos){
        this.archivos = archivos;
        this.graficaSVG = new GraficaSVG();
    }

    /**
     * Metodo que genera el HTML del index
     * @return - HTML del index
     */
    public String generaArchivo(){
        String res = "<!DOCTYPE HTML>" + "\n";
        res += "<html>" + "\n";
        res += "\t<head>" + "\n";
        res += "\t<style>" + "\n";
        res += "\t</style>" + "\n";
        res += "\t\t<title>" + "Index Proyecto3" + "</title>" + "\n";
        res += "\t\t<meta charset=" + '"' + "utf-8" + '"' + " />" + "\n";
        res += "\t\t<meta name=" + '"' + "viewport"+ '"' +" content=" + '"' + "width=device-width, initial-scale=1" +'"' + " />" + "\n";
        res += "\t\t<link rel=" + '"' + "stylesheet" + '"' + " href=" + '"' +  "main.css" + '"' +" />" + "\n";
        res += "\t</head>" + "\n";
        res += "\t<body>" + "\n";
        res += "\t\t<header id=" + '"' + "header" + '"' + ">" + "\n";
        res += "\t\t\t<div class=" + '"' + "logo" + '"' + "><a href=" + '"' + "#" + '"' + ">Index EDD Proyecto3</a>" + "\n";
        res += "\t\t\t</div>" + "\n";
        res += "\t\t<section id=" + '"' + "main" + '"' + ">" + "\n";
        res += "\t\t\t<div class=" + '"' + "inner" + '"' + ">" + "\n";
        res += divisionesArchivos() + "\n";
        res += "\t\t\t</div>" + "\n";
        res += "\t\t</section>" + "\n";
        res += "\t\t<section id=" + '"' + "grafica" + '"' + " class=" + '"' + "wrapper style1" + '"' + ">" + "\n";
        res += "\t\t<header>" + "\n";
        res += "\t\t\t<h3>Gráfica de Archivos</h3>" + "\n";
        res += "\t\t</header>" + "\n";
        res += "\t\t</section>" + "\n";
        res += "\t\t<div class=" + '"' + "content" + '"' + ">" + "\n";
        res += "\t\t\t" + this.graficaSVG.toSVG(archivosGetName()) + "\n";
        res += "\t\t\t</div>" + "\n";
        res += "\t</body>" + "\n";
        res += "</html>";


        return res;
    }

    /**
     * Metodo que genera las divisiones de los archivos
     * @return - divisiones de los archivos
     */
    public String divisionesArchivos(){
        String res = "\t\t\t\t<section id=" + '"' + "archivos" + '"' + " class=" + '"' + "wrapper" + '"' + ">" + "\n";
        for(GeneradorDocumento doc : this.archivos){
            if(doc.numero % 2 == 0){
                res += "<div class=" + '"' + "spotlight" + '"' + ">" + "\n";
            }else{
                res += "<div class=" + '"' + "spotlight alt" + '"' + ">" + "\n";
            }
            res += "\t<div class=" + '"' + "image flush" + '"' + "><img src=" + '"' + "image0" + doc.numero + ".jpg" + '"' + " alt=" + '"' + '"' + " /></div>" + "\n";
            res += "\t\t<div class=" + '"' + "inner" + '"' + ">" + "\n";
            res += "\t\t\t<h3>" +   doc.nombre + "</h3>" + "\n";
            res += "\t\t\t<a href=" + '"' + doc.nombre + ".html" + '"' + ">Ir a reporte</a>" + "\n";
            res += "\t\t\t<p>" + "Palabras totales: "+  doc.contadorPalabras + "</p>" + "\n";
            res += "\t\t\t</div>" + "\n";
            res += "\t\t</div>" + "\n";
            System.out.println(doc.numero);
        }

        res += "\t\t\t\t</section>" + "\n";


        return res;
    }

    

    /**
     * Metodo que crea la lista para la grafica SVG
     * @return - lista de archivos conectados
     */
    public Lista<String> archivosGetName(){
        Lista<GeneradorDocumento> aux =  new Lista<GeneradorDocumento>();
        for(GeneradorDocumento doc :  this.archivos){
            aux.agrega(doc);
        }

        Conjunto<Conjunto<GeneradorDocumento>> aristas = new Conjunto<Conjunto<GeneradorDocumento>>();
        Lista<String> docs = new Lista<String>();

        for(int i = 0; i < aux.getLongitud(); i++){
            for(int j = i + 1; j < aux.getLongitud(); j++){
                Conjunto<GeneradorDocumento> pares;
                if(aux.get(i).compartenPalabrasComunes(aux.get(j))){
                    pares =  new Conjunto<GeneradorDocumento>();
                    pares.agrega(aux.get(i));
                    pares.agrega(aux.get(j));
                    aristas.agrega(pares);
                }
            }
        }


        for(Conjunto<GeneradorDocumento> set : aristas){
            for(GeneradorDocumento doc : set){
                docs.agrega(doc.nombre);
            }
        }
        for(GeneradorDocumento doc : aux){
            if(!docs.contiene(doc.nombre)){
                docs.agrega(doc.nombre);
                docs.agrega(doc.nombre);
            }
        }

        return docs;
    }


    
}