/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienttftp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author markk
 */
public class ClientTFTP extends Application {

    private Stage primaryStage;
    private Pane pane;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initWindow();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        Client client = new Client("127.0.0.1");
    }

    public void initWindow()
    {
        try{
            //Chargement du fichier fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientTFTP.class.getResource("View.fxml"));
            pane = loader.load();

            //Affiche sur l application
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
}
