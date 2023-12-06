package com.example.conecta4dehacendado.modelo;

import com.example.conecta4dehacendado.controlador.Controlador;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;
/**
 * Esta es la clase de cada IA, contiene todos los campos que se consideran necesarios.
 */
public class IA {
    /**
     * El nombre de la IA en cuestión
     */
    private String username;
    /**
     * El color de las fichas de la IA
     */
    private Color color;

    public IA(String username, Color color) {
        this.username = username;
        this.color = color;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * El siguiente método es la logica que sigue la IA para realizar su turno. Esta recorre todas las columnas y analiza
     * si en cada una de las columnas puede realizar una jugada. Si puede, las almacena en una array aparte, y a la hora
     * de realizar su turno, escoge al azar una de esta array aparte.
     * @param piezas Todas las piezas disponibles
     * @return Devuelve su acción
     */
    public int turnoIA(Circle[][] piezas){
        ArrayList<Integer> posiblesColumnas = new ArrayList<>();
        for (int i = 0; i < piezas[0].length; i++) {
            if (Controlador.esBlanca(piezas[0][i])){
                posiblesColumnas.add(i);
            }
        }
        int randomColumPosition = new Random().nextInt(posiblesColumnas.size());
        return posiblesColumnas.get(randomColumPosition);
    }
}
