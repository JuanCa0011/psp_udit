package org.example;

public class LanzadorProcesos {
    public static void main(String[] args) {
        System.out.println("Lanzador procesos");

        ProcessBuilder pb = new ProcessBuilder("notepad.exe");
    }
}
