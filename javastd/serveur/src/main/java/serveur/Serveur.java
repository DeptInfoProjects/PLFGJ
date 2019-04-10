package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;


import detector.RtoDetector;
import detector.shapedetector;
import org.opencv.core.Core;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;



public class Serveur {
    String player;
    SocketIOServer serveur;
    final Object attenteConnexion = new Object();
    private ArrayList<String> time_detector_demander = new ArrayList<>();
    private ArrayList<String> time_detector_dessiner = new ArrayList<>();
    private Integer compter = 0;

    public Serveur() { } // utilisation pour les tests


    public Serveur(Configuration config) {
        serveur = new SocketIOServer(config);
        System.out.println("préparation du listener");

        serveur.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("-------------------------------------------------------------");
                System.out.println("connexion de " + socketIOClient.getRemoteAddress());
            }
        });


// *********************************************************************************************************************
//PARTIE COMMUNICATION CLIENT VERS SERVEUR
// *********************************************************************************************************************


        serveur.addEventListener("username", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                System.out.println("Welcome " + s);
                player = s;
            }
        });
        serveur.addEventListener("nbpoints", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String coup, AckRequest ackRequest) throws Exception {
                String[] list = coup.split(",");
                System.out.println(list);
                boolean verif = verifier(list);
                if ( verif ) {
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("Forme demandée   : " + list[0]);
                    System.out.println("Points donnés    : " + list[1]);
                    System.out.println("serveur.Serveur :  Forme valide , le client passe a la prochaine ");
                    formeValide(socketIOClient, verif);

                } else {
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("Forme demandée   : " + list[0]);
                    System.out.println("Points donnés    : " + list[1]);
                    System.out.println("serveur.Serveur :  Forme pas valide , le client passe a la prochaine ");
                    formeValide(socketIOClient, verif);
                }

            }
        });
		
		
		        serveur.addEventListener("riddleImage", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                String[] list = s.split(",");
                byte[] imgbytes;
                imgbytes = Base64.getMimeDecoder().decode(list[1]);


                final File file = new File("shapes.png");
                final FileOutputStream fileOut = new FileOutputStream(file);
                fileOut.write(imgbytes);
                fileOut.flush();
                fileOut.close();

                RtoDetector rt = new RtoDetector() ;
                String imageRec = rt.detectRtoS("shapes.png");

                System.out.println("Réponse reçue :" + imageRec);
                System.out.println("Réponse attendue :" + list[0]);

                riddleGame(socketIOClient,list[0].equals(imageRec));



            }
        });
		
		
        serveur.addEventListener("timeImage", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                String[] list = s.split(",");
                byte[] imgbytes;

                //System.out.println("s de talle : "+s.length());
                imgbytes = Base64.getMimeDecoder().decode(list[1]);

                final File file = new File("shapes.png");
                final FileOutputStream fileOut = new FileOutputStream(file);
                fileOut.write(imgbytes);
                fileOut.flush();
                fileOut.close();

                shapedetector sd = new shapedetector();
                time_detector_demander.add(list[0]);
                time_detector_dessiner.add(sd.detectShapes("shapes.png").get(0));
                compter ++;

                String res = list[0];
                res  += ",";
                res  += res + sd.detectShapes("shapes.png").get(0);
                System.out.println(compter + " : Forme Demande : " + time_detector_demander);
                System.out.println("  : Forme Dessine : " + time_detector_dessiner);
                listTimeGame(socketIOClient,res);
            }
        });

        serveur.addEventListener("rtoCoup", String.class, new DataListener<String>() {
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

                ArrayList<String> reponse;


                RtoDetector coupRto = new RtoDetector();
                reponse = coupRto.reponseServeur("shapes.png");


                System.out.println("---------------------------------------------------------------");
                System.out.println("RTO \nLe client a joué " + reponse.get(0));
                System.out.println("Le serveur a joué " + reponse.get(1));
                System.out.println("gagnant : " + reponse.get(2));

                System.out.println("---------------------------------------------------------------");



                resultatRto(socketIOClient, reponse.get(0), reponse.get(1), reponse.get(2));

            }
        });


    }



// *********************************************************************************************************************
// METHODES UTILES
// *********************************************************************************************************************

    private void demarrer() throws IOException {

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

    private static boolean verifier(String[] args){ // Verification de forme pour le DrawDetector
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







// *********************************************************************************************************************
//PARTIE COMMUNICATION SERVEUR VERS CLIENT
// *********************************************************************************************************************

    private void formeValide(SocketIOClient socketIOClient, boolean verif) {
        socketIOClient.sendEvent("forme_valide", verif);
    }
    private  void listTimeGame(SocketIOClient socketIOClient,String res){
        socketIOClient.sendEvent("listResTimeGame", res );
    }

    private  void resultatRto(SocketIOClient socketIOClient, String cpJr, String cpSv, String res){
        socketIOClient.sendEvent("resultatRto", cpJr, cpSv, res);
    }

    private void riddleGame(SocketIOClient socketIOClient,boolean rep){
        socketIOClient.sendEvent("riddleGameRes",rep);
    }



// *********************************************************************************************************************
// MAIN
// *********************************************************************************************************************

    public static void loadOpenCV() throws IOException, URISyntaxException {
        // pour trouver le chemin dans le jar d'opencv
        String os = System.getProperty("os.name").toLowerCase();
        String ext = ".dll";
        String prefixe = "lib";
        if (os.indexOf("windows") >=0)  {
            os = "windows";
            prefixe = "";
        }
        else if (os.indexOf("mac") >= 0) {
            os = "osx";
            ext = ".dylib";
        }
        else {
            os="linux";
            ext = ".so";
        }

        // on pourrait mettre la lib dans un dossier temporaire... lÃ , on met Ã  la racine du projet
        File testLib = new File(prefixe+Core.NATIVE_LIBRARY_NAME + ext);


        // ne traite que les x86_64
        if (! testLib.exists()) {

            // phase 1 : retrouver la lib dans le jar (qui est un zip)
            String libLocation = new File(Core.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();

            ZipFile jarFile = new ZipFile(libLocation);
            String libInJar = "nu/pattern/opencv/"+os+"/x86_64/"+prefixe+ Core.NATIVE_LIBRARY_NAME + ext;
            ZipEntry entry = jarFile.getEntry(libInJar);

            System.out.println(libInJar);

            // phase 2 : recopy
            InputStream in = jarFile.getInputStream(entry);

            File fileOut = new File(prefixe+ Core.NATIVE_LIBRARY_NAME+ext);
            OutputStream out = new FileOutputStream(fileOut);


            byte[] buf = new byte[8192];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }

            // c'est recopiÃ©
            in.close();
            out.close();
        }

        // chargement de la lib
        System.load(testLib.getAbsolutePath());
    }
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return "";
    }

    public static final void main(String []args) throws IOException {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            loadOpenCV();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }





        Configuration config = new Configuration();
        config.setMaxFramePayloadLength(200000);
        config.setHostname("192.168.0.100");
        config.setPort(10101);


        Serveur serveur = new Serveur(config);
        serveur.demarrer();


        System.out.println("fin du main");

    }

}
