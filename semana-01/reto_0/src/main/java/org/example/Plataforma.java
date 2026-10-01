package org.example;

import java.util.ArrayList;

public class Plataforma {
    //Atributo privado: Una lista elastica que crecera segun anadamos videos
    private ArrayList<Episodio> episodios;

    public Plataforma() {
        //muy importante: si no inicializamos la lista con new
        //java lanzará un error NullPointerexception al intentar usarla
        this.catalogo,add(e);
    }
    public void procesarCatalogo() {
        for (Episodio episodio : this.catalogo){
            episodio.procesar();
        }
    }
}
