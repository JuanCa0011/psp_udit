# Reto A: Orquestador dinámico

Programa Java que lee una lista de servidores desde un recurso, ejecuta un `ping` a cada dirección y resume cuántos responden. Los pings se lanzan en paralelo para evitar esperar el tiempo de espera completo de cada servidor uno por uno.

## Requisitos

- Java 21
- Windows, porque los comandos `ping` y las aplicaciones que se ejecutan al final usan sintaxis y programas de Windows.

## Configuración

Las direcciones se guardan, una por línea, en `src/main/resources/servidores.txt`. El programa carga el archivo desde el classpath, por lo que Maven lo incorpora a los recursos de la aplicación.

```text
127.0.0.1
192.0.2.1
192.0.2.2
```

Las direcciones `192.0.2.x` pertenecen a un rango reservado para documentación y ejemplos; cámbialas por direcciones de servidores reales para hacer comprobaciones reales.

## Ejecución

Desde la carpeta del proyecto, con Maven instalado:

```powershell
mvn compile
mvn exec:java -Dexec.mainClass="org.example.orquestadorDinamico"
```

El proyecto no configura el plugin de ejecución de Maven, así que también se puede compilar y lanzar desde un IDE configurado con Java 21, asegurándose de que `src/main/resources` esté en el classpath.

## Funcionamiento

1. Lee `servidores.txt` desde los recursos de la aplicación e ignora líneas vacías.
2. Inicia un proceso `ping -n 1 -w 3000 <IP>` por servidor. Cada ping envía un paquete y espera como máximo 3 segundos por una respuesta.
3. Espera la finalización de los procesos y cuenta los códigos de salida: `0` es éxito y cualquier otro resultado se cuenta como fallo.
4. Según el resultado, intenta abrir el Bloc de notas si todos responden, la Calculadora si hay una mezcla de éxitos y fallos, o el Explorador de archivos si todos fallan.
5. Muestra el número de éxitos y fallos y el tiempo total de ejecución.

## Desglose gráfico del código

```text
┌─────────────────────────────────────────────┐
│ Inicio: registrar la hora                   │
└──────────────────────┬──────────────────────┘
                       ▼
┌─────────────────────────────────────────────┐
│ Cargar servidores.txt desde resources       │
│ Leer las direcciones y omitir líneas vacías │
└──────────────────────┬──────────────────────┘
                       ▼
┌─────────────────────────────────────────────┐
│ Lanzar un proceso ping por cada servidor    │
│ Todos se inician antes de esperar resultados│
└──────────────────────┬──────────────────────┘
                       ▼
┌─────────────────────────────────────────────┐
│ Esperar los procesos y contar éxitos/fallos │
└──────────────────────┬──────────────────────┘
                       ▼
             ┌─────────┴───────────┐
             │ ¿Qué resultados hay?│
             └─────────┬───────────┘
       ┌────────────────┼────────────────┐
       ▼                ▼                ▼
   0 fallos         Éxitos y fallos    0 éxitos
       │                │                │
       ▼                ▼                ▼
   notepad.exe        calc.exe        explorer.exe
       └────────────────┼────────────────┘
                        ▼
┌─────────────────────────────────────────────┐
│ Mostrar el tiempo total transcurrido        │
└─────────────────────────────────────────────┘
```

## Reflexiones

### 1. Mundo real: comprobaciones de estado

Esta lógica de health checks se usa en muchos sistemas profesionales. Kubernetes comprueba cada contenedor con sondas de vivacidad y disponibilidad, y lo reinicia o deja de enviarle tráfico si falla. Los balanceadores de carga (como los de AWS, Azure o Nginx) sacan de la rotación a los servidores que no responden. Herramientas de monitorización como Nagios, Zabbix, Prometheus o Datadog lanzan comprobaciones periódicas y avisan cuando algo se cae. Un `ping` solo comprueba la conectividad básica. En producción se suelen comprobar también los puertos o endpoints HTTP del servicio.

### 2. Reflexión sobre mis tiempos

Con `ping -n 2`, cada comprobación tarda aproximadamente 1 segundo, porque el segundo paquete se envía un segundo después del primero. Para servidores lentos o que no responden, cada uno podría tardar bastante más. Llamo **T** al tiempo de una comprobación. Por ejemplo, con T = 3 s:

- **Secuencial:** los procesos se esperan uno tras otro, así que el tiempo se suma: 50 × 3 s = **150 s** (2 min 30 s).
- **Paralelo:** los 50 pings se lanzan antes de esperar a ninguno, así que sus esperas se solapan: tarda aproximadamente **3 s**, más un pequeño coste por crear los procesos.

El paralelo no es gratis: el tiempo real depende de la carga del equipo, de la red y del límite de procesos simultáneos. Aun así, la mejora crece con el número de servidores. Sustituye T por los tiempos que hayas medido al ejecutar el programa.
