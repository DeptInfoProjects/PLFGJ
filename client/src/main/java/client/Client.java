package client;

import java.io.IOException;
import java.net.*;
import java.net.InetAddress;

public class Client {

    private InetAddress addr ;
    private String addresse;
    private String hostname;

    public Client() throws UnknownHostException {
        this.addr  = InetAddress.getLocalHost();
        this.addresse =  this.getAddress();
        this.hostname = addr.getHostName();
    }


    public String getAddress(){
        return this.addr.getHostAddress();
    }

    String getName(){
        return this.addr.getHostName();
    }

    public static void main(String arg[]) throws UnknownHostException {
        Client c = new Client();
        try{
            String myIp = c.addresse;
            String hostname = c.addr.getHostName();
            Socket s = new Socket(c.addr,3000);
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
