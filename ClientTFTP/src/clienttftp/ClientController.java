package clienttftp;

import javafx.fxml.FXML;
import javafx.scene.control.*;


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
    private void initialize() {

    }

    @FXML
    private void receive() {
        int retour = 0;
        client = new Client(this);
        try {
            System.out.println("je suis la");
            String adr = adress.getText();
            String po = port.getText();
            if (testIp(adr,po)) {
                retour = client.receiveFile(pathField.getText(), nameField.getText(), adr, Integer.parseInt(po));
                error(retour);
            }
            } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean testIp(String adr, String po)
    {
        if(adr.equals("") || po.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Adresse IP ou port vide");
            alert.setContentText("Veuillez saisir une adresse IP et un port !");
            alert.showAndWait();
            return false;
        }else
            return true;


    }
    @FXML
    private void send() {
        int retour = 0;
        getCodeRetour().clear();
        client = new Client(this);
        String adr = adress.getText();
        String po = port.getText();
        if(testIp(adr,po)){


        if (pathField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chemin vide");
            alert.setContentText("Veuillez saisir un chemin !");
            alert.showAndWait();
        } else {


            int por = Integer.parseInt(po);
            retour = client.sendFile(pathField.getText(), nameField.getText(), adr, por);
            error(retour);
            pathField.clear();
            nameField.clear();
        }
        }
    }

    public TextArea getCodeRetour() {
        return codeRetour;
    }

    @FXML
    private TextArea codeRetour;

    private void popUpAlert(String text) {

    }

    private void error(int i) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        String msg;
        switch (i) {
            case -1:
                msg = "Echec de l'ouverture du fichier";
                codeRetour.setText(msg);

                break;
            case -2:
                msg = "Délai d'attente dépassé";
                codeRetour.setText(msg);

                break;
            case -3:
                msg = "Adresse IP non déterminé";
                codeRetour.setText(msg);
                break;
            case -4:
                msg = "Erreur d'accès au socket";
                codeRetour.setText(msg);
                break;
            case -5:
                msg = "Problème d'accès réseau";
                codeRetour.setText(msg);
                break;
            case -6:
                msg = "Problème non identifié";
                codeRetour.setText(msg);
                break;
            case -7:
                msg = "Le fichier existe déjà";
                break;
            case -8:
                msg = "Adresse Ip vide, Veuillez saisir une adresse IP !";
            default:
                msg = "";
                break;
        }
        alert.setContentText(msg);
        if (!msg.equals("")) alert.showAndWait();
    }

    public void setApp(ClientTFTP app) {
        this.app = app;
    }

    @FXML
    private void chooseFile() {
        getCodeRetour().clear();
        this.app.choose();
    }

    public void setFile(String path, String name) {
//        this.path = path;
//        this.fileName = name;
        this.pathField.setText(path);
        this.nameField.setText(name);
    }
}
