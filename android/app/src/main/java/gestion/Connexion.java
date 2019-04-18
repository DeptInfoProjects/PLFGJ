package gestion;

import android.renderscript.Allocation;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import commun.Identification;
import commun.StatsData;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connexion {

    private  Controleur controleur;
    Socket connexion;
    StatsData saveStatsRTO;

    public Connexion(){}

    public Connexion(String urlServeur, final Controleur ctrl) {
        this.controleur = ctrl;
        controleur.setConnexion(this);

        try {
            connexion = IO.socket(urlServeur);

            System.out.println("on s'abonne a  la connection / deconnection ");;

            connexion.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(" on est connecte ! et on s'identifie ");
                    controleur.apresConnexion();

                }
            });



            connexion.on("disconnect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(" !! on est deconnecte !! ");
                    connexion.disconnect();
                    connexion.close();
                }
            });
            connexion.on("forme_valide", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println("on a recu une verification avec"+objects.length+" parametre(s) ");
                    if (objects.length > 0 ) {
                        boolean verif = (Boolean)objects[0];
                        if(verif) {
                            System.out.println("le nombre de points est correcte");
                            ctrl.majScor(verif);
                        }
                        else {
                            System.out.println("le nombre de points est incorrecte");
                            ctrl.majScor(verif);
                        }
                    }
                }
            });

			connexion.on("riddleGameRes", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    boolean rep = (boolean) args[0] ;
                    ctrl.riddleRep(rep);
                }
            });

            connexion.on("scoreTimeGame", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Integer score =(Integer) args[0];
                    Integer tentative = (Integer) args[1];
                    ctrl.timeGameScor(score,tentative );

                }
            });
            connexion.on("listResTimeGame", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String listforme =  (String) args[0] ;
                    String[] newL = listforme.split(",");
                    String listFormeDem = newL[0];
                    String listFormeRec = newL[1];
                    ctrl.listTimeGame(listFormeDem,listFormeRec);



                }
            });

            connexion.on("enigmeImp", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String enigme = (String) args[0];
                    String reponse = (String) args[1];
                    ctrl.enigmeImplementation(enigme,reponse);
                }
            });

            connexion.on("enigmeReponse", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                   String enigme  = (String) args[0];
                   String reponse = (String) args[1];

                    ctrl.enigmeRecuperation(enigme,reponse);
                }
            });

            connexion.on("resultatRto", new Emitter.Listener() {
                @Override
                public void call(Object... args) {

                    String coupJoueur = (String) args[0] ;
                    String coupServeur = (String) args[1] ;
                    String resultat = (String) args[2] ;

                    ctrl.resultatRto(coupJoueur,coupServeur,resultat);


                }
            });
            connexion.on("statReponse", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    int scorDraw     = (int)args[0];
                    int tentDraw     = (int)args[1];
                    int scorRto      =    (int)args[2];
                    int tentRto  =   (int)args[3];
                    int scorRiddle    = (int)args[4];
                    int tentRiddle    = (int)args[5];
                    int scorTime    = (int)args[6];
                    int tentTime    = (int)args[7];

                    ctrl.statJoueur(scorDraw,tentDraw,scorRto,tentRto,scorRiddle,tentRiddle,scorTime,tentTime);
                }});


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }



    public boolean seConnecter() {
        // on se connecte
        Log.e("debug", "essaie de se connecter");
        connexion.connect();
        return true;


    }

    public void envoyerId(Identification moi) {
        // pas de conversion automatique obj <-> json avec le json de base d'android
        JSONObject pieceJointe = new JSONObject();
        try {
            pieceJointe.put("nom", moi.getNom());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        connexion.emit("identification", pieceJointe);
    }

    // pour DrawDetector
    public void envoyerValider(String list) {
        connexion.emit("nbpoints", list);
    }

    // Envoie d'images au serveur
    public void timeImage(String image) {connexion.emit("timeImage",image);}
    public void rtoImage(String image) {connexion.emit("rtoCoup",image);}
    public void riddleImage(String image) {connexion.emit("riddleImage",image); }

    // pour timeDetector
    public void endTimeGame() {connexion.emit("endTimeGame"); }
    public void listResTimeGame() {connexion.emit("listResTimeGame"); }


    public void setSocket(Socket socket) {this.connexion = socket; }
    public void setControleur(Controleur ctrl) {this.controleur = ctrl;}

    String x = "hello";
    public void getstat() {connexion.emit("getStat",x);
    }

    public void enigmePropo(String allpropo) {connexion.emit("enigme",allpropo);
    }

    public void getEnigme() {connexion.emit("getEnigme",x);
    }

    public void getNewEnigme() {connexion.emit("getNewEnigme",x);
    }
}
