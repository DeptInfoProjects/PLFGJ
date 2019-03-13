package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import commun.Coup;
import commun.Identification;
import commun.shapedetector;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;


import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Serveur {
    SocketIOServer serveur;
    final Object attenteConnexion = new Object();
    private int àTrouvé = 42;
    Identification leClient ;
    ArrayList<Coup> coups = new ArrayList<>();



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
        serveur.addEventListener("identification", Identification.class, new DataListener<Identification>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identification identification, AckRequest ackRequest) throws Exception {
                System.out.println("Le client est "+identification.getNom());
                leClient = new Identification(identification.getNom(), identification.getNiveau());

                // on enchaine sur une question
                poserUneQuestion(socketIOClient);
            }
        });
        serveur.addEventListener("réponse", int.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer integer, AckRequest ackRequest) throws Exception {
                System.out.println("La réponse de  "+leClient.getNom()+" est "+integer);
                Coup coup = new Coup(integer, integer > àTrouvé);
                if (integer == àTrouvé) {
                    System.out.println("le client a trouvé ! ");
                    synchronized (attenteConnexion) {
                        attenteConnexion.notify();
                    }
                } else
                {
                    coups.add(coup);
                    System.out.println("On a bien recu l'image ");
                    poserUneQuestion(socketIOClient, coup.isPlusGrand());
                }

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
    private void poserUneQuestion(SocketIOClient socketIOClient) {
        socketIOClient.sendEvent("question");
    }

    private void poserUneQuestion(SocketIOClient socketIOClient, boolean plusGrand) {
        socketIOClient.sendEvent("question", plusGrand, coups);
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
        Configuration config = new Configuration();
        //fac
        config.setHostname("10.1.124.22");
        //config.setHostname("172.20.10.2");
        //spiti
        //config.setHostname("192.168.0.18");
        //maison sabri
        //config.setHostname("192.168.1.26");
        config.setPort(10101);


        Serveur serveur = new Serveur(config);
        serveur.démarrer();


        System.out.println("fin du main");


    }




    private List<String> detect(Mat matrix) {
        List<String> shapeDessiner = new ArrayList<>();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //Imgcodecs imgcodecs;
        //imgcodecs = new Imgcodecs();
        //String file = "C:/Users/Kyriakos Petrou/Desktop/shape-detection/shape-detection/test.png";
        //Mat matrix = imgcodecs.imread(img);

        Mat resizematrix = new Mat();
        Size sz = new Size(matrix.width(), matrix.height());
        Imgproc.resize(matrix, resizematrix, sz);

        Mat resizeColor = new Mat();
        Imgproc.cvtColor(resizematrix, resizeColor, Imgproc.COLOR_BGR2GRAY);

        Mat resizeGauss = new Mat();
        Size sz2 = new Size(5, 5);
        Imgproc.GaussianBlur(resizeColor, resizeGauss, sz2, 0);
        Mat resizeThresh = new Mat();

        Imgproc.threshold(resizeGauss, resizeThresh, 60, 255, Imgproc.RETR_EXTERNAL);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(resizeThresh, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        shapedetector sd = new shapedetector();
        for (MatOfPoint c : contours) {
            Moments M = Imgproc.moments(c);
            int cX = (int) (M.m10 / M.m00);
            int cY = (int) (M.m01 / M.m00);
            Point sz3 = new Point(cX, cY);
            String shape = sd.detect(c);
            Scalar scalar = new Scalar(0, 255, 0);
            Scalar scalar2 = new Scalar(125, 125, 125);
            System.out.println(shape);
            Imgproc.drawContours(matrix, contours, -1, scalar, 2);
            Imgproc.putText(matrix, shape, sz3, Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, scalar2, 2);
            Image imageRes = sd.Mat2BufferedImage(matrix);
            sd.displayImage(imageRes);
            shapeDessiner.add(shape);

        }
        return shapeDessiner;
    }
}





