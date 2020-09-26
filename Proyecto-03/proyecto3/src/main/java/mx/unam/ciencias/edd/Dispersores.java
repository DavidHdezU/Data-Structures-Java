package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        // Aquí va su código.
        int r = 0;
        byte[] aux;
        int l;
        int n = 0;
        if(llave.length % 4 != 0){
            l = 4 - (llave.length % 4);
            aux = new byte[llave.length + l];
            for(int i = 0; i < aux.length; i++){
                if(i < llave.length){
                    aux[i] = llave[i];
                }else{
                    aux[i] = (byte)0; // Agregamos 0's al final, en caso de no ser multiplo de 4
                }
            }
        }else{
            aux = llave;
        }
        for(int j = 0; j < aux.length ; j += 4){
            // Creamos el entero
            n = bigEdian(aux[j], aux[j+1], aux[j+2], aux[j+3]);

            r ^= n;  // Aplicamos XOR
        }
        return r;
    }
    /**
     * Método para desplazar bytes de acuerdo a Big Edian
     * @param a - primer byte
     * @param b - segundo byte
     * @param c - tercer byte
     * @param d - cuarto byte
     * @return - Un entero conformado por los 4 bytes desplazados
     */
    private static int bigEdian(byte a, byte b, byte c, byte d){
        return (((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((c & 0xFF) << 8) | (d & 0xFF));
    }

     /**
     * Método para desplazar bytes de acuerdo a Little Edian
     * @param a - primer byte
     * @param b - segundo byte
     * @param c - tercer byte
     * @param d - cuarto byte
     * @return - Un entero conformado por los 4 bytes desplazados
     */
    private static int littleEdian(byte a, byte b, byte c, byte d){
        return (((a & 0xFF)) | ((b & 0xFF) << 8) | ((c & 0xFF) << 16) | (d & 0xFF) << 24);
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        // Aquí va su código.
        int a = 0x9E3779B9;
        int b = 0x9E3779B9;
        int c = 0xFFFFFFFF;

        int longitud = llave.length;
        int i = 0;
        while(longitud >= 12){
            a += littleEdian(llave[i], llave[i+1], llave[i+2], llave[i+3]);
            b += littleEdian(llave[i+4], llave[i+5], llave[i+6], llave[i+7]);
            c += littleEdian(llave[i+8], llave[i+9], llave[i+10], llave[i+11]);

            int[] arreglo = mezcla(a, b, c);
            a = arreglo[0];
            b = arreglo[1];
            c = arreglo[2];

            longitud -= 12;
            i += 12;
        }
        c += llave.length;
        // En caso de que al finak nos queden de 1 - 11 bytes sueltos
        switch(longitud){
            case 11:
                c += ((llave[i+10] & 0xFF) << 24);
            case 10:
                c += ((llave[i+9] & 0xFF) << 16);
            case 9:
                c += ((llave[i+8] & 0xFF) << 8);
            case 8:
                b += ((llave[i+7] & 0xFF) << 24);
            case 7:
                b += ((llave[i+6] & 0xFF) << 16);
            case 6:
                b += ((llave[i+5] & 0xFF) << 8);
            case 5:
                b += (llave[i+4] & 0xFF);
            case 4:
                a += ((llave[i+3] & 0xFF) << 24);
            case 3:
                a += ((llave[i+2] & 0xFF) << 16);
            case 2:
                a += ((llave[i+1] & 0xFF) << 8);
            case 1:
                a += (llave[i] & 0xFF);
        }
        int[] resultado = mezcla(a, b, c);
        return resultado[2];
    }

    /**
     * Método mezcla para el algoritmo de Bob Jenkins
     * @param a - primero entero
     * @param b - segundo entero
     * @param c - tercer entero
     * @return - un arreglo de los enteros mezclados con las operaciones correspondientes aplicadas
     */
    private static int[] mezcla(int a, int b, int c){
        //Primero bloque
        a -= b;
        a -= c;
        a ^= (c >>> 13);
        // Segundo bloque
        b -= c;
        b -= a;
        b ^= (a << 8);
        // Tercer bloque
        c -= a;
        c -= b;
        c ^= (b >>> 13);
        // Cuarto bloque
        a -= b;
        a -= c;
        a ^= (c >>> 12);
        // Quinto bloque
        b -= c;
        b -= a;
        b ^= (a << 16);
        // Sexto bloque
        c -= a;
        c -= b;
        c ^= (b >>> 5);
        // Septimo bloque
        a -= b;
        a -= c;
        a ^= (c >>> 3);
        // Octavo bloque
        b -= c;
        b -= a;
        b ^= (a << 10);
        // Noveno bloque
        c -= a;
        c -= b;
        c ^= (b >>> 15);

        int[] arreglo = {a, b, c};
        return arreglo;
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        // Aquí va su código.
        int h = 5381;
        for(int i = 0; i < llave.length; i++){
            h = (h*33) + (llave[i] & 0xFF);
        }
        return h;
    }
}
