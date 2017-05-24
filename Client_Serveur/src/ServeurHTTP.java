import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Fabien on 24/05/2017.
 */
public class ServeurHTTP implements Runnable{

    private InetAddress clientAdress;
    private int portEcoute;
    private DatagramSocket clientSocket;
    private int clientPort = 0;


    public ServeurHTTP(InetAddress clientAdress, DatagramSocket clientSocket, int clientPort) {
        this.clientAdress = clientAdress;
        this.clientSocket = clientSocket;
        this.clientPort = clientPort;
    }



    @Override
    public void run() {

    }
}
