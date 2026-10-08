package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class PildoraMonitor {
    public static void main(String[] args) {
        System.out.println("=== MONITOR UDITFLIX ===");
        System.out.println("Comprobando servicio...");

        // try / catch
        // Lanzar un programa externo o esperar a que termine PUEDE FALLAR
        // try -> "intentar hacer esto"
        // catch -> "Si algo sale mal, haz esto otro en vez de romper el programa"

        try {
            // ProcessBuilder es el "encargado" que prepara la orden que
            //le daremos al sistema operativo. Es como rellenar un formulario:
            //"ping" -> el programa que queremos ejecutar
            //"-n" -> opcion de Windows: numero de intentos
            //"1" -> haz solo 1 intento (asi termina mas rapido)
            //"127.0.0.1 -> a quien hacemos ping : nuestro propio ordenador
            //              (siempre responde, simula un servicio ACTIVO)
            // OJO "-n" solo vale en Windows. En Linux y Mac seria "-c"
            ProcessBuilder pb = new ProcessBuilder(
                    "ping", "-n", "1", "127.0.0.x"
            );

            // PASO 2: UNIR las dos canales de salida
            // Todo programa tiene dos canales por los que "habla"
            // - salida normal (lo que funciona bien)
            // - salida de error (los mensajes de fallo)
            // Con redirectErrorStream(true) lo juntamos a UNO SOLO
            // Así, leyendo un unico canal, vemos TODO lo que el proceso diga,
            // sea un resultado normal o un error
            pb.redirectErrorStream(true);

            //PASO 3: LANZAR el proceso
            // start() es el boton de "enviar". Ahora si el programa java.
            // Process es el objeto conel que controlamos ese programa.

            Process process = pb.start();

            //PASO 4: MOSTRAR EL PID
            // PID = Process IDentifier. es el "DNI" del proceso

            System.out.println("PID: " + process.pid());

            //PASO 5: PREPARAR la lectura de lo que dice el proceso

            //EL PROCESO PING ESCRIBE SU PROPIA CONSOLA. QUE JAVA NO VE.
            //Para escucharlo nos "conectamos" a su salida con una cadena:
            // proceso.getInputStream() -> la "tuberia" por la que sale
            // el texto del proceso ( en bytes).
            // new InputStreamReader(...) -> traduce esos byter a letras.
            // new BufferReader(...) -> nos deja leer linea a linea
            BufferedReader lector = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            // Variable donde guardamos cada línea que vayamos leyendo
            // Todavia está vacía
            String linea;

            //PASO 6: Leer todo lo que el proceso va a escribiendo
            while ((linea = lector.readLine()) != null) {
                System.out.println(linea);
            }
            // PASO 7: ESPERAR a que el proceso termine
            // waitFor() es lo que nos garantiza el codigo de salida
            int codigo = process.waitFor();

            //PASO 8: INTERPRETAR EL RESULTADO
            if (codigo == 0) {
                System.out.println("ESTADO:SERVICIO ACTIVO");
            } else {
                System.out.println("ESTADO:SERVICIO con ERROR");
            }
        }catch (IOException e) {
            System.out.println("NO SE PUDO LANZAR EL PROCESO");
        }catch (InterruptedException e) {
            System.out.println("La ejecución fue interrumpida");
        }
    }
}