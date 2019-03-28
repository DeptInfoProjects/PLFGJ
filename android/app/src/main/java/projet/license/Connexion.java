package projet.license;



import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import commun.Identification;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connexion {

    private  Controleur controleur;
    Socket connexion;

    public Connexion(){}

    public Connexion(String urlServeur, final Controleur ctrl) {
        this.controleur = ctrl;
        controleur.setConnexion(this);

        try {
            connexion = IO.socket(urlServeur);

            System.out.println("on s'abonne à la connection / déconnection ");;

            connexion.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(" on est connecté ! et on s'identifie ");
                    controleur.aprèsConnexion();

                }
            });
            connexion.on("disconnect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(" !! on est déconnecté !! ");
                    connexion.disconnect();
                    connexion.close();
                }
            });
            connexion.on("forme_valide", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println("on a reçu une verification avec"+objects.length+" paramètre(s) ");
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

                    List<String> listFormeDem = (List<String>) args[0] ;
                    List<String> listFormeRec = (List<String>) args[1] ;
                    for(String ele : listFormeDem){
                        System.out.println(ele);
                    }
                    for(String ele : listFormeRec)
                        System.out.println(ele);
                    ctrl.listTimeGame(listFormeDem,listFormeRec);



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

    public void envoyerStart() {
        connexion.emit("btnStart" );
    }
    public void envoyerReset() {
        connexion.emit("btnEffacer" );
    }
    public void envoyerColor() { connexion.emit("btnColor" ); }
    public void envoyerValider(String list) {
        connexion.emit("nbpoints", list);
    }
    public void envoyerTutoriel(){connexion.emit("Bien recu5");}


    public void sendImage(String image) {connexion.emit("imageB64",image);}
    public void timeImage(String image) {connexion.emit("timeImage",image);}

    public void rtoImage(String image) {connexion.emit("rtoCoup",image);}


    public void endTimeGame() {connexion.emit("endTimeGame"); }
    public void listResTimeGame() {connexion.emit("listResTimeGame"); }

    public void setSocket(Socket socket) {this.connexion = socket; }
    public void setControleur(Controleur ctrl) {this.controleur = ctrl;}
}
