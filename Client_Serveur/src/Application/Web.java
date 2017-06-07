package Application;

import Serveur.ServeurPrimaire;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * Created by Fabien on 31/05/2017.
 */
public class Web {

    private Controler controler;


    Client client;

    public Web(){
    }

    public void requeteClient(String chemin, String fichier, int port){
        String[] recu = null;
        try {
            this.client = new Client();

            recu = client.recevoir(chemin,fichier,port);
            if(recu[0].contains("Error-404")){
                String url = "file:///"+System.getProperty("user.dir")+"\\erreur_404.png";
                controler.setImageView(url);
                controler.setTextView("Le serveur ne contient pas ce fichier");
            }else if(recu[0].contains("Error-504")){
                String url = "file:///"+System.getProperty("user.dir")+"\\erreur_504.jpeg";
                controler.setImageView(url);
            }else {

                extensionFile(recu[0], recu[3]);

            }
            controler.setHeaderReponseField(recu[1]);
            controler.setHeaderRequeteField(recu[2]);



        } catch (IOException e) {
            System.out.println("Dans l'erreur");
            String url = "file:///"+System.getProperty("user.dir")+"\\erreur_504.jpeg";
            controler.setImageView(url);
            controler.setHeaderRequeteField("GET "+chemin+"/"+fichier+" HTTP/1.1");
            controler.setHeaderReponseField("Erreur 504 Bad-Gateway");
            controler.setTextView("Impossible de se connecter aux serveurs ! \nAvez vous correctement taper l'adresse ?\nAvez vous lancer le serveur");
            e.printStackTrace();

        }
    }

    public void extensionFile(String fichier, String extension)
    {
        String url = System.getProperty("user.dir")+"/src/Reception/"+fichier;

        boolean image=false, texte=false, html = false;
        switch (extension){
            case "jpg":
                image = true;
                break;
            case "jpeg":
                image = true;
                break;
            case "png":
                image = true;
                break;
            case "html":
                html = true;
                break;
            default:
                texte = true;
                break;
        }
        //SI C EST UNE IMAGE
        if (image){
            url = "file:///"+url;
            controler.setImageView(url);
        }else if(html){ //SI C EST UNE PAGE HTML

        }else{ //SINON ON CONSIDERE QUE C EST UN TEXTE
            File f = new File(url);
            int size = (int)f.length();
            byte[] streamBuffer = new byte[size];
            try {
                FileInputStream fileInputStream = new FileInputStream(f);
                fileInputStream.read(streamBuffer);
                fileInputStream.close();
                String s = new String(streamBuffer,"UTF-8");
                controler.setTextView(s);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void chargerImage(String url){
        controler.setImageView(url);
    }

    public void setControler(Controler controler) {
        this.controler = controler;
    }
}
