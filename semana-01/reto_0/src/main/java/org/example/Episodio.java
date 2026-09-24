package org.example;

import java.util.ArrayList;

// 1. Clase principal
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Iniciando udiversum");

        ArrayList<Episodio> catalogo = new ArrayList<>();

        // Carga de datos al catálogo
        catalogo.add(new Episodio("Diseño 3D - Intro", 45));
        catalogo.add(new Episodio("Animacion", 60));
        catalogo.add(new Episodio("Texturas", 50));

        // Medicion de tiempo
        long inicio = System.currentTimeMillis();

        // Bucle for-each para procesar cada episodio
        for (Episodio ep : catalogo) {
            ep.procesar(); // Simula el bloqueo de 2 segundos
        }

        long fin = System.currentTimeMillis();

        // Resultado en segundos
        long totalSegundos = (fin - inicio) / 1000;
        System.out.println("Total de segundos: " + totalSegundos + " segundos de bloqueo");
    }
}

// 2. Clase Episodio (dentro del mismo archivo Main.java)
class Episodio {
    private String titulo;
    private int duracion;

    public Episodio(String titulo, int duracion) {
        this.titulo = titulo;
        this.duracion = duracion;
    }

    public void procesar() {
        System.out.println("Procesando episodio: " + titulo);
        try {
            // Pausa la ejecución durante 2 segundos (2000 ms)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracion() {
        return duracion;
    }
}