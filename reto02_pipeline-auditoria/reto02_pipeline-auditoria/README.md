# Reto 02 - Pipeline de auditoría

Este proyecto es una pequeña aplicación Java de consola que demuestra cómo lanzar procesos hijos desde un programa Java y evaluar sus resultados para decidir una acción final.

## Descripción

La clase principal `org.example.reto02_Pipeline_Auditoria` ejecuta dos comandos `ping` en paralelo:

- `ping -n 1 127.0.0.1` (destino local, normalmente correcto)
- `ping -n 2 "Error 1231"` (destino inválido para provocar un error)

Después de ejecutarlos, el programa:

1. espera a que ambos procesos terminen,
2. recoge sus códigos de salida,
3. determina si hubo errores,
4. abre:
   - `Bloc de notas` si ambos procesos han terminado correctamente,
   - `Calculadora` si alguno falló.

## Objetivo

Este ejemplo sirve para practicar:

- creación de procesos con `ProcessBuilder`,
- control de la ejecución de procesos hijos,
- uso de `waitFor()` para sincronizar tareas,
- evaluación del resultado de procesos externos,
- interacción con aplicaciones del sistema operativo.

## Requisitos

- Java 21
- Maven
- Sistema operativo Windows, porque el programa abre `notepad.exe` y `calc.exe`.

## Estructura del proyecto

```text
reto02_pipeline-auditoria/
├── pom.xml
├── src/
│   └── main/
│       └── java/
│           └── org/
│               └── example/
│                   └── reto02_Pipeline_Auditoria.java
└── README.md
```

## Cómo ejecutar

Desde la raíz del proyecto:

```bash
mvn compile
mvn exec:java -Dexec.mainClass=org.example.reto02_Pipeline_Auditoria
```

Si no tienes el plugin `exec` disponible, también puedes compilar y ejecutar con:

```bash
javac -d target/classes src/main/java/org/example/reto02_Pipeline_Auditoria.java
java -cp target/classes org.example.reto02_Pipeline_Auditoria
```

## Salida esperada

El programa imprime mensajes informativos por consola, por ejemplo:

```text
=======================================
Reto de crear dos procesos en paralelo
=======================================
======================================
INICIANDO EJECUCIÓN PARALELA......
   -> Lanzando Procesos
   -> Bloqueando Java para recoger resultados
 TIEMPO TOTAL PARALELO: ...ms
=====================================
Código de salida P1: 0
Código de salida P2: 1
   -> Algún proceso falló. Abriendo Calculadora...
```

## Nota importante

El comportamiento exacto puede variar según la versión de Windows y la configuración del sistema. El programa está pensado como ejercicio de aprendizaje y no como una utilidad de producción.
