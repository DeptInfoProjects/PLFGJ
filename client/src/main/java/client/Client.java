package client;

import java.io.IOException;
import java.net.*;
import java.net.InetAddress;

public class Client {

    public static void main(String arg[]){
        try{
            InetAddress addr = InetAddress.getLocalHost();
            String myIp = addr.getHostAddress();
            String hostname = addr.getHostName();
            Socket s = new Socket(addr,3000);
            System.out.println("Welcome ");
            System.out.println("Local HostAddress : "+ myIp);
            System.out.println("Local Host Name   : " + hostname);
        } catch (UnknownHostException e) {
            {System.out.println("Serveur pas disponible");}
        } catch (IOException e) {
            {System.out.println("Serveur pas disponible");}
        } catch (Exception e) {
            {System.out.println("Serveur pas disponible");}
        }
    }
}
