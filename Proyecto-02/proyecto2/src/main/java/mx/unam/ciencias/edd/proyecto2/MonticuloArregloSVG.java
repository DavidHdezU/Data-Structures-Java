package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.MonticuloArreglo;
import mx.unam.ciencias.edd.ValorIndexable;

/**
 * Clase que construye un MonticuloArreglo
 */
public class MonticuloArregloSVG extends EstructuraSVG{

    @Override
    public String toSVG(Coleccion<Integer> lista){
        Lista<ValorIndexable<Integer>> listMin = new Lista<>();
        for(Integer n : lista){
            listMin.agrega(new ValorIndexable<Integer>(n, n));
        }
        MonticuloArreglo<ValorIndexable<Integer>> monticuloMinimo = new MonticuloArreglo<>(listMin);
         Lista<Integer> datos = new Lista<>();
         for(int i = 0; i < monticuloMinimo.getElementos() ; i++){
             datos.agrega(listMin.get(i).getElemento());
         }
         ArregloSVG arreglo = new ArregloSVG();
         String svg = arreglo.toSVG(datos);
        return svg;
    }
}