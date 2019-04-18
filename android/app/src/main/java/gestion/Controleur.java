package gestion;


import android.os.Bundle;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import commun.Identification;
import commun.StatsData;

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

    public void enigmeImplementation(String enigme , String reponse){
        Bundle bundle = new Bundle();
        bundle.putString("enigme",enigme);
        bundle.putString("reponse",reponse);

        getAffichage().majGraphic("enigmeImp",bundle);
    }

    public void enigmeRecuperation(String enigme,String reponse) {
        Bundle bundle = new Bundle();
        bundle.putString("enigme",enigme);
        bundle.putString("reponse",reponse);

        getAffichage().majGraphic("enigmeRec",bundle);
    }


    public void riddleRep(boolean reponse) {


        Bundle bundle = new Bundle();
        bundle.putBoolean("reponse", reponse);
        getAffichage().majGraphic("riddleRep", bundle);

    }
    public void statJoueur(int scorDraw, int tentDraw, int scorRto, int tentRto, int scorRiddle, int tentRiddle, int scorTime, int tentTime) {
        Bundle bundle = new Bundle();
        bundle.putInt("scorRto",scorRto);
        bundle.putInt("tentRto",tentRto);
        bundle.putInt("scorDraw",scorDraw);
        bundle.putInt("tentDraw",tentDraw);
        bundle.putInt("scorTime",scorTime);
        bundle.putInt("tentTime",tentTime);
        bundle.putInt("scorRiddle",scorRiddle);
        bundle.putInt("tentRiddle",tentRiddle);
        getAffichage().majGraphic("statJoueur",bundle);
    }


    public void setAffichage(Affichage affichage) {
        this.affichage = affichage;
    }

    public Affichage getAffichage() {
        return affichage;
    }


    public void timeDetectorValider(String image) {connexion.timeImage(image);}


    public void endTimeGame() {connexion.endTimeGame(); }
    public  void listTimeGame2(){connexion.listResTimeGame();}

    public void getStat() {connexion.getstat();}
    public void rtoValider(String image) {connexion.rtoImage(image);}
    public void riddleValider(String image) {connexion.riddleImage(image);}
    public void enigmeValider(String allpropo) {connexion.enigmePropo(allpropo);}






    public void getListEnigme() {connexion.getEnigme();
    }


    public void getNewEnigme() {connexion.getNewEnigme();
    }
}