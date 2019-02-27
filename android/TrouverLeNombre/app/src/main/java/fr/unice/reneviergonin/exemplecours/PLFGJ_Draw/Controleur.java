package fr.unice.reneviergonin.exemplecours.PLFGJ_Draw;

import java.util.ArrayList;

import commun.Coup;
import commun.Identification;

public class Controleur {

    Connexion connexion;
    Vue vue;

    int dernierCoup = -1;

    final Identification moi = new Identification("Moi sur Android", 1000);

    public Controleur(Vue vue) {
        this.vue = vue;
    }

    public Vue getVue() {
        return vue;
    }
    public void setVue(Vue vue) {
        this.vue = vue;
    }
    public Connexion getConnexion() {
        return connexion;
    }
    public void setConnexion(Connexion connexion) {
        this.connexion = connexion;
    }



    public void aprèsConnexion() {
        connexion.envoyerId(moi);

    }

    public void finPartie() {
        vue.afficheMessage("bravo !!");

    }

    public void jeRejoue() {
        int val = 0 ;
        if (val >= 0) {
            dernierCoup = val;
            connexion.envoyerCoup(val);
        }
        else {
            vue.afficheMessage("Retentez ..");
        }

    }

    public void rejouer(boolean plusGrand, ArrayList<Coup> coups) {
        vue.afficheMessage(plusGrand?"c'est plus petit que "+dernierCoup:"c'est plus grand que "+dernierCoup);

    }

    public void premierCoup() {
        vue.afficheMessage("Dessiner une forme");
    }
}