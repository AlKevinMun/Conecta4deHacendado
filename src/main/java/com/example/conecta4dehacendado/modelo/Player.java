package com.example.conecta4dehacendado.modelo;

import javafx.scene.paint.Color;

/**
 * Esta es la clase de cada jugador, contiene todos los campos que se consideran necesarios.
 */
public class Player {
    /**
     * Nombre del jugador
     */
    private String username;
    /**
     * Número de movimientos de jugador
     */
    private int movements;
    /**
     * Color del jugador
     */
    private Color color;

    public Player(String username, int movements, Color color) {
        this.username = username;
        this.movements = movements;
        this.color = color;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMovements() {
        return movements;
    }

    public void setMovements(int movements) {
        this.movements = movements;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
