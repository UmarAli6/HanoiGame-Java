package hanoi.view;

import hanoi.model.Rod;
import hanoi.model.Hanoi;

/**
 * The controller is responsible for defining actions on user events, and for
 * mediating between the model and the view.
 *
 * @author Anders Lindström, anderslm@kth.se
 */
public class Controller {

    private final Hanoi model;
    private final MainView view;

    private Rod fromRod = null, toRod = null;

    protected Controller(Hanoi model, MainView view) {
        this.model = model;
        this.view = view;
    }

    /*
     * Called when a new game is selected. Same number of disks as previous.
     */
    protected void onNewGameSelected() {
        model.reset();
        int nDisks = model.getNoOfDisks();
        view.resetView(nDisks);
    }

    /*
     * Called from HanoiPane when the user selects a rod. The user must select
     * two "legal" rods, fromRod and toRod. The move is performed after the
     * second call (if legal).
     */
    protected void onRodSelected(Rod selectedRod) {
        if (selectedRod == fromRod || selectedRod == toRod) {
            return; // ignore accidental double clicks
        }
        if (fromRod == null) {
            fromRod = selectedRod;
        } else {
            toRod = selectedRod;
            if (model.isLegalMove(fromRod, toRod)) {
                model.makeMove(fromRod, toRod);
                view.updateView(model.getCopyOfBoard());
                if (model.isSolved()) {
                    view.showAlert(
                            "Congratulations!",
                            "You completed the game!");
                }
            } else {
                view.showAlert("Warning", "Illegal move!");
            }
            fromRod = null;
            toRod = null;
        }
    }

    /*
     * Called when level is selected. Reset model with new number of disks.
     */
    protected void onLevelSelected(String levelStr) {
        int level;
        switch (levelStr) {
            case "EASY":
                level = 3;
                break;
            case "MEDIUM":
                level = 4;
                break;
            case "HARD":
                level = 5;
                break;
            case "MASTER":
                level = 6;
                break;
            case "TEDIOUS":
                level = 7;
                break;
            default:
                level = 4;
        }
        model.reset(level);
        int nDisks = model.getNoOfDisks();
        view.resetView(nDisks);
    }
}
