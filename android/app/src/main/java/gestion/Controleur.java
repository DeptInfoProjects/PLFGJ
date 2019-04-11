package gestion;


import android.os.Bundle;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import commun.Identification;

public class Controleur {
    Connexion connexion;
    private Identification moi = new Identification("Moi sur Android");
    private Affichage affichage;

    public Controleur(Affichage mainActivity) {
        setAffichage(mainActivity);
    }



    public Connexion getConnexion() {
        return connexion;
    }



    public void setConnexion(Connexion connexion) { this.connexion = connexion; }
    public void apresConnexion() {connexion.envoyerId(moi);}
    public void msgValider(String list)     {connexion.envoyerValider(list);}



    public void setIdentification(String username){moi.setNom(username);}


    public void majScor(boolean verif) {
        //getAffichage().majScor(verif);

        Bundle bundle = new Bundle();
        bundle.putBoolean("verif", verif);
        getAffichage().majGraphic("majScor", bundle);



    }


    public void timeGameScor(Integer scor,Integer tentative) {

        Bundle bundle = new Bundle();
        bundle.putInt("scor", scor);
        bundle.putInt("tentative", tentative);

        getAffichage().majGraphic("timeGameScor", bundle);


    }

    public void listTimeGame(String listFormeDem,String listFormeRec) {

        //getAffichage().listTimeGame(listFormeDem,listFormeRec);

        Bundle bundle = new Bundle();
        bundle.putString("listFormeDem", listFormeDem);
        bundle.putString("listFormeRec", listFormeRec);

        getAffichage().majGraphic("listTimeGame", bundle);


    }


    public void resultatRto(String coupJoueur, String coupServeur, String resultat) {
        //getAffichage().rtoGameScore(coupJoueur, coupServeur, resultat);

        Bundle bundle = new Bundle();
        bundle.putString("coupJoueur", coupJoueur);
        bundle.putString("coupServeur", coupServeur);
        bundle.putString("resultat", resultat);

        getAffichage().majGraphic("resultatRto", bundle);

    }



    public void riddleRep(boolean reponse) {
        //getAffichage().riddleGame(rep);

        Bundle bundle = new Bundle();
        bundle.putBoolean("reponse", reponse);
        getAffichage().majGraphic("riddleRep", bundle);

    }


    public void setAffichage(Affichage affichage) {
        this.affichage = affichage;
    }

    public Affichage getAffichage() {
        return affichage;
    }


    public void timeDetectorValider(String image) {connexion.timeImage(image);}


    public void endTimeGame() {connexion.endTimeGame();

    }
    public  void listTimeGame2(){connexion.listResTimeGame();}

    public void rtoValider(String image) {connexion.rtoImage(image);}
    public void riddleValider(String image) {connexion.riddleImage(image);}



    //pour recuperer l'adressIp
    public String getLocalIpAddress() {
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

}