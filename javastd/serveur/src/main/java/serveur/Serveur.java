package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import commun.Identification;
import commun.RtoDetector;
import commun.shapedetector;
import org.opencv.core.Core;


import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Serveur {

    SocketIOServer serveur;
    final Object attenteConnexion = new Object();
    private List<String> time_detector_demander = new ArrayList<>();
    private List<String> time_detector_dessiner = new ArrayList<>();
    Identification client;
    private Integer compter = 0;


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
                System.out.println(list);
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



        serveur.addEventListener("btnStart", Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient,Object o , AckRequest ackRequest) throws  Exception{
                System.out.println("-------------------------------------------------------------");
                System.out.println("Client  : Changement de forme geometrique ");
                System.out.println("Serveur : Forme demandé a changé " );
            }
        });

        serveur.addEventListener("btnEffacer", Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient,Object o, AckRequest ackRequest) throws  Exception{
                System.out.println("-------------------------------------------------------------");
                System.out.println("Client  : Effacement du Canvas");
                System.out.println("Serveur : Nouveau Canvas a votre disposition ");
            }
        });

        serveur.addEventListener("btnColor", Object.class, new DataListener<Object>() {
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
                shapedetector sd = new shapedetector();
                System.out.println(sd.detectShapes("shapes.png"));
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
                System.out.println(compter + " : Forme Demandée : " + time_detector_demander);
                System.out.println("  : Forme Dessinée : " + time_detector_dessiner);
            }
        });


        serveur.addEventListener("endTimeGame", Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Object o, AckRequest ackRequest) throws Exception {
                endTimeGame(socketIOClient);
            }
        });

        serveur.addEventListener("listResTimeGame", Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Object o, AckRequest ackRequest) throws Exception {
                listTimeGame(socketIOClient);}
        });


        serveur.addEventListener("rtoCoup", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {

                byte[] imgbytes;

                imgbytes = Base64.getMimeDecoder().decode(s);

                final File file = new File("shapes.png");
                final FileOutputStream fileOut = new FileOutputStream(file);
                fileOut.write(imgbytes);
                fileOut.flush();
                fileOut.close();

                ArrayList<String> reponse;


                RtoDetector coupRto = new RtoDetector();
                reponse = coupRto.reponseServeur("shapes.png");


                resultatRto(socketIOClient, reponse.get(0), reponse.get(1), reponse.get(2));

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


    private void endTimeGame(SocketIOClient socketIOClient){
        int score = 0;
        int tendace = 0;
        for(int i = 0 ; i < time_detector_demander.size(); i++){
            if (time_detector_demander.get(i).equals(time_detector_dessiner.get(i))){
                score ++;
            }
            tendace ++;
        }
        socketIOClient.sendEvent("scoreTimeGame",score,tendace);
    }

    private void formeValide(SocketIOClient socketIOClient, boolean verif) {
        socketIOClient.sendEvent("forme_valide", verif);
    }

    private  void listTimeGame(SocketIOClient socketIOClient){
        socketIOClient.sendEvent("listResTimeGame",time_detector_demander,time_detector_dessiner);
    }

    private  void resultatRto(SocketIOClient socketIOClient, String cpJr, String cpSv, String res){
        socketIOClient.sendEvent("resultatRto", cpJr, cpSv, res);
    }





    public static boolean verifier(String[] args){
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

        // on pourrait mettre la lib dans un dossier temporaire... là, on met à la racine du projet
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

            File fileOut = new File(prefixe+Core.NATIVE_LIBRARY_NAME+ext);
            OutputStream out = new FileOutputStream(fileOut);


            byte[] buf = new byte[8192];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }

            // c'est recopié
            in.close();
            out.close();
        }

        // chargement de la lib
        System.load(testLib.getAbsolutePath());
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
        //fac
        //config.setHostname("10.1.124.22");
        //config.setHostname("172.20.10.11");
        //config.setHostname("172.20.10.2");
        //spiti
        config.setHostname("172.20.10.11");
        //maison sabri
        //config.setHostname("192.168.1.26");
        //tilefono
        //config.setHostname("192.168.0.101");
        config.setPort(10101);


        Serveur serveur = new Serveur(config);
        serveur.démarrer();


        System.out.println("fin du main");


    }

}