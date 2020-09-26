package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.MonticuloMinimo;
import mx.unam.ciencias.edd.ValorIndexable;
import mx.unam.ciencias.edd.Lista;
/**
 * Clase que construye un MonticuloMinimo
 */
public class MonticuloMinimoSVG extends EstructuraSVG{

    @Override
    public String toSVG(Coleccion<Integer> lista){
        Lista<ValorIndexable<Integer>> listMin = new Lista<>();
        for(Integer n : lista){
            listMin.agrega(new ValorIndexable<Integer>(n, n));
        }
        MonticuloMinimo<ValorIndexable<Integer>> monticuloMinimo = new MonticuloMinimo<>(listMin);
         Lista<Integer> datos = new Lista<>();
         for(int i = 0; i < monticuloMinimo.getElementos() ; i++){
             datos.agrega(listMin.get(i).getElemento());
         }
         ArbolCompletoSVG arbol = new ArbolCompletoSVG();
         String svg = arbol.toSVG(datos);
         return svg ;   
    }
}