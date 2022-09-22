package pl.edu.wat.kulki.pz;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.edu.wat.kulki.pz.utils.GameLogger;
import pl.edu.wat.kulki.pz.utils.ResourceLoader;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main extends Application {

    private Parent root;
    private static final String menuWidth = "window.width";
    private static final String menuHeight = "window.height";
    private static final String multiLangPath = "language";
    private static final String mainFXML = "mainFXML";
    public static final GameLogger logger = new GameLogger();
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(new Locale("pl"));
        loadView();
        primaryStage.setTitle("Kulki");
        Scene scene = new Scene(root, Integer.parseInt(ResourceLoader.property(menuWidth)), Integer.parseInt(ResourceLoader.property(menuHeight)));
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e->close());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void close(){
        logger.info("Koniec dzialania programu");
        executorService.shutdown();
        Platform.exit();
    }

    private void loadView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setResources(ResourceBundle.getBundle(ResourceLoader.property(multiLangPath), Locale.getDefault()));
        try {
            root = fxmlLoader.load(getClass().getResource(ResourceLoader.property(mainFXML)).openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        logger.info("Gra Kulki - Rozpoczecie pracy");
        ResourceLoader.getNewPropertiesInstance();
        launch(args);
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }
}
