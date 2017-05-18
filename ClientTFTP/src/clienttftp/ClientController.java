package clienttftp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
        int retour=0;
        client = new Client(this);
        try {
            System.out.println("je suis la");
            retour =client.receiveFile(pathField.getText(), nameField.getText(), adress.getText(), Integer.parseInt(port.getText()));
            error(retour);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void send(){
        int retour=0;
        client = new Client(this);
        String adr = adress.getText();
        String po = port.getText();
        int por = Integer.parseInt(po);
        retour=client.sendFile(path,fileName,fileName,adr,por);
        error(retour);

    }

    public TextArea getCodeRetour() {
        return codeRetour;
    }

    @FXML
    private TextArea codeRetour;

    private void error(int i){
        switch(i){
            case -1: codeRetour.setText("Echec de l'ouverture du fichier");
            case -2: codeRetour.setText("Délai d'attente dépassé");
            case -3: codeRetour.setText("Adresse IP non déterminé");
            case -4: codeRetour.setText("Erreur d'accès au socket");
            case -5: codeRetour.setText("Problème d'accès réseau");
            case -6: codeRetour.setText("Problème non identifié");
        }
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
