import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
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

    private Web web;
    public Controler(){

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
        port = Integer.parseInt(portField.getText());

    }

    public void setWeb(Web web) {
        this.web = web;
    }


}
