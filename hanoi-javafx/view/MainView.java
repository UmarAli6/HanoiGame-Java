package hanoi.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import hanoi.model.Hanoi;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

/**
 * The main view for the Tower of Hanoi game. The view for rods and disks are
 * implemented in HanoiPane.
 *
 * @author Anders Lindström, anderslm@kth.se
 */
public class MainView extends VBox { // VBox for menu bar

    private final Hanoi model;
    private final HanoiPane hanoiPane;
    private final Label movesLabel;

    protected enum Level {
        EASY, MEDIUM, HARD, MASTER, TEDIOUS
    }

    public MainView(Hanoi model) {
        Controller controller = new Controller(model, this);
        this.model = model;
        this.hanoiPane = new HanoiPane(controller);
        this.movesLabel = new Label("Moves: ");

        // setup views, with layouts, and menu bar
        BorderPane borderPane = layoutViews(controller);
        MenuBar menuBar = initMenus(controller);
        this.getChildren().addAll(menuBar, borderPane);
    }

    public void fitToSize() {
        hanoiPane.initShapeSizes();
        resetView(model.getNoOfDisks());
    }

    protected void resetView(int nDisks) {
        hanoiPane.resetDisks(nDisks);
        updateView(model.getCopyOfBoard());
    }

    protected void updateView(int[][] diskData) {
        hanoiPane.layoutDisks(diskData);
        movesLabel.setText("Moves: " + model.getNoOfMoves());
    }

    /*
     * Layout details...
     */
    public static final float DIMENSION = 40.0f;

    private BorderPane layoutViews(Controller controller) {
        hanoiPane.setStyle("-fx-background-color: lightgrey;");
        hanoiPane.setStyle("-fx-border-color: lightgray");
        hanoiPane.setStyle("-fx-border-stroke: 10.0");
        hanoiPane.setPrefSize(300, 200);

        Button resetButton = new Button("Reset game");
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.onNewGameSelected();
            }
        });

        var bottomPane = new FlowPane();
        bottomPane.getChildren().addAll(movesLabel, resetButton);
        bottomPane.setPadding(new Insets(10, 0, 0, 0));
        bottomPane.setHgap(20);
        bottomPane.setAlignment(Pos.CENTER);

        var borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setBottom(bottomPane);
        borderPane.setCenter(hanoiPane);
        return borderPane;
    }

    // create the menues and the corresponding event handlers
    private MenuBar initMenus(Controller controller) {

        MenuItem newGameItem = new MenuItem("New Game");
        newGameItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.onNewGameSelected();
            }
        });

        MenuItem[] levelItems = new MenuItem[Level.values().length];
        for (int i = 0; i < levelItems.length; i++) {
            levelItems[i] = new MenuItem(Level.values()[i].toString());
            levelItems[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    MenuItem selectedItem = (MenuItem) event.getSource();
                    controller.onLevelSelected(selectedItem.getText());
                }
            });
        }
        Menu levelMenu = new Menu("Level");
        levelMenu.getItems().addAll(levelItems);

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(newGameItem, levelMenu, exitItem);

        Menu helpMenu = new Menu("Help");
        MenuItem howToItem = new MenuItem("How to...");
        howToItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showAlert("How to play", HELP_STR);
            }
        });

        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showAlert("About", ABOUT_STR);
            }
        });

        helpMenu.getItems().addAll(howToItem, aboutItem);

        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: #dddddd; ");
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        return menuBar;
    }

    /*
     *  Pop up window for messages
     */
    private final Alert alert = new Alert(AlertType.INFORMATION);
    private boolean isFirstAlertCall = true;

    protected void showAlert(String header, String message) {
        if (isFirstAlertCall) {
            alert.initOwner(this.getScene().getWindow());
            isFirstAlertCall = false;
        }
        alert.setTitle("Master Mind Info");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static final String HELP_STR
            = "The aim of this game is to move all disks from the left rod\n"
            + "to the right rod. A disk can only be moved to an empty rod\n"
            + "or a rod with a larger disk.\n"
            + "Select the rod to move the a disk from by clicking it, then\n"
            + "select the rod to move the disk to.\n"
            + "You can find more on rules and playing strategies on the\n"
            + "internet.";
    private static final String ABOUT_STR
            = "Version 0.1\n"
            + "Author(s): \n"
            + "Model developed  by [yourself]. \n"
            + "Views and controller developed by anderslm@kth.se.";
}
