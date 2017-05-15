package clienttftp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * Created by Fabien on 14/05/2017.
 */
public class ClientController {

    private ClientTFTP app;

    @FXML
    private Button send;

    @FXML
    private TextField adress;

    @FXML
    private TextField port;

    private Client client;

    public ClientController() {
    }

    @FXML
    private void initialize()
    {

    }

    @FXML
    private void send(){
        client = new Client();
        String adr = adress.getText();
        String po = port.getText();

        System.out.println(adress.getText()+" "+port.getText());
    }

    public void setApp(ClientTFTP app) {
        this.app = app;
    }

    @FXML
    private void chooseFile(){
        this.app.choose();
    }
}
