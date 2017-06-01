import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    private Socket clientSocket;
    private int clientPort = 0;


    public ServeurHTTP(InetAddress clientAdress, Socket clientSocket, int clientPort) {
        this.clientAdress = clientAdress;
        this.clientSocket = clientSocket;
        this.clientPort = clientPort;
    }



    @Override
    public void run() {
        /**
         * Creation du flux in et out
         */
        DataInputStream inFromClient = null;
        try {
            inFromClient = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outToClient = null;
            outToClient = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
