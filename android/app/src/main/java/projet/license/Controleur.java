package projet.license;

import commun.Identification;

public class Controleur {
    Connexion connexion;
    int dernierCoup = -1;
    final Identification moi = new Identification("Moi sur Android", 1000);
    public Connexion getConnexion() {
        return connexion;
    }


    public void setConnexion(Connexion connexion) {
        this.connexion = connexion;
    }
    public void aprèsConnexion() {connexion.envoyerId(moi);}
    public void msgValider()     {connexion.envoyerValider();}
    public void msgStart()       {connexion.envoyerStart();}
    public void msgReset()       {connexion.envoyerReset();}
    public void msgColor()       {connexion.envoyerColor();}
    public void msgTutoriel()    {connexion.envoyerTutoriel();}



}