package mx.unam.ciencias.edd.proyecto3.svg;

import main.java.mx.unam.ciencias.edd.proyecto3.ComparadorPalabras;
import mx.unam.ciencias.edd.Coleccion;

/**
 * Clase abstracta que contiene todos los métodos para dibujar las figuras necesarias para las figuras
 */
public abstract class EstructuraSVG {
    /**Apertura del documento html */
    protected static String APERTURA = "<?xml version='1.0' encoding='UTF-8' ?>\n";
    /** Apertura de etiqueta */
    protected static String ABRE_ETIQUETA = "<g>";
    /** Cierre de etiqueta */
    protected static String CIERRA_ETIQUETA = "</g>";
    /**
     * Método abstracto para dibujar la estrucutura deseada
     * @param estrutura - Colección de la cual se obtendrán datos
     * @return String - la cadena svg de la estructura
     */
    abstract public String toSVG(Coleccion<Integer> estrutura);
    public String dibujarRectangulo(int x, int y, int rx, int ry, int height, int width, int strokewidth, String colorfill, String colorstroke){
        String res = "";
        res +=  ABRE_ETIQUETA + "\n";
        res += "<rect x=" + '"' + x + '"' + " y=" + '"' + y + '"' + " rx=" + '"' + rx + '"' + " ry=" + '"' + 
        ry + '"' + " width=" + '"' + width + '"' + " height=" + '"' + height + '"' + " fill=" + '"' + colorfill + '"' + " stroke=" + '"' +
        colorstroke + '"' + " stroke-width=" + '"' + strokewidth + '"' + "/>" + "\n";
        return res;
    }
    /**
     * Dibuja el texto
     * @param x - coordenada x
     * @param y - coordenada y
     * @param elemento - elemeto
     * @return String - cadena de texto en svg
     */
    public String dibujarTexto(int x, int y, String elemento){
        String res = "";
        if(elemento.length() >= 6){
            res = "9px";
        }else{
            res = "15px";
        }
        String text = "<text x=" + '"' + x + '"' + " y=" + '"' + y + '"' + " font-size=" + '"' + res + '"' + " fill=" + '"' + "yellow" + '"' + ">" + elemento + "</text>" + "\n";
        return text;
    }
    /**
     * Dibuja el texto
     * @param x - coordenada x
     * @param y - coordenada y
     * @param elemento - elemeto
     * @return String - cadena de texto en svg
     */
    public String dibujarTextoBarra(int x, int y, float elemento){
        String text = "<text x=" + '"' + x + '"' + " y=" + '"' + y + '"' + " fill=" + '"' + "white" + '"' + ">" + elemento + "</text>" + "\n";
        return text;
    }
    /**
     * Dibuna lineas rectas
     * @param x1  - coordenadax1
     * @param y1 - coordenada y1
     * @param x2 - coordenada x2
     * @param y2 - coordenada y2
     * @param storkewidth ancho
     * @return String - cadena de una linea en svg
     */
    public String dibujaLineas(int x1, int y1, int x2, int y2, int storkewidth){
        String linea = "<line x1=" + '"' + x1 + '"' + " y1=" + '"' + y1 + '"' + " x2=" + 
        '"' + x2 + '"' + " y2= " + '"' + y2 + '"' + " stroke =" + '"' + "black" + '"' + 
        " stroke-width= " + '"' + storkewidth + '"' + "/>" + "\n";
        return linea;
    }
    /**
     * Dibuja circulos
     * @param cx - coordenada x
     * @param cy - coordenada y
     * @param r - radio
     * @param stroke - contorno
     * @return String - cadena de una circulo en svg
     */
    public String dibujarCirculos(double cx, double cy, double r, String stroke){
        String vertice = "<circle cx=" + '"' + cx + '"' + " cy=" + '"' + cy + '"' + " r=" + '"' + r + '"' + " stroke=" + '"' + "white" + '"' + 
        " stroke-width=" + '"' + 2 + '"' + " fill=" + '"' + stroke + '"' + "/>";
        return vertice + "\n";
   }
       /**
     * Dibuna lineas rectas
     * @param x1  - coordenadax1
     * @param y1 - coordenada y1
     * @param x2 - coordenada x2
     * @param y2 - coordenada y2
     * @param storkewidth ancho
     * @return String - cadena de una linea en svg
     */
   public String dibujaLineas(Double x1, double y1, double x2, double y2, int storkewidth){
    String linea = "<line x1=" + '"' + x1 + '"' + " y1=" + '"' + y1 + '"' + " x2=" + 
    '"' + x2 + '"' + " y2=" + '"' + y2 + '"' + " stroke=" + '"' + "black" + '"' + 
    " stroke-width=" + '"' + storkewidth + '"' + "/>" + "\n";
    return linea;
    }
    /**
     * Dibuja el texto
     * @param x - coordenada x
     * @param y - coordenada y
     * @param elemento - elemeto
     * @return String - cadena de texto en svg
     */
    public String dibujarTexto(double x, double y, String elemento){
        String text = "<text x=" + '"' + x + '"' + "y=" + '"' + y + '"'  + " font-size=" + '"' + "8px" + '"' + " fill=" + '"' + "yellow" + '"' + ">" + elemento + "</text>" + "\n";
        return text;
    }
    /**
     * Dibuja el texto
     * @param x - coordenada x
     * @param y - coordenada y
     * @param elemento - elemeto
     * @return String - cadena de texto en svg
     */
    public String dibujarTexto2(double x, double y, int elemento){
        String text = "<text x=" + '"' + x + '"' + " y=" + '"' + y + '"' + " font-size=" + '"' + "8px"+ '"'+ " " +" fill=" + '"' + "yellow" + '"' + ">" + elemento + "</text>" + "\n";
        return text;
    }
    /**
     * Dibuja el texto
     * @param x - coordenada x
     * @param y - coordenada y
     * @param elemento - elemeto
     * @return String - cadena de texto en svg
     */
    public String dibujarTextoConColor(double x, double y, String elemento, String color){
        String text = "<text x=" + '"' + x + '"' + " y=" + '"' + y + '"' + " font-size=" + '"' + "smaller"+ '"'+ " " +" fill=" + '"' + color + '"' + ">" + elemento + "</text>" + "\n";
        return text;
    }
    /**
     * Dibuna una parabola respecto al eje y
     * @param x  - coordenadax1
     * @param y - coordenada y1
     * @param x2 - coordenada x2
     * @param y2 - coordenada y2
     * @param signo - signo de la parabola
     * @return String - cadena de una linea en svg
     */
    public String dibujaPahtSeno(double x, double y, double x2, double y2, int signo){
        double b = 1.5*signo;
        if(signo == -1){  
            b = (signo/2) /2;
        }
        if(signo == 1){
            b =2*signo;
        }
        String path = "<path stroke=" + '"' + "#39a0ca" + '"' + " fill=" + '"' + "none" + '"' + " d=" + '"' + "M " + x + ", " + y + 
        " C " +  x + ", " + y*b + " " + x2 + ", " + y2*b + " " + x2 + ", " + y2 + '"' + "/>";
        return path + "\n";  
    }
        /**
     * Dibuna una parabola respecto al eje x
     * @param x  - coordenadax1
     * @param y - coordenada y1
     * @param x2 - coordenada x2
     * @param y2 - coordenada y2
     * @param signo - signo de la parabola
     * @return String - cadena de una linea en svg
     */
    public String dibujaPahtSeno2(double x, double y, double x2, double y2, int signo){
        String path = "<path stroke=" + '"' + "#39a0ca" + '"' + " fill=" + '"' + "none" + '"' + " d=" + '"' + "M " + x + ", " + y + 
        " C " +  1.2*signo*x+ ", " + y + " " + 1.2*signo*x2+ ", " + y2 + " " + x2 + ", " + y2 + '"' + "/>";
        return path + "\n";  
    }
    public int setRadioString(Coleccion<ComparadorPalabras> coleccion){
        int maximo = 0;
        for(ComparadorPalabras n : coleccion){
            if(n.toString().length() > maximo){
                maximo = n.toString().length();
            }
        }
        if(maximo == 1){
            maximo = 4;
        }
        return maximo*2 + 10;
    }
}
