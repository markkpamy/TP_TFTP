package Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

/**
 * Created by Fabien on 31/05/2017.
 */
public class Controler {

    @FXML
    WebView requeteView;

    @FXML
    WebView renderView;

    @FXML
    TextField adressField;

    @FXML
    TextField portField;

    @FXML
    TextField fileField;

    @FXML
    CheckBox checkPort;

    @FXML
    Button getButton;

    @FXML
    ImageView imageView;

    private Web web;
    public Controler(){
//        Image.widthProperty().add(imageView.getFitWidth());
//        Image.heightProperty().add(imageView.getFitHeight());
        //this.imageView = new ImageView(Image);
        //imageView.setImage(new Image("Image.jpg"));
        //imageView = new ImageView(Image);
    }

    @FXML
    private void setPortVisible(){
        if(checkPort.isSelected())
            portField.setVisible(true);
        else{
            portField.setVisible(false);
            portField.clear();
        }

    }

    @FXML
    private void getFile() {
        String adress, file;
        int port;
        adress = adressField.getText();
        file = fileField.getText();
        if(portField.isVisible()) {
            port = Integer.parseInt(portField.getText());
        }
        else port=-1;
        //web.requeteClient(adress,file,port);
        web.requeteClient("127.0.0.1", "image.jpg",-1);
        //setImageView("Image/Image.jpg");
    }

    public void setWeb(Web web) {
        this.web = web;
    }

    public void setImageView(String url)
    {
        Image image = new Image(url);
        imageView.setImage(image);
    }

}
