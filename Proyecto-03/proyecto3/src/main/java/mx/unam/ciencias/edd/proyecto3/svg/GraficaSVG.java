package mx.unam.ciencias.edd.proyecto3.svg;


import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
/**
 * Clase para construir una gráfica
 */
public class GraficaSVG<T extends Comparable<T>>{
    /**
     * Clase que modela un elemento a partir de sus coordenadas en el plano
     */
    private class PuntoGrafica {
        /** Elemento leido */
        private T elemento;
        /** Coordenada en x */
        private double x;
        /** Coordenada en y */
        private double y;
    
        /**
         * Constructor puntografica
         * @param elemento - elemento leido
         */
        public PuntoGrafica(T elemento){
            this.elemento = elemento;
            this.x = 20;
            this.y = 40;
        }
        /**
         * Getter X
         * @return double - x
         */
        public double getX(){
            return this.x;
        }
         /**
         * Getter Y
         * @return double - Y
         */
        public double getY(){
            return this.y;
        }
         /**
         * Getter Elemento
         * @return int - elemento
         */
        public T getElemento(){
            return this.elemento;
        }
        @Override
        /**
         * Nos dice si 2 puntos son iguales si sus elementos lo son
         * @return - <code>true</code> si los elementos son iguales <code>false</code> en otro caso
         */
        public boolean equals(Object object){
            if (object == null || getClass() != object.getClass())
                return false;
            @SuppressWarnings("unchecked") PuntoGrafica punto = (PuntoGrafica)object;
            return this.elemento == punto.elemento;
        }
        /**
         * Nos dice si la diferencia entre las coordenadas de 2 puntos está entre [0, 1]
         * @param z - punto a comparar
         * @return - <code>true</code> si están en ese rango <code>false</code> en otro caso
         */
        public boolean equalsPoints(PuntoGrafica z){
            double deltaX = this.x - z.x;
            double deltaY = this.y - z.y;
            if((deltaX > 0  && deltaX < 1) || (deltaY > 0 && deltaY < 1)){
                return true;
            }
            return false;
        }
        public boolean deltaY(PuntoGrafica z){
            return ((this.y - z.y > 0 && this.y - z.y < 26) || (this.y - z.y < 0 && this.y - z.y > -26));
        }
        public boolean deltaX(PuntoGrafica z){
            return ((this.x - z.x > 0 && this.x - z.x <= 10) || (this.x - z.x < 0 && this.x - z.x >= -10));
        }
        public boolean deltaY2(PuntoGrafica z){
            return ((this.y - z.y > 0 && this.y - z.y <= 15) || (this.y - z.y < 0 && this.y - z.y >= -15));
        }
        public boolean points(PuntoGrafica z){
            return this.x == z.x && this.y == z.y;
        }

    }
    /** Grafica donde se van a guardar los puntos */
    private Grafica<PuntoGrafica> grafica;
    /** Lista donde se van a guardar los puntos a partir de una colección */
    private Lista<PuntoGrafica> coordenadas;
    /**Apertura del documento html */
    private static String APERTURA = "<?xml version='1.0' encoding='UTF-8' ?>\n";
    /** Apertura de etiqueta */
    private static String ABRE_ETIQUETA = "<g>";
    /** Cierre de etiqueta */
    private static String CIERRA_ETIQUETA = "</g>";


    /**
     * Constructor, iniciamos las lista y la gráfica
     */
    public GraficaSVG(){
        this.grafica = new Grafica<>();
        this.coordenadas = new Lista<>();
    }

