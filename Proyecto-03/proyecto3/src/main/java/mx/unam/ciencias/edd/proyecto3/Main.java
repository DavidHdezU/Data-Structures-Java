package main.java.mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.proyecto3.EntradaConsola;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Conjunto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedWriter;
import java.io.*;

public class Main {
    
    public static void main(String[] args) {
        SalidaArchivos archivos =  new SalidaArchivos(args);
        archivos.generarProyecto();
    }
}