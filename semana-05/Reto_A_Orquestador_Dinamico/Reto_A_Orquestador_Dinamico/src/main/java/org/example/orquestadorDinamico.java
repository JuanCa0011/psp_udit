package org.example;

import java.io.IOException;                                                        // Error de lectura o de arranque de proceso
import java.io.BufferedReader;                                                     // Lector con readLine()
import java.io.InputStream;                                                        // Flujo de bytes del archivo
import java.io.InputStreamReader;                                                  // Convierte bytes a texto
import java.nio.charset.StandardCharsets;                                          // Codificación UTF-8
import java.util.ArrayList;                                                        // Lista dinámica
import java.util.List;                                                             // Interfaz de lista

public class orquestadorDinamico {
    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();                                  // Instante de inicio para medir el tiempo total

        try {
            List<String> servidores = new ArrayList<>();                           // Colección donde se guardan las IPs

            InputStream archivoServidores = orquestadorDinamico.class.getResourceAsStream("/servidores.txt"); // Busca el archivo en src/main/resources

            if (archivoServidores == null) {                                       // null = el archivo no existe
                throw new IOException("No se encontró servidores.txt en resources"); // Lanza el error a propósito; lo recoge el catch
            }

            try (BufferedReader lector = new BufferedReader(                       // try-with-resources: se cierra solo
                    new InputStreamReader(archivoServidores, StandardCharsets.UTF_8))) {
                String linea;                                                      // Línea actual
                while ((linea = lector.readLine()) != null) {                      // Lee hasta el final del archivo
                    if (!linea.trim().isEmpty()) {                                 // Salta líneas vacías o solo con espacios
                        servidores.add(linea.trim());                              // Guarda la IP sin espacios sobrantes
                    }
                }
            }
            System.out.println("Servidores cargados: " + servidores.size());       // Cuántas IPs se cargaron

            ArrayList<Process> procesos = new ArrayList<>();                       // Guarda los procesos para esperarlos después
            for (String ip : servidores) {                                         // BUCLE 1: lanzar todos los pings
                System.out.println("  -> Lanzando ping a " + ip);
                Process p = new ProcessBuilder("ping", "-n", "1", "-w", "3000", ip).start(); // 1 paquete, máx. 3000 ms; start() no bloquea
                procesos.add(p);                                                   // Lo guardamos para la recogida
            }

            int exitos = 0;                                                        // Pings con respuesta
            int fallos = 0;                                                        // Pings sin respuesta
            for (Process p : procesos) {                                           // BUCLE 2: recoger resultados
                int codigo = p.waitFor();                                          // Bloquea hasta que termina y da su código de salida
                if (codigo == 0) {
                    exitos++;                                                      // 0 = éxito
                } else {
                    fallos++;                                                      // Distinto de 0 = fallo
                }
            }
            System.out.println("Éxitos: " + exitos + " | Fallos: " + fallos);

            if (fallos == 0) {                                                     // Todos respondieron
                System.out.println("Todo correcto -> abriendo notepad.exe");
                new ProcessBuilder("notepad.exe").start();                         // Abre el Bloc de notas
            } else if (exitos > 0) {                                               // Mezcla de éxitos y fallos
                System.out.println("Éxitos y fallos -> abriendo calc.exe");
                new ProcessBuilder("calc.exe").start();                            // Abre la Calculadora
            } else {                                                               // Ninguno respondió
                System.out.println("Todo falla -> abriendo explorer.exe");
                new ProcessBuilder("explorer.exe").start();                        // Abre el Explorador
            }

        } catch (IOException e) {                                                  // Falla la lectura o el arranque de un proceso
            System.out.println("ERROR: No se pudo leer servidores.txt o lanzar un proceso");
        } catch (InterruptedException e) {                                         // El hilo fue interrumpido en waitFor()
            System.out.println("ERROR: La espera fue interrumpida de forma inesperada");
        }

        System.out.println("Tiempo total: " + (System.currentTimeMillis() - inicio) + " ms"); // Tiempo total en milisegundos
    }
}