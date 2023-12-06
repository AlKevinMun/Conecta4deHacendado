package com.example.conecta4dehacendado.controlador;

import com.example.conecta4dehacendado.excepcions.EmptyUsername;
import com.example.conecta4dehacendado.modelo.IA;
import com.example.conecta4dehacendado.modelo.Player;
import com.example.conecta4dehacendado.modelo.Tiempo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controlador {


    @FXML
    private MenuItem cambiarNombreP1;

    @FXML
    private MenuItem cambiarNombreP2;

    @FXML
    private MenuItem close;

    @FXML
    private ColorPicker colorFicha1;

    @FXML
    private ColorPicker colorFicha2;

    @FXML
    private MenuItem hvsh;

    @FXML
    private MenuItem hvsia;

    @FXML
    private MenuItem iavsia;

    @FXML
    private MenuButton selectMode;

    @FXML
    private MenuItem start;

    @FXML
    private Label temps;
    @FXML
    private Label turno;
    @FXML
    private ColumnConstraints col0,col1,col2,col3,col4,col5,col6;

    @FXML
    private Circle f00,f10,f20,f30,f40,f50,f60,
            f01,f11,f21,f31,f41,f51,f61,
            f02,f12,f22,f32,f42,f52,f62,
            f03,f13,f23,f33,f43,f53,f63,
            f04,f14,f24,f34,f44,f54,f64,
            f05,f15,f25,f35,f45,f55,f65;

    private Circle [] [] fichas;

    private ColumnConstraints[] columnas;

    private Player player1 = new Player("player1",0, Color.RED);
    private Player player2 = new Player("player2",0, Color.YELLOW);
    private IA ia1 = new IA("Maquina1",Color.YELLOW);
    private IA ia2 = new IA("Maquina2", Color.RED);
    private Tiempo tiempo = new Tiempo(0,0,0);

    /**
     * En este método se inicializa todo lo necesario al entrar al programa.
     */
    @FXML
    private void initialize(){
        fichas = new Circle[][] {
                {f00,f10,f20,f30,f40,f50,f60},
                {f01,f11,f21,f31,f41,f51,f61},
                {f02,f12,f22,f32,f42,f52,f62},
                {f03,f13,f23,f33,f43,f53,f63},
                {f04,f14,f24,f34,f44,f54,f64},
                {f05,f15,f25,f35,f45,f55,f65}
        };

    }

    /**
     * En el siguiente método se cambia el color de las fichas del jugador 1 tanto en su clase propia como en CSS con el color seleccionado en el color picker.
     */
    @FXML
    private void colorFichaP1(){

        Color color = colorFicha1.getValue();
        String colorCorrecto = parseColor(color.toString());
        System.out.println(colorCorrecto);
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 7; y++) {
                if (fichas[x][y].getFill().equals(player1.getColor())){
                    fichas[x][y].setStyle("-fx-fill: #"+colorCorrecto+";");
                }
            }
        }
        player1.setColor(Color.web(colorCorrecto));
        System.out.println(player1.getColor());
    }
    /**
     * En el siguiente método se cambia el color de las fichas del jugador 2 tanto en su clase propia como en CSS con el color seleccionado en el color picker.
     */
    @FXML
    private void colorFichaP2(){

        Color color = colorFicha2.getValue();
        String colorCorrecto = parseColor(color.toString());
        System.out.println(colorCorrecto);
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 7; y++) {
                if (fichas[x][y].getFill().equals(player2.getColor())){
                    fichas[x][y].setStyle("-fx-fill: #"+colorCorrecto+";");
                }
            }
        }
        player2.setColor(Color.web(colorCorrecto));
        System.out.println(player2.getColor());
    }

    /**
     * Realiza un parse del color dado en bruto por parte del color picker y lo transforma en un hexadecimal
     * @param color el color proporcionado por un color picker el cual requiere ser transformado
     * @return color en hexadecimal
     */
    private String parseColor(String color){
        String regex = "0x([0-9a-fA-F]+)ff";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(color);

        if (matcher.find()) {
            String extractedValue = matcher.group(1);
            return extractedValue;
        } else {
            System.out.println("No se encontró coincidencia.");
        }
        return null;
    }

    /**
     * Boolean para saber qué usuario se quiere cambiar el nombre
     */
    boolean p1nick = true;
    /**
     * Alerta que se utiliza en el exception al obtener un nombre vació.
     */
    Alert noUsername = new Alert(Alert.AlertType.ERROR);

    /**
     * Método para cambiar el nombre del primer jugador.
     */
    @FXML
    private void seleccionarNombreP1(){
        p1nick = true;
        cambiarNombreP1.setOnAction(e -> {
            try {
                mostrarVentanaP();
            } catch (EmptyUsername emptyUsername) {
                noUsername.setTitle("Nombre vació");
                noUsername.setContentText("Has dejado el nombre del jugador vació, esa acción no esta permitida.");
                noUsername.show();
            }
        });

    }
    /**
     * Método para cambiar el nombre del segundo jugador.
     */
    @FXML
    private void seleccionarNombreP2(){
        p1nick = false;
        cambiarNombreP2.setOnAction(e -> {
            try {
                mostrarVentanaP();
            } catch (EmptyUsername emptyUsername) {
                noUsername.setTitle("Nombre vació");
                noUsername.setContentText("Has dejado el nombre del jugador vació, esa acción no esta permitida.");
                noUsername.show();
            }
        });
    }

    /**
     * Dialog que se muestra al ejecutar el código para cambiar el nombre de uno de los jugadores. En este es donde se
     * inserta el nombre que deseas.
     * @throws EmptyUsername Si el nombre insertado esta vació, salta este throw y se activa una warning al respecto.
     */
    private void mostrarVentanaP()throws EmptyUsername {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Cambiar nombre del usuario");

        //Contenido del diálogo
        Label label = new Label("Inserta el nuevo nombre del usuario:");
        TextField textField = new TextField();
        dialog.getDialogPane().setContent(new VBox(10, label, textField));

        // Botones del diálogo
        ButtonType okButton = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Manejar el resultado
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                return textField.getText();
            }
            return null;
        });

        // Mostrar el diálogo y esperar hasta que se cierre
        Optional<String> result = dialog.showAndWait();

        // Verifica si el nombre no esta vacio
        if(result.get().length()==0) throw new EmptyUsername();

        // Manejar los datos ingresados
        result.ifPresent(data -> {
            System.out.println("Datos ingresados: " + data);
            if (p1nick) {
                player1.setUsername(data);
            } else player2.setUsername(data);
        });

    }

    /**
     * Dialog que aparece cuando un jugador ha ganado la partida
     */
    private void ventanaVictoria(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("¡Has ganado!");

        if (winP1) {
            //Contenido del diálogo
            Label label = new Label("El jugador " + player1.getUsername() + " ha ganado la partida");
            dialog.getDialogPane().setContent(new VBox(10, label));
        }
        else {
            Label label = new Label("El jugador " + player2.getUsername() + " ha ganado la partida");
            dialog.getDialogPane().setContent(new VBox(10, label));
        }

        // Botones del diálogo
        ButtonType okButton = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Mostrar el diálogo y esperar hasta que se cierre
        Optional<String> result = dialog.showAndWait();
    }

    /**
     * El siguiente método es para seleccionar el método que se quiere jugar (IA vs IA no es funcional)
     */
    @FXML
    private void seleccionarModo(){
        hvsh.setOnAction(e -> jugadorvsJugador());

        hvsia.setOnAction(e -> jugadorvsMaquina());

        iavsia.setOnAction(e -> maquinavsMaquina());
    }

    /**
     * Resetea la partida
     */
    private void limpiarTabla() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                fichas[i][j].setStyle("-fx-fill: white;");
            }
        }
    }

    boolean activeHvsh = false;
    boolean activeHvsia = false;
    boolean activeIavsia = false;
    int columnaIA = 0;
    Alert noModeSelected = new Alert(Alert.AlertType.ERROR);

    /**
     * Este método es el que se activa cuando se pulsa un círculo sin haber seleccionado el modo de juego.
     */
    @FXML
    private void action(){
        if (activeHvsh) {
            jugadorvsJugador();
        } else if (activeHvsia){
            jugadorvsMaquina();
        } else if (activeIavsia) {
            maquinavsMaquina();
        } else {
            noModeSelected.setTitle("Modo de juego sin seleccionar");
            noModeSelected.setContentText("No has seleccionado ningún modo de juego. Para empezar a jugar debes seleccionarlo en el menu correspondiente.");
            noModeSelected.show();
        }
    }

    /**
     * En este método se encuentra toda la logica del jugador contra jugador.
     * @param fila la fila en la que se encuentra el jugador
     * @param columna la columna en la que se encuentra el jugador
     */
    @FXML
    private void dobleJugador(int fila, int columna) {
        for (int i = 5; i >= 0; i--) {
            Circle pieza = fichas[i][columna];
            String colorCorrectoP1 = parseColor(player1.getColor().toString());
            String colorCorrectoP2 = parseColor(player2.getColor().toString());

            // Verifica que es una ficha modificable, además de si el modo está activado
            if (esBlanca(pieza) && activeHvsh) {
                if (comprobarVictoria()) {

                    ventanaVictoria();
                } else{
                    if (turnoP1) {
                        turno.setText("Turno: " + player2.getUsername());
                        String colorCorrecto = parseColor(player1.getColor().toString());
                        pieza.setStyle("-fx-fill: #"+colorCorrecto+";");
                        player1.setMovements(player1.getMovements() + 1);
                        System.out.println("Movimientos de player 1: " + player1.getMovements());
                        turnoP1 = false;
                    } else {
                        turno.setText("Turno: " + player1.getUsername());
                        String colorCorrecto = parseColor(player2.getColor().toString());
                        pieza.setStyle("-fx-fill: #"+colorCorrecto+";");
                        player2.setMovements(player2.getMovements() + 1);
                        System.out.println("Movimientos de player 2: " + player2.getMovements());
                        turnoP1 = true;
                    }
                }
                System.out.println(player1.getColor());
                break; // Terminar la búsqueda una vez que se ha encontrado una ficha blanca
            }
        }
    }

    /**
     * En este método se encuentra toda la logica del jugador contra jugador.
     * @param fila la fila en la que se encuentra el jugador
     * @param columna la columna en la que se encuentra el jugador
     */

    @FXML
    private void jugadorUnico(int fila, int columna) throws InterruptedException {
        for (int i = 5; i >= 0; i--) {
            Circle pieza = fichas[i][columna];

            // Verifica que es una ficha modificable
            if (esBlanca(pieza) && activeHvsia) {
                if (comprobarVictoria()) {
                    ventanaVictoria();
                } else{
                    turno.setText("Turno: " + ia2.getUsername());
                    String colorCorrecto = parseColor(player1.getColor().toString());
                    pieza.setStyle("-fx-fill: #"+colorCorrecto+";");
                    player1.setMovements(player1.getMovements() + 1);
                    System.out.println("Movimientos de player 1: " + player1.getMovements());

                    columnaIA = ia2.turnoIA(fichas);
                    System.out.println("Eleccion IA: "+columnaIA);

                    turno.setText("Turno: " + player1.getUsername());
                    boolean colocadaCasillaIA = false;
                    for (int j = 5; j >= 0 && !colocadaCasillaIA ; j--) {
                        Circle pieza2 = fichas[j][columnaIA];
                        if(esBlanca(pieza2)){
                            pieza2.setStyle("-fx-fill: yellow;");
                            colocadaCasillaIA = true;
                        }
                    }
                }
                break; // Terminar la búsqueda una vez que se ha encontrado una ficha blanca
            }
        }
    }

    /*private void maquinas(int fila, int columna) throws InterruptedException {
        for (int i = 5; i >= 0; i--) {
            Circle pieza = fichas[i][columna];

            // Verifica que es una ficha modificable
            if (esBlanca(pieza) && activeHvsia) {
                if (checkWinner(fichas, player1.getColor()) || checkWinner(fichas, ia2.getColor())) {
                    if (checkWinner(fichas, player1.getColor())) winP1 = true;
                    else winP1=false;
                    System.out.println(checkWinner(fichas, ia2.getColor()));
                    ventanaVictoria();
                } else{
                    turno.setText("Turno: " + ia2.getUsername());
                    String colorCorrecto = parseColor(player1.getColor().toString());
                    pieza.setStyle("-fx-fill: #"+colorCorrecto+";");
                    player1.setMovements(player1.getMovements() + 1);
                    System.out.println("Movimientos de player 1: " + player1.getMovements());

                    columnaIA = ia2.turnoIA(fichas);
                    System.out.println("Eleccion IA: "+columnaIA);

                    turno.setText("Turno: " + player1.getUsername());
                    boolean colocadaCasillaIA = false;
                    for (int j = 5; j >= 0 && !colocadaCasillaIA ; j--) {
                        Circle pieza2 = fichas[j][columnaIA];
                        if(esBlanca(pieza2)){
                            pieza2.setStyle("-fx-fill: yellow;");
                            colocadaCasillaIA = true;
                        }
                    }
                }
                break; // Terminar la búsqueda una vez que se ha encontrado una ficha blanca
            }
        }
    }
     */

    /**
     * El siguiente método verifica si la ficha que se está pulsando es blanca, si lo es, se puede colocar una ficha
     * si no, ya hay una ficha colocada en esa posición
     * @param pieza La ficha que se quiere analizar
     * @return devuelve si la ficha es, o no de color blanca
     */
    public static boolean esBlanca(Circle pieza) {
        return pieza.getFill().equals(Color.WHITE);
    }

    /**
     * El siguiente método comprueba todas las posibilidades de victoria, y devuelve true o false dependiendo si hay o no ganador
     * @return Devuelve un boolean dependiendo si existe un ganador
     */
    private boolean comprobarVictoria() {
        return comprobarFilas() || comprobarColumnas() || comprobarDiagonales();
    }

    /**
     * Comprueba las posibilidades horizontales
     * @return devuelve true o false dependiendo si hay o no ganador
     */
    private boolean comprobarFilas() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7 - 3; j++) {
                if (fichas[i][j].getFill().equals(player1.getColor()) &&
                        fichas[i][j].getFill().equals(fichas[i][j + 1].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i][j + 2].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i][j + 3].getFill())) {
                    winP1=true;
                    return true;
                }else if (fichas[i][j].getFill().equals(player2.getColor()) &&
                        fichas[i][j].getFill().equals(fichas[i][j + 1].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i][j + 2].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i][j + 3].getFill())) {
                    winP1=false;
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Comprueba las posibilidades verticales
     * @return devuelve true o false dependiendo si hay o no ganador
     */
    private boolean comprobarColumnas() {
        for (int i = 0; i < 6 - 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (fichas[i][j].getFill().equals(player1.getColor()) &&
                        fichas[i][j].getFill().equals(fichas[i + 1][j].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i + 2][j].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i + 3][j].getFill())) {
                    winP1=true;
                    return true;
                }else if (fichas[i][j].getFill().equals(player2.getColor()) &&
                        fichas[i][j].getFill().equals(fichas[i + 1][j].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i + 2][j].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i + 3][j].getFill())) {
                    winP1=false;
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Comprueba las posibilidades diagonales
     * @return devuelve true o false dependiendo si hay o no ganador
     */
    private boolean comprobarDiagonales() {
        // Comprobar diagonales descendentes
        for (int i = 0; i < 6 - 3; i++) {
            for (int j = 0; j < 7 - 3; j++) {
                if (fichas[i][j].getFill().equals(player1.getColor()) &&
                        fichas[i][j].getFill().equals(fichas[i + 1][j + 1].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i + 2][j + 2].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i + 3][j + 3].getFill())) {
                    winP1=true;
                    return true;
                }else if (fichas[i][j].getFill().equals(player2.getColor()) &&
                        fichas[i][j].getFill().equals(fichas[i + 1][j + 1].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i + 2][j + 2].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i + 3][j + 3].getFill())) {
                    winP1=false;
                    return true;
                }
            }
        }

        // Comprobar diagonales ascendentes
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 7 - 3; j++) {
                if (fichas[i][j].getFill().equals(player1.getColor()) &&
                        fichas[i][j].getFill().equals(fichas[i - 1][j + 1].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i - 2][j + 2].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i - 3][j + 3].getFill())) {
                    winP1=true;
                    return true;
                }else if (fichas[i][j].getFill().equals(player2.getColor()) &&
                        fichas[i][j].getFill().equals(fichas[i - 1][j + 1].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i - 2][j + 2].getFill()) &&
                        fichas[i][j].getFill().equals(fichas[i - 3][j + 3].getFill())) {
                    winP1=false;
                    return true;
                }
            }
        }

        return false;
    }

    /*
    @FXML
    private void comprobarColumna(){
        System.out.println("a");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                Circle pieza = fichas[i][j];
                pieza.setOnMouseClicked(e -> {
                    pieza.setStyle("-fx-fill: black;");
                });

            }
        }
    }
*/
    /**
     * Para saber si es el turno del jugador 1, o del 2
     */
    private boolean turnoP1=true;
    /**
     * Para saber si el ganador es el jugador 1, o el 2
     */
    private boolean winP1 = true;

    /**
     * Método al que se accede mediante los menus. En este caso es el método de jugador vs jugador.
     */
    private void jugadorvsJugador(){
        activeHvsh = true;
        activeHvsia = false;
        activeIavsia = false;
        limpiarTabla();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                int filaActual = i;
                int columnaActual = j;
                Circle pieza = fichas[i][j];

                pieza.setOnMouseClicked(e -> {
                    dobleJugador(filaActual, columnaActual);
                });
            }
        }
    }
    /**
     * Método al que se accede mediante los menus. En este caso es el método de jugador vs máquina.
     */
    private void jugadorvsMaquina(){
        activeHvsh = false;
        activeHvsia = true;
        activeIavsia = false;
        limpiarTabla();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                int filaActual = i;
                int columnaActual = j;
                Circle pieza = fichas[i][j];

                pieza.setOnMouseClicked(e -> {
                    try {
                        jugadorUnico(filaActual, columnaActual);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        }

    }
    /**
     * Método al que se accede mediante los menus. En este caso es el método de máquina vs máquina. (No está finalizado)
     */
    private void maquinavsMaquina(){
        activeHvsh = false;
        activeHvsia = false;
        activeIavsia = true;
        limpiarTabla();
        System.out.println("Funca");
    }

}