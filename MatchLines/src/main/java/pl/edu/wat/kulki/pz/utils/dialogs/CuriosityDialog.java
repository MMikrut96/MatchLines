package pl.edu.wat.kulki.pz.utils.dialogs;

import javafx.scene.control.Alert;
import pl.edu.wat.kulki.pz.exceptions.WrongLoadFromURLException;
import pl.edu.wat.kulki.pz.utils.ResourceLoader;
import pl.edu.wat.kulki.pz.utils.curiosity.CuriosityLoader;
import pl.edu.wat.kulki.pz.utils.curiosity.GoldPrice;

import java.io.IOException;
import java.util.Locale;

public class CuriosityDialog extends Dialogs {

    public static void showCuriosity(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ResourceLoader.localeProperty("curiosityDialogTitle", Locale.getDefault().getLanguage()));
        alert.setHeaderText("");
        String adress = ResourceLoader.property("gsonAdress");
        GoldPrice[] gp = new GoldPrice[0];
        try {
            gp = CuriosityLoader.getPriceOfGold(adress);
        } catch (IOException e) {
            throw new WrongLoadFromURLException();
        }
        alert.setContentText(ResourceLoader.localeProperty("curiosityDialogText",
                Locale.getDefault().getLanguage())+gp[0].getCena()+"zl");
        makeDialog(alert);
        alert.showAndWait();
    }
}
