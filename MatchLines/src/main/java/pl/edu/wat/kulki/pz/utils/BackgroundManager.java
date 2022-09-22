package pl.edu.wat.kulki.pz.utils;


import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.VBox;

public class BackgroundManager {

    private static String bc1Style = ResourceLoader.property("bg1");
    private static String bc2Style = ResourceLoader.property("bg2");
    private static String bc3Style = ResourceLoader.property("bg3");
    private static String selectedInPreviousWindow = bc1Style;

    public static void setBackgroundStyle(String selected, VBox container, RadioMenuItem bc1,RadioMenuItem bc2,RadioMenuItem bc3){
        switch(selected){
            case "1":
                bc1.setSelected(true);
                selectedInPreviousWindow=bc1Style;
                container.setStyle(bc1Style);
                break;
            case "2":
                bc2.setSelected(true);
                selectedInPreviousWindow=bc2Style;
                container.setStyle(bc2Style);
                break;
            case "3":
                bc3.setSelected(true);
                selectedInPreviousWindow=bc3Style;
                container.setStyle(bc3Style);
        }
    }

    public static void getSelectedInPreviousWindow(RadioMenuItem bc1,RadioMenuItem bc2,RadioMenuItem bc3) {
        if (selectedInPreviousWindow.equals(bc1Style))
            bc1.setSelected(true);
        if (selectedInPreviousWindow.equals(bc2Style))
            bc2.setSelected(true);
        if (selectedInPreviousWindow.equals(bc3Style))
            bc3.setSelected(true);
    }
}
