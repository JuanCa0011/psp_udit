package org.example;

import java.io.IOException;

public class reto02_Pipeline_Auditoria {
    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println("Reto de crear dos procesos en paralelo");
        System.out.println("=======================================");

        try {
            System.out.println("======================================");
            System.out.println("INICIANDO EJECUCIÓN PARALELA..........");

            long inicioParalelo = System.currentTimeMillis();

            System.out.println("   -> Lanzando Procesos ");
            Process p1 = new ProcessBuilder("ping", "-n", "1", "127.0.0.1").start();
            Process p2 = new ProcessBuilder("ping", "-n", "2", "Error 1231").start();

            System.out.println("   -> Bloqueando Java para recoger resultados");
            // waitFor() devuelve el código de salida del proceso
            int codigo1 = p1.waitFor();
            int codigo2 = p2.waitFor();

            long finParalelo = System.currentTimeMillis();
            System.out.println(" TIEMPO TOTAL PARALELO: " + (finParalelo - inicioParalelo) + "ms\n");
            System.out.println("=====================================");

            System.out.println("Código de salida P1: " + codigo1);
            System.out.println("Código de salida P2: " + codigo2);

            // Lógica final: ambos OK -> Bloc de Notas; alguno con error -> Calculadora
            if (codigo1 == 0 && codigo2 == 0) {
                System.out.println("   -> Ambos procesos correctos. Abriendo Bloc de Notas...");
                new ProcessBuilder("notepad.exe").start();
            } else {
                System.out.println("   -> Algún proceso falló. Abriendo Calculadora...");
                new ProcessBuilder("calc.exe").start();
            }

        } catch (IOException e) {
            System.out.println("ERROR: No se pudo lanzar el proceso");
        } catch (InterruptedException e) {
            System.out.println("ERROR: La espera fue interrumpida de forma inesperada");
        }
    }
}