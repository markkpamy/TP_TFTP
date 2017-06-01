import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Fabien on 24/05/2017.
 */
public class Client {

    private Socket clientSocket;
    private InetAddress adresseIp;
    private String fichier;
    private String requete;

    public Client()
    {
        try {
            adresseIp = java.net.InetAddress.getByName("localhost");
            clientSocket = new Socket(adresseIp, 2002);
        }catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
