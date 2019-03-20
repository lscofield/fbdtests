package ua.it1.fbd.utils;

import java.util.ArrayList;

public class Pregunta {
    public String pregunta;
    public ArrayList<String> repuestas;

    public Pregunta(String pregunta){
        this.pregunta = pregunta;
    }

    public void setRepuestas(ArrayList<String> repuestas) {
        this.repuestas = repuestas;
    }

}
