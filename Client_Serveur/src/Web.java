/**
 * Created by Fabien on 31/05/2017.
 */
public class Web {

    private Controler controler;

    ServeurPrimaire serveurPrimaire;

    Client client;

    public Web(){
        this.serveurPrimaire = new ServeurPrimaire();
        this.client = new Client();

    }

    public void setControler(Controler controler) {
        this.controler = controler;
    }
}
