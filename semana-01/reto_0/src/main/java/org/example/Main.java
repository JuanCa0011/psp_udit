package org.example;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Iniciando udiversum");

        ArrayList<Episodio> catalogo = new ArrayList<>();

        catalogo.add(new Episodio("Diseño 3D - Intro", 45));
        catalogo.add(new Episodio("Animacion", 60));
        catalogo.add(new Episodio("Texturas", 50));

        //2. medicion de tiempo
        //Usamos 'long' porque los milisegundos son desde 1970 forman un numero
        //tan gigantesco que no cabe en la memoria int normal

        long inicio = System.currentTimeMillis();

        //3. bucle for:each por cada episodio dentro del catalogo
        for (Episodio ep : catalogo) {
            ep.procesar(); //Aqui ocurre el bloqueo de 2s
        }
        long fin = System.currentTimeMillis();
        //4 resultado: restamos fin menos inicio y dividimos entre mil (segundos)

        long totalSegundos = (fin - inicio)/ 1000;
        System.out.println("Total de segundos: " + totalSegundos + " segundos de bloqueo");
    }
}