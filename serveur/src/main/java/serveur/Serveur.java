package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;


import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


import java.net.*;
import java.rmi.server.ExportException;

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
