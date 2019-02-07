
import java.net.*;
import java.rmi.server.ExportException;

public class Server {
    public static void main(String arg[]){
        try{
            ServerSocket server = new ServerSocket(3000);
            Socket s = server.accept();
            System.out.println("Welcome you are connected");
        }catch(Exception e){}
    }
}
