package monopoly.gui;

import javax.swing.JOptionPane;
import monopoly.*;
import monopoly.gameboards.GameBoardDefault;

public class Main {

    private static final MainController MAIN_CONTROLLER = new MainController();
    
    
    
    
    private static int inputNumberOfPlayers(MainWindow window) {
        int numPlayers = 0;
        while(numPlayers < 2 || numPlayers > BoardController.MAX_PLAYER) {
            String numberOfPlayers = JOptionPane.showInputDialog(
                window, 
                "Cuantos jugadores"
            );
            if (numberOfPlayers == null) {
                System.exit(0);
            }
            try {
                numPlayers = Integer.parseInt(numberOfPlayers);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    window, 
                    "Por favor ingrese un número"
                );
                continue;
            }
            if (numPlayers < 2 || numPlayers > BoardController.MAX_PLAYER) {
                JOptionPane.showMessageDialog(
                    window, 
                    "Por favor ingrese un número entre 2 y 8"
                );
            } else {
                MAIN_CONTROLLER.setNumberOfPlayers(numPlayers);
            }
        }
        return numPlayers;
    }

     public static void main(String[] args) {
        MainWindow window = new MainWindow(MAIN_CONTROLLER);
        // ... (código para cargar el tablero personalizado si se proporciona)

        int numPlayers = inputNumberOfPlayers(window);

        // Inicializar el juego antes de crear los jugadores
        MAIN_CONTROLLER.setGameBoard(new GameBoardDefault()); // Tablero por defecto
        MAIN_CONTROLLER.setNumberOfPlayers(numPlayers);
        MAIN_CONTROLLER.reset(); // Reiniciar el juego

        for (int i = 0; i < numPlayers; i++) {
            String name = JOptionPane.showInputDialog(
                window,
                "Por favor ingrese el nombre del jugador " + (i + 1)
            );
            if (name == null || name.trim().isEmpty()) {
                name = "Player " + (i + 1); // Nombre por defecto
            }

            int startingMoney = 0;
            while (startingMoney <= 0) {
                String inputMoney = JOptionPane.showInputDialog(
                    window,
                    "Por favor ingrese el dinero inicial para " + name + ":"
                );
                if (inputMoney == null) {
                    startingMoney = 1500; // Dinero inicial por defecto
                    break; 
                }
                try {
                    startingMoney = Integer.parseInt(inputMoney);
                    if (startingMoney <= 0) {
                        JOptionPane.showMessageDialog(window, "El dinero inicial debe ser mayor que cero.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(window, "Por favor ingrese un número válido.");
                }
            }

            // Crea y configura el jugador:
            Player player = new Player(MAIN_CONTROLLER.getGameBoard().getCell(0), startingMoney); // Inicializa en GO
            player.setName(name);
            MAIN_CONTROLLER.getPlayer(i).setMoney(startingMoney); // Cambia el dinero al creado
            MAIN_CONTROLLER.getPlayer(i).setName(name); // Cambia el nombre al creado
        }

        window.setupGameBoard(MAIN_CONTROLLER.getGameBoard());
        window.setVisible(true);
        MAIN_CONTROLLER.setGUI(window);
        MAIN_CONTROLLER.startGame();
    }
}

