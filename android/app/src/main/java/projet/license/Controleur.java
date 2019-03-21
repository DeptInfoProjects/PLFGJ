package projet.license;

import java.util.List;

import commun.Identification;
import commun.ListDemande;

public class Controleur {
    Connexion connexion;
    final Identification moi = new Identification("Moi sur Android", 1000);
    private Affichage affichage;

    public Controleur(Affichage mainActivity) {
        setAffichage(mainActivity);
    }

    public Connexion getConnexion() {
        return connexion;
    }



    public void setConnexion(Connexion connexion) {
        this.connexion = connexion;
    }
    public void aprèsConnexion() {connexion.envoyerId(moi);}
    public void msgValider(String list)     {connexion.envoyerValider(list);}
    public void msgStart()       {connexion.envoyerStart();}
    public void msgReset()       {connexion.envoyerReset();}
    public void msgColor()       {connexion.envoyerColor();}
    public void msgTutoriel()    {connexion.envoyerTutoriel();}


    public void majScor(boolean verif) {
        getAffichage().majScor(verif);
    }

    public void timeGameScor(Integer scor,Integer tentative) {
        getAffichage().timeGameScor(scor,tentative); }

    public void listTimeGame(List<String> listFormeDem, List<String> listFormeRec) {
        getAffichage().listTimeGame(listFormeDem,listFormeRec);

    }

    public void setAffichage(Affichage affichage) {
        this.affichage = affichage;
    }

    public Affichage getAffichage() {
        return affichage;
    }


    public void timeDetectorValider(String image) {connexion.timeImage(image);}

    public void sendImage(String image) {connexion.sendImage(image);}

    public void endTimeGame() {connexion.endTimeGame(); }

    public  void listTimeGame(){connexion.listResTimeGame();}


}