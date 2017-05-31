import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Fabien on 31/05/2017.
 */
public class App extends Application{
    private Stage primaryStage;
    private Pane pane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Serveur HTTP");
        intWindow();
    }

    private void intWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("Vue.fxml"));
        try {
            pane = loader.load();
            Controler controler = new Controler();
            controler = loader.getController();
            Web web = new Web();
            web.setControler(controler);
            controler.setWeb(web);
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
