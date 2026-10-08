package org.example;

import java.io.IOException;

public class CodigoSalida {
    public static void main(String[] args) {
        System.out.println("============");
        System.out.println("     COMPROBACIÓN DE SERVIDOR");
        System.out.println("============");

        try {
            // 1. PREPARAMOS EL PROCESO EXTERNO
            ProcessBuilder pb = new ProcessBuilder(
                    "ping",
                    "-n",
                    "1",
                    "8.8.8.8"
            );

            // 2. LANZAMOS EL PROCESO
            Process process = pb.start();

            // 3. OBTENDREMOS EL PID
            System.out.println("PID: " + process.pid());

            // 4. ESPERAMOS A QUE TERMINE
            // Usamos 'process' para coincidir con el nombre de la variable declarada
            int codigoSalida = process.waitFor();

            // 5. MOSTRAMOS EL CÓDIGO DE SALIDA
            System.out.println("CodigoSalida: " + codigoSalida);

            // 6. INTERPRETAMOS EL RESULTADO
            if (codigoSalida == 0) {
                System.out.println("ESTADO: ACTIVO");
            } else {
                System.out.println("ESTADO: CAÍDO");
            }
        } catch (IOException e) { // Cambiado de IDException a IOException
            System.out.println("Error al lanzar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("La espera del proceso fue interrumpida");
        }

        System.out.println("============");
        System.out.println("      FIN DE LA COMPROBACIÓN");
        System.out.println("============");
    }
}