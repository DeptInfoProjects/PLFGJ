package projet.license;

import java.util.List;

public interface Affichage {

    void majScor(boolean ok);

    void timeGameScor(Integer score, Integer tentative);

    void listTimeGame(List<String> listFormeDem, List<String> listFormeRec);

    void rtoGameScore(String coupJoueur, String coupServeur, String resultat);
}
