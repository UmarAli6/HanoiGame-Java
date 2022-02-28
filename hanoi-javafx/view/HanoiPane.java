package hanoi.view;

import hanoi.model.Rod;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.*;

/**
 * A sub view rendering the rods and disks.
 */
public class HanoiPane extends Pane {

    public final int MAX_N_DISKS = 8; // Level.TEDIOUS + 1
    private static final Color[] DISK_COLORS = {
        RED, BLUE, GREEN, ORANGE, BLACK, WHITE,
        MAGENTA, DARKRED, DARKBLUE, DARKGREEN
    };
    private static final Color RODS_COLOR = Color.BROWN;
    private static final Color CONTOUR_COLOR = Color.BLACK;
    private static final double STROKES_WIDTH = 1.0;

    private final Rectangle wBase;
    private final Rectangle[] wRods;
    private final Rectangle[] wDisks;
    private int nDisks;

    public HanoiPane(Controller controller) {
        wBase = new Rectangle(1, 1, RODS_COLOR);
        wRods = new Rectangle[4];
        for (int i = 0; i < wRods.length; i++) {
            wRods[i] = new Rectangle(1, 1, RODS_COLOR);
            wRods[i].setStroke(CONTOUR_COLOR);
            wRods[i].setStrokeWidth(STROKES_WIDTH);
            // add event handlers for the rods
            wRods[i].setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // get the selected rod
                    Rod selectedRod = null;
                    Object source = (Rectangle) event.getSource();
                    for (int i = 0; i < wRods.length; i++) {
                        if (source == wRods[i]) {
                            selectedRod = Rod.values()[i];
                            break;
                        }
                    }
                    controller.onRodSelected(selectedRod);
                }
            });
        }

        wDisks = new Rectangle[MAX_N_DISKS];
        for (int i = 0; i < wDisks.length; i++) {
            wDisks[i] = new Rectangle(1, 1, DISK_COLORS[i]);
            wDisks[i].setStroke(CONTOUR_COLOR);
            wDisks[i].setStrokeWidth(STROKES_WIDTH);
            wDisks[i].setMouseTransparent(true);
        }

        this.getChildren().addAll(wRods);
        this.getChildren().add(wBase);
    }

    public void initShapeSizes() {
        layoutRods(this.getWidth(), this.getHeight());
        initDiskSizes();
    }

    // called once, in initShapeSizes
    private void initDiskSizes() {
        double width = this.getWidth(), height = this.getHeight();
        double diskMaxWidth = width / 3.0;
        double diskHeight = height / (wDisks.length + 1);
        for (int i = 0; i < wDisks.length; i++) {
            double diskWidth = (0.3 + i * 0.7 / wDisks.length) * diskMaxWidth;
            layoutRectangle(wDisks[i], 0.0, 0.0,
                    diskWidth, diskHeight, diskHeight);
        }
    }

    protected final void resetDisks(int nDisks) {
        this.nDisks = nDisks;
        this.getChildren().removeAll(wDisks);
        for (int i = 0; i < nDisks; i++) {
            this.getChildren().add(wDisks[i]);
        }
    }

    protected void layoutRods(double width, double height) {
        double baseHeight = height / (wDisks.length + 1);
        double rodDist = width / 3;
        double rodWidth = 0.70 * baseHeight;
        // rods
        for (int i = 0; i < wRods.length; i++) {
            double rodX = rodDist * i + rodDist / 2.0 - rodWidth / 2.0;
            layoutRectangle(wRods[i], rodX, 0.0,
                    rodWidth, height, rodWidth);
        }
        // base
        layoutRectangle(wBase, 0, height - baseHeight,
                width, baseHeight, baseHeight);
    }

    private void layoutRectangle(Rectangle rectangle, double x, double y,
            double width, double height, double arc) {
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setArcWidth(arc);
        rectangle.setArcHeight(arc);
        rectangle.setStroke(CONTOUR_COLOR);
        rectangle.setStrokeWidth(1.0);
    }

    protected void layoutDisks(int[][] disksData) {
        double rodDist = this.getWidth() / 3.0, height = this.getHeight();
        double diskHeight = height / (wDisks.length + 1);
        for (int rod = 0; rod < disksData.length; rod++) {
            for (int pos = 0; pos < disksData[rod].length; pos++) {
                int diskSize = disksData[rod][pos];
                if (diskSize != 0) {
                    int diskIndex = diskSize - 1;
                    Rectangle wDisk = wDisks[diskIndex];
                    double x = (rod + 0.5) * rodDist - wDisk.getWidth() / 2.0;
                    wDisk.setX(x);
                    double y = (wDisks.length - pos - 1) * diskHeight;
                    wDisk.setY(y);
                }
            }
        }
    }
}
