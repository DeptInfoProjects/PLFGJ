package commun;


import android.os.Bundle;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Controleur {
    Connexion connexion;
    private  Identification moi = new Identification("Moi sur Android");
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
        getAffichage().majScor(verif);
    }

    public void timeGameScor(Integer scor,Integer tentative) {
        getAffichage().timeGameScor(scor,tentative);

        /*
        Bundle bundle = new Bundle();
        bundle.putInt("scor", scor);
        bundle.putInt("tentative", tentative);
        getAffichage().majGraphic("timeGameScor", bundle);
        */

    }

    public void listTimeGame(String listFormeDem,String listFormeRec) {
        getAffichage().listTimeGame(listFormeDem,listFormeRec);
    }
    public void resultatRto(String coupJoueur, String coupServeur, String resultat) {
        getAffichage().rtoGameScore(coupJoueur, coupServeur, resultat);
    }

    public void riddleRep(boolean rep) {getAffichage().riddleGame(rep); }


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