package hanoi;

import hanoi.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import hanoi.model.Hanoi;

/**
 * The entry point for the application. The model and the view , including the
 * controller, are created. The view is added to the main window (stage).
 *
 * @author Anders Lindström, anderslm@kth.se
 */
public class HanoiApp extends Application {

    @Override
    public void start(Stage stage) {

        var model = new Hanoi(5); // medium level
        var view = new MainView(model);

        var scene = new Scene(view);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // cant decide the dimensions before layout has completetd
        view.fitToSize();
    }

    public static void main(String[] args) {
        HanoiApp.launch(); // will eventually call the start method above
    }
}