    public String toSVG(Coleccion<T> coleccion){
        if(coleccion.getElementos() % 2 != 0){
            System.err.println("No se puede tener un numero impar de elementos para las graficas");
            System.exit(-1);
        }
        int r = setRadio(coleccion);
        double radioC = ((2*this.coordenadas.getElementos())* (2*r))/Math.PI;
        double altura = (2*r)+(2* radioC)+r;
        agrega(coleccion);
        conectaElementos();
        setXY(r, altura/2);
        String svg = "";
        if(this.grafica.getElementos() <= 10){
             svg += setDimensiones(altura*altura*6);
        }else{
             svg += setDimensiones(Math.pow(this.grafica.getElementos(), 2) * 3);
        }
        svg += dibujaVertices(r);
        svg += dibujaAristas(r);
        return  svg  + "</svg>";
    }
    /**
     * Dibuja las aristas de cada elemento
     * @param r - el radio de los vértices
     * @return String - la cadena de las arsitas
     */
    public String dibujaAristas(int r){
        String res = "";
       for(PuntoGrafica n :  this.grafica){
           for(PuntoGrafica z : this.grafica){
               if(this.grafica.sonVecinos(n, z)){
                   if(n.equals(z)){
                       continue;
                   }
                   int y = 30;
                       if(n.y < y && z.y > y){
                             res += dibujaPahtSeno(n.getX(), n.getY() - r, z.getX(), z.getY() - r, -1);
                       }else if(n.y < y && z.y < y){
                        res += dibujaPahtSeno(n.getX(), n.getY() + r, z.getX(), z.getY() + r, 1);
                        res = res.replaceAll(dibujaPahtSeno(z.getX(), z.getY() + r, n.getX(), n.getY() + r, 1), "");
                       }else if(n.y > y && z.y > y){
                        res += dibujaPahtSeno(n.getX(), n.getY() + r, z.getX(), z.getY() + r, 1);
                        res = res.replaceAll(dibujaPahtSeno(z.getX(), z.getY() + r, n.getX(), n.getY() + r, 1), "");
                       }else if(n.y < y && z.y == y){
                            res += dibujaPahtSeno(n.getX(), n.getY() - r, z.getX(), z.getY() - r, -1);
                            res = res.replaceAll(dibujaPahtSeno(z.getX(), z.getY() - r, n.getX(), n.getY() - r, -1), "");
                       }else if(n.y > y && z.y == y){
                            res += dibujaPahtSeno(n.getX(), n.getY() - r, z.getX(), z.getY() - r, -1);
                            res = res.replaceAll(dibujaPahtSeno(z.getX(), z.getY() - r, n.getX(), n.getY() - r, -1), "");
                       }else{
                            res += dibujaPahtSeno(n.getX(), n.getY() + r, z.getX(), z.getY() + r, 1);
                            res = res.replaceAll(dibujaPahtSeno(z.getX(), z.getY() + r, n.getX(), n.getY() + r, 1), "");
                       }
                    
                res = res.replaceAll(dibujaPahtSeno(z.getX(), z.getY() + r, n.getX(), n.getY() + r, 1), "");
                res = res.replaceAll(dibujaPahtSeno(z.getX(), z.getY() + r, n.getX(), n.getY() + r, -1), "");
                res = res.replaceAll(dibujaPahtSeno(z.getX(), z.getY() - r, n.getX(), n.getY() - r, -1), "");
               }
           }
       }
       return res;
    }
    /**
     * Dibuja los vértices y el texto
     * @param r - radio vértices
     * @return String - cadena svg de los vértices y texto
     */
    public String dibujaVertices(int r){
        String vertices = "";
        for(PuntoGrafica p : this.grafica){
            vertices += dibujarCirculos(p.getX(), p.getY(), r, p.elemento);
            vertices += dibujarTexto(p.getX() - 21, p.getY() + 5, p.elemento);
        }
        return vertices;
    }
     /**
     * Dibuja el texto
     * @param x - coordenada x
     * @param y - coordenada y
     * @param elemento - elemeto
     * @return String - cadena de texto en svg
     */
    public String dibujarTexto(double x, double y, T elemento){
        String text = "<text x=" + '"' + x + '"' + " y=" + '"' + y + '"' + " font-size=" + '"' + "smaller"+ '"'+ " " +" fill=" + '"' + "yellow" + '"' + ">" + elemento.toString() + "</text>" + "\n";
        return text;
    }
    /**
     * Establece las dimensiones
     * @param r - radio vértices
     * @return String cadena svg de ñas dimensiones
     */
    public String setDimensiones(double r){
        return APERTURA + "\n" + "<svg height=" + '"' + r+ '"' + " width=" + '"' + r + '"' + ">" + "\n";
    }
    /**
     * Establece el radio de los vértices
     * @param coleccion - gráfica
     * @return int - radio de los vértices
     */
    private int setRadio(Coleccion<T> coleccion){
        int maximo = 0;
        for(T n : coleccion){
            if(n.toString().length() > maximo){
                maximo = n.toString().length();
            }
        }
        if(maximo == 1){
            maximo = 4;
        }
        return maximo*4 + 9;
    }

