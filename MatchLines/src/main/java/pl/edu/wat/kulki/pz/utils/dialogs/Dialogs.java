package pl.edu.wat.kulki.pz.utils.dialogs;

import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import pl.edu.wat.kulki.pz.controllers.GameBoardController;
import pl.edu.wat.kulki.pz.controllers.MainController;
import pl.edu.wat.kulki.pz.utils.ResourceLoader;

public abstract class Dialogs {
    public static void makeDialog(Dialog dialog) {
        Stage scene;
        if(GameBoardController.getInstance()!=null)
            scene = (Stage) GameBoardController.getInstance().windowContainer.getScene().getWindow();
        else
            scene = (Stage) MainController.getInstance().mainContainer.getScene().getWindow();
        dialog.setResizable(true);
        dialog.getDialogPane().setPrefSize(Integer.parseInt(ResourceLoader.property("dialogWidth")), Integer.parseInt(ResourceLoader.property("dialogHeight")));
        double x = scene.getX() + scene.getWidth() / 2 - dialog.getDialogPane().getPrefWidth() / 2;
        double y = scene.getY() + scene.getHeight() / 2 - dialog.getDialogPane().getPrefHeight() / 2;
        dialog.setResizable(false);
        dialog.setX(x);
        dialog.setY(y);
    }
    public Dialogs() {
    }
}
