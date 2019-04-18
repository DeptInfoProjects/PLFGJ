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
import utile.Enigme;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class Serveur {
    String player;
    SocketIOServer serveur;
    final Object attenteConnexion = new Object();
    private ArrayList<String> time_detector_demander = new ArrayList<>();
    private ArrayList<String> time_detector_dessiner = new ArrayList<>();
    private Integer compter = 0;
    private int scorDraw,scorRto,scorTime,scorRiddle = 0;
    private int tentDraw,tentRto,tentTime,tentRiddle = 0;
    private Integer compterEnigme = 3;
    private Integer compter2 = 0;


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
                    scorDraw ++;
                    tentDraw ++;
                    formeValide(socketIOClient, verif);

                } else {
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("Forme demandée   : " + list[0]);
                    System.out.println("Points donnés    : " + list[1]);
                    System.out.println("Retour au Client :  Forme pas valide , le client passe a la prochaine ");
                    tentDraw ++;
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

                if(list[0].equals(imageRec)){
                    scorRiddle ++;
                }
                tentRiddle++;

                riddleGame(socketIOClient,list[0].equals(imageRec));



            }
        });
		serveur.addEventListener("getNewEnigme", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {

                BufferedReader fileReader = null;
                try {
                    List<Enigme> enigmes = new ArrayList();
                    String line = "";
                    fileReader = new BufferedReader(new FileReader("enigmes.csv"));
                    while ((line = fileReader.readLine()) != null) {
                        String[] tokens = line.split(";");
                        if (tokens.length > 0) {
                            Enigme enigme = new Enigme(tokens[0], tokens[1]);
                            enigmes.add(enigme);
                            if (compter2 <= compterEnigme) {
                                returnEnigmes(socketIOClient, enigmes.get(compter2).getEnigme(), enigmes.get(compter2).getReponse());
                                compter2++;
                            } else {
                                compter2 = 0;
                            }
                        }
                    }
                }catch (Exception e) {
                    System.out.println("Error in CsvFileReader !!!");
                    e.printStackTrace();
                } finally {
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        System.out.println("Error while closing fileReader !!!");
                        e.printStackTrace();
                    }
                }

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

                if (time_detector_demander.get(time_detector_demander.size() -1).equals(time_detector_dessiner.get(time_detector_dessiner.size() -1))){
                    scorTime ++;
                }
                tentTime ++;
            }
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


                System.out.println("---------------------------------------------------------------");
                System.out.println("RTO \nLe client a joué " + reponse.get(0));
                System.out.println("Le serveur a joué " + reponse.get(1));
                System.out.println("gagnant : " + reponse.get(2));

                System.out.println("---------------------------------------------------------------");

                if(reponse.get(2).equals("Joueur")){
                    scorRto ++;
                    tentRto ++;
                }
                else if(reponse.get(2).equals("Serveur")){
                    tentRto ++;
                }
                resultatRto(socketIOClient, reponse.get(0), reponse.get(1), reponse.get(2));

            }
        });

        serveur.addEventListener("enigme", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                String[] allenigme = s.split(";");
                Enigme propo = new Enigme(allenigme[0],allenigme[1]);
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter("enigmes.csv",true);
                    fileWriter.append(propo.getLineSeparateur());
                    fileWriter.append(propo.getEnigme());
                    fileWriter.append(propo.getSeparateur());
                    fileWriter.append(propo.getReponse());

                    System.out.println("Enigme ajouter");
                    System.out.println("Enigme  : " + propo.getEnigme());
                    System.out.println("Reponse : " + propo.getReponse());
                    compterEnigme++;


                } catch (Exception e){
                    System.out.println("Error with CsvFileWriter");
                    e.printStackTrace();
                }finally {
                    try{
                        fileWriter.flush();
                        fileWriter.close();
                    }catch (IOException e){
                        System.out.println("Error while flushing/closing fileWriter");
                        e.printStackTrace();
                    }
                }

            }
        });




        serveur.addEventListener("getStat", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                returnStat(socketIOClient,scorDraw,tentDraw,scorRto,tentRto,scorRiddle,tentRiddle,scorTime,tentTime);

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
    private void listTimeGame(SocketIOClient socketIOClient,String res){
        socketIOClient.sendEvent("listResTimeGame", res );
    }
    private void resultatRto(SocketIOClient socketIOClient, String cpJr, String cpSv, String res){
        socketIOClient.sendEvent("resultatRto", cpJr, cpSv, res);
    }
    private void riddleGame(SocketIOClient socketIOClient,boolean rep){
        socketIOClient.sendEvent("riddleGameRes",rep);

    }
    private void returnStat(SocketIOClient socketIOClient, int scor1, int tent1, int scor2, int tent2, int scor3, int tent3, int scor4, int tent4) {
        socketIOClient.sendEvent("statReponse",scor1,tent1,scor2,tent2,scor3,tent3,scor4,tent4);
    }

    private void returnEnigmes(SocketIOClient socketIOClient,String enigme,String reponse){
        socketIOClient.sendEvent("enigmeReponse",enigme,reponse);
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
        config.setHostname("172.20.10.11");
        config.setPort(10101);


        Serveur serveur = new Serveur(config);
        serveur.demarrer();


        System.out.println("fin du main");

    }






}