    public double setRadioCirculo(double r, int elementos){
        return ((elementos*2)*(r*2))/Math.PI;
    }
    /**
     * Acomoda los vértices en caso de haber discrepancias
     */
    public void acomoda(){
        for(PuntoGrafica p : this.grafica){
            for(PuntoGrafica z : this.grafica){
                if(p.deltaY(z) && p.x == z.x){
                    z.x += 30;
                }
            }
        }
        
    }
    /**
     * Acomoda los vértices de tal forma que formen la función sen(x)
     * @param r - radio vértices
     * @param delimitador - delimitador para mantener congruencias
     */
    public void setCoordenadas2(double r, double delimitador){
        double cx;
        double valorsen;
        double angulo;
        int cont = 0;
        for(PuntoGrafica p : this.grafica){
            if(this.grafica.getElementos() == 1){
                p.x = 35;
                p.y = 35;
            }
            angulo = cont++ * (2*Math.PI /this.grafica.getElementos());
            valorsen = Math.sin(angulo) * r;
            p.x = valorsen + delimitador;
            if(valorsen < 0){
                p.y = 20;
            }else if(valorsen > 0){
                p.y = 100;
            }else if(valorsen == 0){
                p.y = 60;
            }
       }
    }
    public void setXY(double r, double delimitador){
        int cont = 1;
        int cont2 = 1;
        double res;
        double guardar;
        for(PuntoGrafica p : this.grafica){
            res = 20 + (cont * (2*r + 2*delimitador));
            guardar = res;
            p.x = res + 2*r;
            if(cont < this.grafica.getElementos()/2){
                p.y = 2*cont + res;  
            }else{
                p.y += p.y/this.grafica.getElementos()*cont2;
                cont2++;
            }
            cont++;
        }
    }
    /**
     * Agrega los elementos de la colección a la lista de coordenadas
     * @param coleccion - colección a usar
     */
    public void agrega(Coleccion<T> coleccion){
        for(T n : coleccion){
            this.coordenadas.agrega(new PuntoGrafica(n));
        }
    }
    /**
     * Conecta los elementos de lista de coordenadas
     */
    public void conectaElementos(){
        for(int i = 0; i < this.coordenadas.getElementos() ; i+=2){
            PuntoGrafica p1 = this.coordenadas.get(i);
            PuntoGrafica p2 = this.coordenadas.get(i+1);
            if(!this.grafica.contiene(p1)){
                this.grafica.agrega(p1);
            }
            if(p1.equals(p2)){
                continue;
            }else{
                if(!this.grafica.contiene(p2)){
                    this.grafica.agrega(p2);
                }
                if(!this.grafica.sonVecinos(p1, p2)){
                    this.grafica.conecta(p1, p2);
                }
            }
        }
    }


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
    }    /**
    * Dibuja circulos
    * @param cx - coordenada x
    * @param cy - coordenada y
    * @param r - radio
    * @param stroke - contorno
    * @return String - cadena de una circulo en svg
    */
   public String dibujarCirculos(double cx, double cy, double r, T stroke){
       String vertice = "<circle cx=" + '"' + cx + '"' + " cy=" + '"' + cy + '"' + " r=" + '"' + r + '"' + " stroke=" + '"' + "white" + '"' + 
       " stroke-width=" + '"' + 2 + '"' + " fill=" + '"' + stroke + '"' + "/>";
       return vertice + "\n";
  }
}
