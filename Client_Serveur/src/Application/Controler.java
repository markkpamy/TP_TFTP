package Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;

/**
 * Created by Fabien on 31/05/2017.
 */
public class Controler {

    @FXML
    TextArea headerReponseField;

    @FXML
    TextArea headerRequeteField;

    @FXML
    TextArea textAreaField;




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
//        Reception.widthProperty().add(imageView.getFitWidth());
//        Reception.heightProperty().add(imageView.getFitHeight());
        //this.imageView = new ImageView(Reception);
        //imageView.setImage(new Reception("Reception.jpg"));
        //imageView = new ImageView(Reception);
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
        clearAll();
        String adress, file;
        int port;
        adress = adressField.getText();
        file = fileField.getText();
        if(portField.isVisible()) {
            port = Integer.parseInt(portField.getText());
        }
        else port=-1;
        web.requeteClient(adress,file,port);
    }

    public void setWeb(Web web) {
        this.web = web;
    }

    public void setImageView(String url)
    {
        Image image = new Image(url);
        imageView.setImage(image);
    }

    public void setUrl(String url) {
        //String url = url;
        setImageView(url);
    }

    public void setHeaderReponseField(String s) {
        this.headerReponseField.setText(s);
    }

    public void setHeaderRequeteField(String s) {
        this.headerRequeteField.setText(s);
    }

    public void setTextView(String s) {
        this.textAreaField.setText(s);
    }

    public void clearAll(){
        this.imageView.setImage(null);
        this.setTextView("");
        this.setHeaderReponseField("");
        this.setHeaderRequeteField("");
    }


}
