package commun;


import java.util.List;




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
        getAffichage().timeGameScor(scor,tentative); }

    public void listTimeGame(String listFormeDem,String listFormeRec) {
        getAffichage().listTimeGame(listFormeDem,listFormeRec);
    }
    public void resultatRto(String coupJoueur, String coupServeur, String resultat) {
        getAffichage().rtoGameScore(coupJoueur, coupServeur, resultat);
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

    public void rtoValider(String image) {connexion.rtoImage(image);}

}