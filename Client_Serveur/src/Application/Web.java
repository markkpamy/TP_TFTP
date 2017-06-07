package Application;

import Serveur.ServeurPrimaire;

import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * Created by Fabien on 31/05/2017.
 */
public class Web {

    private Controler controler;

    ServeurPrimaire serveurPrimaire;

    Client client;

    public Web(){
        //this.serveurPrimaire = new ServeurPrimaire();
    }

    public void requeteClient(String chemin, String fichier, int port){
        System.out.println("On créer le client");
        this.client = new Client();
        String[] recu = null;
        try {
            //this.serveurPrimaire.lancer();
            recu = client.recevoir(chemin,fichier,port);
            File f = new File("src/Image");

            File[] fd = f.listFiles();
            for(int i =0; i<fd.length; i++){
                fd[i].getName();
            }
            boolean tmp = false;
//            while(!tmp){
//                boolean vie =true;
//                try {
//                    controler.setImageView(recu[0]);
//                }catch (IllegalArgumentException e){
//                    vie = false;
//                }
//                System.out.println(vie);
//                tmp = vie;
//            }

            String url = "file:///"+System.getProperty("user.dir")+"/src/Image/"+recu[0];
            System.out.println(url);
            controler.setImageView(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
    public void chargerImage(String url){
        controler.setImageView(url);
    }

    public void setControler(Controler controler) {
        this.controler = controler;
    }
}
