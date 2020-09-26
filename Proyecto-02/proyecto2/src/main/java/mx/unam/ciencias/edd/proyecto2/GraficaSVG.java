package mx.unam.ciencias.edd.proyecto2;


import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
/**
 * Clase para construir una gráfica
 */
public class GraficaSVG extends EstructuraSVG {
    /**
     * Clase que modela un elemento a partir de sus coordenadas en el plano
     */
    private class PuntoGrafica {
        /** Elemento leido */
        private int elemento;
        /** Coordenada en x */
        private double x;
        /** Coordenada en y */
        private double y;
    
        /**
         * Constructor puntografica
         * @param elemento - elemento leido
         */
        public PuntoGrafica(int elemento){
            this.elemento = elemento;
            this.x = 20;
            this.y = 30;
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
        public int getElemento(){
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


    /**
     * Constructor, iniciamos las lista y la gráfica
     */
    public GraficaSVG(){
        this.grafica = new Grafica<>();
        this.coordenadas = new Lista<>();
    }

    @Override
    public String toSVG(Coleccion<Integer> coleccion){
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
                   //if(n.deltaX(z) || n.deltaY(z)){
                    //res += dibujaLineas(n.getX(), n.getY(), z.getX(), z.getY(), 1);
                   //}
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
            vertices += dibujarCirculos(p.getX(), p.getY(), r, Integer.toString(p.getElemento()));
            vertices += dibujarTexto(p.getX() - 4, p.getY() + 5, p.getElemento());
        }
        return vertices;
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
    public int setRadio(Coleccion<Integer> coleccion){
        int maximo = 0;
        for(Integer n : coleccion){
            if(n.toString().length() > maximo){
                maximo = n.toString().length();
            }
        }
        if(maximo == 1){
            maximo = 2;
        }
        return maximo*3 + 5;
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
        int cont = 0;
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
    public void agrega(Coleccion<Integer> coleccion){
        for(Integer n : coleccion){
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

}