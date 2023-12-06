package com.example.conecta4dehacendado.modelo;

/**
 * Una clase para gestionar el tiempo de cada partida (Al final no se ha utilizado)
 */
public class Tiempo {
    int seconds;
    int minutes;
    int hours;

    public Tiempo(int seconds, int minutes, int hours) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return String.format("Tiempo: %02d:%02d:%02d", hours, minutes, seconds);
    }

    public void resetearTimer(){
        this.seconds=0;
        this.minutes=0;
        this.hours=0;
    }

    public static void incrementarTiempo(Tiempo tiempo) {
        tiempo.seconds++;

        if (tiempo.seconds == 60) {
            tiempo.seconds = 0;
            tiempo.minutes++;

            if (tiempo.minutes == 60) {
                tiempo.minutes = 0;
                tiempo.hours++;

                if (tiempo.hours == 24) {
                    tiempo.hours = 0;
                }
            }
        }
    }


    public Tiempo restar(Tiempo otroTiempo) {
        int totalSeconds = (this.hours - otroTiempo.hours) * 3600 +
                (this.minutes - otroTiempo.minutes) * 60 +
                (this.seconds - otroTiempo.seconds);

        int resultHours = totalSeconds / 3600;
        int resultMinutes = (totalSeconds % 3600) / 60;
        int resultSeconds = totalSeconds % 60;

        return new Tiempo(resultSeconds, resultMinutes, resultHours);
    }
}
