import java.io.IOException;
import java.net.*;

/**
 * Created by Fabien on 24/05/2017.
 */
public class ServeurPrimaire {
    final static int BUF_SIZE = 1024;
    public  int portEcoute;
    private ServerSocket serverSocket;
    public ServeurPrimaire(){
        portEcoute = 2026;
        try {
            serverSocket = new ServerSocket(portEcoute);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lancer(){

        byte [] buffer = new byte[BUF_SIZE];
        try {
            if (true)
            {
                Socket clientSocket = serverSocket.accept();
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                int portClient = receivePacket.getPort();
                InetAddress address = receivePacket.getAddress();
                ServeurHTTP serveurHTTP = new ServeurHTTP(address,clientSocket, portClient);
                Thread thread = new Thread(serveurHTTP);
                thread.start();


            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
