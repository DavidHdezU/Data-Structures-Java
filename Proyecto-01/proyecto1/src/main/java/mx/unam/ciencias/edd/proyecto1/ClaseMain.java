package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.proyecto1.LeerEscribir;

public class ClaseMain {

    public static void main(String[] args) {
        LeerEscribir archivo = new LeerEscribir();
        archivo.verificaArgumentos(args);
    }
}