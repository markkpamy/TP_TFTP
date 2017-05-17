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

    private String path;

    @FXML
    private TextField pathField;

    @FXML
    private TextField nameField;

    @FXML
    private Button send;

    @FXML
    private TextField adress;

    @FXML
    private TextField port;

    private Client client;
    private String fileName;

    public ClientController() {
    }

    @FXML
    private void initialize()
    {

    }

    @FXML
    private void receive()
    {
        client = new Client();
        try {
            client.receiveFile(pathField.getText(), nameField.getText(), adress.getText(), Integer.parseInt(port.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void send(){
        client = new Client();
        String adr = adress.getText();
        String po = port.getText();
        int por = Integer.parseInt(po);
        client.sendFile(path,fileName,fileName,adr,por);
    }

    public void setApp(ClientTFTP app) {
        this.app = app;
    }

    @FXML
    private void chooseFile(){
        this.app.choose();
    }

    public void setFile(String path, String name) {
        this.path = path;
        this.fileName = name;
        this.pathField.setText(path);
        this.nameField.setText(name);
    }
}
