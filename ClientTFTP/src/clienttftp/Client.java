/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienttftp;

import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 *
 * @author markk
 */
public class Client {
    
private DatagramSocket socket;
private byte sentBuffer[];
private byte receiveBuffer[];	
private byte[] donneesFichier;
private int serverPort = 69;
private InetAddress serverAddress;
    
public Client(){

}

public int receiveFile(String addr,int port, String nomFichierDistant,String nomFichierLocal) throws Exception{
    
    DatagramPacket RRQ;
    DatagramPacket DATA;
    DatagramPacket ACK;
    FileOutputStream file;
    
    DatagramSocket portCom = new DatagramSocket();
     serverAddress = InetAddress.getByName(addr);
    return 0;

}

public int sendFile(InetAddress addr,int port,String nomFichierLocal){
    
    
    return 0;

}

public byte[] paquetWRQ(){
    return null;

}
public byte[] paquetRRQ(){
    return null;
}
    
}
