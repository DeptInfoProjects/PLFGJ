package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import commun.Identification;
import commun.shapedetector;
import org.opencv.core.Core;

import java.io.*;
import java.util.Base64;

public class Serveur {

    SocketIOServer serveur;
    final Object attenteConnexion = new Object();

    Identification client;


    public Serveur(Configuration config)  {
        serveur = new SocketIOServer(config);
        System.out.println("préparation du listener");
        serveur.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("-------------------------------------------------------------");
                System.out.println("connexion de "+socketIOClient.getRemoteAddress());

                // on ne s'arrête plus ici
            }
        });


        serveur.addEventListener("nbpoints", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String forme2, AckRequest ackRequest) throws Exception {
                String[] list = forme2.split(",");
                boolean verif = verifier(list);
                if ( verif ) {
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("Forme demandé   : " + list[0]);
                    System.out.println("Points donné    : " + list[1]);
                    System.out.println("Serveur :  Forme valide , le client passe a la prochaine ");
                    formeValide(socketIOClient, verif);

                } else {
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("Forme demandé   : " + list[0]);
                    System.out.println("Points donné    : " + list[1]);
                    System.out.println("Serveur :  Forme pas valide , le client passe a la prochaine ");
                    formeValide(socketIOClient, verif);
                }

            }
        } );



        serveur.addEventListener("Bien recu2", Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient,Object o , AckRequest ackRequest) throws  Exception{
                System.out.println("-------------------------------------------------------------");
                System.out.println("Client  : Changement de forme geometrique ");
                System.out.println("Serveur : Forme demandé a changé " );
            }
        });

        serveur.addEventListener("Bien recu3", Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient,Object o, AckRequest ackRequest) throws  Exception{
                System.out.println("-------------------------------------------------------------");
                System.out.println("Client  : Effacement du Canvas");
                System.out.println("Serveur : Nouveau Canvas a votre disposition ");
            }
        });

        serveur.addEventListener("Bien recu4", Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient,Object o, AckRequest ackRequest) throws  Exception{
                System.out.println("-------------------------------------------------------------");
                System.out.println("Client  : Changement de Couleur");
                System.out.println("Serveur : Nouvelle couleur a votre disposition ");
            }
        });

        serveur.addEventListener("Bien recu5", Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient,Object o, AckRequest ackRequest) throws  Exception{
                System.out.println("-------------------------------------------------------------");
                System.out.println("Client  : Affichage de Tuto");
                System.out.println("Serveur : Tutoriel du jeu a votre disposition ");
            }
        });

        serveur.addEventListener("imageB64", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {

                byte[] imgbytes;

                //System.out.println("s de talle : "+s.length());
                imgbytes = Base64.getMimeDecoder().decode(s);

                final File file = new File("shapes.png");
                final FileOutputStream fileOut = new FileOutputStream(file);
                fileOut.write(imgbytes);
                fileOut.flush();
                fileOut.close();

                //Mat imgMat = Imgcodecs.imdecode(new MatOfByte(imgbytes), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);

                shapedetector sd = new shapedetector();

                System.out.println(sd.detectShapes("shapes.png"));
            }
        });

    }



    private void démarrer() throws IOException {

        serveur.start();

        System.out.println("en attente de connexion");
        synchronized (attenteConnexion) {
            try {
                attenteConnexion.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("erreur dans l'attente");
            }
        }
        System.out.println("Une connexion est arrivée, on arrête");
        serveur.stop();

    }




    private void formeValide(SocketIOClient socketIOClient, boolean verif) {
        socketIOClient.sendEvent("forme_valide", verif);
    }




    private boolean verifier(String[] args){
        if (args[0].equals("Triangle") && args[1].equals("3")){
            return true;}
        if (args[0].equals("Rond") && args[1].equals("5")){
            return true;}
        if (args[0].equals("Carre") && args[1].equals("4")){
            return true;}
        if (args[0].equals("Segment") && args[1].equals("2")){
            return true;}
        if (args[0].equals("Point") && args[1].equals("1")){
            return true;}
        return false;
    }

    public static final void main(String []args) throws IOException {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        nu.pattern.OpenCV.loadShared();
        Configuration config = new Configuration();
        //fac
        //config.setHostname("172.20.10.11");
        //config.setHostname("172.20.10.2");
        //spiti
        config.setHostname("192.168.0.18");
        //maison sabri
        //config.setHostname("192.168.1.26");
        //tilefono
        //config.setHostname("192.168.43.60");
        config.setPort(10101);


        Serveur serveur = new Serveur(config);
        serveur.démarrer();


        System.out.println("fin du main");


    }



}











