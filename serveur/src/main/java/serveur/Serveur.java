package serveur;

import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    public static void main(String arg[]){
        try{
            System.out.println("Waiting for Clients ...");
            ServerSocket server = new ServerSocket(3000);
            Socket s = server.accept();
            System.out.println("Welcome you are connected");
        }catch(Exception e){}
    }
}
