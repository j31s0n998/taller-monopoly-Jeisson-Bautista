package monopoly.cells;

import monopoly.Cell;
import monopoly.MainController;
import monopoly.Player;

public class GoToJailCell extends Cell {
	
    public GoToJailCell() {
        super.setName("ve a prision");
    }

    @Override
    public void playAction(MainController mainController) {
        Player currentPlayer = mainController.getCurrentPlayer();
        mainController.sendToJail(currentPlayer);
    }
}
