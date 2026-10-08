package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("UDITFLIX");
        System.out.println("======================================");
        System.out.println("CATÁLOGO");
        System.out.println("======================================");

        // PASO 1: Matriz bidimensional [5][2] para guardar el nombre del vídeo
        // y su dirección de comprobación IP (127.0.0.1 -> ACTIVO, IP inventada -> CAÍDO).
        String[][] videos = {
                {"Animación 3D", "10.255.255.1"}, // Dirección inexistente (CAÍDO)
                {"Videojuegos", "10.255.255.2"},  // Dirección inexistente (CAÍDO)
                {"Kotlin", "10.255.255.3"},       // Dirección inexistente (CAÍDO)
                {"Android", "127.0.0.1"},         // Dirección activa
                {"Flutter", "127.0.0.1"}          // Dirección activa
        };

        // PASO 2: Recorrer la matriz mediante un bucle for tradicional
        for (int i = 0; i < videos.length; i++) {
            String nombreVideo = videos[i][0];
            String ipComprobacion = videos[i][1];

            System.out.println("[VÍDEO] " + nombreVideo);

            try {
                // ProcessBuilder es el encargado de preparar el comando para el SO.
                // OJO: En Windows se usa "-n" y un timeout con "-w" (ej: -w 1000 ms)
                // para que los vídeos caídos no tarden demasiado en responder.
                ProcessBuilder pb = new ProcessBuilder(
                        "ping", "-n", "1", "-w", "1000", ipComprobacion
                );

                // Unir la salida estándar y el canal de errores a uno solo
                pb.redirectErrorStream(true);

                // PASO 3: LANZAR el proceso
                Process process = pb.start();

                // PASO 4: MOSTRAR EL PID
                System.out.println("PID: " + process.pid());

                // PASO 5: PREPARAR la lectura de la salida del proceso
                BufferedReader lector = new BufferedReader(
                        new InputStreamReader(process.getInputStream())
                );
                String linea;

                // Leer todas las líneas emitidas por el comando ping
                while ((linea = lector.readLine()) != null) {
                    // Opcional: Descomentar si deseas ver la salida completa del ping
                    // System.out.println(linea);
                }

                // PASO 6: ESPERAR a que el proceso termine y obtener el código de salida
                int codigo = process.waitFor();

                // PASO 7 y 8: DETERMINAR Y MOSTRAR EL ESTADO
                if (codigo == 0) {
                    System.out.println("ESTADO: ACTIVO");
                } else {
                    System.out.println("ESTADO: CAÍDO");
                }

            } catch (IOException e) {
                System.out.println("NO SE PUDO LANZAR EL PROCESO");
            } catch (InterruptedException e) {
                System.out.println("La ejecución fue interrumpida");
            }
            System.out.println("--------------------------------------");
        }

        System.out.println("======================================");
        System.out.println("COMPROBACIÓN FINALIZADA");
        System.out.println("======================================");
    }
}

