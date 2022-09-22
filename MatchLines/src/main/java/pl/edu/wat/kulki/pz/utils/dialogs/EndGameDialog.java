package pl.edu.wat.kulki.pz.utils.dialogs;

import javafx.scene.control.TextInputDialog;
import pl.edu.wat.kulki.pz.utils.ResourceLoader;

import java.util.Locale;
import java.util.Optional;

public class EndGameDialog extends Dialogs {

    public static String getPlayerName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(ResourceLoader.localeProperty("dialogTitle", Locale.getDefault().getLanguage()));
        makeDialog(dialog);
        dialog.setHeaderText("");
        dialog.setContentText(ResourceLoader.localeProperty("dialogText", Locale.getDefault().getLanguage()));
        Optional<String> result = dialog.showAndWait();
        return result.orElse(ResourceLoader.localeProperty("dialogOrElse", Locale.getDefault().getLanguage()));
    }
}
